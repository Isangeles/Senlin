/*
 * InventoryLock.java
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

import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.isangeles.senlin.core.bonus.UnlockBonus;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.req.ItemsRequirement;
import pl.isangeles.senlin.data.save.SaveElement;

/**
 * Class for inventory lock
 *
 * @author Isangeles
 */
public class InventoryLock implements SaveElement {
  private ItemsRequirement keyReq;
  private int level;
  private boolean open;
  /** Default lock constructor, creates open lock */
  public InventoryLock() {
    open = true;
    keyReq = new ItemsRequirement(new HashMap<String, Integer>());
  }
  /**
   * Lock constructor
   *
   * @param keyId String with ID of key to open lock
   * @param skillId String with ID of skill to open lock
   */
  public InventoryLock(String keyId, int level) {
    Map<String, Integer> keys = new HashMap<>();
    keys.put(keyId, 1);
    keyReq = new ItemsRequirement(keys);
    this.level = level;
  }
  /**
   * Opens lock
   *
   * @param character Game character opening lock
   * @return True if lock was successfully opened, false otherwise
   */
  public boolean open(Character character) {
    if (keyReq.isMetBy(character)) {
      open = true;
      return true;
    } else return false;
  }
  /**
   * Opens lock with specified skill
   *
   * @param skill Skill
   * @return True if lock was successfully opened, false otherwise
   */
  public boolean open(UnlockBonus skill) {
    if (level <= skill.getLevel()) {
      open = true;
      return true;
    } else return false;
  }
  /**
   * Checks if lock is open
   *
   * @return True if lock is open, false otherwise
   */
  public boolean isOpen() {
    return open;
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.data.save.SaveElement#getSave(org.w3c.dom.Document)
   */
  @Override
  public Element getSave(Document doc) {
    Element lock = doc.createElement("lock");
    lock.setAttribute("level", level + "");
    String keyId = (String) keyReq.getReqItems().keySet().toArray()[0];
    lock.setAttribute("key", keyId);
    return lock;
  }
}
