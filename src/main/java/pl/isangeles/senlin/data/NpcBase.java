/*
 * NpcBase.java
 *
 * Copyright 2017-2018 Dariusz Sikora <dev@isangeles.pl>
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
package pl.isangeles.senlin.data;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.xml.parsers.ParserConfigurationException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.xml.sax.SAXException;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.data.area.Area;
import pl.isangeles.senlin.data.pattern.NpcPattern;
import pl.isangeles.senlin.util.DConnector;
import pl.isangeles.senlin.util.TilePosition;

/**
 * Static class for NPC base, contains all game NPCs
 *
 * @author Isangeles
 */
public class NpcBase {
  private static GameContainer gc;
  private static Map<String, NpcPattern> npcs = new HashMap<>();
  /** Private constructor to prevent initialization */
  private NpcBase() {}
  /**
   * Spawns character with specified id
   *
   * @param npcId NPC ID in base
   * @return New character object
   * @throws IOException
   * @throws FontFormatException
   * @throws SlickException
   */
  public static Character spawn(String npcId)
      throws IOException, FontFormatException, SlickException, NoSuchElementException {
    if (npcs.get(npcId) != null) return npcs.get(npcId).make(gc);
    else throw new NoSuchElementException("No such NPC found");
  }
  /**
   * Spawns new instance of game character with specified id in specified area and at specified
   * position
   *
   * @param npcId NPC ID in base
   * @param area Area for NPC
   * @param pos Table with position on x-axis[0] and y-axis[1]
   * @return New character object
   * @throws IOException
   * @throws FontFormatException
   * @throws SlickException
   */
  public static Character spawnIn(String npcId, Area area, TilePosition tilePos)
      throws IOException, FontFormatException, SlickException, NoSuchElementException {
    if (npcs.get(npcId) != null) {
      Character newChar = npcs.get(npcId).make(gc);
      newChar.setArea(area);
      newChar.setPosition(tilePos);
      return newChar;
    } else throw new NoSuchElementException("No such NPC found");
  }
  /**
   * Spawns new instance of game character with specified id at specified position
   *
   * @param npcId NPC ID in base
   * @param pos Table with position on x-axis[0] and y-axis[1]
   * @return New character object
   * @throws IOException
   * @throws FontFormatException
   * @throws SlickException
   */
  public static Character spawnAt(String npcId, TilePosition tilePos)
      throws IOException, FontFormatException, SlickException, NoSuchElementException {
    if (npcs.get(npcId) != null) {
      Character newChar = npcs.get(npcId).make(gc);
      newChar.setPosition(tilePos);
      return newChar;
    } else throw new NoSuchElementException("No such NPC found");
  }
  /**
   * Loads base
   *
   * @param gc Slick game container to create NPCs
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws IOException
   */
  public static void load(String basePath, GameContainer gc)
      throws ParserConfigurationException, SAXException, IOException {
    NpcBase.gc = gc;
    npcs = DConnector.getNpcMap(basePath);
  }
}
