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
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.SimpleGameObject;
import pl.isangeles.senlin.core.ai.CharacterAi;
import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.data.DialoguesBase;
import pl.isangeles.senlin.data.GuildsBase;
import pl.isangeles.senlin.data.ItemsBase;
import pl.isangeles.senlin.data.NpcBase;
import pl.isangeles.senlin.data.ObjectsBase;
import pl.isangeles.senlin.data.QuestsBase;
import pl.isangeles.senlin.data.SaveEngine;
import pl.isangeles.senlin.data.SavedGame;
import pl.isangeles.senlin.data.ScenariosBase;
import pl.isangeles.senlin.data.area.Exit;
import pl.isangeles.senlin.data.area.Scenario;
import pl.isangeles.senlin.graphic.GameObject;
import pl.isangeles.senlin.graphic.SimpleAnimObject;
import pl.isangeles.senlin.graphic.day.Day;
import pl.isangeles.senlin.graphic.day.FogOfWar;
import pl.isangeles.senlin.inter.Field;
import pl.isangeles.senlin.inter.GameCursor;
import pl.isangeles.senlin.inter.ui.UserInterface;
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
    private List<Scenario> scenarios = new ArrayList<>();
	private Scenario activeScenario;
	private Day dayManager;
	private FogOfWar fow;
	private TiledMap areaMap;
	private Character player;
	private List<Character> areaNpcs = new ArrayList<>();
	private List<SimpleGameObject> gwObjects = new ArrayList<>();
	private List<Exit> areaExits = new ArrayList<>();
	private CharacterAi npcsAi;
	private UserInterface ui;
	private float[] cameraPos = {0f, 0f};
	private AudioPlayer gwMusic;
	private GameCursor gwCursor;
	private Scenario nextArea;
	private boolean changeAreaReq;
	/**
	 * Creates game world for new game
	 * @param player Player character
	 * @param ui User interface
	 */
	public GameWorld(Character player, UserInterface ui)
	{
        this.ui = ui;
        this.player = player;
        player.setPosition(1700, 500);
        player.addItem(ItemsBase.getItem("wSOHI")); //test line
        player.addItem(ItemsBase.getItem("wSOHI")); //test line
        scenarios.add(ScenariosBase.getScenario("prologue01"));
        activeScenario = scenarios.get(0); //test line
        activeScenario.startQuests(player);
	}
	/**
	 * Creates game world for saved game
	 * @param savedGame Saved game
	 */
	public GameWorld(SavedGame savedGame, UserInterface ui)
	{
	    this.ui = ui;
	    this.player = savedGame.getPlayer();
	    this.scenarios = savedGame.getScenarios();
	    this.activeScenario = savedGame.getActiveScenario();
	}
    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException
    {
    	MainMenu.getMusicPlayer().stop();
        try 
        {
        	gwMusic = new AudioPlayer();
        	gwMusic.add("worldExploring.ogg");
        	gwMusic.play(1.0f, 1.0f, "worldExploring.ogg");
        	
        	gwCursor = new GameCursor(container);
        	dayManager = new Day();
        	fow = new FogOfWar();
      
        	System.out.println(activeScenario.getId());
            areaMap = activeScenario.getMap();
            
            areaNpcs = activeScenario.getNpcs(); //test line
            gwObjects = activeScenario.getObjects();
            areaExits = activeScenario.getExits();
            
        	npcsAi = new CharacterAi(this);
            npcsAi.addNpcs(areaNpcs); 
        } 
        catch (SlickException | IOException e) 
        {
            System.err.println("Error message: " + e.getMessage());
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException
    {
    	Toolkit.getDefaultToolkit().sync();
    	//game world
        g.translate(-cameraPos[0], -cameraPos[1]);
        areaMap.render(0, 0);
        for(SimpleGameObject object : gwObjects)
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
        drawFOW(g);
        //interface
        g.translate(cameraPos[0], cameraPos[1]);
        dayManager.draw();
        ui.draw(g);
        //gwCursor.draw();
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException
    {
    	dayManager.update(delta);
    	
        if(!isPause())
            keyDown(container.getInput());
    	
    	player.update(delta, areaMap);
    	npcsAi.update(delta);
    	
    	if(ui.takeSaveReq() == true)
    	{
			try 
			{
				SaveEngine.save(player, scenarios, activeScenario.getId(), ui.getSaveName());
			} 
			catch (ParserConfigurationException | TransformerException e) 
			{
				Log.addSystem("save_maker_fail_msg///" + e.getMessage());
			}
    	}
    	if(ui.takeLoadReq() == true)
    	{
    	   game.addState(new LoadingScreen(ui.getLoadName()));
    	   game.getState(4).init(container, game);
    	   System.gc();
    	   game.enterState(4);
    	}
    	if(changeAreaReq)
    		changeArea(container, game);
    	if(ui.isExitReq())
    		container.exit();
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
    
    @Override
    public void mouseReleased(int button, int x, int y)
    {
    	if(!ui.isMouseOver() && !isPause())
    	{
            int worldX = (int)Global.worldX(x);
            int worldY = (int)Global.worldY(y);
    		if(button == Input.MOUSE_LEFT_BUTTON && isMovable(worldX, worldY))
    		{
    			player.moveTo(worldX, worldY);
    			Log.addInformation("Move: " + worldX + "/" + worldY + " " + areaMap.getTileId(worldX/areaMap.getTileWidth(), worldY/areaMap.getTileHeight(), 1)); //TEST LINE
    		}
    		if(button == Input.MOUSE_RIGHT_BUTTON)
    		{
    			for(Exit exit : areaExits)
    			{
    				if(exit.isMouseOver())
    				{
    					for(Scenario savedScenario : scenarios)
    					{
    						if(savedScenario.getId().equals(exit.exitTo()))
    						{
    							nextArea = savedScenario;
    							changeAreaReq = true;
    						}
    					}

						if(nextArea == null)
						{
							nextArea = ScenariosBase.getScenario(exit.exitTo());
							changeAreaReq = true;
						}
    				}
    			}
    		}
    	}
    }
    
    @Override
    public void keyPressed(int key, char c)
    {
    }

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
        if(input.isKeyDown(Input.KEY_W))
            cameraPos[1] -= 10;
        if(input.isKeyDown(Input.KEY_S))
            cameraPos[1] += 10;
        if(input.isKeyDown(Input.KEY_A))
            cameraPos[0] -= 10;
        if(input.isKeyDown(Input.KEY_D))
            cameraPos[0] += 10;
        Global.setCamerPos(cameraPos[0], cameraPos[1]);
    }
    
    private boolean isPause()
    {
        return ui.isPauseReq();
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
