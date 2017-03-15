package pl.isangeles.senlin.core;
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
    int value;
    
    public Item(String id, String name, String info, int value)
    {
        this.id = id;
        this.name = name;
        this.info = info;
        this.value = value;
    }
}
