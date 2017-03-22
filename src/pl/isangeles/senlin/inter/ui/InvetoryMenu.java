package pl.isangeles.senlin.inter.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;

import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Item;
/**
 * Graphical representation of character inventory
 * @author Isangeles
 *
 */
public class InvetoryMenu extends InterfaceObject implements MouseListener
{
	private MouseOverArea inventoryMOA;
	private Character player;
	private ItemSlot[][] slots;
	private List<Integer> itemsIn = new ArrayList<>();
	/**
	 * Inventory constructor 
	 * @param gc Game container 
	 * @param player Player character with inventory to represent
	 * @throws SlickException
	 * @throws IOException
	 */
    public InvetoryMenu(GameContainer gc, Character player) throws SlickException, IOException
    {
        super(GConnector.getInput("ui/background/inventoryBG.png"), "uiInventory", false, gc);
        this.player = player;
        gc.getInput().addMouseListener(this);
        
        inventoryMOA = new MouseOverArea(gc, this, (int)Coords.getX("BR", 0), (int)Coords.getY("BR", 0));
        
        slots = new ItemSlot[5][20];
        for(int i = 0; i < 5; i ++)
        {
        	for(int j = 0; j < 20; j ++)
        	{
        		slots[i][j] = new ItemSlot(gc);
        	}
        }
    }
    /**
     * Draws inventory
     */
    public void draw(float x, float y)
    {
        super.draw(x, y);
        
        float firstSlotX = x+48;
    	float firstSlotY = y+323;
    	
        for(int i = 0; i < 5; i ++)
        {
        	for(int j = 0; j < 20; j ++)
        	{
        		slots[i][j].draw(firstSlotX + (j*45), firstSlotY + (i*40));;
        	}
        }
        
        inventoryMOA.setLocation(super.x, super.y);
    }
    
    public boolean isMouseOver()
    {
    	return inventoryMOA.isMouseOver();
    }
    
    public void reset()
    {
        inventoryMOA.setLocation(Coords.getX("BR", 0), Coords.getY("BR", 0));
    }
    
    public void update()
    {
    	addItems();
    }
    /**
     * Adds all player items into inventory menu 
     */
    private void addItems()
    {
    	try
    	{
    		int itemId = 0;
            for(int i = 0; i < 5; i ++)
            {
            	for(int j = 0; j < 20; j ++)
            	{
            		if(slots[i][j].isNull() && !isIn(itemId))
            			slots[i][j].insertItem(player.getItem(itemId));
            		
            		itemId ++;
            	}
            }
    	}
    	catch(IndexOutOfBoundsException e)
    	{
    		return;
    	}
    }
    
    public void moveItem(ItemSlot slotForItem)
    {
    	ItemSlot is = null;
    	
    	for(ItemSlot[] slotsLine : slots)
		{
			for(ItemSlot slot : slotsLine)
			{
				if(slot.isItemDragged())
				{
					is = slot;
					slot.dragged(false);
					break;
				}
			}
		}
    	
    	try
    	{
    		slotForItem.insertItem(is.getItem());
			is.removeItem();
    	}
    	catch(NullPointerException e)
    	{
    		return;
    	}
    	
    	
    }
    
	@Override
	public void inputEnded() 
	{
	}
	@Override
	public void inputStarted() 
	{
	}
	@Override
	public boolean isAcceptingInput() 
	{
		return true;
	}
	@Override
	public void setInput(Input input) 
	{
	}
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) 
	{
	}
	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) 
	{
		/*
		
		*/
	}
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) 
	{
	}
	@Override
	public void mousePressed(int button, int x, int y) 
	{
		
	}
	@Override
	public void mouseReleased(int button, int x, int y) 
	{
		for(ItemSlot[] slotsLine : slots)
		{
			for(ItemSlot slot : slotsLine)
			{
				if(slot.isMouseOver())
				{
					moveItem(slot);
					return;
				}
			}
		}
	}
	@Override
	public void mouseWheelMoved(int change) 
	{
	}
	/**
     * Checks if specific item is in inventory menu
     * @param indexInInventory Index of item in character inventory container
     * @return True if item is in inventory menu, false otherwise
     */
    private boolean isIn(int indexInInventory)
    {
    	return itemsIn.contains(indexInInventory);
    }
}
