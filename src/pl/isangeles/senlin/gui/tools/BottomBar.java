package pl.isangeles.senlin.gui.tools;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.MouseOverArea;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.gui.Button;
import pl.isangeles.senlin.gui.GameCursor;
import pl.isangeles.senlin.gui.InterfaceObject;
import pl.isangeles.senlin.gui.Slot;
/**
 * UI bottom bar class, sends requests to show various parts of ui
 * @author Isangeles
 *
 */
class BottomBar extends InterfaceObject implements UiElement, SaveElement, MouseListener, KeyListener
{
    private Button questsB;
    private Button inventoryB;
    private Button craftingB;
    private Button skillsB;
    private Button mapB;
    private Button menuB;
    
    private SkillSlots sSlots;
    private SkillSlot sMenuDSlot;
    
    private Character player;
    
    private MouseOverArea bBarMOA;
    
    private InGameMenu menu;
    private InventoryMenu inventory;
    private SkillsMenu skills;
    private JournalMenu journal;
    private CraftingMenu crafting;
    /**
     * Bottom bar constructor
     * @param gc Game container for superclass and bar buttons
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    public BottomBar(GameContainer gc, InGameMenu menu, InventoryMenu inventory, SkillsMenu skills, JournalMenu journal, CraftingMenu crafting, Character player) throws SlickException, IOException, FontFormatException
    {
        super(GConnector.getInput("ui/bottomBar_DG.png"), "uiBottomBar", false, gc);
        gc.getInput().addMouseListener(this);
        gc.getInput().addKeyListener(this);
        
        this.menu = menu;
        this.inventory = inventory;
        this.skills = skills;
        this.journal = journal;
        this.crafting = crafting;
        questsB = new Button(GConnector.getInput("ui/button/buttonQuests.png"), "uiButtonQue", false, "", gc, TConnector.getText("ui", "questsBInfo"));
        inventoryB = new Button(GConnector.getInput("ui/button/buttonInventory.png"), "uiButtonInv", false, "", gc, TConnector.getText("ui", "inventoryBInfo"));
        craftingB = new Button(GConnector.getInput("ui/button/buttonCrafting.png"), "uiButtonCraft", false, "", gc, TConnector.getText("ui", "craftingBInfo"));
        skillsB = new Button(GConnector.getInput("ui/button/buttonSkills.png"), "uiButtonSkill", false, "", gc, TConnector.getText("ui", "skillsBInfo"));
        mapB = new Button(GConnector.getInput("ui/button/buttonMap.png"), "uiButtonMap", false, "", gc, TConnector.getText("ui", "mapBInfo"));
        menuB = new Button(GConnector.getInput("ui/button/buttonMenu.png"), "uiButtonQMenu", false, "", gc, TConnector.getText("ui", "menuBInfo"));
        
        
        sSlots = new SkillSlots(gc);
        
        this.player = player;
        
        bBarMOA = new MouseOverArea(gc, this, 0, 0);
        
        sSlots.slots[0].insertContent(player.getSkills().get("autoA"));
        sSlots.slots[1].insertContent(player.getSkills().get("shot"));
    }
    /**
     * Draws bar
     */
    public void draw(float x, float y)
    {
        super.draw(x, y, false);
        menuB.draw(x+super.getScaledWidth()-getDis(30), y+getDis(10), false);
        mapB.draw(x+super.getScaledWidth()-getDis(80), y+getDis(10), false);
        skillsB.draw(x+super.getScaledWidth()-getDis(130), y+getDis(10), false);
        inventoryB.draw(x+super.getScaledWidth()-getDis(180), y+getDis(10), false);
        craftingB.draw(x+super.getScaledWidth()-getDis(230), y+getDis(10), false);
        questsB.draw(x+super.getScaledWidth()-getDis(280), y+getDis(10), false);
        
        sSlots.draw(x+getDis(20), y+getDis(10));
        
        bBarMOA.setLocation(x, y);
    }
    
    @Override
    public void update()
    {
        sMenuDSlot = skills.getDragged();
    }
    
