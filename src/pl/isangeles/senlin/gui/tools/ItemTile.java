/*
 * ItemTile.java
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
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.data.GBase;
import pl.isangeles.senlin.gui.InterfaceTile;
/**
 * Class for graphical representation of items in ui
 * @author Isangeles
 *
 */
public class ItemTile extends InterfaceTile 
{
	/**
	 * Item icon constructor
	 * @param fileInput Input stream to icon texture
	 * @param ref Name for icon texture
	 * @param flipped If icon should be flipped
	 * @param gc Slick game container
	 * @param info String with text for info
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public ItemTile(InputStream fileInput, String ref, boolean flipped, GameContainer gc, String info)
			throws SlickException, IOException, FontFormatException 
	{
		super(fileInput, ref, flipped, gc, info);
	}
	/**
	 * Item icon constructor
	 * @param iconImg Icon image 
	 * @param gc Slick game container
	 * @param info String with text for info
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public ItemTile(Image iconImg, GameContainer gc, String info) throws SlickException, IOException, FontFormatException
	{
		super(iconImg, gc, info);
	}

	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) 
	{
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) 
	{
	}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) 
	{
	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) 
	{
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
