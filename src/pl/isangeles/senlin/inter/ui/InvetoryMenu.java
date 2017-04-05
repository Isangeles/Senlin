package pl.isangeles.senlin.inter.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.MouseOverArea;

import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Item;
import pl.isangeles.senlin.data.CommBase;
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
	private TrueTypeFont textTtf;
	/**
	 * Inventory constructor 
	 * @param gc Game container 
	 * @param player Player character with inventory to represent
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException 
	 */
    public InvetoryMenu(GameContainer gc, Character player) throws SlickException, IOException, FontFormatException
    {
        super(GConnector.getInput("ui/background/inventoryBG.png"), "uiInventory", false, gc);
        this.player = player;
        gc.getInput().addMouseListener(this);
        
        File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        textTtf = new TrueTypeFont(font.deriveFont(12f), true);
        
        inventoryMOA = new MouseOverArea(gc, this, 0, 0, (int)super.getScaledWidth(), (int)super.getScaledHeight());
        
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
        super.draw(x, y, false);
        
        float firstSlotX = x+getDis(48);
    	float firstSlotY = y+getDis(323);
    	
        for(int i = 0; i < 5; i ++)
        {
        	for(int j = 0; j < 20; j ++)
        	{
        		slots[i][j].draw(firstSlotX + (j*getDis(45)), firstSlotY + (i*getDis(40)), false);
        	}
        }
        
        float firstStatX = x+getDis(300);
        float firstStatY = y+getDis(50);
        
        for(int i = 0, j = 0; i < getCharStats().length; i ++, j ++)
        {
        	if(i != 0 && i % 6 == 0)
        	{
        		firstStatX += textTtf.getWidth(getCharStats()[2] + getDis(5));
        		j = 0;
        	}
        	textTtf.drawString(firstStatX, firstStatY + (j*textTtf.getHeight()), getCharStats()[i]);
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
		if(getDragged() != null)
		{
			ItemSlot draggedSlot = getDragged();
			for(ItemSlot[] slotsLine : slots)
			{
				for(ItemSlot slot : slotsLine)
				{
					if(slot.isMouseOver())
					{
						moveItem(draggedSlot, slot);
						return;
					}
				}
			}
			draggedSlot.dragged(false);
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
    /**
     * Adds all player items into inventory menu 
     */
    private void addItems()
    {
    	for(Item item : player.getItems())
    	{
    	    for(ItemSlot[] slotsLine : slots)
    	    {
    	        for(ItemSlot slot : slotsLine)
    	        {
    	            if(slot.isNull() && !isIn(item.getNumber()))
    	            {
    	                itemsIn.add(item.getNumber());
    	                slot.insertItem(item);
    	                CommBase.addInformation(item.toString() + " added to inventory menu");
    	                break;
    	            }
    	                
    	        }
    	    }
    	}
    }
    /**
     * Returns dragged slot
     * @return Slot dragged by mouse
     */
    private ItemSlot getDragged()
    {
    	for(ItemSlot[] slotsLine : slots)
		{
			for(ItemSlot slot : slotsLine)
			{
				if(slot.isItemDragged())
				{
					return slot;
				}
			}
		}
    	return null;
    }
    /**
     * Moves item from one slot to another given slot
     * @param draggedSlot Slot with item
     * @param slotForItem New slot for item
     */
    private void moveItem(ItemSlot draggedSlot, ItemSlot slotForItem)
    {
    	ItemSlot is = draggedSlot;
    	
    	try
    	{
    		slotForItem.insertItem(is.getItem());
    		is.dragged(false);
			is.removeItem();
    	}
    	catch(NullPointerException e)
    	{
    		return;
    	}
    }
    
    private String[] getCharStats()
    {
    	return new String[]{player.getName(), "Level: " + player.getLevel(), "Experience: " + player.getExperience() + "/" + player.getMaxExperience(), "Health: " + player.getHealth(),
    			"Magicka: " + player.getMagicka(), "Haste: " + player.getHaste(), "Strenght: " + player.getStr(), "Constitution: " + player.getCon(), "Dexterity: " + player.getDex(), 
    			"Inteligence: " + player.getInt(), "Wisdom: " + player.getWis()};
    }
}
