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

import pl.isangeles.senlin.core.Module;
import pl.isangeles.senlin.core.character.Guild;
import pl.isangeles.senlin.util.DConnector;
/**
 * Class for game guilds base
 * @author Isangeles
 *
 */
public final class GuildsBase 
{
	private static Map<String, Guild> guildsBase = new HashMap<>();
	private static boolean loaded = false;
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
	public static Guild getGuild(String guildId) throws NoSuchElementException
	{
		Guild guild = guildsBase.get(guildId);
		if(guild != null)
		{
		    return guild;
		}
		else
		{
            return new Guild("none");
		}
		           
	}
	/**
	 * Loads XML guilds base file
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void load(String basePath) throws ParserConfigurationException, SAXException, IOException
	{
		if(!loaded)
		{
		    guildsBase = DConnector.getGuildsMap(basePath);
	        guildsBase.put("none", new Guild("none"));
	        loaded = true;
		}
	}
}
