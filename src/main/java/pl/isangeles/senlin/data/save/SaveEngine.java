/*
 * SaveEngine.java
 *
 * Copyright 2017-2018 Dariusz Sikora <dev@isangeles.pl>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 *
 *
 */
package pl.isangeles.senlin.data.save;

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Module;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.gui.tools.UserInterface;
import pl.isangeles.senlin.states.GameWorld;
import pl.isangeles.senlin.util.Settings;
import pl.isangeles.senlin.util.parser.SSGParser;

/**
 * Class for saving and loading game state
 *
 * @author Isangeles
 */
public class SaveEngine {
  public static final String SAVES_PATH =
      "savegames" + File.separator + Settings.getModuleName() + File.separator;
  /** Private constructor to prevent initialization */
  private SaveEngine() {};
  /**
   * Collects save data from player and game world and UI then saves it to file in savegames catalog
   *
   * @param player Player character to save
   * @param world Game world to save
   * @param ui UI to save
   * @param saveName Name for save game file
   * @throws ParserConfigurationException
   * @throws TransformerException
   */
  public static void save(Character player, GameWorld world, UserInterface ui, String saveName)
      throws ParserConfigurationException, TransformerException {
    if (saveName.isEmpty()) {
      Log.addSystem("Invalid save file name");
      return;
    }
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = dbf.newDocumentBuilder();
    Document doc = builder.newDocument();

    Element saveE = doc.createElement("save");
    doc.appendChild(saveE);

    Element gameE = doc.createElement("game");
    Element moduleE = doc.createElement("module");
    moduleE.setTextContent(Module.getName());
    moduleE.setAttribute("chapter", world.getCurrentChapter().getId());
    gameE.appendChild(moduleE);
    saveE.appendChild(gameE);

    Element playerE = doc.createElement("player");
    playerE.appendChild(player.getSave(doc));
    Element scenarioE = doc.createElement("scenario");
    scenarioE.setAttribute("area", player.getCurrentArea().getId());
    scenarioE.setTextContent(world.getCurrentChapter().getActiveScenario().getId());
    playerE.appendChild(scenarioE);
    saveE.appendChild(playerE);

    saveE.appendChild(world.getSave(doc));

    saveE.appendChild(ui.getSave(doc));

    TransformerFactory trf = TransformerFactory.newInstance();
    Transformer tr = trf.newTransformer();
    DOMSource doms = new DOMSource(doc);
    StreamResult sr = new StreamResult(new File(SAVES_PATH + saveName + ".ssg"));

    tr.transform(doms, sr);

    Log.addSystem("Game saved");
  }
  /**
   * Loads save with specified name from savegames catalog
   *
   * @param saveName Save game file name
   * @param gc Slick game container
   * @return SavedGame object
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws IOException
   * @throws FontFormatException
   * @throws SlickException
   */
  public static SavedGame load(String saveName, GameContainer gc)
      throws ParserConfigurationException, SAXException, IOException, FontFormatException,
          SlickException {
    saveName += ".ssg";
    File saveGames = new File(SAVES_PATH);
    File saveGame = null;
    for (File save : saveGames.listFiles()) {
      if (save.getName().equals(saveName)) saveGame = save;
    }
    if (saveGame == null) {
      Log.addSystem(saveName + " no such file");
      System.out.println(saveName + " no such file");
      return null;
    }

    return SSGParser.parseSSG(saveGame, gc);
  }
  /**
   * Loads module and active chapter from save with specified name
   *
   * @param saveName Save file name without .ssg extension
   * @return True if module was successfully loaded, false otherwise
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws IOException
   */
  public static boolean loadModuleFrom(String saveName)
      throws ParserConfigurationException, SAXException, IOException {
    saveName += ".ssg";
    File saveGames = new File(SAVES_PATH);
    File saveGame = null;
    for (File save : saveGames.listFiles()) {
      if (save.getName().equals(saveName)) saveGame = save;
    }
    if (saveGame == null) {
      return false;
    }

    boolean out = false;

    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = dbf.newDocumentBuilder();
    Document doc = builder.parse(saveGame);

    Element gameE = (Element) doc.getDocumentElement().getElementsByTagName("game").item(0);
    Element moduleE = (Element) gameE.getElementsByTagName("module").item(0);
    out = Module.setDir(moduleE.getTextContent());
    return out;
  }
}
