/*
 * CharacterAi.java
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
package pl.isangeles.senlin.core.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.character.Attitude;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.character.Race;
import pl.isangeles.senlin.core.signal.CharacterSignal;
import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.data.area.Area;
import pl.isangeles.senlin.states.GameWorld;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.TConnector;
/**
 * Class for 'artificial intelligence' controlling game NPCs
 * @author Isangeles
 *
 */
public class CharacterAi 
{
	private List<Character> aiNpcs = new ArrayList<>();
	List<Character> deadNpcs = new ArrayList<>();
	List<Character> lostNpcs = new ArrayList<>();
	private Random rng = new Random();
	
	/**
	 * Characters AI constructor
	 */
	public CharacterAi() 
	{	
	}
	/**
	 * Updates all NPCs controlled by AI
	 * @param delta Time between updates
	 */
	public void update(int delta)
	{	
		for(Character npc : aiNpcs)
		{
			Area area = npc.getCurrentArea();
			if(area != null)
			{

				if(rng.nextInt(100) == 1)
				{
					moveAround(npc);
					if(npc.getRace() == Race.HUMAN)
						saySomething(npc, "idle", true);
				}
				
				Targetable target = npc.getTarget();
				if(target != null)
				{
					//checks if combat target is out of range
					if(npc.getSignals().get(CharacterSignal.FIGHTING) == target && !npc.isNearby(target))
					{
						npc.setTarget(null);
						npc.getSignals().stopCombat();
					}
				}
				
				List<Character> nearbyChars = area.getNearbyCharacters(npc);
				if(!nearbyChars.isEmpty())
				{
					for(Character nearbyChar : nearbyChars)
					{
						if(npc.getAttitudeTo(nearbyChar) == Attitude.HOSTILE || nearbyChar.getAttitudeTo(npc) == Attitude.HOSTILE)
						{
							attack(npc, nearbyChar);
							useSkill(npc);
						}
					}
				}

			}
			
			if(!npc.isLive())
				deadNpcs.add(npc);
			if(area == null)
				lostNpcs.add(npc);
		}
		
		aiNpcs.removeAll(deadNpcs);
		aiNpcs.removeAll(lostNpcs);
		lostNpcs.clear();
	}
	/**
	 * Puts specified NPCs under AI control
	 * @param npcs List with game characters
	 */
	public void addNpcs(Collection<Character> npcs)
	{
		aiNpcs.addAll(npcs);
	}
	/**
	 * Checks if any NPC attacks specified character
	 * @param character Game character
	 * @return True if any of AI NPCs attacks specified character, false otherwise
	 */
	public boolean isAttacked(Character character)
	{
		for(Character npc : aiNpcs)
		{
			if(npc.isFighting() && npc.getTarget() == character)
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * Moves NPC around 
	 * @param npc Character controlled by AI
	 */
	private void moveAround(Character npc)
	{
		if(npc.getTarget() == null)
		{
			switch(rng.nextInt(8))
			{
			case 0:
				if(npc.getAvatar().getDirection() != Coords.UP)
					npc.moveBy(0, -4);
				break;
			case 1:
				if(npc.getAvatar().getDirection() != Coords.RIGHT)
					npc.moveBy(4, 0);
				break;
			case 2:
				if(npc.getAvatar().getDirection() != Coords.DOWN)
					npc.moveBy(0, 4);
				break;
			case 3:
				if(npc.getAvatar().getDirection() != Coords.LEFT)
					npc.moveBy(-4, 0);
				break;
			default:
				return;
			}
		}
	}
	/**
	 * Attacks target of AI NPC
	 * @param agressor Character controlled by AI
	 * @param target Target of aggressor
	 */
	private void attack(Character aggressor, Targetable target)
	{
		if(aggressor.getTarget() == null && target.isLive())
		{
			aggressor.setTarget(target);
            aggressor.getSignals().startCombat(target);
            saySomething(aggressor, "aggressive", false);
		}
		else if(aggressor.getTarget() != null && !target.isLive())
		{
            aggressor.setTarget(null);
            aggressor.getSignals().stopCombat();
		}
	}
	/**
	 * Urges NPC to say something
	 * @param who Character controlled by AI
	 * @param what String with one of these categories: aggressive, friendly, idle
	 * @param random Determines whether speech should be random or not 
	 */
	private void saySomething(Character who, String what, boolean random)
	{
	    if(random)
	    {
	        if(rng.nextInt(10) == 1)
	            who.speak(TConnector.getRanomText(who.getRace().getRandomTextId(what)));
	    }
	    else
	        who.speak(TConnector.getRanomText(who.getRace().getRandomTextId(what)));
	}
	/**
	 * Uses random skill of specified character
	 * @param npc Game character
	 */
	private void useSkill(Character npc)
	{
		Targetable target = npc.getTarget(); 
		if(target != null)
		{
			if(Character.class.isInstance(target))
			{
				Character character = (Character)npc;
				if(character.getAttitudeTo(npc) == Attitude.HOSTILE)
				{
					List<Attack> attackSkills = npc.getSkills().getAttacks();
					Attack skill = attackSkills.get(rng.nextInt(attackSkills.size()));
					npc.useSkillOn(target, skill);
				}
			}
		}
	}
}
