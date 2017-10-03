/*
 * Equippable.java
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
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.action.EquipAction;
import pl.isangeles.senlin.core.bonus.Bonuses;
import pl.isangeles.senlin.graphic.AnimObject;
/**
 * Class for equippable items
 * @author Isangeles
 *
 */
public abstract class Equippable extends Item 
{
	protected int reqLevel;
	protected final int type;
	protected final ItemMaterial material;
	protected Bonuses bonuses;
	protected AnimObject itemMSprite;
	protected AnimObject itemFSprite;
	
	protected abstract String getTypeName();
	
	public Equippable(String id, int value, String imgName, GameContainer gc, int reqLevel, Bonuses bonuses, int type, ItemMaterial material)
			throws SlickException, IOException, FontFormatException
	{
		super(id, value, 1, imgName, gc);
		this.reqLevel = reqLevel;
		this.bonuses = bonuses;
		this.type = type;
		this.material = material;
		onUse = new EquipAction(this);
	}
	
	public Equippable(String id, int serial, int value, String imgName, GameContainer gc, int reqLevel, Bonuses bonuses, int type, ItemMaterial material)
			throws SlickException, IOException, FontFormatException
	{
		super(id, serial, value, 1, imgName, gc);
		this.reqLevel = reqLevel;
		this.bonuses = bonuses;
		this.type = type;
		this.material = material;
		onUse = new EquipAction(this);
	}
	/**
	 * Return item type
	 * @return Integer representing item type
	 */
	public int type()
	{
		return this.type;
	}
	
	public AnimObject getMSprite()
	{
		return itemMSprite;
	}
	
	public AnimObject getFSprite()
	{
		return itemFSprite;
	}

}
