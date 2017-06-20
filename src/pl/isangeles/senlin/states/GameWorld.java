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
import pl.isangeles.senlin.data.SaveMaker;
import pl.isangeles.senlin.data.Scenario;
import pl.isangeles.senlin.data.ScenariosBase;
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
	private Scenario scenario;
	private Day dayManager;
	private FogOfWar fow;
	private TiledMap areaMap;
	private Character player;
	private List<Character> areaNpcs = new ArrayList<>();
	private List<SimpleGameObject> gwObjects = new ArrayList<>();
	private CharacterAi npcsAi;
	private UserInterface ui;
	private float[] cameraPos = {0f, 0f};
	private GameCursor gwCursor;
	
	public GameWorld(Character player, UserInterface ui)
	{
        this.player = player;
        this.ui = ui;
	}
	
    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException
    {
        try 
        {
        	gwCursor = new GameCursor(container);
        	dayManager = new Day();
        	fow = new FogOfWar();
        	
        	ItemsBase.loadBases(container);
        	GuildsBase.load();
        	NpcBase.load(container);
        	DialoguesBase.load("dPrologue");
        	QuestsBase.load("qPrologue");
        	ObjectsBase.load("animObjects");
        	ScenariosBase.load();
        	
        	scenario = ScenariosBase.getScenario("prologue01");
            areaMap = scenario.getMap();
            
            scenario.startQuests(player);
            player.setPosition(1700, 500);
            player.addItem(ItemsBase.getItem("wSOHI")); //test line
            player.addItem(ItemsBase.getItem("wSOHI")); //test line
            areaNpcs = scenario.getNpcs(); //test line
            gwObjects = scenario.getObjects();
            
        	npcsAi = new CharacterAi(this);
            npcsAi.addNpcs(areaNpcs);
            
            SaveMaker.saveGame(player, "test.ssg"); //test line
        } 
        catch (SlickException | IOException | FontFormatException | ParserConfigurationException | SAXException | TransformerException e) 
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
                object.draw(1f);
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
        if(areaMap.getTileId(x/areaMap.getTileWidth(), y/areaMap.getTileHeight(), 1) != 0)
            return false;
    	
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
}
