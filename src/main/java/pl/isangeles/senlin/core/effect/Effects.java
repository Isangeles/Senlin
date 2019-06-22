/*
 * Effects.java
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
package pl.isangeles.senlin.core.effect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.data.EffectsBase;
import pl.isangeles.senlin.data.save.SaveElement;
/**
 * Container for character effects
 * @author Isangeles
 *
 */
public class Effects extends ArrayList<Effect> implements SaveElement
{
	private static final long serialVersionUID = 1L;

	private Targetable owner;
	List<Effect> effectsToRemove = new ArrayList<>();
	/**
	 * Effects container constructor
	 * @param owner owner of this container
	 */
	public Effects(Targetable owner)
	{
		this.owner = owner;
	}
	
	@Override
	public boolean add(Effect effect)
	{
		if(super.add(effect)) 
		{
			effect.turnOn(owner);
			return true;
		}
		else
			return false;
	}
	
	@Override
	public boolean addAll(Collection<? extends Effect> effects)
	{
		boolean ok = true;
		for(Effect effect : effects)
		{
			if(!add(effect))
				ok = false;
		}
		return ok;
	}
	/**
	 * Adds all effects from specified source(only this effects that are NOT already active)  
	 * @param source Effect source
	 */
	public void addAllFrom(EffectSource source)
	{
		if(source != null)
		{
			for(String id : source.getEffectsIds())
			{
				if(!hasEffect(id))
				{
					add(source.getEffect(id));
				}
				else
				{
					for(Effect effect : get(id))
					{
						if(effect.getSource() != null && effect.getSource() != source && effect.getSource().getSerialId().equals(source.getSerialId())) 
						{
							add(source.getEffect(id));
							break;
						}
					}
				}
			}
		}
	}
	/**
	 * Removes and disables specified effect
	 * @param effect Effect to remove
	 * @return True if specified effect was successfully removed
	 */
	public boolean remove(Effect effect)
	{
		if(super.remove(effect))
		{
			effect.removeFrom(owner);
			return true;
		}
		else
			return false;
	}
	/**
	 * Removes and disables all effects from specified list
	 * @param effects List with effects
	 * @return True if all effects was successfully removed, false otherwise
	 */
	public boolean removeAll(List<? extends Effect>effects)
	{
		boolean ok = true;
		for(Effect effect : effects)
		{
			if(!remove(effect))
				ok = false;
		}
		return ok;
	}
	/**
	 * Removes all active effects from specified source
	 * @param effectsIds 
	 * @param source
	 */
	public void removeAllFrom(EffectSource source)
	{
		for(Effect effect : this)
		{
			if(effect.getSource() == source)
				effectsToRemove.add(effect);
		}
	}
	/**
	 * Updates all effects in container
	 * @param delta Time (in milliseconds) from last update
	 * @param character Container owner
	 */
	public void update(int delta)
	{
		for(Effect effect : this)
		{
			if(effect.isOn())
			{
				effect.updateTime(delta);
				effect.affect(owner);
			}
			else
			{
				effectsToRemove.add(effect);
			}
		}
		for(Effect effect : effectsToRemove)
		{
			effect.removeFrom(owner);
		}
		this.removeAll(effectsToRemove);
		effectsToRemove.clear();
	}
	
	@Override
	public void clear()
	{
		for(Effect effect : this)
		{
			effect.removeFrom(owner);
		}
		super.clear();
	}
	/**
	 * Checks if similar effect is already active
	 * @param effect Effect to check
	 * @return True if such effect is already active, false otherwise
	 */
	public boolean hasEffect(Effect effect)
	{
		for(Effect e : this)
		{
			if(e.getId().equals(effect.getId()))
				return true;
		}
		return false;
	}
	/**
	 * Checks if effect with specified ID is active
	 * @param effectId Effect ID
	 * @return True if effect with specified ID is active, false otherwise
	 */
	public boolean hasEffect(String effectId)
	{
		for(Effect effect : this)
		{
			if(effect.getId().equals(effectId))
				return true;
		}
		return false;
	}
	/**
	 * Checks if effect with specified ID and from source with specified ID is active
	 * @param effectId Effect ID 
	 * @param sourceEffectId Source ID
	 * @return True if effect is active, false otherwise
	 */
	public boolean hasEffectFrom(String effectId, String sourceEffectId)
	{
		for(Effect effect : this)
		{
			if(effect.getId().equals(effectId))
			{
				if(effect.getSource() != null)
				{
					if(effect.getSource().getSerialId().equals(sourceEffectId))
						return true;
				}
			}
		}
		return false;
	}
	/**
	 * Returns all effects with specified ID 
	 * @param effectId Effect ID
	 * @return List with all active effects with specified ID
	 */
	public List<Effect> get(String effectId)
	{
		List<Effect> effects = new ArrayList<>();
		for(Effect effect : this)
		{
			if(effect.getId().equals(effectId))
				effects.add(effect);
		}
		return effects;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.data.save.SaveElement#getSave(org.w3c.dom.Document)
	 */
	public Element getSave(Document doc)
	{
	    Element effectsE = doc.createElement("effects");
	    for(Effect effect : this)
	    {
	        effectsE.appendChild(effect.getSave(doc));
	    }
	    return effectsE;
	}
	/**
	 * Lists all active effects
	 * @return String with all effects listed
	 */
	public String list()
	{
		String list = "effects on-" + owner.getSerialId() + ":";
		for(Effect effect : this)
		{
			list += System.lineSeparator() + effect.getId() + "//source-" + effect.getSourceId();
		}
		return list;
	}
}
