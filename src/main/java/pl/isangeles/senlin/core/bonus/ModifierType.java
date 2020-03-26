/*
 * ModifierType.java
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
package pl.isangeles.senlin.core.bonus;

/**
 * Enumeration for modifier types
 *
 * @author Isangeles
 */
public enum ModifierType {
  NONE,
  STATS,
  HEALTH,
  MANA,
  HASTE,
  DODGE,
  DAMAGE,
  UNDETECT,
  DUALWIELD,
  RESISTANCE,
  UNLOCK,
  ATTITUDE,
  FLAG,
  EXPERIENCE;
  /**
   * Converts specified type ID to modifier type enum
   *
   * @param id String with modifier type ID
   * @return Modifier type enum
   */
  public static ModifierType fromString(String id) {
    switch (id) {
      case "statsBonus":
        return ModifierType.STATS;
      case "healthBonus":
        return ModifierType.HEALTH;
      case "manaBonus":
        return ModifierType.MANA;
      case "hasteBonus":
        return ModifierType.HASTE;
      case "dodgeBonus":
        return ModifierType.DODGE;
      case "damageBonus":
        return ModifierType.DAMAGE;
      case "undetectBonus":
        return ModifierType.UNDETECT;
      case "dualwieldBonus":
        return ModifierType.DUALWIELD;
      case "resistanceBonus":
        return ModifierType.RESISTANCE;
      case "unlockBonus":
        return ModifierType.UNLOCK;
      case "attitudeModifier":
        return ModifierType.ATTITUDE;
      case "flagModifier":
        return ModifierType.FLAG;
      case "experienceModifier":
        return ModifierType.EXPERIENCE;
      default:
        return ModifierType.NONE;
    }
  }
}
