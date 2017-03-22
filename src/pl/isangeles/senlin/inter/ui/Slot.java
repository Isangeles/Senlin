package pl.isangeles.senlin.inter.ui;

import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.inter.InterfaceTile;
import pl.isangeles.senlin.util.GConnector;
/**
 * Class for ui slots (for spells, items, etc)
 * @author Isangeles
 *
 */
public class Slot extends InterfaceObject 
{
	private InterfaceTile itemInSlot;
	
	public Slot(GameContainer gc) throws SlickException, IOException 
	{
		super(GConnector.getInput("ui/slot.png"), "uiSlot", false, gc);
	}
	
	public void draw(float x, float y)
	{
		if(itemInSlot != null)
			itemInSlot.draw(x-3, y-3);
		super.draw(x, y);
	}
	/**
	 * Inserts item tile in slot
	 * @param item Item tile
	 */
	public void insertItem(InterfaceTile item)
	{
		itemInSlot = item; 
	}
	/**
	 * Removes item tile from slot
	 */
	public void removeItem()
	{
		itemInSlot = null;
	}
	
	public void moveItem(float x, float y)
	{
		if(itemInSlot != null)
		{
			itemInSlot.dragged(true);
			itemInSlot.move(x, y);
		}
	}
	
	public void itemDragged(boolean dragged)
	{
		itemInSlot.dragged(dragged);
	}
	
	public boolean isItemDragged()
	{
		if(itemInSlot != null)
			return itemInSlot.isDragged();
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
	
	public InterfaceTile getItem()
	{
		return itemInSlot;
	}
}
