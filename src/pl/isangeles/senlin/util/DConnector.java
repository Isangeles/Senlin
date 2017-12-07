/*
 * DConnector.java
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
package pl.isangeles.senlin.util;

import java.awt.FontFormatException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.cli.Script;
import pl.isangeles.senlin.core.Attributes;
import pl.isangeles.senlin.core.Inventory;
import pl.isangeles.senlin.core.Module;
import pl.isangeles.senlin.core.bonus.Modifiers;
import pl.isangeles.senlin.core.character.Guild;
import pl.isangeles.senlin.core.craft.Recipe;
import pl.isangeles.senlin.core.dialogue.Answer;
import pl.isangeles.senlin.core.dialogue.Dialogue;
import pl.isangeles.senlin.core.dialogue.DialoguePart;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.item.Armor;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.item.Weapon;
import pl.isangeles.senlin.core.quest.Quest;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.data.EffectsBase;
import pl.isangeles.senlin.data.area.MobsArea;
import pl.isangeles.senlin.data.area.Scenario;
import pl.isangeles.senlin.data.pattern.ArmorPattern;
import pl.isangeles.senlin.data.pattern.AttackPattern;
import pl.isangeles.senlin.data.pattern.BuffPattern;
import pl.isangeles.senlin.data.pattern.EffectPattern;
import pl.isangeles.senlin.data.pattern.MiscPattern;
import pl.isangeles.senlin.data.pattern.RandomItem;
import pl.isangeles.senlin.data.pattern.TrinketPattern;
import pl.isangeles.senlin.data.pattern.WeaponPattern;
import pl.isangeles.senlin.data.pattern.NpcPattern;
import pl.isangeles.senlin.data.pattern.ObjectPattern;
import pl.isangeles.senlin.data.pattern.PassivePattern;
import pl.isangeles.senlin.util.parser.DialogueParser;
import pl.isangeles.senlin.util.parser.EffectParser;
import pl.isangeles.senlin.util.parser.ItemParser;
import pl.isangeles.senlin.util.parser.NpcParser;
import pl.isangeles.senlin.util.parser.ObjectParser;
import pl.isangeles.senlin.util.parser.QuestParser;
import pl.isangeles.senlin.util.parser.RecipeParser;
import pl.isangeles.senlin.util.parser.RequirementsParser;
import pl.isangeles.senlin.util.parser.ScenarioParser;
import pl.isangeles.senlin.util.parser.SkillParser;

/**
 * This class provides static methods giving access to external game data like items, NPCs, etc;
 * @author Isangeles
 *
 */
