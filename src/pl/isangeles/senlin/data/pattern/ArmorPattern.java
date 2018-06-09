/*
 * ArmorPattern.java
 * 
 * Copyright 2017-2018 Dariusz Sikora <darek@pc-solus>
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

import pl.isangeles.senlin.core.bonus.Modifier;
import pl.isangeles.senlin.core.bonus.Modifiers;
import pl.isangeles.senlin.core.item.Armor;
import pl.isangeles.senlin.core.item.ArmorMaterial;
import pl.isangeles.senlin.core.item.ArmorType;
import pl.isangeles.senlin.core.item.ItemMaterial;
import pl.isangeles.senlin.core.req.Requirement;

/**
 * Class for armor patterns 
 * @author Isangeles
 *
 */
public class ArmorPattern 
{
	private final String id;
	private final String type;
	private final String material;
	private final int value;
	private final int armRat;
	private final Modifiers bonuses;
	private final List<String> equipEffects;
	private final List<Requirement> equipReq;
	private final String icon;
	private final String mSprite;
	private final String fSprite;
	/**
	 * Armor pattern constructor
	 * @param id Armor ID
	 * @param type Armor type
	 * @param material Armor material
	 * @param value Item value
	 * @param armRat Armor rating
	 * @param bonuses Item bonuses
	 * @param equipEffects List with IDs of all equip effects
	 * @param equipReq List with equip requirements
	 * @param icon Item UI icon
	 * @param mSprite Name of male sprite sheet file
	 * @param fSprite Name of female sprite sheet file
	 */
	public ArmorPattern(String id, String type, String material, int value, int armRat, List<Modifier> bonuses, List<String> equipEffects, 
						List<Requirement> equipReq, String icon, String mSprite, String fSprite) 
	{
		this.id = id;
		this.type = type;
		this.material = material;
		this.value = value;
		this.armRat = armRat;
		this.bonuses = new Modifiers();
		this.bonuses.addAll(bonuses);
		this.equipEffects = equipEffects;
		this.equipReq = equipReq;
		this.icon = icon;
		this.mSprite = mSprite;
		this.fSprite = fSprite;
	}
	/**
	 * Returns pattern item ID
	 * @return String with item ID
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * Creates item from this pattern
	 * @param gc Slick game container
	 * @return New armor object
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Armor make(GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		return new Armor(id, ArmorType.fromName(type), ItemMaterial.fromName(material), value, armRat, bonuses, equipEffects, equipReq, icon, mSprite, fSprite, gc);
	}
	/**
	 * Creates item from this pattern (with specified serial number)
	 * @param gc Slick game container
	 * @param serial Serial number for item
	 * @return New armor object
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Armor make(GameContainer gc, long serial) throws SlickException, IOException, FontFormatException
	{
		return new Armor(id, serial, ArmorType.fromName(type), ItemMaterial.fromName(material), value, armRat, bonuses, equipEffects, equipReq, icon, mSprite, fSprite, gc);
	}
}
