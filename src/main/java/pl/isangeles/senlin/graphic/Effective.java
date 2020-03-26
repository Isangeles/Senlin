/*
 * Effective.java
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
package pl.isangeles.senlin.graphic;

/**
 * Interface for graphical objects capable to handle animated effects
 *
 * @author Isangeles
 */
public interface Effective {
  /**
   * Adds animated effect to object effects
   *
   * @param effect Simple animation
   * @param loop True if animation should be looped, false if animation should be auto-removed after
   *     last animation frame
   * @return True if effect was successfully added, false otherwise
   */
  public boolean addEffect(SimpleAnim effect, boolean loop);
  /**
   * Removes specified animated effect from object effects list
   *
   * @param effect Simple animation to remove
   * @return True if effect was successfully removed, false otherwise
   */
  public boolean removeEffect(SimpleAnim effect);
}
