/*
 * WeaponType.java
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
 * Enumeration for weapon types
 * @author Isangeles
 *
 */
public enum WeaponType 
{
	DAGGER, SWORD, AXE, MACE, SPEAR, BOW, FIST;
	/**
	 * Converts type name to type ID
	 * @param type Type name 
	 * @return Type ID for item class
	 */
	public static int fromNameToId(String type)
	{
		switch(type.toLowerCase())
		{
		case "dagger":
			return 0;
		case "sword":
			return 1;
		case "axe":
			return 2;
		case "mace":
			return 3;
		case "spear":
			return 4;
		case "bow":
			return 5;
		case "fist":
			return 6;
		default:
			return 6;
		}
	}
	/**
	 * Converts type name to type object
	 * @param type String with type name
	 * @return Weapon type corresponding to specified name
	 */
	public static WeaponType fromName(String type)
	{
		switch(type.toLowerCase())
		{
		case "dagger":
			return DAGGER;
		case "sword":
			return SWORD;
		case "axe":
			return AXE;
		case "mace":
			return MACE;
		case "spear":
			return SPEAR;
		case "bow":
			return BOW;
		case "fist":
			return FIST;
		default:
			return FIST;
		}
	}
	/**
	 * Returns material ID
	 * @return Material ID
	 */
	public int getId()
	{
		switch(this)
		{
		case DAGGER:
			return 0;
		case SWORD:
			return 1;
		case AXE:
			return 2;
		case MACE:
			return 3;
		case SPEAR:
			return 4;
		case BOW:
			return 5;
		case FIST:
			return 6;
		default:
			return 6;
		}
	}
	
	public String getName()
	{
	    switch(this)
        {
        case DAGGER:
            return TConnector.getText("ui", "weaDagger");
        case SWORD:
            return TConnector.getText("ui", "weaSword");
        case AXE:
            return TConnector.getText("ui", "weaAxe");
        case MACE:
            return TConnector.getText("ui", "weaMace");
        case SPEAR:
            return TConnector.getText("ui", "weaSpear");
        case BOW:
            return TConnector.getText("ui", "weaBow");
        case FIST:
            return TConnector.getText("ui", "weaFist");
        default:
            return TConnector.getText("ui", "errorName");
        }
	}
}
