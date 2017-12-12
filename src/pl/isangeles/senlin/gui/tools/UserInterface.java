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
import java.util.concurrent.TimeUnit;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

import pl.isangeles.senlin.states.GameWorld;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.util.Settings;
import pl.isangeles.senlin.util.Size;
import pl.isangeles.senlin.util.Stopwatch;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.cli.CommandInterface;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.TargetableObject;
import pl.isangeles.senlin.core.character.Attitude;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.out.CharacterOut;
import pl.isangeles.senlin.core.signal.CharacterSignal;
import pl.isangeles.senlin.data.area.Area;
import pl.isangeles.senlin.data.area.Exit;
import pl.isangeles.senlin.data.area.Scenario;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.gui.GameCursor;
import pl.isangeles.senlin.gui.InfoField;
import pl.isangeles.senlin.gui.UiLayout;
import pl.isangeles.senlin.gui.Warning;
import pl.isangeles.senlin.gui.tools.*;
/**
 * Class for game graphical interface
 * @author Isangeles
 *
 */
public class UserInterface implements MouseListener, KeyListener, SaveElement
{
    private Character player;
    private GameWorld gw;
    private static GameCursor cursor;
    private Console gameConsole;
    private BottomBar bBar;
    private CharacterFrame charFrame;
    private TargetFrame targetFrame;
    private InGameMenu igMenu;
    private CharacterWindow charWin;
    private InventoryMenu inventory;
    private SkillsMenu skills;
    private CraftingMenu crafting;
    private JournalMenu journal;
    private MapWindow map;
    private LootWindow loot;
    private TradeWindow trade;
    private TrainingWindow train;
    private DialogBox dialogue;
    private ReadWindow reading;
    private CastBar cast;
    private SaveGameWindow save;
    private LoadGameWindow load;
    private SettingsMenu settings;
    private WaitWindow waitWin;
    private ConditionsInfo conditions;
    private DestinationPoint destination;
    private TargetPoint target;
    private Camera camera;
    private Warning uiWarning;
    private InfoFrame info;
    
