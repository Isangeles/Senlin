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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.SlickException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.data.area.MobsArea;
import pl.isangeles.senlin.data.area.Scenario;
import pl.isangeles.senlin.data.pattern.ObjectPattern;
import pl.isangeles.senlin.util.DConnector;
import pl.isangeles.senlin.util.Position;
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
	public static Scenario getScenarioFromFile(File xmlScenario) 
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
					String mapFile = scenarioE.getAttribute("map");
					Map<String, Position> npcs = new HashMap<>();
					List<MobsArea> mobs = new ArrayList<>();
					Map<String, String[]> quests = new HashMap<>();
					Map<String, Position> objects = new HashMap<>();
					Map<String, Position> exits = new HashMap<>();
					Map<String, List<String>> scripts = new HashMap<>();
					
					Node npcsNode = scenarioE.getElementsByTagName("npcs").item(0);
					NodeList npcNl = npcsNode.getChildNodes();
					for(int j = 0; j < npcNl.getLength(); j ++)
					{
						Node npcNode = npcNl.item(j);
						if(npcNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
						{
							try
							{
								Element npc = (Element)npcNode;
								Position npcPos = new Position(npc.getAttribute("position"));
								npcs.put(npc.getTextContent(), npcPos);
							}
							catch(NumberFormatException e)
							{
								Log.addSystem("scenario_builder_fail msg///npc positions corrupted");
								break;
							}
						}
					}
					
					NodeList mobsNl = scenarioE.getElementsByTagName("mobs");
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
					
					NodeList questsNl = scenarioE.getElementsByTagName("quests").item(0).getChildNodes();
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
					
					NodeList objectsNl = scenarioE.getElementsByTagName("objects").item(0).getChildNodes();
					for(int j = 0; j < objectsNl.getLength(); j ++)
					{
						Node objectNode = objectsNl.item(j);
						if(objectNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
						{
							Element objectE = (Element)objectNode;
							objects.put(objectE.getTextContent(), new Position(objectE.getAttribute("position")));
						}
					}
					
					NodeList exitNl = scenarioE.getElementsByTagName("exits").item(0).getChildNodes();
					for(int j = 0; j < exitNl.getLength(); j ++)
					{
						Node exitNode = exitNl.item(j);
						if(exitNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
						{
							Element exitE = (Element)exitNode;
							exits.put(exitE.getTextContent(), new Position(exitE.getAttribute("position")));
						}
					}
					
					NodeList sriptsNl = scenarioE.getElementsByTagName("scripts").item(0).getChildNodes();
					for(int j = 0; j < sriptsNl.getLength(); j ++)
                    {
                        Node scriptNode = sriptsNl.item(j);
                        if(scriptNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
                        {
                            Element scriptE = (Element)scriptNode;
                            String scriptName = scriptE.getTextContent();
                            List<String> script = DConnector.getScript(scriptName);
                            scripts.put(scriptName, script);
                        }
                    }
					
					return new Scenario(id, mapFile, npcs, mobs, quests, objects, exits, scripts);	
			}
		}
		
		return null;
			
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
}
