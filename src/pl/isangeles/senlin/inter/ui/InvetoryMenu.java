package pl.isangeles.senlin.inter.ui;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;

import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.core.Character;
/**
 * Graphical representation of character inventory
 * @author Isangeles
 *
 */
public class InvetoryMenu extends InterfaceObject
{
	MouseOverArea inventoryMOA;
	Character player;
	
    public InvetoryMenu(GameContainer gc, Character player) throws SlickException, IOException
    {
        super(GConnector.getInput("ui/background/inventoryBG.png"), "uiInventory", false, gc);
        this.player = player;
        
        inventoryMOA = new MouseOverArea(gc, super.baseTex, (int)Coords.getX("BR", 0), (int)Coords.getY("BR", 0));
    }
    
    public void draw(float x, float y)
    {
        super.draw(x, y);
        player.drawItems(super.x+45, super.y+320);
        
        inventoryMOA.setLocation(super.x, super.y);
    }
    
    public boolean isMouseOver()
    {
    	return inventoryMOA.isMouseOver();
    }
    
    public void reset()
    {
        inventoryMOA.setLocation(Coords.getX("BR", 0), Coords.getY("BR", 0));
    }

}
