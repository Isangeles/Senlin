/*
 * SkillPattern.java
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
 * Interface for skill patterns
 * @author Isangeles
 *
 */
public interface SkillPattern
{
    /**
     * Checks if specified game character meets skill requirements
     * @param gameChar Game character
     * @return True if specified character meets skill requirements, false otherwise
     */
    public boolean isMeetReq(Character gameChar);
    /**
     * Returns required amount of gold to learn skill
     * @return Required gold value
     */
    public int getPrice();
    /**
     * Returns set of required attributes value
     * @return Attributes object with minimal required attributes values
     */
    public Attributes getReqAttributes();
    /**
     * Returns ID of skill from this pattern
     * @return String with skill ID
     */
    public String getId();
}
