package pl.isangeles.senlin.inter.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.item.Armor;
import pl.isangeles.senlin.core.item.Equippable;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.item.Trinket;
import pl.isangeles.senlin.core.item.Weapon;
import pl.isangeles.senlin.data.Log;
/**
 * Graphical representation of character inventory
 * @author Isangeles
 *
 */
class InvetoryMenu extends InterfaceObject implements UiElement, MouseListener
{
	private Character player;
	private ItemSlot[][] slots;
	private EquipmentSlots eqSlots;
	private List<Integer> itemsIn = new ArrayList<>();
	private TrueTypeFont textTtf;
	/**
	 * Inventory constructor 
	 * @param gc Game container 
	 * @param player Player character with inventory to represent
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException 
	 */
    public InvetoryMenu(GameContainer gc, Character player) throws SlickException, IOException, FontFormatException
    {
        super(GConnector.getInput("ui/background/inventoryBG.png"), "uiInventory", false, gc);
        this.player = player;
        gc.getInput().addMouseListener(this);
        
        File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        textTtf = new TrueTypeFont(font.deriveFont(12f), true);
        
        slots = new ItemSlot[5][20];
        for(int i = 0; i < 5; i ++)
        {
        	for(int j = 0; j < 20; j ++)
        	{
        		slots[i][j] = new ItemSlot(gc);
        	}
        }
        
        eqSlots = new EquipmentSlots(gc);
    }
    /**
     * Draws inventory
     */
    public void draw(float x, float y)
    {
        super.draw(x, y, false);
        //Slots drawing
        float firstSlotX = x+getDis(48);
    	float firstSlotY = y+getDis(323);
    	
        for(int i = 0; i < 5; i ++)
        {
        	for(int j = 0; j < 20; j ++)
        	{
        		slots[i][j].draw(firstSlotX + (j*getDis(45)), firstSlotY + (i*getDis(40)), false);
        	}
        }
        //Eq slots drawing
        eqSlots.draw(x, y);
        
        textTtf.drawString(x+(getScaledWidth()/2), y, TConnector.getText("ui", "iMenuTitle"));
        textTtf.drawString(x+(getScaledWidth()/2), y+(getScaledHeight()-getDis(20)), TConnector.getText("ui", "iMenuGold") + ": " + player.getInventory().getGold());
        //Stats drawing
        float firstStatX = x+getDis(300);
        float firstStatY = y+getDis(50);
        
        for(int i = 0, j = 0; i < getCharStats().length; i ++, j ++)
        {
        	if(i != 0 && i % 6 == 0)
        	{
        		firstStatX += textTtf.getWidth(getCharStats()[2] + getDis(5));
        		j = 0;
        	}
        	textTtf.drawString(firstStatX, firstStatY + (j*textTtf.getHeight()), getCharStats()[i]);
        }
        super.moveMOA(super.x, super.y);
    }
    /**
     * Checks if mouse is over inventory menu
     */
    public boolean isMouseOver()
    {
    	return super.isMouseOver();
    }
    /**
     * Resets inventory menu to default state
     */
    public void reset()
    {
        super.moveMOA(Coords.getX("BR", 0), Coords.getY("BR", 0));
    }
    /**
     * Updates inventory menu
     */
    public void update()
    {
        addItems();
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
		if(getDragged() != null)
		{
			ItemSlot draggedSlot = getDragged();
			for(ItemSlot[] slotsLine : slots)
			{
				for(ItemSlot slot : slotsLine)
				{
					if(slot.isMouseOver())
					{
						if(eqSlots.contains(draggedSlot))
							eqSlots.removeFromEq(draggedSlot);
						moveItem(draggedSlot, slot);
						return;
					}
				}
			}
			
			if(eqSlots.getMouseOverSlot() != null)
			{
				if(eqSlots.setEqItem(draggedSlot.getContent(), eqSlots.getMouseOverSlot()))
					moveItem(draggedSlot, eqSlots.getMouseOverSlot());
				return;
			}
			
			draggedSlot.dragged(false);
		}
	}
	@Override
	public void mouseWheelMoved(int change) 
	{
	}
	/**
     * Checks if specific item is in inventory menu
     * @param indexInInventory Index of item in character inventory container
     * @return True if item is in inventory menu, false otherwise
     */
    private boolean isIn(int indexInInventory)
    {
    	return itemsIn.contains(indexInInventory);
    }
    /**
     * Adds all player items into inventory menu 
     */
    private void addItems()
    {
    	for(Item item : player.getItems())
    	{
    	    for(ItemSlot[] slotsLine : slots)
    	    {
    	        for(ItemSlot slot : slotsLine)
    	        {
    	            if(slot.isNull() && !isIn(item.getNumber()))
    	            {
    	                itemsIn.add(item.getNumber());
    	                slot.insertContent(item);
    	                Log.addInformation(item.toString() + " added to inventory menu");
    	                break;
    	            }
    	                
    	        }
    	    }
    	}
    }
    /**
     * Returns dragged slot
     * @return Slot dragged by mouse
     */
    private ItemSlot getDragged()
    {
    	for(ItemSlot[] slotsLine : slots)
		{
			for(ItemSlot slot : slotsLine)
			{
				if(slot.isItemDragged())
				{
					return slot;
				}
			}
		}
    	
    	for(ItemSlot slot : eqSlots.slotsTable)
    	{
    		if(slot.isItemDragged())
    			return slot;
    	}
    	return null;
    }
    /**
     * Moves item from one slot to another given slot
     * @param draggedSlot Slot with item, after move operation internal item field becomes null
     * @param slotForItem New slot for item
     */
    private void moveItem(ItemSlot draggedSlot, ItemSlot slotForItem)
    {
    	try
    	{
    		if(!slotForItem.isNull())
        	{
        		Item tmpItem;
        		tmpItem = slotForItem.getContent();
        		slotForItem.insertContent(draggedSlot.getContent());
        		draggedSlot.insertContent(tmpItem);
        		draggedSlot.dragged(false);
        		slotForItem.dragged(false);
        	}
        	else
        	{
            	slotForItem.insertContent(draggedSlot.getContent());
            	draggedSlot.dragged(false);
            	draggedSlot.removeContent();
        	}
    	}
    	catch(NullPointerException e)
    	{
    		return;
    	}
    }
    
