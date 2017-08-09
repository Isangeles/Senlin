package pl.isangeles.senlin.gui.tools;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.data.GBase;
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
	public ItemSlot(GameContainer gc) throws SlickException, IOException, FontFormatException 
	{
		super(GBase.getImage("uiSlotA"), gc);
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
	
	@Override
	public void removeContent()
	{
		super.removeContent();
	}
	/**
	 * Checks if tile of item in slots is dragged
	 * @return True if slot is dragged, false otherwise
	 */
	public boolean isItemDragged()
	{
		if(!isEmpty())
			return content.get(0).getTile().isDragged();
		else
			return false;
	}
	
	public Item getContent()
	{
		if(isEmpty())
			return null;
		else
			return (Item)content.get(0);
	}
}
