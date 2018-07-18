/*
 * Switch.java
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
import pl.isangeles.senlin.data.GBase;
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
	private Button plusB;
	private Button minusB;
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
		this.gc = gc;
		this.label = label;
		this.value = value;
		this.points = points;
		this.gc.getInput().addMouseListener(this);
		
		plusB = new Button(GBase.getImage("buttonNext"), "", this.gc);
		minusB = new Button(GBase.getImage("buttonBack"), "", this.gc);
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		labelFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        
        ttf = new TrueTypeFont(labelFont.deriveFont(10f), true);
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
		this.gc = gc;
		this.label = label;
		this.value = value;
		this.points = points;
		this.gc.getInput().addMouseListener(this);
		
		plusB = new Button(GBase.getImage("buttonNext"), "", this.gc);
		minusB = new Button(GBase.getImage("buttonBack"), "", this.gc);
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		labelFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        
        ttf = new TrueTypeFont(labelFont.deriveFont(10f), true);
	}
	
	@Override
	public void draw(float x, float y, boolean scaledPos)
	{
		super.draw(x, y, scaledPos);
		/* 
		float texEndX = super.getScaledWidth();
        float texEndY = super.getScaledHeight();
        float textX = ttf.getWidth(label);
        float textY = ttf.getHeight(label);
        */
		plusB.draw((x+super.getWidth())-plusB.getWidth(), y+getDis(2), scaledPos);
		minusB.draw(x, y+getDis(2), scaledPos);
		
		super.drawString(value+"", ttf, scaledPos);
		ttf.drawString(getCenter(scaledPos).x, getCenter(scaledPos).y-getDis(20), label);
		
	}
	/**
	 * Returns current switch value
	 * @return Current switch value 
	 */
	public int getValue()
	{
		return value;
	}
	/**
	 * Set specified value as current switch value
	 * @param value Value
	 * @return True if value was successfully set, false if specified value was incorrect
	 */
	public boolean setValue(int value)
	{
		if(value > 0 && value <= points.getValue())
		{
			this.value = value;
			return true;
		}
		else
			return false;
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
		if(plusB.isMouseOver() && points.getValue() > 0)
		{
			value ++;
			points.decrement();
		}
		else if(minusB.isMouseOver() && value > 1)
		{
			value --;
			points.increment();;
		}
	}
	@Override
	public void mouseWheelMoved(int change) 
	{
	}
}
