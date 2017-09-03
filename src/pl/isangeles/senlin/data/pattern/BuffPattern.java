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
 * Class for buff patterns
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
    private final List<Bonus> bonuses;
    private final List<String> effects;
    private final Requirements trainReq;
    /**
     * Buff pattern constructor
     * @param id Buff ID
     * @param imgName Buff icon image name
     * @param effect Buff effects type
     * @param type Buff type
     * @param useReqs Requirements to use
     * @param cooldown Cooldown time in seconds
     * @param castTime Casting time in seconds
     * @param range Maximal range form buff target
     * @param duration Buff duration time in seconds
     * @param bonuses Buff bonuses
     * @param effects Buff effects
     * @param trainReq Buff training requirements
     */
	public BuffPattern(String id, String imgName, String effect, String type, List<Requirement> useReqs, int cooldown, int castTime, int range, 
					   List<Bonus>bonuses, List<String> effects, List<Requirement> trainReq)
	{
		this.id = id;
		this.imgName = imgName;
		this.effect = EffectType.fromString(effect);
		this.type = BuffType.fromString(type);
		this.useReqs = new Requirements();
		this.useReqs.addAll(useReqs);
		this.cooldown = cooldown*1000;
		this.castTime = castTime*1000;
		this.range = range;
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
	/**
	 * Builds buff from this pattern
	 * @param character Game character for skill
	 * @param gc Slick game container
	 * @return New instance of buff from this pattern
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Buff make(Character character, GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		return new Buff(character, id, imgName, effect, type, useReqs, castTime, range, cooldown, bonuses, effects, gc);
	}

}
