/*
 * Recipe.java
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
package pl.isangeles.senlin.core.craft;

import java.util.List;

import pl.isangeles.senlin.core.item.Item;

/**
 * @author Isangeles
 *
 */
public class Recipe
{
    private final ProfessionType type;
    private final List<String> reagents;
    private final String result;
    
    public Recipe(ProfessionType type, List<String> reagents, String result)
    {
        this.type = type;
        this.reagents = reagents;
        this.result = result;
    }
}
