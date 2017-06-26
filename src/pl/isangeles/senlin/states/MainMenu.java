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
    private static AudioPlayer menuMusic;

    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException
    {
        try
        {
            container.setMouseCursor(new GameCursor(container), Math.round(10 * Settings.getScale()), 0);
            logo = new Sprite(GConnector.getInput("field/logox3green.png"), "menuLogo", false);
        	buttNewGame = new Button(GConnector.getInput("button/menuButtonLongG.png"), "menuButtNG", false, TConnector.getText("menu", "ngName"), container);
        	buttLoadGame = new Button(GConnector.getInput("button/menuButtonLongG.png"), "menuButtLG", false, TConnector.getText("menu", "lgName"), container);
        	buttOptions = new Button(GConnector.getInput("button/menuButtonLongG.png"), "menuButtO", false, TConnector.getText("menu", "settName"), container);
            buttExit = new Button(GConnector.getInput("button/menuButtonLongG.png"), "menuButtExit", false, TConnector.getText("menu", "exitName"), container);
            menuMusic = new AudioPlayer();
            menuMusic.add("mainTheme.ogg");
            menuMusic.play(1.0f, 1.0f);
        }
        catch(IOException | FontFormatException e)
        {
        	System.err.println(e.toString());
        }
    }

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

}
