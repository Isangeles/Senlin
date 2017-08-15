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
package pl.isangeles.senlin.gui.tools;

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

import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.cli.CommandInterface;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.character.Guild;
import pl.isangeles.senlin.data.QuestsBase;
import pl.isangeles.senlin.data.SkillsBase;
import pl.isangeles.senlin.gui.TextBlock;
import pl.isangeles.senlin.gui.TextBox;
import pl.isangeles.senlin.gui.TextInput;
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
    private CommandInterface cli;
    /**
     * Console constructor
     * @param gc Game container for superclass
     * @param player Player character to manipulate by commands
     * @throws SlickException
     * @throws FontFormatException
     * @throws IOException
     */
    public Console(GameContainer gc, CommandInterface cli, Character player) throws SlickException, FontFormatException, IOException
    {
        super(GConnector.getInput("ui/background/consoleBG_DG.png"), "uiConsoleBg", false, gc);
        super.textField = new TextField(gc, textTtf, (int)Coords.getX("BR", 0), (int)Coords.getY("BR", 0), super.getWidth(), super.getHeight()-170, this);
        this.player = player;
        this.cli = cli;
        logBox = new TextBox(gc);
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
            	cli.executeCommand(super.getText());
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
	@Override
	public void draw(float x, float y)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void reset() 
	{
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.elements.UiElement#close()
	 */
	@Override
	public void close() 
	{
		// TODO Auto-generated method stub
		
	}
    
}
