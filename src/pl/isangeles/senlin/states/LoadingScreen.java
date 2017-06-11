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
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import pl.isangeles.senlin.inter.Field;
import pl.isangeles.senlin.inter.ui.UserInterface;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.core.Character;
/**
 * Class for game loading state
 * @author Isangeles
 *
 */
public class LoadingScreen extends BasicGameState
{
    private Field loadingInfo;
    private Character player;
    private UserInterface ui;
    private GameWorld gw;
    private int loadCounter;
    
    public LoadingScreen(Character player)
    {
    	this.player = player;
    }
    
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

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException
    {
        g.clear();
        loadingInfo.draw(loadingInfo.atCenter().x, loadingInfo.atCenter().y, 250f, 100f, false);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException
    {
		try 
		{
			switch(loadCounter)
			{
			case 0:
	            loadingInfo.setText("loading user interface...");
				break;
			case 1:
				ui = new UserInterface(container, player);
				break;
			case 2:
				loadingInfo.setText("loading game world...");
				break;
			case 3:
		        gw = new GameWorld(player, ui);
		        break;
			case 4:
		        game.addState(gw);
		        game.getState(gw.getID()).init(container, game);
		        game.enterState(gw.getID());
		        break;
			}
			
			loadCounter ++;
		} 
		catch (IOException | FontFormatException e) 
		{
			e.printStackTrace();
		}
    }

    @Override
    public int getID()
    {
        return 4;
    }

}
