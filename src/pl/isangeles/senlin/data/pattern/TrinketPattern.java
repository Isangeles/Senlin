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

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Bonuses;
import pl.isangeles.senlin.core.action.Action;
import pl.isangeles.senlin.core.action.ActionType;
import pl.isangeles.senlin.core.action.EffectAction;
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
	private final String bonuses;
	private final String actionType;
	private final String actionId;
	
	public TrinketPattern(String id, String type, int level, int value, String icon, String bonuses, String actionType, String actionId) 
	{
		this.id = id;
		this.type = type;
		this.value = value;
		this.level = level;
		this.icon = icon;
		this.bonuses = bonuses;
		this.actionType = actionType;
		this.actionId = actionId;
	}
	
	public String getId()
	{
		return id;
	}
	
	public Trinket make(GameContainer gc) throws NumberFormatException, SlickException, IOException, FontFormatException
	{
		Action action;
		ActionType aType = ActionType.fromString(actionType);
		switch(aType)
		{
		case EFFECTUSER:
			action = new EffectAction(EffectsBase.getEffect(actionId), "user");
			break;
		case EFFECTTARGET:
			action = new EffectAction(EffectsBase.getEffect(actionId), "target");
			break;
		default:
			action = new EffectAction();
		}
		
		return new Trinket(id, TrinketType.fromString(type).ordinal(), value, icon, level, new Bonuses(bonuses), action, gc);
	}
	
	public Trinket make(GameContainer gc, int serial) throws NumberFormatException, SlickException, IOException, FontFormatException
	{
		Action action;
		ActionType aType = ActionType.fromString(actionType);
		switch(aType)
		{
		case EFFECTUSER:
			action = new EffectAction(EffectsBase.getEffect(actionId), "user");
			break;
		case EFFECTTARGET:
			action = new EffectAction(EffectsBase.getEffect(actionId), "target");
			break;
		default:
			action = new EffectAction();
		}
		
		return new Trinket(id, serial, TrinketType.fromString(type).ordinal(), value, icon, level, new Bonuses(bonuses), action, gc);
	}

}
