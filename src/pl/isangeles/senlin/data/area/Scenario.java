/*
 * Scenario.java
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

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.data.DialoguesBase;
import pl.isangeles.senlin.data.NpcBase;
import pl.isangeles.senlin.data.ObjectsBase;
import pl.isangeles.senlin.data.QuestsBase;
import pl.isangeles.senlin.data.pattern.ObjectPattern;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.graphic.GameObject;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.audio.AudioPlayer;
import pl.isangeles.senlin.cli.CommandInterface;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.cli.Script;
import pl.isangeles.senlin.core.Module;
import pl.isangeles.senlin.core.SimpleGameObject;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.quest.Quest;
/**
 * Class for game scenarios, defines used map, map exits, NPCs and its positions, etc. 
 * @author Isangeles
 *
 */
public class Scenario implements SaveElement
{
	private final String id;
	private List<MobsArea> mobsAreas;
	private List<Quest> quests = new ArrayList<>();
	private List<Quest> questsToStart = new ArrayList<>();
	private List<Script> scripts = new ArrayList<>();
	private List<Script> finishedScripts = new ArrayList<>();
	private Map<String, String> music = new HashMap<>();
	private Area mainArea;
	private List<Area> subAreas = new ArrayList<>();
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
	public Scenario(String id, String mapFile, Area mainArea, List<Area> subAreas, List<MobsArea> mobsAreas, Map<String, String[]> quests, List<Script> scripts, 
			Map<String, String> music) 
			throws SlickException, IOException, FontFormatException 
	{
		this.id = id;
		
		this.mainArea = mainArea;
		this.subAreas = subAreas;
		/*
		TiledMap map = new TiledMap(Module.getAreaPath() + File.separator + "map" + File.separator + mapFile);
		List<Character> npcsForArea = new ArrayList<>();
		List<SimpleGameObject> objectsForArea = new ArrayList<>();
		List<Exit> exitsForArea = new ArrayList<>();

		for(String key : npcs.keySet())
		{
			Character npc = NpcBase.spawnAt(key, npcs.get(key));
			if(npc != null)
			{
				npcsForArea.add(npc);
				Log.addSystem(key + " spawned");
			}
		}
		for(String oId : objects.keySet())
		{
			SimpleGameObject go = ObjectsBase.get(oId);
			go.setPosition(objects.get(oId));
			objectsForArea.add(go);
		}

		for(Exit exit : exits)
		{
			exitsForArea.add(exit);
		}
		
		mainArea = new Area(id, map, mapFile, npcsForArea, objectsForArea, exitsForArea);
		*/
		
		this.mobsAreas = mobsAreas;
		
		for(MobsArea mobsArea : mobsAreas)
		{
			this.mainArea.getNpcs().addAll(mobsArea.spawnMobs());
		}
		
		for(String qId : quests.keySet())
		{
			Quest quest = QuestsBase.get(qId);
			setQTrigger(quest, quests.get(qId));
			this.quests.add(quest);
		}
		this.mobsAreas = mobsAreas;
		
		this.scripts = scripts;
		this.music = music;
	}
	/**
	 * Draws all scenario objects
	 */
	public void drawObjects()
	{
		for(SimpleGameObject object : mainArea.getObjects())
		{
			object.draw(1f);
		}
		for(Exit exit : mainArea.getExits())
		{
			exit.draw();
		}
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
	 * Returns main area of this scenario
	 * @return Area object
	 */
	public Area getMainArea()
	{
		return mainArea;
	}
	/**
	 * Returns list with sub areas
	 * @return List with sub areas
	 */
	public List<Area> getSubAreas()
	{
		return subAreas;
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
	 * Runs all scripts for this scenario
	 * @param cli Command line interface to execute scripts
	 */
	public void runScripts(CommandInterface cli)
	{
		for(Script script : scripts)
		{
			if(script.isFinished())
			{
				finishedScripts.add(script);
			}
			else
			{
				cli.executeScript(script);
			}
		}
		scripts.removeAll(finishedScripts);
	}
	/**
	 * Adds all music tracks for this scenario to specified audio player
	 * @param player Audio player
	 * @throws IOException
	 * @throws SlickException
	 */
	public void addMusic(AudioPlayer player) throws IOException, SlickException
	{
		for(String track : music.keySet())
		{
			if(track.equals("$all"))
			{
				player.addAll(music.get(track));
			}
			else
			{
				player.add(music.get(track), track);
			}
		}
	}
	/**
	 * Parses all scenario objects to XML document element
	 * @param doc Document for game save
	 * @return XML document element
	 */
	public Element getSave(Document doc)
	{
		Element scenarioE = doc.createElement("scenario");
		scenarioE.setAttribute("id", id);
		scenarioE.setAttribute("map", mainArea.getMapName());
		
		Element objectsE = doc.createElement("objects");
		for(SimpleGameObject object : mainArea.getObjects())
		{
			objectsE.appendChild(object.getSave(doc));
		}
		scenarioE.appendChild(objectsE);
		
		Element charactersE = doc.createElement("characters");
		for(Character npc : mainArea.getNpcs())
		{
			charactersE.appendChild(npc.getSave(doc));
		}
		scenarioE.appendChild(charactersE);
		
		Element scriptsE = doc.createElement("scripts");
		for(Script script : scripts)
		{
			Element scriptE = doc.createElement("script");
			scriptE.setTextContent(script.getName());
			scriptsE.appendChild(scriptE);
		}
		scenarioE.appendChild(scriptsE);
		
		Element musicE = doc.createElement("music");
		for(String track : music.keySet())
		{
			Element trackE = doc.createElement("track");
			trackE.setTextContent(track);
			trackE.setAttribute("category", music.get(track));
			musicE.appendChild(trackE);
		}
		scenarioE.appendChild(musicE);
		
		return scenarioE;
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
