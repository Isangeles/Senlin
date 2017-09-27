/*
 * Dialogue.java
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
package pl.isangeles.senlin.core.dialogue;

import java.util.ArrayList;
import java.util.List;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.character.Character;
/**
 * Class for characters dialogues
 * @author Isangeles
 *
 */
public class Dialogue 
{
	private final String id;
	private final String npcId;
	private final String flagReq;
	private final List<DialoguePart> parts;
	private DialoguePart currentStage;
	private Character dialogueTarget;
	/**
	 * Dialogue constructor
	 * @param id Dialogue unique ID
	 * @param npcId NPC ID holding that dialogue 
	 * @param parts List with all parts of this dialogue
	 */
	public Dialogue(String id, String npcId, String flagReq, List<DialoguePart> parts) 
	{
		this.id = id;
		this.npcId = npcId;
		this.flagReq = flagReq;
		this.parts = parts;
	}
	
	public void startFor(Character target)
	{
		dialogueTarget = target;
        currentStage = getPart("start");
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
		return currentStage.getAnswers();
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
		currentStage = getPart(answer.getId());
	}
	/**
	 * Resets dialogue
	 */
	public void reset()
	{
		currentStage = getPart("start");
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
	 * Returns dialogue owner ID
	 * @return String with NPC ID
	 */
	public String getNpcId()
	{
		return npcId;
	}
	/**
	 * Returns flag requested for that dialogue
	 * @return String with flag ID
	 */
	public String getReqFlag()
	{
		return flagReq;
	}
	/**
	 * Checks if dialogue require some flag on character 
	 * @return True if flag is required, false otherwise
	 */
	public boolean isReqFlag()
	{
		if(flagReq == null || flagReq == "")
			return false;
		else
			return true;
	}
	/**
	 * Get dialogue part corresponding to specified trigger
	 * @param trigger Answer id
	 * @return Dialog part corresponding to trigger
	 */
	private DialoguePart getPart(String trigger)
	{
		Log.addDebug("d_trigger_req//" + trigger);
		
		for(DialoguePart dp : parts)
		{
		    if(dp.getTrigger().equals(trigger) && dp.hasReq() && dialogueTarget != null && dp.checkReq(dialogueTarget))
		        return dp;
		}
		for(DialoguePart dp : parts)
		{
			if(dp.getTrigger().equals(trigger))
				return dp;
		}
		
		for(DialoguePart dp : parts)
		{
			if(dp.getTrigger().equals("error01"))
				return dp;
		}
		
		List<Answer> aList = new ArrayList<>();
		aList.add(new Answer("bye01", "", true));
		return new DialoguePart("err02", "error02", null, null, aList);
	}

}
