/*
 * ObjectsArea.java
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
import java.util.Random;

import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.TargetableObject;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.data.NpcBase;
import pl.isangeles.senlin.data.ObjectsBase;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.util.TilePosition;

/**
 * Class for spawn areas with objects
 * @author Isangeles
 *
 */
public class ObjectsArea extends SpawnArea 
{
	/**
	 * Objects area constructor 
	 * @param startPoint XY starting point of area
	 * @param endPoint XY ending point of area
	 * @param objects Map with objects IDs as keys and its max amount in area as values
	 * @param respawnable True if all object from are should be respawned at game world update
	 */
	public ObjectsArea(TilePosition startPoint, TilePosition endPoint, Map<String, Integer[]> objects, boolean respawnable) 
	{
		super(startPoint, endPoint, objects, respawnable);
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.data.area.SpawnArea#spawn(pl.isangeles.senlin.data.area.Area)
	 */
	@Override
	public boolean spawn(Area area) throws IOException, FontFormatException, SlickException, ArrayIndexOutOfBoundsException 
	{
		clearObjects();
		
		//List<Character> mobsList = new ArrayList<>();
		Random rng = new Random();
		
		for(String objectId : objects.keySet())
		{
			int min = objects.get(objectId)[0];
			int max = objects.get(objectId)[1];
			
			for(int i = spawnedObjects.size(); i <  min + rng.nextInt(max); i ++)
			{
				TilePosition objectTile = new TilePosition(startPoint.row + rng.nextInt(endPoint.row), startPoint.column + rng.nextInt(endPoint.column));
				while(!area.isMovable(objectTile.row*TilePosition.TILE_WIDTH, objectTile.column*TilePosition.TILE_HEIGHT)) //TODO possibility of infinite loop if all area won't be 'moveable' 
				{
					objectTile = new TilePosition(startPoint.row + rng.nextInt(endPoint.row), startPoint.column + rng.nextInt(endPoint.column));
				}
				
				TargetableObject object = ObjectsBase.get(objectId);
				object.setPosition(objectTile);
				if(object != null)
				{
					spawnedObjects.add(object);
					Log.addSystem(objectId + "_spawned"); //DEBUG 
				}
			}
		}
		
		List<TargetableObject> tObjects = new ArrayList<>();
		for(Targetable o : spawnedObjects)
		{
			if(TargetableObject.class.isInstance(o))
			{
				TargetableObject tO = (TargetableObject)o;
				tObjects.add(tO);
			}
		}
		return area.getObjects().addAll(tObjects);
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.data.area.SpawnArea#clearObjects()
	 */
	@Override
	protected boolean clearObjects() 
	{
		// TODO Auto-generated method stub
		return false;
	}

}
