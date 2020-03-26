/*
 * Health.java
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
package pl.isangeles.senlin.core;

/**
 * Tuple for health points
 *
 * @author Isangeles
 */
public class Health {
  private int value;
  private int max;
  /** Default health constructor */
  public Health() {}
  /**
   * Health constructor
   *
   * @param value Current health value
   * @param max Maximal health value
   */
  public Health(int value, int max) {
    this.value = value;
    this.max = max;
  }
  /**
   * Modifies current health value by specified value
   *
   * @param value Value to add(negative value to subtract)
   */
  public void modValue(int value) {
    if (this.value + value > max) value = max;
    else this.value += value;
  }
  /**
   * Modifies maximal health value by specified value
   *
   * @param value Value to add(negative value to subtract)
   */
  public void modMax(int value) {
    max += value;
  }
  /**
   * Sets specified value as current health value
   *
   * @param value Value to set
   */
  public void setValue(int value) {
    this.value = value;
  }
  /**
   * Sets specified value as maximal value of health
   *
   * @param value Value to set
   */
  public void setMax(int value) {
    this.max = value;
  }
  /**
   * Returns current health value
   *
   * @return Health value
   */
  public int getValue() {
    return value;
  }
  /**
   * Returns maximal health value
   *
   * @return Maximal health value
   */
  public int getMax() {
    return max;
  }

  @Override
  public String toString() {
    return value + "/" + max;
  }
}
