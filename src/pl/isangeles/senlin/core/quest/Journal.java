/*
 * Journal.java
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
package pl.isangeles.senlin.core.quest;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.data.save.SaveElement;

import java.util.ArrayList;

/**
 * Class for game character quests container 
 * @author Isangeles
 *
 */
public class Journal extends ArrayList<Quest> implements SaveElement
{
	private static final long serialVersionUID = 1L;
	private List<Quest> qCompleted = new ArrayList<>();
	/**
	 * Updates journal
	 */
	public void update()
	{
		for(Quest quest : this)
		{
			if(quest.isComplete())
				qCompleted.add(quest);
		}
		this.removeAll(qCompleted);
	}
	/**
	 * Marks specified quest as completed by adding it on list with completed quests (only if quest is in container, course)
	 * @param quests List with game quests in character journal
	 */
	/*
	public void markAsCompleted(List<Quest> quests)
	{
		for(Quest quest : quests)
		{
			if(this.contains(quest))
			{
				qCompleted.add(quest);
			}
		}
	}
	*/
	/**
	 * Returns XML document element with parsed quests
	 * TODO save completed quests
	 * @param doc Game save XML document
	 * @return XML element with quests data
	 */
	public Element getSave(Document doc)
	{
		Element journalE = doc.createElement("journal");
		
		Element aQuestsE = doc.createElement("activeQuests");
		for(Quest q : this)
		{
			aQuestsE.appendChild(q.getSave(doc));
		}
		journalE.appendChild(aQuestsE);
		
		Element cQuestsE = doc.createElement("completedQuests");
		for(Quest q : qCompleted)
		{
			Element questE = doc.createElement("quest");
			questE.setTextContent(q.getId());
			cQuestsE.appendChild(questE);
		}
		journalE.appendChild(cQuestsE);
		
		return journalE;
	}
}
