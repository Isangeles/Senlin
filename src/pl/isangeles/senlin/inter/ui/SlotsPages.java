/*
 * SlotsPages.java
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
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.inter.Button;
import pl.isangeles.senlin.inter.Slot;
import pl.isangeles.senlin.inter.SlotContent;
import pl.isangeles.senlin.util.GConnector;

/**
 * @author Isangeles
 *
 */
public class SlotsPages implements MouseListener
{
	private List<SlotContent> content = new ArrayList<>();
	private SlotsBlock slots;
	private int startIndex;
	private Button next;
	private Button prev;
	
	public SlotsPages(Slot[][] pageSlots, GameContainer gc) throws SlickException, FontFormatException, IOException
	{
		gc.getInput().addMouseListener(this);
		
		slots = new SlotsBlock(pageSlots);
		next = new Button(GConnector.getInput("button/buttonNext.png"), "buttonNext", false, "", gc);
		prev = new Button(GConnector.getInput("button/buttonBack.png"), "buttonBack", false, "", gc);
	}
	
	public void draw(float x, float y, boolean scaledPos)
	{
		slots.draw(x, y);
		next.draw(x + slots.getWidth(), y, scaledPos);
		prev.draw(x - prev.getScaledWidth(), y, scaledPos);
	}
	
	public void update()
	{
		slots.clear();
		List<SlotContent> contentToDisplay = content.subList(startIndex, content.size());
		slots.insertContent(contentToDisplay);
	}
	
	public void insertContent(List<SlotContent> content)
	{
		this.content = content;
	}
	
	public void clear()
	{
		slots.clear();
	}
	
	public SlotsBlock getSlots()
	{
		return slots;
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#inputEnded()
	 */
	@Override
	public void inputEnded() 
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#inputStarted()
	 */
	@Override
	public void inputStarted() 
	{
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseClicked(int, int, int, int)
	 */
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) 
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseDragged(int, int, int, int)
	 */
	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) 
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseMoved(int, int, int, int)
	 */
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) 
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mousePressed(int, int, int)
	 */
	@Override
	public void mousePressed(int button, int x, int y) 
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseReleased(int, int, int)
	 */
	@Override
	public void mouseReleased(int button, int x, int y) 
	{
		if(button == Input.MOUSE_LEFT_BUTTON)
		{
			if(next.isMouseOver())
			{
				if(startIndex > 0)
				{
					startIndex --;
				}
			}
			if(prev.isMouseOver())
			{
				if(startIndex < content.size()-1)
				{
					startIndex ++;
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseWheelMoved(int)
	 */
	@Override
	public void mouseWheelMoved(int change) 
	{
		// TODO Auto-generated method stub
		
	}
}
