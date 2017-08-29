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
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.character.Guild;
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
            else if(toolName.equals("$charman"))
            {
            	Log.addDebug("In charman");
            	out = charman.handleCommand(command);
            }
            else if(toolName.equals("$worldman"))
            {
            	out = worldman.handleCommand(command);
            }
            else
            	Log.addWarning(toolName + " " + TConnector.getText("ui", "logCmdFail"));
        }
        catch(NoSuchElementException e)
        {
        	Log.addSystem("Command scann error: " + line);
        }
        scann.close();
        
        return out;
       
    }
    /**
     * Executes specified script
     * @param script Script object
     * @return True if script was successfully executed, false otherwise
     */
    public boolean executeScript(Script script)
    {
    	boolean out = true;
    	String scriptCode = script.toString();
    	String ifCode = script.getIfCode();
    	String endCode = script.getEndCode();
    	
    	Scanner scann = new Scanner(scriptCode);
    	scann.useDelimiter(";|(;\r?\n)");
    	
    	try
    	{
    		while(scann.hasNext())
        	{
        		if(checkCondition(script, ifCode))
            	{
        			String command = scann.next().replaceFirst("^\\s*", "");

            		script.used();
            		if(!executeCommand(command))
            		{
            			out = false;
            			break;
            		}
            	}
        		else
        			break;
        	}
        	scann.close();
        	
        	if(checkEndCondition(script))
        		script.finish();
    	}
    	catch(IndexOutOfBoundsException e)
    	{
    		Log.addSystem("cli_script_corrupted:" + script.getName());
    		script.finish();
    	}
    	
    	return out;
    }
    /**
     * Executes 'if code' of the script
     * @param script Script object
     * @param ifCode Code after if
     * @return True if conditions from if code are met, false otherwise 
     * @throws IndexOutOfBoundsException
     */
    private boolean checkCondition(Script script, String ifCode) throws IndexOutOfBoundsException
    {
    	boolean out = false;
    	Scanner scann = new Scanner(ifCode);
    	scann.useDelimiter("\r?\n");
    	
    	while(scann.hasNext())
    	{
    		String command = scann.next().replaceFirst("^\\s*", "");
        	if(command.equals("true;"))
        		out = true;
        	if(command.startsWith("use="))
        	{
        		int value = Integer.parseInt(command.substring(command.indexOf("=")+1, command.indexOf(";")));
        		if(script.getUseCount() >= value)
        			out = true;
        		else
        		{
        			out = false;
        			break;
        		}
        	}
        	if(command.startsWith("has"))
        	{
        		String[] hasCommand = command.split(" ");
        		if(hasCommand[1].equals("flag"))
        		{
        			String flag = hasCommand[2];
        			if(hasCommand.length < 4 || hasCommand[3].equals("player"))
        			{
        				if(player.getFlags().contains(flag))
        					out = true;
        				else
        				{
        					out = false;
        					break;
        				}
        			}
        		}
        	}
        	if(command.startsWith("!has"))
        	{
        		String[] hasCommand = command.split(" ");
        		if(hasCommand[1].equals("flag"))
        		{
        			String flag = hasCommand[2];
        			if(hasCommand.length < 4 || hasCommand[3].equals("player;"))
        			{
        				if(!player.getFlags().contains(flag))
        					out = true;
        				else
        				{
        					out = false;
        					break;
        				}
        			}
        		}
        	}
    	}
    	
    	scann.close();
    	
    	return out;
    }
    /**
     * Executes code after end statement
     * @param script Script object
     * @return True if conditions of end code are met, false otherwise
     * @throws IndexOutOfBoundsException
     */
    private boolean checkEndCondition(Script script) throws IndexOutOfBoundsException
    {
    	boolean out = false;
    	Scanner scann = new Scanner(script.getEndCode());
    	scann.useDelimiter(":|(:\r?\n)");
    	if(scann.next().equals("if"))
    	{
    		String ifCommand = scann.next();
    		ifCommand.replaceFirst("^\\s*", "");
    		
    		out = checkCondition(script, ifCommand);
    	}
    	
    	scann.close();
    	return out;
    }
}
