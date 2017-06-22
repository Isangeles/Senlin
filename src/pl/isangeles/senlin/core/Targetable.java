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

import java.util.List;

import pl.isangeles.senlin.inter.Portrait;
import pl.isangeles.senlin.quest.ObjectiveTarget;

/**
 * Interface for targetable objects
 * @author Isangeles
 *
 */
public interface Targetable extends ObjectiveTarget
{
	public void setTarget(Targetable target);
	public Targetable getTarget();
	
	public String getName();
	public Portrait getPortrait();
	public Effects getEffects();
	public int getHealth();
	public int getMaxHealth();
	public int getMagicka();
	public int getMaxMagicka();
	public int getExperience();
	public int getMaxExperience();
	public int getLevel();
	public int[] getPosition();
	
	public void takeHealth(int value);
	public void takeMagicka(int value);
	public void takeAttack(Targetable aggressor, int attackDamage, List<Effect> effects);
	
	public boolean isLive();
}
