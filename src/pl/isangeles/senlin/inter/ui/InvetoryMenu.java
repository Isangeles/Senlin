package pl.isangeles.senlin.inter.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;

import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.core.Character;
/**
 * Graphical representation of character inventory
 * @author Isangeles
 *
 */
public class InvetoryMenu extends InterfaceObject
{
	MouseOverArea inventoryMOA;
	Character player;
	private Slot[][] slots;
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
        
        inventoryMOA = new MouseOverArea(gc, super.baseTex, (int)Coords.getX("BR", 0), (int)Coords.getY("BR", 0));
        
        slots = new Slot[5][20];
        for(int i = 0; i < 5; i ++)
        {
        	for(int j = 0; j < 20; j ++)
        	{
        		slots[i][j] = new Slot(gc);
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
            			slots[i][j].insertItem(player.getItem(itemId).getTile());
            		
            		itemId ++;
            	}
            }
    	}
    	catch(IndexOutOfBoundsException e)
    	{
    		return;
    	}
    }
    
    private boolean isIn(int indexInInventory)
    {
    	return itemsIn.contains(indexInInventory);
    }

}
