/*
 * DialoguePart.java
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

import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.core.bonus.Modifier;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.core.req.Requirements;
/**
 * Class for dialogue parts, contains some dialogue owner text and player answers 
 * @author Isangeles
 *
 */
public class DialoguePart 
{
	private final String id;
	private final String ordinalId;
	private final boolean start;
	private final Requirements reqs;
	private final List<Answer> answers;
	private final DialogueTransfer transfer;
	private final List<Modifier> modifiersOwner;
	private final List<Modifier> modifiersPlayer;
	private final String text;
	/**
	 * Dialogue part constructor without any transfer
	 * @param id Dialogue part ID
	 * @param ordianlId Dialogue part ordinal ID
	 * @param start True if this is first dialogue part
	 * @param req List with requirements for this dialogue part
	 * @param answers List of answers on that dialogue part
	 */
	public DialoguePart(String id, String ordinalId, boolean start, List<Requirement> req, List<Answer> answers) 
	{
		this.id = id;
		this.ordinalId = ordinalId;
		this.start = start;
		this.reqs = new Requirements(req);
		this.answers = answers;
		transfer = null;
		modifiersOwner = new ArrayList<>();
		modifiersPlayer = new ArrayList<>();
		text = TConnector.getDialogueText(id);
	}
	/**
	 * Dialogue part constructor with characters modifications
	 * @param id Dialogue part ID
	 * @param ordianlId Dialogue part ordinal ID
	 * @param start True if this is first dialogue part
	 * @param req List with requirements for this dialogue part
	 * @param answers List of answers on that dialogue part
	 * @param transfer Dialogue transfer 
	 * @param modifiersOnwer List with all modifiers to apply on dialogue owner
	 * @param modifiersPlayer List with all modifiers to apply on player
	 */
	public DialoguePart(String id, String ordinalId, boolean start, List<Requirement> req, List<Answer> answers, DialogueTransfer transfer,
						List<Modifier> modifiersOwner, List<Modifier> modifiersPlayer) 
	{
		this.id = id;
		this.ordinalId = ordinalId;
		this.start = start;
		this.reqs = new Requirements(req);
		this.answers = answers;
		this.transfer = transfer;
		this.modifiersOwner = modifiersOwner;
		this.modifiersPlayer = modifiersPlayer;
		text = TConnector.getDialogueText(id);
	}
	/**
	 * Adds new answer for this dialogue part
	 * @param answer Dialogue part answer
	 */
	public void addAnswer(Answer answer)
	{
		answers.add(answer);
	}
	/**
	 * Returns text ID
	 * @return String with text ID
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * Returns text ordinal ID
	 * @return String with ordinal ID
	 */
	public String getOrdinalId()
	{
		return ordinalId;
	}
	/**
	 * Returns text for specified character
	 * @param dialogueTarget Dialogue target (game character)
	 * @return String with text
	 */
	public String getText(Character dialogueTarget)
	{
		return text;
	}
	/**
	 * Checks if this part should be first part of dialogue
	 * @return True if this part should be first part of dialogue, false otherwise
	 */
	public boolean isStart()
	{
		return start;
	}
	/**
	 * Returns all answers of this dialogue part for specified dialogue target
	 * @param dTarget Game character
	 * @return List with dialogue answers
	 */
	public List<Answer> getAnswersFor(Character dTarget)
	{
		List<Answer> answers = new ArrayList<>();
		for(Answer answer : this.answers)
		{
			if(answer.getReqs().isMetBy(dTarget))
				answers.add(answer);
		}
		return answers;
	}
	/**
	 * Returns dialogue part requirements
	 * @return Requirements
	 */
	public Requirements getReqs()
	{
		return reqs;
	}
	/**
	 * Modifies two dialogue participants(e.q. transfers items between them) 
	 * @param charA Dialogue owner (e.g. NPC or some game object)
	 * @param charB Second character (e.g. player character)
	 */
	public void modify(Character charA, Character charB)
	{
		if(transfer != null)
			transfer.exchange(charA, charB);
			
		for(Modifier mod : modifiersOwner)
		{
			charA.addModifier(mod);
		}
		for(Modifier mod : modifiersPlayer)
		{
			charB.addModifier(mod);
		}
	}
	/**
	 * Checks if this part has requirements
	 * @return True if this part have requirements, false otherwise
	 */
	public boolean hasReq()
	{
	    if(reqs != null && reqs.size() > 0)
	        return true;
	    else
	        return false;
	}
}
