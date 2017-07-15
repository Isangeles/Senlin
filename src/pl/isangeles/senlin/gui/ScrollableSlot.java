/*
 * ScrollableButton.java
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
import java.io.File;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.util.GConnector;

/**
 * @author Isangeles
 *
 */
public class ScrollableSlot extends TextButton
{
	private ScrollableContent content;
    private boolean select;
	/**
	 * Scrollable slot constructor
	 * @param gc Slick game container
	 * @throws SlickException 
	 * @throws FontFormatException
	 * @throws IOException
	 */
	public ScrollableSlot(GameContainer gc) throws SlickException, FontFormatException, IOException
	{
		super(gc);
	}
	@Override
    public void draw(float x, float y, boolean scaledPos)
    {
        super.draw(x, y, scaledPos);
    }
	/**
	 * Inserts content into slot
	 * @param content Scrollable content
	 */
	public void insertContent(ScrollableContent content)
	{
		this.content = content;
		setLabel(content.getName());
	}
	/**
	 * Clears slot(removes content from slot)
	 */
	public void clear()
	{
		content = null;
	}
	/**
	 * Returns content inside slot
	 * @return Scrollable content
	 */
	public ScrollableContent getContent()
	{
		return content;
	}
	/**
	 * Checks if slot is empty
	 * @return True if slot is empty, false otherwise
	 */
	public boolean isEmpty()
	{
		return (content == null);
	}
    /**
     * Deselects slot
     */
    public void unselect()
    {
        select = false;
    }
	
	@Override
	public void mouseReleased(int button, int x, int y)
	{
		if(button == Input.MOUSE_LEFT_BUTTON)
		{
			if(isMouseOver())
				select = true;
		}
	}
}
