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

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.dialogue.Answer;
import pl.isangeles.senlin.core.dialogue.Dialogue;
import pl.isangeles.senlin.core.dialogue.DialoguePart;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.core.req.Requirements;
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
	 * Returns dialogue with specified ID
	 * @param dialogueId String with dialogue ID
	 * @return Dialogue with specified ID or null if no such dialogue was found
	 */
	public static Dialogue getDialogue(String dialogueId)
	{
		return dialogsMap.get(dialogueId);
	}
	/**
	 * Returns new instance of default dialogue
	 * @return Game dialogue
	 */
	public static Dialogue getDefaultDialogue()
	{
		Answer bye = new Answer("bye01", "", true, new Requirements());
		List<Answer> answers = new ArrayList<>();
		answers.add(bye);
		DialoguePart dp = new DialoguePart("greeting01", "0", true, new Requirements(), answers);
		List<DialoguePart> parts = new ArrayList<>();
		parts.add(dp);
		return new Dialogue("default", new Requirements(), parts);
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
