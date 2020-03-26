/*
 * UndetectBonus.java
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

import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for undetect bonus Not that this bonus do not affect character in any way, is only checked
 * by area getNearbyCharacters() method
 *
 * @author Isangeles
 */
public class UndetectBonus extends Modifier {
  private int level;
  /**
   * Undetect bonus constructor
   *
   * @param level Stealth level
   */
  public UndetectBonus(int level) {
    super(ModifierType.UNDETECT, TConnector.getText("ui", "bonUndetect"));
    this.level = level;
  }

  /* (non-Javadoc)
   * @see pl.isangeles.senlin.core.bonus.Bonus#applyOn(pl.isangeles.senlin.core.Targetable)
   */
  @Override
  public void applyOn(Targetable object) {}

  /* (non-Javadoc)
   * @see pl.isangeles.senlin.core.bonus.Bonus#removeFrom(pl.isangeles.senlin.core.Targetable)
   */
  @Override
  public void removeFrom(Targetable object) {}

  /**
   * Return stealth level
   *
   * @return Stealth level
   */
  public int getLevel() {
    return level;
  }
}
