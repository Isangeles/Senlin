/*
 * ActionPattern.java
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

import pl.isangeles.senlin.core.action.Action;
import pl.isangeles.senlin.core.action.ActionType;
import pl.isangeles.senlin.core.action.EffectAction;
import pl.isangeles.senlin.core.action.ReadAction;
import pl.isangeles.senlin.data.EffectsBase;

/**
 * Class for action patterns
 * @author Isangeles
 *
 */
public class ActionPattern 
{
	private final ActionType type;
	private final String actionId;
	/**
	 * Action pattern constructor
	 * @param type Action type
	 * @param actionId Action ID, e.g. effect ID for effect action
	 */
	public ActionPattern(String type, String actionId)
	{
		this.type = ActionType.fromString(type);
		this.actionId = actionId;
	}
	/**
	 * Returns new instance of action from this pattern
	 * @return New action object
	 */
	public Action make()
	{
		switch(type)
		{
		case EFFECTUSER:
			return new EffectAction(EffectsBase.getEffect(actionId), "user");
		case EFFECTTARGET:
			return new EffectAction(EffectsBase.getEffect(actionId), "target");
		case READ:
			return new ReadAction(actionId);
		default:
			return new EffectAction();
		}
	}
}
