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
    private String effectId;
    /**
     * Default effect action constructor(action from this constructor do nothing)
     */
    public EffectAction()
    {
        super();
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
        
        effectId = effectOnActionId;
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
        switch(type)
        {
        case EFFECTUSER:
        	user.getEffects().addAll(getEffects());
            return true;
        case EFFECTTARGET:
        	target.getEffects().addAll(getEffects());
            return true;
        default:
            return true;
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
		return "effectAction";
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.effect.EffectSource#getOwner()
	 */
	@Override
	public Targetable getOwner() 
	{
		return null;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.effect.EffectSource#getEffects()
	 */
	@Override
	public Collection<Effect> getEffects()
	{
		List<Effect> effects = new ArrayList<>();
		effects.add(EffectsBase.getEffect(this, effectId));
		return effects;
	}
}
