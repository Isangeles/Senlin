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

import pl.isangeles.senlin.core.Attributes;
import pl.isangeles.senlin.core.Bonuses;
import pl.isangeles.senlin.core.Guild;
import pl.isangeles.senlin.core.Inventory;
import pl.isangeles.senlin.core.craft.Recipe;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.item.Armor;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.item.Weapon;
import pl.isangeles.senlin.data.EffectsBase;
import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.data.area.MobsArea;
import pl.isangeles.senlin.data.area.Scenario;
import pl.isangeles.senlin.data.pattern.ArmorPattern;
import pl.isangeles.senlin.data.pattern.AttackPattern;
import pl.isangeles.senlin.data.pattern.EffectPattern;
import pl.isangeles.senlin.data.pattern.MiscPattern;
import pl.isangeles.senlin.data.pattern.RandomItem;
import pl.isangeles.senlin.data.pattern.TrinketPattern;
import pl.isangeles.senlin.data.pattern.WeaponPattern;
import pl.isangeles.senlin.data.pattern.NpcPattern;
import pl.isangeles.senlin.data.pattern.ObjectPattern;
import pl.isangeles.senlin.dialogue.Answer;
import pl.isangeles.senlin.dialogue.Dialogue;
import pl.isangeles.senlin.dialogue.DialoguePart;
import pl.isangeles.senlin.quest.Quest;
import pl.isangeles.senlin.util.parser.DialogueParser;
import pl.isangeles.senlin.util.parser.ItemParser;
import pl.isangeles.senlin.util.parser.NpcParser;
import pl.isangeles.senlin.util.parser.ObjectParser;
import pl.isangeles.senlin.util.parser.QuestParser;
import pl.isangeles.senlin.util.parser.RecipeParser;
import pl.isangeles.senlin.util.parser.ScenarioParser;

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
	public static Map<String, NpcPattern> getNpcMap(String baseName) throws ParserConfigurationException, SAXException, IOException
	{
		Map<String, NpcPattern> npcMap = new HashMap<>();
		//Parses xml
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document base = builder.parse("data" + File.separator + "npc" + File.separator + baseName);
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
	public static Map<Integer, Guild> getGuildsMap(String baseFile) throws ParserConfigurationException, SAXException, IOException
	{
		Map<Integer, Guild> guildsMap = new HashMap<>();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document base = db.parse("data" + File.separator + "npc" + File.separator + baseFile);
		
		NodeList nl = base.getDocumentElement().getChildNodes();
		for(int i = 0; i < nl.getLength(); i ++)
		{
			Node guildNode = nl.item(i);
			if(guildNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				Element guild = (Element)guildNode;
				Integer guildId = Integer.parseInt(guild.getAttribute("id")); //TODO catch parse exception
				Guild guildOb = new Guild(guildId, guild.getAttribute("name"));
				guildsMap.put(guildId, guildOb);
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
	public static Map<String, Dialogue> getDialogueMap(String baseFile) throws ParserConfigurationException, SAXException, IOException
	{
		Map<String, Dialogue> dialogsMap = new HashMap<>();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document base = db.parse("data" + File.separator + "dialogues" + File.separator + baseFile);
		
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
	public static Map<String, AttackPattern> getAttacksMap(String baseFile) throws SAXException, IOException, ParserConfigurationException
	{
	    Map<String, AttackPattern> attacksMap = new HashMap<>();
	    
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document base = db.parse("data" + File.separator + "skills" + File.separator + baseFile);
	    
        NodeList nl = base.getDocumentElement().getChildNodes();
        //Iterating skills nodes
        for(int i = 0; i < nl.getLength(); i ++)
        {
            Node skillNode = nl.item(i);
            if(skillNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            {
                Element skill = (Element)skillNode;
                try
                {
                    String id = skill.getAttribute("id");
                    String type = skill.getAttribute("type");
                    boolean useWeapon = Boolean.parseBoolean(skill.getAttribute("useWeapon"));
                    String reqWeapon = skill.getAttribute("reqWeapon");
                    
                    String imgName = skill.getElementsByTagName("icon").item(0).getTextContent();
                    int range = Integer.parseInt(skill.getElementsByTagName("range").item(0).getTextContent());
                    int cast = Integer.parseInt(skill.getElementsByTagName("cast").item(0).getTextContent());
                    int damage = Integer.parseInt(skill.getElementsByTagName("damage").item(0).getTextContent());
                    int manaCost = Integer.parseInt(skill.getElementsByTagName("manaCost").item(0).getTextContent());
                    int cooldown = Integer.parseInt(skill.getElementsByTagName("cooldown").item(0).getTextContent());
                    List<Effect> effects = new ArrayList<>();
                    Element trainReqE = (Element)skill.getElementsByTagName("trainReq").item(0);
                    int reqGold = Integer.parseInt(trainReqE.getAttribute("gold"));
                    String reqAttText = trainReqE.getTextContent();
                    Scanner scann = new Scanner(reqAttText);
                    scann.useDelimiter(";");
                    Attributes reqAtt = new Attributes(scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt());
                    
                    NodeList enl = skill.getElementsByTagName("effects").item(0).getChildNodes();
                    for(int j = 0; j < enl.getLength(); j ++)
                    {
                        Node effectNode = enl.item(j);
                        //Iterating effects
                        if(effectNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
                        {
                            Element effect = (Element)effectNode;
                            String effectId = effect.getTextContent();
                            effects.add(EffectsBase.getEffect(effectId));
                        }
                    }
                    
                    AttackPattern pattern = new AttackPattern(id, imgName, type, damage, manaCost, cast, cooldown, useWeapon, reqWeapon, range, effects, reqGold, reqAtt);
                    attacksMap.put(id, pattern);
                }
                catch(NumberFormatException | NoSuchElementException e)
                {
                    Log.addSystem("attacks_base_builder-fail msg///base node corrupted!");

            		e.printStackTrace();
                    break;
                }
            }
        }
	    return attacksMap;
	}
	/**
	 * Parses XML base file content to list with Effect objects
	 * @param baseFile
	 * @return ArrayList with all effects from base
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static Map<String, EffectPattern> getEffectsMap(String baseFile) throws SAXException, IOException, ParserConfigurationException
	{
		Map<String, EffectPattern> effectsMap = new HashMap<>();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document base = db.parse("data" + File.separator + "skills" + File.separator + baseFile);
		
		NodeList nl = base.getDocumentElement().getChildNodes();
		for(int i = 0; i < nl.getLength(); i ++)
		{
			Node effectNode = nl.item(i);
			if(effectNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				Element effect = (Element)effectNode;
				
				try
				{
					String id = effect.getAttribute("id");
					int duration = Integer.parseInt(effect.getAttribute("duration"));
					String type = effect.getAttribute("type");
					String icon = effect.getAttribute("icon");
					
					int hpMod = Integer.parseInt(effect.getElementsByTagName("hpMod").item(0).getTextContent());
					int manaMod = Integer.parseInt(effect.getElementsByTagName("manaMod").item(0).getTextContent());
					
					Element attEle = (Element)effect.getElementsByTagName("attributesMod").item(0);
					int str = Integer.parseInt(attEle.getAttribute("str"));
					int con = Integer.parseInt(attEle.getAttribute("con"));
					int dex = Integer.parseInt(attEle.getAttribute("dex"));
					int inte = Integer.parseInt(attEle.getAttribute("int"));
					int wis = Integer.parseInt(attEle.getAttribute("wis"));
					Attributes attMod = new Attributes(str, con, dex, inte, wis);
					
					float hasteMod = Float.parseFloat(effect.getElementsByTagName("hasteMod").item(0).getTextContent());
					float dodgeMod = Float.parseFloat(effect.getElementsByTagName("dodgeMod").item(0).getTextContent());
					int dmgMod = Integer.parseInt(effect.getElementsByTagName("dmgMod").item(0).getTextContent());
					int dot = Integer.parseInt(effect.getElementsByTagName("dot").item(0).getTextContent());
					
					EffectPattern effectOb = new EffectPattern(id, icon, hpMod, manaMod, attMod, hasteMod, dodgeMod, dmgMod, dot, duration, type);
					effectsMap.put(id, effectOb);
				}
				catch(NumberFormatException e)
				{
					Log.addSystem("effects_base_builder-fail msg///base node corrupted!");
					e.printStackTrace();
					break;
				}
				
				
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
	public static Map<String, Scenario> getScenarios(String scenariosList) throws FileNotFoundException
	{
		Map<String, Scenario> scenariosMap = new HashMap<>();

		String scenariosDir = "data" + File.separator + "area" + File.separator + "scenarios" + File.separator;
		
		List<File> scenariosFiles = new ArrayList<>();
		File list = new File(scenariosDir + scenariosList);
		
		Scanner scann = new Scanner(list);
		scann.useDelimiter(";\r?\n");
		while(scann.hasNext())
		{
			scenariosFiles.add(new File(scenariosDir + scann.next()));
		}
		scann.close();
		
		for(File scenarioFile : scenariosFiles)
		{
			try 
			{
				Scenario sc = ScenarioParser.getScenarioFromFile(scenarioFile);
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
	public static Map<String, Quest> getQuests(String questsBase) throws ParserConfigurationException, SAXException, IOException
	{
	    Map<String, Quest> questsMap = new HashMap<>();
	    
	    String questsDir = "data" + File.separator + "quests" + File.separator + questsBase;
	    
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    Document base = db.parse(questsDir);
	        
	    NodeList questsList = base.getDocumentElement().getChildNodes();
	    for(int i = 0; i < questsList.getLength(); i ++)
	    {
	        Node questNode = questsList.item(i);
	        if(questNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
	        {
	            Quest quest = QuestParser.getQuestFromNode(questNode);
	            questsMap.put(quest.getId(), quest);
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
	public static Map<String, ObjectPattern> getObjects(String objectsBase) throws ParserConfigurationException, SAXException, IOException
	{
		Map<String, ObjectPattern> objectsMap = new HashMap<>();
		
		String objectsDir = "data" + File.separator + "objects" + File.separator + objectsBase;
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document base = db.parse(objectsDir);
		
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
					Log.addSystem("object_parser_fail_msg///base corrupted");
					break;
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
	public static Map<String, WeaponPattern> getWeapons(String weaponsBase) throws ParserConfigurationException, SAXException, IOException
	{
		Map<String, WeaponPattern> weaponsMap = new HashMap<>();
		
		String weaponsDir = "data" + File.separator + "item" + File.separator + weaponsBase;
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document base = db.parse(weaponsDir);
		
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
					Log.addSystem("weapons_builder_fail_msg///base corrupted");
					break;
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
	public static Map<String, ArmorPattern> getArmors(String armorBase) throws ParserConfigurationException, SAXException, IOException
	{
		Map<String, ArmorPattern> armorsMap = new HashMap<>();
		
		String armorsDir = "data" + File.separator + "item" + File.separator + armorBase;
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document base = db.parse(armorsDir);
		
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
					Log.addSystem("armors_builder_fail_msg///base corrupted");
					break;
				}
			}
		}
		
		return armorsMap;
	}
	/**
	 * TODO write trinkets map builder
	 * @param trinketsBase
	 * @return
	 */
	public static Map<String, TrinketPattern> getTrinkets(String trinketsBase)
	{
		Map<String, TrinketPattern> trinketsMap = new HashMap<>();
		
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
	public static Map<String, MiscPattern> getMisc(String miscBase) throws ParserConfigurationException, SAXException, IOException
	{
		Map<String, MiscPattern> miscMap = new HashMap<>();
		
		String miscDir = "data" + File.separator + "item" + File.separator + miscBase;
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document base = db.parse(miscDir);
		
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
					Log.addSystem("miscItems_builder_fail_msg///base corrupted");
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
	public static List<Recipe> getRecipes(String recipesBase) throws ParserConfigurationException, SAXException, IOException
	{
		List<Recipe> recipes = new ArrayList<>();
		
		String baseDir = "data" + File.separator + "item" + File.separator + recipesBase;
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document base = db.parse(baseDir);
		
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
					continue;
				}
			}
		}
		
		return recipes;
	}
}
