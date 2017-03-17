package pl.isangeles.senlin.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import pl.isangeles.senlin.inter.ui.ItemTile;
/**
 * Class for character inventory, contains all player items
 * @author Isangeles
 *
 */
public final class Inventory
{
    List<Item> itemContainer = new LinkedList<>();
    int gold;
    
    public void add(Item item)
    {
        itemContainer.add(item);
    }
    
    public Item getItem(String itemId)
    {
    	for(int i = 0; i < itemContainer.size(); i ++)
    	{
    		if(itemContainer.get(i).id == itemId)
    			return itemContainer.get(i);
    	}
    	return null;
    }
    
    public void drawItems(float x, float y)
    {
    	for(int i = 0; i < itemContainer.size(); i ++)
    	{
    		itemContainer.get(i).getTile().draw(x, y);
    	}
    }
    
}
