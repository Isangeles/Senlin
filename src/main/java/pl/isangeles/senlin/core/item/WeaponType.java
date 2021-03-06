/*
 * WeaponType.java
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
package pl.isangeles.senlin.core.item;

import pl.isangeles.senlin.util.TConnector;

/**
 * Enumeration for weapon types
 *
 * @author Isangeles
 */
public enum WeaponType {
  DAGGER,
  SWORD,
  AXE,
  MACE,
  SPEAR,
  BOW,
  FIST;
  /**
   * Converts type name to type object
   *
   * @param type String with type name
   * @return Weapon type corresponding to specified name
   */
  public static WeaponType fromName(String type) {
    switch (type.toLowerCase()) {
      case "dagger":
        return DAGGER;
      case "sword":
        return SWORD;
      case "axe":
        return AXE;
      case "mace":
        return MACE;
      case "spear":
        return SPEAR;
      case "bow":
        return BOW;
      case "fist":
        return FIST;
      default:
        return FIST;
    }
  }
  /**
   * Converts ordinal to weapon type enumeration
   *
   * @param ordinal Weapon type enum ordinal
   * @return Weapon type enumeration
   */
  public static WeaponType fromOrdinal(int ordinal) {
    switch (ordinal) {
      case 0:
        return DAGGER;
      case 1:
        return SWORD;
      case 2:
        return AXE;
      case 3:
        return MACE;
      case 4:
        return SPEAR;
      case 5:
        return BOW;
      case 6:
        return FIST;
      default:
        return FIST;
    }
  }
  /**
   * Returns weapon type ID
   *
   * @return Weapon type ID
   */
  public int getId() {
    switch (this) {
      case DAGGER:
        return 0;
      case SWORD:
        return 1;
      case AXE:
        return 2;
      case MACE:
        return 3;
      case SPEAR:
        return 4;
      case BOW:
        return 5;
      case FIST:
        return 6;
      default:
        return 6;
    }
  }
  /**
   * Returns string with name of this weapon type
   *
   * @return String with weapon type name
   */
  public String getName() {
    switch (this) {
      case DAGGER:
        return TConnector.getText("ui", "weaDagger");
      case SWORD:
        return TConnector.getText("ui", "weaSword");
      case AXE:
        return TConnector.getText("ui", "weaAxe");
      case MACE:
        return TConnector.getText("ui", "weaMace");
      case SPEAR:
        return TConnector.getText("ui", "weaSpear");
      case BOW:
        return TConnector.getText("ui", "weaBow");
      case FIST:
        return TConnector.getText("ui", "weaFist");
      default:
        return TConnector.getText("ui", "errorName");
    }
  }
  /**
   * Returns default male spritesheet for this weapon type
   *
   * @return String with name of default spritesheet
   */
  public String getDefaultMaleSpritesheet() {
    switch (this) {
      case DAGGER:
        return "longsword-1222211-80x90.png"; // Gender.MALE.getSSName("longsword-1222211-80x90.png");
      case SWORD:
        return "longsword-1222211-80x90.png"; // Gender.MALE.getSSName("longsword-1222211-80x90.png");
      case AXE:
        return "longsword-1222211-80x90.png"; // Gender.MALE.getSSName("longsword-1222211-80x90.png");
      case MACE:
        return "longsword-1222211-80x90.png"; // Gender.MALE.getSSName("longsword-1222211-80x90.png");
      case SPEAR:
        return "longsword-1222211-80x90.png"; // Gender.MALE.getSSName("longsword-1222211-80x90.png");
      case BOW:
        return "bow-1222211-80x90.png"; // Gender.MALE.getSSName("bow-1222211-80x90.png");
      case FIST:
        return "longsword-1222211-80x90.png"; // Gender.MALE.getSSName("longsword-1222211-80x90.png");
      default:
        return "longsword-1222211-80x90.png"; // Gender.MALE.getSSName("longsword-1222211-80x90.png");
    }
  }
}
