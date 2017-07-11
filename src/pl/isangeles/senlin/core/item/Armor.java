/*
 * Armor.java
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

import pl.isangeles.senlin.core.Bonuses;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.graphic.AnimObject;
import pl.isangeles.senlin.gui.elements.ItemTile;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
/**
 * Class for armor parts
 * TODO implement different sprite sheet for each armor material
 * @author Isangeles
 *
 */
public class Armor extends Equippable 
{
	public static final int FEET = 0,
							HANDS = 1,
							OFFHAND = 2,
							CHEST = 3,
							HEAD = 4;
	private int armorRating;
	/**
	 * Armor constructor
	 * @param id Item ID
	 * @param name Item name
	 * @param info Item description
	 * @param type Armor type (0-5 value)
	 * @param material Material of which item is made (0-4 value)
	 * @param value Item value in gold
	 * @param armRat Armor rating value
	 * @param bonuses Armor bonuses to statistics
	 * @param reqLevel Level requested to wear armor
	 * @param imgName Name of image file in icon directory for item tile
	 * @param gc Slick game container for item tile
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Armor(String id, int type, ItemMaterial material, int value, int armRat, Bonuses bonuses, int reqLevel, String imgName, GameContainer gc) 
			throws SlickException, IOException, FontFormatException 
	{
		super(id, value, imgName, gc, reqLevel, bonuses, type, material);
		armorRating = armRat;
		this.itemTile = this.setTile(gc);
		if(type == CHEST)
			setSprite();
	}
	/**
	 * Returns item armor rating
	 * @return Armor rating value
	 */
	public int getArmorRat()
	{
		return armorRating;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Usable#use(pl.isangeles.senlin.core.Targetable, pl.isangeles.senlin.core.Targetable)
	 */
	@Override
	public boolean use(Targetable user, Targetable target) 
	{
		return onUse.start(user, target);
	}
	
	@Override
	protected String getInfo()
	{
		String fullInfo = name + System.lineSeparator() +  getTypeName() + System.lineSeparator() + getMaterialName() + System.lineSeparator() + 
				TConnector.getText("ui", "armRat") + ": " + armorRating + System.lineSeparator() + bonuses.getInfo() + TConnector.getText("ui", "itemRLInfo") 
				+ ": " + reqLevel + System.lineSeparator() + info + System.lineSeparator() + TConnector.getText("ui", "itemVInfo") + ": " + value;

		return fullInfo;
	}
	
	@Override
	protected String getTypeName()
	{
		switch(type)
		{
		case FEET:
			return TConnector.getText("ui", "armFeet");
		case HANDS:
			return TConnector.getText("ui", "armHand");
		case OFFHAND:
			return TConnector.getText("ui", "armOff");
		case CHEST:
			return TConnector.getText("ui", "armChest");
		case HEAD:
			return TConnector.getText("ui", "armHead");
		default:
			return TConnector.getText("ui", "errorName");
		}
	}
	
	private String getMaterialName()
	{
		return material.toString();
	}
	/**
	 * Sets sprite for item depended on item material 
	 * @throws SlickException
	 * @throws IOException
	 */
	private void setSprite() throws SlickException, IOException
	{
		switch(material)
		{
		case CLOTH:
			itemSprite = new AnimObject(GConnector.getInput("sprite/avatar/cloth12221-60x70.png"), "sprite"+id, false, 60, 70);
		case LEATHER:
			itemSprite = new AnimObject(GConnector.getInput("sprite/avatar/cloth12221-60x70.png"), "sprite"+id, false, 60, 70);
		case IRON:
			itemSprite = new AnimObject(GConnector.getInput("sprite/avatar/cloth12221-60x70.png"), "sprite"+id, false, 60, 70);
		case STEEL:
			itemSprite = new AnimObject(GConnector.getInput("sprite/avatar/cloth12221-60x70.png"), "sprite"+id, false, 60, 70);
		case NEPHRITE:
			itemSprite = new AnimObject(GConnector.getInput("sprite/avatar/cloth12221-60x70.png"), "sprite"+id, false, 60, 70);
		default:
			itemSprite = new AnimObject(GConnector.getInput("sprite/avatar/cloth12221-60x70.png"), "sprite"+id, false, 60, 70);
		}
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.item.Item#setTile(org.newdawn.slick.GameContainer)
	 */
	@Override
	protected ItemTile setTile(GameContainer gc) throws SlickException, IOException, FontFormatException
    {
    	return new ItemTile(GConnector.getInput("icon/"+imgName), id+itemNumber, false, gc, this.getInfo());
    }
}
