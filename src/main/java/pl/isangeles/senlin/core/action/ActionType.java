/*
 * ActionType.java
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
package pl.isangeles.senlin.core.action;

/**
 * Enumeration for action types
 * @author Isangeles
 *
 */
public enum ActionType 
{
	NONE, ATTACK, EFFECTUSER, EFFECTTARGET, READ, LOOT, EQUIP, REST;
	/**
	 * Converts action type name to ActionType enum
	 * @param actionName String with action type name
	 * @return ActionType enum object
	 */
	public static ActionType fromString(String actionName)
	{
		switch(actionName)
		{
		case "attack":
			return ActionType.ATTACK;
		case "effectUser":
			return ActionType.EFFECTUSER;
		case "effectTarget":
			return ActionType.EFFECTTARGET;
		case "read":
			return ActionType.READ;
		case "loot":
		    return ActionType.LOOT;
		case "equip":
			return ActionType.EQUIP;
		case "rest":
			return ActionType.REST;
		default:
			return ActionType.NONE;
		}
	}
}
