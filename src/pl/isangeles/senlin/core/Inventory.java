/*
 * Inventory.java
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
package pl.isangeles.senlin.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.core.item.Armor;
import pl.isangeles.senlin.core.item.Equippable;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.item.Trinket;
import pl.isangeles.senlin.core.item.Weapon;
import pl.isangeles.senlin.data.ItemsBase;
import pl.isangeles.senlin.data.SaveElement;
import pl.isangeles.senlin.gui.SlotContent;
/**
 * Class for character inventory, contains all player items
 * @author Isangeles
 *
 */
public final class Inventory extends LinkedList<Item> implements SaveElement
{
	private static final long serialVersionUID = 1L;
	private Equipment equipment;
	private int gold;
	private boolean mod;
	
	public Inventory()
	{
		equipment = new Equipment();
	}
    /**
     * Adds item to inventory
     * @return True if item successfully added, false otherwise 
     */
    public boolean add(Item item)
    {
        if(item != null)
        {
            super.add(item);
            mod = true;
            return true;
        }
        else 
            return false;
    }
	
    public boolean remove(Item item)
    {
    	if(super.remove(item))
    	{
    		mod = true;
    		return true;
    	}
    	else
    		return false;
    }
    /**
     * Adds gold to inventory
     * @param value Integer value to add
     */
    public void addGold(int value)
    {
    	gold += value;
    }
    /**
     * Marks inventory as updated, used by GUI
     */
    public void updated()
    {
    	mod = false;
    }
    /**
     * Returns all gold in inventory
     * @return Amount of gold in integer
     */
    public int getGold()
    {
        return gold;
    }
	/**
	 * Sets item as one of character weapon, item must be in inventory
	 * @param weapon Any item that can be casted to weapon
	 * @return True if item was successful inserted, false otherwise
	 */
    public boolean setWeapon(Item weapon)
    {
    	if(this.contains(weapon))
    	{
    		try
        	{
        		return equipment.setWeapon((Weapon) weapon);
        	}
        	catch(ClassCastException e)
        	{
        		return false;
        	}
    	}
    	else
    		return false;
    }
    /**
     * Sets item as one of character armor parts, item must be in inventory
     * @param armorPart Item that can be casted to armor
     * @return True if item was successful inserted, false otherwise
     */
    public boolean setArmor(Item armorPart)
    {
    	if(this.contains(armorPart))
    	{
    		try
        	{
        		return equipment.setArmor((Armor) armorPart);
        	}
        	catch(ClassCastException e)
        	{
        		return false;
        	}
    	}
    	else
    		return false;
    }
    /**
     * Sets item as one of character trinkets, item must be in inventory
     * @param trinket Item that can be casted to trinket
     * @return True if item was successful inserted, false otherwise
     */
    public boolean setTrinket(Item trinket)
    {
    	if(this.contains(trinket))
    	{
    		try
        	{
        		return equipment.setTrinket((Trinket) trinket);
        	}
        	catch(ClassCastException e)
        	{
        		return false;
        	}
    	}
    	else
    		return false;
    }
    /**
	 * Removes specific item from equippment
	 * @param item Equipped character item
	 */
    public void unequipp(Item item)
    {
    	equipment.unequipp(item);
    }
    /**
     * Returns weapon damage of equipment items
     * @return Table with minimal[0] and maximal[1] damge
     */
    public int[] getWeaponDamage()
    {
    	return equipment.getDamage();
    }
    /**
     * Returns armor rating of equipped items
     * @return Value of armor rating
     */
    public int getArmorRating()
    {
    	return equipment.getArmorRat();
    }
    /**
	 * Return character helmet
	 * @return Equipped armor item, type helmet OR null if not equipped
	 */
    public Armor getHelmet()
	{
		return equipment.getHelmet();
	}
    /**
	 * Return character chest
	 * @return Equipped armor item, type chest OR null if not equipped
	 */
	public Armor getChest()
	{
		return equipment.getChest();
	}
	/**
	 * Return character main weapon
	 * @return Equipped weapon item, any type OR null if not equipped
	 */
    public Weapon getMainWeapon()
    {
    	return equipment.getMainWeapon();
    }
    /**
     * Returns item which matches to specific id 
     * @param itemId Id of requested item
     * @return Item with specific id or error item if item was not fund
     */
    public Item getItem(String itemId)
    {
    	for(int i = 0; i < super.size(); i ++)
    	{
    		if(super.get(i).getId() == itemId)
    			return super.get(i);
    	}
    	return ItemsBase.getErrorItem(itemId);
    }
    /**
     * Returns item with specific index in inventory container
     * @param index Index in inventory container
     * @return Item from inventory container
     */
    public Item getItem(int index)
    {
    	return super.get(index);
    }
    /**
     * Returns array with all inventory items
     * @return Array with items
     */
    public Item[] getItems()
    {
        return this.toArray(new Item[(this.size())]);
    }
    /**
     * Removes specified item from inventory and returns it
     * @param item Item in inventory to remove
     * @return Removed item or null if inventory does not contain that item
     */
    public Item takeItem(Item item)
    {
    	if(this.remove(item))
    		return item;
    	else
    		return null;
    }
    /**
     * Removes item with specified ID from inventory and returns it
     * @param item Item in inventory to remove
     * @return Removed item or null if inventory does not contain item with such ID
     */
    public Item takeItem(String itemId)
    {
    	Item itemToTake = null;
    	for(Item item : this)
    	{
    		if(item.getId().equals(itemId))
    		{
    			itemToTake = item;
    			break;
    		}
    	}
    	this.remove(itemToTake);
    	return itemToTake;
    }
    /**
     * Removes specified amount of gold from inventory
     * @param value Amount of gold to remove
     * @return Removed value or 0 if value is to big to remove
     */
    public int takeGold(int value)
    {
    	gold -= value;
    	if(gold >= 0)
    		return value;
    	else
    		return 0;
    }
    
