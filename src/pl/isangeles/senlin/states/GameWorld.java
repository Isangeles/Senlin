/*
 * GameWorld.java
 * 
 * Copyright 2017-2018 Dariusz Sikora <darek@darek-PC-LinuxMint18>
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

import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.imageout.ImageIOWriter;
import org.newdawn.slick.imageout.ImageOut;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.audio.AudioPlayer;
import pl.isangeles.senlin.cli.CommandInterface;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Chapter;
import pl.isangeles.senlin.core.TargetableObject;
import pl.isangeles.senlin.core.ai.CharacterAi;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.day.Day;
import pl.isangeles.senlin.core.out.CharacterOut;
import pl.isangeles.senlin.data.ItemsBase;
import pl.isangeles.senlin.data.area.Exit;
import pl.isangeles.senlin.data.area.Scenario;
import pl.isangeles.senlin.data.area.Area;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.data.save.SaveEngine;
import pl.isangeles.senlin.data.save.SavedGame;
import pl.isangeles.senlin.graphic.FogOfWar;
import pl.isangeles.senlin.gui.GameCursor;
import pl.isangeles.senlin.gui.tools.UserInterface;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.util.Settings;
/**
 * State for game world
 * 
 * @author Isangeles
 *
 */
public class GameWorld extends BasicGameState implements SaveElement
{
    private Chapter chapter;
    private Scenario activeScenario;
	private Day dayManager;
	private FogOfWar fow;
	private Character player;
	private Area area;
	private Area mainArea;
	private List<Area> subAreas;
	private CharacterAi npcsAi;
	private UserInterface ui;
	private CommandInterface cui;
	private AudioPlayer gwMusic;
	private Exit exitToNewArea;
	private boolean changeAreaReq;
	private boolean combat;
	private int waitForRender;
	/**
	 * Creates game world for new game
	 * @param player Player character
	 * @param ui User interface
	 */
	public GameWorld(Character player, Chapter chapter)
	{
        this.player = player;
        this.chapter = chapter;
        activeScenario = chapter.getActiveScenario();
        activeScenario.addQuestsToStart(player);
        Global.setChapter(chapter);
	}
	/**
	 * Creates game world for saved game
	 * @param savedGame Saved game
	 */
	public GameWorld(SavedGame savedGame)
	{
	    player = savedGame.getPlayer();
	    dayManager = savedGame.getDay();
	    chapter = savedGame.getChapter();
	    activeScenario = chapter.getActiveScenario();
	    activeScenario.addQuestsToStart(player);
        Global.setChapter(chapter);
	}
	/**
	 * Sets specified GUI as game GUI
	 * @param gui UserInterface object
	 */
	public void setGui(UserInterface gui)
	{
		ui = gui;
	}
	/**
	 * Sets specified CLI as game CLI
	 * @param cli Game command line interface
	 */
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
        	gwMusic.createPlaylist("idle");
        	gwMusic.createPlaylist("combat");
        	gwMusic.createPlaylist("special");
        	//gwMusic.playRandomFrom("idle", 1.0f, Settings.getMusicVol());
        	
        	if(dayManager == null)
                dayManager = new Day();
        	fow = new FogOfWar();
      
        	mainArea = activeScenario.getMainArea();
            subAreas = activeScenario.getSubAreas();
            
        	if(player.getCurrentArea() == null)
        	{
                area = mainArea;
                area.getCharacters().add(player);
                player.setArea(area);
        	}
        	else
        	{
        		area = player.getCurrentArea();
        		area.getCharacters().add(player);
        	}
        	area.addMusic(gwMusic);
        	gwMusic.addAllTo("special", "special");
            
        	npcsAi = new CharacterAi();
            npcsAi.addNpcs(mainArea.getNpcs());
            for(Area subArea : subAreas)
            {
            	npcsAi.addNpcs(subArea.getCharactersExcept(player));
            }
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
            g.translate(-ui.getCamera().getPos().x, -ui.getCamera().getPos().y);
            g.scale(ui.getCamera().getZoom(), ui.getCamera().getZoom());
            //map
            if(Settings.getMapRenderType().equals("full"))
            	area.getMap().render(0, 0); 
            else if(Settings.getMapRenderType().equals("light"))
                renderLightMap(area.getMap(), g);
            //interface pointers
            ui.drawPointners();
            //objects
            for(TargetableObject object : area.getObjects())
            {
                if(player.isNearby(object))
                {
                    object.draw(Coords.getSize(1f));
                    object.playSound();
                }
            }
            for(Exit exit : area.getExits())
            {
            	if(player.isNearby(exit.getPos().asTable()))
            		exit.draw();
            }
            for(Character npc : area.getNpcs())
            {
                if(player.isNearby(npc))
                    npc.draw();
            }
            player.draw();
            if(!Settings.getFowType().equals("off"))
                drawFOW(g);
            //interface
            g.resetTransform();
            dayManager.draw();
            ui.draw(g);

