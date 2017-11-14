/*
 * Modifier.java
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
/**
 * Class for targetable objects modifiers
 * @author Isangeles
 *
 */
public abstract class Modifier 
{
	protected ModifierType type;
	protected String info;
	/**
	 * Modifier constructor
	 * @param type Modifier type
	 * @param info Modifier info
	 */
	protected Modifier(ModifierType type, String info)
	{
		this.type = type;
		this.info = info;
	}
	/**
	 * Applies modifier on specified object
	 * @param object Targetable game object
	 */
	public abstract void applyOn(Targetable object);
	/**
	 * Removes modifier from specified object
	 * @param object Targetable game object
	 */
	public abstract void removeFrom(Targetable object);
	/**
	 * Returns info about modifier
	 * @return String with info
	 */
	public String getInfo()
	{
		return info;
	}
	/**
	 * Returns modifier type
	 * @return Modifier type
	 */
	public ModifierType getType()
	{
		return type;
	}
}
