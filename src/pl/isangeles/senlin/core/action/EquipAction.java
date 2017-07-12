/*
 * EquipAction.java
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
package pl.isangeles.senlin.core.action;

import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.item.Equippable;
import pl.isangeles.senlin.core.Character;

/**
 * Class for equip action, for equippable items
 * @author Isangeles
 *
 */
public class EquipAction extends Action 
{
	private Equippable item;
	/**
	 * Equip action constructor
	 * @param item Item to equip on action start
	 */
	public EquipAction(Equippable item)
	{
		type = ActionType.EQUIP;
		this.item = item;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.action.Action#start(pl.isangeles.senlin.core.Targetable, pl.isangeles.senlin.core.Targetable)
	 */
	@Override
	public boolean start(Targetable user, Targetable target) 
	{
		if(Character.class.isInstance(user))
		{
			Character userChar = (Character)user;
			return userChar.equipItem(item);
		}
		else
			return false;
	}

}
