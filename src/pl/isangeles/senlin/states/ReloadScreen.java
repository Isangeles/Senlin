/*
 * ReloadScreen.java
 * 
 * Copyright 2017-2018 Dariusz Sikora <darek@pc-solus>
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

import pl.isangeles.senlin.data.ScenariosBase;
import pl.isangeles.senlin.data.area.Exit;
import pl.isangeles.senlin.data.area.Scenario;
import pl.isangeles.senlin.gui.InfoField;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.character.Character;

/**
 * State for reloading game world
 * @author Isangeles
 *
 */
public class ReloadScreen extends BasicGameState 
{
    private InfoField loadingInfo;
    private Exit scExit;
    private GameWorld gwToReload;
    private int loadCounter;
    
    public void load(Exit scExit, GameWorld gwToReload)
    {
    	this.scExit = scExit;
    	this.gwToReload = gwToReload;
    }
	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
		try
        {
            loadingInfo = new InfoField(Coords.getSize(200f), Coords.getSize(70f), "Loading...", container);
        } 
        catch (IOException | FontFormatException e)
        {
        	System.err.println("reloading screen initialization error:" + e.getMessage());
            e.printStackTrace();
        }
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	{
		g.clear();
        loadingInfo.draw(loadingInfo.atCenter().x, loadingInfo.atCenter().y, 250f, 100f, false);
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
		switch(loadCounter)
		{
		case 0:
			loadingInfo.setText("loading area scenario...");
			break;
		case 1:
        	scExit = gwToReload.getExitToNewArea();
			if(scExit != null)
			{
	    		Log.addSystem("try to load sc:" + scExit.getScenarioId());
				Scenario sc = ScenariosBase.getScenario(scExit.getScenarioId());
		    	if(sc != null)
		    	{
		    		Log.addSystem("loading sc:" + sc.getId());
		    		Character player = gwToReload.getPlayer(); 
					player.setArea(sc.getMainArea());
			    	player.setPosition(scExit.getToPos());
					gwToReload.setScenario(sc, container);
		    	}
		    	else
		    	{
		    		Log.addSystem("scenario_load_fail-msg//scenario not found:" + scExit.getScenarioId());
					game.enterState(2);
		    	}
			}
			else
			{
	    		Log.addSystem("scenario_load_fail-msg//no exit to new area");
				game.enterState(2);
			}
			break;
		case 2:
			loadingInfo.setText("loading game world...");
			break;
		case 3:
			game.getState(2).init(container, game);
			loadCounter = 0;
			game.enterState(2);
			break;
		}
		loadCounter ++;
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
	@Override
	public int getID() 
	{
		return 5;
	}

}
