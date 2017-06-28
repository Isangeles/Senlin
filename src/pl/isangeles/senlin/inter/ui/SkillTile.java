/*
 * SkillTile.java
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.inter.InterfaceTile;
/**
 * Class for graphical representation of skills in UI
 * @author Isangeles
 *
 */
public class SkillTile extends InterfaceTile 
{
	private Color clickColor;
	private boolean active;
	/**
	 * Skill tile constructor
	 * @param fileInput Input stream to img file
	 * @param ref Name for image in system
	 * @param flipped If file suppose to be flipped
	 * @param gc Slick game container
	 * @param textForInfo
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public SkillTile(InputStream fileInput, String ref, boolean flipped, GameContainer gc, String textForInfo)
			throws SlickException, IOException, FontFormatException 
	{
		super(fileInput, ref, flipped, gc, textForInfo);
		clickColor = new Color(73, 73, 73);
		active = true;
	}
	
	@Override
	public void draw(float x, float y, boolean scaledPos)
	{
		if(!clicked && active)
			super.draw(x, y, scaledPos);
		else
			super.draw(x, y, clickColor, scaledPos);
	}
	/**
	 * Sets tile active or inactive
	 * @param active True to activate tile, false otherwise
	 */
	public void setActive(boolean active)
	{
		this.active = active;
	}
	/**
	 * Checks if tile is active
	 * @return True if tile is active, false otherwise
	 */
	public boolean isActive()
	{
		return active;
	}
	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) 
	{
	}
	
	@Override
	public void mousePressed(int button, int x, int y)
	{
		if(isMouseOver() && active)
			super.clicked = true;
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) 
	{
		if(super.isMouseOver())
			super.dragged(true);
	}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) 
	{
	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) 
	{
		if(active)
			super.clicked = false;
	}

	@Override
	public void mouseWheelMoved(int arg0) 
	{
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
	public void setInput(Input arg0) 
	{
	}

}
