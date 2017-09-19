/*
 * Switch.java
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
package pl.isangeles.senlin.gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.core.Attribute;
import pl.isangeles.senlin.util.*;
/**
 * Graphical switch to manipulate integers 
 * @author Isangeles
 *
 */
public final class Switch extends InterfaceObject implements MouseListener
{
	private int value;
	private Attribute points;
	private Font labelFont;
	private TrueTypeFont ttf;
	private String label;
	private Button buttPlus;
	private Button buttMinus;
	private GameContainer gc;
	/**
	 * Switch constructor (without info window)
	 * @param gc Slick game container
	 * @param label Switch label
	 * @param value Switch start value
	 * @param points Max number of increments
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Switch(GameContainer gc, String label, int value, Attribute points) throws SlickException, IOException, FontFormatException 
	{
		super(GConnector.getInput("switch/switchBG.png"), "switch", false, gc);
		build(gc, label, value, points);
	}
	/**
	 * Switch constructor (with info window)
	 * @param gc Slick game container
	 * @param label Switch label
	 * @param value Switch start value
	 * @param points Max number of increments
	 * @param textForInfo Text for info window
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Switch(GameContainer gc, String label, int value, Attribute points, String textForInfo) throws SlickException, IOException, FontFormatException
	{
		super(GConnector.getInput("switch/switchBG.png"), "switch", false, gc, textForInfo);
		build(gc, label, value, points);
	}
	
	@Override
	public void draw(float x, float y, boolean scaledPos)
	{
		super.draw(x, y, scaledPos);
		
		float texEndX = super.getScaledWidth();
        float texEndY = super.getScaledHeight();
        float textX = ttf.getWidth(label);
        float textY = ttf.getHeight(label);
        
		buttPlus.draw((x+super.getScaledWidth())-buttPlus.getScaledWidth(), y+2);
		buttMinus.draw(x, y+2);
		
		super.drawString(value+"", ttf);
		ttf.drawString(super.getCenteredCoord(super.x, texEndX, textX), super.getCenteredCoord(super.y, texEndY, textY-20), label);
		
	}
	/**
	 * Returns current switch value
	 * @return Current switch value 
	 */
	public int getValue()
	{
		return value;
	}
	@Override
	public void inputEnded() 
	{
	}
	@Override
	public void inputStarted() 
	{	
	}
	@Override
	public boolean isAcceptingInput() 
	{
		return true;
	}
	@Override
	public void setInput(Input input) 
	{
	}
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) 
	{	
	}
	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) 
	{
	}
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) 
	{
	}
	@Override
	public void mousePressed(int button, int x, int y) 
	{
	}
	@Override
	public void mouseReleased(int button, int x, int y) 
	{
		if(buttPlus.isMouseOver() && points.getValue() > 0)
		{
			value ++;
			points.decrement();
		}
		else if(buttMinus.isMouseOver() && value > 1)
		{
			value --;
			points.increment();;
		}
	}
	@Override
	public void mouseWheelMoved(int change) 
	{
	}
	/**
	 * Builds switch, called by every constructor
	 * @param gc Slick game container
	 * @param label Switch label
	 * @param value Value for switch 
	 * @param points Max number of increments
	 * @throws SlickException
	 * @throws FontFormatException
	 * @throws IOException
	 */
	private void build(GameContainer gc, String label, int value, Attribute points) throws SlickException, FontFormatException, IOException
	{
		this.gc = gc;
		this.label = label;
		this.value = value;
		this.points = points;
		this.gc.getInput().addMouseListener(this);
		
		buttPlus = new Button(GConnector.getInput("switch/switchButtonPlus.png"), "switchBP", false, "", this.gc);
		buttMinus = new Button(GConnector.getInput("switch/switchButtonMinus.png"), "switchBM", false, "", this.gc);
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		labelFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        
        ttf = new TrueTypeFont(labelFont.deriveFont(10f), true);
	}
}
