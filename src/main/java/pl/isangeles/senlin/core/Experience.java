/*
 * Experience.java
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

/**
 * Tuple for experience points
 * @author Isangeles
 *
 */
public class Experience 
{
	private int value;
	private int max;
	/**
	 * Default experience constructor
	 */
	public Experience() {}
	/**
	 * Experience constructor
	 * @param value Current experience value
	 * @param max Maximal experience value
	 */
	public Experience(int value, int max)
	{
		this.value = value;
		this.max = max;
	}
	/**
	 * Modifies current experience value by specified value
	 * @param value Value to add(negative value to subtract)
	 */
	public void modValue(int value)
	{
		this.value += value;
	}
	/**
	 * Modifies maximal experience value by specified value
	 * @param value Value to add(negative value to subtract)
	 */
	public void modMax(int value)
	{
		max += value;
	}
	/**
	 * Sets specified value as current experience value
	 * @param value Value to set
	 */
	public void setValue(int value)
	{
		this.value = value;
	}
	/**
	 * Sets specified value as maximal value of experience
	 * @param value Value to set
	 */
	public void setMax(int value)
	{
		this.max = value;
	}
	/**
	 * Returns current experience value
	 * @return Health value
	 */
	public int getValue()
	{
		return value;
	}
	/**
	 * Returns maximal experience value
	 * @return Maximal experience value
	 */
	public int getMax()
	{
		return max;
	}
	/**
	 * Checks if current value is maximal
	 * @return True if current value is equals or greater then maximal, false otherwise
	 */
	public boolean isMax()
	{
		return value >= max;
	}
	
	@Override
	public String toString()
	{
		return value + "/" + max;
	}
}
