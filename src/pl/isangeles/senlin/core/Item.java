package pl.isangeles.senlin.core;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.data.CommBase;
import pl.isangeles.senlin.inter.ui.ItemTile;
import pl.isangeles.senlin.util.GConnector;

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
     * @param imgName Item image file name
     * @param gc Game container for superclass
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    public Item(String id, String name, String info, int value, String imgName, GameContainer gc) throws SlickException, IOException, FontFormatException
    {
        this.id = id;
        this.name = name;
        this.info = info;
        this.value = value;
        this.imgName = imgName;
        itemCounter ++;
        
    }
    /**
     * Draws item tile
     * @param x Position on X axis
     * @param y Position on Y axis
     */
    public void draw(float x, float y)
    {
    	itemTile.draw(x, y);
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
    @Override
    public String toString()
    {
    	return name;
    }
    @Override
    public Item clone()
    {
    	try 
    	{
    		itemNumber ++;
			Item item = (Item) super.clone();
			item.setTile(this.itemTile.clone());
			itemNumber --;
			return item;
		} 
    	catch (CloneNotSupportedException e)
    	{
    		CommBase.addWarning("Item cloning error!");
			return this;
		}
    }
    /**
     * Get basic info about item
     * @return String with basic info
     */
    protected String getInfo()
    {
    	String fullInfo = name + System.lineSeparator() + info + System.lineSeparator() + "Value: " + value;
    	return fullInfo;
    }
    
    protected ItemTile setTile(GameContainer gc) throws SlickException, IOException, FontFormatException
    {
    	return new ItemTile(GConnector.getInput("icon/"+imgName), id+itemNumber, false, gc, getInfo());
    }
    
    public void setTile(ItemTile tile)
    {
    	this.itemTile = tile;
    }
    
}
