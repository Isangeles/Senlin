/*
 * Slot.java
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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.data.GBase;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.GConnector;
/**
 * Abstract class for interface slots
 * @author Isangeles
 *
 */
public abstract class Slot extends InterfaceObject implements MouseListener
{
	protected LinkedList<SlotContent> content = new LinkedList<>();
	private TrueTypeFont ttf;
	
	private Slot(InputStream is, String ref, boolean flipped, GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		super(is, ref, flipped, gc);
		gc.getInput().addMouseListener(this);
		
		Font font = GBase.getFont("mainUiFont");
		ttf = new TrueTypeFont(font.deriveFont(getSize(9f)), true);
	}
	/**
	 * Slot constructor
	 * @param tex Texture for slot
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Slot(Image tex, GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		super(tex, gc);
		gc.getInput().addMouseListener(this);
		
		Font font = GBase.getFont("mainUiFont");
		ttf = new TrueTypeFont(font.deriveFont(getSize(9f)), true);
	}

	/**
	 * Checks if slot is empty
	 * @return True if slot is empty, false otherwise
	 */
	public boolean isEmpty()
	{
		return content.size() == 0;
	}
	/**
	 * Checks if slot is full
	 * @return
	 */
	public boolean isFull()
	{
		if(!isEmpty())
		{
			return (content.size() == content.get(0).getMaxStack());
		}
		else
			return false;
	}
	/**
	 * Inserts content in slot
	 * @param content Content for slot
	 */
	public abstract boolean insertContent(SlotContent content);
	/**
	 * Inserts all content from specified collection to slot
	 * @param content Collection with content
	 * @return True if insertion was successfully, false otherwise
	 */
	public abstract boolean insertContent(Collection<? extends SlotContent> content);
	/**
	 * Removes whole content from slot
	 */
	public void removeContent()
	{
		content.clear();
	}
	/**
	 * Removes specified content from slot
	 * @param contentToRemove Slot content to remove
	 * @return True if specified content was successfully removed, false otherwise
	 */
	public boolean removeContent(SlotContent contentToRemove)
	{
		return content.remove(contentToRemove);
	}
	/**
	 * Removes all specified content
	 * @param contentToRemove Collection with slot content to remove
	 */
	public void removeContent(Collection<SlotContent> contentToRemove)
	{
		content.removeAll(contentToRemove);
	}
	/**
	 * Returns slot content
	 * @return Slot content or null
	 */
	public List<? extends SlotContent> getContent()
	{
		if(isEmpty())
			return null;
		else
			return content;
	}
	/**
	 * Returns whole 'dragged' content from slot
	 * @return Collection with slot content
	 */
	public Collection<SlotContent>getDraggedContent()
	{
		List<SlotContent> draggedContent = new ArrayList<>();
		for(SlotContent con : content)
		{
			if(con.getTile().isDragged())
			{
				draggedContent.add(con);
			}
		}
		return draggedContent;
	}
	
	@Override
	public void draw(float x, float y, boolean scaledPos)
	{
		super.draw(x, y, false);
		if(!isEmpty())
		{
			for(SlotContent con : content)
			{
				con.draw(x - getDis(3), y - getDis(3), scaledPos);
			}
			if(content.size() > 1)
			{
				ttf.drawString(x, y, content.size()+"");
			}
		}
	}
	/**
	 * Clicks or unclicks slot content
	 * @param clicked True to click, false to unclick
	 */
	public void click(boolean clicked)
	{
		for(SlotContent con : content)
		{
			con.getTile().click(clicked);
		}
	}
	/**
	 * Sets whole content in slots as dragged or not
	 * @param dragged True to set whole content in slot as dragged, false to disable dragging in whole slot content 
	 */
	public void dragged(boolean dragged)
	{
		for(SlotContent con : content)
		{
			con.getTile().dragged(dragged);
		}
	}
	/**
	 * Checks if content tile in slot is dragged
	 * @return True if slot is dragged, false otherwise
	 */
	public boolean isContentDragged()
	{
		if(!isEmpty())
			return content.get(0).getTile().isDragged();
		else
			return false;
	}
	
	@Override
	public void mousePressed(int button, int x, int y)
	{
		
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseClicked(int, int, int, int)
	 */
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseDragged(int, int, int, int)
	 */
	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) 
	{
	    for(SlotContent con : content)
        {
            if(con.getTile().isMouseOver() && !con.getTile().isDragged())
            {
                con.getTile().dragged(true);
                break;
            }
        }
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseMoved(int, int, int, int)
	 */
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseReleased(int, int, int)
	 */
	@Override
	public void mouseReleased(int button, int x, int y) 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseWheelMoved(int)
	 */
	@Override
	public void mouseWheelMoved(int change) 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#inputEnded()
	 */
	@Override
	public void inputEnded() 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#inputStarted()
	 */
	@Override
	public void inputStarted()
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#isAcceptingInput()
	 */
	@Override
	public boolean isAcceptingInput() 
	{
		return true;
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#setInput(org.newdawn.slick.Input)
	 */
	@Override
	public void setInput(Input input) 
	{
	}
}
