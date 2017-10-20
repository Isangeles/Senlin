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

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.states.GameWorld;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.util.Settings;
import pl.isangeles.senlin.util.TilePosition;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.data.NpcBase;

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
        	return getCommands(command);
        }
        else if(commandTarget.equals("remove"))
        {
        	return removeCommands(command);
        }
        else if(commandTarget.equals("music"))
        {
        	return musicCommands(command);
        }
        else if(commandTarget.equals("add"))
        {
        	return addCommands(command);
        }
        
        Log.addSystem("no such target for worldman:" + commandTarget);
		return false;
	}
	/**
	 * Handles get commands
	 * @param command Command
	 * @return True if command was successfully executed, false otherwise
	 */
	public boolean getCommands(String command)
	{
		if(command.equals("time"))
		{
			Log.addSystem(world.getDay().getTime());
			return true;
		}
		return false;
	}
	/**
	 * Handles remove commands
	 * @param commandLine Command
	 * @return True if command was successfully executed, false otherwise
	 */
	public boolean removeCommands(String commandLine)
	{
		boolean out = false;
        Scanner scann = new Scanner(commandLine);
        String option = scann.next();
        String arg = scann.next();
        scann.close();
        
        if(option.equals("npc"))
        {
        	Character npc = world.getCurrentChapter().getCharacter(arg);
        	if(npc != null)
        	{
        		out = world.getCurrentChapter().removeTObject(npc);
        	}
        	else
        		Log.addSystem("bad value for remove: " + arg);
        }
        return out;
	}
	/**
	 * Handles music commands
	 * @param commandLine Command
	 * @return True if command was successfully executed, false otherwise
	 */
	public boolean musicCommands(String commandLine)
	{
		boolean out = false;
        Scanner scann = new Scanner(commandLine);
        String option = scann.next();
        String arg = scann.next();
        scann.close();
        
        if(option.equals("-play"))
        {
        	world.getMusiPlayer().play(1.0f, Settings.getMusicVol(), arg);
        	out = true;
        }
        else if(option.equals("-playSpecial"))
        {
        	world.getMusiPlayer().playFrom("special", 1.0f, Settings.getMusicVol(), arg);
        	out = true;
        }
        else if(option.equals("-list"))
        {
        	Log.addSystem(arg + ":" + world.getMusiPlayer().getTracksList(arg));
        	out = true;
        }
        
        return out;
	}

	/**
	 * Handles add commands
	 * @param commandLine Command
	 * @return True if command was successfully executed, false otherwise
	 */
	public boolean addCommands(String commandLine)
	{
		boolean out = false;
        Scanner scann = new Scanner(commandLine);
        String option = scann.next();
        String[] args = {scann.next(), scann.next()};
        scann.close();

		try
		{
			if(option.equals("ch") || option.equals("-character"))
			{
				String charId = args[0];
				String[] pos = {world.getPlayer().getPosition()[0] + "", world.getPlayer().getPosition()[1] + ""};
				String area = world.getArea().getId();
				if(args.length > 1)
					pos = args[1].split("x");
				if(args.length > 2)
					area = args[2];
				
				TilePosition p = new TilePosition(pos[0] + ";" + pos[1]);
				Character spawnedChar = NpcBase.spawnIn(charId, world.getCurrentChapter().getActiveScenario().getArea(area), p);
				world.getArea().getCharacters().add(spawnedChar);
				spawnedChar.setArea(world.getArea());
			}
		}
		catch(NoSuchElementException e)
		{
			Log.addSystem("not enough arguments");
		}
		catch(SlickException | IOException | FontFormatException e)
		{
			Log.addSystem("game error");
		}
		
        return out;
	}
}
