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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Inventory;
import pl.isangeles.senlin.core.SimpleGameObject;
import pl.isangeles.senlin.core.character.Attitude;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.quest.Objective;
import pl.isangeles.senlin.core.quest.Quest;
import pl.isangeles.senlin.core.skill.Buff;
import pl.isangeles.senlin.data.EffectsBase;
import pl.isangeles.senlin.data.ItemsBase;
import pl.isangeles.senlin.data.ObjectsBase;
import pl.isangeles.senlin.data.QuestsBase;
import pl.isangeles.senlin.data.ScenariosBase;
import pl.isangeles.senlin.data.SkillsBase;
import pl.isangeles.senlin.data.area.Scenario;
import pl.isangeles.senlin.data.save.SavedGame;
import pl.isangeles.senlin.gui.UiLayout;
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
        Element chapterE = (Element)saveE.getElementsByTagName("chapter").item(0);
        String chapterId = chapterE.getAttribute("id");
        Element scenariosE = (Element)chapterE.getElementsByTagName("scenarios").item(0);
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
        
        Node uiNode = saveE.getElementsByTagName("ui").item(0);
        UiLayout uiLayout = getUiLayout(uiNode);
        
        return new SavedGame(player, chapterId, scenarios, activeScenario, uiLayout);
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
    	int serial = Integer.parseInt(charE.getAttribute("serial"));
        Character character = NpcParser.getNpcFromNode(charE).make(gc, serial);
        
        Element questsE = (Element)charE.getElementsByTagName("quests").item(0);
        character.getQuests().addAll(getSavedQuests(questsE));
        
        Element flagsE = (Element)charE.getElementsByTagName("flags").item(0);
        character.getFlags().addAll(getSavedFlags(flagsE));
        
        Element effectsE = (Element)charE.getElementsByTagName("effects").item(0);
        character.getEffects().addAll(getSavedEffects(effectsE));
        
        Element pointsE = (Element)charE.getElementsByTagName("points").item(0);
        character.setHealth(Integer.parseInt(pointsE.getElementsByTagName("hp").item(0).getTextContent()));
        character.setMagicka(Integer.parseInt(pointsE.getElementsByTagName("mana").item(0).getTextContent()));
        character.setExperience(Integer.parseInt(pointsE.getElementsByTagName("exp").item(0).getTextContent()));
        character.addLearnPoints(Integer.parseInt(pointsE.getElementsByTagName("lp").item(0).getTextContent()));
        
        Element attMemoryE = (Element)charE.getElementsByTagName("attMemory").item(0);
        NodeList attList = attMemoryE.getElementsByTagName("object");
        for(int i = 0; i < attList.getLength(); i ++)
        {
            Node objectNode = attList.item(i);
            if(objectNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            {
                Element objectE = (Element)objectNode;
                String id = objectE.getTextContent();
                Attitude att = Attitude.fromString(objectE.getAttribute("attitude"));
                character.memCharAs(id, att);
            }
        }
        
        character.setName(charE.getElementsByTagName("name").item(0).getTextContent());
        character.setPosition(new Position(charE.getElementsByTagName("position").item(0).getTextContent()));
        
        return character;
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
        
        scenario.getMainArea().setNpcs(npcs);
        scenario.getMainArea().setObjects(objects);
        
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
     * Parses specified SSG element to list with effects
     * @param effectsE SSG element, effects element
     * @return List with saved effects
     */
    private static List<Effect> getSavedEffects(Element effectsE)
    {
    	List<Effect> effects = new ArrayList<>();
    	NodeList effectsNl = effectsE.getElementsByTagName("effect");
    	for(int i = 0; i < effectsNl.getLength(); i ++)
    	{
    		Node effectNode = effectsNl.item(i);
    		if(effectNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
    		{
    			Element effectE = (Element)effectNode;
    			try
    			{
        			String effectId = effectE.getTextContent();
        			int effectTime = Integer.parseInt(effectE.getAttribute("time"));
        			Effect effect = EffectsBase.getEffect(effectId);
        			effect.setTime(effectTime);
        			effects.add(effect);
    			}
    			catch(NumberFormatException e)
    			{
    				Log.addSystem("ssg_parser_fail-msg///saved effect corrupted!");
    				break;
    			}
    		}
    	}
    	return effects;
    }
    /**
     * Parses specified SSG element to list with buffs
     * @param character Game character, buffs owner
     * @param buffsE SSG element, buffs element
     * @return List with saved buffs
     */
    /*
    private static List<Buff> getSavedBuffs(Character character, Element buffsE)
    {
    	List<Buff> buffs = new ArrayList<>();
    	NodeList buffsNl = buffsE.getElementsByTagName("buff");
    	for(int i = 0; i < buffsNl.getLength(); i ++)
    	{
    		Node buffNode = buffsNl.item(i);
    		if(buffNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
    		{
    			Element buffE = (Element)buffNode;
    			try
    			{
    				String buffId = buffE.getTextContent();
    				int buffTime = Integer.parseInt(buffE.getAttribute("time"));
    				Buff buff = (Buff)SkillsBase.getSkill(character, buffId);
    				buff.setTime(buffTime);
    				buffs.add(buff);
    			}
    			catch(NumberFormatException | SlickException | IOException | FontFormatException | ClassCastException  e)
    			{
    				Log.addSystem("ssg_parser_fail-msg///saved buff corrupted!");
    				break;
    			}
    		}
    	}
    	return buffs;
    }
    */
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
    /**
     * Parses ui node to UI layout 
     * @param uiNode Node for ssg save (ui node)
     * @return UI layout 
     */
    private static UiLayout getUiLayout(Node uiNode)
    {
    	Element uiE = (Element)uiNode;
        Map<String, Integer> bBarLayout = getBBarLayout(uiE.getElementsByTagName("bar").item(0));
        Map<String, Integer[]> invLayout = getInvLayout(uiE.getElementsByTagName("inventory").item(0));
        Element cameraE = (Element)uiE.getElementsByTagName("camera").item(0);
        String[] cPosString = cameraE.getElementsByTagName("pos").item(0).getTextContent().split(";");
        float[] cPos = {0f, 0f};
        cPos[0] = Float.parseFloat(cPosString[0]);
        cPos[1] = Float.parseFloat(cPosString[1]);
        
        return new UiLayout(bBarLayout, invLayout, cPos);
    }
    /**
     * Parses bar node to UI bottom bar layout
     * @param barNode Node from ui node (bar node)
     * @return Map with skills IDs as keys and slots IDs as values
     */
    private static Map<String, Integer> getBBarLayout(Node barNode)
    {
    	Map<String, Integer> layout = new HashMap<>();
    	
    	Element barE = (Element)barNode;
    	
    	Element slotsE = (Element)barE.getElementsByTagName("slots").item(0);
    	NodeList slotsList = slotsE.getElementsByTagName("slot");
    	for(int i = 0; i < slotsList.getLength(); i ++)
    	{
    		Node slotNode = slotsList.item(i);
    		if(slotNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
    		{
    			Element slotE = (Element)slotNode;
    			try
    			{
    				String slotContent = slotE.getTextContent();
        			int slotId = Integer.parseInt(slotE.getAttribute("id"));
        			layout.put(slotContent, slotId);
    			}
    			catch(NumberFormatException e)
    			{
    				continue;
    			}
    		}
    	}
    	
    	return layout;
    }
    /**
     * Parses inventory node to UI inventory menu layout
     * @param inventoryNode Node from ui node (inventory node)
     * @return Map with items IDs as keys and slots IDs as values
     */
    private static Map<String, Integer[]> getInvLayout(Node inventoryNode)
    {
    	Map<String, Integer[]> layout = new HashMap<>();
    	
    	Element inventoryE = (Element)inventoryNode;
    	
    	Element slotsE = (Element)inventoryE.getElementsByTagName("slots").item(0);
    	NodeList slotsList = slotsE.getElementsByTagName("slot");
    	for(int i = 0; i < slotsList.getLength(); i ++)
    	{
    		Node slotNode = slotsList.item(i);
    		if(slotNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
    		{
    			Element slotE = (Element)slotNode;
    			try
    			{
    				String slotContent = slotE.getTextContent();
        			String[] slotPos = slotE.getAttribute("id").split(",");
        			Integer[] slotId = new Integer[] {Integer.parseInt(slotPos[0]), Integer.parseInt(slotPos[1])};
        			layout.put(slotContent, slotId);
    			}
    			catch(NumberFormatException e)
    			{
    				continue;
    			}
    		}
    	}
    	return layout;
    }
}
