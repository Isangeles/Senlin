/*
 * QuestParser.java
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

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.quest.Objective;
import pl.isangeles.senlin.core.quest.ObjectiveType;
import pl.isangeles.senlin.core.quest.Quest;
import pl.isangeles.senlin.core.quest.Stage;
/**
 * Class for external quest base parsing
 * @author Isangeles
 *
 */
public class QuestParser
{
    /**
     * Private constructor to prevent initialization
     */
    private QuestParser(){}
    /**
     * Parses specified quest node from XML base to quest object
     * @param questNode Quest node from quests base
     * @return Quest object
     */
    public static Quest getQuestFromNode(Node questNode)
    {
        Element questE = (Element)questNode;
        
        String id = questE.getAttribute("id");
        Node flagsOnStartNode = questE.getElementsByTagName("qFlagsOnStart").item(0);
        List<String> flagsOnStart = getFlags(flagsOnStartNode, "on");
        List<String> flagsOffStart = getFlags(flagsOnStartNode, "off");
        Node flagsOnEndNode = questE.getElementsByTagName("qFlagsOnEnd").item(0);
        List<String> flagsOnEnd = getFlags(flagsOnEndNode, "on");
        List<String> flagsOffEnd = getFlags(flagsOnEndNode, "off");
        List<Stage> stages = new ArrayList<>();
        
        Node stagesNode = questE.getElementsByTagName("stages").item(0);
        NodeList stagesList = stagesNode.getChildNodes();
        for(int i = 0; i < stagesList.getLength(); i ++)
        {
            Node stageNode = stagesList.item(i);
            if(stageNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            	stages.add(getStageFromNode(stageNode));
        }
        
        return new Quest(id, flagsOnStart, flagsOffStart, flagsOnEnd, flagsOffEnd, stages);
    }
    /**
     * Parses specified stage node
     * @param stageNode Stage node
     * @return Stage object
     */
    private static Stage getStageFromNode(Node stageNode)
    {
        Element stageE = (Element)stageNode;
        
        String id = stageE.getAttribute("id");
        String nextStage = stageE.getAttribute("next");
        
        Node flagsOnStartNode = stageE.getElementsByTagName("sFlagsOnStart").item(0);
        List<String> flagsOnStart = getFlags(flagsOnStartNode, "on");
        List<String> flagsOffStart = getFlags(flagsOnStartNode, "off");
        Node flagsOnEndNode = stageE.getElementsByTagName("sFlagsOnEnd").item(0);
        List<String> flagsOnEnd = getFlags(flagsOnEndNode, "on");
        List<String> flagsOffEnd = getFlags(flagsOnEndNode, "off");
        
        List<Objective> objectives = new ArrayList<>();
        
        Element objectivesE = (Element)stageE.getElementsByTagName("objectives").item(0);
        NodeList objectivesList = objectivesE.getElementsByTagName("objective");
        for(int i = 0; i < objectivesList.getLength(); i ++)
        {
            Node objectiveNode = objectivesList.item(i);
            if(objectiveNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
                objectives.add(getObjectiveFromNode(objectiveNode));
        }
        
        return new Stage(id, flagsOnStart, flagsOffStart, flagsOnEnd, flagsOffEnd, nextStage, objectives);
    }
    /**
     * Parses specified objective node
     * @param objectiveNode Objective node
     * @return Objective object
     */
    private static Objective getObjectiveFromNode(Node objectiveNode)
    {
        Element objectiveE = (Element)objectiveNode;
        
        String type = objectiveE.getAttribute("type");
        boolean finisher = Boolean.parseBoolean(objectiveE.getAttribute("finisher"));
        int amount = 0;
        try
        {
        	if(type.equals("kill") || type.equals("gather"))
            	amount = Integer.parseInt(objectiveE.getAttribute("amount"));
        }
        catch(NumberFormatException e)
        {
        	Log.addWarning("quest_builder_objective_fail_msg//node corrupted");;
        }
        String target = objectiveE.getTextContent();
        
        return new Objective(ObjectiveType.fromString(type), target, amount, finisher);
    }
    /**
     * Parses specified flags node to list with flags
     * @param flagsNode XML flags node
     * @param flagsType String with desired flags type ('on' or 'off')
     * @return List with flags IDs
     */
    private static List<String> getFlags(Node flagsNode, String flagsType)
    {
    	List<String> flags = new ArrayList<>();
    	if(flagsNode == null)
    		return flags;
    	
    	Element flagsE = (Element)flagsNode;
    	NodeList flagNl = flagsE.getElementsByTagName("flag");
    	for(int i = 0; i < flagNl.getLength(); i ++)
    	{
    		Node flagNode = flagNl.item(i);
    		if(flagNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
    		{
    			Element flagE = (Element)flagNode;
    			String flagType = flagE.getAttribute("type");
    			if(flagType.equals(flagsType))
    			{
    				flags.add(flagE.getTextContent());
    			}
    		}
    	}
    	return flags;
    }
}
