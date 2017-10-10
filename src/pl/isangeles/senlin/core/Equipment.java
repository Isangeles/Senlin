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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.core.item.Armor;
import pl.isangeles.senlin.core.item.Equippable;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.item.Trinket;
import pl.isangeles.senlin.core.item.Weapon;
import pl.isangeles.senlin.data.save.SaveElement;

/**
 * Class for character equipment
 * @author Isangeles
 *
 */
public class Equipment implements SaveElement
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
	
	/**
     * Sets item as equipped main weapon
     * @param weapon Any weapon
     * @return True if item was successful inserted, false otherwise
     */
	private boolean setWeapon(Weapon weapon)
	{
		if(weaponMain == null)
		{
			weaponMain = weapon;
			return true;
		}
		else if(weaponSec == null)
		{
			weaponSec = weapon;
			return true;
		}
		else
		{
			weaponMain = weapon;
			return true;
		}
	}
	/**
	 * Sets armor part as equipped item
	 * @param armorPart Armor item with proper type
	 * @return True if item was successful inserted to equipment
	 */
	private boolean setArmor(Armor armorPart)
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
	private boolean setTrinket(Trinket trinket)
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
	public void unequipp(Equippable item)
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
     * Equips specified equippable item
     * @param item Equippable item
     * @return True if item was successfully equipped, false otherwise
     */
	public boolean equip(Equippable item)
	{
		item.reset();
		
		if(Armor.class.isInstance(item))
			return setArmor((Armor)item);
		else if(Weapon.class.isInstance(item))
			return setWeapon((Weapon)item);
		else if(Trinket.class.isInstance(item));
			return setTrinket((Trinket)item);
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
	 * @return Equipped main weapon or null if no item equipped
	 */
	public Weapon getMainWeapon()
	{
		return weaponMain;
	}
	/**
	 * Returns character off hand
	 * @return Equipped secondary weapon or null if no item equipped
	 */
	public Weapon getOffHand()
	{
		return weaponSec;
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
	public List<Equippable> getAll()
	{
		List<Equippable> eq = new ArrayList<>();
		if(weaponMain != null)
			eq.add(weaponMain);
		if(weaponSec != null)
			eq.add(weaponSec);
		if(boots != null)
			eq.add(boots);
		if(gloves != null)
			eq.add(gloves);
		if(shield != null)
			eq.add(shield);
		if(chest != null)
			eq.add(chest);
		if(helmet != null)
			eq.add(helmet);
		if(ring != null)
			eq.add(ring);
		if(ringSec != null)
			eq.add(ringSec);
		if(amulet != null)
			eq.add(amulet);
		if(artifact != null)
			eq.add(artifact);
		return eq;
	}
	/**
	 * Checks if specified item is equipped
	 * @param item Game item
	 * @return True if specified item is equipped, false otherwise
	 */
	public boolean isEquipped(Equippable item)
	{
		return getAll().contains(item);
	}
	/**
	 * Checks if both main and secondary weapons are equipped
	 * @return True if main and secondary weapons are equipped, false otherwise
	 */
	public boolean isDualwield()
	{
		return (weaponMain != null && weaponSec != null);
	}
	/**
	 * Parses equipment to XML document element
	 * @param doc Document for game save file
	 * @return XML document element
	 */
	public Element getSave(Document doc)
	{
		Element eq = doc.createElement("eq");
		
		Element headE = doc.createElement("head");
		if(helmet != null)
			headE.setTextContent(helmet.getId());
		eq.appendChild(headE);
		
		Element chestE = doc.createElement("chest");
		if(chest != null)
			chestE.setTextContent(chest.getId());
		eq.appendChild(chestE);
		
		Element handsE = doc.createElement("hands");
		if(gloves != null)
			handsE.setTextContent(gloves.getId());
		eq.appendChild(handsE);
		
		Element mainhandE = doc.createElement("mainhand");
		if(weaponMain != null)
			mainhandE.setTextContent(weaponMain.getId());
		eq.appendChild(mainhandE)
		;
		Element offhandE = doc.createElement("offhand");
		if(weaponSec != null)
			offhandE.setTextContent(weaponSec.getId());
		eq.appendChild(offhandE);
		
		Element feetE = doc.createElement("feet");
		if(boots != null)
			feetE.setTextContent(boots.getId());
		eq.appendChild(feetE);
		
		Element neckE = doc.createElement("neck");
		if(amulet != null)
			neckE.setTextContent(amulet.getId());
		eq.appendChild(neckE);
		
		Element finger1E = doc.createElement("finger1");
		if(ring != null)
			finger1E.setTextContent(ring.getId());
		eq.appendChild(finger1E);
		
		Element finger2E = doc.createElement("finger2");
		if(ringSec != null)
			finger2E.setTextContent(ringSec.getId());
		eq.appendChild(finger2E);
		
		Element artifactE = doc.createElement("artifact");
		if(artifact != null)
			artifactE.setTextContent(artifact.getId());
		eq.appendChild(artifactE);
		
		return eq;
	}
}
