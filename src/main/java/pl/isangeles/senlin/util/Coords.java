/*
 * Coords.java
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
package pl.isangeles.senlin.util;
/**
 * Static class with utilities methods for positioning
 *
 * @author Isangeles
 */
public final class Coords {
  public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
  /** Private constructor to prevent initialization */
  private Coords() {}
  /**
   * Returns scaled position on x-axis related to specific point on screen
   *
   * @param point "TL" - for top left, "BL" - for bottom left, "TR" - for top right, "BR" - for
   *     bottom right, "CE" - for center
   * @param dis Distance from specific point
   * @return Scaled position related to specific point
   */
  public static float getX(String point, float dis) {
    return get(point, dis * Settings.getScale(), 0)[0];
  }
  /**
   * Returns scaled position on y-axis related to specific point on screen
   *
   * @param point "TL" - for top left, "BL" - for bottom left, "TR" - for top right, "BR" - for
   *     bottom right, "CE" - for center
   * @param dis Distance from specific point
   * @return Scaled position related to specific point
   */
  public static float getY(String point, float dis) {
    return get(point, 0, dis * Settings.getScale())[1];
  }
  /**
   * Returns distance corrected by scale
   *
   * @param rawDistance Distance on 1920x1080
   * @return Distance scaled to current resolution
   */
  public static int getDis(int rawDistance) {
    return Math.round(rawDistance * Coords.getScale());
  }
  /**
   * Returns size corrected by scale
   *
   * @param rawSize Size on 1920x1080
   * @return Size scaled to current resolution
   */
  public static float getSize(float rawSize) {
    return Math.round(rawSize * Coords.getScale());
  }
  /**
   * Returns scale based on current resolution
   *
   * @return Float scale value
   */
  public static float getScale() {
    float defResX = 1920;
    float defResY = 1080;
    float resX = Settings.getResolution()[0];
    float resY = Settings.getResolution()[1];
    float proportionX = resX / defResX;
    float proportionY = resY / defResY;
    return Math.round(Math.min(proportionX, proportionY) * 10f) / 10f;
  }
  /**
   * Returns table with positions on x and y axis related to specific point on screen
   *
   * @param point "TL" - for top left, "BL" - for bottom left, "TR" - for top right, "BR" - for
   *     bottom right, "CE" - for center
   * @param disX Distance from specific point on x-axis
   * @param disY Distance from specific point on y-axis
   * @return Table with x position[0] and y position[1]
   */
  private static float[] get(String point, float disX, float disY) {
    float resWidth = Settings.getResolution()[0];
    float resHeight = Settings.getResolution()[1];

    switch (point) {
      case "TL":
        return new float[] {disX, disY};
      case "TR":
        return new float[] {resWidth - disX, disY};
      case "BL":
        return new float[] {disX, resHeight - disY};
      case "BR":
        return new float[] {resWidth - disX, resHeight - disY};
      case "CE":
        {
          float ceX = resWidth / 2;
          float ceY = resHeight / 2;
          return new float[] {ceX + disX, ceY + disY};
        }
      default:
        return new float[] {disX, disY};
    }
  }
  /**
   * Checks if specified coordinates are between two specified positions
   *
   * @param posToCheck Position to check
   * @param posStart Start of area
   * @param posEnd End of area
   * @return True if specified position are inside specified area
   */
  public static boolean isIn(Position posToCheck, Position posStart, Position posEnd) {
    if (posToCheck.x >= posStart.x && posToCheck.y >= posStart.y) {
      if (posToCheck.x <= posEnd.x && posToCheck.y <= posEnd.y) return true;
    }
    return false;
  }
}
