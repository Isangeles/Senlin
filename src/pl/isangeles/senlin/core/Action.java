/*
 * Action.java
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
package pl.isangeles.senlin.core;

import pl.isangeles.senlin.core.skill.Skill;

/**
 * Class for in-game actions(eg. items on-click actions)
 * @author Isangeles
 *
 */
public class Action 
{
	public ActionType type;
	public Skill skill;
	public Effect effect;
	/**
	 * Default constructor, action do nothing
	 */
	public Action()
	{
		type = ActionType.NONE;
	}
	/**
	 * Constructor for action causing specified effect on user/target
	 * @param effectOnAction Effect of action
	 * @param target String with 'user' for effect on user, 'target' for effect on target
	 */
	public Action(Effect effectOnAction, String target)
	{
		if(target.equals("user"))
			type = ActionType.EFFECTUSER;
		if(target.equals("target"))
			type = ActionType.EFFECTTARGET;
		
		effect = effectOnAction;
		if(effect == null)
			type = ActionType.NONE;
	}
	/**
	 * Starts action
	 * @param user Action user
	 * @param target User target 
	 * @return 
	 */
	public boolean start(Targetable user, Targetable target)
	{
		switch(type)
		{
		case EFFECTUSER:
			effect.turnOn(user);
			return true;
		default:
			return true;
		}
	}
	/**
	 * Returns action type
	 * @return ActionType enumeration
	 */
	public ActionType getType()
	{
		return type;
	}
	
}
