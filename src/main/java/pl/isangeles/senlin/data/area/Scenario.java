/*
 * Scenario.java
 * 
 * Copyright 2017-2018 Dariusz Sikora <dev@isangeles.pl>
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.newdawn.slick.SlickException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.data.QuestsBase;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.cli.CommandInterface;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.cli.Script;
import pl.isangeles.senlin.core.TargetableObject;
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
	private Map<Quest, String> questsToStart = new HashMap<>();
	private List<Script> scripts = new ArrayList<>();
	private List<Script> finishedScripts = new ArrayList<>();
	private Area mainArea;
	private List<Area> subAreas = new ArrayList<>();
	/**
	 * Scenario constructor 
	 * @param id Scenario ID
	 * @param mainArea Main area for scenario
	 * @param subAreas List with all sub-areas for scenario
	 * @param mobsAreas List containing areas with mobs in scenario
	 * @param quests Map with quests IDs as keys and triggers IDs as values
	 * @param scripts List with scripts for scenario
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Scenario(String id, Area mainArea, List<Area> subAreas, Map<String, String> quests, List<Script> scripts) 
			throws SlickException, IOException, FontFormatException 
	{
		this.id = id;
		
		this.mainArea = mainArea;
		this.subAreas = subAreas;
		
		mainArea.spawnObjects();
		for(Area area : subAreas)
		{
			area.spawnObjects();
		}
		
		for(String qId : quests.keySet())
		{
			Quest quest = QuestsBase.get(qId);
			questsToStart.put(quest, quests.get(qId));
		}
		
		this.scripts = scripts;
	}
	/**
	 * Draws all scenario objects
	 */
	public void drawObjects()
	{
		for(TargetableObject object : mainArea.getObjects())
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
	 * Returns list with all areas in this scenario
	 * @return List with areas
	 */
	public List<Area> getAreas()
	{
		List<Area> areas = new ArrayList<>();
		areas.add(mainArea);
		areas.addAll(subAreas);
		return areas;
	}
	/**
	 * Returns sub area with specified ID from scenario sub areas list
	 * @param id ID of desired sub area
	 * @return Area with specified ID or null if no such area was found
	 */
	public Area getSubArea(String id)
	{
	    for(Area subArea : subAreas)
	    {
	        if(subArea.getId().equals(id))
	            return subArea;
	    }
	    
	    return null;
	}
	/**
	 * Returns area with specified ID
	 * @param id Desired area ID
	 * @return Area with specified ID from this scenario
	 */
	public Area getArea(String id)
	{
		if(mainArea.getId().equals(id))
			return mainArea;
		else
			return getSubArea(id);
	}
	/**
	 * Sets specified list with scripts as scenario scripts
	 * @param scripts List with game scripts
	 */
	public void setScripts(List<Script> scripts)
	{
		this.scripts = scripts;
	}
	/**
	 * Adds all quests as quests to start to specified player quests tracker
	 * @param player Player character
	 */
	public void addQuestsToStart(Character player)
	{
		for(Entry<Quest, String> q : questsToStart.entrySet())
		{
			player.getQTracker().addQuestToStart(q.getKey(), q.getValue());
		}
	}
	/**
	 * Runs all scripts for this scenario
	 * @param cli Command line interface to execute scripts
	 * @param delta Time from last update
	 */
	public void runScripts(CommandInterface cli, int delta)
	{
		for(Script script : scripts)
		{
			if(script.isFinished())
			{
				finishedScripts.add(script);
			}
			else if(script.isWaiting())
			{
				script.update(delta);
			}
			else
			{
				cli.executeScript(script);
			}
		}
		scripts.removeAll(finishedScripts);
	}
	/**
	 * Adds specified scripts to this scenario
	 * @param script CLI script
	 */
	public void addScript(Script script)
	{
		this.scripts.add(script);
	}
	/**
	 * Respawns all 'respawnable' mobs in all scenario areas
	 */
	public void respawnMobs()
	{
		try 
		{
			mainArea.respawnObjects();
		} 
		catch (ArrayIndexOutOfBoundsException | IOException | FontFormatException | SlickException e) 
		{
			Log.addSystem("scenario_mobs_respawn_fail_msg:" + mainArea.getId() + " - "+ e.getMessage());
		}
	
		for(Area area : subAreas)
		{
			try 
			{
				area.respawnObjects();
			}
			catch (ArrayIndexOutOfBoundsException | IOException | FontFormatException | SlickException e)
			{
				Log.addSystem("scenario_mobs_respawn_fail_msg:" + area.getId() + " - "+ e.getMessage());
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
		
		Element mainareaE = doc.createElement("mainarea");
		mainareaE.setAttribute("id", mainArea.getId());
		mainareaE.setAttribute("map", mainArea.getMapName());
		
		Element objectsE = doc.createElement("objects");
		for(TargetableObject object : mainArea.getObjects())
		{
			objectsE.appendChild(object.getSave(doc));
		}
		mainareaE.appendChild(objectsE);
		
		Element charactersE = doc.createElement("characters");
		for(Character npc : mainArea.getNpcs())
		{
			charactersE.appendChild(npc.getSave(doc));
		}
		mainareaE.appendChild(charactersE);
		
		scenarioE.appendChild(mainareaE);
		
		//TODO save quests triggers
		Element questsE = doc.createElement("quests");
		
		Element scriptsE = doc.createElement("scripts");
		for(Script script : scripts)
		{
			scriptsE.appendChild(script.getSave(doc));
		}
		scenarioE.appendChild(scriptsE);
		
		Element subareasE = doc.createElement("subareas");
		for(Area subArea : subAreas)
		{
			subareasE.appendChild(subArea.getSave(doc));
		}
		scenarioE.appendChild(subareasE);
		
		return scenarioE;
	}
}
