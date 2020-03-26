/*
 * Race.java
 *
 * Copyright 2017 Dariusz Sikora <dev@isangeles.pl>
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
package pl.isangeles.senlin.core.character;

import pl.isangeles.senlin.util.TConnector;

/**
 * Class for characters races
 *
 * @author Isangeles
 */
public enum Race {
  HUMAN,
  GOBLIN,
  WOLF;
  /**
   * Converts specified race name to race enumeration
   *
   * @param raceName String with race name
   * @return Race with specified name
   */
  public static Race fromId(String raceId) {
    switch (raceId) {
      case "human":
        return Race.HUMAN;
      case "goblin":
        return Race.GOBLIN;
      case "wolf":
        return Race.WOLF;
      default:
        return Race.HUMAN;
    }
  }
  /**
   * Returns race ID
   *
   * @return String with race ID
   */
  @Override
  public String toString() {
    switch (this) {
      case HUMAN:
        return "human";
      case GOBLIN:
        return "goblin";
      case WOLF:
        return "wolf";
      default:
        return "human";
    }
  }
  /**
   * Returns localized race name
   *
   * @return String with race name
   */
  public String getName() {
    return TConnector.getText("ui", "race_" + toString());
  }
  /**
   * Returns default spritesheet name for this race
   *
   * @return String with spritesheet name
   */
  public String getDefaultSpritesheet(Gender sex) {
    switch (this) {
      case HUMAN:
        return sex.getSSName("cloth-1222211-80x90.png");
      case GOBLIN:
        return "goblin-1222211-80x90.png";
      case WOLF:
        return "wolf-1222211-80x90.png";
      default:
        return sex.getSSName("cloth-1222211-80x90.png");
    }
  }
  /**
   * Returns random text ID of specified category for this race
   *
   * @param category Random text category (e.g. aggressive, idle or friendly)
   * @return String with complete random text category ID
   */
  public String getRandomTextId(String category) {
    switch (this) {
      case HUMAN:
      case GOBLIN:
        return "human-" + category;
      case WOLF:
        return "beast-" + category;
      default:
        return "human-" + category;
    }
  }
}
