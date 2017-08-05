/*
 * GameWorld.java
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
package pl.isangeles.senlin.states;

import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.xml.sax.SAXException;

import pl.isangeles.senlin.audio.AudioPlayer;
import pl.isangeles.senlin.cli.CommandInterface;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Chapter;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Module;
import pl.isangeles.senlin.core.SimpleGameObject;
import pl.isangeles.senlin.core.ai.CharacterAi;
import pl.isangeles.senlin.core.day.Day;
import pl.isangeles.senlin.core.exc.CharacterOut;
import pl.isangeles.senlin.data.DialoguesBase;
import pl.isangeles.senlin.data.GuildsBase;
import pl.isangeles.senlin.data.ItemsBase;
import pl.isangeles.senlin.data.NpcBase;
import pl.isangeles.senlin.data.ObjectsBase;
import pl.isangeles.senlin.data.QuestsBase;
import pl.isangeles.senlin.data.ScenariosBase;
import pl.isangeles.senlin.data.area.Exit;
import pl.isangeles.senlin.data.area.Scenario;
import pl.isangeles.senlin.data.save.SaveEngine;
import pl.isangeles.senlin.data.save.SavedGame;
import pl.isangeles.senlin.graphic.FogOfWar;
import pl.isangeles.senlin.graphic.GameObject;
import pl.isangeles.senlin.graphic.SimpleAnimObject;
import pl.isangeles.senlin.gui.Field;
import pl.isangeles.senlin.gui.GameCursor;
import pl.isangeles.senlin.gui.elements.UserInterface;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.util.Settings;
/**
 * State for game world
 * 
 * System cursor currently in use
 * @author Isangeles
 *
 */
public class GameWorld extends BasicGameState
{
    private Chapter chapter;
    private Scenario activeScenario;
	private Day dayManager;
	private FogOfWar fow;
	private TiledMap areaMap;
	private Character player;
	private List<Character> areaNpcs = new ArrayList<>();
	private List<SimpleGameObject> areaObjects = new ArrayList<>();
	private List<Exit> areaExits = new ArrayList<>();
	private CharacterAi npcsAi;
	private UserInterface ui;
	private CommandInterface cui;
	private AudioPlayer gwMusic;
	private GameCursor gwCursor;
	private Scenario nextArea;
	private boolean changeAreaReq;
	/**
	 * Creates game world for new game
	 * @param player Player character
	 * @param ui User interface
	 */
	public GameWorld(Character player, Chapter chapter)
	{
        this.player = player;
        player.setPosition(1700, 500);
        player.addItem(ItemsBase.getItem("wSOHI")); //test line
        player.addItem(ItemsBase.getItem("wSOHI")); //test line
        this.chapter = chapter;
        activeScenario = chapter.getActiveScenario();
        activeScenario.startQuests(player);
        if(cui != null)
        	activeScenario.runScripts(cui);
	}
	/**
	 * Creates game world for saved game
	 * @param savedGame Saved game
	 */
	public GameWorld(SavedGame savedGame)
	{
	    this.player = savedGame.getPlayer();
	    this.chapter = savedGame.getChapter();
	    this.activeScenario = chapter.getActiveScenario();
	}
	/**
	 * Sets specified GUI as game GUI
	 * @param gui UserInterface object
	 */
	public void setGui(UserInterface gui)
	{
		ui = gui;
	}
	
