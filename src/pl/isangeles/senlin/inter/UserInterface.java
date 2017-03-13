package pl.isangeles.senlin.inter;


import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;

import pl.isangeles.senlin.inter.ui.*;
import pl.isangeles.senlin.util.Coords;

public class UserInterface
{
    BottomBar bBar;
    InGameMenu igMenu;
    Warning uiWarning;
    
    public UserInterface(GameContainer gc) throws SlickException, IOException, FontFormatException
    {
        bBar = new BottomBar(gc);
        igMenu = new InGameMenu(gc);
        uiWarning = new Warning(gc, "");
    }
    
    public void draw()
    {
        bBar.draw(Coords.get("BL", 200, 70)[0], Coords.get("BL", 100, 70)[1]);
        if(bBar.isMenuReq())
        	igMenu.draw(Coords.get("CE", -100, 0)[0], Coords.get("CE", 0, -100)[1]);
        	
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
