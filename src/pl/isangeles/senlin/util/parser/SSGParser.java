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
import pl.isangeles.senlin.cli.Script;
import pl.isangeles.senlin.core.Inventory;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.TargetableObject;
import pl.isangeles.senlin.core.character.Attitude;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.day.Day;
import pl.isangeles.senlin.core.day.WeatherType;
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
import pl.isangeles.senlin.data.area.Area;
import pl.isangeles.senlin.data.area.Scenario;
import pl.isangeles.senlin.data.save.SavedGame;
import pl.isangeles.senlin.gui.UiLayout;
import pl.isangeles.senlin.util.DConnector;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.util.TilePosition;

/**
 * Class for parsing senlin saved games XML files
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
    public static SavedGame parseSSG(File ssgFile, GameContainer gc) 
    throws ParserConfigurationException, SAXException, IOException, FontFormatException, SlickException
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();
        Document doc = builder.parse(ssgFile);
        
        Element saveE = doc.getDocumentElement();
        Element playerE = (Element)saveE.getElementsByTagName("player").item(0); 
        Element playerCharE = (Element)playerE.getElementsByTagName("character").item(0);
        Character player = getCharFromSave(playerCharE, gc);
        
        Element worldE = (Element)saveE.getElementsByTagName("world").item(0);
        
        List<Scenario> scenarios = new ArrayList<>();
        Element chapterE = (Element)worldE.getElementsByTagName("chapter").item(0);
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
        
        Element scenarioE = (Element)playerE.getElementsByTagName("scenario").item(0);
        String activeScenario = scenarioE.getTextContent();
        String currentArea = scenarioE.getAttribute("area");
        for(Scenario scenario : scenarios)
        {
        	if(scenario.getId().equals(activeScenario))
        	{
        		if(scenario.getMainArea().getId().equals(currentArea))
        			player.setArea(scenario.getMainArea());
        		else
        		{
        			for(Area subArea : scenario.getSubAreas())
        			{
        				if(subArea.getId().equals(currentArea))
        					player.setArea(subArea);
        			}
        		}
        	}
        }
        
        Node dayNode = worldE.getElementsByTagName("day").item(0);
        Day day = getDayFromNode(dayNode);
        
        Node uiNode = saveE.getElementsByTagName("ui").item(0);
        UiLayout uiLayout = getUiLayout(uiNode);
        
        return new SavedGame(player, chapterId, scenarios, activeScenario, day, uiLayout);
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
        
        Element journalE = (Element)charE.getElementsByTagName("journal").item(0);
        character.getQuests().addAll(getSavedQuests(journalE));
        
        Element flagsE = (Element)charE.getElementsByTagName("flags").item(0);
        character.getFlags().addAll(getSavedFlags(flagsE));
        
        character.getSkills().resetPassives(); //To clear passive effects
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
        character.setPosition(new TilePosition(charE.getElementsByTagName("position").item(0).getTextContent()));

        return character;
    }
    /**
     * Parses specified scenario save document element to game area scenario
     * @param scenarioE Scenario element from .ssg document
     * @param gc Slick game container
     * @return Saved game scenario
     * @throws DOMException
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    public static Scenario getSavedScenario(Element scenarioE, GameContainer gc) 
    throws DOMException, SlickException, IOException, FontFormatException, NumberFormatException
    {
        Scenario scenario = ScenariosBase.getScenario(scenarioE.getAttribute("id"));
        
        Node objectsNode = scenarioE.getElementsByTagName("objects").item(0);
        List<TargetableObject> objects = getSavedObjects(objectsNode);
        
        Node charactersNode = scenarioE.getElementsByTagName("characters").item(0);
        List<Character> npcs = getSavedNpcs(charactersNode, gc);
        
        List<Script> scripts = new ArrayList<>();
        Element scriptsE = (Element)scenarioE.getElementsByTagName("scripts").item(0);
        NodeList scriptsNl = scriptsE.getElementsByTagName("script");
        for(int i = 0; i < scriptsNl.getLength(); i ++)
        {
        	Node scriptNode = scriptsNl.item(i);
        	if(scriptNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
        	{
        		Element scriptE = (Element)scriptNode;
        		String scriptId = scriptE.getTextContent();
        		long waitTime = Long.parseLong(scriptE.getAttribute("wait"));
        		int activeLineId = Integer.parseInt(scriptE.getAttribute("aID"));
        		Script script = DConnector.getScript(scriptId);
        		if(script != null)
        		{
        			script.pause(waitTime);
        			script.setActiveLineId(activeLineId);
        			scripts.add(script);
        		}
        	}
        }
        
        scenario.getMainArea().setCharacters(npcs);
        scenario.getMainArea().setObjects(objects);
        scenario.setScripts(scripts);
        
        Element subareasE = (Element)scenarioE.getElementsByTagName("subareas").item(0);
        NodeList subareasNl = subareasE.getElementsByTagName("area");
        for(int i = 0; i < subareasNl.getLength(); i ++)
        {
            Node areaNode = subareasNl.item(i);
            if(areaNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            {
                Element areaE = (Element)areaNode;
                String areaId = areaE.getAttribute("id");
                Area targetArea = scenario.getSubArea(areaId);
                if(targetArea != null)
                {
                    Node subCharactersNode = areaE.getElementsByTagName("npcs").item(0);
                    List<Character> characters = getSavedNpcs(subCharactersNode, gc);
                    
                    Node subObjectsNode = areaE.getElementsByTagName("objects").item(0);
                    List<TargetableObject> subObjects = getSavedObjects(subObjectsNode);
                    
                    targetArea.setCharacters(characters);
                    targetArea.setObjects(subObjects);
                }
            }
        }
        
        return scenario;
    }
    /**
     * Parses specified SSG node to list with game characters
     * @param npcsNode SSG document node 
     * @param gc Slick game container
     * @return List with game character from node
     * @throws IOException
     * @throws FontFormatException
     * @throws SlickException
     */
    private static List<Character> getSavedNpcs(Node npcsNode, GameContainer gc) throws IOException, FontFormatException, SlickException
    {
        List<Character> npcs = new ArrayList<>();
        Element charactersE = (Element)npcsNode;
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
        return npcs;
    }
    /**
     * Parses specified SSG node to list with simple game objects
     * @param objectsNode SSG document node
     * @return List with simple game objects
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    private static List<TargetableObject> getSavedObjects(Node objectsNode) throws SlickException, IOException, FontFormatException
    {
        List<TargetableObject> objects = new ArrayList<>();
        Element objectsE = (Element)objectsNode;
        NodeList objectsList = objectsE.getElementsByTagName("object");
        for(int i = 0; i < objectsList.getLength(); i ++)
        {
            Node objectNode = objectsList.item(i);
            if(objectNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            {
                Element objectE = (Element)objectNode;
                TargetableObject object = ObjectsBase.get(objectE.getAttribute("id"));
                object.setPosition(new TilePosition(objectE.getAttribute("position")));
                Element eqE = (Element)objectE.getElementsByTagName("eq").item(0);
                if(eqE != null)
                    object.setInventory(getObjectInventory(object, eqE));
                objects.add(object);
            }
        }
        return objects;
    }
    /**
     * Parses specified save document element to list with quests
     * @param questsE Quests element form .ssg document
     * @return List with saved quests
     */
    private static List<Quest> getSavedQuests(Element journalE)
    {
        List<Quest> savedQuests = new ArrayList<>();
        
        Element aQuestsE = (Element)journalE.getElementsByTagName("activeQuests").item(0);
        
        NodeList aQuestsList = aQuestsE.getElementsByTagName("quest");
        for(int i = 0; i < aQuestsList.getLength(); i ++)
        {
            Node questNode = aQuestsList.item(i);
            if(questNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            {
                Element questE = (Element)questNode;
                String questId = questE.getAttribute("id");
                Quest quest = QuestsBase.get(questId);
                quest.setStage(questE.getAttribute("stage"));
                NodeList objectivesList = questNode.getChildNodes();
                for(int j = 0; j < objectivesList.getLength(); j++)
                {
                    Node objectiveNode = objectivesList.item(j);
                    if(objectiveNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
                    {
                        Element objectiveE = (Element)objectiveNode;
                        Objective objective = quest.getCurrentObjectives().get(j);
                        objective.setComplete(Boolean.parseBoolean(objectiveE.getAttribute("complete")));
                        objective.setAmount(Integer.parseInt(objectiveE.getTextContent().split("/")[0]));
                    }
                }
                savedQuests.add(quest);
            }
        }
        
        Element cQuestsE = (Element)journalE.getElementsByTagName("completedQuests").item(0);
        
        NodeList cQuestsList = cQuestsE.getElementsByTagName("quest");
        for(int i = 0; i < cQuestsList.getLength(); i ++)
        {
        	Node questNode = cQuestsList.item(i);
        	if(questNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
        	{
        		Element questE = (Element)questNode;
        		String questId = questE.getTextContent();
        		Quest quest = QuestsBase.get(questId);
        		quest.complete();
        		savedQuests.add(quest);
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
        			int effectTime = Integer.parseInt(effectE.getAttribute("duration"));
        			String source = effectE.getAttribute("source");
        			String owner = effectE.getAttribute("owner");
        			Effect effect = EffectsBase.getEffect(owner, source, effectId); //TODO find way to restore proper effects sources 
        			effect.setTime(effectTime);
        			effects.add(effect);
    			}
    			catch(NumberFormatException e)
    			{
    				Log.addSystem("ssg_parser_fail-msg///saved effect corrupted! node:" + effectE.getTextContent());
    				break;
    			}
    		}
    	}
    	return effects;
    }
    /**
     * Parses specified save eq element to inventory(ignores equipment)
     * @param eqE SSG eq doc element
     * @return New inventory object
     */
    private static Inventory getObjectInventory(Targetable object, Element eqE)
    {
    	Inventory objectInventory = new Inventory(object);
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
     * Parses specified day node to game day
     * @param dayNode Node from SSG file (day node)
     * @return New game day instance
     * @throws IOException 
     * @throws SlickException 
     */
    private static Day getDayFromNode(Node dayNode) throws SlickException, IOException, NumberFormatException
    {
    	//Day day = new Day();
    	Element dayE = (Element)dayNode;
    	long dayTime = Long.parseLong(dayE.getAttribute("time"));
    	
    	Element weatherE = (Element)dayE.getElementsByTagName("weather").item(0);
    	String weatherId = weatherE.getTextContent();
    	int weatherTime = Integer.parseInt(weatherE.getAttribute("timer"));
    	int weatherDuration = Integer.parseInt(weatherE.getAttribute("to"));
    	
    	Day day = new Day();
    	day.setTime(dayTime);
    	day.setWeather(WeatherType.fromId(weatherId), weatherTime, weatherDuration);
    	
    	return day;
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
