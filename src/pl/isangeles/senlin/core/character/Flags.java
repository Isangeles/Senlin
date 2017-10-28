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
package pl.isangeles.senlin.core.character;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.quest.Quest;
import pl.isangeles.senlin.data.save.SaveElement;
/**
 * Container for game characters flags 
 * @author Isangeles
 *
 */
public class Flags extends ArrayList<String> implements SaveElement
{
	private static final long serialVersionUID = 1L;

	/**
	 * Updates flags container by adding and removing requested flags
	 * @param quests List of all character quests
	 */
	public void update(Collection<Quest> quests)
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
		if(flag != "")
		{
			for(String ele : this)
			{
				if(ele.equals(flag))
					return true;
			}
		}
		return false;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.data.save.SaveElement#getSave(org.w3c.dom.Document)
	 */
	@Override
	public Element getSave(Document doc) 
	{
		Element flagsE = doc.createElement("flags");
        for(String flag : this)
        {
            Element flagE = doc.createElement("flag");
            flagE.setTextContent(flag);
            flagsE.appendChild(flagE);
        }
        return flagsE;
	}
}