    private String[] getCharStats()
    {
    	return new String[]{player.getName(), "Level: " + player.getLevel(), "Experience: " + player.getExperience() + "/" + player.getMaxExperience(), "Health: " + player.getHealth(),
    			"Magicka: " + player.getMagicka(), "Haste: " + player.getHaste(), "Damage: " + player.getDamage()[0] + "-" + player.getDamage()[1], "Armor: " + player.getArmorRating(), "Strenght: " + player.getStr(), 
    			"Constitution: " + player.getCon(), "Dexterity: " + player.getDex(), "Inteligence: " + player.getInt(), "Wisdom: " + player.getWis()};
    }
    
    /**
     * Private inner class for equipment slots
     * @author Isangeles
     *
     */
    private class EquipmentSlots 
    {
    	private ItemSlot feet;
    	private ItemSlot hands;
    	private ItemSlot offhand;
    	private ItemSlot chest;
    	private ItemSlot head;
    	
    	private ItemSlot weapon;
    	
    	private ItemSlot finger;
    	private ItemSlot secFinger;
    	private ItemSlot neck;
    	private ItemSlot artifact;

		private ItemSlot[] slotsTable;
		
    	public EquipmentSlots(GameContainer gc) throws SlickException, IOException 
    	{
    		feet = new ItemSlot(gc);
    		hands = new ItemSlot(gc);
    		offhand = new ItemSlot(gc);
    		chest = new ItemSlot(gc);
    		head = new ItemSlot(gc);
    		
    		weapon = new ItemSlot(gc);
    		
    		finger = new ItemSlot(gc);
    		secFinger = new ItemSlot(gc);
    		neck = new ItemSlot(gc);
    		artifact = new ItemSlot(gc);
    		
    		slotsTable = new ItemSlot[]{feet, hands, offhand, chest, head, weapon, finger, secFinger, neck, artifact};
    	}
    	
