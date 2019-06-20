/*
 * Item.java
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
package pl.isangeles.senlin.core.item;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.Usable;
import pl.isangeles.senlin.core.action.Action;
import pl.isangeles.senlin.core.action.ActionType;
import pl.isangeles.senlin.core.action.EffectAction;
import pl.isangeles.senlin.core.effect.EffectSource;
import pl.isangeles.senlin.core.quest.ObjectiveTarget;
import pl.isangeles.senlin.gui.SlotContent;
import pl.isangeles.senlin.gui.tools.ItemTile;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;

/**
 * Base class for all items in the game
 * @author Isangeles
 *
 */
public abstract class Item implements SlotContent, Usable, ObjectiveTarget
{
	private static long itemCounter;
	private static List<Long> reservedIDs = new ArrayList<>();
	protected static Map<String, Image> icons = new HashMap<>();
	protected Targetable owner;
	protected long itemNumber = itemCounter++;
	protected String id;
	protected String serialId;
    protected String name;
    protected String info;
    protected String imgName;
    protected int value;
    protected int maxStack;
    protected ItemTile itemTile;
    protected Action onUse;
    /**
     * Basic item constructor
     * @param id Item ID, unique for all items
     * @param name Item name
     * @param info Info about item
     * @param value Item value
     * @param imgName Item image file name in icon dir
     * @param gc Game container for superclass
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    public Item(String id, int value, int maxStack, String imgName, GameContainer gc) throws SlickException, IOException, FontFormatException
    {
        this.id = id;
        this.name = TConnector.getInfoFromModule("items", id)[0];
        this.info = TConnector.getInfoFromModule("items", id)[1];
        this.value = value;
        this.maxStack = maxStack;
        this.imgName = imgName;
        this.onUse = new EffectAction();
        while(reservedIDs.contains(itemNumber))
        {
        	itemNumber ++;
        	itemCounter ++;
        }
        serialId = id + "_" + itemNumber;
    }

    /**
     * Basic item constructor (with saved serial number)
     * @param id Item ID, unique for all items
     * @param serial Saved serial number
     * @param name Item name
     * @param info Info about item
     * @param value Item value
     * @param imgName Item image file name in icon dir
     * @param gc Game container for superclass
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    public Item(String id, long serial, int value, int maxStack, String imgName, GameContainer gc) throws SlickException, IOException, FontFormatException
    {
    	this(id, value, maxStack, imgName, gc);
    	serialId = id + "_" + serial;
    	itemNumber = serial;
    	reservedIDs.add(itemNumber);
    	itemCounter --;
    }
    /**
     * Draws item tile
     * @param x Position on X axis
     * @param y Position on Y axis
     */
    public void draw(float x, float y, boolean scaledPos)
    {
    	itemTile.draw(x, y, scaledPos);
    }
    /**
     * Sets item owner 
     * @param owner Targetable object
     */
    public void setOwner(Targetable owner)
    {
    	this.owner = owner;
    }
    /**
     * Get item tile
     * @return ItemTile of specific item
     */
    public ItemTile getTile()
    {
    	return itemTile;
    }
    /**
     * Returns item ID
     * @return String with item ID
     */
    public String getId()
    {
    	return id;
    }
    /**
     * Returns serial ID of this item
     */
    public String getSerialId()
    {
    	return serialId;
    }
    /**
     * Returns number unique for every item in game
     * @return Item number
     */
    public long getNumber()
    {
    	return itemNumber;
    }
    /**
     * Returns item value
     * @return Item value
     */
    public int getValue()
    {
        return value;
    }
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.SlotContent#getMaxStack()
	 */
	@Override
	public int getMaxStack() 
	{
		return maxStack;
	}
    /**
     * Returns items name
     */
    @Override
    public String toString()
    {
    	return name;
    }
    /**
     * Compares items
     * @param item Item to check
     * @return True if unique item numbers are same, false otherwise
     */
    /*
    public boolean equals(Item item)
    {
    	try
    	{
    		if(serialId.equals(item.getSerialId()))
        		return true;
        	else
        		return false;
    	}
    	catch(NullPointerException e)
    	{
    		return false;
    	}
    }
    */
    public ActionType getActionType()
    {
    	return onUse.getType();
    }
    /**
     * Returns full info about item for item tile
     * @return String with basic info
     */
    protected abstract String getInfo();
    /**
     * Sets image from icon dir for item tile
     * @param gc Slick game container
     * @return ItemTile 
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    protected abstract ItemTile buildIcon(GameContainer gc) throws SlickException, IOException, FontFormatException;
    /**
     * Sets item icon
     * UNUSED
     * @param tile Item tile
     */
    @Deprecated
    public void setTile(ItemTile tile)
    {
    	this.itemTile = tile;
    }
    
}
