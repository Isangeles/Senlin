/*
 * ScrollableField.java
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;

/**
 * Class for scrollable fields
 * @author Isangeles
 *
 */
public class ScrollableList extends LinkedList<ScrollableContent> implements MouseListener 
{
	private static final long serialVersionUID = 1L;
	private List<ScrollableSlot> slots;
	private Image selectTex;
	private Button upB;
	private Button downB;
	private ScrollableSlot selectedS;
	private float fieldWidth;
	private float fieldHeight;
	private int startIndex;
	private boolean focus;
	
	public ScrollableList(int slotsAmount, GameContainer gc) throws SlickException, FontFormatException, IOException
	{
		super();
		gc.getInput().addMouseListener(this);
		slots = new ArrayList<>();
		for(int i = 0; i < slotsAmount; i ++)
		{
			slots.add(new ScrollableSlot(gc));
		}
		selectTex = new Image(GConnector.getInput("field/select.png"), "uiSelectTex", false);
		upB = new Button(GConnector.getInput("button/buttonUp.png"), "uiButtonUp", false, "", gc);
		downB = new Button(GConnector.getInput("button/buttonDown.png"), "uiButtonDown", false, "", gc);
		
		fieldWidth = slots.get(0).getScaledWidth();
		fieldHeight = (slots.get(0).getScaledHeight() + Coords.getDis(5)) * slots.size();
	}
	
	public void draw(float x, float y, boolean scaledPos)
	{
		if(selectedS != null)
			selectTex.draw(selectedS.x, selectedS.y, selectedS.getScale());
		
		for(int i = 0; i < slots.size(); i ++)
		{
			TextButton button = slots.get(i);
			button.draw(x, y + ((button.getScaledHeight() + Coords.getDis(5)) * i), scaledPos);
		}
		
		upB.draw(x + (fieldWidth), y, scaledPos);
		downB.draw(x + (fieldWidth), y + (fieldHeight), scaledPos);
	}
	
	public void update()
	{
		for(int i = startIndex, j = 0; i < this.size() && j < slots.size(); i ++, j ++)
		{
			if(slots.get(j).isEmpty())
			{
				slots.get(j).insertContent(this.get(i));
				break;
			}
		}
	}
	
	public void setFocus(boolean focus)
	{
		this.focus = focus;
	}
	
	public void clear()
	{
		selectedS = null;
		for(ScrollableSlot slot : slots)
		{
			slot.clear();
		}
		super.clear();
	}
	
	public ScrollableContent getSelected()
	{
		if(selectedS != null)
			return selectedS.getContent();
		else
			return null;
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
		return focus;
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

			Log.addSystem("checking");
			if(upB.isMouseOver())
			{
				if(startIndex > 0)
				{
					startIndex --;
					update();
				}
			}
			if(downB.isMouseOver())
			{
				if(startIndex < this.size()-1)
				{
					startIndex ++;
					update();
				}
			}
			
			for(ScrollableSlot slot : slots)
			{
				if(slot.isMouseOver())
				{
					selectedS = slot;
					break;
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
