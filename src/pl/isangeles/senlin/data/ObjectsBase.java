/*
 * ObjectsBase.java
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

import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.SlickException;
import org.xml.sax.SAXException;

import pl.isangeles.senlin.data.pattern.ObjectPattern;
import pl.isangeles.senlin.graphic.GameObject;
import pl.isangeles.senlin.util.DConnector;
/**
 * Static class for game objects base
 * @author Isangeles
 *
 */
public class ObjectsBase 
{
	private static Map<String, ObjectPattern> objectsMap = new HashMap<>();
	/**
	 * Private constructor to prevent initialization
	 */
	private ObjectsBase(){}
	
	public static GameObject get(String objectId) throws SlickException, IOException
	{
		return objectsMap.get(objectId).make();
	}

	public static void load(String baseName) throws ParserConfigurationException, SAXException, IOException
	{
		objectsMap = DConnector.getObjects(baseName);
	}
}