    public List<Item> getWithoutEq()
    {
    	List<Item> invWithoutEq = new ArrayList<>();
    	invWithoutEq.addAll(this);
    	invWithoutEq.removeAll(equipment.getAll());
    	return invWithoutEq;
    }
    
    public boolean isMod()
    {
    	return mod;
    }
    /**
     * Returns all inventory as list with slot content
     * @return List with slot content
     */
    public List<SlotContent> asSlotsContent()
    {
    	List<SlotContent> slotsContent = new ArrayList<>();
    	slotsContent.addAll(this);
    	return slotsContent;
    }
    /**
     * UNSED Now slots draws items
     * @param x
     * @param y
     */
    @Deprecated 
    public void drawItems(float x, float y)
    {
    	for(int i = 0; i < super.size(); i ++)
    	{
    		super.get(i).getTile().draw(x, y);
    	}
    }
    /**
     * Parses inventory to XML document element
     * @param doc Document for save game file
     * @return XML document element
     */
    public Element getSave(Document doc)
    {	
    	Element eq = equipment.getSave(doc);
    	Element in = doc.createElement("in");
    	in.setAttribute("gold", gold+"");
    	for(Item item : this)
    	{
    		if(!equipment.isEquipped(item))
    		{
    			Element itemE = doc.createElement("item");
        		itemE.setTextContent(item.getId());
        		in.appendChild(itemE);
    		}
    	}
    	eq.appendChild(in);
		
		return eq;
    }
    /**
     * Parses inventory without equipment to XML document element
     * @param doc Document for save game file
     * @return XML document element
     */
    public Element getSaveWithoutEq(Document doc)
    {
    	Element eq = doc.createElement("eq");
    	Element in = doc.createElement("in");
    	in.setAttribute("gold", gold+"");
    	for(Item item : this)
    	{
    		Element itemE = doc.createElement("item");
    		itemE.setTextContent(item.getId());
    		in.appendChild(itemE);
    	}
    	eq.appendChild(in);
		
		return eq;
    }
}
