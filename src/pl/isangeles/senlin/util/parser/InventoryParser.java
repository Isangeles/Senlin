/*
 * InventoryParser.java
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
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.InventoryLock;
import pl.isangeles.senlin.data.pattern.RandomItem;

/**
 * Static class for parsing inventory nodes
 * @author Isangeles
 *
 */
public final class InventoryParser 
{
	/**
	 * Private constructor to prevent initialization
	 */ 
	private InventoryParser() {}
	/**
	 * Parses specified inventory node to list items IDs
	 * @param inNode XML inventory node
	 * @return List with random items patterns (NOTE that if specified node is null then returns empty list) 
	 */
	public static List<RandomItem> getItemsFromNode(Node itemsNode)
	{
		List<RandomItem> items = new ArrayList<>();
		if(itemsNode == null)
			return items;
		Element itemsE = (Element)itemsNode;
		NodeList itemsNl = itemsE.getElementsByTagName("item");
		for(int j = 0; j < itemsNl.getLength(); j ++)
        {
            try
            {
            	Element itemE = (Element)itemsE.getElementsByTagName("item").item(j);
                int amount = 1;
                if(itemE.hasAttribute("amount"))
                	amount = Integer.parseInt(itemE.getAttribute("amount"));
                boolean ifRandom = Boolean.parseBoolean(itemE.getAttribute("random"));
                String itemInId = itemE.getTextContent();
                String itemSerialS  = itemE.getAttribute("serial");
                if(itemSerialS.equals(""))
                {
                	for(int i = 0;i < amount; i ++)
                    {
                    	RandomItem ip = new RandomItem(itemInId, ifRandom);
                        items.add(ip);
                    }
                }
                else
                {
                    long itemSerial = Long.parseLong(itemSerialS);
                    RandomItem ip = new RandomItem(itemInId, itemSerial, ifRandom);
                    items.add(ip);
                }
            }
            catch(NumberFormatException e)
            {
            	Log.addSystem("inventory_parser_fail-msg//item node corrupted");
            	continue;
            }
        }
		
		return items;
	}
	/**
	 * Parses specified inventory node to gold value
	 * DEPRECATED gold id now represented by in-game items
	 * @param inNode XML inventory node
	 * @return Gold value from specified inventory node
	 */
	@Deprecated
	private static int getGoldFromNode(Node inNode)
	{
		try
		{
			Element inE = (Element)inNode;
			int gold = Integer.parseInt(inE.getAttribute("gold"));
			return gold;
		}
		catch(NumberFormatException | ClassCastException e)
		{
			Log.addSystem("inventory_parser_fail-msg///gold value corrupted");
			return 0;
		}
	}
	/**
	 * Parses specified lock node to inventory lock
	 * @param lockNode XML lock node
	 * @return Inventory lock
	 */
	public static InventoryLock getLockFromNode(Node lockNode)
	{
		try
		{
			Element lockE = (Element)lockNode;
			int level = Integer.parseInt(lockE.getAttribute("level"));
			String key = lockE.getAttribute("key");
			return new InventoryLock(key, level);
		}
		catch(NumberFormatException | ClassCastException e)
		{
			Log.addSystem("inventory_parser_fail-msg///lock node corrupted");
			return new InventoryLock();
		}
	}
}
