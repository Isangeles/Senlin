/*
 * LoadingScreen.java
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
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.xml.sax.SAXException;

import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.cli.CommandInterface;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.data.DialoguesBase;
import pl.isangeles.senlin.data.GuildsBase;
import pl.isangeles.senlin.data.ItemsBase;
import pl.isangeles.senlin.data.NpcBase;
import pl.isangeles.senlin.data.ObjectsBase;
import pl.isangeles.senlin.data.QuestsBase;
import pl.isangeles.senlin.data.RecipesBase;
import pl.isangeles.senlin.data.ScenariosBase;
import pl.isangeles.senlin.data.save.SaveEngine;
import pl.isangeles.senlin.data.save.SavedGame;
import pl.isangeles.senlin.gui.Field;
import pl.isangeles.senlin.gui.elements.UserInterface;
/**
 * Class for game loading state
 * @author Isangeles
 *
 */
public class LoadingScreen extends BasicGameState
{
    private Field loadingInfo;
    private Character player;
    private SavedGame gameToLoad;
    private String saveName;
    private UserInterface ui;
    private CommandInterface cli;
    private GameWorld gw;
    private String loadType;
    private int loadCounter;
    /**
     * Creates new game loader
     * @param player Player character
     */
    public LoadingScreen(Character player)
    {
    	this.player = player;
    	loadType = "newGame";
    }
    /**
     * Creates saved game loader
     * @param gameToLoad Saved game
     */
    public LoadingScreen(String saveName)
    {
        this.saveName = saveName;
        loadType = "savedGame";
    }
    /* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException
    {
        try
        {
            loadingInfo = new Field(100f, 70f, "Loading...", container);
        } 
        catch (IOException | FontFormatException e)
        {
            e.printStackTrace();
        }
    }
    /* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException
    {
        g.clear();
        loadingInfo.draw(loadingInfo.atCenter().x, loadingInfo.atCenter().y, 250f, 100f, false);
    }
    /* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException
    {
		try 
		{
			switch(loadCounter)
			{
			case 0:
			    loadingInfo.setText("loding game data...");
			    break;
			case 1:
	            ItemsBase.loadBases(container);
	            RecipesBase.load();
	            GuildsBase.load();
	            NpcBase.load(container);
	            DialoguesBase.load("dPrologue");
	            QuestsBase.load("qPrologue");
	            ObjectsBase.load("gameObjects", container);
	            ScenariosBase.load();
			    break;
			case 2:
			    if(loadType.equals("savedGame"))
			        loadingInfo.setText("loading saved game...");
			    break;
			case 3:
			    if(loadType.equals("savedGame"))
			    {
			        gameToLoad = SaveEngine.load(saveName, container);
			        player = gameToLoad.getPlayer();
			        Global.setPlayer(gameToLoad.getPlayer());
			    }
			    break;
            case 4:
                loadingInfo.setText("loading user interface...");
                break;
            case 5:
            	cli = new CommandInterface(player);
                ui = new UserInterface(container, cli, player);
                if(loadType.equals("savedGame"))
                {
                	ui.setBBarLayout(gameToLoad.getBBarLayout());
                	ui.setInvLayout(gameToLoad.getInvLayout());
                	ui.getCamera().setPos(gameToLoad.getCameraPos());
                }
                break;
			case 6:
				loadingInfo.setText("loading game world...");
				break;
			case 7:
		        if(loadType.equals("newGame"))
		            gw = new GameWorld(player, ui);
		        if(loadType.equals("savedGame"))
		            gw = new GameWorld(gameToLoad, ui);
		        break;
			case 8:
		        game.addState(gw);
		        game.getState(gw.getID()).init(container, game);
		        loadCounter = 0;
		        game.enterState(gw.getID());
		        break;
			}
			
			loadCounter ++;
		} 
		catch (IOException | FontFormatException | ParserConfigurationException | SAXException e) 
		{
			e.printStackTrace();
		}
    }
    /* (non-Javadoc)
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
    @Override
    public int getID()
    {
        return 4;
    }

}
