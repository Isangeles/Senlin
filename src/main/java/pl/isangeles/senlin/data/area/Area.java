/*
 * Area.java
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
package pl.isangeles.senlin.data.area;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.isangeles.senlin.audio.AudioPlayer;
import pl.isangeles.senlin.core.TargetableObject;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.quest.ObjectiveTarget;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.Size;

/**
 * Class for game world areas
 *
 * @author Isangeles
 */
public class Area implements SaveElement, ObjectiveTarget {
  private String id;
  private TiledMap map;
  private String mapFileName;
  private Size size;
  private Set<Character> characters = new HashSet<>();
  private List<TargetableObject> objects = new ArrayList<>();
  private List<Exit> exits = new ArrayList<>();
  private Map<String, String> idleMusic = new HashMap<>();
  private Map<String, String> combatMusic = new HashMap<>();
  private List<SpawnArea> spawnAreas = new ArrayList<>();
  /**
   * Empty area constructor
   *
   * @param id Area ID
   * @param map Area map
   * @param mapFileName Name of area map file
   */
  public Area(String id, TiledMap map, String mapFileName) {
    this.id = id;
    this.map = map;
    this.mapFileName = mapFileName;

    size = new Size(map.getWidth(), map.getHeight());
  }
  /**
   * Area constructor
   *
   * @param id Area ID
   * @param map Tiled map
   * @param mapFileName Name of tiled map file
   * @param npcs List with NPCs
   * @param objects List with objects
   * @param exits List exits
   * @param idleMusic
   * @param combatMusic
   * @param spawnAreas List with mobs
   */
  public Area(
      String id,
      TiledMap map,
      String mapFileName,
      Collection<Character> npcs,
      List<TargetableObject> objects,
      List<Exit> exits,
      Map<String, String> idleMusic,
      Map<String, String> combatMusic,
      List<SpawnArea> spawnAreas) {
    this.id = id;
    this.map = map;
    this.mapFileName = mapFileName;
    size = new Size(map.getTileWidth() * map.getWidth(), map.getTileHeight() * map.getHeight());
    this.characters.addAll(npcs);
    this.objects = objects;
    this.exits = exits;

    this.combatMusic = combatMusic;
    this.idleMusic = idleMusic;

    this.spawnAreas = spawnAreas;

    for (Character npc : characters) {
      npc.setArea(this);
    }
  }
  /**
   * Returns area ID
   *
   * @return String with area ID
   */
  public String getId() {
    return id;
  }
  /**
   * Returns area map
   *
   * @return Tiled map of this area
   */
  public TiledMap getMap() {
    return map;
  }
  /**
   * Returns map file name
   *
   * @return String with map file name
   */
  public String getMapName() {
    return mapFileName;
  }
  /**
   * Returns area map size
   *
   * @return Tuple with width and height of this area map
   */
  public Size getMapSize() {
    return new Size(size.width, size.height);
  }
  /**
   * Returns all characters without players in NEW collection
   *
   * @return Collection with game characters
   */
  public Collection<Character> getNpcs() {
    Collection<Character> charactersToReturn = new ArrayList<>();
    for (Character character : characters) {
      if (!character.equals(Global.getPlayer())) charactersToReturn.add(character);
    }
    return charactersToReturn;
  }
  /**
   * Returns all characters in area
   *
   * @return Collection with game characters
   */
  public Collection<Character> getCharacters() {
    return characters;
  }
  /**
   * Returns all characters except specified character
   *
   * @param exceptChar Character to retain from area characters
   * @return Collection with all area characters except specified character
   */
  public Collection<Character> getCharactersExcept(Character exceptChar) {
    Collection<Character> charactersToReturn = new ArrayList<>();
    for (Character character : characters) {
      if (!character.equals(exceptChar)) charactersToReturn.add(character);
    }
    return charactersToReturn;
  }

