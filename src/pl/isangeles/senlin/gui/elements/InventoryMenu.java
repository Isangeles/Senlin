/*
 * InventoryMenu.java
 * 
 * Copyright 2017 Dariusz Sikora <darek@darek-PC-LinuxMint18>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */
package pl.isangeles.senlin.gui.elements;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.action.ActionType;
import pl.isangeles.senlin.core.item.Armor;
import pl.isangeles.senlin.core.item.Equippable;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.item.Trinket;
import pl.isangeles.senlin.core.item.Weapon;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.gui.InterfaceObject;
import pl.isangeles.senlin.gui.Slot;
/**
 * Graphical representation of character inventory
 * TODO this UI element is terrible slow 
 * @author Isangeles
 *
 */
class InventoryMenu extends InterfaceObject implements UiElement, SaveElement, MouseListener
{
	private Character player;
	private SlotsBlock slots;
	private EquipmentSlots eqSlots;
	private Map<String, Integer[]> layoutToLoad;
	private List<Item> itemsIn = new ArrayList<>();
	private TrueTypeFont textTtf;
	private boolean openReq;
	/**
	 * Inventory constructor 
	 * @param gc Game container 
	 * @param player Player character with inventory to represent
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException 
	 */
    public InventoryMenu(GameContainer gc, Character player) throws SlickException, IOException, FontFormatException
    {
        super(GConnector.getInput("ui/background/inventoryBG.png"), "uiInventoryBg", false, gc);
        this.player = player;
        gc.getInput().addMouseListener(this);
        
        File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        textTtf = new TrueTypeFont(font.deriveFont(12f), true);
        
        Slot[][] slotsT = new ItemSlot[5][20];
        for(int i = 0; i < 5; i ++)
        {
        	for(int j = 0; j < 20; j ++)
        	{
        		slotsT[i][j] = new ItemSlot(gc);
        	}
        }
        
        slots = new SlotsBlock(slotsT);
        
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
    	slots.draw(firstSlotX, firstSlotY);
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
    
    public void open()
    {
    	openReq = true;
    }
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.elements.UiElement#close()
	 */
	@Override
	public void close()
	{
		openReq = false;
		reset();
	}
    /**
     * Updates inventory menu
     */
    public void update()
    {
        if(player.getInventory().isMod())
        {
        	itemsIn.clear();
        	slots.clear();
        	player.getInventory().updated();
        }
        if(layoutToLoad != null)
        {
        	setLayout(layoutToLoad);
        	layoutToLoad = null;
        }
        else
            addItems();
    }
    
    public boolean isOpenReq()
    {
    	return openReq;
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
		return openReq;
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
			if(getDragged() != null)
			{
				ItemSlot draggedSlot = getDragged();
				ItemSlot slotOver = (ItemSlot)slots.getMouseOver();
				if(slotOver != null)
				{
					if(eqSlots.contains(draggedSlot))
						eqSlots.removeFromEq(draggedSlot);
					moveItem(draggedSlot, slotOver);
					return;
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
		if(button == Input.MOUSE_RIGHT_BUTTON)
		{
			if(slots.getMouseOver() != null)
			{
				Item item = (Item)slots.getMouseOver().getContent();
				if(item != null)
				{
					if(item.use(player, player.getTarget()))
					{
						if(item.getActionType() == ActionType.EQUIP)
						{
							moveItem((ItemSlot)slots.getMouseOver(), eqSlots.getSlotFor((Equippable)item));
						}
					}
					
				}
			}
		}
	}
	@Override
	public void mouseWheelMoved(int change) 
	{
	}
    /**
     * Saves inventory menu configuration to XML document element
     * @param doc Document for save game file
     * @return XML document element
     */
	public Element getSave(Document doc)
	{
		Element inventoryE = doc.createElement("inventory");
		
		Element slotsE = doc.createElement("slots");
		for(Slot[] line : slots.getSlots())
		{
			for(Slot slot : line)
			{
				if(!slot.isEmpty())
				{
					Element slotE = doc.createElement("slot");
					int[] slotPos = slots.getSlotPos(slot);
					slotE.setAttribute("id", slotPos[0] + "," + slotPos[1]);
					slotE.setTextContent(slot.getContent().getSerialId());
					slotsE.appendChild(slotE);
				}
			}
		}
		inventoryE.appendChild(slotsE);
		
		return inventoryE;
	}
	
	public void loadLayout(Map<String, Integer[]> layout)
	{
		layoutToLoad = layout;
	}
    /**
     * Adds all player items into inventory menu 
     */
    private void addItems()
    {
    	for(Item item : player.getInventory())
    	{
    		if(!itemsIn.contains(item))
            {
                itemsIn.add(item);

                if(!player.getInventory().isEquipped(item))
                {
                    if(slots.insertContent(item))
                        Log.addInformation(item.toString() + " added to inventory menu");
                }
                	
                break;
            }
    	}
    }
    /**
     * Sets specified layout
     * @param layout Map with items serial IDs as keys and slots positions as values
     */
    private void setLayout(Map<String, Integer[]> layout)
    {
    	for(String id : layout.keySet())
    	{
    		Slot slot = slots.getSlotOn(layout.get(id)[0], layout.get(id)[1]);
    		Item item = player.getInventory().getItemBySerial(id);
    		if(slot != null && item != null && slot.insertContent(item))
    		{
    			itemsIn.add(item);
    		}
    	}
    }
    /**
     * Returns dragged slot
     * @return Slot dragged by mouse
     */
    private ItemSlot getDragged()
    {
    	ItemSlot nSlot = (ItemSlot)slots.getDragged();
    	if(nSlot != null)
    		return nSlot;
    	
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
    		if(!slotForItem.isEmpty())
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
    /**
     * Builds and returns string with various player statistics
     * @return String with player statistics
     */
    private String[] getCharStats()
    {
    	return new String[]{player.getName(), TConnector.getText("ui", "levelName") + ": " + player.getLevel(), 
    			TConnector.getText("ui", "expName") + ": " + player.getExperience() + "/" + player.getMaxExperience(), TConnector.getText("ui", "guildName") + ": " + player.getGuild().toString(), 
    			TConnector.getText("ui", "hpName") + ": " + player.getHealth(), TConnector.getText("ui", "manaName") + ": " + player.getMagicka(), TConnector.getText("ui", "lpName") + ": " + player.getLearnPoints(),
    			TConnector.getText("ui", "hastName") + ": " + player.getHaste(), TConnector.getText("ui", "dmgName") + ": " + player.getDamage()[0] + "-" + player.getDamage()[1], 
    			TConnector.getText("ui", "armRat") + ": " + player.getArmorRating(), TConnector.getText("ui", "dodgeCh") + ": " + player.getDodgeChance() + "%", 
    			TConnector.getText("ui", "attStrName") + ": " + player.getStr(), TConnector.getText("ui", "attConName") + ": " + player.getCon(), 
    			TConnector.getText("ui", "attDexName") + ": " + player.getDex(), TConnector.getText("ui", "attIntName") + ": " + player.getInt(), 
    			TConnector.getText("ui", "attWisName") + ": " + player.getWis()};
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
		
    	public EquipmentSlots(GameContainer gc) throws SlickException, IOException, FontFormatException 
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

    		if(eqSlot == weapon && Weapon.class.isInstance(eqItem))
    			return player.equipItem(eqItem);
    		if((eqSlot == feet || eqSlot == hands || eqSlot == chest || eqSlot == head) && Armor.class.isInstance(eqItem))
    			return player.equipItem(eqItem);
    		if((eqSlot == finger || eqSlot == neck || eqSlot == artifact) && Trinket.class.isInstance(eqItem))
    			return player.equipItem(eqItem);
    		
    		return false;
    	}
    	/**
    	 * Inserts item to one of equipment slots
    	 * @param slot Equippable item
    	 * @return True if item was successful inserted, false otherwise
    	 */
    	public boolean insertItem(Equippable eqItem)
    	{
    		ItemSlot eqSlot = getSlotFor(eqItem);
    		
    		return eqSlot.insertContent(eqItem);
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
    	/**
    	 * Returns slot for specific equippable item
    	 * @param item Equippable item
    	 * @return ItemsSlot for this specific item, or null
    	 */
    	public ItemSlot getSlotFor(Equippable item)
    	{
    		if(Weapon.class.isInstance(item))
    		{
    			return weapon;
    		}
    		
    		if(Armor.class.isInstance(item))
    		{
    			switch(item.type())
    			{
    			case Armor.FEET:
    				return feet;
    			case Armor.HANDS:
    				return hands;
    			case Armor.CHEST:
    				return chest;
    			}
    		}
    		if(Trinket.class.isInstance(item))
    		{
    			switch(item.type())
    			{
    			case Trinket.ARTIFACT:
    				return artifact;
    			case Trinket.FINGER:
    				return finger;
    			case Trinket.NECK:
    				return neck;
    			}
    		}
    		
    		return null;
    	}
    }


}
