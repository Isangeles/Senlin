package pl.isangeles.senlin.core;

public abstract class Item
{
    String id;
    int value;
    
    public Item(String id, int value)
    {
        this.id = id;
        this.value = value;
    }
}
