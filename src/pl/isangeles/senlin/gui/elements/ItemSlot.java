package pl.isangeles.senlin.gui.elements;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.gui.InterfaceObject;
import pl.isangeles.senlin.gui.InterfaceTile;
import pl.isangeles.senlin.gui.Slot;
import pl.isangeles.senlin.gui.SlotContent;
import pl.isangeles.senlin.util.GConnector;
/**
 * Class for ui item slots
 * @author Isangeles
 *
 */
class ItemSlot extends Slot 
{
	private Item itemInSlot;
	
	public ItemSlot(GameContainer gc) throws SlickException, IOException, FontFormatException 
	{
		super(GConnector.getInput("ui/slot.png"), "uiISlot", false, gc);
	}
	
	public void draw(float x, float y, boolean scaledPos)
	{
		if(itemInSlot != null)
		    itemInSlot.draw(x-3, y-3, scaledPos);
		
		super.draw(x, y, false);
	}
	/**
	 * Inserts item in slot
	 * @param item Item tile
	 */
	public boolean insertContent(SlotContent item)
	{
		if(!isFull())
		{
			try
			{
				itemInSlot = (Item)item;
				return super.content.add(item);
			}
			catch(ClassCastException e)
			{
				return false;
			}
		}
		else
			return false;
	}
	
	public void dragged(boolean dragged)
	{
		itemInSlot.getTile().dragged(dragged);
	}
	
	@Override
	public void removeContent()
	{
		super.removeContent();
		itemInSlot = null;
	}
	/**
	 * Checks if tile of item in slots is dragged
	 * @return True if slot is dragged, false otherwise
	 */
	public boolean isItemDragged()
	{
		if(itemInSlot != null)
			return itemInSlot.getTile().isDragged();
		else
			return false;
	}
	
	public Item getContent()
	{
		return itemInSlot;
	}
}
