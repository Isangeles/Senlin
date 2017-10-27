package pl.isangeles.senlin.gui.tools;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
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
class ItemSlot extends Slot implements MouseListener
{	
	/**
	 * Item slot constructor
	 * @param gc Slick game container 
	 * @throws SlickException 
	 * @throws IOException
	 * @throws FontFormatException
	 */
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
			if(Item.class.isInstance(item))
				return super.content.add((Item)item);
			else
				return false;
		}
		else
			return false;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.Slot#insertContent(java.util.Collection)
	 */
	@Override
	public boolean insertContent(Collection<? extends SlotContent> content) 
	{
		if(!isFull())
		{
			if(isEmpty())
			{
				List<Item> items = new ArrayList<>();
				for(SlotContent item : content)
				{
					try
					{
						items.add((Item)item);
					}
					catch(ClassCastException e)
					{
						return false;
					}
				}
				if(items.size() > 0)
				{
					Item item = items.get(0);
					if(items.size() > item.getMaxStack())
						return false;
					else
						return this.content.addAll(items);
				}
				else
					return false;
			}
			else
			{
				SlotContent itemIn = this.content.get(0);
				for(SlotContent item : content)
				{
					if(!item.getId().equals(itemIn.getId()))
						return false;	
				}
				if(content.size() > itemIn.getMaxStack()-this.content.size())
					return false;
				else
					return this.content.addAll(content);
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
	
	public List<Item> getContent()
	{
		if(isEmpty())
			return null;
		else
		{
			List<Item> content = new ArrayList<>();
			for(SlotContent item : this.content)
			{
				content.add((Item)item);
			}
			return content;
		}
	}
}
