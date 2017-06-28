/*
 * DialogueParser.java
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
package pl.isangeles.senlin.util.parser;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.dialogue.Answer;
import pl.isangeles.senlin.dialogue.Dialogue;
import pl.isangeles.senlin.dialogue.DialoguePart;
/**
 * Static class for dialogues XML base parsing 
 * @author Isangeles
 *
 */
public class DialogueParser 
{
	private static Node defaultDialogueNode;
	/**
	 * Private constructor to prevent initialization
	 */
	private DialogueParser() 
	{
	}
	/**
	 * Returns default dialogue XML node
	 * @return Node with parsed default dialogue
	 */
	public static Node getDefDialogueNode()
	{
		return defaultDialogueNode;
	}
	/**
	 * Parses XML dialogue node
	 * @param dialogueNode Dialogue node from XML dialogues base
	 * @return Dialogue object
	 */
	public static Dialogue getDialogueFromNode(Node dialogueNode)
	{
		Element dialog = (Element)dialogueNode;
		String dialogId = dialog.getAttribute("id");
		String npcId = dialog.getAttribute("npc");
		String flagReq = dialog.getAttribute("flagReq");
		List<DialoguePart> partsList = new ArrayList<>();
		
		for(int j = 0; j < dialog.getElementsByTagName("text").getLength(); j ++)
		{
			Node textNode = dialog.getElementsByTagName("text").item(j);
			partsList.add(getDialoguePartFromNode(textNode));
		}
		if(dialogId.equals("default"))
			defaultDialogueNode = dialogueNode;
		
		return new Dialogue(dialogId, npcId, flagReq, partsList);
	}
	/**
	 * Returns dialogue part from specified XML node
	 * @param textNode XML node from dialogues base file
	 * @return DialoguePart object
	 */
	private static DialoguePart getDialoguePartFromNode(Node textNode)
	{
		List<Answer> answersList = new ArrayList<>();
		if(textNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
		{
			Element text = (Element)textNode;
			String id = text.getAttribute("id");
			String on = text.getAttribute("on");
			
			NodeList answers = textNode.getChildNodes();
			for(int i = 0; i < answers.getLength(); i ++)
			{
				Node answerNode = answers.item(i);
				if(answerNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
				{
					Element answer = (Element)answerNode;
					
					String qOn = "";
					boolean endBool = false;
					if(answer.hasAttribute("end"))
						endBool = Boolean.parseBoolean(answer.getAttribute("end"));
					
					if(answer.hasAttribute("qOn"))
						qOn = answer.getAttribute("qOn");
					
					answersList.add(new Answer(answer.getTextContent(), qOn, endBool));
				}
			}
			return new DialoguePart(id, on, answersList);
		}
		else
		{
			Log.addSystem("dialog_builder_msg//fail");
		}
		answersList.add(new Answer("bye01", "", true));
		return new DialoguePart("err01", "error01", answersList);
	}
}
