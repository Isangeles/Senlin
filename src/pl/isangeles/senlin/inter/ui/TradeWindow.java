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
package pl.isangeles.senlin.inter.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.inter.Button;
import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
/**
 * Class for UI trade window 
 * @author Isangeles
 *
 */
public class TradeWindow extends InterfaceObject implements UiElement, MouseListener
{
	private Character buyer;
	private Character trader;
	private TrueTypeFont ttf;
	private Button trade;
	private Button exit;
	private Button nextBuy;
	private Button prevBuy;
	private Button nextSell;
	private Button prevSell;
	private SlotsBlock slotsBuy;
	private SlotsBlock slotsSell;
	private List<Item> traderAssortment = new ArrayList<>();
	private List<Item> buyerAssortment = new ArrayList<>();
	private List<Item> itemsToSell = new ArrayList<>();
	private List<Item> itemsToBuy = new ArrayList<>();
	private boolean openReq;
	/**
	 * Trade window constructor
	 * @param fileInput
	 * @param ref
	 * @param flipped
	 * @param gc
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
		exit = new Button(GConnector.getInput("button/buttonS.png"), "uiTradeBExit", false, "close", gc);
		nextBuy = new Button(GConnector.getInput("button/buttonNext.png"), "uiTradeBNext", false, "", gc);
		prevBuy = new Button(GConnector.getInput("button/buttonBack.png"), "uiTradeBPrev", false, "", gc);
		nextSell = new Button(GConnector.getInput("button/buttonNext.png"), "uiTradeBNext", false, "", gc);
		prevSell = new Button(GConnector.getInput("button/buttonBack.png"), "uiTradeBPrev", false, "", gc);
		
		ItemSlot[][] itemsToBuy = new ItemSlot[8][6];
		for(int i = 0; i < itemsToBuy.length; i ++)
		{
			for(int j = 0; j < itemsToBuy[i].length; j ++)
			{
				itemsToBuy[i][j] = new ItemSlot(gc);
			}
		}
		slotsBuy = new SlotsBlock(itemsToBuy);
		
		ItemSlot[][] itemsToSell = new ItemSlot[8][6];
		for(int i = 0; i < itemsToSell.length; i ++)
		{
			for(int j = 0; j < itemsToSell[i].length; j ++)
			{
				itemsToSell[i][j] = new ItemSlot(gc);
			}
		}
		slotsSell = new SlotsBlock(itemsToSell);
		
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
		String aoGold = "Gold: " + trader.getInventory().getGold();
		ttf.drawString(x+((getScaledWidth()/2)-ttf.getWidth(windowTitle)), y, windowTitle);
		ttf.drawString(x+((getScaledWidth()/2)-ttf.getWidth(aoGold)), y+getDis(30), aoGold);
		//Slots
		slotsBuy.draw(x+getDis(8), y+getDis(72));
		slotsSell.draw(x + getDis(322), y + getDis(72));
		//Buttons
		nextBuy.draw(x+getDis(0), y+getDis(350), false);
		prevBuy.draw(x+getDis(243), y+getDis(350), false);
		nextSell.draw(x+getDis(322), y+getDis(350), false);
		prevSell.draw(x+getDis(565), y+getDis(350), false);
		trade.draw(x+getDis(107), y+getDis(350), false);
		exit.draw(x+getDis(422), y+getDis(350), false);
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.inter.ui.UiElement#update()
	 */
	@Override
	public void update() 
	{
		// TODO Auto-generated method stub

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
		traderAssortment.clear();
	}
	
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
	
	public void close()
	{
		openReq = false;
		reset();
	}
	
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
		if(button == Input.MOUSE_RIGHT_BUTTON)
		{
			
		}
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseWheelMoved(int)
	 */
	@Override
	public void mouseWheelMoved(int change) 
	{
	}
	
	private void loadAssortment()
	{
		traderAssortment = trader.getInventory().getWithoutEq();
		for(Item item : traderAssortment)
		{
			slotsBuy.insertContent(item);
		}
	}
}
