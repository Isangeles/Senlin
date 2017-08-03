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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import pl.isangeles.senlin.cli.tools.CharMan;
import pl.isangeles.senlin.cli.tools.WorldMan;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Guild;
import pl.isangeles.senlin.data.GuildsBase;
import pl.isangeles.senlin.data.ItemsBase;
import pl.isangeles.senlin.data.QuestsBase;
import pl.isangeles.senlin.data.SkillsBase;
import pl.isangeles.senlin.states.GameWorld;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for game command-line interface
 * 
 * command syntax: $[tool] [target] [command] [-prefix] [value]
 * 
 * @author Isangeles
 *
 */
public class CommandInterface 
{
	private Character player;
	private GameWorld gw;
	private CharMan charman;
	private WorldMan worldman;
	
	public CommandInterface(Character player, GameWorld gw)
	{
		this.player = player;
		this.gw = gw;
		charman = new CharMan(player);
		worldman = new WorldMan(gw);
	}

    /**
     * Checks entered command target, first command check  
     * @param command Text line with command to check 
     */
    public boolean executeCommand(String line)
    {
    	//If not a game command
    	if(!line.startsWith("$"))
    	{
    		player.speak(line);
    		return true;
    	}
    	
    	boolean out = true;
    	
        Scanner scann = new Scanner(line);
        String toolName = "";
        String command = "";
        try
        {
            toolName = scann.next();
            command = scann.nextLine();
        }
        catch(NoSuchElementException e)
        {
        	Log.addSystem("Command scann error: " + line);
        }
        scann.close();
        
        if(toolName.equals("$debug"))
        {
        	if(command.equals("on"))
        	{
        		Log.setDebug(true);
        	}
        	else if(command.equals("off"))
        	{
        		Log.setDebug(false);
        	}
        	
        	out = true;
        }
        
        if(toolName.equals("$charman"))
        {
        	Log.addDebug("In charman");
        	out = charman.handleCommand(command);
        }
        
        if(toolName.equals("$system"))
        {
        	systemCommands(command);
        	out = true;
        }
        
        if(toolName.equals("$worldman"))
        {
        	out = worldman.handleCommand(command);
        }
        
        if(!out)
        	Log.addWarning(toolName + " " + TConnector.getText("ui", "logCmdFail"));
        
        return out;
       
    }
    
    public boolean executeScript(List<String> script)
    {
    	for(String command : script)
    	{
    		if(executeCommand(command))
    		{
    			return false;
    		}
    	}
    	return true;
    }
    /**
     * Checks entered command for system, second command check
     * @param commandLine Rest of command line (after target) 
     */
    private void systemCommands(String commandLine)
    {
    	Scanner scann = new Scanner(commandLine);
        String command = scann.next();
        String prefix = scann.nextLine();
        scann.close();
        

        Log.addSystem(command + " " + TConnector.getText("ui", "logCmdSys"));
    }
}
