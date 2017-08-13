/*
 * Effects.java
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

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.data.save.SaveElement;
/**
 * Container for character effects
 * @author Isangeles
 *
 */
public class Effects extends ArrayList<Effect> implements SaveElement
{
	private static final long serialVersionUID = 1L;

	/**
	 * Updates all effects in container
	 * @param delta Time (in milliseconds) from last update
	 * @param character Container owner
	 */
	public void update(int delta, Character character)
	{
		List<Effect> effectsToRemove = new ArrayList<>();
		for(Effect effect : this)
		{
			if(effect.isOn())
			{
				effect.updateTime(delta);
				effect.affect(character);
			}
			else
			{
				effect.removeFrom(character);
				effectsToRemove.add(effect);
			}
		}
		this.removeAll(effectsToRemove);
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.data.save.SaveElement#getSave(org.w3c.dom.Document)
	 */
	public Element getSave(Document doc)
	{
	    Element effectsE = doc.createElement("effects");
	    for(Effect effect : this)
	    {
	        Element effectE = doc.createElement("effect");
	        effectE.setAttribute("duration", effect.getTime()+"");
	        effectE.setTextContent(effect.getId());
	        effectsE.appendChild(effectE);
	    }
	    return effectsE;
	}
}
