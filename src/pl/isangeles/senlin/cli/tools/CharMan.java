/*
 * CharMod.java
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
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.TargetableObject;
import pl.isangeles.senlin.core.Usable;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.character.Guild;
import pl.isangeles.senlin.core.craft.Profession;
import pl.isangeles.senlin.core.craft.ProfessionType;
import pl.isangeles.senlin.core.craft.Recipe;
import pl.isangeles.senlin.core.item.Item;
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
	private static final String TOOL_NAME = "charman";
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
	 * @see pl.isangeles.senlin.cli.CliTool#handleCommand(java.lang.String)
	 */
	@Override
	public String[] handleCommand(String line) 
	{
		String[] output = {"0", ""};
		Scanner scann = new Scanner(line);
        String commandTarget = "";
        String command = "";
        try
        {
            commandTarget = scann.next();
            command = scann.nextLine();
            
            if(commandTarget.equals("player"))
            	output = characterCommands(command, player);
            else
            {
            	Character npc = gw.getCurrentChapter().getCharacter(commandTarget);
            	if(npc != null)
            		output = characterCommands(command, npc);
            	else
            	{
            		Log.addSystem("no such target for charman: " + commandTarget);
            		output[0] = "5";
            	}
            }
        }
        catch(NoSuchElementException e)
        {
        	Log.addSystem("Command scann error: " + line);
    		output[0] = "5";
        }
        finally
        {
            scann.close();
        }
        
		return output;
	}
    /**
     * Handles character commands
     * TODO exceptions catching
     * @param commandLine Rest of command line (after target) 
     * @param target Command target
     * @return Command result[0] and output[1]
     */
    private String[] characterCommands(String commandLine, Character target)
    {
        Scanner scann = new Scanner(commandLine);
        String command = scann.next();
        String prefix = scann.nextLine();
        scann.close();
        
        if(command.equals("add"))
        {
        	return addCommands(prefix, target);
        }
        else if(command.equals("remove"))
        {
        	return removeCommands(prefix, target);
        }
        else if(command.equals("set"))
        {
        	return setCommands(prefix, target);
        }
        else if(command.equals("show"))
        {
        	return showCommands(prefix, target);
        }
        else if(command.equals("use"))
        {
            return useCommands(prefix, target);
        }
        else if(command.equals("is"))
        {
        	return isCommands(prefix, target);
        }
        else
        {
        	Log.addSystem(command + " " + TConnector.getText("ui", "logCmdPla"));
        	return new String[] {"4", ""};
        }
    }
    /**
     * Handles set commands
     * @param commandLine Rest of command line (after command)
     * @param target Target of command
     * @return Command result[0] and output[1]
     */
    private String[] setCommands(String commandLine, Character target)
    {
    	String result = "0";
    	String out = "";
    	Scanner scann = new Scanner(commandLine);
    	try
        {
    	    String prefix = scann.next();
    	    String arg1 = scann.next();
    	
    		if(prefix.equals("-guild"))
        	{
    			Guild guild = GuildsBase.getGuild(arg1);
        		target.setGuild(guild);
        	}
        	else if(prefix.equals("-attitude"))
        	{
        		//TODO set attitude command
        	}
        	else if(prefix.equals("-position"))
        	{
        		String[] pos = arg1.split("x");
        		int x = Integer.parseInt(pos[0]);
        		int y = Integer.parseInt(pos[1]);
        		if(!target.setPosition(new TilePosition(x, y)))
        			result = "2";
        	}
        	else if(prefix.equals("-destination"))
        	{
        		String[] pos = arg1.split("x");
        		int x = Integer.parseInt(pos[0]);
        		int y = Integer.parseInt(pos[1]);
        		target.moveTo(x, y);
        	}
            else if(prefix.equals("-destTile"))
            {
                String[] pos = arg1.split("x");
                int row = Integer.parseInt(pos[0]);
                int column = Integer.parseInt(pos[1]);
                Position p = new TilePosition(row, column).asPosition();
                target.moveTo(p.x, p.y);
            }
        	else
        	{
            	Log.addSystem(prefix + " " + TConnector.getText("ui", "logCmdSet"));
            	result = "3";
        	}	
    	}
        catch(NoSuchElementException e)
        {
            Log.addSystem("Not enought arguments");
        	result = "1";
        }
        catch(NumberFormatException e)
        {
            Log.addSystem(TConnector.getText("ui", "logBadVal") + ":'" + commandLine + "'");
        	result = "1";
        }
        finally
        {
            scann.close();
        }
    	return new String[] {result, out};
    }
    /**
     * Handles add commands
     * @param commandLine Rest of command line (after command)
     * @param target Target of command
     * @return Command result[0] and output[1]
     */
    private String[] addCommands(String commandLine, Character target)
    {
    	String result = "0";
		String out = "";
		
    	Scanner scann = new Scanner(commandLine);
    	try
        {
    	    String prefix = scann.next();
    	    String arg1 = scann.next();
    	    String arg2 = null;
    	    if(scann.hasNext())
    	        arg2 = scann.next();
    	
    		if(prefix.equals("-i") || prefix.equals("-item"))
        	{
        		if(!target.addItem(ItemsBase.getItem(arg1)))
                    result = "2";
        	}
    		else if(prefix.equals("-g") || prefix.equals("-gold"))
    		{
    			int amount = Integer.parseInt(arg1);
    			//TODO looks shady
    			Item[] gold = new Item[amount];
    			for(Item coin : gold)
    			{
    				coin = ItemsBase.getItem("gold01");
    			}
    			if(!target.getInventory().addAll(gold))
    				result = "2";
    		}
    		else if(prefix.equals("-h"))
    		{
    		    target.modHealth(Integer.parseInt(arg1));
    		}
    		else if(prefix.equals("-m"))
    		{
        	    target.modMagicka(Integer.parseInt(arg1));
    		}
    		else if(prefix.equals("-e"))
    		{
        	    target.modExperience(Integer.parseInt(arg1));
    		}
    		else if(prefix.equals("-s") || prefix.equals("-skills")) 
    		{
        		if(!target.addSkill(SkillsBase.getSkill(target, arg1)))
        			result = "2";
    		}
    		else if(prefix.equals("-sp") ||  prefix.equals("-speech"))
    		{
    			String speech = TConnector.getTextFromChapter("speeches", arg1);
    			target.speak(speech);
    		}
    		else if(prefix.equals("-p") || prefix.equals("-profession")) 
    		{
        		if(!target.addProfession(new Profession(ProfessionType.fromString(arg1))))
        			result = "2";
    		}
    		else if(prefix.equals("-r") || prefix.equals("-recipe"))
        	{
        		Recipe recipe = RecipesBase.get(arg1);
        		if(recipe != null && target.getProfession(recipe.getType()) != null) 
        		{
        			if(!target.getProfession(recipe.getType()).add(recipe))
        				result = "2";
        		}
        	}
    		else if(prefix.equals("-f") || prefix.equals("-flag"))
    		{
    			target.getFlags().add(arg1);
    		}
    		else if(prefix.equals("-q") || prefix.equals("-quest"))
    		{
        		target.startQuest(QuestsBase.get(arg1));
    		}
    		else if(prefix.equals("-l") || prefix.equals("-level"))
    		{
    			target.levelUp();
    		}
    		else if(prefix.equals("-at") || prefix.equals("-attackTarget"))
    		{
    			Targetable attackTarget = gw.getCurrentChapter().getTObject(arg1);
    			if(attackTarget != null)
    			{
    				target.getSignals().startCombat(attackTarget);
    			}
    			else
    				Log.addSystem("no such object: " + arg1);
    		}
    		else
    		{
            	Log.addSystem(prefix + " " + TConnector.getText("ui", "logCmdAdd"));
            	result = "3";
    		}
    	}
    	catch(NumberFormatException e)
    	{
    		Log.addSystem(TConnector.getText("ui", "logBadVal") + ":'" + commandLine + "'");
    		result = "1";
    	}
    	catch(NoSuchElementException e)
    	{
    		Log.addSystem("not enough arguments" + ":'" + commandLine + "'");
    		result = "1";
    	}
    	catch(SlickException | IOException | FontFormatException e)
    	{
    		Log.addSystem("some fatal error" + ":'" + commandLine + "'");
    		result = "1";
    	}
        finally
        {
            scann.close();
        }

        return new String[] {result, out};
    }
    /**
     * Handles remove commands
     * @param commLine Rest of command line (after command)
     * @param target Target of command
     * @return Command result[0] and output[1]
     */
    private String[] removeCommands(String commandLine, Character target)
    {
        String result = "0";
    	String out = "";
    	Scanner scann = new Scanner(commandLine);
    	try
        {
    	    String prefix = scann.next();
    	    String arg1 = scann.next();
    	    String arg2 = null;
    	    if(scann.hasNext())
    	        arg2 = scann.next();
    	
    		if(prefix.equals("-h"))
    		{
    		    target.modHealth(-Integer.parseInt(arg1));
    		}
    		else if(prefix.equals("-m"))
        	{
        	    target.modMagicka(-Integer.parseInt(arg1));
        	}
    		else if(prefix.equals("-e"))
        	{
        	    target.modExperience(-Integer.parseInt(arg1));
        	}
    		else if(prefix.equals("-i") || prefix.equals("-item"))
    		{
    			if(arg2 == null) 
    			{
    				if(!target.getInventory().remove(arg1, 1))
    					result = "2";
    			}
    			else
    			{
    			    int amount = Integer.parseInt(arg2);
                    if(!target.getInventory().remove(arg1, amount))
                    	result = "2";
    			}
    		}
    		else
    		{
    			Log.addSystem(prefix + " " + TConnector.getText("ui", "logCmdRem") + ":'" + commandLine + "'");
    			result = "3";
			}
    	}
    	catch(NoSuchElementException e)
    	{
    	    Log.addSystem("Not enought arguments" + "-/" + commandLine + "/");
    	    result = "1";
    	}
    	catch(NumberFormatException e)
    	{
    		Log.addSystem(TConnector.getText("ui", "logBadVal") + ":'" + commandLine + "'");
    	    result = "1";
    	}
        finally
        {
            scann.close();
        }
    	return new String[] {result, out};
    }
    /**
     * Handles show commands
     * @param commLine Rest of command line (after command)
     * @param target Target of command
     * @return Command result[0] and output[1]
     */
    private String[] showCommands(String commandLine, Character target)
    {
        String result = "0";
    	String out = "";
    	Scanner scann = new Scanner(commandLine);
    	try
    	{
        	String prefix = scann.next();
        	String arg1 = null;
        	if(scann.hasNext())
        		arg1 = scann.next();
			
			switch(prefix)
			{
			case "-f":
				out = target.getName() + "-flags: " + target.getFlags().list();
				break;
			case "-r":
				out = target.getProfession(ProfessionType.fromString(arg1)).toString();
				break;
			case "-e":
				out = target.getEffects().list();
				break;
			case "-d": case "--dis":
			    if(arg1 != null)
			    {
			    	Targetable object = null;
				    
				    if(arg1.equals("player"))
				        object = player;
				    else
				        object = gw.getCurrentChapter().getTObject(arg1);
				    
				    if(object != null)
				        out = "range from " + object.getId() +":" + target.getRangeFrom(object);
			    }
			    else
			    	throw new NoSuchElementException();
				break;
			default:
		    	Log.addSystem(prefix + " " + TConnector.getText("ui", "logCmdSho") + ":'" + commandLine + "'");
		    	result = "3";
				break;
			}
    	}
    	catch(NoSuchElementException e)
    	{
    		Log.addSystem("empty value:" + "'" + commandLine + "'");
    		result = "1";
    	}
    	finally
    	{
			scann.close();
    	}
        return new String[] {result, out};
    }
    /**
     * Handles use command
     * @param commandLine Command
     * @param target Command target
     * @return Command result[0] and output[1]
     */
    private String[] useCommands(String commandLine, Character target)
    {
       String result = "0";	
       String out = "";
       Scanner scann = new Scanner(commandLine);
       try
       {
           String prefix = scann.next();
           String arg1 = scann.next();
           String arg2 = null;
           if(scann.hasNext())
               arg2 = scann.next();
           
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
                   Log.addSystem(charOut.toString()); //display error message
           }
           else if(prefix.equals("-i") || prefix.equals("-item"))
           {
               Item item = target.getInventory().getItem(arg1);
               if(item != null && Usable.class.isInstance(item))
               {
                   Usable itemToUse = item;
                   if(arg2 == null) 
                   {
                	   itemToUse.use(target, target);
                       out = "0";
                   }
                   else
                   {
                       Targetable useTarget = gw.getCurrentChapter().getTObject(arg2);
                       if(useTarget != null)
                       {
                    	   itemToUse.use(target, useTarget);
                       }
                   }
               }
           }
           else
           {
        	   result = "3";
           }
       }
       catch(NoSuchElementException e)
       {
           Log.addSystem("not enough arguments" + ":'" + commandLine + "'");
           result = "1";
       }
       finally
       {
           scann.close();
       }
       
       return new String[] {result, out};
    }
    /**
     * Handles is commands
     * @param commandLine Command
     * @param target Command target
     * @return Command output
     */
    private String[] isCommands(String commandLine, Character target)
    {
    	String result = "0";
    	String out = "";
        Scanner scann = new Scanner(commandLine);
        try
        {
            String prefix = scann.next();
            String arg1 = scann.next();
            String arg2 = null;
            if(scann.hasNext())
                arg2 = scann.next();
            
            if(prefix.equals("-dis<"))
            {
            	int dis = Integer.parseInt(arg1);
            	Targetable object = gw.getCurrentChapter().getTObject(arg2);
            	if(object != null)
            	{
            		if(target.getRangeFrom(object) < dis)
                		out = "true";
                	else
                		out = "false";
            	}
            	else
            		result = "2";
            }
            else if(prefix.equals("-dis>"))
            {
            	int dis = Integer.parseInt(arg1);
            	Targetable object = gw.getCurrentChapter().getTObject(arg2);
            	if(object != null)
            	{
            		if(target.getRangeFrom(object) > dis)
                		out = "true";
                	else
                		out = "false";
            	}
            	else
            		result = "2";
            }
            else if(prefix.equals("-flag"))
            {
            	if(target.getFlags().contains(arg1))
            		out = "true";
            	else
            		out = "false";
            }
            else if(prefix.equals("-flag!"))
            {
            	if(!target.getFlags().contains(arg1))
            		out = "true";
            	else
            		out = "false";
            }
            else
            {
                Log.addSystem("no such option for is: " + prefix);
            	result = "3";
            }
            
        }
        catch(NoSuchElementException e)
        {
            Log.addSystem("not enough arguments" + ":'" + commandLine + "'");
            result = "1";
        }
        catch(NumberFormatException e)
        {
        	Log.addSystem("bad argument value" + ":'" + commandLine + "'");
            result = "1";
        }
        finally
        {
            scann.close();
        }
        
        return new String[] {result, out};
    }
}
