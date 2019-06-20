/*
 * ItemMaterial.java
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
package pl.isangeles.senlin.core.item;

import pl.isangeles.senlin.util.TConnector;

/**
 * Enumeration for weapon materials
 * @author Isangeles
 *
 */
public enum ItemMaterial 
{
	CLOTH, LEATHER, WOOD, IRON, STEEL, NEPHRITE;
	/**
	 * Converts material name to material ID
	 * @param material Material name
	 * @return Material ID for item class
	 */
	public static int fromNameToId(String material)
	{
		switch(material)
		{
		case "cloth":
			return 0;
		case "leather":
			return 1;
		case "iron":
			return 2;
		case "stell":
			return 3;
		case "nephrite":
			return 4;
		default:
			return 0;
		}
	}
	/**
	 * Converts material name to ItemMaterial enum
	 * @param material String with material name
	 * @return ItemMaterial corresponding to specified name
	 */
	public static ItemMaterial fromName(String material)
	{
		switch(material)
		{
		case "cloth":
			return ItemMaterial.CLOTH;
		case "leather":
			return ItemMaterial.LEATHER;
		case "wood":
			return ItemMaterial.WOOD;
		case "iron":
			return ItemMaterial.IRON;
		case "steel":
			return ItemMaterial.STEEL;
		case "nephrite":
			return ItemMaterial.NEPHRITE;
		default:
			return ItemMaterial.IRON;
		}
	}
	/**
	 * Return material name
	 */
	public String toString()
	{
		switch(this)
		{
		case CLOTH:
			return TConnector.getText("ui", "matCloth");
		case LEATHER:
			return TConnector.getText("ui", "matLeath");
		case WOOD:
			return TConnector.getText("ui", "matWood");
		case IRON:
			return TConnector.getText("ui", "matIron");
		case STEEL:
			return TConnector.getText("ui", "matSteel");
		case NEPHRITE:
			return TConnector.getText("ui", "matNeph");
		default:
			return TConnector.getText("ui", "errorName");
		}
	}
}
