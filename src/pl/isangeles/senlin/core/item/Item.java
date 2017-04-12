package pl.isangeles.senlin.core.item;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.inter.ui.ItemTile;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;

/**
 * Base class for all items in the game
 * @author Isangeles
 *
 */
public abstract class Item
{
	private static int itemCounter;
	protected int itemNumber = itemCounter;
	protected String id;
    protected String name;
    protected String info;
    protected String imgName;
    protected int value;
    protected ItemTile itemTile;
    /**
     * Basic item constructor
     * @param id Item ID, unique for all items
     * @param name Item name
     * @param info Info about item
     * @param value Item value
     * @param imgName Item image file name in icon dir
     * @param gc Game container for superclass
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    public Item(String id, int value, String imgName, GameContainer gc) throws SlickException, IOException, FontFormatException
    {
        this.id = id;
        this.name = TConnector.getText("items", id+"-name");
        this.info = TConnector.getText("items", id+"-info");
        this.value = value;
        this.imgName = imgName;
        itemCounter ++;   
    }
    /**
     * Draws item tile
     * @param x Position on X axis
     * @param y Position on Y axis
     */
    public void draw(float x, float y, boolean scaledPos)
    {
    	itemTile.draw(x, y, scaledPos);
    }
    /**
     * Get item tile
     * @return ItemTile of specific item
     */
    public ItemTile getTile()
    {
    	return itemTile;
    }
    /**
     * Get item ID
     * @return Unique item ID in String
     */
    public String getId()
    {
    	return id;
    }
    /**
     * Returns number unique for every item in game
     * @return Item number
     */
    public int getNumber()
    {
    	return itemNumber;
    }
    /**
     * Returns items name
     */
    @Override
    public String toString()
    {
    	return name;
    }
    /**
     * Compares items
     * @param item Item to check
     * @return True if unique item numbers are same, false otherwise
     */
    public boolean equals(Item item)
    {
    	try
    	{
    		if(this.itemNumber == item.getNumber())
        		return true;
        	else
        		return false;
    	}
    	catch(NullPointerException e)
    	{
    		return false;
    	}
    }
    /**
     * Returns basic info about item for item tile
     * @return String with basic info
     */
    protected abstract String getInfo();
    /**
     * Sets image from icon dir for item tile
     * @param gc Slick game container
     * @return ItemTile 
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    protected ItemTile setTile(GameContainer gc) throws SlickException, IOException, FontFormatException
    {
    	return new ItemTile(GConnector.getInput("icon/"+imgName), id+itemNumber, false, gc, this.getInfo());
    }
    /**
     * Sets specific item tile as this item tile
     * UNUSED
     * @param tile Item tile
     */
    @Deprecated
    public void setTile(ItemTile tile)
    {
    	this.itemTile = tile;
    }
    
}
