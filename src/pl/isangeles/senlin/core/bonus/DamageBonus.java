/*
 * DamageBonus.java
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
import pl.isangeles.senlin.core.item.WeaponType;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for damage bonus
 * Note that this bonus is only dynamically added to character hit, i.e. not affect character in any other way
 * @author Isangeles
 *
 */
public class DamageBonus extends Bonus
{
	private int dmgBonus;
	private WeaponType weaponType;
	/**
	 * Damage bonus constructor(for specific weapon type)
	 * @param dmgValue Bonus value
	 * @param weaponType Required type of weapon
	 */
	public DamageBonus(int dmgValue, WeaponType weaponType)
	{
		super(BonusType.DAMAGE, TConnector.getText("ui", "bonDmg") + ":" + dmgValue + "(" + weaponType.getName() + ")");
		
		dmgBonus = dmgValue;
		this.weaponType = weaponType;
	}
	/**
	 * Damage bonus constructor
	 * @param dmgValue Bonus value
	 */
	public DamageBonus(int dmgValue)
	{
		super(BonusType.DAMAGE, TConnector.getText("ui", "bonDamage") + ":" + dmgValue);
		
		dmgBonus = dmgValue;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.bonus.Bonus#applyOn(pl.isangeles.senlin.core.Targetable)
	 */
	@Override
	public void applyOn(Targetable target)
	{
		return;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.bonus.Bonus#removeFrom(pl.isangeles.senlin.core.Targetable)
	 */
	@Override
	public void removeFrom(Targetable target)
	{
		return;
	}
	/**
	 * Returns bonus damage value
	 * @return Damage value
	 */
	public int getDmg()
	{
		return dmgBonus;
	}
	/**
	 * Returns weapon type required by this value or null if bonus affects on all weapon types
	 * @return Weapon type or NULL
	 */
	public WeaponType getWeaponType()
	{
		return weaponType;
	}
}
