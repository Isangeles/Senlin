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

import java.awt.Toolkit;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.audio.AudioPlayer;
import pl.isangeles.senlin.cli.CommandInterface;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Chapter;
import pl.isangeles.senlin.core.SimpleGameObject;
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
	private List<Area> subAreas = new ArrayList<>();
	private CharacterAi npcsAi;
	private UserInterface ui;
	private CommandInterface cui;
	private AudioPlayer gwMusic;
	private Scenario nextScenario;
	private boolean changeScenarioReq;
	private boolean combat;
	/**
	 * Creates game world for new game
	 * @param player Player character
	 * @param ui User interface
	 */
	public GameWorld(Character player, Chapter chapter)
	{
        this.player = player;
        player.setPosition(1700, 500);
        this.chapter = chapter;
        activeScenario = chapter.getActiveScenario();
        activeScenario.startQuests(player);
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
        	gwMusic.playRandomFrom("idle", 1.0f, Settings.getMusicVol());
        	
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
            
            if(Settings.getMapRenderType().equals("full"))
            	area.getMap().render(0, 0); 
            else if(Settings.getMapRenderType().equals("light"))
                renderLightMap(area.getMap());
            
            for(SimpleGameObject object : area.getObjects())
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
            g.translate(ui.getCamera().getPos().x, ui.getCamera().getPos().y);
            dayManager.draw();
            ui.draw(g);
    	}
    }
    /* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException
    {
    	if(!isPause())
    	{
    	    dayManager.update(delta);
            
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
            
            if(!isPause())
                keyDown(container.getInput());
            
            CharacterOut out;
            out = player.update(delta);
            if(out != CharacterOut.SUCCESS)
                Log.addWarning(out.toString());
            
            npcsAi.update(delta);
            combat = npcsAi.isAttacked(player);
            
            if(changeScenarioReq)
                changeScenario(container, game);
            if(cui != null)
                activeScenario.runScripts(cui);
    	}
    	
    	if(ui != null)
    	{
    		ui.update();
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
        	   System.gc();
        	   game.enterState(4);
        	}
        	if(ui.isExitReq())
        		container.exit();
    	}
    }
    
    public Chapter getCurrentChapter()
    {
    	return chapter;
    }
    
    public TiledMap getAreaMap()
    {
        return area.getMap();
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
    	if((ui == null || !ui.isMouseOver()) && !isPause())
    	{
            int worldX = (int)Global.worldX(x);
            int worldY = (int)Global.worldY(y);
    		if(button == Input.MOUSE_LEFT_BUTTON && isMovable(worldX, worldY))
    		{
    			for(Character npc : area.getNpcs())
    			{
    				if(npc.isMouseOver())
    					return;
    			}
    			player.moveTo(worldX, worldY);
    			Log.addInformation("Move: " + worldX + "/" + worldY + " " + area.getMap().getTileId(worldX/area.getMap().getTileWidth(), worldY/area.getMap().getTileHeight(), 1)); //TEST LINE
    		}
    		if(button == Input.MOUSE_RIGHT_BUTTON)
    		{
    			for(Exit exit : area.getExits())
    			{
    				if(exit.isMouseOver() && player.getRangeFrom(exit.getPos().asTable()) <= 40)
    				{
    				    //Log.addSystem(exit.getScenarioId() + " exit clicked!");// TEST LINE
    					Scenario scenario = chapter.getScenario(exit.getScenarioId());
    					
						if(scenario != null)
						{
							if(!scenario.getId().equals(activeScenario.getId()))
							{
								changeScenarioReq = true;
								nextScenario = scenario;
								//Log.addSystem("change to: " + scenario.getId());// TEST LINE
							}
							else
							{
								if(exit.isToSub())
								{
									for(Area subArea : subAreas)
									{
										if(subArea.getId().equals(exit.getSubAreaId()))
										{
											changeArea(exit, subArea);
										}
									}
								}
								else
								    changeArea(exit, mainArea);
							}
						}
    				}
    			}
    			
    			for(SimpleGameObject object : area.getObjects())
    			{
    			    if(object.isMouseOver())
    			    {
    			        player.setTarget(object);
    			        return;
    			    }
    			    	
    			}
    			
    			for(Character npc : area.getNpcs())
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
    public void mouseWheelMoved(int value)
    {
    	if(ui != null)
    	{
    		if(value > 0)
        	{
        		//ui.getCamera().zoom(0.1f);
        	}
        	if(value < 0)
        	{
        		//ui.getCamera().unzoom(0.1f);
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
     * KeyDown method called in update, because engine does not provide keyDown method for override  
     * @param input Input from game container
     */
    private void keyDown(Input input)
    {
        if(ui != null)
        {
        	if(input.isKeyDown(Input.KEY_W) && ui.getCamera().getPos().y > 0)
                ui.getCamera().up(32);
            if(input.isKeyDown(Input.KEY_S) && ui.getCamera().getBRPos().y < area.getMapSize().height)
                ui.getCamera().down(32);
            if(input.isKeyDown(Input.KEY_A) && ui.getCamera().getPos().x > 0)
                ui.getCamera().left(32);
            if(input.isKeyDown(Input.KEY_D) && ui.getCamera().getBRPos().x < area.getMapSize().width)
                ui.getCamera().right(32);
            Global.setCamerPos(ui.getCamera().getPos().x, ui.getCamera().getPos().y);
        }
    }
    /**
     * Checks if game should be paused
     * @return True if game should be paused, false otherwise
     */
    private boolean isPause()
    {
        if(ui != null)
        	return ui.isPauseReq();
        else
        	return false;
    }
    /**
     * Checks if specified xy positions are 'moveable' on game world map
     * @param x Position on x axis
     * @param y Position on y axis
     * @return True if position are moveable, false otherwise
     */
    private boolean isMovable(int x, int y)
    {
    	TiledMap map = area.getMap();
        try
        {
        	if(map.getTileId(x/map.getTileWidth(), y/map.getTileHeight(), 2) != 0 || //blockground layer 
     	           map.getTileId(x/map.getTileWidth(), y/map.getTileHeight(), 3) != 0 || //water layer
     	           map.getTileId(x/map.getTileWidth(), y/map.getTileHeight(), 4) != 0 || //trees layer
     	           map.getTileId(x/map.getTileWidth(), y/map.getTileHeight(), 5) != 0 || //buildings layer
     	           map.getTileId(x/map.getTileWidth(), y/map.getTileHeight(), 6) != 0)   //objects layer
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
    private void changeScenario(GameContainer gc, StateBasedGame game) throws SlickException
    {
    	this.activeScenario = nextScenario;
    	game.addState(new ReloadScreen());
    	changeScenarioReq = false;
    	nextScenario = null;
    	player.setArea(activeScenario.getMainArea());
    	//entering to reload screen
    	game.getState(5).init(gc, game);
    	game.enterState(5);
    }
    /**
     * Changes current area to specified area
     * @param exit Exit from current area to specified area
     * @param area Area to enter	
     */
    private void changeArea(Exit exit, Area area)
    {
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
    	ui.getCamera().centerAt(new Position(player.getPosition()));
    }
    /**
     * Renders only visible(thats in UI camera lens) part of specified map 
     * @param map Map to render
     */
    private void renderLightMap(TiledMap map)
    {
    	int renderStartX = ui.getCamera().getPos().x;
    	int renderStartY = ui.getCamera().getPos().y;
    	int renderEndX = ((int)ui.getCamera().getSize().width)/32;
    	int renderEndY = ((int)ui.getCamera().getSize().height)/32;
    	int fTileX = Math.floorDiv(renderStartX, 32);
    	int fTileY = Math.floorDiv(renderStartY, 32);
    	
    	map.render(renderStartX, renderStartY, fTileX, fTileY, renderEndX, renderEndY);
    }
}
