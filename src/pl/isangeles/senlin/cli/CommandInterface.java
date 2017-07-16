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

import java.util.NoSuchElementException;
import java.util.Scanner;

import pl.isangeles.senlin.cli.tools.CharMan;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Guild;
import pl.isangeles.senlin.data.GuildsBase;
import pl.isangeles.senlin.data.ItemsBase;
import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.data.QuestsBase;
import pl.isangeles.senlin.data.SkillsBase;
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
	private CharMan charman;
	
	public CommandInterface(Character player)
	{
		this.player = player;
		charman = new CharMan(player);
	}

    /**
     * Checks entered command target, first command check  
     * @param command Text line with command to check 
     */
    public void checkCommand(String line)
    {
    	//If not a game command
    	if(!line.startsWith("$"))
    	{
    		player.speak(line);
    		return;
    	}
    	
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
        	
        	return;
        }
        
        if(toolName.equals("$charman"))
        {
        	Log.addDebug("In charman");
        	charman.handleCommand(command);
        	return;
        }
        
        if(toolName.equals("$system"))
        {
        	systemCommands(command);
        	return;
        }
        
        Log.addWarning(toolName + " " + TConnector.getText("ui", "logCmdFail"));
       
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
