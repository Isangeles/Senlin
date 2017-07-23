/*
 * Requirements.java
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

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.data.save.SaveElement;
/**
 * Abstract class for requirements
 * @author Isangeles
 *
 */
public abstract class Requirement implements SaveElement
{
	protected String info;
	/**
	 * Checks if specified character meets this requirement
	 * @param character Game character
	 * @return True if specified  character meet this requirements, false otherwise
	 */
	public abstract boolean isMetBy(Character character);
	/**
	 * Takes items/gold required by this requirement from specified character
	 * @param character Game character
	 */
	public abstract void charge(Character character);
	
	public String getInfo()
	{
		return info;
	}
}
