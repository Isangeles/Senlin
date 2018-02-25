/*
 * SavedScenario.java
 * 
 * Copyright 2018 Dariusz Sikora <darek@pc-solus>
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
package pl.isangeles.senlin.data.save;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

import pl.isangeles.senlin.data.area.Scenario;
import pl.isangeles.senlin.util.exception.InvalidDocumentElementException;
import pl.isangeles.senlin.util.parser.SSGParser;

/**
 * Class for saved scenarios
 * Saves scenario elements from SSG to load it later
 * @author Isangeles
 *
 */
public class SavedScenario 
{
	private Element scenarioE;

	private String scenarioId;
	private String mainareaId;
	/**
	 * Saved scenario constructor
	 * @param scenarioE Scenario element from SSG
	 * @throws InvalidDocumentElementException 
	 */
	public SavedScenario(Element scenarioE) throws InvalidDocumentElementException
	{
		this.scenarioE = scenarioE;
		
		scenarioId = scenarioE.getAttribute("id");
		Element mainareaE = (Element)scenarioE.getElementsByTagName("mainarea").item(0);
		if(mainareaE != null) 
			mainareaId = mainareaE.getAttribute("id");
		else
			throw new InvalidDocumentElementException("Fail to find mainarea node in specified scenario element");
	}
	/**
	 * Returns ID of saved scenario
	 * @return String with scenario ID
	 */
	public String getScenarioId()
	{
		return scenarioId;
	}
	/**
	 * Returns ID of main area of saved scenario
	 * @return String with area ID 
	 */
	public String getMainAreaId()
	{
		return mainareaId;
	}
	/**
	 * Loads this saved scenario
	 * @param gc Slick game container
	 * @return Scenario from saved SSG element
	 * @throws NumberFormatException
	 * @throws DOMException
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Scenario load(GameContainer gc) throws NumberFormatException, DOMException, SlickException, IOException, FontFormatException
	{
		return SSGParser.getSavedScenario(scenarioE, gc);
	}
}
