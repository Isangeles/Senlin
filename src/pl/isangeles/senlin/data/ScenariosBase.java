/*
 * ScenariosBase.java
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
package pl.isangeles.senlin.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

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
	private static Map<String, Scenario> scenarios;
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
		System.out.println("scenariob req for _ " + id);
		return scenarios.get(id);
	}
	/**
	 * Loads base with scenarios for active module
	 * @throws FileNotFoundException
	 */
	public static void load(String areaPath) throws FileNotFoundException
	{
		scenarios = DConnector.getScenarios(areaPath + File.separator + "scenarios");
	}
}
