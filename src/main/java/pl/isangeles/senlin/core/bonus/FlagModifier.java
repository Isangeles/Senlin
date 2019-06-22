/*
 * FlagModifier.java
 * 
 * Copyright 2018 Dariusz Sikora <dev@isangeles.pl>
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

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.character.Character;

/**
 * Class for flag modifiers
 * @author Isangeles
 *
 */
public class FlagModifier extends Modifier 
{
	private final String flag;
	/**
	 * Flag modifier constructor
	 * @param flag Flag to add on target
	 */
	public FlagModifier(String flag)
	{
		super(ModifierType.FLAG, "");
		this.flag = flag;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.bonus.Modifier#applyOn(pl.isangeles.senlin.core.Targetable)
	 */
	@Override
	public void applyOn(Targetable object)
	{
		if(Character.class.isInstance(object))
		{
			Character character = (Character)object;
			character.getFlags().add(flag);
		}
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.bonus.Modifier#removeFrom(pl.isangeles.senlin.core.Targetable)
	 */
	@Override
	public void removeFrom(Targetable object) 
	{
		if(Character.class.isInstance(object))
		{
			Character character = (Character)object;
			character.getFlags().remove(flag);
		}
	}
}
