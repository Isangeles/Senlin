package pl.isangeles.senlin.inter.ui;

import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.inter.InterfaceTile;
import pl.isangeles.senlin.util.GConnector;
/**
 * Class for ui item slots
 * @author Isangeles
 *
 */
public class ItemSlot extends InterfaceObject 
{
	private Item itemInSlot;
	
	public ItemSlot(GameContainer gc) throws SlickException, IOException 
	{
		super(GConnector.getInput("ui/slot.png"), "uiSlot", false, gc);
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
	public void insertItem(Item item)
	{
		itemInSlot = item; 
	}
	/**
	 * Removes item from slot
	 */
	public void removeItem()
	{
		itemInSlot = null;
	}
	
	public void dragged(boolean dragged)
	{
		itemInSlot.getTile().dragged(dragged);
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
	/**
	 * Checks if slot is empty
	 * @return True if slot is empty, false otherwise
	 */
	public boolean isNull()
	{
		if(itemInSlot == null)
			return true;
		else
			return false;
	}
	
	public Item getItem()
	{
		return itemInSlot;
	}
}
