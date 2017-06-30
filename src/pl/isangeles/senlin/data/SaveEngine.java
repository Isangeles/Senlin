/*
 * SaveEngine.java
 * 
 * Copyright 2017 Dariusz Sikora <darek@darek-PC-LinuxMint18>
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
package pl.isangeles.senlin.data;

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.data.area.Scenario;
import pl.isangeles.senlin.inter.ui.UserInterface;
import pl.isangeles.senlin.util.parser.SSGParser;
/**
 * Class for saving and loading game state
 * @author Isangeles
 *
 */
public class SaveEngine 
{
	public static final String SAVES_PATH = "savegames" + File.separator;
	
	private SaveEngine(){};
	/**
	 * Collects save data from player character and game scenarios then saves it to file in savegames catalog
	 * @param player Player game character
	 * @param scenarios List with game scenarios
	 * @param saveName Name for save game file
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public static void save(Character player, List<Scenario> scenarios, String currentScenario, UserInterface ui, String saveName) throws ParserConfigurationException, TransformerException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document doc = builder.newDocument();
		
		Element saveE = doc.createElement("save");
		doc.appendChild(saveE);
		
		Element playerE = doc.createElement("player");
		playerE.appendChild(player.getSave(doc));
		Element scenarioE = doc.createElement("scenario");
		scenarioE.setTextContent(currentScenario);
		playerE.appendChild(scenarioE);
		saveE.appendChild(playerE);
		
		Element scenariosE = doc.createElement("scenarios");
		for(Scenario scenario : scenarios)
		{
		    scenariosE.appendChild(scenario.getSave(doc));
		}
		saveE.appendChild(scenariosE);
		
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
	 * @param saveName Save game file name
	 * @param gc Slick game container
	 * @return SavedGame object
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws FontFormatException
	 * @throws SlickException
	 */
	public static SavedGame load(String saveName, GameContainer gc) throws ParserConfigurationException, SAXException, IOException, FontFormatException, SlickException
	{
	    saveName += ".ssg";
	    File saveGames = new File(SAVES_PATH);
	    File saveGame = null;
	    for(File save : saveGames.listFiles())
	    {
	        if(save.getName().equals(saveName))
	            saveGame = save;
	    }
	    if(saveGame == null)
	    {
	        Log.addSystem(saveName + " no such file");
	        System.out.println(saveName + " no such file");
	        return null;
	    }
	    
	    return SSGParser.parseSSG(saveGame, gc);
	}
}
