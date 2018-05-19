/*
 * WorldMan.java
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
import pl.isangeles.senlin.core.Module;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.data.NpcBase;

/**
 * CLI tool for game world management
 * @author Isangeles
 *
 */
public class WorldMan implements CliTool
{
	private static final String TOOL_NAME = "worldman";
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
	 * @see pl.isangeles.senlin.cli.tools.CliTool#getName()
	 */
	@Override
	public String getName() 
	{
		return TOOL_NAME;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.cli.tools.CliTool#equals(java.lang.String)
	 */
	@Override
	public boolean equals(String name) 
	{
		return name.equals(TOOL_NAME);
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.cli.tools.CliTool#handleCommand(java.lang.String)
	 */
	@Override
	public String[] handleCommand(String line) 
	{
		String[] output = {"0", ""};
		Scanner scann = new Scanner(line);
        try
        {
            String commandTarget = scann.next();
            String command = scann.nextLine();
            switch(commandTarget)
            {
            case "get":
            	output = getCommands(command);
            	break;
            case "remove":
            	output = removeCommands(command);
            	break;
            case "music":
            	output = musicCommands(command);
            	break;
            case "add":
            	output = addCommands(command);
            	break;
            case "set":
            	output = setCommands(command);
            	break;
            default:
                Log.addSystem("no such target for worldman:" + commandTarget);
                output[0] = "5";
                break;
            }
        }
        catch(NoSuchElementException e)
        {
        	Log.addSystem("Command scann error: " + line);
        	output[0] = "6";
        }
        finally
        {
            scann.close();
        }
		return output;
	}
	/**
	 * Handles get commands
	 * @param command Command
	 * @return Command result[0] and output[1]
	 */
	private String[] getCommands(String command)
	{
		String result = "0";
		String out = "";
		if(command.equals("time"))
		{
			out = world.getDay().getTime().toString();
		}
		
		return new String[] {result, out};
	}
	/**
	 * Handles remove commands
	 * @param commandLine Command
	 * @return Command result[0] and output[1]
	 */
	private String[] removeCommands(String commandLine)
	{
		String result = "0";
	    String out = "";
        Scanner scann = new Scanner(commandLine);
        try
        {
            String option = scann.next();
            String arg1 = scann.next();
            switch(option)
            {
            case "-n": case "--npc":
            	Character npc = world.getCurrentChapter().getCharacter(arg1);
            	if(npc != null)
            	{
            		if(!world.getCurrentChapter().removeTObject(npc))
            		    result = "2";
            	}
            	else
            	{
            		Log.addSystem("bad value for remove: " + arg1);
            		result = "2";
            	}
            	break;
            default: //TODO not such option msg
            	break;
            }
        }
        catch(NoSuchElementException e)
        {
        	result = "2";
        }
        finally
        {
            scann.close();
        }
        return new String[] {result, out};
	}
	/**
	 * Handles music commands
	 * @param commandLine Command
	 * @return Command result[0] and output[1]
	 */
	private String[] musicCommands(String commandLine)
	{
		String result = "0";
	    String out = "1";
        Scanner scann = new Scanner(commandLine);
        try
        {
            String option = scann.next();
            String arg1 = null;
            if(scann.hasNext())
                arg1 = scann.next();
            
            switch(option)
            {
            case "-p": case "--play":
                if(arg1 != null)
                    world.getMusiPlayer().play(1.0f, Settings.getMusicVol(), arg1);
                else
                    world.getMusiPlayer().playRandomFrom("exploring", 1.0f, Settings.getMusicVol());
                break;
            case "-s": case "--stop":
                world.getMusiPlayer().stop();
                break;
            case "-ps": case "--playSpecial": 
            		if(arg1 != null)
            			world.getMusiPlayer().playFrom("special", 1.0f, Settings.getMusicVol(), arg1);
                	break;
            case "-l": case "--list":
                if(arg1 != null)
                    out = arg1 + ":" + world.getMusiPlayer().getTracksList(arg1);
                else
                    out = arg1 + ":" + world.getMusiPlayer().getTracksList();
                break;
            }
        }
        catch(NoSuchElementException e)
        {
            Log.addSystem("Not enought arguments" + ":'" + commandLine + "'");
            result = "2";
        }
        finally
        {
            scann.close();
        }
        
        return new String[] {result, out};
	}

	/**
	 * Handles add commands
	 * @param commandLine Command
	 * @return Command result[0] and out[1]
	 */
	private String[] addCommands(String commandLine)
	{
		String result = "0";
	    String out = "";
        Scanner scann = new Scanner(commandLine);
        try
        {
            String option = scann.next();
            String arg1 = scann.next();
            String arg2 = null;
            String arg3 = null;
            if(scann.hasNext())
            	arg2 = scann.next();
            if(scann.hasNext())
            	arg3 = scann.next();
		
            switch(option)
            {
            case "-c": case "--character":
				String charId = arg1;
				String[] pos = {world.getPlayer().getPosition()[0] + "", world.getPlayer().getPosition()[1] + ""};
				String area = world.getArea().getId();
				if(arg2 != null)
					pos = arg2.split("x");
				if(arg3 != null)
					area = arg3;
				
				TilePosition p = new TilePosition(pos[0] + ";" + pos[1]);
				Character spawnedChar = NpcBase.spawnIn(charId, world.getCurrentChapter().getActiveScenario().getArea(area), p);
				world.getArea().getCharacters().add(spawnedChar);
				spawnedChar.setArea(world.getArea());
				break;
        	default:
        		result = "3";
				break;
            }
		}
		catch(NoSuchElementException e)
		{
			Log.addSystem("not enough arguments" + ":'" + commandLine + "'");
			result = "2";
		}
		catch(SlickException | IOException | FontFormatException e)
		{
			Log.addSystem("game error");
			result = "2";
		}
        finally
        {
            scann.close();
        }
		
        return new String[] {result, out};
	}
	/**
	 * Handles set commands
	 * @param command Command
	 * @return Command result[0] and out[0]
	 */
	private String[] setCommands(String command)
	{
		String result = "0";
		String out = "";
        Scanner scann = new Scanner(command);
        try
        {
            String option = scann.next();
            String arg1 = scann.next();
            String arg2 = null;
            if(scann.hasNext())
            	arg2 = scann.next();
            switch(option)
            {
            case "-t": case "--time":
        		int hours = Integer.parseInt(arg1);
        		int minutes = 0;
        		if(arg2 != null)
        			minutes = Integer.parseInt(arg2);
        		
        		world.getDay().getTime().addHours(hours);
        		world.getDay().getTime().addMinutes(minutes);
        		break;
            case "-c": case "--chapter":
            	if(arg1.equals("next"))
            		world.setNextChapterReq(true);
            	break;
        	default:
        		result = "3";
        		break;
            }
		}
		catch(NoSuchElementException e)
		{
			Log.addSystem("not enough arguments" + ":'" + command + "'");
			result = "2";
		}
        catch(NumberFormatException e)
        {
        	Log.addSystem("wrong arguments values" + ":'" + command + "'");
			result = "2";
        }
        finally
        {
            scann.close();
        }
		
        return new String[] {result, out};
	}
}
