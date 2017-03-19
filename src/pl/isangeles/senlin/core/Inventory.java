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
public final class Inventory extends LinkedList<Item>
{
	private static final long serialVersionUID = 1L;
	int gold;
    
    public boolean add(Item item)
    {
        if(item != null)
        {
            super.add(item);
            return true;
        }
        else 
            return false;
    }
    
    public Item getItem(String itemId)
    {
    	for(int i = 0; i < super.size(); i ++)
    	{
    		if(super.get(i).id == itemId)
    			return super.get(i);
    	}
    	return null;
    }
    
    public Item getItem(int index)
    {
    	return super.get(index);
    }
    @Deprecated 
    public void drawItems(float x, float y)
    {
    	for(int i = 0; i < super.size(); i ++)
    	{
    		super.get(i).getTile().draw(x, y);
    	}
    }
    
}
