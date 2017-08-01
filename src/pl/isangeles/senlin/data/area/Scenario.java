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
import pl.isangeles.senlin.cli.CommandInterface;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Module;
import pl.isangeles.senlin.core.SimpleGameObject;
import pl.isangeles.senlin.core.quest.Quest;
/**
 * Class for game scenarios, defines used map, map exits, NPCs and its positions, etc. 
 * @author Isangeles
 *
 */
public class Scenario implements SaveElement
{
	private final String id;
	private String mapFileName;
	private TiledMap map;
	private List<Character> npcs = new ArrayList<>();
	private List<MobsArea> mobsAreas;
	private List<Quest> quests = new ArrayList<>();
	private List<Quest> questsToStart = new ArrayList<>();
	private List<SimpleGameObject> objects = new ArrayList<>();
	private List<Exit> exits = new ArrayList<>();
	private List<String> scripts = new ArrayList<>();
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
	public Scenario(String id, String mapFile, Map<String, Position>npcs, List<MobsArea> mobsAreas, Map<String, String[]> quests, Map<String, Position> objects,
			        Map<String, Position> exits, List<String> scripts) 
			throws SlickException, IOException, FontFormatException 
	{
		this.id = id;
		mapFileName = mapFile;
		map = new TiledMap(Module.getAreaPath() + File.separator + "map" + File.separator + mapFile);
		
		for(String key : npcs.keySet())
		{
			Character npc = NpcBase.spawnAt(key, npcs.get(key));
			if(npc != null)
			{
				this.npcs.add(npc);
				Log.addSystem(key + " spawned");
			}
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
		
		for(String oId : objects.keySet())
		{
			SimpleGameObject go = ObjectsBase.get(oId);
			go.setPosition(objects.get(oId));
			this.objects.add(go);
		}

		for(String eId : exits.keySet())
		{
			this.exits.add(new Exit(exits.get(eId), eId));
		}
		this.mobsAreas = mobsAreas;
		
		this.scripts = scripts;
	}
	/**
	 * Draws all scenario objects
	 */
	public void drawObjects()
	{
		for(SimpleGameObject object : objects)
		{
			object.draw(1f);
		}
		for(Exit exit : exits)
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
	 * Returns all scenario objects
	 * @return List with scenario SimpleGameObjects
	 */
	public List<SimpleGameObject> getObjects()
	{
		return objects;
	}
	
	public List<Exit> getExits()
	{
		return exits;
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
	
	public void runScripts(CommandInterface cli)
	{
	    for(String script : scripts)
	    {
	        cli.executeCommand(script);
	    }
	}
	
	public void setNpcs(List<Character> npcs)
	{
	    this.npcs = npcs;
	}
	
	public void setObjects(List<SimpleGameObject> objects)
	{
	    this.objects = objects;
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
		scenarioE.setAttribute("map", mapFileName);
		
		Element objectsE = doc.createElement("objects");
		for(SimpleGameObject object : objects)
		{
			objectsE.appendChild(object.getSave(doc));
		}
		scenarioE.appendChild(objectsE);
		
		Element charactersE = doc.createElement("characters");
		for(Character npc : npcs)
		{
			charactersE.appendChild(npc.getSave(doc));
		}
		scenarioE.appendChild(charactersE);
		
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
