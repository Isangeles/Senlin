/*
 * ScenariosBase.java
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
package pl.isangeles.senlin.data;

import java.awt.FontFormatException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.xml.sax.SAXException;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Module;
import pl.isangeles.senlin.data.area.Scenario;
import pl.isangeles.senlin.util.DConnector;
/**
 * Static base for game scenarios
 * @author Isangles
 *
 */
public class ScenariosBase 
{
	private static Map<String, Scenario> scenarios = new HashMap<>();
	private static String scenariosDir;
	private static GameContainer gc;
	/**
	 * Private constructor to prevent initialization
	 */ 
	private ScenariosBase() {}
	/**
	 * Returns scenario with specified ID
	 * @param id String with desired scenario ID
	 * @return Scenario from base or null if scenario with specified ID was not found
	 */
	public static Scenario getScenario(String id)
	{
		try 
		{
			Scenario s = scenarios.get(id);
			if(s == null) //if scenario was not loaded already
			{
				s = DConnector.getScenario(scenariosDir, id, gc);
				scenarios.put(s.getId(), s);
			}
			return s;
		} 
		catch (ParserConfigurationException | SAXException | IOException | SlickException | FontFormatException e) 
		{
			Log.addSystem("scenarios_base_get_not_found_msg-//" + e.getMessage());
			return null;
		}
	}
	/**
	 * Loads base with scenarios for active module
	 * Note that now scenarios are only loaded dynamically on getScenario call(to shorten game load time) 
	 * @throws FileNotFoundException
	 */
	public static void load(String areaPath, GameContainer gc) throws FileNotFoundException
	{
		ScenariosBase.scenariosDir = areaPath + File.separator + "scenarios";
		ScenariosBase.gc = gc;
		//scenarios = DConnector.getScenarios(scenariosDir, gc); //full loading
	}
}
