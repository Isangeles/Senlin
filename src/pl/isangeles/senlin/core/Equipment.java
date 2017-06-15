/*
 * Equipment.java
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.isangeles.senlin.core.item.Armor;
import pl.isangeles.senlin.core.item.Equippable;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.item.Trinket;
import pl.isangeles.senlin.core.item.Weapon;

/**
 * Class for character equipment
 * @author Isangeles
 *
 */
public class Equipment 
{
	private Weapon weaponMain;
	private Weapon weaponSec;
	private Armor boots;
	private Armor gloves;
	private Armor shield;
	private Armor chest;
	private Armor helmet;
	private Trinket ring;
	private Trinket ringSec;
	private Trinket amulet;
	private Trinket artifact;
	
	public Equipment() 
	{
	}
	/**
     * Sets item as equipped main weapon
     * @param weapon Any weapon
     * @return True if item was successful inserted, false otherwise
     */
	public boolean setWeapon(Weapon weapon)
	{
		weaponMain = weapon;
		return true;
	}
	/**
	 * TODO implements secondary weapons
	 * @param weapon
	 * @return
	 */
	public boolean setSecWeapon(Weapon weapon)
	{
		return false;
	}
	/**
	 * Sets armor part as equipped item
	 * @param armorPart Armor item with proper type
	 * @return True if item was successful inserted to equipment
	 */
	public boolean setArmor(Armor armorPart)
	{
		if(armorPart.type() == Armor.FEET)
		{
			this.boots = armorPart;
			return true;
		}
		
		if(armorPart.type() == Armor.HANDS)
		{
			this.gloves = armorPart;
			return true;
		}
		
		if(armorPart.type() == Armor.OFFHAND)
		{
			this.shield = armorPart;
			return true;
		}
		
		if(armorPart.type() == Armor.CHEST)
		{
			this.chest = armorPart;
			return true;
		}
		
		if(armorPart.type() == Armor.HEAD)
		{
			this.helmet = armorPart;
			return true;
		}
		
		return false;
	}
	/**
     * Sets item as equipped trinket
     * @param trinket Trinket item with proper type (finger, neck or artifact)
     * @return True if item was successful inserted, false otherwise
     */
	public boolean setTrinket(Trinket trinket)
	{
		if(trinket.type() == Trinket.FINGER)
		{
			this.ring = trinket;
			return true;
		}
		
		if(trinket.type() == Trinket.FINGER)
		{
			this.ringSec = trinket;
			return true;
		}
		
		if(trinket.type() == Trinket.NECK)
		{
			this.amulet = trinket;
			return true;
		}
		
		if(trinket.type() == Trinket.ARTIFACT)
		{
			this.artifact = trinket;
			return true;
		}
		
		return false; 
	}
	/**
	 * Removes specific item from equipment
	 * @param item Equipped character item
	 */
	public void unequipp(Item item)
	{
		if(item == weaponMain)
		{
			weaponMain = null;
			return;
		}
		if(item == weaponSec)
		{
			weaponSec = null;
			return;
		}
		if(item == boots)
		{
			boots = null;
			return;
		}
		if(item == gloves)
		{
			gloves = null;
			return;
		}
		if(item == shield)
		{
			shield = null;
			return;
		}
		if(item == chest)
		{
			chest = null;
			return;
		}
		if(item == ring)
		{
			ring = null;
			return;
		}
		if(item == ringSec)
		{
			ringSec = null;
			return;
		}
		if(item == amulet)
		{
			amulet = null;
			return;
		}
		if(item == artifact)
		{
			artifact = null;
			return;
		}
			
	}
	/**
	 * Return character helmet
	 * @return Equipped armor item, type head OR null if not equipped
	 */
	public Armor getHelmet()
	{
		return helmet;
	}
	/**
	 * Return character chest
	 * @return Equipped armor item, type chest OR null if not equipped
	 */
	public Armor getChest()
	{
		return chest;
	}
	/**
	 * Return character main weapon
	 * @return Equipped weapon item, any type OR null if not equipped
	 */
	public Weapon getMainWeapon()
	{
		return weaponMain;
	}
	/**
	 * Returns equipped weapons damage
	 * @return Table with damage minimal[0] and maximal[1] values
	 */
	public int[] getDamage()
	{
		if(weaponMain != null)
		{
			return weaponMain.getDamage();
		}
		else
			return new int[]{0, 0};
	}
	/**
	 * Returns total amount of equipment armor rating 
	 * @return Value of armor rating
	 */
	public int getArmorRat()
	{
		int rating = 0;
		
		if(boots != null)
			rating += boots.getArmorRat();
		if(gloves != null)
			rating += gloves.getArmorRat();
		if(chest != null)
			rating += chest.getArmorRat();
		if(helmet != null)
			rating += helmet.getArmorRat();
		if(shield != null)
			rating += shield.getArmorRat();
		
		return rating;
		
	}
	/**
	 * Returns list with all equipment items 
	 * @return ArrayList with items
	 */
	public List<Item> getAll()
	{
		List<Item> eq = new ArrayList<>();
		eq.add(weaponMain);
		eq.add(weaponSec);
		eq.add(boots);
		eq.add(gloves);
		eq.add(shield);
		eq.add(chest);
		eq.add(helmet);
		eq.add(ring);
		eq.add(ringSec);
		eq.add(amulet);
		eq.add(artifact);
		return eq;
	}
}
