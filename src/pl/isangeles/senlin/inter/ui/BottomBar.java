package pl.isangeles.senlin.inter.ui;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;

import pl.isangeles.senlin.inter.Button;
import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.util.GConnector;

public class BottomBar extends InterfaceObject implements MouseListener
{
    Button quests;
    Button inventory;
    Button skills;
    Button map;
    Button menu;
    MouseOverArea bBarMOA;

    public BottomBar(GameContainer gc) throws SlickException, IOException, FontFormatException
    {
        super(GConnector.getInput("ui/bottomBar.png"), "uiBottomBar", false, gc);
        gc.getInput().addMouseListener(this);
        
        quests = new Button(GConnector.getInput("ui/button/buttonQuests.png"), "uiButtonQ", false, "", gc);
        inventory = new Button(GConnector.getInput("ui/button/buttonInventory.png"), "uiButtonI", false, "", gc);
        skills = new Button(GConnector.getInput("ui/button/buttonSkills.png"), "uiButtonS", false, "", gc);
        map = new Button(GConnector.getInput("ui/button/buttonMap.png"), "uiButtonMa", false, "", gc);
        menu = new Button(GConnector.getInput("ui/button/buttonMenu.png"), "uiButtonQMe", false, "", gc);
        
        bBarMOA = new MouseOverArea(gc, super.baseTex, 0, 0);
    }
    
    public void draw(float x, float y)
    {
        super.draw(x, y);
        quests.draw(super.x+super.getWidth()-50, super.y+10);
        inventory.draw(super.x+super.getWidth()-100, super.y+10);
        skills.draw(super.x+super.getWidth()-150, super.y+10);
        map.draw(super.x+super.getWidth()-200, super.y+10);
        menu.draw(super.x+super.getWidth()-250, super.y+10);
        
        bBarMOA.setLocation(x, y);
    }
    
    public boolean isMouseOver()
    {
    	return bBarMOA.isMouseOver();
    }

    @Override
    public void inputEnded()
    {
    }

    @Override
    public void inputStarted()
    {
    }

    @Override
    public boolean isAcceptingInput()
    {
        return true;
    }

    @Override
    public void setInput(Input input)
    {
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount)
    {
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy)
    {
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy)
    {
    }

    @Override
    public void mousePressed(int button, int x, int y)
    {
    }

    @Override
    public void mouseReleased(int button, int x, int y)
    {
    }

    @Override
    public void mouseWheelMoved(int change)
    {
    }

}
