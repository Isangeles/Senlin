/*
 * EffectSource.java
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
package pl.isangeles.senlin.core.effect;

import java.util.Collection;

import pl.isangeles.senlin.core.Targetable;

/**
 * Interface for effects sources like skills, items, etc.
 * @author Isangeles
 *
 */
public interface EffectSource 
{
	/**
	 * Returns source ID
	 * @return String with effect source ID
	 */
	public String getId();
	/**
	 * Returns source serial ID
	 * @return String with effect source ID
	 */
	public String getSerialId();
	/**
	 * Return effect source owner
	 * @return Targetable object
	 */
	public Targetable getOwner();
	/**
	 * Returns all effects from this source
	 * @return Collection with effects
	 */
	public Collection<Effect> getEffects();
}
