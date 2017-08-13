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

import java.util.List;

import pl.isangeles.senlin.core.Attributes;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.req.Requirement;
/**
 * Interface for skill patterns
 * @author Isangeles
 *
 */
public interface SkillPattern
{   
    public List<Requirement> getRequirements();
    /**
     * Returns ID of skill from this pattern
     * @return String with skill ID
     */
    public String getId();
}
