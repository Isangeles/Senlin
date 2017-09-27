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

import pl.isangeles.senlin.core.skill.Skill;

/**
 * Class for skill casting
 * @author Isangeles
 *
 */
public class SkillCaster 
{
	private Skill skillToCast;
	private Character caster;
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
		time += delta;
		if(skillToCast != null && time >= skillToCast.getCastTime())
		{
			skillToCast.activate();
			reset();
		}
	}
	/**
	 * Casts specified skill
	 * @param skill Skill to cast
	 */
	public void cast(Skill skill)
	{
		reset();
		if(skill.isInstant())
		{
			skill.activate();
			return;
		}
		else
		{
			skillToCast = skill;
			casting = true;
		}
	}
	/**
	 * Resets caster
	 */
	public void reset()
	{
		skillToCast = null;
		time = 0;
		casting = false;
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
}
