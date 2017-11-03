/*
 * ObjectParser.java
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
package pl.isangeles.senlin.util.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.InventoryLock;
import pl.isangeles.senlin.data.pattern.ObjectPattern;
import pl.isangeles.senlin.data.pattern.RandomItem;
import pl.isangeles.senlin.graphic.GameObject;

/**
 * Static class for objects XML base parsing
 * @author Isangeles
 *
 */
public class ObjectParser 
{
	/**
	 * Private constructor to prevent initialization
	 */
	private ObjectParser() {}
	/**
	 * Parses object node from XML base to game objects pattern
	 * @param objectNode Node from XML base
	 * @return New object pattern
	 * @throws NumberFormatException
	 */
	public static ObjectPattern getObjectFormNode(Node objectNode) throws NumberFormatException
	{
		Element objectE = (Element)objectNode;
		String id = objectE.getAttribute("id");
		String mainTex = objectE.getAttribute("mainTex");
		String type = objectE.getAttribute("type");
		String action = objectE.getAttribute("action");
		int frames = 0;
		int fWidth = 0;
		int fHeight = 0;
		if(type.equals("anim"))
		{
			frames = Integer.parseInt(objectE.getAttribute("frames"));
			fWidth = Integer.parseInt(objectE.getAttribute("fWidth"));
			fHeight = Integer.parseInt(objectE.getAttribute("fHeight"));
		}
		
		String portrait = "default.png";
		Element portraitE = (Element)objectE.getElementsByTagName("portrait").item(0);
		if(portraitE != null)
		    portrait = portraitE.getTextContent();
		
		String sound = "";
		Element soundE = (Element)objectE.getElementsByTagName("sound").item(0);
		if(soundE != null)
			sound = soundE.getTextContent();
		
		List<RandomItem> items = new ArrayList<>(); 
		Node inNode = objectE.getElementsByTagName("in").item(0);
		Element inE = (Element)inNode;
        int gold = 0;
        InventoryLock lock = new InventoryLock();
		if(inE != null)
		{
		    Node itemsNode = (Element)inE.getElementsByTagName("items").item(0);
		    items = InventoryParser.getItemsFromNode(itemsNode);
		    gold = InventoryParser.getGoldFromNode(inNode);
		    Node lockNode = (Element)inE.getElementsByTagName("lock").item(0);
		    if(lockNode != null)
			    lock = InventoryParser.getLockFromNode(lockNode);
		}
		
		return new ObjectPattern(id, mainTex, portrait, sound, type, frames, fWidth, fHeight, action, gold, items, lock);
	}
}
