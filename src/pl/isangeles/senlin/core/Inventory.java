package pl.isangeles.senlin.core;

import java.util.LinkedList;

import pl.isangeles.senlin.core.item.Armor;
import pl.isangeles.senlin.core.item.Equippable;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.item.Trinket;
import pl.isangeles.senlin.core.item.Weapon;
import pl.isangeles.senlin.data.ItemBase;
/**
 * Class for character inventory, contains all player items
 * @author Isangeles
 *
 */
public final class Inventory extends LinkedList<Item>
{
	private static final long serialVersionUID = 1L;
	private Equipment equipment;
	private int gold;
	
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
            return true;
        }
        else 
            return false;
    }

	/**
	 * Sets item as one of character weapon
	 * @param weapon Any item that can be casted to weapon
	 * @return True if item was successful inserted, false otherwise
	 */
    public boolean setWeapon(Item weapon)
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
    /**
     * Sets item as one of character armor parts
     * @param armorPart Item that can be casted to armor
     * @return True if item was successful inserted, false otherwise
     */
    public boolean setArmor(Item armorPart)
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
    /**
     * Sets item as one of character trinkets
     * @param trinket Item that can be casted to trinket
     * @return True if item was successful inserted, false otherwise
     */
    public boolean setTrinket(Item trinket)
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
    	return ItemBase.getErrorItem(itemId);
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
     * Now slots draws items
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
    
}
