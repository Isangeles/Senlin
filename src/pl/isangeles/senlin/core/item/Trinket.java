/*
 * Trinket.java
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
import pl.isangeles.senlin.gui.elements.ItemTile;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
/**
 * Class for trinkets like rings, amulets, etc.
 * @author Isangeles
 *
 */
public class Trinket extends Equippable 
{
	public static final int FINGER = 0,
							NECK = 1,
							ARTIFACT = 2;
	
	public Trinket(String id, int type, int value, String imgName, GameContainer gc, int reqLevel,
			Bonuses bonuses) throws SlickException, IOException, FontFormatException 
	{
		super(id, value, imgName, gc, reqLevel, bonuses, type, ItemMaterial.IRON);
		this.itemTile = this.setTile(gc);
	}
	
	public Trinket(String id, int serial, int type, int value, String imgName, GameContainer gc, int reqLevel,
			Bonuses bonuses) throws SlickException, IOException, FontFormatException 
	{
		super(id, serial, value, imgName, gc, reqLevel, bonuses, type, ItemMaterial.IRON);
		this.itemTile = this.setTile(gc);
	}

	@Override
	protected String getInfo() 
	{
		String fullInfo = name + System.lineSeparator() + getTypeName() + System.lineSeparator() + bonuses.getInfo() + TConnector.getText("ui", "itemRLInfo") + ": " + reqLevel + 
						  System.lineSeparator() + info + System.lineSeparator() + TConnector.getText("ui", "itemVInfo") + ": " + value;
		return fullInfo;
	}
	@Override
	protected String getTypeName()
	{
		switch(type)
		{
		case FINGER:
			return TConnector.getText("ui", "triFinger");
		case NECK:
			return TConnector.getText("ui", "triNeck");
		case ARTIFACT:
			return TConnector.getText("ui", "triArtifact");
		default:
			return TConnector.getText("ui", "errorName");
		}
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Usable#use(pl.isangeles.senlin.core.Targetable, pl.isangeles.senlin.core.Targetable)
	 */
	@Override
	public boolean use(Targetable user, Targetable target) 
	{
		return onUse.start(user, target);
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
