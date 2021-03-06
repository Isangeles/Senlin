/*
 * Position.java
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

import java.util.NoSuchElementException;

/**
 * Tuple for XY position
 *
 * @author Isangeles
 */
public class Position {
  public int x;
  public int y;
  /** Position default constructor */
  public Position() {
    this.x = 0;
    this.y = 0;
  }

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Position(float x, float y) {
    this.x = (int) x;
    this.y = (int) y;
  }

  public Position(int[] xy) {
    this.x = xy[0];
    this.y = xy[1];
  }
  /**
   * Copy constructor
   *
   * @param pos Position to copy
   */
  public Position(Position pos) {
    x = pos.x;
    y = pos.y;
  }
  /**
   * Constructs position from string in this form: [x position];[y position]
   *
   * @param xSemicolonY String in this form: [x position];[y position]
   * @throws NumberFormatException
   */
  public Position(String xSemicolonY) throws NumberFormatException, NoSuchElementException {
    this.x = Integer.parseInt(xSemicolonY.split(";")[0]);
    this.y = Integer.parseInt(xSemicolonY.split(";")[1]);
  }
  /**
   * Returns position as normal table with two values: x[0] and y[1]
   *
   * @return Table with x[0] and y[1] position
   */
  public int[] toArray() {
    return new int[] {x, y};
  }
  /**
   * Checks if position is between two specified positions
   *
   * @param areaStart Start of area
   * @param areaEnd End of area
   * @return True if position are inside specified area, false otherwise
   */
  public boolean isIn(Position areaStart, Position areaEnd) {
    if (x >= areaStart.x && y >= areaStart.y) {
      if (x <= areaEnd.x && y <= areaEnd.y) return true;
    }
    return false;
  }

  /** format: [x];[y] */
  @Override
  public String toString() {
    return x + ";" + y;
  }
  /**
   * Checks if specified position is equal to this position
   *
   * @param pos Position to check
   * @return True if specified position is equal to this position, false otherwsie
   */
  public boolean equals(Position pos) {
    if (x == pos.x && y == pos.y) return true;
    else return false;
  }
}
