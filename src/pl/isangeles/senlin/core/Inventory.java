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
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.bonus.DualwieldBonus;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.item.Armor;
import pl.isangeles.senlin.core.item.Equippable;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.item.Weapon;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.gui.SlotContent;
/**
 * Class for character inventory, contains all player items
 * @author Isangeles
 *
 */
public final class Inventory extends LinkedList<Item> implements SaveElement
{
	private static final long serialVersionUID = 1L;
	private Targetable owner;
	private Equipment equipment;
	private int gold;
	/**
	 * Inventory constructor 
	 * @param character Inventory owner
	 */
	public Inventory(Targetable character)
	{
		equipment = new Equipment();
		owner = character;
	}
	/**
	 * Updates inventory
	 * (for example bonuses from equipped items)
	 */
	public void update()
	{
		for(Equippable item : equipment.getAll())
		{
			owner.getEffects().addAllFrom(item);
		}
	}
    /**
     * Adds item to inventory
     * @return True if item successfully added, false otherwise 
     */
	@Override
    public boolean add(Item item)
    {
        if(item != null)
        {
            super.add(item);
        	item.setOwner(owner);
            if(Character.class.isInstance(owner))
            {
            	((Character)owner).getQTracker().check(item);
            }
            return true;
        }
        else 
            return false;
    }
	/**
	 * Adds all items from collection to inventory
	 * @param items Collection with items
	 * @return True if all items was added successfully, false if at least one wasn't added
	 */
	@Override
	public boolean addAll(Collection<? extends Item> items)
	{
    	boolean isOk = true;
		for(Item item : items)
		{
			if(!add(item))
				isOk = false;
		}
		
		return isOk; 
	}
	/**
	 * Removes specified item from inventory
	 * @param item Game item
	 * @return True if item was successfully removed, false otherwise
	 */
	@Override
    public boolean remove(Object item)
    {
    	if(super.remove(item))
    	{
    		if(Equippable.class.isInstance(item)) 
    		{
    			equipment.unequipp((Equippable)item);
    		}
    		return true;
    	}
    	else
    		return false;
    }
    /**
     * Removes items with specified serial ID from inventory
     * @param serialId Serial ID of item to remove
     * @return True if item was successfully removed, false otherwise
     */
    public boolean remove(String serialId)
    {
    	for(Item item : this)
    	{
    		if(item.getSerialId().equals(serialId))
    			return remove(item);
    	}
    	return false;
    }
    
    @Override
    public boolean removeAll(Collection<?> items)
    {
    	boolean ok = true;
    	for(Object item : items)
    	{
    		if(this.contains(item))
    		{
    			if(!this.remove(item))
    				ok = false;
    		}
    	}
    	return ok;
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
     * Returns all gold in inventory
     * @return Amount of gold in integer
     */
    public int getGold()
    {
        return gold;
    }
    /**
	 * Removes specific item from equippment
	 * @param item Equipped character item
	 */
    public void unequipp(Equippable item)
    {
    	equipment.unequipp(item);
    	owner.getEffects().removeAllFrom(item);
    }
    /**
     * Equips specified item, if item is in character inventory and its equippable
     * @param item Equippable item in character inventory
     * @return True if item was successfully equipped, false otherwise
     */
    public boolean equip(Item item)
    {
    	if(this.contains(item) && Equippable.class.isInstance(item))
    	{
    		return equipment.equip((Equippable)item);
    	}
    	else
    		return false;
    }
	/**
	 * Checks if specified item is equipped
	 * @param item Game item
	 * @return True if specified item is equipped, false otherwise
	 */
    public boolean isEquipped(Item item)
    {
    	if(Equippable.class.isInstance(item))
        	return equipment.isEquipped((Equippable)item);
    	else
    		return false;
    }
    /**
     * Returns weapon damage of equipment items
     * @return Table with minimal[0] and maximal[1] damage
     */
    public int[] getWeaponDamage()
    {
		int[] damage = {0, 0};
		if(equipment.getMainWeapon() != null)
		{
			Weapon mainWeapon = equipment.getMainWeapon();
			damage[0] += mainWeapon.getDamage()[0];
			damage[1] += mainWeapon.getDamage()[1];
		}
		if(equipment.getOffHand() != null)
		{
			Weapon secWeapon = equipment.getOffHand();
			damage[0] += secWeapon.getDamage()[0];
			damage[1] += secWeapon.getDamage()[1];
		}
		return damage;
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
	 * @return Equipped main weapon or null if no item equipped
	 */
    public Weapon getMainWeapon()
    {
    	return equipment.getMainWeapon();
    }
	/**
	 * Returns character off hand
	 * @return Equipped secondary weapon or null if no item equipped
	 */
    public Weapon getOffHand()
    {
    	return equipment.getOffHand();
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
    		if(super.get(i).getId().equals(itemId))
    			return super.get(i);
    	}
    	return null;//ItemsBase.getErrorItem(itemId);
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
     * Returns item with specified serial ID
     * @param serialId String with serial ID
     * @return Item with specified serial ID or null if item was not found
     */
    public Item getItemBySerial(String serialId)
    {
    	for(int i = 0; i < super.size(); i ++)
    	{
    		if(super.get(i).getSerialId().equals(serialId))
    			return super.get(i);
    	}
    	return null;
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
    
    public boolean isDualwield()
    {
    	return equipment.isDualwield();
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
    		Element itemE = doc.createElement("item");
			itemE.setAttribute("serial", item.getNumber()+"");
    		itemE.setTextContent(item.getId());
    		in.appendChild(itemE);
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
    		itemE.setAttribute("serial", item.getNumber()+"");
    		itemE.setTextContent(item.getId());
    		in.appendChild(itemE);
    	}
    	eq.appendChild(in);
		
		return eq;
    }
}
