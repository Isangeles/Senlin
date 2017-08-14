/*
 * AttackPattern.java
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

import pl.isangeles.senlin.core.Attributes;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.effect.EffectType;
import pl.isangeles.senlin.core.item.WeaponType;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.util.TConnector;
/**
 * Pattern for attack skills
 * @author Isangeles
 *
 */
public class AttackPattern implements SkillPattern
{
    private final String id;
    private final EffectType type;
    private final List<Requirement> useReqs;
    private final int cooldown;
    private final int castTime;
    private final String imgName;
    private final int damage;
    private final int range;
    private final List<Effect> effects;
    private final List<Requirement> skillReq;
    /**
     * 
     * Attack pattern constructor
     * @param id Skill ID
     * @param imgName Skill UI icon
     * @param type Skill type
     * @param damage Attack damage
     * @param magickaCost Skill magicka cost
     * @param castTime Skill cast time
     * @param cooldown Skill cooldown
     * @param useWeapon If weapon is required to use that skill 
     * @param reqWeapon Type of required weapon
     * @param range Maximal range from target
     * @param effects Skill use effects
     */
    public AttackPattern(String id, String imgName, String type, int damage, List<Requirement> reqs, int castTime, int cooldown, int range, List<Effect> effects, List<Requirement> skillReq)
    {
        this.type = EffectType.fromString(type);
        this.id = id;
        this.imgName = imgName;
        this.useReqs = reqs;
        this.castTime = castTime*1000;
        this.cooldown = cooldown*1000;
        this.damage = damage;
        this.range = range;
        this.effects = effects;
        this.skillReq = skillReq;
    }
    /**
     * Creates attack from this pattern
     * @param character Game character, skill owner
     * @param gc Slick game container
     * @return New attack object assigned to specified character
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    public Attack make(Character character, GameContainer gc) throws SlickException, IOException, FontFormatException
    {
        return new Attack(character, id, imgName, type, damage, useReqs, castTime, cooldown, range, effects, gc);
    }

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.data.pattern.SkillPattern#getId()
     */
    @Override
    public String getId()
    {
        return id;
    }
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.data.pattern.SkillPattern#getRequirements()
	 */
	@Override
	public List<Requirement> getRequirements() 
	{
		return skillReq;
	}
}
