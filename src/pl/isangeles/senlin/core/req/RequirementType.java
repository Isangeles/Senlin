/*
 * RequirementType.java
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
package pl.isangeles.senlin.core.req;

/**
 * Enumeration for requirements types
 * @author Isangeles
 *
 */
public enum RequirementType 
{
	NONE, LEVEL, STATS, GENDER, GUILD, GOLD, ITEMS, POINTS, MANA, HEALTH, WEAPON, FLAG;
	/**
	 * Converts type ID to requirement type enum
	 * @param typeName Requirement type ID
	 * @return Requirement type enum
	 */
	public static RequirementType fromString(String typeName)
	{
		switch(typeName)
		{
		case "levelReq":
			return RequirementType.LEVEL;
		case "statsReq":
			return RequirementType.STATS;
		case "genderReq":
			return RequirementType.GENDER;
		case "guildReq":
			return RequirementType.GUILD;
		case "goldReq":
			return RequirementType.GOLD;
		case "itemsReq":
			return RequirementType.ITEMS;
		case "pointsReq":
		    return RequirementType.POINTS;
		case "manaReq":
		    return RequirementType.MANA;
		case "hpReq":
		    return RequirementType.HEALTH;
		case "weaponReq":
			return RequirementType.WEAPON;
		case "flagReq":
			return RequirementType.FLAG;
		default:
			return RequirementType.NONE;
		}
	}
}
