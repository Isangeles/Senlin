/*
 * UiMan.java
 * 
 * Copyright 2017-2018 Dariusz Sikora <dev@isangeles.pl>
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
	private static final String TOOL_NAME = "uiman";
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
	    String[] output = new String[] {"0", ""};
		Scanner scann = new Scanner(line);
        try
        {
            String commandTarget = scann.next();
            String command = scann.nextLine();
            
            switch(commandTarget)
            {
            case "camera":
            	output = cameraCommands(command);
            	break;
            case "ui":
            	output = uiCommands(command);
            	break;
        	default:
        		output[0] = "5";
            }
        }
        catch(NoSuchElementException e)
        {
        	Log.addSystem("Command scann error:" + line);
        	output[0] = "6";
        }
        finally
        {
            scann.close();
        }
        
		return output;
	}
	/**
	 * Handles camera commands
	 * @param commandLine Command
	 * @return Command result[0] and output[1]
	 */
	public String[] cameraCommands(String commandLine)
	{
		String result = "0";
	    String out = "";
        Scanner scann = new Scanner(commandLine);
        
        try
        {
            String prefix = scann.next();
            String arg1 = scann.next();
            
        	switch(prefix)
        	{
        	case "-m": case "--move":
            	Position posMove = new Position(arg1);
            	ui.getCamera().setPos(posMove);
            	break;
        	case "-c": case "--center":
            	String[] posCenter = arg1.split("x");
        		int xC = Integer.parseInt(posCenter[0]);
        		int yC = Integer.parseInt(posCenter[1]);
            	ui.getCamera().centerAt(new Position(xC, yC));
            	break;
        	case "-cat": case "--centerAtTile":
                String[] posCenterT = arg1.split("x");
                int xCat = Integer.parseInt(posCenterT[0]);
                int yCat = Integer.parseInt(posCenterT[1]);
                ui.getCamera().centerAt(new TilePosition(xCat, yCat).asPosition());
                break;
            default:
            	result = "3";
        	}
        }
        catch(NumberFormatException | NoSuchElementException e)
        {
        	Log.addSystem("bad value for camera" + ":'" + commandLine + "'");
        	result = "1";
        }
        finally
        {
        	scann.close();
        }
        
        return new String[] {result, out};
	}
	/**
	 * Handles UI commands
	 * @param commandLine Command 
	 * @return Command result[0] and output[1]
	 */
	public String[] uiCommands(String commandLine)
	{
		String result = "0";
	    String out = "";
        Scanner scann = new Scanner(commandLine);
        
        try
        {
            String prefix = scann.next();
            String value = scann.next();
            
            switch(prefix)
            {
            case "-l": case "--lock":
            	boolean lock = Boolean.parseBoolean(value);
            	ui.setLock(lock);	
            	break;
        	default:
        		result = "3";
            }
        }
        catch(NumberFormatException | NoSuchElementException e)
        {
        	Log.addSystem("bad value for lock" + ":'" + commandLine + "'");
        	result = "1";
        }
        finally
        {
            scann.close();
        }
        
        return new String[] {result, out};
	}
}
