/*
 * Answer.java
 * 
 * Copyright 2017-2018 Dariusz Sikora <dev@isangeles.pl>
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
package pl.isangeles.senlin.core.dialogue;

import java.util.List;

import pl.isangeles.senlin.core.quest.ObjectiveTarget;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.core.req.Requirements;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.util.TConnector;
/**
 * Class for answer, smallest dialogue part
 * @author Isangeles
 *
 */
public class Answer implements ObjectiveTarget
{
	private final String id;
	private final String fullId;
	private final String to;
	private final boolean train;
	private final boolean trade;
	private final boolean end;
	private final Requirements reqs;
	private final String text;
	/**
	 * Answer constructor(without parent dialogue part)
	 * @param id Answer text content ID
	 * @param to ID of text to display after this answer, 'end' to end dialogue after this answer
	 * @param train True if this answer starts training, false otherwise
	 * @param trade True if this answer starts trade, false otherwise
	 * @param end True if dialogue should end after this answer, false otherwise
	 * @param reqs List with requirement for this answer
	 */
	public Answer(String id, String to, boolean train, boolean trade, boolean end, List<Requirement> reqs) 
	{
		this.id = id;
		this.fullId = id;
		this.to = to;
		this.train = train;
		this.trade = trade;
		if(this.to.equals("end"))
			this.end = true;
		else
			this.end = end;
		this.reqs = new Requirements(reqs);
		
		text = TConnector.getDialogueText(id);
	}
	/**
	 * Answer constructor(with parent dialogue part)
	 * @param id Answer text content ID
	 * @param parentId ID of parent dialogue part of this answer
	 * @param to ID of text to display after this answer, 'end' to end dialogue after this answer
	 * @param train True if this answer starts training, false otherwise
	 * @param trade True if this answer starts trade, false otherwise
	 * @param end True if dialogue should end after this answer, false otherwise
	 * @param reqs List with requirement for this answer
	 */
	public Answer(String id, String parentId, String to, boolean train, boolean trade, boolean end, List<Requirement> reqs) 
	{
		this.id = id;
		this.fullId = parentId + "-" + id;
		this.to = to;
		this.train = train;
		this.trade = trade;
		if(this.to.equals("end"))
			this.end = true;
		else
			this.end = end;
		this.reqs = new Requirements(reqs);
		
		text = TConnector.getDialogueText(id);
	}
	/**
	 * Returns answer ID
	 * @return String with ID
	 */
	public String getId()
	{
		return id;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.quest.ObjectiveType#getObjectiveTargetId()
	 */
	@Override
	public String getObjectiveTargetId()
	{
		return fullId;
	}
	/**
	 * Returns ordinal ID of text to display after choosing this answer
	 * @return String with text ID
	 */
	public String getTo()
	{
		return to;
	}
	/**
	 * Returns text corresponding to answer ID
	 * @param dialogueTarget Target of dialogue
	 * @return String with text
	 */
	public String getTextFor(Character dialogueTarget)
	{
		String textForTar = text.replaceAll(Dialogue.NAME_MACRO, dialogueTarget.getName());
		return textForTar;
	}
	/**
	 * Checks if this answer starts training
	 * @return True if training should be starter after this answer, false otherwise
	 */
	public boolean isTrain()
	{
		return train;
	}
	/**
	 * Checks if this answer start trade
	 * @return True if trade should be started after this answer, false otherwise
	 */
	public boolean isTrade()
	{
		return trade;
	}
	/**
	 * Checks if that answer ends dialogue
	 * @return True if dialogue should be ended after this answer, false otherwise
	 */
	public boolean isEnd()
	{
		return end;
	}
	/**
	 * Returns answer requirements
	 * @return Requirements container
	 */
	public Requirements getReqs()
	{
		return reqs;
	}
}
