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
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Guild;
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
        
        if(commandTarget.equals("player"))
        	playerCommands(command);
        
		return true;
	}
	

    /**
     * Checks entered command for player, second command check
     * @param commandLine Rest of command line (after target) 
     */
    private void playerCommands(String commandLine)
    {
        Scanner scann = new Scanner(commandLine);
        String command = scann.next();
        String prefix = scann.nextLine();
        scann.close();
        
        if(command.equals("add"))
        {
            addCommands(prefix, player);
            return;
        }
        if(command.equals("remove"))
        {
        	removeCommands(prefix, player);
            return;
        }
        if(command.equals("set"))
        {
        	setCommands(prefix, player);
            return;
        }
        if(command.equals("show"))
        {
        	showCommands(prefix, player);
        	return;
        }
        
        Log.addSystem(command + " " + TConnector.getText("ui", "logCmdPla"));
    }
    /**
     * Checks set command for target, last command check
     * @param commandLine Rest of command line (after command)
     * @param target Target of command
     */
    private void setCommands(String commandLine, Character target)
    {
    	Scanner scann = new Scanner(commandLine);
    	String prefix = scann.next();
    	String value = scann.next();
    	scann.close();
    	
    	if(prefix.equals("-guild"))
    	{
    		try
    		{
    			Guild guild = GuildsBase.getGuild(value);
        		target.setGuild(guild);
    		}
    		catch(NumberFormatException | NoSuchElementException e)
    		{
    			Log.addSystem("bad value: " + value);
    		}
    		return;
    	}
    	if(prefix.equals("-attitude"))
    	{
    		return;
    	}

        Log.addSystem(prefix + " " + TConnector.getText("ui", "logCmdSet"));
    }
    /**
     * Checks add command for target, last command check
     * @param commandLine Rest of command line (after command)
     * @param target Target of command
     */
    private void addCommands(String commandLine, Character target)
    {
    	Scanner scann = new Scanner(commandLine);
    	String prefix = scann.next();
    	String value = scann.next();
    	scann.close();
    	
    	if(prefix.equals("-i"))
    	{
    		if(target.addItem(ItemsBase.getItem(value)))
                Log.addInformation(TConnector.getText("ui", "logAddI"));
            else
                Log.addInformation(TConnector.getText("ui", "logAddIFail"));
    	}
    	try
    	{
    		Boolean out = false;
    	    if(prefix.equals("-g") || prefix.equals("-gold"))
    	        target.addGold(Integer.parseInt(value));
    		if(prefix.equals("-h"))
    		    target.addHealth(Integer.parseInt(value));
        	if(prefix.equals("-m"))
        	    target.addMagicka(Integer.parseInt(value));
        	if(prefix.equals("-e"))
        	    target.addExperience(Integer.parseInt(value));
        	if(prefix.equals("-s") || prefix.equals("-skills"))
        		out = target.addSkill(SkillsBase.getAttack(target, value));
        	if(prefix.equals("-p") || prefix.equals("-profession"))
        		out = target.addProfession(new Profession(ProfessionType.fromString(value)));
        	if(prefix.equals("-r") || prefix.equals("-recipe"))
        	{
        		Recipe recipe = RecipesBase.get(value);
        		if(recipe != null && target.getProfession(recipe.getType()) != null)
        			out = target.getProfession(recipe.getType()).add(recipe);
        	}
        	if(prefix.equals("-q") || prefix.equals("-quest"))
        		target.startQuest(QuestsBase.get(value));
        	
        	Log.addSystem("Command out:" + out.toString());
        	return;
    	}
    	catch(NumberFormatException e)
    	{
    		Log.addSystem(TConnector.getText("ui", "logBadVal"));
    	}

        Log.addSystem(prefix + " " + TConnector.getText("ui", "logCmdAdd"));
    }
    /**
     * Checks remove command for target, last command check
     * @param commLine Rest of command line (after command)
     * @param target Target of command
     */
    private void removeCommands(String commandLine, Character target)
    {
    	Scanner scann = new Scanner(commandLine);
    	String prefix = scann.next();
    	String value = scann.next();
    	scann.close();
    	
    	try
    	{
    		if(prefix.equals("-h"))
    		{
    		    player.takeHealth(Integer.parseInt(value));
    		    return;
    		}
        	if(prefix.equals("-m"))
        	{
        	    player.takeMagicka(Integer.parseInt(value));
        	    return;
        	}
        	if(prefix.equals("-e"))
        	{
        	    player.takeExperience(Integer.parseInt(value));
        	    return;
        	}
        	
            Log.addSystem(prefix + " " + TConnector.getText("ui", "logCmdRem"));
    	}
    	catch(NumberFormatException e)
    	{
    		Log.addInformation(TConnector.getText("ui", "logBadVal"));
    	}

    }
    /**
     * Checks show command for target, last command check
     * @param commLine Rest of command line (after command)
     * @param target Target of command
     */
    private void showCommands(String commandLine, Character target)
    {
    	Scanner scann = new Scanner(commandLine);
    	String prefix = scann.next();
    	String value = scann.next();
    	scann.close();
    	
    	if(prefix.equals("-f"))
    	{
    		Log.addSystem(target.getName() + "//flags: " + target.getFlags().list());
    		return;
    	}
    	if(prefix.equals("-r"))
    	{
    		Log.addSystem(target.getProfession(ProfessionType.fromString(value)).toString());
    		return;
    	}
        Log.addSystem(prefix + " " + TConnector.getText("ui", "logCmdSho"));
    }
}
