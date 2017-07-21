/*
 * SSGParser.java
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

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Inventory;
import pl.isangeles.senlin.core.SimpleGameObject;
import pl.isangeles.senlin.data.ItemsBase;
import pl.isangeles.senlin.data.ObjectsBase;
import pl.isangeles.senlin.data.QuestsBase;
import pl.isangeles.senlin.data.SavedGame;
import pl.isangeles.senlin.data.ScenariosBase;
import pl.isangeles.senlin.data.area.Scenario;
import pl.isangeles.senlin.quest.Objective;
import pl.isangeles.senlin.quest.Quest;
import pl.isangeles.senlin.util.Position;

/**
 * Class to parsing senlin saved games XML files
 * @author Isangeles
 *
 */
public final class SSGParser
{
    /**
     * Private constructor to prevent initialization
     */
    private SSGParser(){}
    /**
     * Unparses .ssg file to SavedGame object
     * @param ssgFile Senlin saved game file
     * @param gc Slick game container
     * @return SavedGame object ready to load
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws FontFormatException
     * @throws SlickException
     */
    public static SavedGame parseSSG(File ssgFile, GameContainer gc) throws ParserConfigurationException, SAXException, IOException, FontFormatException, SlickException
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();
        Document doc = builder.parse(ssgFile);
        
        Element saveE = doc.getDocumentElement();
        Element playerE = (Element)saveE.getElementsByTagName("player").item(0); 
        Character player = getCharFromSave((Element)playerE.getElementsByTagName("character").item(0), gc);
        
