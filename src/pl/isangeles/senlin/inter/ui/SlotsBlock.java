package pl.isangeles.senlin.inter.ui;

import java.io.IOException;
import java.util.Collection;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.inter.Slot;
import pl.isangeles.senlin.inter.SlotContent;
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
				if(slots[i][j].isNull())
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
	public boolean insertContent(Collection<SlotContent> contentCollection)
	{
		for(SlotContent content : contentCollection)
		{
			if(!insertContent(content))
				return false;
		}
		return true;
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
}
