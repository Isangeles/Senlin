/*
 * ScenarioParser.java
 * 
 * Copyright 2017-2018 Dariusz Sikora <darek@pc-solus>
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import pl.isangeles.senlin.core.Module;
import pl.isangeles.senlin.core.TargetableObject;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.cli.Script;
import pl.isangeles.senlin.data.NpcBase;
import pl.isangeles.senlin.data.ObjectsBase;
import pl.isangeles.senlin.data.area.Area;
import pl.isangeles.senlin.data.area.Exit;
import pl.isangeles.senlin.data.area.MobsArea;
import pl.isangeles.senlin.data.area.Scenario;
import pl.isangeles.senlin.util.DConnector;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.util.Size;
import pl.isangeles.senlin.util.TilePosition;
/**
 * Static class for scenario XMLs parsing methods
 * @author Isangeles
 *
 */
public class ScenarioParser 
{
    /**
     * Private constructor to prevent initialization
     */
	private ScenarioParser() {}
	/**
	 * Parses XML file to scenario object
	 * @param xmlScenario Properly XML file
	 * @return New scenario object
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws SlickException
	 * @throws FontFormatException
	 */
	public static Scenario getScenarioFromNode(Node scenarioNode, GameContainer gc) throws SlickException, IOException, FontFormatException 
	{		
		if(scenarioNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
		{
			Element scenarioE = (Element)scenarioNode;
			
			String id = scenarioE.getAttribute("id");
			
			Element mainareaE = (Element)scenarioE.getElementsByTagName("mainarea").item(0);
			
			Area mainArea = getAreaFromNode(mainareaE, gc);
			
			Map<String, String> quests = new HashMap<>();
		
			List<Script> scripts = new ArrayList<>();
									
			NodeList questsNl = mainareaE.getElementsByTagName("quests").item(0).getChildNodes();
			for(int j = 0; j < questsNl.getLength(); j ++)
			{
				Node questNode = questsNl.item(j);
				if(questNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
				{
					Element questE = (Element)questNode;
					String trigger = questE.getAttribute("trigger");
					quests.put(questE.getTextContent(), trigger);
				}
			}
			
			Node scriptsNode = scenarioE.getElementsByTagName("scripts").item(0);
			scripts = ScriptParser.getScriptsFromNode(scriptsNode);
			
			
			Node subareasNode = mainareaE.getElementsByTagName("subareas").item(0);
			List<Area> subAreas = getSubAreasFromNode(subareasNode, gc);
			
			return new Scenario(id, mainArea, subAreas, quests, scripts);	
		}
		return null;
	}
	/**
	 * Parses specified area node to area object
	 * @param areaNode XML document node, area node
	 * @return Area object from specified node
	 * @throws SlickException
	 */
	private static Area getAreaFromNode(Node areaNode, GameContainer gc) throws SlickException
	{
		Element areaE = (Element)areaNode;
		String id = areaE.getAttribute("id");
		String mapFile = areaE.getAttribute("map");

		TiledMap map = new TiledMap(Module.getAreaPath() + File.separator + "map" + File.separator + mapFile);

		Node npcsNode = areaE.getElementsByTagName("npcs").item(0);
		List<Character> npcs = getNpcsFromNode(npcsNode);
		
		Node objectsNode = areaE.getElementsByTagName("objects").item(0);
		List<TargetableObject> objects = getObjectsFromNode(objectsNode);
		
		Node exitsNode = areaE.getElementsByTagName("exits").item(0);
		List<Exit> exits = getExitsFromNode(exitsNode, gc);
		
		Element musicE = (Element)areaE.getElementsByTagName("music").item(0);
		
		Node idleNode = musicE.getElementsByTagName("idle").item(0);
		Map<String, String> idleMusic = getMusicFromNode(idleNode);
		
		Node combatNode = musicE.getElementsByTagName("combat").item(0);
		Map<String, String> combatMusic = getMusicFromNode(combatNode);
		
		List<MobsArea> mobs = new ArrayList<>();
		NodeList mobsNl = areaE.getElementsByTagName("mobs");
		for(int j = 0; j < mobsNl.getLength(); j ++)
		{
			Node mobsNode = mobsNl.item(j);
			if(mobsNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				try
				{
					mobs.add(getMobsAreaFromNode(mobsNode));
				}
				catch(NumberFormatException e)
				{
					Log.addSystem("scenario_builder_fail-msg///mobs area corrupted");
					break;
				}
			}
		}
		
		return new Area(id, map, mapFile, npcs, objects, exits, idleMusic, combatMusic, mobs);
	}
	/**
	 * Parses specified npcs node to list with game characters
	 * @param npcsNode XML document node, npcs node 
	 * @return List with game characters from specified node
	 */
	private static List<Character> getNpcsFromNode(Node npcsNode)
	{
		List<Character> npcs = new ArrayList<>();
		NodeList npcNl = npcsNode.getChildNodes();
		for(int j = 0; j < npcNl.getLength(); j ++)
		{
			Node npcNode = npcNl.item(j);
			if(npcNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				try
				{
					Element npcE = (Element)npcNode;
					String npcId = npcE.getTextContent();
					TilePosition npcPos = new TilePosition(npcE.getAttribute("position"));
					Character npc = NpcBase.spawnAt(npcId, npcPos);
					if(npcE.hasAttribute("defaultPosition"))
						npc.setDefaultPosition(new TilePosition(npcE.getAttribute("defaultPosition")));
					else
						npc.setDefaultPosition(npcPos);
					npcs.add(npc);
				}
				catch(NumberFormatException | DOMException | IOException | FontFormatException | SlickException e)
				{
					System.err.println("scenario_builder_fail_msg///npc node corrupted:" + npcNode.getTextContent());
					Log.addSystem("scenario_builder_fail_msg///npc node corrupted:" + npcNode.getTextContent());
					continue;
				}
				catch(NoSuchElementException e)
				{
					System.err.println("scenario_builder_fail_msg///no such NPC in base:" + npcNode.getTextContent());
					Log.addSystem("scenario_builder_fail_msg///no such NPC in base:" + npcNode.getTextContent());
					continue;
				}
			}
		}
		return npcs;
	}
	/**
	 * Parses specified objects node to list with simple game objects
	 * @param objectsNode XML document node, objects node
	 * @return List with simple game objects from specified node
	 */
	private static List<TargetableObject> getObjectsFromNode(Node objectsNode)
	{
		List<TargetableObject> objects = new ArrayList<>();
		NodeList objectsNl = objectsNode.getChildNodes();
		for(int j = 0; j < objectsNl.getLength(); j ++)
		{
			Node objectNode = objectsNl.item(j);
			if(objectNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				try
				{

					Element objectE = (Element)objectNode;
					String objectId = objectE.getTextContent();
					TilePosition objectPos = new TilePosition(objectE.getAttribute("position"));
					TargetableObject object = ObjectsBase.get(objectId);
					object.setPosition(objectPos);
					objects.add(object);
				}
				catch(NumberFormatException | SlickException | IOException | FontFormatException e)
				{
					Log.addSystem("scenario_builder_fail msg///object node corrupted:" + objectNode.getTextContent());
					break;
				}
			}
		}
		return objects;
	}
	/**
	 * Parses specified exits node to list with game area exits
	 * @param exitsNode XML document node, exits node
	 * @return List with game area exits from specified node
	 */
	private static List<Exit> getExitsFromNode(Node exitsNode, GameContainer gc)
	{
		List<Exit> exits = new ArrayList<>();
		NodeList exitNl = exitsNode.getChildNodes();
		for(int j = 0; j < exitNl.getLength(); j ++)
		{
			Node exitNode = exitNl.item(j);
			if(exitNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				try
				{
					Element exitE = (Element)exitNode;
					String exitToId = exitE.getTextContent();
					TilePosition exitToPos = new TilePosition(exitE.getAttribute("to"));
					TilePosition pos = new TilePosition(exitE.getAttribute("position"));
					String texture = exitE.getAttribute("texture");
					Exit exit;
					if(texture == "")
						exit = new Exit(pos, exitToId, exitToPos, gc);
					else
						exit = new Exit(pos, texture, exitToId, exitToPos, gc);
					
					exits.add(exit);
				}
				catch(SlickException | IOException | FontFormatException e)
				{
					Log.addSystem("scenario_builder_fail msg///exit node corrupted:" + exitNode.getTextContent());
					break;
				}
			}
		}
		return exits;
	}
	/**
	 * Parses specified subareas node to list with game world areas
	 * @param subareasNode XML document node, subareas node
	 * @return List with game world areas from specified node
	 */
	private static List<Area> getSubAreasFromNode(Node subareasNode, GameContainer gc)
	{
		List<Area> subAreas = new ArrayList<>();
		if(subareasNode == null)
			return subAreas;
		
		Element subareasE = (Element)subareasNode;
		NodeList subareasNl = subareasE.getElementsByTagName("area");
		for(int i = 0; i < subareasNl.getLength(); i ++)
		{
			Node areaNode = subareasNl.item(i);
			if(areaNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{	
				try 
				{
					Area area = getAreaFromNode(areaNode, gc);
					subAreas.add(area);
				} 
				catch (SlickException e) 
				{
					Log.addSystem("scenario_buildr_fail-msg///area node corrupted");
					break;
				}
			}
		}
		return subAreas;
	}
	/**
	 * Parses node to MobsArea object
	 * @param mobsNode XML mobs node
	 * @return MobsArea object
	 * @throws NumberFormatException
	 */
	private static MobsArea getMobsAreaFromNode(Node mobsNode) throws NumberFormatException
	{
		Element mobsE = (Element)mobsNode;
		TilePosition areaStart = new TilePosition(mobsE.getAttribute("start"));
		TilePosition areaEnd = new TilePosition(mobsE.getAttribute("end"));
		boolean respawn = Boolean.parseBoolean(mobsE.getAttribute("respawn"));
		Map<String, Integer[]> mobCon = new HashMap<>();
			
		NodeList mobNl = mobsNode.getChildNodes();
		for(int k = 0; k < mobNl.getLength(); k ++)
		{
			Node mobNode = mobNl.item(k);
			if(mobNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				Element mobE = (Element)mobNode;
				int min = 1;
				int max = 1;
				
				if(mobE.hasAttribute("min"))
					min = Integer.parseInt(mobE.getAttribute("min"));
				if(mobE.hasAttribute("max"))
					max = Integer.parseInt(mobE.getAttribute("max"));
				
				mobCon.put(mobE.getTextContent(), new Integer[]{min, max});
			}
		}
			
		return new MobsArea(areaStart, areaEnd, mobCon, respawn);
	}
	/**
	 * Parses specified music node to map with tracks names as keys and tracks categories as values
	 * @param musicNode XML document node, music node
	 * @return Map with tracks names as keys and tracks categories as values from specified node
	 */
	private static Map<String, String> getMusicFromNode(Node musicNode)
	{
		Map<String, String> music = new HashMap<>();
		
		Element musicE = (Element)musicNode;
		NodeList musicsNl = musicE.getElementsByTagName("track");
		for(int j = 0; j < musicsNl.getLength(); j ++)
        {
            Node trackNode = musicsNl.item(j);
            if(trackNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            {
                Element trackE = (Element)trackNode;
                String trackName = trackE.getTextContent();
                String trackCategory = trackE.getAttribute("category");
                music.put(trackName, trackCategory);
            }
        }
		
		return music;
	}
}