    private boolean lock;
    /**
     * UI constructor, calls all UI elements constructors
     * @param gc Game container for superclass and ui elements
     * @param cli Command line interface
     * @param player Player character 
     * @param gw Game world
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    public UserInterface(GameContainer gc, CommandInterface cli, Character player, GameWorld gw) throws SlickException, IOException, FontFormatException
    {
        this.player = player;
        this.gw = gw;
        gc.getInput().addMouseListener(this);
        gc.getInput().addKeyListener(this);
        
        //cursor = new GameCursor(gc);

        uiWarning = new Warning(gc);
        info = new InfoFrame(gc);
        gameConsole = new Console(gc, cli);
        charFrame = new CharacterFrame(gc, player);
        targetFrame = new TargetFrame(gc, player);
        igMenu = new InGameMenu(gc);
        charWin = new CharacterWindow(gc, player);
        inventory = new InventoryMenu(gc, player);
        skills = new SkillsMenu(gc, player);
        crafting = new CraftingMenu(gc, player);
        journal = new JournalMenu(gc, gw, player);
        map = new MapWindow(gc, player);
        loot = new LootWindow(gc, player);
        trade = new TradeWindow(gc, player);
        train = new TrainingWindow(gc, player);
        dialogue = new DialogBox(gc);
        reading = new ReadWindow(gc, player);
        cast = new CastBar(gc, player);
        save = new SaveGameWindow(gc);
        load = new LoadGameWindow(gc);
        settings = new SettingsMenu(gc, gw);
        waitWin = new WaitWindow(gc, gw);
        bBar = new BottomBar(gc, gw, igMenu, charWin, inventory, skills, journal, crafting, map, player);
        conditions = new ConditionsInfo(gc, gw);
        destination = new DestinationPoint(gc, player);
        target = new TargetPoint(gc, player);
        camera = new Camera(gc, gw, new Size(Settings.getResolution()[0], Settings.getResolution()[1])); 
    }
    /**
     * Draws ui elements
     */
    public void draw(Graphics g)
    {
        if(!lock)
        {
        	gameConsole.draw(Coords.getX("TR", gameConsole.getWidth()+10), Coords.getY("BR", gameConsole.getHeight()+20), g);
            conditions.draw(Coords.getX("BL", 50), Coords.getY("BL", 50));
            bBar.draw(Coords.getX("BL", 200), Coords.getY("BL", 70));
            charFrame.draw(Coords.getX("TL", 10), Coords.getY("TL", 10));
        }
        
        if(cast.isOpenReq())
        	cast.draw(Coords.getX("CE", 0), Coords.getY("CE", 100));
        
        if(player.getTarget() != null)
        	targetFrame.draw(Coords.getX("CE", 0), Coords.getY("TR", 0));
        
        if(charWin.isOpenReq())
        	charWin.draw(Coords.getX("CE", -250), Coords.getY("CE", -350));
        
        if(inventory.isOpenReq())
            inventory.draw(Coords.getX("CE", -250), Coords.getY("CE", -300));
        
        if(skills.isOpenReq())
        	skills.draw(Coords.getX("CE", -500), Coords.getY("CE", -200));
        
        if(journal.isOpenReq())
        	journal.draw(Coords.getX("CE", -500), Coords.getY("CE", -200));
        
        if(map.isOpenReq())
        	map.draw(Coords.getX("CE", -250), Coords.getY("CE", -350), g);
        
        if(loot.isOpenReq())
        	loot.draw(Coords.getX("CE", -100), Coords.getY("CE", -100));
        
        if(trade.isOpenReq())
        	trade.draw(Coords.getX("CE", -100), Coords.getY("CE", -100));
        
        if(train.isOpenReq())
        	train.draw(Coords.getX("CE", -100), Coords.getY("CE", -100));
        
        if(dialogue.isOpenReq())
        	dialogue.draw(Coords.getX("CE", -100), Coords.getY("CE", -100));
        
        if(reading.isOpenReq())
        	reading.draw(Coords.getX("CE", -100), Coords.getY("CE", -100));
        
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
        
        if(waitWin.isOpenReq())
        	waitWin.draw(Coords.getX("CE", -100), Coords.getY("CE", -100));
        
        if(info.isOpenReq())
        	info.draw(Coords.getX("CE", 0) - (info.getScaledWidth()/2), Coords.getY("CE", 0) - (info.getScaledHeight()/2));
        
        //cursor.draw();   	
    }
    /**
     * Draws UI pointers, like destination point or target point
     */
    public void drawPointners()
    {
    	if(destination.isOpenReq())
    		destination.draw();
    	if(target.isOpenReq())
    		target.draw();
    }
    /**
     * Updates ui elements
     */
    public void update(GameContainer gc)
    {
        keyDown(gc.getInput());
    	
    	if(player.getTarget() != null)
    	{
    		targetFrame.setCharacter(player.getTarget());
    	}
    	if(player.looting() != null && !loot.isOpenReq())
    	{
    		CharacterOut out = loot.open(player.looting());
    		if(out == CharacterOut.LOCKED)
    		{
    		    player.getSignals().stopLooting();
    		    Log.addInformation(TConnector.getText("ui", "logLocked"));
    		}
    	}
    	if(player.reading() != null && !reading.isOpenReq())
    	{
    		reading.open(player.reading());
    	}
    	if(player.getSignals().get(CharacterSignal.RESTING) != null)
    	{
    		waitWin.open(true);
    		player.getSignals().stopResting();
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
    	if(gw.isChangeAreaReq())
    		info.open(TConnector.getText("ui", "loadAreaInfo"));
    	else if(!gw.isChangeAreaReq() && info.isOpenReq())
    		info.close();
    	
    	bBar.update();
        charFrame.update();
        cast.update();
        targetFrame.update();
        inventory.update();
        charWin.update();
        skills.update();
        crafting.update();
        journal.update();
        map.update();
        loot.update();
        trade.update();
        train.update();
        dialogue.update();
        reading.update();
        save.update();
        load.update();
        settings.update();
        waitWin.update();
        gameConsole.update();
        conditions.update();
        destination.update();
    }
    /**
     * Checks if mouse is over one of ui elements
     * @return
     */
    public boolean isMouseOver()
    {
    	return bBar.isMouseOver() || igMenu.isMouseOver() || charFrame.isMouseOver() || gameConsole.isMouseOver() || inventory.isMouseOver() || 
    		   skills.isMouseOver() || journal.isMouseOver() || loot.isMouseOver() || dialogue.isMouseOver() || trade.isMouseOver() || 
    		   train.isMouseOver() || save.isMouseOver() || load.isMouseOver() || settings.isMouseOver() || crafting.isMouseOver() ||
    		   charWin.isMouseOver() || map.isMouseOver() || waitWin.isMouseOver();
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
        return !gameConsole.isHidden() || bBar.isPauseReq() || load.isOpenReq() || save.isOpenReq() || settings.isOpenReq() || waitWin.isOpenReq();
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
    /**
     * Returns UI camera
     * @return UI camera
     */
    public Camera getCamera()
    {
    	return camera;
    }
    /**
     * Checks if UI is locked
     * @return True if UI is locked, false otherwise
     */
    public boolean isLocked()
    {
    	return lock;
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
    /**
     * Lock on unlocks UI
     * @param lock True to lock UI, false to unlock
     */
    public void setLock(boolean lock)
    {
    	this.lock = lock;
    	//camera.setLock(lock);
    	bBar.setFocus(!lock);
    }
    /**
     * Closes all UI windows
     */
    public void closeAll()
    {
    	igMenu.close();
    	charWin.close();
    	inventory.close();
    	skills.copy();
    	crafting.close();
    	journal.close();
    	map.close();
    	loot.close();
    	trade.close();
    	train.close();
    	dialogue.close();
    	reading.close();
    	settings.close();
    	save.close();
    	load.close();
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
		return !lock;
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
		    if(!isMouseOver())
		    {
	            Targetable target = player.getTarget();
	            if(target != null)
	            {
	            	if(target.isMouseOver())
                    {
                    	if(Character.class.isInstance(target))
                    	{
                    		Character targetedChar = (Character)target;
                            switch(targetedChar.getAttitudeTo(player))
                            {
                            case FRIENDLY:
                                dialogue.open(player, targetedChar);
                                break;
                            case HOSTILE:
                                player.getSignals().startCombat(target);
                                break;
                            case NEUTRAL:
                                player.getSignals().startCombat(target);
                                break;
                            case DEAD:
                            	player.getSignals().startLooting(target);
                                break;
                            }
                    	}
                    	else if(TargetableObject.class.isInstance(target))
                    	{
                    		TargetableObject targetedObject = (TargetableObject)target;
                    		targetedObject.startAction(player);
                    	}
                    }
                    else
                    {
                        player.getSignals().stopCombat();
                    }
	            }
	            
                int worldX = (int)Global.worldX(x);
                int worldY = (int)Global.worldY(y);
                if(gw.getArea().isMovable(worldX, worldY))
                {
                    for(Character npc : gw.getArea().getNpcs())
                    {
                        if(npc.isMouseOver())
                            return;
                    }
                    destination.setPosition(new Position(Global.worldX(x), Global.worldY(y)));
                    player.moveTo((int)Global.worldX(x), (int)Global.worldY(y));
                    Log.addInformation("Move: " + worldX + "/" + worldY + " " + gw.getArea().getMap().getTileId(worldX/gw.getArea().getMap().getTileWidth(), worldY/gw.getArea().getMap().getTileHeight(), 1)); //TEST LINE
                }
                
		    }
		}

        if(button == Input.MOUSE_RIGHT_BUTTON)
        {
            for(Exit exit : gw.getArea().getExits())
            {
                if(exit.isMouseOver() && player.getRangeFrom(exit.getPos().asTable()) <= 70)
                	gw.setChangeAreaReq(exit);
            }
            
            for(TargetableObject object : gw.getArea().getObjects())
            {
                if(object.isMouseOver())
                {
                    player.setTarget(object);
                    return;
                }
                    
            }
            
            for(Character npc : gw.getArea().getNpcs())
            {
                if(npc.isMouseOver())
                {
                    player.setTarget(npc);
                    return;
                }
            }
            if(!isMouseOver())
                player.setTarget(null);
        }
	}
	@Override
	public void mouseWheelMoved(int change) 
	{
        if(!isMouseOver())
        {
            if(change > 0 && camera.getZoom() < 1.5f)
            {
                camera.zoom(0.1f);
            }
            if(change < 0 && camera.getZoom() > 0.5f)
            {
                camera.unzoom(0.1f);
            }
        }
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.KeyListener#keyPressed(int, char)
	 */
	@Override
	public void keyPressed(int key, char c) 
	{
		
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.KeyListener#keyReleased(int, char)
	 */
	@Override
	public void keyReleased(int key, char c) 
	{
	}
	/**
	 * UNUSED
	 * @return
	 * @deprecated
	 */
	public static GameCursor getUiCursor()
	{
		return cursor;
	}
    /**
     * KeyDown method called in update, because engine does not provide keyDown method for override  
     * @param input Input from game container
     */
    private void keyDown(Input input)
    {
       if(!gameConsole.isFocused())
       {
    	   if(input.isKeyDown(Input.KEY_W) && camera.getPos().y > -200)
               camera.up(Coords.getDis(32));
           if(input.isKeyDown(Input.KEY_S) && camera.getBRPos().y < (gw.getArea().getMapSize().height+Coords.getSize(200)))
               camera.down(Coords.getDis(32));
           if(input.isKeyDown(Input.KEY_A) && camera.getPos().x > -200)
               camera.left(Coords.getDis(32));
           if(input.isKeyDown(Input.KEY_D) && camera.getBRPos().x < (gw.getArea().getMapSize().width+Coords.getSize(200)))
               camera.right(Coords.getDis(32));
           Global.setCamerPos(camera.getPos().x, camera.getPos().y);
       }
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
	/**
	 * Hides in-game menu
	 */
	private void hideMenu()
	{
		igMenu.close();
	}
}
