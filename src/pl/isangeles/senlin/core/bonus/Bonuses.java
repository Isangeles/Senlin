/*
 * Bonuses.java
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

import java.util.ArrayList;

import pl.isangeles.senlin.core.character.Character;
/**
 * Container class for bonuses
 * @author Isnageles
 *
 */
public class Bonuses extends ArrayList<Bonus>
{
	private static final long serialVersionUID = 1L;
	/**
	 * Applies all bonuses on specified character
	 * @param character Game character
	 */
	public void applyAllOn(Character character)
	{
		for(Bonus bonus : this)
		{
			bonus.applyOn(character);
		}
	}
	/**
	 * Removes all bonuses from specified character
	 * @param character Game character
	 */
	public void removeAllFrom(Character character)
	{
		for(Bonus bonus : this)
		{
			bonus.removeFrom(character);
		}
	}
	/**
	 * Check if any bonus occurs
	 * @return
	 */
	public boolean isBonus()
	{
		if(this.size() > 0)
			return true;
		else
			return false;
	}
	/**
	 * Get full info about this specific bonus
	 * @return String with full info about bonus
	 */
	public String getInfo()
	{
		String bonusInfo = "";
		
		for(Bonus bonus : this)
		{
			bonusInfo += bonus.getInfo() + System.lineSeparator();
		}
		
		return bonusInfo;
	}
}