public final class DConnector 
{
    public static final String SIMSUN_FONT = "data" + File.separator + "font" + File.separator + "SIMSUN.ttf";
	/**
	 * Private constructor to prevent initialization
	 */
	private DConnector(){}
	/**
	 * Parses XML NPC base to map with NPC IDs as keys assigned to specific NPC pattern
	 * @param baseName Name of base in data/npc dir
	 * @return Map with NPC IDs as keys assigned to specific NPC pattern
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Map<String, NpcPattern> getNpcMap(String basePath) throws ParserConfigurationException, SAXException, IOException
	{
		Map<String, NpcPattern> npcMap = new HashMap<>();
		
		if(!basePath.endsWith(".base"))
	    {
	    	basePath += ".base";
	    }
		
		//Parses xml
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document base = builder.parse(basePath);
		//Puts all npc nodes to list
		NodeList nList = base.getDocumentElement().getChildNodes();
		//Iterates over all npc nodes
		for(int i = 0; i < nList.getLength(); i ++)
		{
			//Puts all nodes inside npcs node to list
			Node npcNode = nList.item(i);
			//Iterates over all nodes inside npcs node
			if(npcNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				try
				{
					NpcPattern npcp = NpcParser.getNpcFromNode(npcNode);
					npcMap.put(npcp.getId(), npcp);
				}
				catch(NumberFormatException | NullPointerException e)
				{
					Log.addSystem("npc_builder_fail_msg///node corrupted!");
					System.err.println("npc_builder_fail_msg///node corrupted!");
					e.printStackTrace();
					continue;
				}
				    
			}
		}
		
		return npcMap;
	}
	/**
	 * Parses XML doc and builds map with guilds and guilds IDs as keys
	 * @param baseFile Name of xml file in data/npc dir
	 * @return Map with IDs and guilds
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Map<String, Guild> getGuildsMap(String basePath) throws ParserConfigurationException, SAXException, IOException
	{
		Map<String, Guild> guildsMap = new HashMap<>();
		
		if(!basePath.endsWith(".base"))
	    {
	    	basePath += ".base";
	    }
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document base = db.parse(basePath);
		
		NodeList nl = base.getDocumentElement().getChildNodes();
		for(int i = 0; i < nl.getLength(); i ++)
		{
			Node guildNode = nl.item(i);
			if(guildNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				Element guild = (Element)guildNode;
				String guildId = guild.getTextContent();
				Guild guildOb = new Guild(guildId);
				guildsMap.put(guildOb.getId(), guildOb);
			}
		}
		
		return guildsMap;
	}
	/**
	 * Returns Map with dialogues map from specified base file
	 * @param baseFile XML base file
	 * @return HashMap with dialogues and npcs IDs as keys
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Map<String, Dialogue> getDialogueMap(String basePath) throws ParserConfigurationException, SAXException, IOException
	{
		Map<String, Dialogue> dialogsMap = new HashMap<>();
		
		if(!basePath.endsWith(".base"))
	    {
	    	basePath += ".base";
	    }
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document base = db.parse(basePath);
		
		NodeList nl = base.getDocumentElement().getChildNodes();
		for(int i = 0; i < nl.getLength(); i ++)
		{
			Node dialogueNode = nl.item(i);
			if(dialogueNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				Dialogue dialogueOb = DialogueParser.getDialogueFromNode(dialogueNode);
				
				dialogsMap.put(dialogueOb.getId(), dialogueOb);
			}
		}
		
		return dialogsMap;
	}
	/**
	 * Parses XML base file and builds map with attacks patterns as values and its IDs as keys
	 * @param baseFile XML base file
	 * @return Map with attacks patterns as values and its IDs as keys
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static Map<String, AttackPattern> getAttacksMap(String basePath) throws SAXException, IOException, ParserConfigurationException
	{
	    Map<String, AttackPattern> attacksMap = new HashMap<>();
	    
	    if(!basePath.endsWith(".base"))
	    {
	    	basePath += ".base";
	    }
	    
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document base = db.parse(basePath);
	    
        NodeList nl = base.getDocumentElement().getChildNodes();
        //Iterating skills nodes
        for(int i = 0; i < nl.getLength(); i ++)
        {
            Node skillNode = nl.item(i);
            if(skillNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            {
                try
                {
                    AttackPattern pattern = SkillParser.getAttackFromNode(skillNode);
                    attacksMap.put(pattern.getId(), pattern);
                }
                catch(NumberFormatException | NoSuchElementException e)
                {
                    Log.addSystem("attacks_base_builder-fail msg///base node corrupted!");
                    e.printStackTrace();
                }
            }
        }
	    return attacksMap;
	}

	/**
	 * Parses XML base file and builds map with buffs patterns as values and its IDs as keys
	 * @param baseFile XML base file
	 * @return Map with buffs patterns as values and its IDs as keys
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static Map<String, BuffPattern> getBuffsMap(String basePath) throws SAXException, IOException, ParserConfigurationException
	{
	    Map<String, BuffPattern> buffsMap = new HashMap<>();
	    
	    if(!basePath.endsWith(".base"))
	    {
	    	basePath += ".base";
	    }
	    
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document base = db.parse(basePath);
	    
        NodeList nl = base.getDocumentElement().getChildNodes();
        //Iterating skills nodes
        for(int i = 0; i < nl.getLength(); i ++)
        {
            Node skillNode = nl.item(i);
            if(skillNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            {
                try
                {
                    BuffPattern pattern = SkillParser.getBuffFromNode(skillNode);
                    buffsMap.put(pattern.getId(), pattern);
                }
                catch(NumberFormatException | NoSuchElementException e)
                {
                    Log.addSystem("buffs_base_builder-fail msg///base node corrupted!");
                    e.printStackTrace();
                }
            }
        }
	    return buffsMap;
	}
	/**
	 * Parses XML base file content and builds map with passive skills patterns as values and its IDs as keys
	 * @param basePath Path to XML base
	 * @return Map with skills patterns as values and its IDs as keys
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static Map<String, PassivePattern> getPassivesMap(String basePath) throws ParserConfigurationException, SAXException, IOException
	{
		Map<String, PassivePattern> passivesMap = new HashMap<>();
		
		if(!basePath.endsWith(".base"))
			basePath += ".base";
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document base = db.parse(basePath);
	    
        NodeList nl = base.getDocumentElement().getChildNodes();
        //Iterating skills nodes
        for(int i = 0; i < nl.getLength(); i ++)
        {
            Node skillNode = nl.item(i);
            if(skillNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            {
                try
                {
                    PassivePattern pattern = SkillParser.getPassiveFromNode(skillNode);
                    passivesMap.put(pattern.getId(), pattern);
                }
                catch(NumberFormatException | NoSuchElementException e)
                {
                    Log.addSystem("passives_base_builder-fail msg///base node corrupted!");
                    e.printStackTrace();
                }
            }
        }
		
		return passivesMap;
	}
	/**
	 * Parses XML base file content to list with Effect objects
	 * @param baseFile
	 * @return ArrayList with all effects from base
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static Map<String, EffectPattern> getEffectsMap(String basePath) throws SAXException, IOException, ParserConfigurationException
	{
		Map<String, EffectPattern> effectsMap = new HashMap<>();
		
		if(!basePath.endsWith(".base"))
	    {
	    	basePath += ".base";
	    }
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document base = db.parse(basePath);
		
		NodeList nl = base.getDocumentElement().getChildNodes();
		for(int i = 0; i < nl.getLength(); i ++)
		{
			Node effectNode = nl.item(i);
			if(effectNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				EffectPattern pattern = EffectParser.getEffectFromNode(effectNode);
				effectsMap.put(pattern.getId(), pattern);
			}
		}
		
		return effectsMap;
	}
	/**
	 * Returns map with all scenarios from specified file with scenarios IDs
	 * @param scenariosList file with scenarios IDs from data/area/scenarios dir
	 * @return Map with scenarios as values and its IDs as keys
	 * @throws FileNotFoundException
	 */
	public static Map<String, Scenario> getScenarios(String scenariosDir, GameContainer gc) throws FileNotFoundException
	{
		Map<String, Scenario> scenariosMap = new HashMap<>();
		
		List<File> scenariosFiles = new ArrayList<>();

		File list = new File(scenariosDir + File.separator + "scenarios.list");
		
		Scanner scann = new Scanner(list);
		scann.useDelimiter(";\r?\n");
		while(scann.hasNext())
		{
			scenariosFiles.add(new File(scenariosDir + File.separator + scann.next() + ".scen"));
		}
		scann.close();
		
		for(File scenarioFile : scenariosFiles)
		{
			try 
			{
				Scenario sc = ScenarioParser.getScenarioFromFile(scenarioFile, gc);
				scenariosMap.put(sc.getId(), sc);
			} 
			catch (ParserConfigurationException | SAXException | IOException | SlickException| FontFormatException e) 
			{
				Log.addSystem("scenario_parser_fail msg///" + scenarioFile.toString());
			}
			
		}
		
		return scenariosMap;
	}
	/**
	 * Parses specified XML quest base file and returns map with quests ID's as keys and quest as values
	 * @param questsBase Base file name in data/quests dir
	 * @return Map with quests ID's as keys and quest as values
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Map<String, Quest> getQuests(String basePath) throws ParserConfigurationException, SAXException, IOException
	{
	    Map<String, Quest> questsMap = new HashMap<>();
	    
	    if(!basePath.endsWith(".base"))
	    {
	    	basePath += ".base";
	    }
	    
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    Document base = db.parse(basePath);
	        
	    NodeList questsList = base.getDocumentElement().getChildNodes();
	    for(int i = 0; i < questsList.getLength(); i ++)
	    {
	        Node questNode = questsList.item(i);
	        if(questNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
	        {
	            try
	            {
	            	Quest quest = QuestParser.getQuestFromNode(questNode);
		            questsMap.put(quest.getId(), quest);
	            }
	            catch(NumberFormatException e) 
	            {
	            	Log.addSystem("quests_builder_fail-msg///quest node corrupted!");
	            	continue;
	            }
	        }
	    }
	    
	    return questsMap;
	}
	/**
	 * Parses specified XML base with game objects to map with objects patterns as values and objects IDs as keys
	 * @param objectsBase Name of base file in data/objects
	 * @return Map with objects patterns as values and objects IDs as keys
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Map<String, ObjectPattern> getObjects(String basePath) throws ParserConfigurationException, SAXException, IOException
	{
		Map<String, ObjectPattern> objectsMap = new HashMap<>();
		
		if(!basePath.endsWith(".base"))
	    {
	    	basePath += ".base";
	    }
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document base = db.parse(basePath);
		
		NodeList objectsList = base.getDocumentElement().getChildNodes();
		for(int i = 0; i < objectsList.getLength(); i ++)
		{
			Node objectNode = objectsList.item(i);
			if(objectNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				try
				{
					ObjectPattern pattern = ObjectParser.getObjectFormNode(objectNode);
					objectsMap.put(pattern.getId(), pattern);
				}
				catch(NumberFormatException e)
				{
					//System.out.println("object_parser_fail_msg///base node corrupted");
					Log.addSystem("object_parser_fail_msg///base node corrupted");
				}
			}
		}
		
		return objectsMap;
	}
	/**
	 * Parses specified XML base and returns map with weapon patterns
	 * @param weaponsBase Name of base file in data/item dir
	 * @return Map with weapon patterns as values and weapons IDs as keys
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Map<String, WeaponPattern> getWeapons(String basePath) throws ParserConfigurationException, SAXException, IOException
	{
		Map<String, WeaponPattern> weaponsMap = new HashMap<>();
		
		if(!basePath.endsWith(".base"))
	    {
	    	basePath += ".base";
	    }
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document base = db.parse(basePath);
		
		NodeList weaponsList = base.getDocumentElement().getChildNodes();
		for(int i = 0; i < weaponsList.getLength(); i ++)
		{
			Node itemNode = weaponsList.item(i);
			if(itemNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				try
				{
					WeaponPattern wp = ItemParser.getWeaponFromNode(itemNode);
					weaponsMap.put(wp.getId(), wp);
				}
				catch(NumberFormatException e)
				{
					Log.addSystem("weapons_builder_fail_msg///base node corrupted");
				}
			}
		}
		
		return weaponsMap;
	}
	/**
	 * Parses specified XML base and returns map with armor patterns
	 * @param armorBase Name of XML base in data/item dir
	 * @return Map with armor patterns as values and armor IDs as keys
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Map<String, ArmorPattern> getArmors(String basePath) throws ParserConfigurationException, SAXException, IOException
	{
		Map<String, ArmorPattern> armorsMap = new HashMap<>();
		
		if(!basePath.endsWith(".base"))
	    {
	    	basePath += ".base";
	    }
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document base = db.parse(basePath);
		
		NodeList armorsList = base.getDocumentElement().getChildNodes();
		for(int i = 0; i < armorsList.getLength(); i ++)
		{
			Node itemNode = armorsList.item(i);
			if(itemNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				try
				{
					ArmorPattern ap = ItemParser.getArmorFromNode(itemNode);
					armorsMap.put(ap.getId(), ap);
				}
				catch(NumberFormatException e)
				{
					Log.addSystem("armors_builder_fail_msg///base node corrupted");
				}
			}
		}
		
		return armorsMap;
	}
	/**
	 * Parses specified XML base and returns map with trinkets patterns
	 * @param trinketsBase Name of XML base in data/item dir
	 * @return Map with trinkets patterns as values and its IDs as keys
	 */
	public static Map<String, TrinketPattern> getTrinkets(String basePath) throws ParserConfigurationException, SAXException, IOException
	{
		Map<String, TrinketPattern> trinketsMap = new HashMap<>();
		
		if(!basePath.endsWith(".base"))
	    {
	    	basePath += ".base";
	    }
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document base = db.parse(basePath);
		
		NodeList trinketsList = base.getDocumentElement().getChildNodes();
		for(int i = 0; i < trinketsList.getLength(); i ++)
		{
			Node itemNode = trinketsList.item(i);
			if(itemNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				try
				{
					TrinketPattern tp = ItemParser.getTrinketFromNode(itemNode);
					trinketsMap.put(tp.getId(), tp);
				}
				catch(NumberFormatException e)
				{
					Log.addSystem("trinItems_builder_fail_msg///base node currupted");
				}
			}
		}
		
		return trinketsMap;
	}
	/**
	 * Returns map with miscellaneous item patterns as values and its IDs as keys
	 * @param miscBase Name of misc items base in data/item 
	 * @return Map with miscellaneous item patterns as values and its IDs as keys
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Map<String, MiscPattern> getMisc(String basePath) throws ParserConfigurationException, SAXException, IOException
	{
		Map<String, MiscPattern> miscMap = new HashMap<>();
		
		if(!basePath.endsWith(".base"))
	    {
	    	basePath += ".base";
	    }
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document base = db.parse(basePath);
		
		NodeList miscList = base.getDocumentElement().getChildNodes();
		for(int i = 0; i < miscList.getLength(); i ++)
		{
			Node itemNode = miscList.item(i);
			if(itemNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				try
				{
					MiscPattern mp = ItemParser.getMiscFromNode(itemNode);
					miscMap.put(mp.getId(), mp);
				}
				catch(NumberFormatException e)
				{
					Log.addSystem("miscItems_builder_fail_msg///base node corrupted");
				}
			}
		}
		
		return miscMap;
	}
	/**
	 * Parses specified recipes base to list with recipe objects
	 * @param recipesBase String with recipes base name in data/item catalog
	 * @return List with recipes objects
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static List<Recipe> getRecipes(String basePath) throws ParserConfigurationException, SAXException, IOException
	{
		List<Recipe> recipes = new ArrayList<>();
		
		if(!basePath.endsWith(".base"))
	    {
	    	basePath += ".base";
	    }
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document base = db.parse(basePath);
		
		NodeList recipesList = base.getDocumentElement().getChildNodes();
		for(int i = 0; i < recipesList.getLength(); i ++)
		{
			Node recipeNode = recipesList.item(i);
			if(recipeNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				try
				{
					recipes.add(RecipeParser.getRecipeFromNode(recipeNode));
				}
				catch(NullPointerException e)
				{
					System.out.println("recipes_builder_fail_msg///" + e.getMessage());
					e.printStackTrace();
					recipes.remove(null);
				}
			}
		}
		
		return recipes;
	}
	/**
	 * Parses script with specified name in current module script dir
	 * @param scriptFileName Name of script in current module script dir
	 * @return List with commands for game CLI
	 * @throws FileNotFoundException
	 */
	public static Script getScript(String scriptFileName) throws FileNotFoundException
	{
		if(!scriptFileName.endsWith(".script"))
	    {
			scriptFileName += ".script";
	    }
		
	    String scriptPath = Module.getScriptsPath() + File.separator + scriptFileName;
	    File scriptFile = new File(scriptPath);
	    Scanner scann = new Scanner(scriptFile, "UTF-8");
	    
	    String scriptCode = "";
	    String ifCode = "";
	    String endCode = "";
	    
	    while(scann.hasNextLine())
	    {
	    	String line = scann.nextLine().replaceFirst("^\\s*", "");
	    	if(!line.startsWith("#"))
	    	{
	    		if(line.startsWith("script:"))
	    		{
	    			while(scann.hasNextLine())
	    			{
	    				line = scann.nextLine().replaceFirst("^\\s*", "");
		    			if(line.startsWith("if:") || line.startsWith("end:"))
		    			{
		    				break;
		    			}
		    			if(!line.startsWith("#"))
		    			{
		    				scriptCode += line;
		    			}
	    			}
	    		}
	    		if(line.startsWith("if:"))
	    		{
	    			while(scann.hasNextLine())
	    			{
	    				line = scann.nextLine().replaceFirst("^\\s*", "");
		    			if(line.startsWith("script:") || line.startsWith("end:"))
		    			{
		    				break;
		    			}
		    			if(!line.startsWith("#"))
		    			{
		    				ifCode += line;
		    			}
	    			}
	    		}
	    		if(line.startsWith("end:"))
	    		{
	    			while(scann.hasNextLine())
	    			{
	    				line = scann.nextLine().replaceFirst("^\\s*", "");
		    			if(line.startsWith("script:"))
		    			{
		    				break;
		    			}
		    			if(!line.startsWith("#"))
		    			{
		    				endCode += line;
		    			}
	    			}
	    		}
	    	}
	    }
	    scann.close();
	    return new Script(scriptFileName, scriptCode, ifCode, endCode);
	}
}
