/*
 * Bonus.java
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

import pl.isangeles.senlin.core.character.Character;
/**
 * Class for bonuses
 * @author Isangeles
 *
 */
public abstract class Bonus 
{
	protected BonusType type;
	protected String info;
	/**
	 * Bonus constructor
	 * @param type Bonus type
	 * @param info Bonus info
	 */
	protected Bonus(BonusType type, String info)
	{
		this.type = type;
		this.info = info;
	}
	/**
	 * Applies bonus on specified character
	 * @param character Game character
	 */
	public abstract void applyOn(Character character);
	/**
	 * Removes bonus from character
	 * @param character Game character
	 */
	public abstract void removeFrom(Character character);
	/**
	 * Returns info about bonus
	 * @return String with info
	 */
	public String getInfo()
	{
		return info;
	}
	/**
	 * Returns bonus type
	 * @return Bonus type
	 */
	public BonusType getType()
	{
		return type;
	}
}
