/*
 * WorldMan.java
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
package pl.isangeles.senlin.cli.tools;

import java.util.NoSuchElementException;
import java.util.Scanner;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.states.GameWorld;

/**
 * CLI tool for game world management
 * @author Isangeles
 *
 */
public class WorldMan implements CliTool
{
	private GameWorld world;
	/**
	 * World manager constructor
	 * @param world Game world
	 */
	public WorldMan(GameWorld world)
	{
		this.world = world;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.cli.tools.CliTool#handleCommand(java.lang.String)
	 */
	@Override
	public boolean handleCommand(String line) 
	{
		Scanner scann = new Scanner(line);
        String commandTarget = "";
        String command = "";
        try
        {
            commandTarget = scann.next();
            command = scann.nextLine();
        }
        catch(NoSuchElementException e)
        {
        	Log.addSystem("Command scann error: " + line);
        	return false;
        }
        finally
        {
            scann.close();
        }
        
        if(commandTarget.equals("get"))
        {
        	return getCommand(command);
        }
        
		return false;
	}

	public boolean getCommand(String command)
	{
		if(command.equals("time"))
		{
			Log.addSystem(world.getDay().getTime());
			return true;
		}
		return false;
	}
}
