/*
 * SpawnArea.java
 * 
 * Copyright 2018 Dariusz Sikora <dev@isangeles.pl>
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

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.util.TilePosition;

/**
 * Abstract class for spawn areas
 * @author Isangeles
 *
 */
public abstract class SpawnArea 
{
	protected TilePosition startPoint;
	protected TilePosition endPoint;
	protected Map<String, Integer[]> objects;
	protected List<Targetable> spawnedObjects = new ArrayList<>();
	protected boolean respawnable;
	/**
	 * Spawn area constructor 
	 * @param startPoint XY starting point of area
	 * @param endPoint XY ending point of area
	 * @param mobs Map with objects IDs as keys and its max amount in area as values
	 * @param respawnable True if all object from are should be respawned at game world update
	 */
	public SpawnArea(TilePosition startPoint, TilePosition endPoint, Map<String, Integer[]> objects, boolean respawnable) 
	{
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.objects= objects;
		this.respawnable = respawnable;
	}
	/**
	 * Checks if this area is 'respawnable'
	 * @return True if area is 'respawnable', false otherwise
	 */
	public boolean isRespawnable()
	{
		return respawnable;
	}
	/**
	 * Spawns all objects in specified area to list
	 * @param area Scenario area
	 * @return True if spawned mobs was successfully added to area, false otherwise
	 * @throws IOException
	 * @throws FontFormatException
	 * @throws SlickException
	 */
	public abstract boolean spawn(Area area) throws IOException, FontFormatException, SlickException, ArrayIndexOutOfBoundsException;
	/**
	 * Clears all unnecessary objects from list with spawned objects
	 * @return True if all unnecessary objects was successfully removed, false otherwise
	 */
	protected abstract boolean clearObjects();
}