  /**
   * Returns all characters except specified character
   *
   * @param exceptChar Character to retain from area characters
   * @return Collection with all area characters except specified character
   */
  public Collection<Character> getCharactersExcept(String exceptCharSerialId) {
    Collection<Character> charactersToReturn = new ArrayList<>();
    for (Character character : characters) {
      if (!character.getSerialId().equals(exceptCharSerialId)) charactersToReturn.add(character);
    }
    return charactersToReturn;
  }
  /**
   * Returns area objects
   *
   * @return List with simple game objects
   */
  public List<TargetableObject> getObjects() {
    return objects;
  }
  /**
   * Returns area exits
   *
   * @return List with exits
   */
  public List<Exit> getExits() {
    return exits;
  }
  /**
   * Returns all nearby characters in area
   *
   * @param character A character around which to look for other nearby characters
   * @return List with all nearby characters
   */
  public List<Character> getNearbyCharacters(Character character) {
    List<Character> nearbyCharacters = new ArrayList<>();
    /*
    if(character.getRangeFrom(player.getPosition()) < 200)
    	nearbyCharacters.add(player);
    */
    for (Character npc : characters) {
      if (npc != character
          && npc.getInvisibilityLevel() < 1
          && character.getRangeFrom(npc.getPosition()) < 200) nearbyCharacters.add(npc);
    }

    return nearbyCharacters;
  }
  /**
   * Checks if this area has any music tracks
   *
   * @return True if this area has its own music, false otherwise
   */
  public boolean hasMusic() {
    if (idleMusic.size() > 0 || combatMusic.size() > 0) return true;
    else return false;
  }
  /**
   * Sets specified list as this area NPCs list
   *
   * @param charcters List with game characters for this area
   */
  public void setCharacters(Collection<Character> characters) {
    this.characters.clear();
    this.characters.addAll(characters);
    for (Character character : this.characters) {
      character.setArea(this);
    }
  }
  /**
   * Sets specified list as this area objects list
   *
   * @param objects List with simple game objects for this area
   */
  public void setObjects(List<TargetableObject> objects) {
    this.objects = objects;
  }
  /**
   * Adds all music tracks for this scenario to specified audio player
   *
   * @param player Audio player
   * @throws IOException
   * @throws SlickException
   */
  public void addMusic(AudioPlayer player) {
    for (String track : idleMusic.keySet()) {
      if (track.equals("$all")) {
        player.addAllTo("idle", idleMusic.get(track));
      } else {
        player.addTo("idle", idleMusic.get(track), track);
      }
    }
    for (String track : combatMusic.keySet()) {
      if (track.equals("$all")) {
        player.addAllTo("combat", combatMusic.get(track));
      } else {
        player.addTo("combat", combatMusic.get(track), track);
      }
    }
  }
  /**
   * Spawns all mobs in area
   *
   * @return True if all mobs was successfully spawned, false otherwise
   * @throws ArrayIndexOutOfBoundsException
   * @throws IOException
   * @throws FontFormatException
   * @throws SlickException
   */
  public boolean spawnObjects()
      throws ArrayIndexOutOfBoundsException, IOException, FontFormatException, SlickException {
    boolean spawn = true;
    for (SpawnArea area : spawnAreas) {
      spawn = area.spawn(this);
    }
    return spawn;
  }
  /**
   * Respawns all 'respawnable' objects in area
   *
   * @return True if objects was successfully respawned
   * @throws ArrayIndexOutOfBoundsException
   * @throws IOException
   * @throws FontFormatException
   * @throws SlickException
   */
  public boolean respawnObjects()
      throws ArrayIndexOutOfBoundsException, IOException, FontFormatException, SlickException {
    boolean spawn = true;
    for (SpawnArea area : spawnAreas) {
      if (area.isRespawnable()) {
        spawn = area.spawn(this);
      }
    }
    return spawn;
  }
  /**
   * Checks if specified xy positions are 'moveable' on game world map
   *
   * @param x Position on x axis
   * @param y Position on y axis
   * @return True if position are moveable, false otherwise
   */
  public boolean isMovable(int x, int y) {
    try {
      if (map.getTileId(x / map.getTileWidth(), y / map.getTileHeight(), 2) != 0
          || // blockground layer
          map.getTileId(x / map.getTileWidth(), y / map.getTileHeight(), 3) != 0
          || // water layer
          map.getTileId(x / map.getTileWidth(), y / map.getTileHeight(), 4) != 0
          || // trees layer
          map.getTileId(x / map.getTileWidth(), y / map.getTileHeight(), 5) != 0
          || // buildings layer
          map.getTileId(x / map.getTileWidth(), y / map.getTileHeight(), 6) != 0) // objects layer
      return false;
    } catch (ArrayIndexOutOfBoundsException e) {
      return false;
    }

    return true;
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.data.save.SaveElement#getSave(org.w3c.dom.Document)
   */
  @Override
  public Element getSave(Document doc) {
    Element areaE = doc.createElement("area");

    areaE.setAttribute("id", id);
    areaE.setAttribute("map", mapFileName);

    Element npcsE = doc.createElement("npcs");
    for (Character npc : getCharactersExcept("player_0")) {
      npcsE.appendChild(npc.getSave(doc));
    }
    areaE.appendChild(npcsE);

    Element objectsE = doc.createElement("objects");
    for (TargetableObject object : objects) {
      objectsE.appendChild(object.getSave(doc));
    }
    areaE.appendChild(objectsE);

    return areaE;
  }
}