            if(ui.takeScreenshotReq())
            {
            	try
            	{
                	printScreen(g);
            	}
            	catch(IOException | SlickException e)
            	{
            		Log.addSystem("Fail to save screenshot! msg//" + e.getMessage());
            	}
            }
    	}
    }
    /* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException
    {
    	if(ui != null)
    	{
    		ui.update(container);
        	if(ui.takeSaveReq() == true)
        	{
    			try 
    			{
    				SaveEngine.save(player, this, ui, ui.getSaveName());
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
        	   ui.closeAll();
        	   System.gc();
        	   game.enterState(4);
        	}
        	if(ui.isExitReq())
        		container.exit();
    	}
    	
    	if(!isPause())
    	{
    	    dayManager.update(delta);
            
            if(!gwMusic.getActivePlaylist().equals("special"))
            {
            	if(combat && !gwMusic.getActivePlaylist().equals("combat"))
                {
                    gwMusic.stop();
                    gwMusic.playRandomFrom("combat", 1.0f, Settings.getMusicVol());
                }
                else if(!combat && !gwMusic.getActivePlaylist().equals("idle"))
                {
                    gwMusic.stop();
                    gwMusic.playRandomFrom("idle", 1.0f, Settings.getMusicVol());
                }
            }
            else
            {
            	if(!gwMusic.isPlayling())
                    gwMusic.playRandomFrom("idle", 1.0f, Settings.getMusicVol());
            }
            
            CharacterOut out = player.update(delta);
            if(out != CharacterOut.SUCCESS)
                Log.addWarning(out.toString());
            
            npcsAi.update(delta);
            combat = npcsAi.isAttacked(player);
            for(Character npc : area.getCharactersExcept(player))
            {
            	npc.update(delta);
            }
            for(TargetableObject object : area.getObjects())
            {
            	object.update(delta);
            }
               
            if(changeAreaReq && exitToNewArea != null)
            {
            	if(waitForRender <= 0)//to let UI to display appropriate message
            	{
                	if(exitToNewArea.getScenarioId().equals(activeScenario.getId()))
                		changeArea(exitToNewArea);
                	else
                        changeScenario(exitToNewArea, container, game);
                	
                	waitForRender = 0;
            	}
            	else
            		waitForRender --;
            }
            
            if(cui != null)
                activeScenario.runScripts(cui, delta);
    	}
    }
    /**
     * Returns active chapter
     * @return Module chapter
     */
    public Chapter getCurrentChapter()
    {
    	return chapter;
    }
    /**
     * Returns current area map
     * @return Tiled map of current area
     */
    public TiledMap getAreaMap()
    {
        return area.getMap();
    }
    /**
     * Returns current area
     * @return Game world area
     */
    public Area getArea()
    {
    	return area;
    }
    /**
     * Returns main area of current scenario
     * @return Game world area
     */
    public Area getMainArea()
    {
        return mainArea;
    }
    /**
     * Returns list with all sub-areas in current area
     * @return List with game world areas
     */
    public List<Area> getSubAreas()
    {
        return subAreas;
    }
    /**
     * Returns player character 
     * @return Game character owned by player
     */
    public Character getPlayer()
    {
    	return player;
    }
    /**
     * Returns game world music player 
     * @return Audio player
     */
    public AudioPlayer getMusiPlayer()
    {
    	return gwMusic;
    }
    
    public Day getDay()
    {
    	return dayManager;
    }
    /**
     * Restarts game world music
     */
    public void replayMusic()
    {
        gwMusic.stop();
        gwMusic.playRandom(1.0f, Settings.getMusicVol());
    }
    
    @Override
    public void mouseReleased(int button, int x, int y)
    {
    }
    
    @Override
    public void keyPressed(int key, char c)
    {
    }
    /**
     * Requests world to change area to area from specified exit
     * @param exit Game world exit
     */
    public void setChangeAreaReq(Exit exit)
    {
    	Scenario scenario = getCurrentChapter().getScenario(exit.getScenarioId());
        if(scenario != null)
        {
        	changeAreaReq = true;
        	exitToNewArea = exit;
        	waitForRender = 1;//to let UI to display appropriate message
        }
    }
    /* (non-Javadoc)
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
    @Override
    public int getID()
    {
        return 2;
    }
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.data.save.SaveElement#getSave(org.w3c.dom.Document)
	 */
	@Override
	public Element getSave(Document doc) 
	{
		Element worldE = doc.createElement("world");
		worldE.appendChild(chapter.getSave(doc));
		worldE.appendChild(dayManager.getSave(doc));
		return worldE;
	}
    /**
     * Checks if game should be paused
     * @return True if game should be paused, false otherwise
     */
    public boolean isPause()
    {
        if(ui != null)
        	return ui.isPauseReq();
        else
        	return false;
    }
    /**
     * Checks if area change is requested
     * @return True if area change is requested, false otherwise
     */
    public boolean isChangeAreaReq()
    {
    	return changeAreaReq;
    }
    /**
     * Draws fog of war on all map tiles except these ones in player field of view
     */
    private void drawFOW(Graphics g)
    {
        int x = 0;
        int y = 0;
        for(int i = 0; i < area.getMap().getHeight(); i ++)
        {
            for(int j = 0; j < area.getMap().getWidth(); j ++)
            {
            	Position tilePos = new Position(x, y);
                if(!player.isNearby(new int[]{x, y}) && (Settings.getFowType().equals("full") || tilePos.isIn(Global.getCameraStartPos(), Global.getCameraEndPos())))
                    fow.drawTile(x, y, Coords.getScale());

                x += 32;
            }
            x = 0;
            y += 32;
        }
    }
    /**
     * Changes active scenario to next scenario
     * @param gc Slick game container
     * @param game Slick game
     * @throws SlickException
     */
    private void changeScenario(Exit exit, GameContainer gc, StateBasedGame game) throws SlickException
    {
        Scenario nextScenario = chapter.getScenario(exit.getScenarioId());
    	this.activeScenario = nextScenario;
    	game.addState(new ReloadScreen());
    	changeAreaReq = false;
    	nextScenario = null;
    	player.setArea(activeScenario.getMainArea());
    	player.setPosition(exit.getToPos());
    	activeScenario.addQuestsToStart(player);
    	chapter.setScenario(activeScenario.getId());
    	//entering to reload screen
    	game.getState(5).init(gc, game);
    	game.enterState(5);
    	ui.getCamera().centerAt(new Position(player.getPosition()));
    }
    /**
     * Changes current area to specified area
     * @param exit Exit from current area to specified area
     * @param area Area to enter	
     */
    private void changeArea(Exit exit)
    {
    	Area area = mainArea;
    	if(!exit.isToSub())
    		area = activeScenario.getMainArea();
    	else
    		area = activeScenario.getArea(exit.getSubAreaId());
    	
    	if(player.getCurrentArea() != null)
        	player.getCurrentArea().getCharacters().remove(player);
    	player.setArea(area);
    	player.setPosition(exit.getToPos());
    	area.getCharacters().add(player);
    	if(area.hasMusic())
    	{
        	gwMusic.clearPlaylists();
        	area.addMusic(gwMusic);
        	gwMusic.playRandomFrom("idle", 1.0f, Settings.getMusicVol());
        }
    	this.area = area;
    	changeAreaReq = false;
    	ui.getCamera().centerAt(new Position(player.getPosition()));
    }
    /**
     * Renders only visible(thats in UI camera lens) part of specified map 
     * @param map Map to render
     */
    private void renderLightMap(TiledMap map, Graphics g)
    {
    	int renderStartX = ui.getCamera().getPos().x;
    	int renderStartY = ui.getCamera().getPos().y;
    	int renderEndX = ((int)ui.getCamera().getSize().width)/(int)32;
    	int renderEndY = ((int)ui.getCamera().getSize().height)/(int)32;
    	int fTileX = Math.floorDiv(renderStartX, (int)32);
    	int fTileY = Math.floorDiv(renderStartY, (int)32);
    	//g.scale(Coords.getScale(), Coords.getScale());
    	map.render(renderStartX, renderStartY, fTileX, fTileY, renderEndX+1, renderEndY+1);
    }
    /**
     * Prints screen to image and saves it in screenshots directory
     * @param g Game graphics
     * @throws SlickException 
     * @throws IOException 
     */
    private void printScreen(Graphics g) throws SlickException, IOException
    {
    	Image screenshot = new Image((int)Settings.getResolution()[0], (int)Settings.getResolution()[1]);
    	g.copyArea(screenshot, 0, 0);
    	
    	ImageOut.write(screenshot, Settings.SCREENSHOTS_DIR + File.separator + "screenshot-" + new Date().toString() + ".jpg");
    	
    	Log.addSystem("Screenshot captured!");
    }
}