	public void setCli(CommandInterface cli)
	{
		cui = cli;
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException
    {
    	MainMenu.getMusicPlayer().stop();
        try 
        {
        	gwMusic = new AudioPlayer();
        	gwMusic.addAll("exploring");
        	gwMusic.playRandom(1.0f, 1.0f);
        	
        	gwCursor = new GameCursor(container);
        	dayManager = new Day();
        	fow = new FogOfWar();
      
        	System.out.println(activeScenario.getId());
            areaMap = activeScenario.getMap();
            
            areaNpcs = activeScenario.getNpcs(); //test line
            areaObjects = activeScenario.getObjects();
            areaExits = activeScenario.getExits();
            
        	npcsAi = new CharacterAi(this);
            npcsAi.addNpcs(areaNpcs); 
        } 
        catch (SlickException | IOException e) 
        {
            System.err.println("Error message: " + e.getMessage());
        }
    }
    /* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException
    {
    	Toolkit.getDefaultToolkit().sync();
    	if(ui != null)
    	{
    		//game world
            g.translate(-ui.getCamera().getPos()[0], -ui.getCamera().getPos()[1]);
            areaMap.render(0, 0);
            for(SimpleGameObject object : areaObjects)
            {
                if(player.isNearby(object))
                    object.draw(Coords.getSize(1f));
            }
            for(Exit exit : areaExits)
            {
            	if(player.isNearby(exit.getPos().asTable()))
            		exit.draw();
            }
            for(Character npc : areaNpcs)
            {
                if(player.isNearby(npc))
                    npc.draw();
            }
            player.draw();
            if(!Settings.getFowType().equals("FOW OFF"))
                drawFOW(g);
            //interface
            g.translate(ui.getCamera().getPos()[0], ui.getCamera().getPos()[1]);
            dayManager.draw();
            ui.draw(g);
            //gwCursor.draw();
    	}
    }
    /* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException
    {
    	dayManager.update(delta);
    	
        if(!isPause())
            keyDown(container.getInput());
    	
        CharacterOut out;
        out = player.update(delta, areaMap);
        if(out != CharacterOut.SUCCESS)
            Log.addWarning(out.toString());
    	
    	npcsAi.update(delta);
    	if(changeAreaReq)
    		changeArea(container, game);
    	
    	if(ui != null)
    	{
    		ui.update();
        	if(ui.takeSaveReq() == true)
        	{
    			try 
    			{
    				SaveEngine.save(player, chapter, ui, ui.getSaveName());
    			} 
    			catch (ParserConfigurationException | TransformerException e) 
    			{
    				Log.addSystem("save_builder_fail_msg///" + e.getMessage());
    			}
        	}
        	if(ui.takeLoadReq() == true)
        	{
        	   game.addState(new LoadingScreen(ui.getLoadName()));
        	   game.getState(4).init(container, game);
        	   System.gc();
        	   game.enterState(4);
        	}
        	if(ui.isExitReq())
        		container.exit();
    	}
    }
    /**
     * Returns all nearby characters in area 
     * @param character A character around which to look for other nearby characters
     * @return List with all nearby characters
     */
    public List<Character> getNearbyCharacters(Character character)
    {
    	List<Character> nearbyCharacters = new ArrayList<>();
    	
    	if(character.getRangeFrom(player.getPosition()) < 200)
    		nearbyCharacters.add(player);
    	
    	for(Character npc : areaNpcs)
    	{
    		if(npc != character && character.getRangeFrom(npc.getPosition()) < 200)
        		nearbyCharacters.add(npc);
    	}
    	
    	return nearbyCharacters;
    }
    
    public TiledMap getAreaMap()
    {
        return areaMap;
    }
    
    public Day getDay()
    {
    	return dayManager;
    }
    
    @Override
    public void mouseReleased(int button, int x, int y)
    {
    	if((ui == null || !ui.isMouseOver()) && !isPause())
    	{
            int worldX = (int)Global.worldX(x);
            int worldY = (int)Global.worldY(y);
    		if(button == Input.MOUSE_LEFT_BUTTON && isMovable(worldX, worldY))
    		{
    			for(Character npc : areaNpcs)
    			{
    				if(npc.isMouseOver())
    					return;
    			}
    			player.moveTo(worldX, worldY);
    			Log.addInformation("Move: " + worldX + "/" + worldY + " " + areaMap.getTileId(worldX/areaMap.getTileWidth(), worldY/areaMap.getTileHeight(), 1)); //TEST LINE
    		}
    		if(button == Input.MOUSE_RIGHT_BUTTON)
    		{
    			for(Exit exit : areaExits)
    			{
    				if(exit.isMouseOver())
    				{
    					Scenario scenario = chapter.getScenario(exit.getScenarioId());

						if(scenario != null)
						{
							changeAreaReq = true;
							nextArea = scenario;
						}
    				}
    			}
    			
    			for(SimpleGameObject object : areaObjects)
    			{
    			    if(object.isMouseOver())
    			    {
    			        player.setTarget(object);
    			        return;
    			    }
    			    	
    			}
    			
    			for(Character npc : areaNpcs)
    			{
    			    if(npc.isMouseOver())
    			    {
    			        player.setTarget(npc);
    			        npc.targeted(true);
    			        return;
    			    }
    			    else
    			        npc.targeted(false);
    			}
    			player.setTarget(null);
    		}
    	}
    }
    
    @Override
    public void keyPressed(int key, char c)
    {
    }
    /* (non-Javadoc)
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
    @Override
    public int getID()
    {
        return 2;
    }
    /**
     * KeyDown method called in update, because engine does not provide keyDown method for override  
     * @param input Input from game container
     */
    private void keyDown(Input input)
    {
        if(ui != null)
        {
        	if(input.isKeyDown(Input.KEY_W))
                ui.getCamera().up(10);
            if(input.isKeyDown(Input.KEY_S))
                ui.getCamera().down(10);
            if(input.isKeyDown(Input.KEY_A))
                ui.getCamera().left(10);
            if(input.isKeyDown(Input.KEY_D))
                ui.getCamera().right(10);
            Global.setCamerPos(ui.getCamera().getPos()[0], ui.getCamera().getPos()[1]);
        }
    }
    
    private boolean isPause()
    {
        if(ui != null)
        	return ui.isPauseReq();
        else
        	return false;
    }
    /**
     * Checks if specified xy positions are moveable on game world map
     * @param x Position on x axis
     * @param y Position on y axis
     * @return True if position are moveable, false otherwise
     */
    private boolean isMovable(int x, int y)
    {
        try
        {
        	if(areaMap.getTileId(x/areaMap.getTileWidth(), y/areaMap.getTileHeight(), 1) != 0)
                return false;
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
        	return false;
        }
    	
    	return true;
    }
    /**
     * Draws fog of war on all map tiles except these ones in player field of view
     */
    private void drawFOW(Graphics g)
    {
        int x = 0;
        int y = 0;
        for(int i = 0; i < areaMap.getHeight(); i ++)
        {
            for(int j = 0; j < areaMap.getWidth(); j ++)
            {
            	Position tilePos = new Position(x, y);
                if(!player.isNearby(new int[]{x, y}) && (Settings.getFowType().equals("full FOW") || tilePos.isIn(Global.getCameraStartPos(), Global.getCameraEndPos())))
                    fow.drawTile(x, y, Coords.getScale());

                x += 32;
            }
            x = 0;
            y += 32;
        }
    }
    
    private void changeArea(GameContainer gc, StateBasedGame game) throws SlickException
    {
    	this.activeScenario = nextArea;
    	game.addState(new ReloadScreen());
    	changeAreaReq = false;
    	nextArea = null;
    	game.getState(5).init(gc, game);
    	game.enterState(5);
    }
}
