/*
 * Attack.java
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

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.effect.EffectType;
import pl.isangeles.senlin.core.item.WeaponType;
import pl.isangeles.senlin.core.out.CharacterOut;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.core.req.WeaponRequirement;
import pl.isangeles.senlin.data.EffectsBase;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.TConnector;
/**
 * Class for offensive skills
 * @author Isangeles
 *
 */
public class Attack extends Skill 
{
	private int damage;
	private int range;
    protected boolean useWeapon;
	/**
	 * Attack constructor
	 * @param character Skill owner
	 * @param id Skill ID
	 * @param name Skill Name
	 * @param info Skill description
	 * @param imgName Skill icon file
	 * @param damage Damage dealt on target
	 * @param magickaCost Magicka cost of use, determines whether skill is magic or not
	 * @param castTime Cast time
	 * @param cooldown Time that must be waited after skill use
	 * @param range Required range
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Attack(Character character, String id, String imgName, EffectType type, int damage, List<Requirement> reqs, int castTime, int cooldown, int range, 
	              List<String> effects, GameContainer gc)
			throws SlickException, IOException, FontFormatException 
	{
		super(character, id, imgName, type, reqs, castTime, cooldown, effects);
		this.damage = damage;
		this.range = range;
		for(Requirement req : useReqs)
		{
		    if(WeaponRequirement.class.isInstance(req))
		    {
		        useWeapon = true;
		        break;
		    }
		}
		setTile(gc);
		setSoundEffect();
	}
	
	public int getDamage()
	{
		return damage + owner.getHit();
	}
	
	public List<Effect> getEffects()
	{
		List<Effect> effectsToPass = new ArrayList<>();
        if(effects != null)
        {
	        for(String effectId : effects)
	        {
	        	effectsToPass.add(EffectsBase.getEffect(effectId));
	        }
        }
        return effectsToPass;
	}

	@Override
	public String getInfo() 
	{
		String fullInfo = name + System.lineSeparator() + TConnector.getText("ui", "eleTInfo") + ":" + getTypeString() + System.lineSeparator() +  
						  TConnector.getText("ui", "dmgName") + ":" + damage + System.lineSeparator() + 
						  TConnector.getText("ui", "rangeName") + ":" + range + System.lineSeparator() +
						  TConnector.getText("ui", "castName") + ":" + getCastTime() + System.lineSeparator() + 
						  TConnector.getText("ui", "cdName") + ":" + cooldown/1000 + " sec"  + System.lineSeparator() + 
						  info;
		
		return fullInfo;
	}
	@Override
	public CharacterOut prepare(Character user, Targetable target)
	{
		if(super.isReady())
		{
			if(target != null && useReqs.isMetBy(user))
			{
				//Log.addInformation("Range: " + owner.getRangeFrom(target.getPosition()) + " maxRange: " + range); //TEST LINE
				if(owner.getRangeFrom(target.getPosition()) <= range)
				{
				    this.target = target;
				    active = true;
				    ready = false;
				    return CharacterOut.SUCCESS;
				}
				else
				{
	                user.moveTo(target, range);
	                return CharacterOut.NORANGE;
				}
			}
			else
				return CharacterOut.NOTARGET;
		}
		else
		    return CharacterOut.NOTREADY;
	}
    /**
     * Activates attack skill
     */
	@Override
	public void activate()
	{
	    if(active)
	    {
	        useReqs.chargeAll(owner);
	        target.takeAttack(owner, this);
            playSoundEffect();
	        active = false;
	    }
	}
}
