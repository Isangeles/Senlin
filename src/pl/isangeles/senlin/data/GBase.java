/*
 * GBase.java
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
import pl.isangeles.senlin.util.TConnector;

/**
 * Base for graphical textures of UI elements
 * Contains frequently used textures, used by some GUI elements to reduce number of accesses to gData archive
 * @author Isangeles
 *
 */
public final class GBase 
{
	private static Map<String, Image> texturesMap = new HashMap<>();
	private static Map<String, Font> fontsMap = new HashMap<>();
	
	private GBase() {}
	
	public static Image getImage(String name)
	{
		return texturesMap.get(name);
	}
	
	public static Font getFont(String name)
	{
		return fontsMap.get(name);
	}
	
	public static void load() throws IOException, SlickException, FontFormatException
	{
		texturesMap.put("uiSlotA", new Image(GConnector.getInput("ui/slot.png"), "uiSlotA", false));
		texturesMap.put("uiSlotB", new Image(GConnector.getInput("ui/slotB.png"), "uiSlotB", false));
		texturesMap.put("infoWinBg", new Image(GConnector.getInput("field/infoWindowBG.png"), "infoWinBg", false));
		texturesMap.put("textButtonBg", new Image(GConnector.getInput("field/textBg.png"), "textButtonBg", false));
		texturesMap.put("uiCurvedBg", new Image(GConnector.getInput("field/cBgS.png"), "uiCurvedBg", false));
		texturesMap.put("buttonS", new Image(GConnector.getInput("button/buttonS.png"), "buttonS", false));
        texturesMap.put("areaExit", new Sprite(GConnector.getInput("object/exit.png"), "areaExit", false));
		
		Font simsun = Font.createFont(Font.TRUETYPE_FONT, new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf"));
		fontsMap.put("mainUiFont", simsun);
	}

}
