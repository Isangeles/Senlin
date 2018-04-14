/*
 * Gender.java
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
package pl.isangeles.senlin.core.character;

import pl.isangeles.senlin.util.TConnector;

/**
 * Enumeration for game character gender
 * @author Isangeles
 *
 */
public enum Gender 
{
	MALE, FEMALE;
	/**
	 * Converts specified gender ID to gender enum
	 * @param id String with gender ID
	 * @return Gender enum
	 */
	public static Gender fromId(String id)
	{
		switch(id)
		{
		case "male":
			return Gender.MALE;
		case "female":
			return Gender.FEMALE;
		default:
			return Gender.MALE;
		}
	}

	@Override
	public String toString()
	{
		switch(this)
		{
		case MALE:
			return "male";
		case FEMALE:
			return "female";
		default:
			return "error";
		}
	}
	/**
	 * Returns localized gender name
	 * @return String with geneder name
	 */
	public String getName()
	{
		switch(this)
		{
		case MALE:
			return TConnector.getText("ui", "genMale");
		case FEMALE:
			return TConnector.getText("ui", "genFemale");
		default:
			return TConnector.getText("ui", "errorName");
		}
	}
	/**
	 * Returns name of sprite sheet for this gender
	 * @param basicSSName Basic name of sprite sheet
	 * @return String with full sprite sheet name
	 */
	public String getSSName(String basicSSName)
	{
		switch(this)
		{
		case MALE:
			return "m-" + basicSSName;
		case FEMALE:
			return "f-" + basicSSName;
		default:
			return "m-" + basicSSName;
		}
	}
}
