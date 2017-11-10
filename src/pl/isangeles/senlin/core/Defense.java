/*
 * Defense.java
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
package pl.isangeles.senlin.core;

import java.util.Collection;
import java.util.Random;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.character.Attitude;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.core.skill.Buff;
import pl.isangeles.senlin.core.skill.Passive;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for targetable objects defense
 * @author Isangeles
 *
 */
public class Defense 
{
	private final Targetable owner;
	private Random rng = new Random();
	/**
	 * Defense constructor
	 * @param object Targetable game object
	 */
	public Defense(Targetable object)
	{
		owner = object;
	}
	/**
	 * Handles skills used on owner
	 * @param skill Skill
	 */
	public void handleSkill(Skill skill)
	{
		Targetable sOwner = skill.getOwner();
		if(Attack.class.isInstance(skill))
		{
			if(Character.class.isInstance(owner) && Character.class.isInstance(sOwner))
				((Character)owner).memCharAs((Character)sOwner, Attitude.HOSTILE);
			
			Attack attack = (Attack)skill;
			if(rng.nextFloat()+owner.getAttributes().getDodge() >= 1f)
			{
				Log.addInformation(owner.getName() + ":" + TConnector.getText("ui", "logDodge"));
				return;
			}
			else
			{
				int damage = attack.getDamage() - owner.getInventory().getArmorRating() - owner.getAttributes().getResistances().getResistanceFor(attack.getEffectType());
				if(damage < 0)
					damage = 0;
				owner.takeHealth(skill.getOwner(), damage);
				handleEffects(attack.getEffects());
				if(sOwner.getInventory().getMainWeapon() != null)
				{
					handleEffects(skill.getOwner().getInventory().getMainWeapon().getHitEffects());
				}
				if(sOwner.getInventory().getOffHand() != null)
				{
					handleEffects(sOwner.getInventory().getOffHand().getHitEffects());
				}
			}
		}
		else if(Buff.class.isInstance(skill))
		{
			Buff buff = (Buff)skill;
			handleEffects(buff.getEffects());
		}
		else if(Passive.class.isInstance(skill))
		{
			Passive passive = (Passive)skill;
			handleEffects(passive.getEffects());
		}
	}
	/**
	 * Handles effects 
	 * @param effect Effect
	 */
	public void handleEffect(Effect effect)
	{
		if(owner.getAttributes().getResistances().getResistanceFor(effect.getType()) >= 100)
			owner.getEffects().add(effect);
	}
	/**
	 * Handles effects
	 * @param effects List with effects
	 */
	public void handleEffects(Collection<? extends Effect> effects)
	{
		for(Effect effect : effects)
		{
			handleEffect(effect);
		}
	}
}
