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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pl.isangeles.senlin.core.Attributes;
import pl.isangeles.senlin.core.req.ReqType;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.core.req.StatsRequirement;
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
			Element textE = (Element)textNode;
			String id = textE.getAttribute("id");
			String on = textE.getAttribute("on");
			
			NodeList answers = textE.getElementsByTagName("answer");
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

			Map<Requirement, String> oTextsMap = null;
			
			Node otherTextsNode = textE.getElementsByTagName("otherTexts").item(0);
			if(otherTextsNode != null)
			{
				oTextsMap = getOtherTexts(otherTextsNode);
			}
			
			Element transferE = (Element)textE.getElementsByTagName("transfer").item(0);
			if(transferE != null)
			{
				List<String> iToGive = new ArrayList<>();
				List<String> iToTake = new ArrayList<>();
				int gToGive = 0;
				int gToTake = 0;
				
				Element giveE = (Element)transferE.getElementsByTagName("give").item(0);
				try
				{
					gToGive = Integer.parseInt(giveE.getAttribute("gold"));
				}
				catch(NumberFormatException e)
				{
					gToGive = 0;
				}
				NodeList itemsToGiveList = giveE.getChildNodes();
				for(int j = 0; j < itemsToGiveList.getLength(); j ++)
				{
					Node itemNode = itemsToGiveList.item(j);
					if(itemNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
					{
						Element itemE = (Element)itemNode;
						iToGive.add(itemE.getTextContent());
					}
				}
				
				Element takeE = (Element)transferE.getElementsByTagName("take").item(0);
				try
				{
					gToTake = Integer.parseInt(takeE.getAttribute("gold"));
				}
				catch(NumberFormatException e)
				{
					gToTake = 0;
				}
				NodeList itemsToTakeList = takeE.getChildNodes();
				for(int j = 0; j < itemsToTakeList.getLength(); j ++)
				{
					Node itemNode = itemsToTakeList.item(j);
					if(itemNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
					{
						Element itemE = (Element)itemNode;
						iToTake.add(itemE.getTextContent());
					}
				}

				return new DialoguePart(id, on, oTextsMap, answersList, iToGive, iToTake, gToGive, gToTake);
			}
			return new DialoguePart(id, on, oTextsMap, answersList);
		}
		else
		{
			Log.addSystem("dialog_builder_msg//fail");
		}
		answersList.add(new Answer("bye01", "", true));
		return new DialoguePart("err01", "error01", null, answersList);
	}
	
	public static Map<Requirement, String> getOtherTexts(Node otherTextsNode)
	{
		Map<Requirement, String> textsMap = new HashMap<>();
		Element otherTextsE = (Element)otherTextsNode;
		NodeList textsList = otherTextsE.getElementsByTagName("text");
		for(int i = 0; i < textsList.getLength(); i ++)
		{
			Node textNode = textsList.item(i);
			if(textNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				Element textE = (Element)textNode;
				String typeId = textE.getAttribute("if").split(":")[0];
				String req = textE.getAttribute("if").split(":")[1];
				ReqType type = ReqType.fromString(typeId);
				switch(type)
				{
				case STATS:
					textsMap.put(new StatsRequirement(new Attributes(req)), textE.getTextContent());
					break;
				}
			}
		}
		
		return textsMap;
	}
}
