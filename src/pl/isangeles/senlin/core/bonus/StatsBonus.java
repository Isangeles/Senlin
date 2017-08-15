/*
 * StatsBonus.java
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

import pl.isangeles.senlin.core.Attributes;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.character.Character;

/**
 * Class for attributes bonus
 * @author Isangeles
 *
 */
public class StatsBonus extends Bonus 
{
	private Attributes statsBonus;
	/**
	 * Attributes bonus constructor
	 * @param stats Value of bonus attributes
	 */
	public StatsBonus(Attributes stats)
	{
		super(BonusType.STATS, stats.toString());
		statsBonus = stats;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.bonus.Bonus#applyOn(pl.isangeles.senlin.core.Targetable)
	 */
	@Override
	public void applyOn(Targetable object) 
	{
	    object.getAttributes().increaseBy(statsBonus);
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.bonus.Bonus#removeFrom(pl.isangeles.senlin.core.Targetable)
	 */
	@Override
	public void removeFrom(Targetable object) 
	{
	    object.getAttributes().decreaseBy(statsBonus);
	}

}
