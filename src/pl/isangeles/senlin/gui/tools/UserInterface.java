/*
 * UserInterface.java
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

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

import pl.isangeles.senlin.states.GameWorld;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.cli.CommandInterface;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.character.Attitude;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.gui.GameCursor;
import pl.isangeles.senlin.gui.UiLayout;
import pl.isangeles.senlin.gui.Warning;
import pl.isangeles.senlin.gui.tools.*;
/**
 * Class for game graphical interface
 * @author Isangeles
 *
 */
public class UserInterface implements MouseListener, SaveElement
{
    private Character player;
    private GameWorld gw;
    private static GameCursor cursor;
    private Console gameConsole;
    private BottomBar bBar;
    private CharacterFrame charFrame;
    private CharacterFrame targetFrame;
    private InGameMenu igMenu;
    private InventoryMenu inventory;
    private SkillsMenu skills;
    private CraftingMenu crafting;
    private JournalMenu journal;
    private LootWindow loot;
    private TradeWindow trade;
    private TrainingWindow train;
    private DialogBox dialogue;
    private CastBar cast;
    private SaveGameWindow save;
    private LoadGameWindow load;
    private SettingsMenu settings;
    private ConditionsInfo conditions;
    private DestinationPoint point;
    private Camera camera;
    private Warning uiWarning;
    /**
     * UI constructor, calls all UI elements constructors
     * @param gc Game container for superclass and ui elements
     * @param player Player character 
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    public UserInterface(GameContainer gc, CommandInterface cli, Character player, GameWorld gw) throws SlickException, IOException, FontFormatException
    {
        this.player = player;
        this.gw = gw;
        gc.getInput().addMouseListener(this);
        
        //cursor = new GameCursor(gc);
        gameConsole = new Console(gc, cli, player);
        charFrame = new CharacterFrame(gc, player);
        targetFrame = new CharacterFrame(gc, player);
        igMenu = new InGameMenu(gc);
        inventory = new InventoryMenu(gc, player);
        skills = new SkillsMenu(gc, player);
        crafting = new CraftingMenu(gc, player);
        journal = new JournalMenu(gc, player, gw);
        loot = new LootWindow(gc, player);
        trade = new TradeWindow(gc, player);
        train = new TrainingWindow(gc, player);
        dialogue = new DialogBox(gc);
        cast = new CastBar(gc, player);
        save = new SaveGameWindow(gc);
        load = new LoadGameWindow(gc);
        settings = new SettingsMenu(gc);
        bBar = new BottomBar(gc, igMenu, inventory, skills, journal, crafting, player);
        conditions = new ConditionsInfo(gc, gw);
        point = new DestinationPoint(gc, player);
        camera = new Camera();
        
        uiWarning = new Warning(gc);
    }
    /**
     * Draws ui elements
     */
    public void draw(Graphics g)
    {
    	point.draw();
        gameConsole.draw(Coords.getX("TR", gameConsole.getWidth()+10), Coords.getY("BR", gameConsole.getHeight()+20), g);
        conditions.draw(Coords.getX("BL", 50), Coords.getY("BL", 50));
        bBar.draw(Coords.getX("BL", 200), Coords.getY("BL", 70));
        charFrame.draw(Coords.getX("TL", 10), Coords.getY("TL", 10));
        cast.draw(Coords.getX("CE", -200), Coords.getY("CE", 200));
        
        if(player.getTarget() != null)
        	targetFrame.draw(Coords.getX("CE", 0), Coords.getY("TR", 0));
        
        if(inventory.isOpenReq())
            inventory.draw(Coords.getX("CE", -500), Coords.getY("CE", -200));
        
        if(skills.isOpenReq())
        	skills.draw(Coords.getX("CE", -500), Coords.getY("CE", -200));
        
        if(journal.isOpenReq())
        	journal.draw(Coords.getX("CE", -500), Coords.getY("CE", -200));
        
        if(loot.isOpenReq())
        	loot.draw(Coords.getX("CE", -100), Coords.getY("CE", -100));
        
        if(trade.isOpenReq())
        	trade.draw(Coords.getX("CE", -100), Coords.getY("CE", -100));
        
        if(train.isOpenReq())
        	train.draw(Coords.getX("CE", -100), Coords.getY("CE", -100));
        
        if(dialogue.isOpenReq())
        	dialogue.draw(Coords.getX("CE", -100), Coords.getY("CE", -100));
        
        if(igMenu.isOpenReq())
        	igMenu.draw(Coords.getX("CE", -100), Coords.getY("CE", -100));
        
        if(crafting.isOpenReq())
        	crafting.draw(Coords.getX("CE", -500), Coords.getY("CE", -200));
        	
        if(save.isOpenReq())
        	save.draw(Coords.getX("CE", -100), Coords.getY("CE", -100), g);
        
        if(load.isOpenReq())
            load.draw(Coords.getX("CE", -100), Coords.getY("CE", -100), g);
        
        if(settings.isOpenReq())
        	settings.draw(Coords.getX("CE", -100), Coords.getY("CE", -100));
        
        
        //cursor.draw();   	
    }
    /**
     * Updates ui elements
     */
    public void update()
    {
    	if(player.getTarget() != null)
    	{
    		targetFrame.setCharacter(player.getTarget());
    		if(player.isLooting())
    		{
    			loot.open(player.getTarget());
    		}
    	}
    	if(dialogue.isTradeReq())
    	{
    		trade.open((Character)player.getTarget());
    		dialogue.tradeReq(false);
    	}
    	if(dialogue.isTrainReq())
    	{
    		train.open(((Character)player.getTarget()));
    		dialogue.trainReq(false);
    	}
    		
    	if(igMenu.isSaveReq())
    	{
    		save.open();
    		hideMenu();
    	}
    	if(igMenu.isLoadReq())
    	{
    	    load.open();
    	    hideMenu();
    	}
    	if(igMenu.isSettingsReq())
    	{
    		settings.open();
    		hideMenu();
    	}
    	
    	bBar.update();
        charFrame.update();
        cast.update();
        targetFrame.update();
        inventory.update();
        skills.update();
        crafting.update();
        journal.update();
        loot.update();
        trade.update();
        train.update();
        dialogue.update();
        save.update();
        load.update();
        settings.update();
        gameConsole.update();
        conditions.update();
    }
    /**
     * Checks if mouse is over one of ui elements
     * @return
     */
    public boolean isMouseOver()
    {
    	return bBar.isMouseOver() || igMenu.isMouseOver() || charFrame.isMouseOver() || inventory.isMouseOver() || skills.isMouseOver() ||
    		   journal.isMouseOver() || loot.isMouseOver() || dialogue.isMouseOver() || trade.isMouseOver() || train.isMouseOver() || 
    		   save.isMouseOver() || load.isMouseOver() || settings.isMouseOver() || crafting.isMouseOver();
    }
    /**
     * Checks if exit game is requested
     * @return True if game should be closed or false otherwise
     */
    public boolean isExitReq()
    {
    	return igMenu.isExitReq();
    }
    /**
     * Checks if game pause is requested
     * @return True if game should be paused or false otherwise
     */
    public boolean isPauseReq()
    {
        return !gameConsole.isHidden() || bBar.isPauseReq();
    }
    
