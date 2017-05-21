package pl.isangeles.senlin.data.area;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.data.NpcBase;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.core.Character;
/**
 * Class for areas with mobs
 * @author Isangelse
 *
 */
public class MobsArea 
{
	private Position startPoint;
	private Position endPoint;
	private Map<String, Integer> mobs;
	/**
	 * Mobs area constructor 
	 * @param startPoint XY starting point of area
	 * @param endPoint XY ending point of area
	 * @param mobs Map with characters IDs as keys and its max amount in area as values
	 */
	public MobsArea(Position startPoint, Position endPoint, Map<String, Integer> mobs) 
	{
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.mobs = mobs;
	}
	/**
	 * Spawns all mobs in area to list
	 * @return List with spawned mobs
	 * @throws IOException
	 * @throws FontFormatException
	 * @throws SlickException
	 */
	public List<Character> spawnMobs() throws IOException, FontFormatException, SlickException
	{
		List<Character> mobsList = new ArrayList<>();
		Random rng = new Random();
		
		for(String mobId : mobs.keySet())
		{
			for(int i = 0; i <  1 + rng.nextInt(mobs.get(mobId)); i ++)
			{
				Position mobPos = new Position(startPoint.x + rng.nextInt(endPoint.x), startPoint.y + rng.nextInt(endPoint.y));
				mobsList.add(NpcBase.spawnAt(mobId, mobPos));
				Log.addSystem(mobId + " spawned");
			}
		}
		
		return mobsList;
	}

}