        List<Scenario> scenarios = new ArrayList<>();
        Element scenariosE = (Element)saveE.getElementsByTagName("scenarios").item(0);
        NodeList scenariosList = scenariosE.getElementsByTagName("scenario");
        for(int i = 0; i < scenariosList.getLength(); i ++)
        {
            Node scenarioNode = scenariosList.item(i);
            if(scenarioNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            {
                Element scenarioE = (Element)scenarioNode;
                scenarios.add(getSavedScenario(scenarioE, gc));
            }
        }
        
        String activeScenario = playerE.getElementsByTagName("scenario").item(0).getTextContent();
        
        return new SavedGame(player, scenarios, activeScenario);
    }
    /**
     * Parses specified save document element to game character 
     * @param charE Character element from .ssg document 
     * @param gc Slick game container
     * @return Saved game character
     * @throws IOException
     * @throws FontFormatException
     * @throws SlickException
     */
    public static Character getCharFromSave(Element charE, GameContainer gc) throws IOException, FontFormatException, SlickException
    {
        Character player = NpcParser.getNpcFromNode(charE).make(gc);
        
        Element questsE = (Element)charE.getElementsByTagName("quests").item(0);
        player.getQuests().addAll(getSavedQuests(questsE));
        
        Element flagsE = (Element)charE.getElementsByTagName("flags").item(0);
        player.getFlags().addAll(getSavedFlags(flagsE));
        
        Element pointsE = (Element)charE.getElementsByTagName("points").item(0);
        player.setHealth(Integer.parseInt(pointsE.getElementsByTagName("hp").item(0).getTextContent()));
        player.setMagicka(Integer.parseInt(pointsE.getElementsByTagName("mana").item(0).getTextContent()));
        player.setExperience(Integer.parseInt(pointsE.getElementsByTagName("exp").item(0).getTextContent()));
        player.addLearnPoints(Integer.parseInt(pointsE.getElementsByTagName("lp").item(0).getTextContent()));
        
        player.setName(charE.getElementsByTagName("name").item(0).getTextContent());
        player.setPosition(new Position(charE.getElementsByTagName("position").item(0).getTextContent()));
        
        return player;
    }
    /**
     * Parses specified scenario save document element to game area scenario
     * @param scenarioE Scenario element from .ssg document
     * @param gc Slick game container
     * @return Saved game area scenario
     * @throws DOMException
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    public static Scenario getSavedScenario(Element scenarioE, GameContainer gc) throws DOMException, SlickException, IOException, FontFormatException
    {
        Scenario scenario = ScenariosBase.getScenario(scenarioE.getAttribute("id"));
        
        List<SimpleGameObject> objects = new ArrayList<>();
        Element objectsE = (Element)scenarioE.getElementsByTagName("objects").item(0);
        NodeList objectsList = objectsE.getElementsByTagName("object");
        for(int i = 0; i < objectsList.getLength(); i ++)
        {
            Node objectNode = objectsList.item(i);
            if(objectNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            {
                Element objectE = (Element)objectNode;
                SimpleGameObject object = ObjectsBase.get(objectE.getAttribute("id"));
                object.setPosition(new Position(objectE.getAttribute("position")));
                Element eqE = (Element)objectE.getElementsByTagName("eq").item(0);
                if(eqE != null)
                	object.setInventory(getObjectInventory(eqE));
                objects.add(object);
            }
        }
        
        List<Character> npcs = new ArrayList<>();
        Element charactersE = (Element)scenarioE.getElementsByTagName("characters").item(0);
        NodeList charactersList = charactersE.getElementsByTagName("character");
        for(int i = 0; i < charactersList.getLength(); i ++)
        {
            Node characterNode = charactersList.item(i);
            if(characterNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            {
                Element characterE = (Element)characterNode;
                Character npc = getCharFromSave(characterE, gc);
                npcs.add(npc);
            }
        }
        
        scenario.setNpcs(npcs);
        scenario.setObjects(objects);
        
        return scenario;
    }
    /**
     * Parses specified save document element to list with quests
     * @param questsE Quests element form .ssg document
     * @return List with saved quests
     */
    private static List<Quest> getSavedQuests(Element questsE)
    {
        List<Quest> savedQuests = new ArrayList<>();
        NodeList questsList = questsE.getElementsByTagName("quest");
        for(int i = 0; i < questsList.getLength(); i ++)
        {
            Node questNode = questsList.item(i);
            if(questNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            {
                Element questE = (Element)questNode;
                Quest q = QuestsBase.get(questE.getAttribute("id"));
                q.setStage(questE.getAttribute("stage"));
                NodeList objectivesList = questNode.getChildNodes();
                for(int j = 0; j < objectivesList.getLength(); j++)
                {
                    Node objectiveNode = objectivesList.item(j);
                    if(objectiveNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
                    {
                        Element objectiveE = (Element)objectiveNode;
                        Objective objective = q.getCurrentObjectives().get(j);
                        objective.setComplete(Boolean.parseBoolean(objectiveE.getAttribute("complete")));
                        objective.setAmount(Integer.parseInt(objectiveE.getTextContent().split("/")[0]));
                    }
                }
                savedQuests.add(q);
            }
        }
        return savedQuests;
    }
    /**
     * Parses specified flags save document element to list with flags
     * @param flagsE Flags element from .ssg document
     * @return List with saved flags
     */
    private static List<String> getSavedFlags(Element flagsE)
    {
        List<String> flags = new ArrayList<>();
        NodeList flagsList = flagsE.getElementsByTagName("flag");
        for(int i = 0; i < flagsList.getLength(); i ++)
        {
            Node flagNode = flagsList.item(i);
            if(flagNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            {
                Element flagE = (Element)flagNode;
                flags.add(flagE.getTextContent());
            }
        }
        return flags;
    }
    /**
     * Parses specified save eq element to inventory(ignores equipment)
     * @param eqE SSG eq doc element
     * @return New inventory object
     */
    private static Inventory getObjectInventory(Element eqE)
    {
    	Inventory objectInventory = new Inventory();
    	Node inNode = eqE.getElementsByTagName("in").item(0);
    	
    	Element inE = (Element)inNode;
    	objectInventory.addGold(Integer.parseInt(inE.getAttribute("gold")));
    	
    	NodeList itemsList = inNode.getChildNodes();
    	for(int i = 0; i < itemsList.getLength(); i ++)
    	{
    		Node itemNode = itemsList.item(0);
    		if(itemNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
    		{
    			Element itemE = (Element)itemNode;
    			objectInventory.add(ItemsBase.getItem(itemE.getTextContent()));
    		}
    	}
    	
    	return objectInventory;
    }
}
