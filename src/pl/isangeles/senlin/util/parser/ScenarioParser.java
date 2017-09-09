/*
 * ScenarioParser.java
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
import java.io.FileNotFoundException;
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
import org.newdawn.slick.tiled.TiledMap;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import pl.isangeles.senlin.core.Module;
import pl.isangeles.senlin.core.SimpleGameObject;
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
	public static Scenario getScenarioFromFile(File xmlScenario, GameContainer gc) 
			throws ParserConfigurationException, SAXException, IOException, SlickException, FontFormatException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document base = db.parse(xmlScenario);
		
		NodeList nl = base.getDocumentElement().getChildNodes();
		
		for(int i = 0; i < nl.getLength(); i ++)
		{
			Node scenarioNode = nl.item(i);
				
			if(scenarioNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
					Element scenarioE = (Element)scenarioNode;
					
					String id = scenarioE.getAttribute("id");
					
					Element mainareaE = (Element)scenarioE.getElementsByTagName("mainarea").item(0);
					
					Area mainArea = getAreaFromNode(mainareaE, gc);
					
					List<MobsArea> mobs = new ArrayList<>();
					Map<String, String[]> quests = new HashMap<>();
				
					List<Script> scripts = new ArrayList<>();
					Map<String, String> music = new HashMap<>();
					
					NodeList mobsNl = mainareaE.getElementsByTagName("mobs");
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
					
					NodeList questsNl = mainareaE.getElementsByTagName("quests").item(0).getChildNodes();
					for(int j = 0; j < questsNl.getLength(); j ++)
					{
						Node questNode = questsNl.item(j);
						if(questNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
						{
							Element questE = (Element)questNode;
							String[] trigger = new String[]{questE.getAttribute("qOn"), questE.getAttribute("trigger")};
							quests.put(questE.getTextContent(), trigger);
						}
					}
					
					Node scriptsNode = scenarioE.getElementsByTagName("scripts").item(0);
					scripts = getScriptsFromNode(scriptsNode);
					
					Node musicNode = mainareaE.getElementsByTagName("music").item(0);
					music = getMusicFromNode(musicNode);
					
					Node subareasNode = mainareaE.getElementsByTagName("subareas").item(0);
					List<Area> subAreas = getSubAreasFromNode(subareasNode, gc);
					
					return new Scenario(id, mainArea, subAreas, mobs, quests, scripts, music);	
			}
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
		List<SimpleGameObject> objects = getObjectsFromNode(objectsNode);
		
		Node exitsNode = areaE.getElementsByTagName("exits").item(0);
		List<Exit> exits = getExitsFromNode(exitsNode, gc);
		
		return new Area(id, map, mapFile, npcs, objects, exits);
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
					Position npcPos = new Position(npcE.getAttribute("position"));
					Character npc = NpcBase.spawnAt(npcE.getTextContent(), npcPos);
					npcs.add(npc);
				}
				catch(NumberFormatException | DOMException | IOException | FontFormatException | SlickException e)
				{
					Log.addSystem("scenario_builder_fail msg///npc node corrupted:" + npcNode.getTextContent());
					break;
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
	private static List<SimpleGameObject> getObjectsFromNode(Node objectsNode)
	{
		List<SimpleGameObject> objects = new ArrayList<>();
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
					Position objectPos = new Position(objectE.getAttribute("position"));
					SimpleGameObject object = ObjectsBase.get(objectId);
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
					Position exitToPos = new Position(exitE.getAttribute("to"));
					Position pos = new Position(exitE.getAttribute("position"));
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
		Position areaStart = new Position(mobsE.getAttribute("start"));
		Position areaEnd = new Position(mobsE.getAttribute("end"));
		Map<String, Integer> mobCon = new HashMap<>();
			
		NodeList mobNl = mobsNode.getChildNodes();
		for(int k = 0; k < mobNl.getLength(); k ++)
		{
			Node mobNode = mobNl.item(k);
			if(mobNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				Element mobE = (Element)mobNode;
				mobCon.put(mobE.getTextContent(), Integer.parseInt(mobE.getAttribute("max")));
			}
		}
			
		return new MobsArea(areaStart, areaEnd, mobCon);
	}
	/**
	 * Parses specified scripts node to list with scenario scripts
	 * @param scriptsNode XML document node, scripts node
	 * @return List with scenario scripts
	 * @throws FileNotFoundException
	 */
	private static List<Script> getScriptsFromNode(Node scriptsNode)
	{
		List<Script> scripts = new ArrayList<>();
		
		Element scriptsE = (Element)scriptsNode;
		NodeList sriptsNl = scriptsE.getElementsByTagName("script");
		for(int j = 0; j < sriptsNl.getLength(); j ++)
        {
            Node scriptNode = sriptsNl.item(j);
            if(scriptNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            {
            	Element scriptE = (Element)scriptNode;
                try 
                {
                    String scriptName = scriptE.getTextContent();
					scripts.add(DConnector.getScript(scriptName));
				} 
                catch (FileNotFoundException e) 
                {
                	Log.addSystem("scenario_builder_fail-msg///script node corrupted:" + scriptE.getTextContent());
					break;
				}
            }
        }
		return scripts;
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
