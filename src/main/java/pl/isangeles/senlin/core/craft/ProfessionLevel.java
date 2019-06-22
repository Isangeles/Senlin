/*
 * ProfessionLevel.java
 * 
 * Copyright 2017 Dariusz Sikora <dev@isangeles.pl>
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
 * Enumeration for profession levels
 * @author Isangeles
 *
 */
public enum ProfessionLevel 
{
	NOVICE, APPRENTICE, SPECIALIST, MASTER;
	/**
	 * Converts profession level ID to profession level enum
	 * @param proTypeName String with level ID
	 * @return Profession level enum
	 */
	public static ProfessionLevel fromString(String proLvlName)
	{
		switch(proLvlName)
		{
		case "novice":
			return ProfessionLevel.NOVICE;
		case "apprentice":
			return ProfessionLevel.APPRENTICE;
		case "specialist":
			return ProfessionLevel.SPECIALIST;
		case "master":
			return ProfessionLevel.MASTER;
		default:
			return ProfessionLevel.MASTER;
		}
	}
	/**
	 * Returns level ID
	 */
	public String toString()
	{
		switch(this)
		{
		case NOVICE:
			return "novice";
		case APPRENTICE:
			return "apprentice";
		case SPECIALIST:
			return "specialist";
		case MASTER:
			return "master";
		default:
			return "error";
		}
	}
	/**
	 * Returns profession level name for UI
	 * @return String with profession level
	 */
	public String getName()
	{
		switch(this)
		{
		case NOVICE:
			return TConnector.getText("ui", "proLevN");
		case APPRENTICE:
			return TConnector.getText("ui", "proLevA");
		case SPECIALIST:
			return TConnector.getText("ui", "proLevS");
		case MASTER:
			return TConnector.getText("ui", "proLeVM");
		default:
			return TConnector.getText("ui", "errorName");
		}
	}
}
