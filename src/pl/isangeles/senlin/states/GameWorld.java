package pl.isangeles.senlin.states;

import java.io.File;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class GameWorld extends BasicGameState
{
	TiledMap areaMap;
	
    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException
    {
    	areaMap = new TiledMap(new String("data" + File.separator + "area" + File.separator + "map" + File.separator + "area01.tmx"));
    	
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException
    {
    	areaMap.render(0, 0);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException
    {
    }

    @Override
    public int getID()
    {
        return 2;
    }

}
