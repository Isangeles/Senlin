/*
 * ItemsBase.java
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
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.xml.sax.SAXException;

import pl.isangeles.senlin.core.item.ErrorItem;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.item.Weapon;
import pl.isangeles.senlin.data.pattern.ArmorPattern;
import pl.isangeles.senlin.data.pattern.MiscPattern;
import pl.isangeles.senlin.data.pattern.TrinketPattern;
import pl.isangeles.senlin.data.pattern.WeaponPattern;
import pl.isangeles.senlin.util.DConnector;
/**
 * Class with containers with all items in game
 * @author Isangeles
 *
 */
public class ItemsBase 
{
	//public static List<Weapon> weapons; //UNUSED
	public static Map<String, WeaponPattern> weaponsMap;
	public static Map<String, ArmorPattern> armorsMap;
	public static Map<String, TrinketPattern> trinketsMap;
	public static Map<String, MiscPattern> miscMap;
	private static GameContainer gc;
	/**
	 * Private constructor to prevent initialization
	 */
	private ItemsBase(){}
	/**
	 * Returns new copy of item with specific id from base
	 * @param id Item id
	 * @return Copy of item from base (by clone method) or null if item was not found 
	 */
	public static Item getItem(String id)
	{
		/* TEST CODE
	 	for(Item item : weapons)
		{
			if(item.getId() == id)
				return item;
		}
		 */
		if(id == "")
			return null;
		
		try
		{
			if(weaponsMap.get(id) != null)
				return weaponsMap.get(id).make(gc);
			
			if(armorsMap.get(id) != null)
				return armorsMap.get(id).make(gc);
			
			if(trinketsMap.get(id) != null)
				return null; //TODO write trinket builder
			
			if(miscMap.get(id) != null)
				return miscMap.get(id).make(gc);
			
			return new ErrorItem(id, gc);
		}
		catch(SlickException | IOException | FontFormatException e)
		{
			System.err.println(e.getMessage());
			return null;
		}
		
	}
	/**
	 * Return error item
	 * @param itemId Id of item that cause error
	 * @return errorItem
	 */
	public static Item getErrorItem(String itemId)
	{
		try 
		{
			return new ErrorItem(itemId, gc);
		} 
		catch (SlickException | IOException | FontFormatException e) 
		{
			System.err.println(e.getMessage());
			return null;
		}
	}
	/**
	 * Loads text files with items to game maps
	 * @param gc Slick game container for getItem method
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public static void loadBases(GameContainer gc) throws SlickException, IOException, FontFormatException, ParserConfigurationException, SAXException
	{
		ItemsBase.gc = gc;
		weaponsMap = DConnector.getWeapons("weaponsBase");
		armorsMap = DConnector.getArmors("armorBase");
		trinketsMap = DConnector.getTrinkets("trinketsBase");
		miscMap = DConnector.getMisc("miscBase");
	}
}
