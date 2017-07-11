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

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Effect;
import pl.isangeles.senlin.core.EffectType;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.exc.GameLogErr;
import pl.isangeles.senlin.core.exc.NoRangeInfo;
import pl.isangeles.senlin.core.exc.NoTargetInfo;
import pl.isangeles.senlin.core.exc.NotReadyInfo;
import pl.isangeles.senlin.core.item.WeaponType;
import pl.isangeles.senlin.data.EffectsBase;
import pl.isangeles.senlin.data.Log;
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
	private WeaponType reqWeapon;
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
	public Attack(Character character, String id, String name, String info, String imgName, EffectType type, int damage, int magickaCost,
				  int castTime, int cooldown, boolean useWeapon, WeaponType reqWeapon, int range, List<Effect> effects, GameContainer gc)
			throws SlickException, IOException, FontFormatException 
	{
		super(character, id, name, info, imgName, type, magickaCost, castTime, cooldown, useWeapon, effects);
		this.damage = damage;
		this.range = range;
		this.reqWeapon = reqWeapon;
		setTile(gc);
		setSoundEffect();
	}

	@Override
	public String getInfo() 
	{
		String fullInfo = name + System.lineSeparator() + TConnector.getText("ui", "eleTInfo") + ":" + getTypeString() + System.lineSeparator() +  
						  TConnector.getText("ui", "dmgName") + ":" + damage + System.lineSeparator() + 
						  TConnector.getText("ui", "rangeName") + ":" + range + System.lineSeparator() +
						  TConnector.getText("ui", "castName") + ":" + getCastSpeed() + System.lineSeparator() + 
						  TConnector.getText("ui", "cdName") + ":" + cooldown/1000 + " sec"  + System.lineSeparator() + 
						  info;
		
		return fullInfo;
	}
	@Override
	public boolean prepare(Character user, Targetable target) throws GameLogErr
	{
		if(super.isActive() && weaponOk(user))
		{
			if(target != null)
			{
				//Log.addInformation("Range: " + owner.getRangeFrom(target.getPosition()) + " maxRange: " + range); //TEST LINE
				if(owner.getRangeFrom(target.getPosition()) <= range)
				{
				    this.target = target;
				    ready = true;
				    active = false;
		            playSoundEffect();
				    return true;
				}
				else
				{
	                user.moveTo(target, range);
					throw new NoRangeInfo();
				}
			}
			else
				throw new NoTargetInfo();
		}
		else
			throw new NotReadyInfo();
	}
    /**
     * Activates attack skill
     */
	@Override
	public void activate()
	{
	    if(ready)
	    {
	        owner.takeMagicka(magickaCost);
	        
	        List<Effect> effectsToPass = new ArrayList<>();
	        if(effects != null)
	        {
		        for(Effect effect : effects)
		        {
		        	effectsToPass.add(EffectsBase.getEffect(effect.getId()));
		        }
	        }
	        target.takeAttack(owner, owner.getHit()+damage, effectsToPass);
	        ready = false;
	    }
	}
	
	private boolean weaponOk(Character user)
	{
		if(useWeapon)
		{
			if(user.getInventory().getMainWeapon() != null && user.getInventory().getMainWeapon().getType() == reqWeapon.getId())
				return true;
			else
				return false;
		}
		else
			return true;
	}
}
