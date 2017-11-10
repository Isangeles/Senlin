/*
 * Buff.java
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
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.bonus.Bonus;
import pl.isangeles.senlin.core.bonus.Bonuses;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.effect.EffectType;
import pl.isangeles.senlin.core.effect.Effects;
import pl.isangeles.senlin.core.out.CharacterOut;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.data.EffectsBase;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.graphic.AvatarAnimType;
import pl.isangeles.senlin.gui.tools.EffectTile;
import pl.isangeles.senlin.util.Settings;
import pl.isangeles.senlin.util.TConnector;
/**
 * Class for buffs
 * @author Isangeles
 *
 */
public class Buff extends Skill
{
	private BuffType type;
    private int range;
    /**
     * Buff constructor
     * @param character Game character (skill owner)
     * @param id Skill ID
     * @param imgName Skill icon image
     * @param effectType Effect type
     * @param type Buff type
     * @param reqs Requirements to use
     * @param castTime Casting time
     * @param range Maximal range form target
     * @param cooldown Skill cooldown time in milliseconds
     * @param duration Buff duration time in milliseconds
     * @param bonuses Buff bonuses
     * @param effects Skill effects
     * @param gc Slick game container
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
	public Buff(Character character, String id, String imgName, EffectType effectType, BuffType type, List<Requirement> reqs, int castTime, int range, int cooldown, 
	            List<String> effects, GameContainer gc) throws SlickException, IOException, FontFormatException 
	{
		super(character, id, imgName, effectType, reqs, castTime, cooldown, effects);
		this.type = type;
		
		avatarAnim = AvatarAnimType.CAST;
		setTile(gc);
		setGraphicEffects(gc);
		setSoundEffect();
	}
	
	@Override
	public void update(int delta)
	{
		super.update(delta);
	}
	@Override
	public String getInfo() 
	{
	    String fullInfo = name + System.lineSeparator() +
	            TConnector.getText("ui", "eleTInfo") + ":" + getTypeString() + System.lineSeparator() + 
                TConnector.getText("ui", "rangeName") + ":" + range + System.lineSeparator() +
                TConnector.getText("ui", "castName") + ":" + getCastTime()/1000 + System.lineSeparator() + 
                TConnector.getText("ui", "cdName") + ":" + cooldown/1000  + System.lineSeparator();
	    for(Requirement req : useReqs)
        {
            if(!req.getInfo().equals(""))
                fullInfo += req.getInfo() + System.lineSeparator();
        }
        fullInfo += info;

	    return fullInfo;
	}
	/**
	 * Check if buff is active
	 * @return
	 */
	public boolean isActive()
	{
		return active;
	}
    @Override
    public void activate()
    {
    	if(active)
    	{
    		target.getDefense().handleSkill(this);
    		castSound.stop();
    		deactivate();
    	}
    }

    @Override
    public CharacterOut prepare(Character user, Targetable target)
    {
        if(isReady() && useReqs.isMetBy(user))
        {
        	if(type == BuffType.ONTARGET && target == null)
        		return CharacterOut.NOTARGET;
        	
        	if(type.useTarget() && target != null)
        	{
        		if(user.getRangeFrom(target) <= range)
        		{
        			this.target = target;
        			active = true;
        			ready = false;
        			castSound.loop(1.0f, Settings.getEffectsVol());
        			return CharacterOut.SUCCESS;
        		}
            	else
            	{
            		user.moveTo(target, range);
            		return CharacterOut.NORANGE;
            	}
        	}
        	else if(type.useUser())
        	{
        		this.target = user;
    			active = true;
    			ready = false;
    			castSound.loop(1.0f, Settings.getEffectsVol());
    			return CharacterOut.SUCCESS;
        	}
        	else
        		return CharacterOut.UNKNOWN;
        }
        else
        	return CharacterOut.NOTREADY;
    }
    /**
     * Deactivates buff
     */
    private void deactivate()
    {
    	target = null;
        active = false;
    }
}
