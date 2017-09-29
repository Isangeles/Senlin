/*
 * Passive.java
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
import java.io.IOException;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.effect.EffectType;
import pl.isangeles.senlin.core.out.CharacterOut;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.util.TConnector;
/**
 * Class for passive skills
 * @author Isangeles
 *
 */
public class Passive extends Skill 
{
    private PassiveType passType;
    /**
     * Passive constructor
     * @param character Skill owner
     * @param id Skill ID
     * @param imgName Skill icon image name
     * @param skillType Passive type
     * @param type Effect type
     * @param reqs Use requirements
     * @param effects Skill activation effects
     * @param gc Slick game container
     * @throws IOException 
     * @throws SlickException 
     * @throws FontFormatException 
     */
	public Passive(Character character, String id, String imgName, PassiveType skillType, EffectType type, List<Requirement> reqs, List<String> effects, GameContainer gc)
			throws SlickException, IOException, FontFormatException 
	{
		super(character, id, imgName, type, reqs, 0, 0, effects);
		passType = skillType;
		if(passType == PassiveType.ALWEYSON) 
		{
			target = owner;
			activate();
		}
		setTile(gc);
		setGraphicEffects(gc);
		setSoundEffect();
	}

	@Override
	protected String getInfo() 
	{
		String fullInfo = name + System.lineSeparator() + TConnector.getText("ui", "eleTInfo") + ":" + getTypeString();

	    fullInfo += System.lineSeparator() + info;

	    return fullInfo;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.skill.Skill#activate()
	 */
	@Override
	public void activate() 
	{
		active = true;
		target.takePassvie(owner, this);
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.skill.Skill#prepare(pl.isangeles.senlin.core.character.Character, pl.isangeles.senlin.core.Targetable)
	 */
	@Override
	public CharacterOut prepare(Character user, Targetable target) 
	{
		if(user == target)
		{
			if(passType == PassiveType.SWITCH)
			{
				if(active)
				{
					deactivate();
				}
				else
				{
					activate();
				}
			}
			return CharacterOut.SUCCESS;
		}
		else
			return CharacterOut.UNABLE;
	}
	
	public void deactivate()
	{
		active = false;
	}

}