    	public void draw(float x, float y)
    	{
    		feet.draw(x+getDis(196), y+getDis(242), false);
            weapon.draw(x+getDis(99), y+getDis(140), false);
            offhand.draw(x+getDis(201), y+getDis(137), false);
            hands.draw(x+getDis(85), y+getDis(91), false);
            chest.draw(x+getDis(150), y+getDis(67), false);
            head.draw(x+getDis(147), y+getDis(9), false);
            
            finger.draw(x+getDis(20), y+getDis(175), false);
            secFinger.draw(x+getDis(20), y+getDis(131), false);
            neck.draw(x+getDis(20), y+getDis(88), false);
            artifact.draw(x+getDis(20), y+getDis(45), false);
    	}
    	/**
    	 * Checks if specific slot is one of equipment slots
    	 * @param slot Item slot to check 
    	 * @return True if slot is one of equipment slots, false  otherwise
    	 */
    	public boolean contains(ItemSlot slot)
    	{
    		for(ItemSlot is : slotsTable)
    		{
    			if(slot == is)
    				return true;
    		}
    		
    		return false;
    	}
    	/**
    	 * Inserts item from specific slot to character equipment
    	 * @param slot Equippable item
    	 * @param eqSlot One of equipment item slots
    	 * @return True if item was successful inserted, false otherwise
    	 */
    	public boolean setEqItem(Item eqItem, ItemSlot eqSlot)
    	{
    		try
    		{
    			if(!isCompatible((Equippable)eqItem, eqSlot))
        			return false;
    		}
    		catch(ClassCastException e)
    		{
    			return false;
    		}
    		
    		if(eqSlot == weapon)
    			return player.setWeapon(eqItem);
    		if(eqSlot == feet || eqSlot == hands || eqSlot == chest || eqSlot == head)
    			return player.setArmor(eqItem);
    		if(eqSlot == finger || eqSlot == neck || eqSlot == artifact)
    			return player.setTrinket(eqItem);
    		   		
    		return false;
    	}
    	/**
    	 * Returns slot on which mouse is over
    	 * @return ItemSlot where isMouseOver true, null if not found such slot
    	 */
    	public ItemSlot getMouseOverSlot()
    	{
    		for(ItemSlot slot : slotsTable)
    		{
    			if(slot.isMouseOver())
    				return slot;
    		}
    		
    		return null;
    	}
    	/**
    	 * Removes item in specific slot from player equipment
    	 * @param slot One of equipment slots
    	 */
    	public void removeFromEq(ItemSlot slot)
    	{
    		for(ItemSlot is : slotsTable)
    		{
    			if(slot == is)
    			{
    				player.unequipp(slot.getContent());
    			}
    		}
    	}
    	/**
    	 * Checks if item is compatible with slot
    	 * @param item Equippable item
    	 * @param slot One of equipment slots
    	 * @return True if item and slot are compatible, false otherwise
    	 */
    	private boolean isCompatible(Equippable item, ItemSlot slot)
    	{
    		if(Armor.class.isInstance(item))
    		{
    			if(item.type() != Armor.FEET && slot == feet)
    				return false;
    			if(item.type() != Armor.HANDS && slot == hands)
    				return false;
    			if(item.type() != Armor.OFFHAND && slot == offhand)
    				return false;
    			if(item.type() != Armor.CHEST && slot == chest)
    				return false;
    			if(item.type() != Armor.HEAD && slot == head)
    				return false;
    		}
    		if(!Weapon.class.isInstance(item) && (slot == weapon || slot == offhand))
    			return false;
    		
    		if(Trinket.class.isInstance(item))
    		{
    			if(item.type() != Trinket.FINGER && slot == finger)
    				return false;
    			if(item.type() != Trinket.FINGER && slot == secFinger)
    				return false;
    			if(item.type() != Trinket.NECK && slot == neck)
    				return false;
    			if(item.type() != Trinket.ARTIFACT && slot == artifact)
    				return false;
    		}
    		
    		return true;
    	}
    }
}
