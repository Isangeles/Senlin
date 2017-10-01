/*
 * InterfaceTile.java
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

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.util.Coords;
/**
 * Base class for graphical representations of skills, items, etc. in UI   
 * @author Isangeles
 *
 */
public abstract class InterfaceTile extends InterfaceObject implements MouseListener
{ 
	private static int counter; //TODO check if necessary
	private Input gameInput;
	private boolean dragged;
	private Color clickColor;
	protected boolean clicked;
	/**
	 * InterfaceTile constructor
	 * @param fileInput Input stream to image
	 * @param ref Name for image
	 * @param flipped If image should be filpped
	 * @param gc Slick game container
	 * @param info Info from content
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public InterfaceTile(InputStream fileInput, String ref, boolean flipped, GameContainer gc, String info)
			throws SlickException, IOException, FontFormatException 
	{
		super(fileInput, ref+counter, flipped, gc, info, (int)Coords.getSize(45), (int)Coords.getSize(40));
		gameInput = gc.getInput();
		gameInput.addMouseListener(this);
        clickColor = new Color(73, 73, 73);
		counter ++;
	}
	@Override
	public void draw(float x, float y, boolean scaledPos)
	{
	    if(clicked)
	    {
	        draw(x, y, getSize(45f), getSize(40f), clickColor, scaledPos);
	        return;
	    }
		if(!dragged)
			super.draw(x, y, getSize(45f), getSize(40f), scaledPos);
		else
			super.draw(gameInput.getMouseX(), gameInput.getMouseY(), getSize(45f), getSize(40f), scaledPos);
		
	}
	@Override
	public void draw(float x, float y, Color filter, boolean scaledPos)
	{
		if(!dragged)
			super.draw(x, y, getSize(45f), getSize(40f), filter, scaledPos);
		else
			super.draw(gameInput.getMouseX(), gameInput.getMouseY(), getSize(45f), getSize(40f), filter, scaledPos);
	}
	
	public void dragged(boolean dragged)
	{
		this.dragged = dragged;
	}
	
	public void click(boolean click)
	{
		clicked = click;
	}
	
	@Override
	public float getScaledWidth()
	{
		return getSize(45f);
	}
	
	@Override
	public float getScaledHeight()
	{
		return getSize(40f);
	}
	/**
	 * Checks if icon is dragged 
	 * @return True if icon is dragged, false otherwise
	 */
	public boolean isDragged()
	{
		return dragged;
	}
	
	@Override
	public void mousePressed(int button, int x, int y)
	{
		//if(button == Input.MOUSE_LEFT_BUTTON && super.isMouseOver() && !dragged)
			//dragged = true;
	}
	
}
