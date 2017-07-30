/*
 * Flags.java
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

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.quest.Quest;
/**
 * Container for character flags 
 * @author Isangeles
 *
 */
public class Flags extends ArrayList<String>
{
	private static final long serialVersionUID = 1L;

	public Flags()
	{
	}
	/**
	 * Updates flags container by adding and removing requested flags
	 * @param quests List of all character quests
	 */
	public void update(List<Quest> quests)
	{
		for(Quest quest : quests)
		{
			if(quest.hasFlag())
			{
				for(String flag : quest.getFlagsToSet())
				{
					if(flag != "" && !this.contains(flag))
					{
						this.add(flag);
						quest.clearFlag(flag);
						Log.addSystem(flag + " flag added");
					}
				}
				
				for(String flag : quest.getFlagsToRemove())
				{
					if(flag != "" && this.contains(flag))
					{
						this.remove(flag);
						quest.clearFlag(flag);
						Log.addSystem(flag + " flag removed");
					}
				}
			}
		}
	}
	/**
	 * Lists all flags in container to string
	 * @return String with all flags IDs listed
	 */
	public String list()
	{
		String flags = "";
		for(String flag : this)
		{
			flags += flag + ";";
		}
		return flags;
	}
	/**
	 * Checks if container contains specified flag
	 * @param flag Flag ID
	 * @return True if container have specified flag, false otherwise
	 */
	public boolean contains(String flag)
	{
		for(String ele : this)
		{
			if(ele.equals(flag))
				return true;
		}
		return false;
	}
}
