/*
 * Abilities.java
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
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.effect.Effect;
/**
 * Class for all skills acquired by character
 * @author Isangeles
 *
 */
public class Abilities extends LinkedList<Skill>
{
	private static final long serialVersionUID = 1L;

	private Character owner;
	/**
	 * Abilities constructor
	 * @param owner Abilities owner
	 */
	public Abilities(Character owner)
	{
		this.owner = owner;
	}
	/**
	 * Updates all skills
	 * @param delta Time from last update
	 */
	public void update(int delta)
	{
		for(Skill skill : this)
		{
			skill.update(delta);
			
			if(Passive.class.isInstance(skill))
			{
				if(skill.isActive())
				{
					/*
					for(Effect effect : skill.getEffects())
					{
						if(!owner.getEffects().hasEffect(effect))
							owner.getEffects().add(effect);
					}
					*/
				}
				else
				{
					owner.getEffects().removeAll(skill.getEffects());
				}
			}
			
		}
	}
	@Override
	public boolean add(Skill skill)
	{
		if(!isKnown(skill))
			return super.add(skill);
		else
			return false;
	}
	/**
	 * Returns skill with specified ID
	 * @param skillId String with desired skill ID
	 * @return Skill from character skills list
	 */
	public Skill get(String skillId)
	{
		for(Skill skill : this)
		{
			if(skill.getId().equals(skillId))
			{
				return skill;
			}
		}
		
		return null;
	}
	/**
	 * Returns all attacks from character skills list
	 * @return ArrayList with attacks
	 */
	public List<Attack> getAttacks()
	{
		List<Attack> attacks = new ArrayList<>();
		
		for(Skill skill : this)
		{
			if(Attack.class.isInstance(skill))
				attacks.add((Attack)skill);
		}
		
		return attacks;
	}
	/**
	 * Checks if skill with same ID as specified skill is already on skills list
	 * @param skill Some skill
	 * @return True if skill with same ID is already on list, false otherwise
	 */
	public boolean isKnown(Skill skill)
	{
		for(Skill tSkill : this)
		{
			if(tSkill.getId().equals(skill.getId()))
				return true;
		}
		
		return false;
	}
	/**
	 * Parses all skills to XML document element
	 * @param doc Document for game save file
	 * @return XML document element
	 */
	public Element getSave(Document doc)
	{	
		Element skillsE = doc.createElement("skills");
		for(Skill skill : this)
		{
			Element skillE = doc.createElement("skill");
			skillE.setTextContent(skill.getId());
			skillsE.appendChild(skillE);
		}
		
		return skillsE;
	}
}
