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
    
    public Item(String id, String name, String info, int value, String imgName, GameContainer gc) throws SlickException, IOException, FontFormatException
    {
        this.id = id;
        this.name = name;
        this.info = info;
        this.value = value;
        this.imgName = imgName;
        itemTile = setTile(gc);
    }
    
    public ItemTile getTile()
    {
    	return itemTile;
    }
    
    public String getId()
    {
    	return id;
    }
    
    private ItemTile setTile(GameContainer gc) throws SlickException, IOException, FontFormatException
    {
    	return new ItemTile(GConnector.getInput("icon/"+imgName), id, false, gc, info);
    }
    
}
