/*
 * Targetable.java
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

import pl.isangeles.senlin.core.bonus.Bonus;
import pl.isangeles.senlin.core.bonus.Bonuses;
import pl.isangeles.senlin.core.effect.Effects;
import pl.isangeles.senlin.core.item.WeaponType;
import pl.isangeles.senlin.core.quest.ObjectiveTarget;
import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.core.skill.Buff;
import pl.isangeles.senlin.core.skill.Passive;
import pl.isangeles.senlin.graphic.Effective;
import pl.isangeles.senlin.gui.Portrait;

/**
 * Interface for targetable game objects
 * @author Isangeles
 *
 */
public interface Targetable extends ObjectiveTarget
{
	/**
	 * Sets this game objects target on another targetable game object
	 * @param target Targetable game object
	 */
	public void setTarget(Targetable target);
	/**
	 * Returns this object target
	 * @return Targetable game object or null if this object has no target
	 */
	public Targetable getTarget();
	/**
	 * Returns serial ID of target
	 * @return String with serial ID
	 */
	public String getSerialId();
	/**
	 * Returns object name
	 * @return String with object name
	 */
	public String getName();
	public Portrait getPortrait();
	public Effects getEffects();
	public Bonuses getBonuses();
	public Inventory getInventory();
	/**
	 * Returns target attributes
	 * @return Attributes
	 */
	public Attributes getAttributes();
	/**
	 * Returns target for graphical effects
	 * @return Graphical object able to handle graphical effects
	 */
	public Effective getGEffectsTarget();
	public int getHealth();
	public int getMaxHealth();
	public int getMagicka();
	public int getMaxMagicka();
	public int getExperience();
	public int getMaxExperience();
	public int getLevel();
	public int[] getPosition();
	
	public void takeHealth(Targetable source, int value);
	/**
	 * Handles attacks
	 * @param aggressor Aggressor
	 * @param attack Attack skill
	 */
	public void takeAttack(Targetable aggressor, Attack attack);
	/**
	 * Handles buffs
	 * @param buffer Buff provider
	 * @param buff Buff skill
	 */
	public void takeBuff(Targetable buffer, Buff buff);
	/**
	 * Handles passives
	 * @param passSource Targetable object
	 * @param passive Passive skill to handle
	 */
	public void takePassvie(Targetable passSource, Passive passive);
	
	public void modHealth(int value);
	public void modMagicka(int value);
	public void modExperience(int value);
	/**
	 * Mods maximal amount of health points by specified value
	 * @param value Value to add(negative value to subtract)
	 */
	public void modMaxHealth(int value);
	/**
	 * Modifies maximal amount of magicka points by specified value
	 * @param value Value to add(negative value to subtract)
	 */
	public void modMaxMagicka(int value);
	/**
	 * Starts looting action on specified target
	 * @param target Targetable object to loot
	 */
	public void startLooting(Targetable target);
	/**
	 * Stops looting action
	 */
	public void stopLooting();
	/**
	 * Starts reading action
	 * @param textId ID of text to read
	 */
	public void startReading(String textId);
	/**
	 * Stops reading action
	 */
	public void stopReading();

    public boolean addBonus(Bonus bonus);
    public boolean removeBonus(Bonus bonus);
    public boolean hasBonus(Bonus bonus);
    
	public boolean isLive();
	public boolean isMouseOver();
	/**
	 * Checks if looting action is in progress
	 * @return Looted object or null if nothing is looted now
	 */
	public Targetable looting();
	/**
	 * Checks if reading action is in progress
	 * @return String with ID of text to read or null if nothing is read now
	 */
	public String reading();
}
