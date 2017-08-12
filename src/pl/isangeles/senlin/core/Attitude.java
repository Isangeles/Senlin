/*
 * Attitude.java
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
/**
 * Enumeration for characters attitudes
 * @author Isangeles
 *
 */
public enum Attitude 
{
	FRIENDLY, NEUTRAL, HOSTILE, DEAD;
    
    public static Attitude fromString(String id)
    {
        switch(id)
        {
        case "friendly":
            return Attitude.FRIENDLY;
        case "neutral":
            return Attitude.NEUTRAL;
        case "hostile":
            return Attitude.HOSTILE;
        case "dead":
            return Attitude.DEAD;
        default:
            return Attitude.FRIENDLY;
        }
    }
	
	@Override
	public String toString()
	{
		switch(this)
		{
		case FRIENDLY:
			return "friendly";
		case NEUTRAL:
			return "neutral";
		case HOSTILE:
			return "hostile";
		case DEAD:
			return "dead";
		default:
			return "friendly";
		}
	}
}
