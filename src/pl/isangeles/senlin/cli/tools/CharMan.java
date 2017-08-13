/*
 * CharMod.java
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
import pl.isangeles.senlin.core.Guild;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.craft.Profession;
import pl.isangeles.senlin.core.craft.ProfessionType;
import pl.isangeles.senlin.core.craft.Recipe;
import pl.isangeles.senlin.data.GuildsBase;
import pl.isangeles.senlin.data.ItemsBase;
import pl.isangeles.senlin.data.QuestsBase;
import pl.isangeles.senlin.data.RecipesBase;
import pl.isangeles.senlin.data.SkillsBase;
import pl.isangeles.senlin.util.TConnector;

/**
 * CLI tool for game characters management
 * @author Isangeles
 *
 */
public class CharMan implements CliTool
{
	private Character player;
	/**
	 * Characters manager constructor
	 * @param player Player character
	 */
	public CharMan(Character player)
	{
		this.player = player;
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.cli.CliTool#handleCommand(java.lang.String)
	 */
	@Override
	public boolean handleCommand(String line) 
	{
		boolean out = false;
		Scanner scann = new Scanner(line);
        String commandTarget = "";
        String command = "";
        try
        {
            commandTarget = scann.next();
            command = scann.nextLine();
            
            if(commandTarget.equals("player"))
            	out = playerCommands(command);
        }
        catch(NoSuchElementException e)
        {
        	Log.addSystem("Command scann error: " + line);
        	out = false;
        }
        finally
        {
            scann.close();
        }
        
		return out;
	}
	

    /**
     * Checks entered command for player, second command check
     * @param commandLine Rest of command line (after target) 
     */
    private boolean playerCommands(String commandLine)
    {
    	boolean out = false;
        Scanner scann = new Scanner(commandLine);
        String command = scann.next();
        String prefix = scann.nextLine();
        scann.close();
        
        if(command.equals("add"))
        {
        	out = addCommands(prefix, player);
        }
        else if(command.equals("remove"))
        {
        	out = removeCommands(prefix, player);
        }
        else if(command.equals("set"))
        {
        	out = setCommands(prefix, player);
        }
        else if(command.equals("show"))
        {
        	out = showCommands(prefix, player);
        }
        else
        	Log.addSystem(command + " " + TConnector.getText("ui", "logCmdPla"));
        return out;
    }
    /**
     * Checks set command for target, last command check
     * @param commandLine Rest of command line (after command)
     * @param target Target of command
     */
    private boolean setCommands(String commandLine, Character target)
    {
    	boolean out = false;
    	Scanner scann = new Scanner(commandLine);
    	String prefix = scann.next();
    	String value = scann.next();
    	scann.close();
    	
    	try
    	{
    		if(prefix.equals("-guild"))
        	{
    			Guild guild = GuildsBase.getGuild(value);
        		target.setGuild(guild);
        		out = true;
        	}
        	else if(prefix.equals("-attitude"))
        	{
        		out = true;
        	}
        	else
            	Log.addSystem(prefix + " " + TConnector.getText("ui", "logCmdSet"));
    	}
    	catch(NumberFormatException | NoSuchElementException e)
		{
			Log.addSystem("bad value: " + value);
		}
        return out;
    }
    /**
     * Checks add command for target, last command check
     * @param commandLine Rest of command line (after command)
     * @param target Target of command
     */
    private boolean addCommands(String commandLine, Character target)
    {
		boolean out = false;
		
    	Scanner scann = new Scanner(commandLine);
    	String prefix = scann.next();
    	String value = scann.next();
    	scann.close();
    	
    	try
    	{
    		if(prefix.equals("-i") || prefix.equals("-item"))
        	{
    			out = target.addItem(ItemsBase.getItem(value));
        		if(out)
                    Log.addInformation(TConnector.getText("ui", "logAddI"));
                else
                    Log.addInformation(TConnector.getText("ui", "logAddIFail"));
        	}
    		else if(prefix.equals("-g") || prefix.equals("-gold"))
    	        target.addGold(Integer.parseInt(value));
    		else if(prefix.equals("-h"))
    		    target.addHealth(Integer.parseInt(value));
    		else if(prefix.equals("-m"))
        	    target.addMagicka(Integer.parseInt(value));
    		else if(prefix.equals("-e"))
        	    target.addExperience(Integer.parseInt(value));
    		else if(prefix.equals("-s") || prefix.equals("-skills"))
        		out = target.addSkill(SkillsBase.getAttack(target, value));
    		else if(prefix.equals("-p") || prefix.equals("-profession"))
        		out = target.addProfession(new Profession(ProfessionType.fromString(value)));
    		else if(prefix.equals("-r") || prefix.equals("-recipe"))
        	{
        		Recipe recipe = RecipesBase.get(value);
        		if(recipe != null && target.getProfession(recipe.getType()) != null)
        			out = target.getProfession(recipe.getType()).add(recipe);
        	}
    		else if(prefix.equals("-q") || prefix.equals("-quest"))
        		target.startQuest(QuestsBase.get(value));
    		else
            	Log.addSystem(prefix + " " + TConnector.getText("ui", "logCmdAdd"));
        	
    		Log.addSystem("Command out:" + out);
        	
    	}
    	catch(NumberFormatException e)
    	{
    		Log.addSystem(TConnector.getText("ui", "logBadVal"));
    	}

        return out;
    }
    /**
     * Checks remove command for target, last command check
     * @param commLine Rest of command line (after command)
     * @param target Target of command
     */
    private boolean removeCommands(String commandLine, Character target)
    {
    	boolean out = false;
    	Scanner scann = new Scanner(commandLine);
    	String prefix = scann.next();
    	String value = scann.next();
    	scann.close();
    	
    	try
    	{
    		if(prefix.equals("-h"))
    		{
    		    player.takeHealth(Integer.parseInt(value));
    		    out = true;
    		}
    		else if(prefix.equals("-m"))
        	{
        	    player.takeMagicka(Integer.parseInt(value));
        	    out = true;
        	}
    		else if(prefix.equals("-e"))
        	{
        	    player.takeExperience(Integer.parseInt(value));
        	    out = true;
        	}
    		else
    			Log.addSystem(prefix + " " + TConnector.getText("ui", "logCmdRem"));
    	}
    	catch(NumberFormatException e)
    	{
    		Log.addInformation(TConnector.getText("ui", "logBadVal"));
    	}
    	return out;
    }
    /**
     * Checks show command for target, last command check
     * @param commLine Rest of command line (after command)
     * @param target Target of command
     */
    private boolean showCommands(String commandLine, Character target)
    {
    	boolean out = false;
    	Scanner scann = new Scanner(commandLine);
    	String prefix = scann.next();
    	String value = scann.next();
    	scann.close();
    	
    	if(prefix.equals("-f"))
    	{
    		Log.addSystem(target.getName() + "//flags: " + target.getFlags().list());
    		out = true;
    	}
    	else if(prefix.equals("-r"))
    	{
    		Log.addSystem(target.getProfession(ProfessionType.fromString(value)).toString());
    		out = true;
    	}
    	else
        	Log.addSystem(prefix + " " + TConnector.getText("ui", "logCmdSho"));
        return out;
    }
}
