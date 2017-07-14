/*
 * RecipeParser.java
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

import pl.isangeles.senlin.core.craft.ProfessionLevel;
import pl.isangeles.senlin.core.craft.ProfessionType;
import pl.isangeles.senlin.core.craft.Recipe;
import pl.isangeles.senlin.core.req.ItemsRequirement;

/**
 * Class for parsing recipes bases elements
 * @author Isangeles
 *
 */
public final class RecipeParser 
{
	/**
	 * Private constructor to prevent initialization
	 */
	private RecipeParser() {}
	/**
	 * Parses specified recipe node from recipes base to new recipe object
	 * @param recipeNode XML document node (recipe node from recipes base)
	 * @return New recipe
	 * @throws NullPointerException
	 */
	public static Recipe getRecipeFromNode(Node recipeNode) throws NullPointerException
	{
		Element recipeE = (Element)recipeNode;
		String id = recipeE.getAttribute("id");
		ProfessionType type = ProfessionType.fromString(recipeE.getAttribute("type"));
		ProfessionLevel level = ProfessionLevel.fromString(recipeE.getAttribute("level"));
		ItemsRequirement reqItems = new ItemsRequirement(getComponents(recipeE.getElementsByTagName("components").item(0)));
		List<String> result = getResult(recipeE.getElementsByTagName("result").item(0));
		
		return new Recipe(id, type, level, reqItems, result);
	}
	/**
	 * Parses specified components node to map with items IDs as keys and amount of these items as values
	 * @param componentsNode XML document node (component node from recipes base)
	 * @return Map with items IDs as keys and amount of these items as values
	 * @throws NullPointerException
	 */
	private static Map<String, Integer> getComponents(Node componentsNode) throws NullPointerException
	{
		Map<String, Integer> componentsMap = new HashMap<>();
		
		Element componentsE = (Element)componentsNode;
		NodeList itemsList = componentsE.getElementsByTagName("item");
		for(int i = 0; i < itemsList.getLength(); i ++)
		{
			Node itemNode = itemsList.item(i);
			if(itemNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				Element itemE = (Element)itemNode;
				try
				{
					componentsMap.put(itemE.getTextContent(), Integer.parseInt(itemE.getAttribute("amount")));
				}
				catch(NumberFormatException e)
				{
					continue;
				}
			}
		}
		
		return componentsMap;
	}
	/**
	 * Parses specified result node to list with result items IDs
	 * @param resultNode XML document node (result node from recipes base)
	 * @return List with result items IDs
	 * @throws NullPointerException
	 */
	private static List<String> getResult(Node resultNode) throws NullPointerException
	{
		List<String> resultMap = new ArrayList<>();
		
		Element resultE = (Element)resultNode;
		NodeList itemsList = resultE.getElementsByTagName("item");
		for(int i = 0; i < itemsList.getLength(); i ++)
		{
			Node itemNode = itemsList.item(i);
			if(itemNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				Element itemE = (Element)itemNode;
				try
				{
					int amount = Integer.parseInt(itemE.getAttribute("amount"));
					for(int j = 0; j < amount; j ++)
					{
						resultMap.add(itemE.getTextContent());
					}
				}
				catch(NumberFormatException e)
				{
					continue;
				}
				
			}
		}
		
		return resultMap;
	}
}
