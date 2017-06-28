/*
 * MiscPattern.java
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
package pl.isangeles.senlin.data.pattern;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Action;
import pl.isangeles.senlin.core.ActionType;
import pl.isangeles.senlin.core.item.Misc;
import pl.isangeles.senlin.data.EffectsBase;

/**
 * Class for miscellaneous item patterns
 * @author Isangeles
 *
 */
public class MiscPattern 
{
	private final String id;
	private final int value;
	private final boolean disposable;
	private final String icon;
	private final String actionType;
	private final String actionId;
	/**
	 * Misc item pattern constructor
	 * @param id Item ID
	 * @param value Item value
	 * @param disposable If item disappears after use
	 * @param imgName Item image name, for icon
	 * @param onUse Action on use(ppm click in inventory)
	 */
	public MiscPattern(String id, int value, boolean disposable, String icon, String actionType, String actionId) 
	{
		this.id = id;
		this.value = value;
		this.disposable = disposable;
		this.icon = icon;
		this.actionType = actionType;
		this.actionId = actionId;
	}
	/**
	 * Returns pattern item ID
	 * @return String with item ID
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * Creates item from this pattern
	 * @param gc Slick game container
	 * @return New armor object
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Misc make(GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		ActionType at = ActionType.fromString(actionType);
		Action action;
		switch(at)
		{
		case EFFECTUSER:
			action = new Action(EffectsBase.getEffect(actionId), "user");
			break;
		case EFFECTTARGET:
			action = new Action(EffectsBase.getEffect(actionId), "target");
			break;
		default:
			action = new Action();
		}
		return new Misc(id, value, disposable, icon, action, gc);
	}
}
