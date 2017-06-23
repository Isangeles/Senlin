/*
 * Effects.java
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
package pl.isangeles.senlin.core;

import java.util.ArrayList;
import java.util.List;
/**
 * Container for character effects
 * @author Isangeles
 *
 */
public class Effects extends ArrayList<Effect>
{
	private static final long serialVersionUID = 1L;

	public Effects() 
	{
	}
	/**
	 * Updates all effects in container
	 * @param delta Time (in milliseconds) from last update
	 * @param character Container owner
	 */
	public void update(int delta, Character character)
	{
		List<Effect> effectsToRemove = new ArrayList<>();
		for(Effect effect : this)
		{
			if(effect.isOn())
			{
				effect.updateTime(delta);
				effect.affect(character);
			}
			else
			{
				effect.removeFrom(character);
				effectsToRemove.add(effect);
			}
		}
		this.removeAll(effectsToRemove);
	}
}