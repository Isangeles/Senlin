/*
 * Chapter.java
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
package pl.isangeles.senlin.core;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.data.ScenariosBase;
import pl.isangeles.senlin.data.area.Area;
import pl.isangeles.senlin.data.area.Scenario;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.core.character.Character;

/**
 * Class for game chapters
 * @author Isangeles
 *
 */
public class Chapter implements SaveElement
{ 
	private String id;
	private List<Scenario> loadedScenarios = new ArrayList<>();
	private Scenario activeScenario;
	/**
	 * Chapter constructor
	 * @param id Chapter ID
	 * @param startScenario Start scenario
	 */
	public Chapter(String id, String startScenario)
	{
		this.id = id;
		setScenario(startScenario);
	}
	/**
	 * Chapter constructor
	 * @param id Chapter ID
	 * @param scenarios List of scenarios
	 * @param startScenario Start scenario
	 */
	public Chapter(String id, List<Scenario> scenarios, String startScenario)
	{
		this.id = id;
		this.loadedScenarios = scenarios;
		for(Scenario scenario : scenarios)
		{
			if(scenario.getId().equals(startScenario))
			{
				activeScenario = scenario;
				return;
			}
		}
		//If start scenario was not found
		activeScenario = scenarios.get(0);
	}
	
	public String getId()
	{
		return id;
	}
	/**
	 * Returns scenario with specified ID from scenarios list
	 * @param scenarioId String with desired scenario ID
	 * @return Scenario with specified ID or null if not such scenario was found
	 */
	public Scenario getScenario(String scenarioId)
	{
		final String scenarioID = scenarioId;
		for(Scenario scenario : loadedScenarios)
		{
			if(scenario.getId().equals(scenarioID))
				return scenario;
		}
		
		Scenario scenario = ScenariosBase.getScenario(scenarioID);
		if(scenario != null)
			loadedScenarios.add(scenario);
		return scenario;
	}
	/**
	 * Returns active scenario
	 * @return Active chapter scenario
	 */
	public Scenario getActiveScenario()
	{
		return activeScenario;
	}
	/**
	 * Returns all loaded(visited) scenarios of this chapter
	 * @return List with scenarios
	 */
	public List<Scenario> getScenarios()
	{
		return loadedScenarios;
	}
	/**
	 * Returns character with specified ID from all spawned character in this chapter
	 * @param serial Character serial ID
	 * @return Character with specified serial ID or null if no character with such serial ID found
	 */
	public Character getCharacter(String serial)
	{
		for(Scenario scenario : loadedScenarios)
		{
			for(Area area : scenario.getAreas())
			{
				for(Character character : area.getCharacters())
				{
					if(character.getSerialId().equals(serial))
						return character;
				}
			}
		}
		return null;
	}
	/**
	 * Returns targetable object with specified ID from all spawned object in this chapter
	 * @param tObId Targetable object ID
	 * @return Targetable object with specified ID
	 */
	public Targetable getTObject(String tObId)
	{
		for(Scenario scenario : loadedScenarios)
		{
			for(Area area : scenario.getAreas())
			{
				for(Character character : area.getCharacters())
				{
					if(character.getSerialId().equals(tObId))
						return character;
				}
				
				for(TargetableObject object : area.getObjects())
				{
					if(object.getSerialId().equals(tObId))
						return object;
				}
			}
		}
		return null;
	}
	/**
	 * Removes specified targetable object from chapter
	 * @param obToRemove Targetable object to remove
	 * @return True if specified object was successfully removed, false otherwise
	 */
	public boolean removeTObject(Targetable obToRemove)
	{
		for(Scenario scenario : loadedScenarios)
		{
			for(Area area : scenario.getAreas())
			{
				for(Character character : area.getCharacters())
				{
					if(character == obToRemove)
						return area.getCharacters().remove(obToRemove);
				}
				
				for(TargetableObject object : area.getObjects())
				{
					if(object == obToRemove)
						return area.getObjects().remove(obToRemove);
				}
			}
		}
		return false;
	}
	/**
	 * Sets scenario with specified ID as active scenario
	 * @param scenarioId Scenario ID
	 * @return True if scenario with specified ID was successfully set as active scenario, false otherwise
	 */
	public boolean setScenario(String scenarioId)
	{
		Scenario scenario = getScenario(scenarioId);
		if(scenario != null)
		{
			activeScenario = scenario;
			return true;
		}
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.data.save.SaveElement#getSave(org.w3c.dom.Document)
	 */
	@Override
	public Element getSave(Document doc) 
	{
		Element chapterE = doc.createElement("chapter");
		chapterE.setAttribute("id", id);
		
		Element scenariosE = doc.createElement("scenarios");
		for(Scenario scenario : loadedScenarios)
		{
			scenariosE.appendChild(scenario.getSave(doc));
		}
		chapterE.appendChild(scenariosE);
		
		return chapterE;
	}
	
	@Override
	public String toString()
	{
		return id;
	}
}
