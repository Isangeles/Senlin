package pl.isangeles.senlin.inter.ui;

import java.awt.FontFormatException;
import java.io.IOException;

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
/**
 * UI bottom bar class, sends requests to show various parts of ui
 * @author Isangeles
 *
 */
class BottomBar extends InterfaceObject implements MouseListener, KeyListener
{
    private Button quests;
    private Button inventory;
    private Button skills;
    private Button map;
    private Button menu;
    
    SkillSlots sSlots;
    
    private MouseOverArea bBarMOA;
    
    private boolean menuReq;
    private boolean inventoryReq;
    /**
     * Bottom bar constructor
     * @param gc Game container for superclass and bar buttons
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    public BottomBar(GameContainer gc) throws SlickException, IOException, FontFormatException
    {
        super(GConnector.getInput("ui/bottomBar.png"), "uiBottomBar", false, gc);
        gc.getInput().addMouseListener(this);
        gc.getInput().addKeyListener(this);
        
        quests = new Button(GConnector.getInput("ui/button/buttonQuests.png"), "uiButtonQ", false, "", gc, TConnector.getText("ui", "questsBInfo"));
        inventory = new Button(GConnector.getInput("ui/button/buttonInventory.png"), "uiButtonI", false, "", gc, TConnector.getText("ui", "inventoryBInfo"));
        skills = new Button(GConnector.getInput("ui/button/buttonSkills.png"), "uiButtonS", false, "", gc, TConnector.getText("ui", "skillsBInfo"));
        map = new Button(GConnector.getInput("ui/button/buttonMap.png"), "uiButtonMa", false, "", gc, TConnector.getText("ui", "mapBInfo"));
        menu = new Button(GConnector.getInput("ui/button/buttonMenu.png"), "uiButtonQMe", false, "", gc, TConnector.getText("ui", "menuBInfo"));
        
        sSlots = new SkillSlots(gc);
        
        bBarMOA = new MouseOverArea(gc, this, 0, 0);
    }
    /**
     * Draws bar
     */
    public void draw(float x, float y)
    {
        super.draw(x, y, false);
        menu.draw(x+super.getScaledWidth()-getDis(50), y+getDis(10), false);
        map.draw(x+super.getScaledWidth()-getDis(100), y+getDis(10), false);
        skills.draw(x+super.getScaledWidth()-getDis(150), y+getDis(10), false);
        inventory.draw(x+super.getScaledWidth()-getDis(200), y+getDis(10), false);
        quests.draw(x+super.getScaledWidth()-getDis(250), y+getDis(10), false);
        
        sSlots.draw(x+getDis(20), y+getDis(10));
        
        bBarMOA.setLocation(x, y);
    }
    /**
     * Checks if mouse is over bar
     * @return True if mouse is over or false otherwise
     */
    public boolean isMouseOver()
    {
    	return bBarMOA.isMouseOver();
    }
    /**
     * Checks if menu should be open
     * @return Boolean true if menu should be opened or false otherwise 
     */
    public boolean isMenuReq()
    {
    	return menuReq;
    }
    
    public boolean isInventoryReq()
    {
        return inventoryReq;
    }
    
    public boolean isPauseReq()
    {
        return menuReq || inventoryReq;
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
    	
    	if(button == Input.MOUSE_LEFT_BUTTON && inventory.isMouseOver() && !inventoryReq)
            inventoryReq = true;
        else if(button == Input.MOUSE_LEFT_BUTTON && inventory.isMouseOver() && inventoryReq)
            inventoryReq = false;
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
		
		if(key == Input.KEY_I && !inventoryReq)
		    inventoryReq = true ;
		else if(key == Input.KEY_I && inventoryReq)
            inventoryReq = false ;
	}

	@Override
	public void keyReleased(int key, char c) 
	{
	}
	/**
	 * Inner class for skills slots
	 * @author Isangeles
	 *
	 */
	private class SkillSlots
	{
		private SkillSlot[] slots;
		
		public SkillSlots(GameContainer gc) throws SlickException, IOException
		{
			slots = new SkillSlot[5];
	        for(int i = 0; i < slots.length; i ++)
	        {
	        	slots[i] = new SkillSlot(gc);
	        }
		}
		
		public void draw(float x, float y)
		{
			for(int i = 0; i < slots.length; i ++)
			{
				slots[i].draw(x+(slots[i].getWidth()*i), y, false);
			}
		}
	}
}
