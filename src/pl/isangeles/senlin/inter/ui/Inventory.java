package pl.isangeles.senlin.inter.ui;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.util.GConnector;

public class Inventory extends InterfaceObject
{

    public Inventory(GameContainer gc) throws SlickException, IOException
    {
        super(GConnector.getInput("ui/background/inventoryBG.png"), "uiInventory", false, gc);
        
    }
    
    public void draw(float x, float y)
    {
        super.draw(x, y);
    }

}
