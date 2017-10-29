/*
 * PassivePattern.java
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

import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.effect.EffectType;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.core.req.Requirements;
import pl.isangeles.senlin.core.skill.Passive;
import pl.isangeles.senlin.core.skill.PassiveType;

/**
 * Class for passive skills patterns
 * @author Isangeles
 *
 */
public class PassivePattern implements SkillPattern 
{
	private final String id;
    private final String imgName;
    private final EffectType effect;
    private final PassiveType type;
    private final Requirements useReqs;
    private final List<String> effects;
    private final Requirements trainReq;
    /**
     * Passive skill pattern constructor 
     * @param id
     * @param iconName
     * @param effectType
     * @param skillType
     * @param reqs
     * @param effectsIDs
     * @param trainReqs
     */
    public PassivePattern(String id, String iconName, String effectType, String skillType, List<Requirement> reqs, List<String> effectsIDs, List<Requirement> trainReqs)
    {
    	this.id = id;
    	this.imgName = iconName;
    	this.effect = EffectType.fromId(effectType);
    	this.type = PassiveType.fromName(skillType);
    	this.useReqs = new Requirements();
    	this.useReqs.addAll(reqs);
    	this.effects = effectsIDs;
    	this.trainReq = new Requirements();
    	this.trainReq.addAll(trainReqs);
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
	 * Returns new instance of passive skill from this pattern
	 * @param owner Skill owner
	 * @param gc Slick game container
	 * @return New instance of passive skill from this pattern
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Passive make(Character owner, GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		return new Passive(owner, id, imgName, type, effect, useReqs, effects, gc);
	}
}
