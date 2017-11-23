/*
 * InfoFrame.java
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

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.gui.InfoField;
import pl.isangeles.senlin.util.Coords;

/**
 * Class for UI information frame 
 * @author Isangeles
 *
 */
class InfoFrame extends InfoField implements UiElement 
{
	private boolean openReq;
	/**
	 * Loading info frame constructor
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public InfoFrame(GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		super(Coords.getSize(200f), Coords.getSize(70f), gc);
	}
	
	@Override
	public void draw(float x, float y)
	{
		super.draw(x, y, false);
	}
	/**
	 * Opens information frame
	 */
	public void open(String information)
	{
		setText(information);
		openReq = true;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.tools.UiElement#close()
	 */
	@Override
	public void close() 
	{
		openReq = false;
		reset();
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.tools.UiElement#update()
	 */
	@Override
	public void update() 
	{
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.tools.UiElement#reset()
	 */
	@Override
	public void reset() 
	{
		moveMOA(Coords.getX("BR", 0), Coords.getY("BR", 0));
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.tools.UiElement#isOpenReq()
	 */
	@Override
	public boolean isOpenReq() 
	{
		return openReq;
	}

}
