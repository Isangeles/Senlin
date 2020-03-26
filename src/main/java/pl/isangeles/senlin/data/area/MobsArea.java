/*
 * MobsArea.java
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
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.newdawn.slick.SlickException;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.data.NpcBase;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.util.TilePosition;

/**
 * Class for spawn areas with mobs
 *
 * @author Isangelse
 */
public class MobsArea extends SpawnArea {
  /**
   * Mobs area constructor
   *
   * @param startPoint XY starting point of area
   * @param endPoint XY ending point of area
   * @param objects Map with objects IDs as keys and its max amount in area as values
   * @param respawnable True if all object from are should be respawned at game world update
   */
  public MobsArea(
      TilePosition startPoint,
      TilePosition endPoint,
      Map<String, Integer[]> objects,
      boolean respawnable) {
    super(startPoint, endPoint, objects, respawnable);
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.data.area.SpawnArea#spawn(pl.isangeles.senlin.data.area.Area)
   */
  public boolean spawn(Area area)
      throws IOException, FontFormatException, SlickException, ArrayIndexOutOfBoundsException {
    clearObjects();

    Random rng = new Random();

    for (String mobId : objects.keySet()) {
      int min = objects.get(mobId)[0];
      int max = objects.get(mobId)[1];

      for (int i = spawnedObjects.size(); i < min + rng.nextInt(max); i++) {
        TilePosition mobTile =
            new TilePosition(
                startPoint.row + rng.nextInt(endPoint.row),
                startPoint.column + rng.nextInt(endPoint.column));
        Position mobPostion = mobTile.asPosition();
        while (!area.isMovable(
            mobTile.row * TilePosition.TILE_WIDTH,
            mobTile.column
                * TilePosition
                    .TILE_HEIGHT)) // TODO possibility of infinite loop if all area won't be
                                   // 'moveable'
        {
          mobTile =
              new TilePosition(
                  startPoint.row + rng.nextInt(endPoint.row),
                  startPoint.column + rng.nextInt(endPoint.column));
        }

        Character mob = NpcBase.spawnIn(mobId, area, mobTile);
        mob.setDefaultPosition(mobTile);
        if (mob != null) {
          spawnedObjects.add(mob);
          // Log.addSystem(mobId + " spawned"); // DEBUG
        }
      }
    }

    List<Character> mobs = new ArrayList<>();
    for (Targetable o : spawnedObjects) {
      if (Character.class.isInstance(o)) {
        Character m = (Character) o;
        mobs.add(m);
      }
    }
    return area.getCharacters().addAll(mobs);
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.data.area.SpawnArea#clearObjects()
   */
  protected boolean clearObjects() {
    List<Character> deadMobs = new ArrayList<>();
    for (Targetable o : spawnedObjects) {
      if (Character.class.isInstance(o)) {
        Character mob = (Character) o;
        if (!mob.isLive()) deadMobs.add(mob);
      }
    }
    return spawnedObjects.removeAll(deadMobs);
  }
}
