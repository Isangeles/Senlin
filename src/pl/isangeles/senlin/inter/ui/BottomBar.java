package pl.isangeles.senlin.inter.ui;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;

import pl.isangeles.senlin.inter.Button;
import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;

public class BottomBar extends InterfaceObject implements MouseListener, KeyListener
{
    private Button quests;
    private Button inventory;
    private Button skills;
    private Button map;
    private Button menu;
    private MouseOverArea bBarMOA;
    
    private boolean menuReq;

    public BottomBar(GameContainer gc) throws SlickException, IOException, FontFormatException
    {
        super(GConnector.getInput("ui/bottomBar.png"), "uiBottomBar", false, gc);
        gc.getInput().addMouseListener(this);
        gc.getInput().addKeyListener(this);
        
        quests = new Button(GConnector.getInput("ui/button/buttonQuests.png"), "uiButtonQ", false, "", gc, TConnector.getText("textUI.txt", "questsBInfo"));
        inventory = new Button(GConnector.getInput("ui/button/buttonInventory.png"), "uiButtonI", false, "", gc, TConnector.getText("textUI.txt", "inventoryBInfo"));
        skills = new Button(GConnector.getInput("ui/button/buttonSkills.png"), "uiButtonS", false, "", gc, TConnector.getText("textUI.txt", "skillsBInfo"));
        map = new Button(GConnector.getInput("ui/button/buttonMap.png"), "uiButtonMa", false, "", gc, TConnector.getText("textUI.txt", "mapBInfo"));
        menu = new Button(GConnector.getInput("ui/button/buttonMenu.png"), "uiButtonQMe", false, "", gc, TConnector.getText("textUI.txt", "menuBInfo"));
        
        bBarMOA = new MouseOverArea(gc, super.baseTex, 0, 0);
    }
    
    public void draw(float x, float y)
    {
        super.draw(x, y);
        menu.draw(super.x+super.getWidth()-50, super.y+10);
        map.draw(super.x+super.getWidth()-100, super.y+10);
        skills.draw(super.x+super.getWidth()-150, super.y+10);
        inventory.draw(super.x+super.getWidth()-200, super.y+10);
        quests.draw(super.x+super.getWidth()-250, super.y+10);
        
        bBarMOA.setLocation(x, y);
    }
    
    public boolean isMouseOver()
    {
    	return bBarMOA.isMouseOver();
    }
    
    public boolean isMenuReq()
    {
    	return menuReq;
    }
    
    public void hideMenu()
    {
    	menuReq = false;
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
    	if(button == Input.MOUSE_LEFT_BUTTON && menu.isMouseOver() && !menuReq)
    		menuReq = true;
    	else if(button == Input.MOUSE_LEFT_BUTTON && menu.isMouseOver() && menuReq)
    		menuReq = false;
    }

    @Override
    public void mouseWheelMoved(int change)
    {
    }

	@Override
	public void keyPressed(int key, char c) 
	{
		if(key == Input.KEY_ESCAPE && !menuReq)
    		menuReq = true;
    	else if(key == Input.KEY_ESCAPE && menuReq)
    		menuReq = false;
	}

	@Override
	public void keyReleased(int key, char c) 
	{
	}

}
