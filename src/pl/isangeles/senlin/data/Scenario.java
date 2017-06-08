package pl.isangeles.senlin.data;

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import pl.isangeles.senlin.data.area.MobsArea;
import pl.isangeles.senlin.quest.Quest;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.core.Character;
/**
 * Class for game scenarios, defines used map, map exits, NPCs and its positions, etc. 
 * @author Isangeles
 *
 */
public class Scenario 
{
	private final String id;
	private TiledMap map;
	private List<Character> npcs = new ArrayList<>();
	private Map<String, Position> exits;
	private List<MobsArea> mobsAreas;
	private List<Quest> quests = new ArrayList<>();
	private List<Quest> questsToStart = new ArrayList<>();
	/**
	 * Scenario constructor 
	 * @param id Scenario ID
	 * @param mapFile Scenario TMX map file
	 * @param npcs Map with NPCs IDs as keys and its positions as values
	 * @param mobsAreas List containing areas with mobs in scenario
	 * @param quests List with quests IDs
	 * @param exits Exits from map
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Scenario(String id, String mapFile, Map<String, Position>npcs, List<MobsArea> mobsAreas, Map<String, String[]> quests, Map<String, Position> exits) 
			throws SlickException, IOException, FontFormatException 
	{
		this.id = id;
		map = new TiledMap("data" + File.separator + "area" + File.separator + "map" + File.separator + mapFile);
		
		for(String key : npcs.keySet())
		{
			this.npcs.add(NpcBase.spawnAt(key, npcs.get(key)));
			Log.addSystem(key + " spawned");
		}
		
		this.mobsAreas = mobsAreas;
		
		for(MobsArea mobsArea : mobsAreas)
		{
			this.npcs.addAll(mobsArea.spawnMobs());
		}
		
		for(String qId : quests.keySet())
		{
			Quest quest = QuestsBase.get(qId);
			setQTrigger(quest, quests.get(qId));
			this.quests.add(quest);
		}
		this.exits = exits;
		this.mobsAreas = mobsAreas;
	}
	/**
	 * Returns scenario ID
	 * @return String with scenario ID
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * Return scenario map
	 * @return TMX tiled map
	 */
	public TiledMap getMap()
	{
		return map;
	}
	/**
	 * Returns list with all NPCs in scenario
	 * @return ArrayList with game characters
	 */
	public List<Character> getNpcs()
	{
		return npcs;
	}
	/**
	 * Starts all scenario quests with "start" trigger for specified character
	 * @param player Player character
	 */
	public void startQuests(Character player)
	{
		for(Quest q : questsToStart)
		{
			player.startQuest(q);
		}
	}
	/**
	 * Sets triggers for all scenario quests 
	 * @param quest Scenario quests
	 * @param trigger Table with trigger type[0] and trigger ID[1]
	 */
	private void setQTrigger(Quest quest, String[] trigger)
	{
		Log.addSystem(trigger[0]);
		switch(trigger[0])	
		{
		case "start":
			questsToStart.add(quest);
			return;
		case "talk":
			DialoguesBase.setTrigger(trigger[1], quest.getId());
			return;
		}
	}
}
