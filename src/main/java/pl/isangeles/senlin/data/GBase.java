/*
 * GBase.java
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
package pl.isangeles.senlin.data;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import pl.isangeles.senlin.graphic.Sprite;
import pl.isangeles.senlin.util.GConnector;

/**
 * Base for graphical textures of UI elements Contains frequently used textures, used by some GUI
 * elements to reduce number of accesses to gData archive loaded in main menu
 *
 * @author Isangeles
 */
public final class GBase {
  private static Map<String, Image> texturesMap = new HashMap<>();
  private static Map<String, Font> fontsMap = new HashMap<>();
  /** Private constructor to prevent initialization */
  private GBase() {}
  /**
   * Returns image with specified ID from base
   *
   * @param name ID of desired image in base
   * @return Image with specified ID or null if no such image was found
   */
  public static Image getImage(String name) {
    return texturesMap.get(name);
  }
  /**
   * Returns font with specified ID form base
   *
   * @param name ID of desired font in base
   * @return Font with specified ID or null if no such font was found
   */
  public static Font getFont(String name) {
    return fontsMap.get(name);
  }
  /**
   * Loads base
   *
   * @throws IOException
   * @throws SlickException
   * @throws FontFormatException
   */
  public static void load() throws IOException, SlickException, FontFormatException {
    texturesMap.put("uiSlotA", new Image(GConnector.getInput("ui/slot.png"), "uiSlotA", false));
    texturesMap.put("uiSlotB", new Image(GConnector.getInput("ui/slotB.png"), "uiSlotB", false));
    texturesMap.put(
        "infoWinBg", new Image(GConnector.getInput("field/infoWindowBG.png"), "infoWinBg", false));
    texturesMap.put(
        "textButtonBg", new Image(GConnector.getInput("field/textBg.png"), "textButtonBg", false));
    texturesMap.put(
        "uiCurvedBg", new Image(GConnector.getInput("field/cBgS.png"), "uiCurvedBg", false));
    texturesMap.put(
        "buttonS", new Image(GConnector.getInput("button/buttonS.png"), "buttonS", false));
    texturesMap.put(
        "buttonUp", new Image(GConnector.getInput("button/buttonUp.png"), "buttonUp", false));
    texturesMap.put(
        "buttonNext", new Image(GConnector.getInput("button/buttonNext.png"), "buttonNext", false));
    texturesMap.put(
        "buttonDown", new Image(GConnector.getInput("button/buttonDown.png"), "buttonDown", false));
    texturesMap.put(
        "buttonBack", new Image(GConnector.getInput("button/buttonBack.png"), "buttonBack", false));
    texturesMap.put(
        "areaExit", new Sprite(GConnector.getInput("object/exit.png"), "areaExit", false));
    texturesMap.put(
        "errorIcon", new Image(GConnector.getInput("icon/unknown.png"), "errorIcon", false));
    texturesMap.put(
        "defIcon", new Image(GConnector.getInput("icon/unknown.png"), "errorIcon", false));

    Font simsun =
        Font.createFont(
            Font.TRUETYPE_FONT,
            new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf"));
    fontsMap.put("mainUiFont", simsun);
  }
}
