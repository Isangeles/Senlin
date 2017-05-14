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
import pl.isangeles.senlin.core.Effect;
import pl.isangeles.senlin.core.Guild;
import pl.isangeles.senlin.core.Inventory;
import pl.isangeles.senlin.core.item.Armor;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.item.Weapon;
import pl.isangeles.senlin.data.EffectsBase;
import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.data.pattern.AttackPattern;
import pl.isangeles.senlin.data.pattern.EffectPattern;
import pl.isangeles.senlin.data.pattern.ItemPattern;
import pl.isangeles.senlin.data.pattern.NpcPattern;
import pl.isangeles.senlin.dialogue.Answer;
import pl.isangeles.senlin.dialogue.Dialogue;
import pl.isangeles.senlin.dialogue.DialoguePart;

/**
 * This class provides static methods giving access to external game data like items, NPCs, etc;
 * @author Isangeles
 *
 */
public final class DConnector 
{
	/**
	 * Private constructor to prevent initialization
	 */
	private DConnector(){}
	/**
	 * Build and returns item with the specified id 
	 * @param itemId Item id in base file
	 * @param gc Slick game container for item constructor
	 * @return New object of specific item from base file
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	@Deprecated
	private static Item getItem(String itemId, GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		Item item;
		switch(itemId.toCharArray()[0])
		{
		case 'w':
		{
			File base = new File("data" + File.separator + "item" + File.separator + "weaponBase");
			Scanner scann = new Scanner(base);
			scann.useDelimiter(";\r?\n");
			String baseLine;
			while((baseLine = scann.findInLine(itemId+".*[^;]")) == null)
			{
			    scann.nextLine();
			}
			scann.close();
			
			scann = new Scanner(baseLine);
			scann.useDelimiter(":|;");
			item =  new Weapon(scann.next(), 
					scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(), new Bonuses(scann.nextInt(), scann.nextInt(), 
					scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextFloat()), scann.nextInt(), scann.next(), scann.next(), gc);
			scann.close();
			return item;
		}
		default:
		{
			return null;
		}
		}
	}
	/**
	 * Builds and returns weapon from line of text
	 * @param line String with text in this form: 
	 * [id]:[name]:[basic info]:[type(0-5)]:[material(0-2)]:[value]:[min damage]:[max damage]:[bonus str]:[bonus con]:[bonus dex]:[bonus int]:[bonus wis]:[bonus dmg]:[bonus haste]:[required level]:[img file name];
	 * @param gc Slick game container
	 * @return New weapon
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public static Weapon getWeaponFromLine(String line, GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		Scanner scann = new Scanner(line);
		scann.useDelimiter(":|;");
		Weapon item =  new Weapon(scann.next(),
				scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(), new Bonuses(scann.nextInt(), scann.nextInt(), 
				scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextFloat()), scann.nextInt(), scann.next(), scann.next(), gc);
		scann.close();
		return item;
	}
	/**
	 * Builds and returns armor from line of text
	 * @param line String with text in this form: 
	 * [id]:[name]:[basic info]:[type(0-5)]:[material(0-4)]:[value]:[armor rating]:[bonus str]:[bonus con]:[bonus dex]:[bonus int]:[bonus wis]:[bonus dmg]:[bonus haste]:[required level]:[img file name];
	 * @param gc Slick game container
	 * @return New armor
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public static Armor getArmorFromLine(String line, GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		Scanner scann = new Scanner(line);
		scann.useDelimiter(":|;");
		Armor item = new Armor(scann.next(),
				scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(),
				new Bonuses(scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextFloat()), 
				scann.nextInt(), scann.next(), gc);
		scann.close();
		return item;
	}
	/**
	 * UNUSED Builds and returns list of all weapons in base file
	 * @param baseName name of base in data/item/ directory
	 * @param gc Slick game container for item constructor
	 * @return Linked list with items
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public static List<Weapon> getWeaponBase(String baseName, GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		List<Weapon> itemList = new LinkedList<>();
		
		File baseFile = new File("data" + File.separator + "item" + File.separator + baseName);
		Scanner scann = new Scanner(baseFile);
		scann.useDelimiter(";\r?\n");
		
		while(scann.hasNextLine())
		{
			itemList.add((Weapon)getWeaponFromLine(scann.nextLine(), gc));
		}
		scann.close();
		
		return itemList;
	}
	/**
	 * Returns map with lines from base file as values and lines item IDs as keys
	 * @return Map with item lines from base file
	 * @throws FileNotFoundException If file was not found
	 */
	public static Map<String, String> getItemsLinesMap(String itemBaseName) throws FileNotFoundException
	{
		Map<String, String> map = new HashMap<>();
		File baseFile = new File("data" + File.separator + "item" + File.separator + itemBaseName);
		Scanner scann = new Scanner(baseFile);
		scann.useDelimiter(";\r?\n");
		
		while(scann.hasNextLine())
		{
			String line = scann.nextLine();
			map.put(line.split(":|;")[0], line);
		}
		scann.close();
		return map;
	}
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
			//Puts all nodes inside npc node to list
			Node npcNode = nList.item(i);
			//Iterates over all nodes inside npc node
			if(npcNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				Element npc = (Element)npcNode;
				String id = npc.getAttribute("id");
				String attitude = npc.getAttribute("attitude");
				int guildID = Integer.parseInt(npc.getAttribute("guild")); //TODO catch parse exception
				NpcPattern npcP;
				
				String stats = npc.getElementsByTagName("stats").item(0).getTextContent();
				
				NodeList npcNodes = npcNode.getChildNodes();
				Node eqNode = npcNodes.item(1);
				Element eq = (Element)npcNodes;
				
				String head = eq.getElementsByTagName("head").item(0).getTextContent();
				String chest = eq.getElementsByTagName("chest").item(0).getTextContent();
				String hands = eq.getElementsByTagName("hands").item(0).getTextContent();
				String mainHand = eq.getElementsByTagName("mainhand").item(0).getTextContent();
				String offHand = eq.getElementsByTagName("offhand").item(0).getTextContent();
				String feet = eq.getElementsByTagName("feet").item(0).getTextContent();
				String neck = eq.getElementsByTagName("neck").item(0).getTextContent();
				String fingerA = eq.getElementsByTagName("finger1").item(0).getTextContent();
				String fingerB = eq.getElementsByTagName("finger2").item(0).getTextContent();
				String artifact = eq.getElementsByTagName("artifact").item(0).getTextContent();
				
				Element in = (Element)eq.getElementsByTagName("in").item(0);
				int gold = Integer.parseInt(in.getAttribute("gold")); //TODO catch parse exception
				
				List<ItemPattern> itemsIn = new LinkedList<>();
				
				for(int j = 0; j < in.getElementsByTagName("item").getLength(); j ++)
				{
					Element itemNode = (Element)in.getElementsByTagName("item").item(j);
					boolean ifRandom = Boolean.parseBoolean(itemNode.getAttribute("random"));
					String itemInId = itemNode.getTextContent();
					ItemPattern ip = new ItemPattern(itemInId, ifRandom);
				}
				
				npcP = new NpcPattern(id, attitude, guildID, stats, head, chest, hands, mainHand, offHand, feet,
									  neck, fingerA, fingerB, artifact, gold, itemsIn);
				npcMap.put(id, npcP);
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
			Node dialogNode = nl.item(i);
			if(dialogNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				Element dialog = (Element)dialogNode;
				String dialogId = dialog.getAttribute("id");
				String npcId = dialog.getAttribute("npc");
				List<DialoguePart> partsList = new ArrayList<>();
				
				NodeList parts = dialog.getChildNodes();
				for(int j = 0; j < dialog.getElementsByTagName("text").getLength(); j ++)
				{
					Node textNode = dialog.getElementsByTagName("text").item(j);
					//Element text = (Element)dialog.getElementsByTagName("text").item(j);
					partsList.add(getDialoguePartFromNode(textNode));
				}
				Dialogue dialogueOb = new Dialogue(dialogId, npcId, partsList);
				
				dialogsMap.put(npcId, dialogueOb);
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
                    
                    String imgName = skill.getElementsByTagName("icon").item(0).getTextContent();
                    int range = Integer.parseInt(skill.getElementsByTagName("range").item(0).getTextContent());
                    int cast = Integer.parseInt(skill.getElementsByTagName("cast").item(0).getTextContent());
                    int damage = Integer.parseInt(skill.getElementsByTagName("damage").item(0).getTextContent());
                    int manaCost = Integer.parseInt(skill.getElementsByTagName("manaCost").item(0).getTextContent());
                    int cooldown = Integer.parseInt(skill.getElementsByTagName("cooldown").item(0).getTextContent());
                    List<Effect> effects = new ArrayList<>();
                    
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
                    
                    AttackPattern pattern = new AttackPattern(id, imgName, type, damage, manaCost, cast, cooldown, useWeapon, range, effects);
                    attacksMap.put(id, pattern);
                }
                catch(NumberFormatException e)
                {
                    Log.addSystem("attacks_base_builder-fail msg///base node corrupted!");
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
					
					boolean endBool = false;
					if(answer.hasAttribute("end"))
						endBool = Boolean.parseBoolean(answer.getAttribute("end"));
					
					answersList.add(new Answer(answer.getTextContent(), endBool));
				}
			}
			return new DialoguePart(id, on, answersList);
		}
		else
		{
			Log.addSystem("dialog_builder_msg//fail");
		}
		answersList.add(new Answer("bye01", true));
		return new DialoguePart("err01", "error01", answersList);
	}
}
