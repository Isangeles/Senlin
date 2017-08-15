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

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import pl.isangeles.senlin.core.bonus.Bonus;
import pl.isangeles.senlin.core.bonus.Bonuses;
import pl.isangeles.senlin.data.pattern.ArmorPattern;
import pl.isangeles.senlin.data.pattern.MiscPattern;
import pl.isangeles.senlin.data.pattern.TrinketPattern;
import pl.isangeles.senlin.data.pattern.WeaponPattern;

/**
 * Class for XML items bases nodes parsing
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
		
		String icon = itemE.getElementsByTagName("icon").item(0).getTextContent();
		String spriteSheet = itemE.getElementsByTagName("spriteSheet").item(0).getTextContent();
		
		Node bonusesNode = itemE.getElementsByTagName("bonuses").item(0);
		List<Bonus> bonuses = BonusesParser.getBonusesFromNode(bonusesNode);
		
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
		
		String icon = itemE.getElementsByTagName("icon").item(0).getTextContent();
		String sprite = "";
		
		Node bonusesNode = itemE.getElementsByTagName("bonuses").item(0);
		List<Bonus> bonuses = BonusesParser.getBonusesFromNode(bonusesNode);
		
		Element spriteE = (Element)itemE.getElementsByTagName("sprite").item(0);
		if(spriteE != null)
		{
			sprite = spriteE.getTextContent();
		}
		
		return new ArmorPattern(id, reqLvl, type, material, value, armRat, bonuses, icon, sprite);
	}
	/**
	 * Parses item node from trinkets base
	 * @param itemNode Node from XML trinkets base (item node)
	 * @return New trinket pattern
	 */
	public static TrinketPattern getTrinketFromNode(Node itemNode) throws NumberFormatException
	{
		Element itemE = (Element)itemNode;
		
		String id = itemE.getAttribute("id");
		String type = itemE.getAttribute("type");
		int reqLvl = Integer.parseInt(itemE.getAttribute("reqLevel"));
		int value = Integer.parseInt(itemE.getAttribute("value"));
		String icon = itemE.getElementsByTagName("icon").item(0).getTextContent();
		
		Node bonusesNode = itemE.getElementsByTagName("bonuses").item(0);
		List<Bonus> bonuses = BonusesParser.getBonusesFromNode(bonusesNode);
		
		Element actionE = (Element)itemE.getElementsByTagName("action").item(0);
		String actionType = actionE.getAttribute("type");
		String actionId = actionE.getTextContent();
		
		return new TrinketPattern(id, type, reqLvl, value, icon, bonuses, actionType, actionId);
	}
	/**
	 * Parses item node from miscellaneous items base
	 * @param itemNode item node from XML miscellaneous items base 
	 * @return New miscellaneous items pattern
	 * @throws NumberFormatException
	 */
	public static MiscPattern getMiscFromNode(Node itemNode) throws NumberFormatException
	{
		Element itemE = (Element)itemNode;
		
		String id = itemE.getAttribute("id");
		int value = Integer.parseInt(itemE.getAttribute("value"));
		int stack = Integer.parseInt(itemE.getAttribute("stack"));
		boolean disposable = Boolean.parseBoolean(itemE.getAttribute("disposable"));
		String icon = itemE.getElementsByTagName("icon").item(0).getTextContent();
		
		Element action = (Element)itemE.getElementsByTagName("action").item(0);
		String actionType = action.getAttribute("type");
		String actionId = action.getTextContent();
		
		return new MiscPattern(id, value, stack, disposable, icon, actionType, actionId);
	}
}
