package pl.isangeles.senlin.states;

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.inter.UserInterface;

public class GameWorld extends BasicGameState
{
	private TiledMap areaMap;
	private Character player;
	private UserInterface ui;
	private int[] destPoint = {0, 0};
	
	public GameWorld(Character player)
	{

        this.player = player;
	}
	
	public void addPlayer(Character player)
	{
	}
	
    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException
    {
        try 
        {
            areaMap = new TiledMap(new String("data" + File.separator + "area" + File.separator + "map" + File.separator + "area01.tmx"));
            ui = new UserInterface(container);
        } 
        catch (SlickException e) 
        {
            System.err.println(e.getMessage());
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (FontFormatException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException
    {
    	areaMap.render(0, 0);
    	player.draw();
    	ui.draw();
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException
    {
    	if(player.move(destPoint[0], destPoint[1]))
    	{
    		player.update(delta);
    	}
    }
    
    @Override
    public void mouseReleased(int button, int x, int y)
    {
    	if(!ui.isMouseOver())
    	{
    		if(button == Input.MOUSE_LEFT_BUTTON)
        	{
        		destPoint[0] = x;
        		destPoint[1] = y;
        	}
    	}
    }

    @Override
    public int getID()
    {
        return 2;
    }

}
