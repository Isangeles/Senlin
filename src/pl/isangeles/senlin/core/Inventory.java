package pl.isangeles.senlin.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class Inventory
{
    List<Item> itemContainer = new LinkedList<>();
    int gold;
    
    public void add(Item item)
    {
        itemContainer.add(item);
    }
    
}
