/*
 * HealthBonus.java
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

import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for health bonus
 * @author Isangeles
 *
 */
public class HealthBonus extends Bonus 
{
	private int hpBonus;
	/**
	 * Health bonus constructor
	 * @param healthPoints Amount of health points
	 */
	public HealthBonus(int healthPoints)
	{
		super(BonusType.HEALTH, TConnector.getText("ui", "bonHp") + ":" + healthPoints);
		hpBonus = healthPoints;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.bonus.Bonus#applyOn(pl.isangeles.senlin.core.Targetable)
	 */
	@Override
	public void applyOn(Targetable object) 
	{
	    object.modMaxHealth(hpBonus);
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.bonus.Bonus#removeFrom(pl.isangeles.senlin.core.Targetable)
	 */
	@Override
	public void removeFrom(Targetable object) 
	{
	    object.modMaxHealth(-hpBonus);
	}

}
