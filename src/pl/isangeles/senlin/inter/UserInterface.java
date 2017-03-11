package pl.isangeles.senlin.inter;


import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.inter.ui.*;
import pl.isangeles.senlin.util.Coords;

public class UserInterface
{
    BottomBar bBar;
    
    public UserInterface(GameContainer gc) throws SlickException, IOException, FontFormatException
    {
        bBar = new BottomBar(gc);
    }
    
    public void draw()
    {
        bBar.draw(Coords.get("BL", 100, 70)[0], Coords.get("BL", 100, 70)[1]);
    }
}
