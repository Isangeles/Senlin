/*
 * MobsArea.java
 * 
 * Copyright 2017-2018 Dariusz Sikora <darek@pc-solus>
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

import pl.isangeles.senlin.data.NpcBase;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.util.TilePosition;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.character.Character;
/**
 * Class for areas with mobs
 * @author Isangelse
 *
 */
public class MobsArea 
{
	private TilePosition startPoint;
	private TilePosition endPoint;
	private Map<String, Integer[]> mobs;
	private List<Character> spawnedMobs = new ArrayList<>();
	private boolean respawnable;
	/**
	 * Mobs area constructor 
	 * @param startPoint XY starting point of area
	 * @param endPoint XY ending point of area
	 * @param mobs Map with characters IDs as keys and its max amount in area as values
	 */
	public MobsArea(TilePosition startPoint, TilePosition endPoint, Map<String, Integer[]> mobs, boolean respawnable) 
	{
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.mobs = mobs;
		this.respawnable = respawnable;
	}
	/**
	 * Spawns all mobs in area to list
	 * @return List with spawned mobs
	 * @throws IOException
	 * @throws FontFormatException
	 * @throws SlickException
	 */
	public List<Character> spawnMobs(Area area) throws IOException, FontFormatException, SlickException, ArrayIndexOutOfBoundsException
	{
		clearDeadMobs();
		
		//List<Character> mobsList = new ArrayList<>();
		Random rng = new Random();
		
		for(String mobId : mobs.keySet())
		{
			int min = mobs.get(mobId)[0];
			int max = mobs.get(mobId)[1];
			
			for(int i = spawnedMobs.size(); i <  min + rng.nextInt(max); i ++)
			{
				TilePosition mobTile = new TilePosition(startPoint.row + rng.nextInt(endPoint.row), startPoint.column + rng.nextInt(endPoint.column));
				Position mobPostion = mobTile.asPosition();
				while(!area.isMovable(mobTile.row*TilePosition.TILE_WIDTH, mobTile.column*TilePosition.TILE_HEIGHT)) //TODO possibility of infinite loop if all area won't be 'moveable' 
				{
					mobTile = new TilePosition(startPoint.row + rng.nextInt(endPoint.row), startPoint.column + rng.nextInt(endPoint.column));
				}
				
				Character mob = NpcBase.spawnIn(mobId, area, mobTile);
				mob.setDefaultPosition(mobTile);
				if(mob != null)
				{
					//mobsList.add(mob);
					spawnedMobs.add(mob);
					//Log.addSystem(mobId + " spawned"); //DEBUG
				}
			}
		}
		return spawnedMobs;
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
	 * Clears all dead mobs from list with spawned mobs
	 * @return True if all dead mobs was successfully removed, false otherwise
	 */
	private boolean clearDeadMobs()
	{
		List<Character> deadMobs = new ArrayList<>();
		for(Character mob : spawnedMobs)
		{
			if(!mob.isLive())
				deadMobs.add(mob);
		}
		return spawnedMobs.removeAll(deadMobs);
	}
}
