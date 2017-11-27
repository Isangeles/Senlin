/*
 * UiMan.java
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
import pl.isangeles.senlin.gui.tools.UserInterface;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.util.TilePosition;

/**
 * Class for managing user interface
 * @author Isangeles
 *
 */
public class UiMan implements CliTool 
{
	private UserInterface ui;
	/**
	 * User interface manager constructor
	 * @param ui User interface to manage
	 */
	public UiMan(UserInterface ui)
	{
		this.ui = ui;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.cli.tools.CliTool#handleCommand(java.lang.String)
	 */
	@Override
	public String handleCommand(String line) 
	{
	    String out = "1";
		Scanner scann = new Scanner(line);
        String commandTarget = "";
        String command = "";
        try
        {
            commandTarget = scann.next();
            command = scann.nextLine();
            
           if(commandTarget.equals("camera"))
        	   out = cameraCommands(command);
           if(commandTarget.equals("ui"))
        	   out = uiCommands(command);
        }
        catch(NoSuchElementException e)
        {
        	Log.addSystem("Command scann error:" + line);
        	out = "1";
        }
        finally
        {
            scann.close();
        }
        
		return out;
	}
	/**
	 * Handles camera commands
	 * @param commandLine Command
	 * @return True if command executed successfully, false otherwise
	 */
	public String cameraCommands(String commandLine)
	{
	    String out = "1";
        Scanner scann = new Scanner(commandLine);
        String prefix = scann.next();
        String value = scann.next();
        scann.close();
        
        try
        {
            if(prefix.equals("-m") || prefix.equals("-move"))
            {
            	Position pos = new Position(value);
            	ui.getCamera().setPos(pos);
            	out = "0";
            }
            if(prefix.equals("-c") || prefix.equals("-center"))
            {
            	String[] pos = value.split("x");
        		int x = Integer.parseInt(pos[0]);
        		int y = Integer.parseInt(pos[1]);
            	ui.getCamera().centerAt(new Position(x, y));
            	out = "0";
            }
            if(prefix.equals("-cat") || prefix.equals("-centerAtTile"))
            {
                String[] pos = value.split("x");
                int x = Integer.parseInt(pos[0]);
                int y = Integer.parseInt(pos[1]);
                ui.getCamera().centerAt(new TilePosition(x, y).asPosition());
                out = "0";
            }
        }
        catch(NumberFormatException | NoSuchElementException e)
        {
        	Log.addSystem("bad value for camera:" + value);
        	return "1";
        }
        
        return out;
	}
	/**
	 * Handles UI commands
	 * @param commandLine Command 
	 * @return True if command executed successfully, false otherwise
	 */
	public String uiCommands(String commandLine)
	{
	    String out = "1";
        Scanner scann = new Scanner(commandLine);
        String prefix = scann.next();
        String value = scann.next();
        scann.close();
        
        try
        {
            if(prefix.equals("-l") || prefix.equals("-lock"))
            {
            	boolean lock = Boolean.parseBoolean(value);
            	ui.setLock(lock);
            	out = "0";
            }
        }
        catch(NumberFormatException | NoSuchElementException e)
        {
        	Log.addSystem("bad value for lock: " + value);
        	return "1";
        }
        
        return out;
	}
}
