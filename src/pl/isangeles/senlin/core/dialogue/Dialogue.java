/*
 * Dialogue.java
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
package pl.isangeles.senlin.core.dialogue;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.core.req.Requirements;
import pl.isangeles.senlin.data.save.SaveElement;
/**
 * Class for characters dialogues
 * @author Isangeles
 *
 */
public class Dialogue implements SaveElement
{
	public final static String NAME_MACRO = "[$]name"; //regex
	
	private final String id;
	private Requirements reqs;
	private final List<DialoguePart> parts;
	private DialoguePart currentStage;
	private Character dialogueTarget;
	/**
	 * Dialogue constructor
	 * @param id Dialogue unique ID
	 * @param reqs List with requirements for this dialogue
	 * @param parts List with all parts of this dialogue
	 */
	public Dialogue(String id, List<Requirement> reqs, List<DialoguePart> parts) 
	{
		this.id = id;
		this.reqs = new Requirements(reqs);
		this.parts = parts;
	}
	/**
	 * Starts this dialogue for specified target
	 * @param target Game character
	 */
	public void startFor(Character target)
	{
		dialogueTarget = target;
		currentStage = getFirstPart();
	}
	/**
	 * Returns text of current dialogue part
	 * @return String with dialogue part text
	 */
	public String getText()
	{
		return currentStage.getText(dialogueTarget);
	}
	/**
	 * Returns all answers on current dialogue part
	 * @return List with all answers for current dialogue
	 */
	public List<Answer> getAnswers()
	{
		return currentStage.getAnswersFor(dialogueTarget);
	}
	/**
	 * Returns current dialogue part
	 * @return Current dialogue part of this dialogue
	 */
	public DialoguePart getCurrentStage()
	{
		return currentStage;
	}
	/**
	 * Changes current dialogue stage to answer-associated dialog part 
	 * @param answer Some answer
	 */
	public void answerOn(Answer answer)
	{
		if(dialogueTarget != null)
			dialogueTarget.getQTracker().check(answer);
		
		if(!answer.isEnd())
		{
			currentStage = getPart(answer.getTo());
			if(dialogueTarget != null)
				dialogueTarget.getQTracker().check(currentStage);
		}
		else
			reset();
	}
	/**
	 * Resets dialogue
	 */
	public void reset()
	{
		currentStage = getFirstPart();
		dialogueTarget = null;
	}
	/**
	 * Adds new option to first dialogue part
	 * @param option Answer on first dialogue part
	 */
	public void addOption(Answer option)
	{
		if(parts.size() >= 1)
		{
			if(parts.get(0) != null)
				parts.get(0).addAnswer(option);
		}
	}
	/**
	 * Returns dialogue ID
	 * @return String with dialogue ID
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * Returns flag requested for that dialogue
	 * @return String with flag ID
	 */
	public Requirements getReqs()
	{
		return reqs;
	}
	/**
	 * Checks if dialogue require some flag on character 
	 * @return True if flag is required, false otherwise
	 */
	public boolean hasReqs()
	{
		if(reqs.isEmpty())
			return false;
		else
			return true;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.data.save.SaveElement#getSave(org.w3c.dom.Document)
	 */
	@Override
	public Element getSave(Document doc) 
	{
		Element dialogueE = doc.createElement("dialogue");
    	
		dialogueE.setAttribute("id", id);
		Element dReq = doc.createElement("dReq");
		for(Requirement req : reqs)
		{
			dReq.appendChild(req.getSave(doc));
		}
		dialogueE.appendChild(dReq);
    	
    	return dialogueE;
	}
	/**
	 * Returns dialogue part with specified ordinal ID
	 * @param ordinalId String with dialogue part ordinal ID
	 * @return Dialog part with specified ordinal ID or error part if no such dialogue was found
	 */
	private DialoguePart getPart(String ordinalId)
	{
		//Log.addSystem("d_trigger_req//" + ordinalId);

		for(DialoguePart dp : parts)
		{
		    if(dp.getOrdinalId().equals(ordinalId) && dp.hasReq() && dialogueTarget != null)
		    {
		    	if(dp.getReqs().isMetBy(dialogueTarget))
		    	{
		    		//dp.getReqs().chargeAll(dialogueTarget);
		    		return dp;
		    	}
		    }
		}
		
		for(DialoguePart dp : parts)
		{
			if(dp.getOrdinalId().equals(ordinalId) && !dp.hasReq())
				return dp;
		}

		for(DialoguePart dp : parts)
		{
			if(dp.getId().equals("error01"))
				return dp;
		}

		//Error part
		List<Answer> aList = new ArrayList<>();
		Requirements reqs = new Requirements();
		aList.add(new Answer("bye01", "err02", "", false, false, true, reqs));
		return new DialoguePart("err02", "err", true, reqs, aList);
	}
	/**
	 * Returns first part of this dialogue
	 * @return Dialogue part
	 */
	private DialoguePart getFirstPart()
	{
		if(dialogueTarget != null)
		{
			for(DialoguePart part : parts)
			{
				//Log.addSystem("d_part_check:" + part.getId());
				if(part.isStart() && part.hasReq() && part.getReqs().isMetBy(dialogueTarget))
				{
					//Log.addSystem("req_d_part:" + part.getId());
					return part;
				}
			}
		}
		for(DialoguePart part : parts)
		{
			if(part.isStart() && !part.hasReq())
			{
				//Log.addSystem("non_req_d_part:" + part.getId());
				return part;
			}
		}
		
		return parts.get(0);
	}
}
