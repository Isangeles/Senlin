/*
 * Requirements.java
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
package pl.isangeles.senlin.core.req;

import java.util.ArrayList;
import java.util.List;

import pl.isangeles.senlin.core.character.Character;

/**
 * Container class for requirements
 * @author Isangeles
 *
 */
public class Requirements extends ArrayList<Requirement>
{
    private static final long serialVersionUID = 1L;
    
    public Requirements() {}
    
    public Requirements(List<Requirement> reqsList)
    {
    	super();
    	this.addAll(reqsList);
    }
    /**
     * Checks if all requirements are met by specified game character
     * @param character Game character
     * @return True if all requirement are met, false otherwise
     */
    public boolean isMetBy(Character character)
    {
        boolean isMet = true;
        for(Requirement req : this)
        {
            if(!req.isMetBy(character))
            {
                isMet = false;
            }
        }
        return isMet;
    }
    /**
     * Takes all 'chargeable' requirements from specified character
     * @param character Game character
     */
    public void chargeAll(Character character)
    {
        for(Requirement req : this)
        {
            req.charge(character);
        }
    }
    
    public boolean isEmpty()
    {
    	if(super.isEmpty())
    		return true;
    	else
    	{
    		for(Requirement req : this)
    		{
    			if(req.getType() != RequirementType.NONE)
    				return false;
    		}
    		return true;
    	}
    }
}
