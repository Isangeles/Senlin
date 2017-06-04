package pl.isangeles.senlin.states;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import pl.isangeles.senlin.inter.Field;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;

public class LoadingScreen extends BasicGameState
{
    private Field loadingInfo;
    private BasicGameState stateToLoad;
    
    public LoadingScreen(BasicGameState stateToLoad)
    {
        this.stateToLoad = stateToLoad;
    }
    
    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException
    {
        try
        {
            loadingInfo = new Field(100f, 70f, "Loading...", container);
        } catch (IOException | FontFormatException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException
    {
        g.clear();
        loadingInfo.draw(Coords.getX("CE", 0), Coords.getY("CE", 0));
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException
    {
        
        game.addState(stateToLoad);
        game.getState(stateToLoad.getID()).init(container, game);
        game.enterState(stateToLoad.getID());
    }

    @Override
    public int getID()
    {
        return 4;
    }

}
