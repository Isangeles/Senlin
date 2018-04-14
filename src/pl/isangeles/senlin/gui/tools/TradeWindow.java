/*
 * TradeWindow.java
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
package pl.isangeles.senlin.gui.tools;

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

import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.gui.Button;
import pl.isangeles.senlin.gui.InterfaceObject;
import pl.isangeles.senlin.gui.Slot;
import pl.isangeles.senlin.gui.SlotContent;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
/**
 * Class for UI trade window 
 * @author Isangeles
 *
 */
class TradeWindow extends InterfaceObject implements UiElement, MouseListener
{
	private Character buyer;
	private Character trader;
	private TrueTypeFont ttf;
	private Button trade;
	private Button exit;
	private SlotsPages slotsBuy;
	private SlotsPages slotsSell;
	private List<Item> traderAssortment = new ArrayList<>();
	private List<Item> buyerAssortment = new ArrayList<>();
	private List<Item> itemsToSell = new ArrayList<>();
	private List<Item> itemsToBuy = new ArrayList<>();
	private int buyValue;
	private int sellValue;
	private boolean openReq;
	/**
	 * Trade window constructor
	 * @param gc Slick game container
	 * @param player Player character
	 * @throws SlickException 
	 * @throws IOException
	 * @throws FontFormatException 
	 */
	public TradeWindow(GameContainer gc, Character player) throws SlickException, IOException, FontFormatException 
	{
		super(GConnector.getInput("ui/background/tradeBG.png"), "uiTradeBg", false, gc);
		gc.getInput().addMouseListener(this);
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		ttf = new TrueTypeFont(font.deriveFont(12f * getScale()), true);
		
		trade = new Button(GConnector.getInput("button/buttonS.png"), "uiTradeBTrade", false, "", gc);
		exit = new Button(GConnector.getInput("button/buttonS.png"), "uiTradeBExit", false, TConnector.getText("ui", "uiClose"), gc);
		
		ItemSlot[][] itemsToBuy = new ItemSlot[6][6];
		for(int i = 0; i < itemsToBuy.length; i ++)
		{
			for(int j = 0; j < itemsToBuy[i].length; j ++)
			{
				itemsToBuy[i][j] = new ItemSlot(gc);
			}
		}
		slotsBuy = new SlotsPages(itemsToBuy, gc);
		
		ItemSlot[][] itemsToSell = new ItemSlot[6][6];
		for(int i = 0; i < itemsToSell.length; i ++)
		{
			for(int j = 0; j < itemsToSell[i].length; j ++)
			{
				itemsToSell[i][j] = new ItemSlot(gc);
			}
		}
		slotsSell = new SlotsPages(itemsToSell, gc);
		
		buyer = player;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.inter.InterfaceObject#update(float, float, boolean)
	 */
	@Override
	public void draw(float x, float y)
	{
		super.draw(x, y, false);
		//Text
		String windowTitle = trader.getName() + " " + "assortment";
		//String aoGold = "Gold: " + trader.getInventory().getGold();
		ttf.drawString(x+((getScaledWidth()/2)-ttf.getWidth(windowTitle)), y, windowTitle);
		//ttf.drawString(x+((getScaledWidth()/2)-ttf.getWidth(aoGold)), y+getDis(30), aoGold);
		//Slots
		slotsBuy.draw(x+getDis(8), y+getDis(72), false);
		slotsSell.draw(x+getDis(322), y+getDis(72), false);
		//Buttons
		trade.draw(x+getDis(107), y+getDis(350), false);
		exit.draw(x+getDis(422), y+getDis(350), false);
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.inter.ui.UiElement#update()
	 */
	@Override
	public void update() 
	{	   
	    int tradeValue = sellValue - buyValue;
		   
	    trade.setLabel("Trade: " + tradeValue + " gold");
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.inter.ui.UiElement#reset()
	 */
	@Override
	public void reset() 
	{
		moveMOA(Coords.getX("BR", 0), Coords.getY("BR", 0));
		trader = null;
		slotsBuy.clear();
		slotsSell.clear();
		traderAssortment.clear();
		buyerAssortment.clear();
		resetTrade();
	}
	/**
	 * Opens window and starts trade
	 * @param trader Game character to trade with
	 */
	public void open(Character trader)
	{
		this.trader = trader;
		if(!openReq)
		{
			if(buyer.getRangeFrom(trader.getPosition()) < 40)
			{
				loadAssortment();
				openReq = true;
			}
		}
		else
			close();
	}
	/**
	 * Closes window
	 */
	public void close()
	{
		openReq = false;
		reset();
	}
	/**
	 * Checks if window open is requested
	 * @return True if window should be opened, false otherwise
	 */
	public boolean isOpenReq()
	{
		return openReq;
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#inputEnded()
	 */
	@Override
	public void inputEnded() 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#inputStarted()
	 */
	@Override
	public void inputStarted() 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#isAcceptingInput()
	 */
	@Override
	public boolean isAcceptingInput() 
	{
		return true;
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#setInput(org.newdawn.slick.Input)
	 */
	@Override
	public void setInput(Input input) 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseClicked(int, int, int, int)
	 */
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseDragged(int, int, int, int)
	 */
	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseMoved(int, int, int, int)
	 */
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mousePressed(int, int, int)
	 */
	@Override
	public void mousePressed(int button, int x, int y) 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseReleased(int, int, int)
	 */
	@Override
	public void mouseReleased(int button, int x, int y) 
	{
		if(openReq)
		{
			if(button == Input.MOUSE_RIGHT_BUTTON)
			{
				Slot slotBuy = slotsBuy.getMouseOver();
				if(slotBuy != null && !slotBuy.isEmpty())
				{
				    for(SlotContent content : slotBuy.getContent())
				    {
				    	Item itemBuy = (Item)content;
					    if(itemsToBuy.contains(itemBuy))
					    {
					        itemsToBuy.remove(itemBuy);
					        buyValue -= itemBuy.getValue();
					        slotBuy.click(false);
					    }
					    else
					    {
					        itemsToBuy.add(itemBuy);
					        buyValue += itemBuy.getValue();
			                slotBuy.click(true);
					    }
				    }
				}
				
				Slot slotSell = slotsSell.getMouseOver();
				if(slotSell != null && !slotSell.isEmpty())
				{
				    for(SlotContent content : slotSell.getContent())
				    {
				    	Item itemSell = (Item)content;
					    if(itemsToSell.contains(itemSell))
					    {
					        itemsToSell.remove(itemSell);
					        sellValue -= itemSell.getValue();
			                slotSell.click(false);
					    }
					    else
					    {
					        itemsToSell.add(itemSell);
					        sellValue += itemSell.getValue();
			                slotSell.click(true);
					    }
				    }
				}
			}
			if(button == Input.MOUSE_LEFT_BUTTON)
			{
			    if(exit.isMouseOver())
			        close();
			    if(trade.isMouseOver())
		        {
			    	trade();
			    	resetTrade();
					loadAssortment();
	            }
			    if(slotsBuy.getDragged() != null)
			    {
			    	Slot dSlot = slotsBuy.getDragged();
			    	Slot moSlot = slotsBuy.getMouseOver();
			    	if(moSlot != null)
			    		moveItem(dSlot, moSlot);
			    }
			    if(slotsSell.getDragged() != null)
			    {
			    	Slot dSlot = slotsSell.getDragged();
			    	Slot moSlot = slotsSell.getMouseOver();
			    	if(moSlot != null)
			    		moveItem(dSlot, moSlot);
			    }
			}
		}
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseWheelMoved(int)
	 */
	@Override
	public void mouseWheelMoved(int change) 
	{
	}
	/**
	 * Loads all unequipped items as assortment for both trading characters 
	 */
	private void loadAssortment()
	{
		traderAssortment = trader.getInventory().getWithoutEq();
		for(Item item : traderAssortment)
		{
			slotsBuy.insertContent(item);
		}
		
		buyerAssortment = buyer.getInventory().getWithoutEq();
		for(Item item : buyerAssortment)
		{
		    slotsSell.insertContent(item);
		}
	}
	/**
	 * Tries to trade items and gold over both characters
	 * @return True if trade was successfully, false otherwise
	 */
	private boolean trade()
	{
		int tradeValue = buyValue - sellValue;
		if(((trader.getInventory().getCashValue()+buyValue)-sellValue) >= 0 && ((buyer.getInventory().getCashValue()+sellValue)-buyValue) >= 0)
		{
			if(tradeValue <= 0)
			{
				   deal();
				   return true;
			}
			else 
				return false;
		}
		else
			return false;
	}
	/**
	 * Completes trade over both characters
	 */
	private void deal()
	{
		//buyer.addGold(sellValue);
        //trader.getInventory().takeGold(sellValue);
        //buyer.getInventory().takeGold(buyValue);
        //trader.addGold(buyValue);
	    
	    for(Item item : itemsToBuy)
	    {
            item.getTile().click(false);
	        buyer.addItem(item);
	        trader.getInventory().remove(item);
	    }
	    for(Item item : itemsToSell)
	    {
	        item.getTile().click(false);
	        trader.addItem(item);
	        buyer.getInventory().remove(item);
	    }
	}
	/**
	 * Resets trade
	 */
	private void resetTrade()
	{
	    for(Item item : itemsToSell)
	    {
	        item.getTile().click(false);
	    }
	    for(Item item : itemsToBuy)
        {
            item.getTile().click(false);
        }
	    itemsToSell.clear();
	    itemsToBuy.clear();
	    slotsSell.clear();
	    slotsBuy.clear();
	    sellValue = 0;
	    buyValue = 0;
	}
    /**
     * Moves all dragged items from one slot to another specified slot
     * @param draggedSlot Slot with dragged items
     * @param slotForItem New slot for dragged items
     */
    private void moveItem(Slot draggedSlot, Slot slotForItem)
    {
    	if(draggedSlot == slotForItem)
		{
			draggedSlot.dragged(false);
			return;
		}
		if(!slotForItem.isEmpty())
    	{
    		if(slotForItem.insertContent(draggedSlot.getDraggedContent()))
    		{
    			draggedSlot.removeContent(draggedSlot.getDraggedContent());
    		}
    		else
    		{
    			/*
        		List<Item> contentMem = slotForItem.getContent();
    			slotForItem.removeContent();
    			slotForItem.insertContent(draggedSlot.getDraggedContent());
    			draggedSlot.removeContent(draggedSlot.getDraggedContent());
    			draggedSlot.insertContent(contentMem);
    			*/
    		}
    		draggedSlot.dragged(false);
    		slotForItem.dragged(false);
    	}
    	else
    	{
        	slotForItem.insertContent(draggedSlot.getDraggedContent());
        	draggedSlot.removeContent(draggedSlot.getDraggedContent());
        	draggedSlot.dragged(false);
    		slotForItem.dragged(false);
    	}
    }
}
