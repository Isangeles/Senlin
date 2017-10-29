/*
 * EffectType.java
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
package pl.isangeles.senlin.core.effect;

import pl.isangeles.senlin.util.TConnector;

/**
 * Enumeration for effect types used by items, skills, etc.
 * @author Isangeles
 *
 */
public enum EffectType 
{
	FIRE, ICE, NATURE, MAGIC, NORMAL;
	/**
	 * Converts type ID to effect type enumeration
	 * @param typeId String with type ID
	 * @return Effect type enumeration
	 */
	public static EffectType fromId(String typeId)
	{
		switch(typeId)
		{
		case "fire":
			return EffectType.FIRE;
		case "ice":
			return EffectType.ICE;
		case "nature":
			return EffectType.NATURE;
		case "magic":
			return EffectType.MAGIC;
		default:
			return EffectType.NORMAL;
		}
	}
	/**
	 * Returns type name
	 * @return String with type name
	 */
	public String getName()
	{
		switch(this)
		{
		case FIRE:
			return TConnector.getText("ui", "");
		case ICE:
			return TConnector.getText("ui", "");
		case NATURE:
			return TConnector.getText("ui", "");
		case MAGIC:
			return TConnector.getText("ui", "");
		default:
			return "";
		}
	}
}
