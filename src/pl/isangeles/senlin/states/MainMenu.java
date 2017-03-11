package pl.isangeles.senlin.states;

import pl.isangeles.senlin.inter.*;
import pl.isangeles.senlin.util.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.*;
import java.awt.FontFormatException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
/**
 * Class for main menu of the game
 * @author Isangeles
 *
 */
public class MainMenu extends BasicGameState
{
    Button buttNewGame,
		   buttLoadGame,
		   buttOptions,
		   buttExit;
    boolean closeReq;
    boolean newGameReq;
    boolean settingsReq;

    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException
    {
        try
        {
        	buttNewGame = new Button(GConnector.getInput("button/menuButtonLong.png"), "menuButtNG", false, "New Game", container);
        	buttLoadGame = new Button(GConnector.getInput("button/menuButtonLong.png"), "menuButtLG", false, "Load Game", container);
        	buttOptions = new Button(GConnector.getInput("button/menuButtonLong.png"), "menuButtO", false, "Options", container);
            buttExit = new Button(GConnector.getInput("button/menuButtonLong.png"), "menuButtExit", false, "Exit", container);
        }catch(IOException | FontFormatException e)
        {
        	System.err.println(e.toString());
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException
    {
    	buttNewGame.draw(700, 400);
    	buttLoadGame.draw(700, 550);
    	buttOptions.draw(700, 700);
        buttExit.draw(700, 850);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException
    {
    	if(newGameReq)
    	{
    	    newGameReq = false;
    		game.enterState(1); 
    	}
    	if(settingsReq)
    	{
    		settingsReq = false;
    		game.enterState(3);
    	}
    	if(closeReq)
    		container.exit();
    }

    @Override
    public int getID()
    {
        return 0;
    }
    
    @Override
    public void mouseReleased(int button, int x, int y)
    {
    	if(buttNewGame.isMouseOver())
    		newGameReq = true;
    	if(button == Input.MOUSE_LEFT_BUTTON && buttOptions.isMouseOver())
    		settingsReq = true;
    	if(buttExit.isMouseOver())
    		closeReq = true;
    }

}
