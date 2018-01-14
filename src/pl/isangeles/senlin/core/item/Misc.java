/*
 * Misc.java
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

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.action.Action;
import pl.isangeles.senlin.core.action.ActionType;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.data.GBase;
import pl.isangeles.senlin.gui.tools.ItemTile;
import pl.isangeles.senlin.util.GConnector;
/**
 * Class for miscellaneous items
 * @author Isangeles
 *
 */
public class Misc extends Item 
{
	private boolean disposable;
	private boolean currency;
	/**
	 * Miscellaneous item constructor
	 * @param id Item ID
	 * @param value Item value
	 * @param disposable If item disappears after use
	 * @param imgName Item image name, for icon
	 * @param onUse Action on use(ppm click in inventory)
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Misc(String id, int value, int maxStack, boolean disposable, boolean currency, String imgName, Action onUse, GameContainer gc) 
			throws SlickException, IOException, FontFormatException 
	{
		super(id, value, maxStack, imgName, gc);
		this.disposable = disposable;
		this.currency = currency;
		this.onUse = onUse;
		this.itemTile = buildIcon(gc);
	}
	/**
	 * Miscellaneous item constructor (with saved serial number)
	 * @param id Item ID
     * @param serial Saved serial number
	 * @param value Item value
	 * @param disposable If item disappears after use
	 * @param imgName Item image name, for icon
	 * @param onUse Action on use(ppm click in inventory)
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Misc(String id, long serial, int value, int maxStack, boolean disposable, boolean currency, String imgName, Action onUse, GameContainer gc) 
			throws SlickException, IOException, FontFormatException 
	{
		super(id, serial, value, maxStack, imgName, gc);
		this.disposable = disposable;
		this.currency = currency;
		this.onUse = onUse;
		this.itemTile = buildIcon(gc);
	}
    /* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Usable#use(pl.isangeles.senlin.core.Targetable, pl.isangeles.senlin.core.Targetable)
	 */
    @Override
    public boolean use(Targetable user, Targetable target)
    {
    	boolean out = onUse.start(user, target);
    	if(out && disposable)
    		user.getInventory().remove(this);
    	return out;
    }
    /**
     * Checks if this items can be treated as currency
     * @return True if this items is currency, false otherwise
     */
    public boolean isCurrency()
    {
    	return currency;
    }
    /**
     * Returns basic info about item for item tile
     * @return String with basic info
     */
    protected String getInfo()
    {
    	String fullInfo = name + System.lineSeparator() + info + System.lineSeparator() + "Value: " + value;
    	return fullInfo;
    }
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.item.Item#setTile(org.newdawn.slick.GameContainer)
	 */
	@Override
	protected ItemTile buildIcon(GameContainer gc) throws SlickException, IOException, FontFormatException 
    {
		try 
		{
			if(!icons.containsKey(id))
			{
				Image iconImg = new Image(GConnector.getInput("icon/item/misc/"+imgName), id, false);
				icons.put(id, iconImg);
				return new ItemTile(iconImg, gc, this.getInfo());
			}
			else
			{
				Image iconImg = icons.get(id);
				return new ItemTile(iconImg, gc, this.getInfo());
			}
		}
		catch(SlickException | IOException e) 
    	{
			return new ItemTile(GBase.getImage("errorIcon"), gc, this.getInfo());
		}
    }
}
