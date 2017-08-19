/*
 * BuffPattern.java
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
package pl.isangeles.senlin.data.pattern;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.effect.EffectType;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.core.req.Requirements;
import pl.isangeles.senlin.core.skill.Buff;
import pl.isangeles.senlin.core.skill.BuffType;
import pl.isangeles.senlin.core.bonus.Bonus;
import pl.isangeles.senlin.core.character.Character;

/**
 * @author Isangeles
 *
 */
public class BuffPattern implements SkillPattern 
{
	private final String id;
    private final String imgName;
    private final EffectType effect;
    private final BuffType type;
    private final Requirements useReqs;
    private final int cooldown;
    private final int castTime;
    private final int range;
    private final int duration;
    private final List<Bonus> bonuses;
    private final List<String> effects;
    private final Requirements trainReq;
    
	public BuffPattern(String id, String imgName, String effect, String type, List<Requirement> useReqs, int cooldown, int castTime, int range, int duration, 
					   List<Bonus>bonuses, List<String> effects, List<Requirement> trainReq)
	{
		this.id = id;
		this.imgName = imgName;
		this.effect = EffectType.fromString(effect);
		this.type = BuffType.fromString(type);
		this.useReqs = new Requirements();
		this.useReqs.addAll(useReqs);
		this.cooldown = cooldown;
		this.castTime = castTime;
		this.range = range;
		this.duration = duration;
		this.bonuses = bonuses;
		this.effects = effects;
		this.trainReq = new Requirements();
		this.trainReq.addAll(trainReq);
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.data.pattern.SkillPattern#getRequirements()
	 */
	@Override
	public List<Requirement> getRequirements() 
	{
		return trainReq;
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.data.pattern.SkillPattern#getId()
	 */
	@Override
	public String getId() 
	{
		return id;
	}
	
	public Buff make(Character character, GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		return new Buff(character, id, imgName, effect, type, useReqs, castTime, range, cooldown, duration, bonuses, effects, gc);
	}

}
