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
package pl.isangeles.senlin.quest;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Class for game character quests container 
 * @author Isangeles
 *
 */
public class Journal extends ArrayList<Quest>
{
	private static final long serialVersionUID = 1L;
	private List<Quest> qCompleted = new ArrayList<>();
	/**
	 * Updates journal
	 */
	public void update()
	{
		this.removeAll(qCompleted);
	}
	/**
	 * Marks specified quest as completed by adding it on list with completed quests (only if quest is in container, course)
	 * @param quests List with game quests in character journal
	 */
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
	/**
	 * Returns XML document element with parsed quests
	 * TODO save completed quests
	 * @param doc Game save XML document
	 * @return XML element with quests data
	 */
	public Element getSave(Document doc)
	{
		Element questsE = doc.createElement("quests");
		for(Quest q : this)
		{
			Element questE = doc.createElement("quest");
			questE.setAttribute("id", q.getId());
			questE.setAttribute("stage", q.getCurrentStageId());
			for(Objective ob : q.getCurrentObjectives())
			{
				Element objectiveE = doc.createElement("objective");
				objectiveE.setAttribute("complete", ob.isComplete()+"");
				objectiveE.setTextContent(ob.getCurrentAmount()+"/"+ob.getReqAmount());
				questE.appendChild(objectiveE);
			}
			questsE.appendChild(questE);
		}
		
		return questsE;
	}
}
