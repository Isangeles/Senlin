package pl.isangeles.senlin.data;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.xml.sax.SAXException;

import pl.isangeles.senlin.util.DConnector;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.core.Module;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.data.pattern.NpcPattern;
/**
 * Static class for NPC base, contains all game NPCs 
 * @author Isangeles
 *
 */
public class NpcBase 
{
	private static GameContainer gc;
	private static Map<String, NpcPattern> npcs = new HashMap<>();
	/**
	 * Private constructor to prevent initialization
	 */
	private NpcBase() {}
	/**
	 * Spawns character with specified id
	 * @param npcId NPC ID in base
	 * @return New character object
	 * @throws IOException
	 * @throws FontFormatException
	 * @throws SlickException
	 */
	public static Character spawn(String npcId) throws IOException, FontFormatException, SlickException
	{
		if(npcs.get(npcId) != null)
			return npcs.get(npcId).make(gc);
		else
			return null;
	}
	/**
	 * Spawns character with specified id at specified position
	 * @param npcId NPC ID in base
	 * @param pos Table with position on x-axis[0] and y-axis[1]
	 * @return New character object
	 * @throws IOException
	 * @throws FontFormatException
	 * @throws SlickException
	 */
	public static Character spawnAt(String npcId, Position pos) throws IOException, FontFormatException, SlickException
	{
		if(npcs.get(npcId) != null)
		{
			Character newChar = npcs.get(npcId).make(gc);
			newChar.setPosition(pos.x, pos.y);
			return newChar;
		}
		else
			return null;
	}
	/**
	 * Loads base
	 * @param gc Slick game container to create NPCs
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void load(String basePath, GameContainer gc) throws ParserConfigurationException, SAXException, IOException
	{
		NpcBase.gc = gc;
		npcs = DConnector.getNpcMap(basePath);
	}
}