    public boolean takeSaveReq()
    {
    	return save.takeSaveReq();
    }
    
    public boolean takeLoadReq()
    {
        return load.takeLoadReq();
    }
    
    public String getSaveName()
    {
    	return save.getSaveName();
    }
    
    public String getLoadName()
    {
        return load.getSaveName();
    }
    
    public Camera getCamera()
    {
    	return camera;
    }
    /**
     * Sets specified UI layout
     * @param layout UI layout to set
     */
    public void setLayout(UiLayout layout)
    {
    	bBar.loadLayout(layout.getBarLayout());
    	inventory.loadLayout(layout.getInvLayouyt());
    	camera.setPos(layout.getCameraPos());
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
			Targetable target = player.getTarget();
			if(target != null)
			{
				try
				{
					if(target.isMouseOver())
					{
						if(target.isLive())
						{
		                    Character targetedChar = (Character)target;
							switch(targetedChar.getAttitudeTo(player))
							{
							case FRIENDLY:
								dialogue.open(player, targetedChar);
								break;
							case HOSTILE:
								player.enterCombat(target);
								break;
							case NEUTRAL:
								player.enterCombat(target);
								break;
							case DEAD:
								break;
							}
						}
						else
						{
							player.startLooting(target);
						}
					}
					else
					{
						player.stopCombat();
					}
				}
				catch(ClassCastException | NullPointerException e)
				{
					Log.addSystem("ui_mcheck_fail!msg///"+e);
					e.printStackTrace();
					return;
				}
			}
		}
	}
	@Override
	public void mouseWheelMoved(int change) 
	{
	}
	
	public static GameCursor getUiCursor()
	{
		return cursor;
	}
    /**
     * Saves UI configuration to XML document element
     * @param doc Document for save game file
     * @return XML document element
     */
	public Element getSave(Document doc)
	{
		Element uiE = doc.createElement("ui");
		uiE.appendChild(inventory.getSave(doc));
		uiE.appendChild(bBar.getSave(doc));
		uiE.appendChild(camera.getSave(doc));
		return uiE;
	}
	
	private void hideMenu()
	{
		igMenu.close();
	}
    
}
