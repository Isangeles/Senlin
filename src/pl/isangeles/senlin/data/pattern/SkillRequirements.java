/*
 * SkillRequirements.java
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

import pl.isangeles.senlin.core.Attributes;
import pl.isangeles.senlin.core.Character;

/**
 * Tuple class for skill requirements
 * @author Isangeles
 *
 */
class SkillRequirements
{
    private int gold;
    private Attributes reqAtt;
    /**
     * SkillRequirements constructor 
     * @param gold Required amount of gold 
     * @param reqAtt Required attributes values
     */
    public SkillRequirements(int gold, Attributes reqAtt)
    {
        this.gold = gold;
        this.reqAtt = reqAtt;
    }
    /**
     * Checks if specified game character meets requirements
     * @param gameChar Game character
     * @return True if specified game character meets requirements, false otherwise
     */
    public boolean isMeetReq(Character gameChar)
    {
        if(gameChar.getInventory().getGold() < gold)
            return false;
        
        if(!gameChar.getAttributes().compareTo(reqAtt))
            return false;
        
        return true;
    }
    /**
     * Returns required amount of gold 
     * @return Required gold value
     */
    public int getPrice()
    {
        return gold;
    }
    /**
     * Returns set of required attributes value
     * @return Attributes object with minimal required attributes values
     */
    public Attributes getReqAttributes()
    {
        return reqAtt;
    }
}
