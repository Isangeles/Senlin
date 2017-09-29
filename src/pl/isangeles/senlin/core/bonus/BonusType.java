/*
 * BonusType.java
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
package pl.isangeles.senlin.core.bonus;

/**
 * Enumeration for bonus types
 * @author Isangeles
 *
 */
public enum BonusType 
{
	NONE, STATS, HEALTH, MANA, HASTE, DODGE, DAMAGE;
	/**
	 * Converts specified type ID to bonus type enum
	 * @param id String with bonus type ID
	 * @return Bonus type enum
	 */
	public static BonusType fromString(String id)
	{
		switch(id)
		{
		case "statsBonus":
			return BonusType.STATS;
		case "healthBonus":
			return BonusType.HEALTH;
		case "manaBonus":
			return BonusType.MANA;
		case "haseBonus":
			return BonusType.HASTE;
		case "dodgeBonus":
			return BonusType.DODGE;
		case "damageBonus":
			return BonusType.DAMAGE; 
		default:
			return BonusType.NONE;
		}
	}
}
