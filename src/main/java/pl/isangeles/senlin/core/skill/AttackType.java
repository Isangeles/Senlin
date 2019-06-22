/*
 * AttackType.java
 * 
 * Copyright 2017 Dariusz Sikora <dev@isangeles.pl>
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
package pl.isangeles.senlin.core.skill;

import pl.isangeles.senlin.graphic.AvatarAnimType;

/**
 * Enumeration for attack types
 * @author Isangeles
 *
 */
public enum AttackType 
{
	MELEE, RANGE, SPELL;
	/**
	 * Converts attack type name to attack type
	 * @param typeName String with attack type name
	 * @return Attack type enumeration
	 */
	public static AttackType fromName(String typeName)
	{
		switch(typeName)
		{
		case "melee":
			return AttackType.MELEE;
		case "range":
			return AttackType.RANGE;
		case "spell":
			return AttackType.SPELL;
		default:
			return AttackType.MELEE;
		}
	}
	/**
	 * Returns avatar animation type for this attack type
	 * @return Avatar animation type enumeration
	 */
	public AvatarAnimType getAnimType()
	{
		switch(this)
		{
		case MELEE:
			return AvatarAnimType.MELEE;
		case RANGE:
			return AvatarAnimType.RANGE;
		case SPELL:
			return AvatarAnimType.CAST;
		default:
			return AvatarAnimType.MELEE;
		}
	}
}
