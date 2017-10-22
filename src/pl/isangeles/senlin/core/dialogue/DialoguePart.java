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
import java.util.Map;

import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.core.req.Requirements;
/**
 * Class for dialogue parts, contains id corresponding to text in current lang directory and answers 
 * @author Isangeles
 *
 */
public class DialoguePart 
{
	private final String id;
	private final String on;
	private final Requirements reqs = new Requirements();
	private final List<Answer> answers;
	private final List<String> itemsToGive;
	private final List<String> itemsToTake;
	private final int goldToGive;
	private final int goldToTake;
	private final boolean transfer;
	/**
	 * Dialogue part constructor without any transfer
	 * @param id Dialogue part ID
	 * @param on Dialogue part trigger(e.g. dialogue answer ID or 'start' to display this part at dialogue start)
	 * @param answers List of answers on that dialogue part
	 */
	public DialoguePart(String id, String on, List<Requirement> req, List<Answer> answers) 
	{
		this.id = id;
		this.on = on;
		this.reqs.addAll(req);
		this.answers = answers;
		itemsToGive = new ArrayList<>();
		itemsToTake = new ArrayList<>();
		goldToGive = 0;
		goldToTake = 0;
		transfer = false;
	}
	/**
	 * Dialogue part constructor with transfer
	 * @param id Dialogue part ID
	 * @param on Dialogue part trigger(e.g. dialogue answer ID or 'start' to display this part at dialogue start)
	 * @param answers List of answers on that dialogue part
	 * @param itemsToGive Items to give to player on this dialogue part start
	 * @param itemsToTake Items to take from player on this dialogue part start
	 * @param goldToGive Amount of gold to give to player on this dialogue part start
	 * @param goldToTake Amount of gold to take from player on this dialogue part start
	 */
	public DialoguePart(String id, String on, List<Requirement> req, List<Answer> answers, List<String> itemsToGive, 
						List<String> itemsToTake, int goldToGive, int goldToTake) 
	{
		this.id = id;
		this.on = on;
		this.reqs.addAll(req);
		this.answers = answers;
		this.itemsToGive = itemsToGive;
		this.itemsToTake = itemsToTake;
		this.goldToGive = goldToGive;
		this.goldToTake = goldToTake;
		transfer = true;
	}
	
	public void addAnswer(Answer answer)
	{
		answers.add(answer);
	}

	public String getTrigger()
	{
		return on;
	}
	/**
	 * Returns text for specified character
	 * @param dialogueTarget Dialogue target (game character)
	 * @return String with text
	 */
	public String getText(Character dialogueTarget)
	{
		return TConnector.getDialogueText(id);
	}
	/**
	 * Returns all answers for this dialogue part
	 * @return
	 */
	public List<Answer> getAnswers()
	{
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
	 * Transfers items and gold between two dialogue participants 
	 * @param charA Dialogue owner (e.g. NPC or some game object)
	 * @param charB Second character (e.g. player character)
	 */
	public void transfer(Character charA, Character charB)
	{
		if(transfer)
		{
			for(String itemId : itemsToGive)
			{
				charB.addItem(charA.getInventory().takeItem(itemId));
			}
			for(String itemId : itemsToTake)
			{
				charA.addItem(charB.getInventory().takeItem(itemId));
			}
			charB.addGold(charA.getInventory().takeGold(goldToGive));
			charA.addGold(charB.getInventory().takeGold(goldToTake));
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
