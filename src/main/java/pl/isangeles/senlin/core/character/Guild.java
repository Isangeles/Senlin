/*
 * Guild.java
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
package pl.isangeles.senlin.core.character;

import java.io.File;

import pl.isangeles.senlin.core.Module;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for game guilds
 * @author Isangeles
 *
 */
public class Guild 
{
    private String id;
	private String name;
	
	public Guild(String id) 
	{
		this.id = id;
		this.name = TConnector.getTextFromChapter("guilds", id);
	}
	
	public String getName()
	{
		if(name != "")
			return "<" + name + ">";
		else
			return name;
	}
	
	public String getId()
	{
		return id;
	}
	
	public String toString()
	{
		return name;
	}

}
