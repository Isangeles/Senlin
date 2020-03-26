/*
 * BuffType.java
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
package pl.isangeles.senlin.core.skill;

/**
 * Enumeration for buffs types
 *
 * @author Isangeles
 */
public enum BuffType {
  ONUSER,
  ONTARGET,
  ONUSERORTARGET;

  public static BuffType fromString(String id) {
    switch (id) {
      case "on user":
        return BuffType.ONUSER;
      case "on target":
        return BuffType.ONTARGET;
      case "on user or target":
        return BuffType.ONUSERORTARGET;
      default:
        return BuffType.ONUSER;
    }
  }
  /**
   * Checks if type allows to use buff on target
   *
   * @return True if buff can be used on target, false otherwise
   */
  public boolean useTarget() {
    if (this == ONTARGET || this == ONUSERORTARGET) return true;
    else return false;
  }
  /**
   * Checks if type allows to use buff on user
   *
   * @return True if buff can be used on user, false otherwise
   */
  public boolean useUser() {
    if (this == ONUSER || this == ONUSERORTARGET) return true;
    else return false;
  }
}
