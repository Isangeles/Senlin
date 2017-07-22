/*
 * ItemsRequirement.java
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
package pl.isangeles.senlin.core.req;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.data.SaveElement;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for items requirements
 * @author Isangeles
 *
 */
public class ItemsRequirement extends Requirement
{
	private Map<String, Integer> reqItems;
	private List<Item> itemsToRemove = new ArrayList<>();
	private boolean meet;
	/**
	 * Items requirement constructor
	 * @param reqItems Map with required items IDs as keys and amount of thats items as values
	 */
	public ItemsRequirement(Map<String, Integer> reqItems)
	{
		this.reqItems = reqItems;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.req.Requirement#isMeet(pl.isangeles.senlin.core.Character)
	 */
	@Override
	public boolean isMetBy(Character character) 
	{
		Map<String, Integer> countMap = new HashMap<>();
    	for(String reqItId : reqItems.keySet())
    	{
    		countMap.put(reqItId, 0);
    	}
    	
    	for(Item item : character.getInventory())
    	{
    		if(countMap.containsKey(item.getId()))
    		{
    			int amount = countMap.get(item.getId());
    			countMap.put(item.getId(), amount + 1);
    			itemsToRemove.add(item);
    		}
    	}
    	
    	if(countMap.equals(reqItems))
    	{
    		meet = true;
    		character.getInventory().removeAll(itemsToRemove);
    		return true;
    	}
    	else
    	{
    		return false;
    	}
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.req.Requirement#charge(pl.isangeles.senlin.core.Character)
	 */
	@Override
	public void charge(Character character) 
	{
		if(meet)
		{
			character.getInventory().removeAll(itemsToRemove);
			itemsToRemove = null;
			meet = false;
		}
	}
	
	public String getInfo()
	{
		String reqInfo = "";
		
		for(String itemId : reqItems.keySet())
		{
			String itemName = TConnector.getInfo("items", itemId)[0];
			String amount = "" + reqItems.get(itemId);
			reqInfo = TConnector.getText("ui", "reqName") + ": " + itemName + " " + amount;
		}
		
		return reqInfo;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.data.SaveElement#getSave(org.w3c.dom.Document)
	 */
	@Override
	public Element getSave(Document doc) 
	{
		// TODO Auto-generated method stub
		Element itemReq = doc.createElement("itemReq");
		return itemReq;
	}

}