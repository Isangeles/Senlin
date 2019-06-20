/*
 * EffectAction.java
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
package pl.isangeles.senlin.core.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.effect.EffectSource;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.data.EffectsBase;

/**
 * Class for action causing specified effect on user/target
 * @author Isangeles
 *
 */
public class EffectAction extends Action implements EffectSource
{
	private static int counter;
	private final int id = counter++;
    private final List<String> effectsIds;
    private int antiLoopCounter;
    /**
     * Default effect action constructor(action from this constructor do nothing)
     */
    public EffectAction()
    {
        super();
        effectsIds = new ArrayList<>();
    }
    /**
     * Constructor for action causing specified effect on user/target
     * @param effectOnAction Effect of action
     * @param target String with 'user' for effect on user, 'target' for effect on target
     */
    public EffectAction(String effectOnActionId, String target)
    {
        if(target.equals("user"))
            type = ActionType.EFFECTUSER;
        if(target.equals("target"))
            type = ActionType.EFFECTTARGET;
        
        effectsIds = new ArrayList<>();
        effectsIds.add(effectOnActionId);
    }
    /**
     * Starts action
     * @param user Action user
     * @param target User target 
     * @return 
     */
    @Override
    public boolean start(Targetable user, Targetable target)
    {
    	lastUser = user;
        switch(type)
        {
        case EFFECTUSER:
        	Log.addSystem("adding effects");
        	user.getDefense().handleEffects(getEffects());
            return true;
        case EFFECTTARGET:
        	if(target != null) 
        	{
        		user.getDefense().handleEffects(getEffects());
            	return true;
            }
        default:
            return false;
        }
    }
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.effect.EffectSource#getId()
	 */
	@Override
	public String getId() 
	{
		return "effectAction";
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.effect.EffectSource#getSerialId()
	 */
	@Override
	public String getSerialId() 
	{
		return "effectAction_" + id;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.effect.EffectSource#getOwner()
	 */
	@Override
	public Targetable getOwner() 
	{
		return lastUser;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.effect.EffectSource#getEffects()
	 */
	@Override
	public Collection<Effect> getEffects()
	{
		List<Effect> effectsToPass = new ArrayList<>();
		
		// TODO interestingly, after starting action building effects cause infinite loop, this is some fix for that 
		antiLoopCounter ++;
		if(antiLoopCounter > 1) 
		{
			antiLoopCounter = 0;
			return effectsToPass;
		}
		//
		
		for(String effectId : effectsIds)
        {
        	effectsToPass.add(EffectsBase.getEffect(this, effectId));
        }
		/* DEBUG
		try
		{
			throw new Exception();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		*/
        return effectsToPass;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.effect.EffectSource#getEffect(java.lang.String)
	 */
	@Override
	public Effect getEffect(String effectId) 
	{
		for(String id : this.effectsIds)
		{
			if(id.equals(effectId))
				return EffectsBase.getEffect(this, id);
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.effect.EffectSource#getEffectsIds()
	 */
	@Override
	public List<String> getEffectsIds() 
	{
		return effectsIds;
	}
}
