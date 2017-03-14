package pl.isangeles.senlin.inter;


import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.inter.ui.*;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.core.Character;

public class UserInterface
{
    Character player;
    BottomBar bBar;
    CharacterFrame charFrame;
    InGameMenu igMenu;
    Inventory inventory;
    Warning uiWarning;
    
    public UserInterface(GameContainer gc, Character player) throws SlickException, IOException, FontFormatException
    {
        this.player = player;
        
        bBar = new BottomBar(gc);
        charFrame = new CharacterFrame(gc, player);
        igMenu = new InGameMenu(gc);
        inventory = new Inventory(gc);
        uiWarning = new Warning(gc, "");
    }
    
    public void update(Character player)
    {
        this.player = player;
    }
    
    public void draw()
    {
        bBar.draw(Coords.getX("BL", 200), Coords.getY("BL", 70));
        charFrame.draw(Coords.getX("TL", 10), Coords.getY("TL", 10));
        
        if(bBar.isMenuReq())
        	igMenu.draw(Coords.getX("CE", -100), Coords.getY("CE", -100));
        if(igMenu.isResumeReq() || !bBar.isMenuReq())
        {
        	bBar.hideMenu();
        	igMenu.reset();
        }
        
        if(bBar.isInventoryReq())
            inventory.draw(Coords.getX("CE", -500), Coords.getY("CE", -200));
        	
    }
    
    public boolean isMouseOver()
    {
    	return bBar.isMouseOver() || igMenu.isMouseOver();
    }
    
    public boolean isExitReq()
    {
    	return igMenu.isExitReq();
    }
    
}
