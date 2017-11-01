/*
 * InventoryLock.java
 * 
 * Copyright 2017 Dariusz Sikora <darek@darek-PC-LinuxMint18>
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

import pl.isangeles.senlin.core.bonus.UnlockBonus;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.skill.Skill;
/**
 * Class for inventory lock
 * @author Isangeles
 *
 */
public class InventoryLock
{
    private String keyId;
    private int level;
    private boolean open;
    /**
     * Default lock constructor, creates open lock
     */
    public InventoryLock()
    {
        open = true;
    }
    /**
     * Lock constructor
     * @param keyId String with ID of key to open lock
     * @param skillId String with ID of skill to open lock
     */
    public InventoryLock(String keyId, int level)
    {
        this.keyId = keyId;
        this.level = level;
    }
    /**
     * Opens lock with key item
     * @param key Key item
     * @return True if lock was successfully opened, false otherwise
     */
    public boolean open(Item key)
    {
        if(keyId.equals(key.getId()))
        {
            open = true;
            return true;
        }
        else
            return false;
    }
    /**
     * Opens lock with specified skill
     * @param skill Skill
     * @return True if lock was successfully opened, false otherwise
     */
    public boolean open(UnlockBonus skill)
    {
        if(level <= skill.getLevel()) 
        {
            open = true;
            return true;
        }
        else
            return false;
    }
    /**
     * Checks if lock is open
     * @return True if lock is open, false otherwise
     */
    public boolean isOpen()
    {
        return open;
    }
}
