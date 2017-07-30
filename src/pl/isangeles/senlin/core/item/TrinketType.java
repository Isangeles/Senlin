/*
 * TrinketType.java
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

/**
 * Enumeration for trinkets types
 * @author Isangeles
 *
 */
public enum TrinketType
{
	FINGER, NECK, ARTIFACT;
	
	public static TrinketType fromString(String typeName)
	{
		switch(typeName.toLowerCase())
		{
		case "finger":
			return TrinketType.FINGER;
		case "neck":
			return TrinketType.NECK;
		case "artifact":
			return TrinketType.ARTIFACT;
		default:
			return TrinketType.ARTIFACT;
		}
	}
}