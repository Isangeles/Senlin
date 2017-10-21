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

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.TargetableObject;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.character.Guild;
import pl.isangeles.senlin.core.craft.Profession;
import pl.isangeles.senlin.core.craft.ProfessionType;
import pl.isangeles.senlin.core.craft.Recipe;
import pl.isangeles.senlin.core.out.CharacterOut;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.data.GuildsBase;
import pl.isangeles.senlin.data.ItemsBase;
import pl.isangeles.senlin.data.NpcBase;
import pl.isangeles.senlin.data.QuestsBase;
import pl.isangeles.senlin.data.RecipesBase;
import pl.isangeles.senlin.data.SkillsBase;
import pl.isangeles.senlin.states.GameWorld;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.util.TilePosition;

/**
 * CLI tool for game characters management
 * @author Isangeles
 *
 */
public class CharMan implements CliTool
{
	private Character player;
	private GameWorld gw;
	/**
	 * Characters manager constructor
	 * @param player Player character
	 * @param gw Game world
	 */
	public CharMan(Character player, GameWorld gw)
	{
		this.player = player;
		this.gw = gw;
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
            	out = characterCommands(command, player);
            else
            {
            	Character npc = gw.getCurrentChapter().getCharacter(commandTarget);
            	if(npc != null)
            		out = characterCommands(command, npc);
            	else
            		Log.addSystem("no such target for charman: " + commandTarget);
            }
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
     * Checks entered command for specified game character, second command check
     * @param commandLine Rest of command line (after target) 
     * @param target Command target
     */
    private boolean characterCommands(String commandLine, Character target)
    {
    	boolean out = false;
        Scanner scann = new Scanner(commandLine);
        String command = scann.next();
        String prefix = scann.nextLine();
        scann.close();
        
        if(command.equals("add"))
        {
        	out = addCommands(prefix, target);
        }
        else if(command.equals("remove"))
        {
        	out = removeCommands(prefix, target);
        }
        else if(command.equals("set"))
        {
        	out = setCommands(prefix, target);
        }
        else if(command.equals("show"))
        {
        	out = showCommands(prefix, target);
        }
        else if(command.equals("use"))
        {
            out = useCommands(prefix, target);
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
        	else if(prefix.equals("-position"))
        	{
        		String[] pos = value.split("x");
        		int x = Integer.parseInt(pos[0]);
        		int y = Integer.parseInt(pos[1]);
        		out = target.setPosition(new TilePosition(x, y));
        	}
        	else if(prefix.equals("-destination"))
        	{
        		String[] pos = value.split("x");
        		int x = Integer.parseInt(pos[0]);
        		int y = Integer.parseInt(pos[1]);
        		target.moveTo(x, y);
        		out = true;
        	}
            else if(prefix.equals("-destTile"))
            {
                String[] pos = value.split("x");
                int row = Integer.parseInt(pos[0]);
                int column = Integer.parseInt(pos[1]);
                Position p = new TilePosition(row, column).asPosition();
                target.moveTo(p.x, p.y);
                out = true;
            }
        	else
            	Log.addSystem(prefix + " " + TConnector.getText("ui", "logCmdSet"));
    	}
    	catch(NumberFormatException | NoSuchElementException | IndexOutOfBoundsException e)
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
    	String[] args = scann.next().split(" ");
    	scann.close();
    	
    	try
    	{
    		if(prefix.equals("-i") || prefix.equals("-item"))
        	{
    			out = target.addItem(ItemsBase.getItem(args[0]));
        		if(out)
                    Log.addInformation(TConnector.getText("ui", "logAddI"));
                else
                    Log.addInformation(TConnector.getText("ui", "logAddIFail"));
        	}
    		else if(prefix.equals("-g") || prefix.equals("-gold"))
    		{
    	        target.addGold(Integer.parseInt(args[0]));
    	        out = true;
    		}
    		else if(prefix.equals("-h"))
    		{
    		    target.modHealth(Integer.parseInt(args[0]));
    		    out = true;
    		}
    		else if(prefix.equals("-m"))
    		{
        	    target.modMagicka(Integer.parseInt(args[0]));
        	    out = true;
    		}
    		else if(prefix.equals("-e"))
    		{
        	    target.modExperience(Integer.parseInt(args[0]));
        	    out = true;
    		}
    		else if(prefix.equals("-s") || prefix.equals("-skills"))
        		out = target.addSkill(SkillsBase.getSkill(target, args[0]));
    		else if(prefix.equals("-sp") ||  prefix.equals("-speech"))
    		{
    			String speech = TConnector.getTextFromChapter("speeches", args[0]);
    			target.speak(speech);
    			out = true;
    		}
    		else if(prefix.equals("-p") || prefix.equals("-profession"))
        		out = target.addProfession(new Profession(ProfessionType.fromString(args[0])));
    		else if(prefix.equals("-r") || prefix.equals("-recipe"))
        	{
        		Recipe recipe = RecipesBase.get(args[0]);
        		if(recipe != null && target.getProfession(recipe.getType()) != null)
        			out = target.getProfession(recipe.getType()).add(recipe);
        	}
    		else if(prefix.equals("-f") || prefix.equals("-flag"))
    		{
    			target.getFlags().add(args[0]);
    			out = true;
    		}
    		else if(prefix.equals("-q") || prefix.equals("-quest"))
    		{
        		target.startQuest(QuestsBase.get(args[0]));
        		out = true;
    		}
    		else if(prefix.equals("-l") || prefix.equals("-level"))
    		{
    			target.levelUp();
    			out = true;
    		}
    		else if(prefix.equals("-at") || prefix.equals("-attackTarget"))
    		{
    			Targetable attackTarget = gw.getCurrentChapter().getTObject(args[0]);
    			if(attackTarget != null)
    			{
    				target.enterCombat(attackTarget);
    				out = true;
    			}
    			else
    				Log.addSystem("no such object: " + args[0]);
    		}
    		else
            	Log.addSystem(prefix + " " + TConnector.getText("ui", "logCmdAdd"));
    	}
    	catch(NumberFormatException e)
    	{
    		Log.addSystem(TConnector.getText("ui", "logBadVal"));
    	}
    	catch(NoSuchElementException e)
    	{
    		Log.addSystem("not enough arguments");
    	}
    	catch(SlickException | IOException | FontFormatException e)
    	{
    		Log.addSystem("some fatal error");
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
    	String arg1 = scann.next();
    	String arg2 = null;
    	if(scann.hasNext())
    	    arg2 = scann.next();
    	scann.close();
    	
    	try
    	{
    		if(prefix.equals("-h"))
    		{
    		    target.modHealth(-Integer.parseInt(arg1));
    		    out = true;
    		}
    		else if(prefix.equals("-m"))
        	{
        	    target.modMagicka(-Integer.parseInt(arg1));
        	    out = true;
        	}
    		else if(prefix.equals("-e"))
        	{
        	    target.modExperience(-Integer.parseInt(arg1));
        	    out = true;
        	}
    		else if(prefix.equals("-i") || prefix.equals("-item"))
    		{
    			if(arg2 == null)
                    out = target.getInventory().remove(arg1, 1);
    			else
    			{
    			    int amount = Integer.parseInt(arg2);
                    out = target.getInventory().remove(arg1, amount);
    			}
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
    	String value = "";
    	try
    	{
        	value = scann.next();
    	}
    	catch(NoSuchElementException e)
    	{
    		Log.addSystem("empty value:" + prefix);
    	}
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
    	else if(prefix.equals("-e"))
    	{
    		Log.addSystem(target.getEffects().list());
    		out = true;
    	}
    	else
        	Log.addSystem(prefix + " " + TConnector.getText("ui", "logCmdSho"));
        return out;
    }
    /**
     * Handles use command
     * @param commandLine Command
     * @param target Command target
     * @return Command out
     */
    private boolean useCommands(String commandLine, Character target)
    {
       boolean out = false;
       try
       {
           Scanner scann = new Scanner(commandLine);
           String prefix = scann.next();
           String arg1 = scann.next();
           String arg2 = null;
           if(scann.hasNext())
               arg2 = scann.next();
           scann.close();
           
           if(prefix.equals("-s") || prefix.equals("-skill"))
           {
               Targetable skillTarget = null;
               if(arg2 != null)
                   skillTarget = gw.getCurrentChapter().getTObject(arg2);
               
               CharacterOut charOut = CharacterOut.UNKNOWN;
               
               if(skillTarget != null)
                   charOut = target.useSkillOn(skillTarget, target.getSkills().get(arg1));
               else
                   charOut = target.useSkill(target.getSkills().get(arg1));

               if(charOut != CharacterOut.SUCCESS)
               {
                   Log.addSystem(charOut.toString());
                   out = false;
               }
               else
                   out = true;
               
           }
       }
       catch(NoSuchElementException e)
       {
           out = false;
       }
       
       return out;
    }
}
