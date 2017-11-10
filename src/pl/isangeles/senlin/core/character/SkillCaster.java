/*
 * SkillCaster.java
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
package pl.isangeles.senlin.core.character;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.skill.Passive;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.graphic.Effective;
import pl.isangeles.senlin.util.Settings;

/**
 * Class for skill casting
 * @author Isangeles
 *
 */
public class SkillCaster 
{
    private final Character caster;
	private Skill skillToCast;
	private boolean casting;
	private int time;
	/**
	 * Skill caster constructor
	 * @param character Game character
	 */
	public SkillCaster(Character character)
	{
		caster = character;
	}
	/**
	 * Updates skill caster
	 * @param delta Update delta
	 */
	public void update(int delta)
	{
		if(casting)
		{
			time += delta;
			if(skillToCast != null && time >= skillToCast.getCastTime())
			{
				activateSkill(skillToCast);
			}
		}
	}
	/**
	 * Casts specified skill
	 * @param skill Skill to cast
	 */
	public void cast(Skill skill)
	{
		if(casting) 
		{
			reset();
			caster.getAvatar().stopAnim();
		}

		if(skill.isInstant())
		{
			activateSkill(skill);
		}
		else
		{
			startCast(skill);
		}
	}
	/**
	 * Resets caster
	 */
	public void reset()
	{
        if(skillToCast != null)
        {
            skillToCast.reset();
            caster.getAvatar().removeEffect(skillToCast.getCastAnim());
            skillToCast.getCastSound().stop();
        }
        skillToCast = null;
		time = 0;
		casting = false;
		//caster.getAvatar().stopAnim();
	}
	/**
	 * Checks if skill is casted by caster
	 * @return True if skill is casted, false otherwise
	 */
	public boolean isCasting()
	{
		return casting;
	}
	/**
	 * Returns current cast time
	 * @return Time in milliseconds
	 */
	public int getTime()
	{
		return time;
	}
	/**
	 * Return current casted skill cast time
	 * @return Current skill cast time or 0 if nothing is casted
	 */ 
	public int getCastTime()
	{
		if(skillToCast != null)
		{
			return (int)skillToCast.getCastTime();
		}
		else
			return 0;
	}
	/**
	 * Starts casting specified skill
	 * @param skill Game skill
	 */
	private void startCast(Skill skill)
	{
		skillToCast = skill;
		casting = true;
		switch(skill.getAvatarAnimType())
		{
		case MELEE:
			caster.getAvatar().meleeAnim(true);
			break;
		case RANGE:
			caster.getAvatar().rangeAnim(true);
			break;
		case CAST:
			caster.getAvatar().castAnim(true);
			break;
		default:
			caster.getAvatar().meleeAnim(true);
		}
		caster.getAvatar().addEffect(skillToCast.getCastAnim(), true);
		skillToCast.getCastSound().loop(1.0f, Settings.getEffectsVol());
	}
	/**
	 * Activates specified skill
	 * @param skill Game skill
	 */
	private void activateSkill(Skill skill)
	{
		caster.getAvatar().stopAnim();
		switch(skill.getAvatarAnimType())
		{
		case MELEE:
			caster.getAvatar().meleeAnim(false);
			break;
		case RANGE:
			caster.getAvatar().rangeAnim(false);
			break;
		case CAST:
			caster.getAvatar().castAnim(false);
			break;
		default:
			caster.getAvatar().meleeAnim(false);
		}
		caster.getAvatar().removeEffect(skill.getCastAnim());
		skill.getCastSound().stop();
		skill.getActivateSound().play(1.0f, Settings.getEffectsVol());
		if(skill.getTarget() != null)
			skill.getTarget().getGEffectsTarget().addEffect(skill.getActiveAnim(), false);

		skill.activate();
		reset();
	}
}
