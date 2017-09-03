/*
 * Buffs.java
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
package pl.isangeles.senlin.core.skill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.data.save.SaveElement;

/**
 * Container for characters buffs
 * @author Isangeles
 *
 */
public class Buffs extends ArrayList<Buff> implements SaveElement
{
	private static final long serialVersionUID = 1L;
	
	private Targetable owner;
	private List<Buff> buffsToRemove = new ArrayList<>();
	/**
	 * Buffs container constructor 
	 * @param owner Owner of this container
	 */
	public Buffs(Targetable owner)
	{
		this.owner = owner;
	}
	/**
	 * Updates all buffs in container
	 * @param delta Time between updates
	 */
	public void update(int delta)
	{
		for(Buff buff : this)
		{
			buff.update(delta);
			if(buff.isActive() == false)
				buffsToRemove.add(buff);
		}
		removeAll(buffsToRemove);
	}
	
	@Override
	public boolean add(Buff buff)
	{
		if(super.add(buff))
		{
			buff.setActive(true);
			return true;
		}
		else
			return false;
	}
	
	@Override
	public boolean addAll(Collection<? extends Buff> buffs)
	{
		boolean ok = true;
		for(Buff buff : buffs)
		{
			if(!add(buff))
				ok = false;
		}
		return ok;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.data.save.SaveElement#getSave(org.w3c.dom.Document)
	 */
	@Override
	public Element getSave(Document doc) 
	{
		Element buffsE = doc.createElement("buffs");
		for(Buff buff : this)
		{
			buffsE.appendChild(buff.getSave(doc));
		}
		return buffsE;
	}
	
}
