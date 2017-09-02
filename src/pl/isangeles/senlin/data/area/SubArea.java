/*
 * SubArea.java
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
package pl.isangeles.senlin.data.area;

import java.util.List;

import org.newdawn.slick.tiled.TiledMap;

import pl.isangeles.senlin.core.SimpleGameObject;
import pl.isangeles.senlin.core.character.Character;
/**
 * Class for sub areas
 * @author Isangeles
 *
 */
public class SubArea
{
    private TiledMap map;
    private List<Character> npcs;
    private List<SimpleGameObject> objects;
    private List<Exit> exits;
    /**
     * Sub area constructor
     * @param npcs List with NPCs
     * @param objects List with objects
     * @param exits List exits
     */
    public SubArea(TiledMap map, List<Character> npcs, List<SimpleGameObject> objects, List<Exit> exits)
    {
        this.map = map;
        this.npcs = npcs;
        this.objects = objects;
        this.exits = exits;
    }
}
