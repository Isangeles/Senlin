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

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.data.pattern.ObjectPattern;
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

	public static ObjectPattern getObjectFormNode(Node objectNode) throws NumberFormatException
	{
		Element objectE = (Element)objectNode;
		String id = objectE.getAttribute("id");
		String mainTex = objectE.getAttribute("mainTex");
		String type = objectE.getAttribute("type");
		int frames = Integer.parseInt(objectE.getAttribute("frames"));
		int fWidth = Integer.parseInt(objectE.getAttribute("fWidth"));
		int fHeight = Integer.parseInt(objectE.getAttribute("fHeight"));
		
		return new ObjectPattern(id, mainTex, type, frames, fWidth, fHeight);
	}
}
