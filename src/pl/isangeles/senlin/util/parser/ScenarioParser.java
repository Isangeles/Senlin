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

import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.data.Scenario;
import pl.isangeles.senlin.data.area.MobsArea;
import pl.isangeles.senlin.util.Position;
/**
 * Static class for scenario XMLs parsing methods
 * @author Isangeles
 *
 */
public class ScenarioParser 
{

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
					Map<String, Position> exits = new HashMap<>();
					
					
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
								Log.addSystem("scenario_builder_mob_fail msg///mobs area corrupted");
								break;
							}
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
					
					return new Scenario(id, mapFile, npcs, mobs, exits);	
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
