/*
 * SlotsBlock.java
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
package pl.isangeles.senlin.gui.elements;

import java.io.IOException;
import java.util.Collection;

import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.gui.Slot;
import pl.isangeles.senlin.gui.SlotContent;
import pl.isangeles.senlin.util.Coords;
/**
 * Class for graphical multidimensional slots tables
 * @author Isangeles
 *
 */
public class SlotsBlock
{
	private Slot[][] slots;
	/**
	 * Slots block constructor
	 * @param slotsTab Initialized multidimensional table with slots
	 * @throws SlickException
	 * @throws IOException
	 */
	public SlotsBlock(Slot[][] slotsTab) throws SlickException, IOException
	{
		slots = slotsTab;
	}
	/**
	 * Draws all skill slots
	 * @param x Position on x-axis
	 * @param y	Position on y-axis
	 */
	public void draw(float x, float y)
	{
		for(int i = 0; i < slots.length; i ++)
		{
			for(int j = 0; j < slots[i].length; j ++)
			{
				slots[i][j].draw(x + (j*Coords.getDis(45)) + Coords.getDis(3), y + (i*Coords.getDis(35)) + Coords.getDis(3), false);
			}
		}
	}
	/**
	 * Moves content from slotA to slotB
	 * @param slotA Current skill slot
	 * @param slotB New slot for skill
	 */
	public void moveContent(Slot slotA, Slot slotB)
	{
		slotA.dragged(false);
		slotB.insertContent(slotA.getContent());
		slotA.removeContent();
	}
	/**
	 * Inserts content to first empty slot
	 * @param content Some slot content
	 * @return True if content was successfully inserted into slot, false otherwise
	 */
	public boolean insertContent(SlotContent content)
	{
		for(int i = 0; i < slots.length; i ++)
		{
			for(int j = 0; j < slots[i].length; j ++)
			{
				if(slots[i][j].isEmpty())
				{
					slots[i][j].insertContent(content);
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Insert whole content collection to block 
	 * @param contentCollection Collection with SlotContent
	 * @return True if all content was inserted successfully, false if at least one insertion fail
	 */
	public boolean insertContent(Collection<? extends SlotContent> contentCollection)
	{
		for(SlotContent content : contentCollection)
		{
			if(!insertContent(content))
				return false;
		}
		return true;
	}
	/**
	 * Inserts content into specified slot
	 * @param content Content to insert
	 * @param row Row of desired slot
	 * @param column Column of desired slot
	 * @return True if content was successfully inserted, false otherwise
	 */
	public boolean insertContentInto(SlotContent content, int row, int column)
	{
		if(slots[row][column].isEmpty())
			return slots[row][column].insertContent(content);
		else
			return false;
	}
	/**
	 * Removes content from all slots
	 */
	public void clear()
	{
		for(Slot[] slotsLine : slots)
		{
			for(Slot slot : slotsLine)
			{
				slot.removeContent();
			}
		}
	}
	/**
	 * Returns dragged slot
	 * @return Current dragged slot, if no slot from table dragged returns null
	 */
	public Slot getDragged()
	{
		for(Slot slotsLine[] : slots)
		{
			for(Slot ss : slotsLine)
			{
				if(ss.isContentDragged())
					return ss;
			}
		}
		return null;
	}
	/**
	 * Returns slot on which mouse cursor is pointed
	 * @return Slot pointed by mouse or null if such slot was not found
	 */
	public Slot getMouseOver()
	{
	    for(Slot slotsLine[] : slots)
        {
            for(Slot slot : slotsLine)
            {
                if(slot.isMouseOver())
                    return slot;
            }
        }
	    
	    return null;
	}
	/**
	 * Return table with slots
	 * @return Multidimensional table of this block
	 */
	public Slot[][] getSlots()
	{
		return slots;
	}
	/**
	 * Returns specified slot position in block
	 * @param slotToCheck Slot from this block to check
	 * @return Table with block row[0] and column[1] numbers
	 */
	public int[] getSlotPos(Slot slotToCheck)
	{
		int row = 0;
		int column = 0;
		
		for(Slot[] line : slots)
		{
			for(Slot slot : line)
			{
				if(slot == slotToCheck)
					return new int[] {row, column};
				column ++;
			}
			row ++;
			column = 0;
		}
		return new int[] {0,0};
	}
	/**
	 * Returns block width
	 * @return Block width
	 */
	public float getWidth()
	{
		return slots[0][0].getScaledWidth() * slots[0].length;
	}
	/**
	 * Returns block height
	 * @return Block height
	 */
	public float getHeight()
	{
		return slots[0][0].getScaledHeight() * slots.length;
	}
}
