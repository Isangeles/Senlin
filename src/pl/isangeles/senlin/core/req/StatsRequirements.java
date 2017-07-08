/*
 * StatsRequirements.java
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

import pl.isangeles.senlin.core.Attributes;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.skill.Abilities;

/**
 * @author Isangeles
 *
 */
public class StatsRequirements extends Requirements 
{
	private Attributes minStats;

	public StatsRequirements(Attributes minStats)
	{
		this.minStats = minStats;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.req.Requirements#isMeet(pl.isangeles.senlin.core.Character)
	 */
	@Override
	public boolean isMeet(Character character) 
	{
		return character.getAttributes().compareTo(minStats);
	}

}