    @Override
    public void reset()
    {
    }
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.elements.UiElement#close()
	 */
	@Override
	public void close() 
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
     * Checks if game should be paused
     * @return Boolean true if game should be paused or false otherwise
     */
    public boolean isPauseReq()
    {
        return menu.isOpenReq() || inventory.isOpenReq() || skills.isOpenReq();
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
    	if(button == Input.MOUSE_LEFT_BUTTON)
    	{
    		if(menuB.isMouseOver() && !menu.isOpenReq())
        		menu.open();
        	else if(menuB.isMouseOver() && menu.isOpenReq())
        		menu.close();
        	
        	if(inventoryB.isMouseOver() && !inventory.isOpenReq())
                inventory.open();
            else if(inventoryB.isMouseOver() && inventory.isOpenReq())
                inventory.close();
        	
        	if(craftingB.isMouseOver() && !crafting.isOpenReq())
                crafting.open();
            else if(craftingB.isMouseOver() && crafting.isOpenReq())
                crafting.close();
        	
        	if(skillsB.isMouseOver() && !skills.isOpenReq())
        		skills.open();
        	else if(skillsB.isMouseOver() && skills.isOpenReq())
        		skills.close();
        	
        	if(questsB.isMouseOver() && !journal.isOpenReq())
        		journal.open();
        	else if(questsB.isMouseOver() && journal.isOpenReq())
        		journal.close();
    	}
    		
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
    		dSlot.removeContent();
    	}
    	//Skills menu slots handling
    	if(sMenuDSlot != null)
    	{
    	    if(sSlots.getOverrided() != null)
    	    {
    	        sSlots.insertSkill(sMenuDSlot.getContent(), sSlots.getOverrided());
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
		if(key == Input.KEY_ESCAPE && !menu.isOpenReq())
    		menu.open();
    	else if(key == Input.KEY_ESCAPE && menu.isOpenReq())
    		menu.close();
		
		if(key == Input.KEY_I && !inventory.isOpenReq())
		    inventory.open();
		else if(key == Input.KEY_I && inventory.isOpenReq())
            inventory.close();
		
		if(key == Input.KEY_K && !skills.isOpenReq())
    		skills.open();
    	else if(key == Input.KEY_K && skills.isOpenReq())
    		skills.close();
		
		if(key == Input.KEY_P && !crafting.isOpenReq())
			crafting.open();
		else if(key == Input.KEY_P && crafting.isOpenReq())
			crafting.close();
		
		if(key == Input.KEY_L && !journal.isOpenReq())
			journal.open();
		else if(key == Input.KEY_L && journal.isOpenReq())
			journal.close();
		
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
		private TrueTypeFont slotsTtf;
		
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
			slotB.insertContent(slotA.getContent());
			slotA.removeContent();
		}
		
		public boolean insertSkill(Skill skill, SkillSlot slot)
		{
		    if(slot.isEmpty())
		    {
		        slot.insertContent(skill);
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
		
		public SkillSlot[] getAll()
		{
			return slots;
		}
		
		public void clear()
		{
			for(SkillSlot slot : slots)
			{
				slot.removeContent();
			}
		}
	}
    /**
     * Saves bottom bar configuration to XML document element
     * @param doc Document for save game file
     * @return XML document element
     */
    public Element getSave(Document doc)
    {
    	Element barE = doc.createElement("bar");
    	
    	Element slotsE = doc.createElement("slots");
    	for(int i = 0; i < 5; i ++)
    	{
    		if(!sSlots.slots[i].isEmpty())
    		{
    			Element slotE = doc.createElement("slot");
    			slotE.setAttribute("id", i+"");
    			slotE.setTextContent(sSlots.slots[i].getContent().getId());
    			slotsE.appendChild(slotE);
    		}
    	}
    	barE.appendChild(slotsE);
    	
    	return barE;
    }
    
    public void loadLayout(Map<String, Integer> layout)
    {
    	sSlots.clear();
    	for(String id : layout.keySet())
    	{
    		sSlots.slots[layout.get(id)].insertContent(player.getSkills().get(id));
    	}
    }

}
