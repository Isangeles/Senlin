/*
 * ItemParser.java
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

import pl.isangeles.senlin.core.Bonuses;
import pl.isangeles.senlin.data.pattern.ArmorPattern;
import pl.isangeles.senlin.data.pattern.WeaponPattern;

/**
 * Class for XML items bases parsing
 * @author Isangeles
 *
 */
public class ItemParser 
{
	/**
	 * Private constructor to prevent initialization
	 */
	private ItemParser(){}
	/**
	 * Parses item node from weapons base
	 * @param itemNode Item node from XML weapons base
	 * @return New weapon pattern
	 * @throws NumberFormatException
	 */
	public static WeaponPattern getWeaponFromNode(Node itemNode) throws NumberFormatException
	{
		Element itemE = (Element)itemNode;
		
		String id = itemE.getAttribute("id");
		int reqLvl = Integer.parseInt(itemE.getAttribute("reqLvl"));
		String type = itemE.getAttribute("type");
		String material = itemE.getAttribute("material"); 
		int value = Integer.parseInt(itemE.getAttribute("value"));
		String dmg = itemE.getAttribute("dmg");
		int minDmg = Integer.parseInt(dmg.split("-")[0]);
		int maxDmg = Integer.parseInt(dmg.split("-")[1]);
		Bonuses bonuses = new Bonuses(itemE.getElementsByTagName("bonuses").item(0).getTextContent());
		String icon = itemE.getElementsByTagName("icon").item(0).getTextContent();
		String spriteSheet = itemE.getElementsByTagName("spriteSheet").item(0).getTextContent();
		
		return new WeaponPattern(id, reqLvl, type, material, value, minDmg, maxDmg, bonuses, icon, spriteSheet);
	}
	/**
	 * Parses item node from armors base
	 * @param itemNode Item node from XML armors base
	 * @return New armor pattern
	 * @throws NumberFormatException
	 */
	public static ArmorPattern getArmorFromNode(Node itemNode) throws NumberFormatException
	{
		Element itemE = (Element)itemNode;
		
		String id = itemE.getAttribute("id");
		int reqLvl = Integer.parseInt(itemE.getAttribute("reqLvl"));
		String type = itemE.getAttribute("type");
		String material = itemE.getAttribute("material"); 
		int value = Integer.parseInt(itemE.getAttribute("value"));
		int armRat = Integer.parseInt(itemE.getAttribute("armRat"));
		Bonuses bonuses = new Bonuses(itemE.getElementsByTagName("bonuses").item(0).getTextContent());
		String icon = itemE.getElementsByTagName("icon").item(0).getTextContent();
		
		return new ArmorPattern(id, reqLvl, type, material, value, armRat, bonuses, icon);
	}
}