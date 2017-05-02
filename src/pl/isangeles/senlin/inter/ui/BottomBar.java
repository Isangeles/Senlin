package pl.isangeles.senlin.inter.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.MouseOverArea;

import pl.isangeles.senlin.inter.Button;
import pl.isangeles.senlin.inter.GameCursor;
import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.skill.Skill;
/**
 * UI bottom bar class, sends requests to show various parts of ui
 * @author Isangeles
 *
 */
class BottomBar extends InterfaceObject implements UiElement, MouseListener, KeyListener
{
    private Button quests;
    private Button inventory;
    private Button skills;
    private Button map;
    private Button menu;
    
    private SkillSlots sSlots;
    private SkillSlot sMenuDSlot;
    
    private Character player;
    
    private MouseOverArea bBarMOA;
    
    private boolean menuReq;
    private boolean inventoryReq;
    private boolean skillsReq;
    /**
     * Bottom bar constructor
     * @param gc Game container for superclass and bar buttons
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    public BottomBar(GameContainer gc, Character player) throws SlickException, IOException, FontFormatException
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
        
        this.player = player;
        
        bBarMOA = new MouseOverArea(gc, this, 0, 0);
        
        sSlots.slots[0].insertSkill(player.getSkills().get("autoA"));
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
     * Updates bottom bar
     * @param sMenuDSlot Dragged skill slot from skills menu to handle
     */
    public void update(SkillSlot sMenuDSlot)
    {
        this.sMenuDSlot = sMenuDSlot;
    }
    
    @Override
    public void reset()
    {
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
    /**
     * Checks if inventory menu should be open
     * @return Boolean true if menu should be opened or false otherwise
     */
    public boolean isInventoryReq()
    {
        return inventoryReq;
    }
    /**
     * Checks if skills menu should be open
     * @return Boolean true if menu should be opened or false otherwise
     */
    public boolean isSkillsReq()
    {
    	return skillsReq;
    }
    /**
     * Checks if game should be paused
     * @return Boolean true if game should be paused or false otherwise
     */
    public boolean isPauseReq()
    {
        return menuReq || inventoryReq || skillsReq;
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
    	
    	if(button == Input.MOUSE_LEFT_BUTTON && skills.isMouseOver() && !skillsReq)
    		skillsReq = true;
    	else if(button == Input.MOUSE_LEFT_BUTTON && skills.isMouseOver() && skillsReq)
    		skillsReq = false;
    		
    	//Slots dragging system
    	SkillSlot dSlot;
    	if((dSlot = sSlots.getDragged()) != null)
    	{
    		if(sSlots.getOverrided() != null)
    		{
    		    sSlots.moveSkill(dSlot, sSlots.getOverrided());
                return;
    		}
    		dSlot.dragged(false);
    		dSlot.removeSkill();
    	}
    	//Skills menu slots handling
    	if(sMenuDSlot != null)
    	{
    	    if(sSlots.getOverrided() != null)
    	    {
    	        sSlots.insertSkill(sMenuDSlot.getSkill(), sSlots.getOverrided());
    	        sMenuDSlot.dragged(false);
    	        sMenuDSlot = null;
    	    }
    	}
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
		
		if(key == Input.KEY_K && !skillsReq)
    		skillsReq = true;
    	else if(key == Input.KEY_K && skillsReq)
    		skillsReq = false;
		
		if(key == Input.KEY_1)
		{
			sSlots.slots[0].click(true);
			sSlots.slots[0].useSkill();
		}
		if(key == Input.KEY_2)
		{
			sSlots.slots[1].click(true);
			sSlots.slots[1].useSkill();
		}
		if(key == Input.KEY_3)
		{
			sSlots.slots[2].click(true);
			sSlots.slots[2].useSkill();
		}
		if(key == Input.KEY_4)
		{
			sSlots.slots[3].click(true);
			sSlots.slots[3].useSkill();
		}
		if(key == Input.KEY_5)
		{
			sSlots.slots[4].click(true);
			sSlots.slots[4].useSkill();
		}
	}

	@Override
	public void keyReleased(int key, char c) 
	{
		if(key == Input.KEY_1)
			sSlots.slots[0].click(false);
		if(key == Input.KEY_2)
			sSlots.slots[1].click(false);
		if(key == Input.KEY_3)
			sSlots.slots[2].click(false);
		if(key == Input.KEY_4)
			sSlots.slots[3].click(false);
		if(key == Input.KEY_5)
			sSlots.slots[4].click(false);
	}
	
	/**
	 * Inner class for bar skills slots
	 * @author Isangeles
	 *
	 */
	private class SkillSlots
	{
		private SkillSlot[] slots;
		TrueTypeFont slotsTtf;
		
		public SkillSlots(GameContainer gc) throws SlickException, IOException, FontFormatException
		{
			slots = new SkillSlot[5];
	        for(int i = 0; i < slots.length; i ++)
	        {
	        	slots[i] = new SkillSlot(gc);
	        }
        	File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
    		Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            
            slotsTtf = new TrueTypeFont(font.deriveFont(10f), true);
		}
		/**
		 * Draws all skill slots
		 * @param x Position on x-axis
		 * @param y	Position on y-axis
		 */
		public void draw(float x, float y)
		{
			for(int i = 0; i < slots.length; i ++)
			{
				slots[i].draw(x+((slots[i].getWidth()+getDis(10))*i), y, false);
				slotsTtf.drawString(x+((slots[i].getWidth()+getDis(10))*i), y, ""+(i+1));
			}
		}
		/**
		 * Moves skill from slotA to slotB
		 * @param slotA Current skill slot
		 * @param slotB New slot for skill
		 */
		public void moveSkill(SkillSlot slotA, SkillSlot slotB)
		{
			slotA.dragged(false);
			slotB.insertSkill(slotA.getSkill());
			slotA.removeSkill();
		}
		
		public boolean insertSkill(Skill skill, SkillSlot slot)
		{
		    if(slot.isNull())
		    {
		        slot.insertSkill(skill);
		        return true;
		    }
		    else
		        return false;
		}
		/**
		 * Returns dragged skill slot
		 * @return Current dragged slot, if no slot from table dragged returns null
		 */
		public SkillSlot getDragged()
		{
			for(SkillSlot ss : slots)
			{
				if(ss.isSkillDragged())
					return ss;
			}
			return null;
		}
		
		public SkillSlot getOverrided()
		{
		    for(SkillSlot ss : slots)
		    {
		        if(ss.isMouseOver())
		            return ss;
		    }
		    return null;
		}
	}

    @Override
    public void update()
    {
        // TODO Auto-generated method stub
        
    }
}
