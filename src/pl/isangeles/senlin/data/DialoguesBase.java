/*
 * DialoguesBase.java
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
package pl.isangeles.senlin.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import pl.isangeles.senlin.dialogue.Dialogue;
import pl.isangeles.senlin.util.DConnector;
import pl.isangeles.senlin.util.parser.DialogueParser;
/**
 * Static class for dialogues base
 * @author Isangeles
 *
 */
public class DialoguesBase 
{
	private static Map<String, Dialogue> dialogsMap = new HashMap<>();
	/**
	 * Private constructor to prevent initialization
	 */
	private DialoguesBase() {}
	/**
	 * Returns all dialogues for specified NPC
	 * @param npcId NPC ID
	 * @return List with dialogues
	 */
	public static List<Dialogue> getDialogues(String npcId)
	{
		List<Dialogue> dialogues = new ArrayList<>();
		for(Dialogue dialogue : dialogsMap.values())
		{
			if(dialogue.getNpcId().equals(npcId))
				dialogues.add(dialogue);
		}
		return dialogues;
	}
	/**
	 * Returns default dialogue from base
	 * @return Dialogue object
	 */
	public static Dialogue getDefaultDialogue()
	{
		//return dialogsMap.get("default");
		return DialogueParser.getDialogueFromNode(DialogueParser.getDefDialogueNode());
	}
	/**
	 * Sets quest trigger for specific dialogue answer 
	 * @param answerId Dialogue answer ID
	 * @param questId Quest ID
	 */
	public static void setTrigger(String answerId, String questId)
	{
		System.out.println("dBase_req_for//" + answerId);
		for(Dialogue dialogue : dialogsMap.values())
		{
			dialogue.setQTrigger(answerId, questId);
		}
	}
	/**
	 * Loads base
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void load(String basePath) throws ParserConfigurationException, SAXException, IOException
	{
		dialogsMap = DConnector.getDialogueMap(basePath);
	}
}
