package pl.isangeles.senlin.core;

import java.util.LinkedList;

import pl.isangeles.senlin.core.item.Armor;
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
     * Sets item as character main weapon
     * @param weapon Item that can be casted to weapon
     * @return True if item is valid (can be casted to weapon), false otherwise
     */
    public boolean setMainWeapon(Item weapon)
    {
    	try
    	{
    		return equipment.setMainWeapon((Weapon) weapon);
    	}
    	catch(ClassCastException e)
    	{
    		return false;
    	}
    }
    /**
     * Sets item as character secondary weapon
     * @param weapon Item that can be casted to weapon
     * @return True if item is valid (can be casted to weapon), false otherwise
     */
    public boolean setSecWeapon(Item weapon)
    {
    	try
    	{
    		return equipment.setSecWeapon((Weapon) weapon);
    	}
    	catch(ClassCastException e)
    	{
    		return false;
    	}
    }
    /**
     * Sets item as character boots
     * @param weapon Item that can be casted to weapon
     * @return True if item is valid (can be casted to armor), false otherwise
     */
    public boolean setBoots(Item boots)
    {
    	try
    	{
    		return equipment.setBoots((Armor) boots);
    	}
    	catch(ClassCastException e)
    	{
    		return false;
    	}
    }
    /**
     * Sets item as character shield
     * @param weapon Item that can be casted to weapon
     * @return True if item is valid (can be casted to armor), false otherwise
     */
    public boolean setShield(Item shield)
    {
    	try
    	{
    		return equipment.setShield((Armor) shield);
    	}
    	catch(ClassCastException e)
    	{
    		return false;
    	}
    }
    /**
     * Sets item as character shield
     * @param weapon Item that can be casted to weapon
     * @return True if item is valid (can be casted to armor), false otherwise
     */
    public boolean setGloves(Item gloves)
    {
    	try
    	{
    		return equipment.setGloves((Armor) gloves);
    	}
    	catch(ClassCastException e)
    	{
    		return false;
    	}
    }
    /**
     * Sets item as character chest
     * @param weapon Item that can be casted to weapon
     * @return True if item is valid (can be casted to armor), false otherwise
     */
    public boolean setChest(Item chest)
    {
    	try
    	{
    		return equipment.setChest((Armor) chest);
    	}
    	catch(ClassCastException e)
    	{
    		return false;
    	}
    }
    /**
     * Sets item as character helmet
     * @param weapon Item that can be casted to weapon
     * @return True if item is valid (can be casted to armor), false otherwise
     */
    public boolean setHelmet(Item helmet)
    {
    	try
    	{
    		return equipment.setHelmet((Armor) helmet);
    	}
    	catch(ClassCastException e)
    	{
    		return false;
    	}
    }
    /**
     * Sets item as character ring
     * @param weapon Item that can be casted to weapon
     * @return True if item is valid (can be casted to trinket), false otherwise
     */
    public boolean setRing(Item ring)
    {
    	try
    	{
    		return equipment.setRing((Trinket) ring);
    	}
    	catch(ClassCastException e)
    	{
    		return false;
    	}
    }
    /**
     * Sets item as character secondary ring
     * @param weapon Item that can be casted to weapon
     * @return True if item is valid (can be casted to trinket), false otherwise
     */
    public boolean setSecRing(Item ring)
    {
    	try
    	{
    		return equipment.setSecRing((Trinket) ring);
    	}
    	catch(ClassCastException e)
    	{
    		return false;
    	}
    }
    /**
     * Sets item as character amulet
     * @param weapon Item that can be casted to weapon
     * @return True if item is valid (can be casted to trinket), false otherwise
     */
    public boolean setAmulet(Item amulet)
    {
    	try
    	{
    		return equipment.setAmulet((Trinket) amulet);
    	}
    	catch(ClassCastException e)
    	{
    		return false;
    	}
    }

    /**
     * Sets item as character artifact
     * @param weapon Item that can be casted to weapon
     * @return True if item is valid (can be casted to trinket), false otherwise
     */
    public boolean setArtifact(Item artifact)
    {
    	try
    	{
    		return equipment.setArtifact((Trinket) artifact);
    	}
    	catch(ClassCastException e)
    	{
    		return false;
    	}
    }
    
    public void removeMainWeapon()
    {
    	equipment.removeMainWeapon();
    }
    
    public int[] getWeaponDamage()
    {
    	return equipment.getDamage();
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
