/*
 * WeaponPattern.java
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
package pl.isangeles.senlin.data.pattern;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.item.Weapon;
import pl.isangeles.senlin.core.bonus.Bonus;
import pl.isangeles.senlin.core.bonus.Bonuses;
import pl.isangeles.senlin.core.item.ItemMaterial;
import pl.isangeles.senlin.core.item.WeaponType;

/**
 * Class for weapons patterns
 * @author Isangeles
 *
 */
public class WeaponPattern 
{
	private final String id;
	private final int reqLvl;
	private final String type;
	private final String material;
	private final int value;
	private final int minDmg;
	private final int maxDmg;
	private final Bonuses bonuses;
	private final String icon;
	private final String spriteSheet;
	/**
	 * Weapon pattern constructor
	 * @param id Item ID
	 * @param reqLvl Level required to use this item
	 * @param type Item type name
	 * @param material Item material name
	 * @param value Item value
	 * @param minDmg Weapon minimal damage 
	 * @param maxDmg Weapon maximal damage
	 * @param bonuses Item bonuses
	 * @param icon Item UI icon
	 * @param spriteSheet Item sprite sheet
	 */
	public WeaponPattern(String id, int reqLvl, String type, String material, int value, int minDmg, int maxDmg, List<Bonus> bonuses, String icon, String spriteSheet)
	{
		this.id = id;
		this.reqLvl = reqLvl;
		this.type = type;
		this.material = material;
		this.value = value;
		this.minDmg = minDmg;
		this.maxDmg = maxDmg;
		this.bonuses = new Bonuses();
		this.bonuses.addAll(bonuses);
		this.icon = icon;
		this.spriteSheet = spriteSheet;
	}
	/**
	 * Returns pattern weapon ID
	 * @return String with weapon ID
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * Creates new weapons from this pattern
	 * @param gc Slick game container
	 * @return New weapon object
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Weapon make(GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		return new Weapon(id, WeaponType.fromNameToId(type), ItemMaterial.fromName(material), value, minDmg, maxDmg, bonuses, reqLvl, icon, spriteSheet, gc);
	}

	/**
	 * Creates new weapons from this pattern
	 * @param gc Slick game container
	 * @param serial Serial number for item
	 * @return New weapon object
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Weapon make(GameContainer gc, int serial) throws SlickException, IOException, FontFormatException
	{
		return new Weapon(id, serial, WeaponType.fromNameToId(type), ItemMaterial.fromName(material), value, minDmg, maxDmg, bonuses, reqLvl, icon, spriteSheet, gc);
	}
}
