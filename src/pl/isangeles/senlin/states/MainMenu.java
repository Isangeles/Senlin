/*
 * MainMenu.java
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

import pl.isangeles.senlin.audio.AudioPlayer;
import pl.isangeles.senlin.graphic.Sprite;
import pl.isangeles.senlin.inter.*;
import pl.isangeles.senlin.util.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.*;
import java.awt.FontFormatException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
/**
 * Class for main menu of the game
 * @author Isangeles
 *
 */
public class MainMenu extends BasicGameState
{
	private Sprite logo;
    private Button buttNewGame,
		   	       buttLoadGame,
		   	       buttOptions,
		   	       buttExit;
    private boolean closeReq;
    private boolean newGameReq;
    private boolean loadGameReq;
    private boolean settingsReq;
    private static AudioPlayer menuMusic = new AudioPlayer();
    private static GameCursor cursor;
    /* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException
    {
        try
        {
        	cursor = new GameCursor(container);
            container.setMouseCursor(cursor, Math.round(10 * Settings.getScale()), 0);
            logo = new Sprite(GConnector.getInput("field/logox3green.png"), "menuLogo", false);
        	buttNewGame = new Button(GConnector.getInput("button/menuButtonLongG.png"), "menuButtLong", false, TConnector.getText("menu", "ngName"), container);
        	buttLoadGame = new Button(GConnector.getInput("button/menuButtonLongG.png"), "menuButtLong", false, TConnector.getText("menu", "lgName"), container);
        	buttOptions = new Button(GConnector.getInput("button/menuButtonLongG.png"), "menuButtLong", false, TConnector.getText("menu", "settName"), container);
            buttExit = new Button(GConnector.getInput("button/menuButtonLongG.png"), "menuButtLong", false, TConnector.getText("menu", "exitName"), container);
            menuMusic.add("mainTheme.ogg");
            menuMusic.play(1.0f, 1.0f, "mainTheme.ogg");
        }
        catch(IOException | FontFormatException e)
        {
        	System.err.println(e.toString());
        }
    }
    /* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException
    {
    	logo.draw(0, 0, true);
    	buttNewGame.draw(0, 500);
    	buttLoadGame.draw(0, 650);
    	buttOptions.draw(0, 800);
        buttExit.draw(0, 950);
    }
    /* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException
    {
    	if(newGameReq)
    	{
    	    newGameReq = false;
    		game.enterState(1); 
    	}
    	if(loadGameReq)
    	{
    	    loadGameReq = false;
    	    game.enterState(4);
    	}
    	if(settingsReq)
    	{
    		settingsReq = false;
    		game.enterState(3);
    	}
    	if(closeReq)
    		container.exit();
    }
    /* (non-Javadoc)
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
    @Override
    public int getID()
    {
        return 0;
    }
    
    @Override
    public void mouseReleased(int button, int x, int y)
    {
    	if(button == Input.MOUSE_LEFT_BUTTON)
    	{
    	    if(buttNewGame.isMouseOver())
                newGameReq = true;
    	    if(buttLoadGame.isMouseOver())
    	        loadGameReq = true;
            if(buttOptions.isMouseOver())
                settingsReq = true;
            if(buttExit.isMouseOver())
                closeReq = true;
    	}
    }
    
    public static AudioPlayer getMusicPlayer()
    {
    	return menuMusic;
    }
    
    public static GameCursor getCursor()
    {
    	return cursor;
    }

}
