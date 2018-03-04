/*
 * CommandInterface.java
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
package pl.isangeles.senlin.cli;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import pl.isangeles.senlin.cli.tools.CharMan;
import pl.isangeles.senlin.cli.tools.CliTool;
import pl.isangeles.senlin.cli.tools.UiMan;
import pl.isangeles.senlin.cli.tools.WorldMan;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.gui.tools.UserInterface;
import pl.isangeles.senlin.states.GameWorld;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for game command-line interface
 * 
 * command syntax: $[tool] [target] [command] [-option] [arguments]
 * 
 * @author Isangeles
 *
 */
public class CommandInterface 
{
	private Map<String, CliTool> tools;
	private Character player;
	private CharMan charman;
	private WorldMan worldman;
	private UiMan uiman;
	private ScriptProcessor ssp;
	/**
	 * Command interface constructor
	 * @param player Player character
	 * @param gw Game world
	 */
	public CommandInterface(Character player, GameWorld gw)
	{
		this.player = player;
		
		tools = new HashMap<>();
		charman = new CharMan(player, gw);
		worldman = new WorldMan(gw);
		tools.put(charman.getName(), charman);
		tools.put(worldman.getName(), worldman);
		
		ssp = new ScriptProcessor(this);
	}
	/**
	 * Executes specified command
	 * @param line String with command
	 * @return Array with command result[0] and output[1]
	 */
    public String[] executeCommand(String line)
    {	
    	String[] output = {"0", ""};
        Scanner scann = new Scanner(line);
        try
        {
            String toolName = scann.next().replace("$", "");
            String command = scann.nextLine();
            
            if(toolName.equals("debug"))
            {
            	if(command.equals("on"))
            	{
            		Log.setDebug(true);
            	}
            	else if(command.equals("off"))
            	{
            		Log.setDebug(false);
            	}
            }
            else
            {
        		if(tools.containsKey(toolName))
        		{
        			CliTool tool = tools.get(toolName);
    				output = tool.handleCommand(command);
        		}
            	else
            	{
                	Log.addWarning(toolName + " " + TConnector.getText("ui", "logCmdFail"));
                	output[0] = "8";
            	}
            }
        }
        catch(NoSuchElementException e)
        {
        	Log.addSystem("Command scann error: " + line);
        	output[0] = "9";
        }
        finally 
        {
            scann.close();
        }
        
        return output;
    }
    /**
     * Executes specified script
     * @param script Script to execute
     * @return True if script was successfully executed, false otherwise
     */
    public boolean executeScript(Script script)
    {
        return ssp.process(script);
    }
    /**
     * Sets GUI to manage by CLI
     * @param uiToMan GUI 
     */
    public void setUiMan(UserInterface uiToMan)
    {
    	uiman = new UiMan(uiToMan);
    	tools.put(uiman.getName(), uiman);
    }
    /**
     * Returns player character
     * @return Game character
     */
    public Character getPlayer()
    {
        return player;
    }
}
