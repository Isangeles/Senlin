/*
 * Resistance.java
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

import java.util.EnumMap;

import pl.isangeles.senlin.core.effect.EffectType;

/**
 * Class for game objects resistances
 * @author Isangeles
 *
 */
public class Resistance 
{
	private EnumMap<EffectType, Integer> resistances = new EnumMap<>(EffectType.class);
	/**
	 * Resistance constructor
	 */
	public Resistance()
	{
		for(EffectType resi : resistances.keySet())
		{
			resistances.put(resi, 0);
		}
	}
	/**
	 * Adds resistance for specified element
	 * @param type Resistance type
	 * @param value Resistance value
	 */
	public void modResistanceFor(EffectType type, int value)
	{
		int resi = resistances.get(type);
		resistances.put(type, resi + value); 
	}
	/**
	 * Returns value of resistance for specified element 
	 * @param type Effect type
	 * @return Resistance value
	 */
	public int getResistanceFor(EffectType type)
	{
		return resistances.get(type);
	}
}
