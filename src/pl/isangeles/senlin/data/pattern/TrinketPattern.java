/*
 * TrinketPattern.java
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
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.action.Action;
import pl.isangeles.senlin.core.action.ActionType;
import pl.isangeles.senlin.core.action.EffectAction;
import pl.isangeles.senlin.core.bonus.Modifier;
import pl.isangeles.senlin.core.bonus.Modifiers;
import pl.isangeles.senlin.core.item.Trinket;
import pl.isangeles.senlin.core.item.TrinketType;
import pl.isangeles.senlin.data.EffectsBase;

/**
 * Class for trinkets patterns
 * @author Isangeles
 *
 */
public class TrinketPattern 
{
	private final String id;
	private final String type;
	private final int value;
	private final int level;
	private final String icon;
	private final Modifiers bonuses;
	private final List<String> equipEffects;
	private final String actionType;
	private final String actionId;
	/**
	 * Trinket pattern constructor
	 * @param id Trinket ID
	 * @param type String with trinket type name
	 * @param level Level required to use this item
	 * @param value Item value
	 * @param icon Item icon for GUI
	 * @param bonuses Item bonuses 
	 * @param equipEffects List with IDs of all equip Effects
	 * @param actionType Type of on-click action
	 * @param actionId ID for on-click action
	 */
	public TrinketPattern(String id, String type, int level, int value, String icon, List<Modifier> bonuses, List<String> equipEffects, String actionType, String actionId) 
	{
		this.id = id;
		this.type = type;
		this.value = value;
		this.level = level;
		this.icon = icon;
		this.bonuses = new Modifiers();
		this.bonuses.addAll(bonuses);
		this.equipEffects = equipEffects;
		this.actionType = actionType;
		this.actionId = actionId;
	}
	/**
	 * Returns ID of item from this pattern
	 * @return String with item ID
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * Return new instance of item from this pattern
	 * @param gc Slick game container
	 * @return New instance of specific trinket from this pattern
	 * @throws NumberFormatException
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Trinket make(GameContainer gc) throws NumberFormatException, SlickException, IOException, FontFormatException
	{
		Action action;
		ActionType aType = ActionType.fromString(actionType);
		switch(aType)
		{
		case EFFECTUSER:
			action = new EffectAction(actionId, "user");
			break;
		case EFFECTTARGET:
			action = new EffectAction(actionId, "target");
			break;
		default:
			action = new EffectAction();
		}
		
		return new Trinket(id, TrinketType.fromString(type), value, icon, level, bonuses, equipEffects, action, gc);
	}
	/**
	 * Return new instance of item(with specified serial number) from this pattern
	 * @param gc Slick game container
	 * @param serial Serial number from this specific instance of trinket from this pattern
	 * @return New instance of specific trinket from this pattern
	 * @throws NumberFormatException
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Trinket make(GameContainer gc, int serial) throws NumberFormatException, SlickException, IOException, FontFormatException
	{
		Action action;
		ActionType aType = ActionType.fromString(actionType);
		switch(aType)
		{
		case EFFECTUSER:
			action = new EffectAction(actionId, "user");
			break;
		case EFFECTTARGET:
			action = new EffectAction(actionId, "target");
			break;
		default:
			action = new EffectAction();
		}
		
		return new Trinket(id, serial, TrinketType.fromString(type), value, icon, level, bonuses, equipEffects, action, gc);
	}

}
