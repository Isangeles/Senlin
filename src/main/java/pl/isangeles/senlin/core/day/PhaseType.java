/*
 * PhaseType.java
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
package pl.isangeles.senlin.core.day;

import pl.isangeles.senlin.util.TConnector;

/**
 * Enumeration for day phase type
 *
 * @author Isangeles
 */
enum PhaseType {
  MORNING,
  MIDDAY,
  AFTERNOON,
  NIGHT;
  /**
   * Converts specified day phase ID to phase type enumeration
   *
   * @param id String with day phase ID
   * @return Day phase type enumeration
   */
  public static PhaseType fromId(String id) {
    switch (id) {
      case "morning":
        return PhaseType.MORNING;
      case "midday":
        return PhaseType.MIDDAY;
      case "afternoon":
        return PhaseType.AFTERNOON;
      case "night":
        return PhaseType.NIGHT;
      default:
        return PhaseType.MORNING;
    }
  }
  /**
   * Returns next day phase
   *
   * @return Day phase type enumeration
   */
  public PhaseType getNext() {
    switch (this) {
      case MORNING:
        return PhaseType.MIDDAY;
      case MIDDAY:
        return PhaseType.AFTERNOON;
      case AFTERNOON:
        return PhaseType.NIGHT;
      case NIGHT:
        return PhaseType.MORNING;
      default:
        return PhaseType.MORNING;
    }
  }
  /**
   * Returns ID of this type
   *
   * @return String with ID
   */
  public String getId() {
    switch (this) {
      case MORNING:
        return "morning";
      case MIDDAY:
        return "midday";
      case AFTERNOON:
        return "afternoon";
      case NIGHT:
        return "night";
      default:
        return "morning";
    }
  }
  /**
   * Returns name of this type
   *
   * @return String with name
   */
  public String getName() {
    switch (this) {
      case MORNING:
        return TConnector.getText("ui", "dayMorning");
      case MIDDAY:
        return TConnector.getText("ui", "dayMidday");
      case AFTERNOON:
        return TConnector.getText("ui", "dayAfternoon");
      case NIGHT:
        return TConnector.getText("ui", "dayNight");
      default:
        return "error";
    }
  }
}
