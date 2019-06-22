/*
 * Magicka.java
 * 
 * Copyright 2017 Dariusz Sikora <dev@isangeles.pl>
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

/**
 * Tuple for magicka points
 * @author Isangeles
 *
 */
public class Magicka 
{
	private int value;
	private int max;
	/**
	 * Default magicka constructor
	 */
	public Magicka() {}
	/**
	 * Magicka constructor
	 * @param value Current magicka value
	 * @param max Maximal magicka value
	 */
	public Magicka(int value, int max)
	{
		this.value = value;
		this.max = max;
	}
	/**
	 * Modifies current magicka value by specified value
	 * @param value Value to add(negative value to subtract)
	 */
	public void modValue(int value)
	{
		if(this.value + value > max)
			value = max;
		else
			this.value += value;
	}
	/**
	 * Modifies maximal magicka value by specified value
	 * @param value Value to add(negative value to subtract)
	 */
	public void modMax(int value)
	{
		max += value;
	}
	/**
	 * Sets specified value as current magicka value
	 * @param value Value to set
	 */
	public void setValue(int value)
	{
		this.value = value;
	}
	/**
	 * Sets specified value as maximal value of magicka
	 * @param value Value to set
	 */
	public void setMax(int value)
	{
		this.max = value;
	}
	/**
	 * Returns current magicka value
	 * @return magicka value
	 */
	public int getValue()
	{
		return value;
	}
	/**
	 * Returns maximal magicka value
	 * @return Maximal magicka value
	 */
	public int getMax()
	{
		return max;
	}
	
	@Override
	public String toString()
	{
		return value + "/" + max;
	}
}
