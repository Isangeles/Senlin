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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.util.GConnector;

/**
 * Base for graphical textures of UI elements
 * Contains frequently used textures, used by some GUI elements to reduce number of accesses to gData archive
 * @author Isangeles
 *
 */
public final class GBase 
{
	private static Map<String, Image> texturesMap = new HashMap<>();
	
	private GBase() {}
	
	public static Image get(String name)
	{
		return texturesMap.get(name);
	}
	
	public static void load() throws IOException, SlickException
	{
		texturesMap.put("uiSlotA", new Image(GConnector.getInput("ui/slot.png"), "uiSlotA", false));
		texturesMap.put("uiSlotB", new Image(GConnector.getInput("ui/slotB.png"), "uiSlotB", false));
		texturesMap.put("infoWinBg", new Image(GConnector.getInput("field/infoWindowBG.png"), "infoWinBg", false));
		texturesMap.put("textButtonBg", new Image(GConnector.getInput("field/textBg.png"), "textButtonBg", false));
	}

}
