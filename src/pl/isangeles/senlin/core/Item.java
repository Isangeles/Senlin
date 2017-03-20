package pl.isangeles.senlin.core;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.inter.ui.ItemTile;
import pl.isangeles.senlin.util.GConnector;

/**
 * Base class for all items in the game
 * @author Isangeles
 *
 */
public abstract class Item
{
    String id;
    String name;
    String info;
    String imgName;
    int value;
    ItemTile itemTile;
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
        itemTile = setTile(gc);
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
    
    private String getInfo(String basicInfo)
    {
    	String fullInfo = name + System.lineSeparator() + info + System.lineSeparator() + "Value: " + value;
    	return fullInfo;
    }
    
    private ItemTile setTile(GameContainer gc) throws SlickException, IOException, FontFormatException
    {
    	return new ItemTile(GConnector.getInput("icon/"+imgName), id, false, gc, getInfo(info));
    }
    
}
