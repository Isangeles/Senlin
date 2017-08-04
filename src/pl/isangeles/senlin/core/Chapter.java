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
import pl.isangeles.senlin.data.area.Scenario;
import pl.isangeles.senlin.data.save.SaveElement;

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
	
	public Chapter(String id, String startScenario)
	{
		this.id = id;
		setScenario(startScenario);
	}
	
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
	
	public Scenario getScenario(String scenarioId)
	{
		final String scenarioID = scenarioId;
		for(Scenario scenario : loadedScenarios)
		{
			if(scenario.getId().equals(scenarioID))
			{
				loadedScenarios.add(scenario);
				return scenario;
			}
		}
		
		Scenario scenario = ScenariosBase.getScenario(scenarioID);
		if(scenario != null)
			loadedScenarios.add(scenario);
		return scenario;
	}
	
	public Scenario getActiveScenario()
	{
		return activeScenario;
	}
	
	public List<Scenario> getScenarios()
	{
		return loadedScenarios;
	}
	
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