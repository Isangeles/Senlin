/*
 * WeatherType.java
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
 * Enumeration for weather types
 *
 * @author Isangeles
 */
public enum WeatherType {
  SUN,
  RAIN;
  /**
   * Converts specified weather ID to weather type enum
   *
   * @param id String with weather ID
   * @return Weather type enumeration
   */
  public static WeatherType fromId(String id) {
    switch (id) {
      case "sun":
        return WeatherType.SUN;
      case "rain":
        return WeatherType.RAIN;
      default:
        return WeatherType.SUN;
    }
  }
  /**
   * Returns ID for this type
   *
   * @return String with ID
   */
  public String getId() {
    switch (this) {
      case SUN:
        return "sun";
      case RAIN:
        return "rain";
      default:
        return "sun";
    }
  }
  /**
   * Returns name of this type
   *
   * @return String with name
   */
  public String getName() {
    switch (this) {
      case SUN:
        return TConnector.getText("ui", "daySun");
      case RAIN:
        return TConnector.getText("ui", "dayRain");
      default:
        return TConnector.getText("ui", "daySun");
    }
  }
}
