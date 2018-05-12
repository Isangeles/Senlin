/*
 * Requirements.java
 * 
 * Copyright 2017-2018 Dariusz Sikora <darek@pc-solus>
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

import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.data.save.SaveElement;
/**
 * Abstract class for requirements
 * @author Isangeles
 *
 */
public abstract class Requirement implements SaveElement
{
	protected final RequirementType type;
	protected String info;
	protected boolean met;
	protected final boolean expect;
	/**
	 * Requirement constructor
	 * @param type Requirement type
	 * @param info Requirement info
	 */
	protected Requirement(RequirementType type, String info)
	{
		this.type = type;
		this.info = info;
		expect = true;
	}
	/**
	 * Requirement constructor
	 * @param type Requirement type
	 * @param info Requirement info
	 * @param expect Expected result of requirement check
	 */
	protected Requirement(RequirementType type, String info, boolean expect)
	{
		this.type = type;
		this.info = info;
		this.expect = expect;
	}
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
	/**
	 * Returns info about this requirement
	 * @return String with info
	 */
	public String getInfo()
	{
		return info;
	}
	/**
	 * Returns requirement type
	 * @return Requirement type enum
	 */
	public RequirementType getType()
	{
		return type;
	}
}
