/*
 * GuildsBase.java
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
package pl.isangeles.senlin.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import pl.isangeles.senlin.core.Guild;
import pl.isangeles.senlin.util.DConnector;
/**
 * Class for game guilds base
 * @author Isangeles
 *
 */
public final class GuildsBase 
{
	private static Map<Integer, Guild> guildsBase = new HashMap<>();
	/**
	 * Private constructor to prevent initialization
	 */
	private GuildsBase(){}
	/**
	 * Returns guild with specified ID from base
	 * @param guildId Guild ID
	 * @return Guild with specified ID
	 * @throws NoSuchElementException If guild with specified ID was not found
	 */
	public static Guild getGuild(int guildId) throws NoSuchElementException
	{
		Guild guild = new Guild(0, "None");
		
		if(guildId == 0)
			return guild;
		else
		{
			guild = guildsBase.get(guildId);
			if(guild == null)
				throw new NoSuchElementException();
			else
				return guild;
		}
	}
	/**
	 * Loads XML guilds base file
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void load() throws ParserConfigurationException, SAXException, IOException
	{
		guildsBase = DConnector.getGuildsMap("guilds");
	}
}
