/*
 * AttributeType.java
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

import pl.isangeles.senlin.util.TConnector;

/**
 * Enumeration for attributes types
 * @author Isangeles
 *
 */
public enum AttributeType 
{
	STRENGHT, CONSTITUTION, DEXTERITY, INTELLIGENCE, WISDOM;
	/**
	 * Converts attribute ID to attribute type enumeration
	 * @param attName String with attribute Id
	 * @return Attribute type enumeration
	 */
	public static AttributeType fromId(String attName)
	{
		switch(attName)
		{
		case "strenght": case "str":
			return STRENGHT;
		case "constitution": case "con":
			return CONSTITUTION;
		case "dexterity": case "dex":
			return DEXTERITY;
		case "intelligence": case "int":
			return INTELLIGENCE;
		case "wisdom": case "wis":
			return WISDOM;
		default:
			return STRENGHT;
		}
	}
	/**
	 * Returns name of this type
	 * @return String with name
	 */
	public String getName()
	{
		switch(this)
		{
		case STRENGHT:
			return TConnector.getText("ui", "attStr");
		case CONSTITUTION:
			return TConnector.getText("ui", "attCon");
		case DEXTERITY:
			return TConnector.getText("ui", "atttDex");
		case INTELLIGENCE:
			return TConnector.getText("ui", "attInt");
		case WISDOM:
			return TConnector.getText("ui", "attWis");
		default:
			return TConnector.getText("ui", "errorName");
		}
	}
	/**
	 * Returns ID of this type
	 * @return String with ID
	 */
	public String getId()
	{
		switch(this)
		{
		case STRENGHT:
			return "strenght";
		case CONSTITUTION:
			return "constitution";
		case DEXTERITY:
			return "dexterity";
		case INTELLIGENCE:
			return "intelligence";
		case WISDOM:
			return "wisdom";
		default:
			return "strenght";
		}
	}
}
