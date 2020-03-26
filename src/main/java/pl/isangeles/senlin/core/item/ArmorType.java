/*
 * ArmorType.java
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

/**
 * Enumeration for armor types
 *
 * @author Isangeles
 */
public enum ArmorType {
  FEET,
  HANDS,
  OFFHAND,
  CHEST,
  HEAD;

  /**
   * Converts type name to armor type enumeration
   *
   * @param type String with type name
   * @return Armor type enum
   */
  public static ArmorType fromName(String type) {
    switch (type.toLowerCase()) {
      case "feet":
        return ArmorType.FEET;
      case "hands":
        return ArmorType.HANDS;
      case "offhand":
        return ArmorType.OFFHAND;
      case "chest":
        return ArmorType.CHEST;
      case "head":
        return ArmorType.HEAD;
      default:
        return ArmorType.CHEST;
    }
  }
}
