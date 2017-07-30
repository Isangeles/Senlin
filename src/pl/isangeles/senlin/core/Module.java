/*
 * Module.java
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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pl.isangeles.senlin.util.Settings;
import pl.isangeles.senlin.util.TConnector;

/**
 * Static class for current game module details
 * @author Isangeles
 *
 */
public final class Module 
{
	private static String name = "none";
	private static String activeChapter = "none";
	/**
	 * Private constructor to prevent initialization
	 */
	private Module() {}
	/**
	 * Loads module with specified name
	 * @param modName Module name
	 * @return True if module was successfully loaded, false otherwise
	 * @throws FileNotFoundException
	 */
	public static boolean load(String modName) throws FileNotFoundException
	{
		File modDir = new File("data" + File.separator + "modules" + File.separator + modName);
		if(modDir.isDirectory())
		{
			name = modName;
			String modInfo = "data" + File.separator + "modules" + File.separator + modName + File.separator + "modInfo";
			
			activeChapter = TConnector.getTextFromFile(modInfo, "startChapter");
		}
		else
			return false;
		
		return true;
	}
	/**
	 * Sets chapter with specified ID as active chapter
	 * @param chapterId String with desired chapter ID
	 */
	public static boolean setChapter(String chapterId)
	{
		activeChapter = chapterId;
		return true; //TODO proper check
	}
	/**
	 * Returns current module name
	 * @return String with module name
	 */
	public static String getName()
	{
		return name;
	}
	/**
	 * Returns active chapter name 
	 * @return String with active chapter name
	 */
	public static String getActiveChapter()
	{
		return activeChapter;
	}
	/**
	 * Returns path to dialogues base file in current module directory
	 * @return String with path to dialogues base file
	 */
	public static String getDBasePath()
	{
		if(name != null && name != "none")
		{
			return "data" + File.separator + "modules" + File.separator + name + File.separator + "chapters"+ File.separator + activeChapter + File.separator + "dialogues" + File.separator + "dialogues"; 
		}
		else
			return null;
	}

	/**
	 * Returns path to quests base file in current module directory
	 * @return String with path to quests base file
	 */
	public static String getQBasePath()
	{
		if(name != null && name != "none")
		{
			return "data" + File.separator + "modules" + File.separator + name + File.separator + "chapters" + File.separator + activeChapter + File.separator + "quests" + File.separator + "quests"; 
		}
		else
			return null;
	}

	/**
	 * Returns path to area directory in current module directory
	 * @return String with path to area directory
	 */
	public static String getAreaPath()
	{
		if(name != null && name != "none")
		{
			return "data" + File.separator + "modules" + File.separator + name + File.separator + "chapters" + File.separator + activeChapter + File.separator + "area"; 
		}
		else
			return null;
	}
	/**
	 * Returns path to dialogues lang file in current module directory
	 * @return String with path to dialogues lang file
	 */
	public static String getLangDPath()
	{
		if(name != null && name != "none")
		{
			return "data" + File.separator + "modules" + File.separator + name + File.separator + "lang" + File.separator + Settings.getLang() + File.separator + "d_" + activeChapter; 
		}
		else
			return null;
	}

	/**
	 * Returns path to quests lang file in current module directory
	 * @return String with path to quests lang file
	 */
	public static String getLangQPath()
	{
		if(name != null && name != "none")
		{
			return "data" + File.separator + "modules" + File.separator + name + File.separator + "lang" + File.separator + Settings.getLang() + File.separator + "q_" + activeChapter; 
		}
		else
			return null;
	}
}
