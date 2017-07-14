/*
 * ProfessionType.java
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
package pl.isangeles.senlin.core.craft;

import pl.isangeles.senlin.util.TConnector;

/**
 * Enumeration for professions types
 * @author Isangeles
 *
 */
public enum ProfessionType
{
	ARMORCRAFTING, WEAPONCRAFTING, ALCHEMY, ENCHANTING;
	
	public static ProfessionType fromString(String proTypeName)
	{
		switch(proTypeName)
		{
		case "armor crafting":
			return ProfessionType.ARMORCRAFTING;
		case "weapon crafting":
			return ProfessionType.WEAPONCRAFTING;
		case "alchemy":
			return ProfessionType.ALCHEMY;
		case "enchanting":
			return ProfessionType.ENCHANTING;
		default:
			return ProfessionType.ARMORCRAFTING;
		}
	}
	
	public String toString()
	{
		switch(this)
		{
		case ARMORCRAFTING:
			return TConnector.getText("ui", "proArm");
		case WEAPONCRAFTING:
			return TConnector.getText("ui", "proWea");
		case ALCHEMY:
			return TConnector.getText("ui", "proAlch");
		case ENCHANTING:
			return TConnector.getText("ui", "proEnch");
		default:
			return TConnector.getText("ui", "errorName");
		}
	}
}
