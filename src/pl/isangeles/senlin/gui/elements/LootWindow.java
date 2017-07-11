/*
 * LootWindow.java
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

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.gui.Button;
import pl.isangeles.senlin.gui.InterfaceObject;
import pl.isangeles.senlin.gui.Slot;
/**
 * Class for loot window
 * TODO disable items dragging, fix for multiple pages
 * @author Isangeles
 *
 */
class LootWindow extends InterfaceObject implements UiElement, MouseListener, KeyListener
{
	private Character lootingChar;
	private Targetable lootedChar;
	private TrueTypeFont ttf;
	private Button takeAll;
	private Button takeGold;
	private SlotsPages slotsP;
	private int pageIndex;
	private boolean openReq;
	/**
	 * Loot window constructor
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public LootWindow(GameContainer gc, Character player) throws SlickException, IOException, FontFormatException
	{
		super(GConnector.getInput("ui/background/lootBG.png"), "uiLoot", false, gc);
		gc.getInput().addMouseListener(this);
		gc.getInput().addKeyListener(this);
		this.gc = gc;
		lootingChar = player;
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		ttf = new TrueTypeFont(font.deriveFont(12f), true);
		
		takeAll = new Button(GConnector.getInput("button/buttonS.png"), "uiLootB1S", false, "Take all",  gc);
		takeGold = new Button(GConnector.getInput("button/buttonS.png"), "uiLootB2S", false, "Take gold",  gc);
		
		ItemSlot[][] isTab = new ItemSlot[8][6]; 
		for(int i = 0; i < isTab.length; i ++)
		{
			for(int j = 0; j < isTab[i].length; j ++)
			{
				isTab[i][j] = new ItemSlot(gc);
			}
		}
		slotsP = new SlotsPages(isTab, gc);
	}
	
	@Override
	public void draw(float x, float y)
	{
		super.draw(x, y, false);
		//Text
		String windowTitle = lootedChar.getName() + " " + "loot";
		String aoGold = "Gold: " + lootedChar.getInventory().getGold();
		ttf.drawString(x+((getScaledWidth()/2)-ttf.getWidth(windowTitle)), y, windowTitle);
		ttf.drawString(x+((getScaledWidth()/2)-ttf.getWidth(aoGold)), y+getDis(30), aoGold);
		//Slots
		slotsP.draw(x+getDis(15), y+getDis(70), false);
		//Buttons
		takeAll.draw(x+getDis(159), y+getDis(350), false);
		takeGold.draw(x+getDis(70), y+getDis(350), false);
	}
	
	@Override
	public void update() 
	{
		slotsP.update();
	}
	/**
	 * Resets loot window to default state
	 */
	@Override
	public void reset() 
	{
		super.moveMOA(Coords.getX("BR", 0), Coords.getY("BR", 0));
		lootedChar = null;
		pageIndex = 0;
		clearSlots();
	}
	/**
	 * Opens loot window
	 * @param characterToLoot Character with loot to display 
	 * @throws SlickException
	 * @throws IOException
	 */
	public void open(Targetable characterToLoot)
	{
		if(openReq == false)
		{
	        lootedChar = characterToLoot;
		    if(lootingChar.getRangeFrom(lootedChar.getPosition()) < 40)
	        {
		        clearSlots();
	            loadLoot();
	            openReq = true;
	        }
	        else
	            lootingChar.moveTo(lootedChar.getPosition()[0], lootedChar.getPosition()[1]);
		}
	}
	/**
	 * Closes and resets loot window
	 */
	public void close()
	{
		openReq = false;
		lootingChar.looting(false);
		reset();
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
		Slot slot = slotsP.getDragged();
		if(slot != null)
			slot.dragged(false);
	}

	@Override
	public void mouseReleased(int button, int x, int y) 
	{
		if(button == Input.MOUSE_LEFT_BUTTON)
		{
			if(takeAll.isMouseOver())
			{
				List<Item> itemsToTake = new ArrayList<>();
				for(Item lootedItem : lootedChar.getInventory())
				{
					lootingChar.getInventory().add(lootedItem);
					itemsToTake.add(lootedItem);
				}
				lootedChar.getInventory().removeAll(itemsToTake);
				
				lootingChar.addGold(lootedChar.getInventory().takeGold(lootedChar.getInventory().getGold()));
				close();
			}
			if(takeGold.isMouseOver())
			{
				lootingChar.addGold(lootedChar.getInventory().takeGold(lootedChar.getInventory().getGold()));
				close();
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
		if(key == Input.KEY_ESCAPE)
			close();
	}

	@Override
	public void keyReleased(int key, char c) 
	{
	}
	/**
	 * Loads all items from looted character to window
	 * @throws SlickException
	 * @throws IOException
	 */
	private void loadLoot()
	{
		slotsP.insertContent(lootedChar.getInventory().asSlotsContent());
	}
	
	private void clearSlots()
	{
        slotsP.clear();
	}
}
