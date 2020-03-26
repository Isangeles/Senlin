/*
 * UnlockBonus.java
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
 * Class for unlock bonus
 *
 * @author Isangeles
 */
public class UnlockBonus extends Modifier {
  private int level;
  /**
   * Unlock bonus constructor
   *
   * @param lockLevel Maximal lock level to unlock
   */
  public UnlockBonus(int lockLevel) {
    super(ModifierType.UNLOCK, TConnector.getText("ui", "bonUnlock"));
    level = lockLevel;
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.core.bonus.Bonus#applyOn(pl.isangeles.senlin.core.Targetable)
   */
  @Override
  public void applyOn(Targetable object) {
    object.getInventory().unlock(this);
  }

  /* (non-Javadoc)
   * @see pl.isangeles.senlin.core.bonus.Bonus#removeFrom(pl.isangeles.senlin.core.Targetable)
   */
  @Override
  public void removeFrom(Targetable object) {
    return;
  }
  /**
   * Return unlock bonus level
   *
   * @return Unlock level
   */
  public int getLevel() {
    return level;
  }
}
