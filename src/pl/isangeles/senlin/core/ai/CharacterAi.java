/*
 * CharacterAi.java
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
package pl.isangeles.senlin.core.ai;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Attitude;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.exc.GameLogErr;
import pl.isangeles.senlin.states.GameWorld;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.TConnector;
/**
 * Class for artificial intelligence controlling game characters
 * @author Isangeles
 *
 */
public class CharacterAi 
{
	private List<Character> aiNpcs = new ArrayList<>();
	private Random rng = new Random();
	private int moveTimer;
	
	private GameWorld gw;
	/**
	 * Characters AI constructor
	 * @param gw Slick game container
	 */
	public CharacterAi(GameWorld gw) 
	{	
		this.gw = gw;
	}
	/**
	 * Updates all NPCs controlled by AI
	 * @param delta
	 */
	public void update(int delta)
	{
		List<Character> deadNpcs = new ArrayList<>();
		
		for(Character npc : aiNpcs)
		{
			if(rng.nextInt(100) == 1)
			{
				moveAround(npc);
                saySomething(npc, "idle", true);
			}
			
			for(Character nearbyChar : gw.getNearbyCharacters(npc))
			{
				if(npc.getAttitudeTo(nearbyChar) == Attitude.HOSTILE || nearbyChar.getAttitudeTo(npc) == Attitude.HOSTILE)
				{
					attack(npc, nearbyChar);
				}
			}
			try 
			{
				npc.update(delta, gw.getAreaMap());
			} 
			catch (GameLogErr e) 
			{
			}
			
			//Removing NPCs dynamically causes ConcurrentModificationException   
			if(!npc.isLive())
				deadNpcs.add(npc);
		}
		
		aiNpcs.removeAll(deadNpcs);
	}
	/**
	 * Puts specified NPCs under AI control
	 * @param npcs List with game characters
	 */
	public void addNpcs(List<Character> npcs)
	{
		aiNpcs.addAll(npcs);
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
					npc.move(0, -4);
				break;
			case 1:
				if(npc.getAvatar().getDirection() != Coords.RIGHT)
					npc.move(4, 0);
				break;
			case 2:
				if(npc.getAvatar().getDirection() != Coords.DOWN)
					npc.move(0, 4);
				break;
			case 3:
				if(npc.getAvatar().getDirection() != Coords.LEFT)
					npc.move(-4, 0);
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
            saySomething(aggressor, "aggressive", false);
		}
		else if(aggressor.getTarget() != null && !target.isLive())
            aggressor.setTarget(null);
		
		try 
		{
			aggressor.useSkill(aggressor.getSkills().get("autoA"));
		} 
		catch (GameLogErr e) 
		{
			
			return;
		}
	}
	/**
	 * Urges NPC to say something
	 * @param who Character controled by AI
	 * @param what String with one of these categories: aggressive, friendly, idle
	 * @param random Determines whether speech should be random or not 
	 */
	private void saySomething(Character who, String what, boolean random)
	{
	    if(random)
	    {
	        if(rng.nextInt(10) == 1)
	            who.speak(TConnector.getRanomText(what));
	    }
	    else
	        who.speak(TConnector.getRanomText(what));
	}
}
