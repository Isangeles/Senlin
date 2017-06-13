/*
 * Console.java
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
package pl.isangeles.senlin.inter.ui;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;

import pl.isangeles.senlin.inter.TextBlock;
import pl.isangeles.senlin.inter.TextBox;
import pl.isangeles.senlin.inter.TextInput;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Guild;
import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.data.QuestsBase;
import pl.isangeles.senlin.data.SkillsBase;
import pl.isangeles.senlin.data.GuildsBase;
import pl.isangeles.senlin.data.ItemsBase;
/**
 * Class for game console
 * command syntax: $[target] [command] [-prefix] [value]
 * TODO Seems to command check not work entirely properly
 * @author Isangeles
 *
 */
final class Console extends TextInput implements UiElement 
{
    private boolean hide;
    private Character player;
    private TextBox logBox;
    /**
     * Console constructor
     * @param gc Game container for superclass
     * @param player Player character to manipulate by commands
     * @throws SlickException
     * @throws FontFormatException
     * @throws IOException
     */
    public Console(GameContainer gc, Character player) throws SlickException, FontFormatException, IOException
    {
        super(GConnector.getInput("ui/background/consoleBG_DG.png"), "uiConsoleBg", false, gc);
        super.textField = new TextField(gc, textTtf, (int)Coords.getX("BR", 0), (int)Coords.getY("BR", 0), super.getWidth(), super.getHeight()-170, this);
        logBox = new TextBox(gc);
        this.player = player;
        hide = true;
    }
    /**
     * Draws console on unscaled position
     * @param x Position on x-axis
     * @param y Position on y-axis
     * @param g Slick graphic to render text field
     */
    public void draw(float x, float y, Graphics g)
    {
        super.draw(x, y, false);
        /*
        for(int i = 1; i < 10; i ++)
        {
        	super.textTtf.drawString(super.x, (super.y + super.getScaledHeight() - 7) - textField.getHeight()*i, Log.get(Log.size()-i));
        }
        */
        logBox.draw(x, y, 630f, 180f, false);
        
        if(!hide)
        {   
            textField.setLocation((int)super.x, (int)(super.y + super.getScaledHeight()));
            super.render(g);
        }
        
    }
    
	@Override
	public void update() 
	{
		for(int i = 0; i < Log.size(); i ++)
		{
			if(!logBox.contains(Log.get(i)))
				logBox.add(new TextBlock(Log.get(i), 80, textTtf));
		}
	}
            
    @Override
    public void keyPressed(int key, char c)
    {
        if(key == Input.KEY_GRAVE && !hide)
        {
            super.textField.deactivate();
            hide = true;
            super.textField.setFocus(false);
        }
        else if(key == Input.KEY_GRAVE && hide)
        {
            hide = false;
            super.textField.setFocus(true);
        }
        
        if(key == Input.KEY_ENTER && !hide)
        {
            if(super.getText() != null)
            	checkCommand(super.getText());
            super.clear();
        }
    }
    
    @Override
    public void mouseReleased(int button, int x, int y)
    {
    }
    /**
     * Checks if console is hidden
     * @return True if is hidden or false otherwise
     */
    public boolean isHidden()
    {
        return hide;
    }
    /**
     * Checks entered command target, first command check  
     * @param command Text line with command to check 
     */
    private void checkCommand(String line)
    {
    	//If not a game command
    	if(!line.startsWith("$"))
    	{
    		player.getAvatar().speak(line);
    		return;
    	}
    	
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
        	Log.addDebug("Command scann error: " + line);
        }
        scann.close();
        
        if(commandTarget.equals("$debug"))
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
        
        if(commandTarget.equals("$player"))
        {
        	Log.addDebug("In player check");
        	playerCommands(command);
        	return;
        }
        
        if(commandTarget.equals("$system"))
        {
        	systemCommands(command);
        	return;
        }
        
        Log.addWarning(commandTarget + " " + TConnector.getText("ui", "logCmdFail"));
       
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
    			Guild guild = GuildsBase.getGuild(Integer.parseInt(value));
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
    	    if(prefix.equals("-g") || prefix.equals("-gold"))
    	        target.addGold(Integer.parseInt(value));
    		if(prefix.equals("-h"))
    		    target.addHealth(Integer.parseInt(value));
        	if(prefix.equals("-m"))
        	    target.addMagicka(Integer.parseInt(value));
        	if(prefix.equals("-e"))
        	    target.addExperience(Integer.parseInt(value));
        	if(prefix.equals("-s") || prefix.equals("-skills"))
        	    target.addSkill(SkillsBase.getAttack(target, value));
        	if(prefix.equals("-q") || prefix.equals("-quest"))
        		target.startQuest(QuestsBase.get(value));
        	
        	return;
    	}
    	catch(NumberFormatException e)
    	{
    		Log.addWarning(TConnector.getText("ui", "logBadVal"));
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
    	scann.close();
    	
    	if(prefix.equals("-f"))
    	{
    		Log.addSystem(target.getName() + "//flags: " + target.getFlags().list());
    		return;
    	}
    	
        Log.addSystem(prefix + " " + TConnector.getText("ui", "logCmdSho"));
    }
	@Override
	public void draw(float x, float y)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void reset() 
	{
	}
    
}
