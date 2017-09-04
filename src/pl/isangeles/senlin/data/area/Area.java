/*
 * Area.java
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
 * Class for game world areas
 * @author Isangeles
 *
 */
public class Area
{
	private String id;
    private TiledMap map;
    private String mapFileName;
    private List<Character> npcs;
    private List<SimpleGameObject> objects;
    private List<Exit> exits;
    /**
     * Sub area constructor
     * @param npcs List with NPCs
     * @param objects List with objects
     * @param exits List exits
     */
    public Area(String id, TiledMap map, String mapFileName, List<Character> npcs, List<SimpleGameObject> objects, List<Exit> exits)
    {
    	this.id = id;
        this.map = map;
        this.mapFileName = mapFileName;
        this.npcs = npcs;
        this.objects = objects;
        this.exits = exits;
        
        for(Character npc : npcs)
        {
            npc.setMap(map);
        }
    }
    /**
     * Returns area ID
     * @return String with area ID
     */
    public String getId()
    {
    	return id;
    }
    /**
     * Returns area map
     * @return Tiled map of this area
     */
    public TiledMap getMap()
    {
    	return map;
    }
    /**
     * Returns map file name
     * @return String with map file name
     */
    public String getMapName()
    {
    	return mapFileName;
    }
    /**
     * Returns area NPCs
     * @return List with game characters
     */
    public List<Character> getNpcs()
    {
    	return npcs;
    }
    /**
     * Returns area objects
     * @return List with simple game objects
     */
    public List<SimpleGameObject> getObjects()
    {
    	return objects;
    }
    /**
     * Returns area exits
     * @return List with exits
     */
    public List<Exit> getExits()
    {
    	return exits;
    }
    /**
     * Sets specified list as this area NPCs list
     * @param charcters List with game characters for this area
     */
    public void setNpcs(List<Character> charcters)
    {
    	npcs = charcters;
    }
    /**
     * Sets specified list as this area objects list
     * @param objects List with simple game objects for this area
     */
    public void setObjects(List<SimpleGameObject> objects)
    {
    	this.objects = objects;
    }
}
