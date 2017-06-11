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
package pl.isangeles.senlin.inter.ui;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.inter.GameCursor;
import pl.isangeles.senlin.inter.Warning;
import pl.isangeles.senlin.inter.ui.*;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.core.Attitude;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.data.Log;
/**
 * Class containing all ui elements
 * @author Isangeles
 *
 */
public class UserInterface implements MouseListener
{
    private Character player;
    private Console gameConsole;
    private BottomBar bBar;
    private CharacterFrame charFrame;
    private CharacterFrame targetFrame;
    private InGameMenu igMenu;
    private InvetoryMenu inventory;
    private SkillsMenu skills;
    private JournalMenu journal;
    private LootWindow loot;
    private TradeWindow trade;
    private DialogBox dialogue;
    private CastBar cast;
    private Warning uiWarning;
    /**
     * UI constructor, calls all ui elements constructors
     * @param gc Game container for superclass and ui elements
     * @param player Player character 
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    public UserInterface(GameContainer gc, Character player) throws SlickException, IOException, FontFormatException
    {
        this.player = player;
        gc.getInput().addMouseListener(this);
        
        gameConsole = new Console(gc, player);
        bBar = new BottomBar(gc, player);
        charFrame = new CharacterFrame(gc, player);
        targetFrame = new CharacterFrame(gc, player);
        igMenu = new InGameMenu(gc);
        inventory = new InvetoryMenu(gc, player);
        skills = new SkillsMenu(gc, player);
        journal = new JournalMenu(gc, player);
        loot = new LootWindow(gc, player);
        trade = new TradeWindow(gc, player);
        dialogue = new DialogBox(gc);
        cast = new CastBar(gc, player);
        uiWarning = new Warning(gc, "");
    }
    /**
     * Draws ui elements
     * TODO looks pretty chaotic
     */
    public void draw(Graphics g)
    {
        gameConsole.draw(Coords.getX("TR", gameConsole.getWidth()+10), Coords.getY("BR", gameConsole.getHeight()+20), g);
        
        bBar.draw(Coords.getX("BL", 200), Coords.getY("BL", 70));
        charFrame.draw(Coords.getX("TL", 10), Coords.getY("TL", 10));
        cast.draw(Coords.getX("CE", -200), Coords.getY("CE", 200));
        if(player.getTarget() != null)
        	targetFrame.draw(Coords.getX("CE", 0), Coords.getY("TR", 0));
        
        if(bBar.isInventoryReq())
            inventory.draw(Coords.getX("CE", -500), Coords.getY("CE", -200));
        else
        	inventory.reset();
        
        if(bBar.isSkillsReq())
        	skills.draw(Coords.getX("CE", -500), Coords.getY("CE", -200));
        else
        	skills.reset();
        
        if(bBar.isJournalReq())
        	journal.draw(Coords.getX("CE", -500), Coords.getY("CE", -200));
        else
        	journal.reset();
        
        if(loot.isOpenReq())
        	loot.draw(Coords.getX("CE", -100), Coords.getY("CE", -100));
        
        if(trade.isOpenReq())
        	trade.draw(Coords.getX("CE", -100), Coords.getY("CE", -100));
        
        if(dialogue.isOpenReq())
        	dialogue.draw(Coords.getX("CE", -100), Coords.getY("CE", -100));
        
        if(bBar.isMenuReq())
        	igMenu.draw(Coords.getX("CE", -100), Coords.getY("CE", -100));
        if(igMenu.isResumeReq() || !bBar.isMenuReq())
        {
        	bBar.hideMenu();
        	igMenu.reset();
        }
        
        update();     	
    }
    /**
     * Updates ui elements
     */
    private void update()
    {
    	if(player.getTarget() != null)
    		targetFrame.setCharacter(player.getTarget());
    	
    	if(dialogue.isTradeReq())
    	{
    		trade.open((Character)player.getTarget());
    		dialogue.tradeReq(false);
    	}
    	
    	bBar.update(skills.getDragged());
        charFrame.update();
        cast.update();
        targetFrame.update();
        inventory.update();
        skills.update();
        journal.update();
        loot.update();
        trade.update();
        dialogue.update();
        gameConsole.update();
    }
    /**
     * Checks if mouse is over one of ui elements
     * @return
     */
    public boolean isMouseOver()
    {
    	return bBar.isMouseOver() || igMenu.isMouseOver() || charFrame.isMouseOver() || inventory.isMouseOver() || skills.isMouseOver() ||
    		   journal.isMouseOver() || loot.isMouseOver() || dialogue.isMouseOver() || trade.isMouseOver();
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
		if(player.getTarget() == null)
			return;
		try
		{
			Character target = (Character)player.getTarget();
			if(button == Input.MOUSE_RIGHT_BUTTON && target.isMouseOver())
			{
				if(target.isLive())
				{
					switch(target.getAttitudeTo(player))
					{
					case FRIENDLY:
						dialogue.open(player, target);
						break;
					case HOSTILE:
						player.useSkill(player.getSkills().get("autoA"));
						break;
					case NEUTRAL:
						player.useSkill(player.getSkills().get("autoA"));
						break;
					case DEAD:
						break;
					}
				}
				else
				{
					try 
					{
						loot.open(target);
					} 
					catch (SlickException | IOException e) 
					{
						Log.addSystem("Loot load fail!msg/// " + e.getMessage());
					}
				}
			}
		}
		catch(ClassCastException | NullPointerException e)
		{
			Log.addSystem("ui_mcheck_fail!msg///"+e);
			e.printStackTrace();
			return;
		}
	}
	@Override
	public void mouseWheelMoved(int change) 
	{
	}
    
}
