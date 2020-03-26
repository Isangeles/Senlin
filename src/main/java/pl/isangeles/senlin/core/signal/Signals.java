/*
 * Signals.java
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
package pl.isangeles.senlin.core.signal;

import java.util.EnumMap;
import pl.isangeles.senlin.core.Targetable;

/**
 * Class for character signals container
 *
 * @author Isangeles
 */
public class Signals extends EnumMap<CharacterSignal, Object> {
  private static final long serialVersionUID = 1L;
  /** Signals constructor */
  public Signals() {
    super(CharacterSignal.class);
  }
  /**
   * Puts talking request with specified targetable object to signals list
   *
   * @param talking
   */
  public void startTalking(Targetable target) {
    put(CharacterSignal.TALKING, target);
  }

  public void stopTalking() {
    remove(CharacterSignal.TALKING);
  }

  public void startLooting(Targetable target) {
    put(CharacterSignal.LOOTING, target);
  }

  public void stopLooting() {
    remove(CharacterSignal.LOOTING);
  }
  /**
   * Sets request to follow specified object
   *
   * @param target Targetable object
   */
  public void startFollowing(Targetable target) {
    put(CharacterSignal.FOLLOWING, target);
  }

  public void stopFollowing() {
    remove(CharacterSignal.FOLLOWING);
  }
  /**
   * Sets combat request with specified object
   *
   * @param target Targetable object
   */
  public void startCombat(Targetable target) {
    put(CharacterSignal.FIGHTING, target);
    startFollowing(target);
  }

  public void stopCombat() {
    remove(CharacterSignal.FIGHTING);
  }

  public void startReading(String textId) {
    put(CharacterSignal.READING, textId);
  }

  public void stopReading() {
    remove(CharacterSignal.READING);
  }

  public void startResting() {
    put(CharacterSignal.RESTING, new Object());
  }

  public void stopResting() {
    remove(CharacterSignal.RESTING);
  }
}
