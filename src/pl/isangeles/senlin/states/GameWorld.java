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
import pl.isangeles.senlin.data.ItemBase;
import pl.isangeles.senlin.inter.UserInterface;
/**
 * State for game world
 * @author Isangeles
 *
 */
public class GameWorld extends BasicGameState
{
	private TiledMap areaMap;
	private Character player;
	private UserInterface ui;
	private int[] destPoint = {0, 0};
	private float[] cameraPos = {0f, 0f};
	
	public GameWorld(Character player)
	{
        this.player = player;
	}
	
    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException
    {
        try 
        {
        	ItemBase.loadBases(container);
        	
            areaMap = new TiledMap(new String("data" + File.separator + "area" + File.separator + "map" + File.separator + "area01.tmx"));
            ui = new UserInterface(container, player);
            
            player.addItem(ItemBase.getItem("wSOHI")); //test line
            player.addItem(ItemBase.getItem("wSOHI")); //test line
        } 
        catch (SlickException | IOException | FontFormatException e) 
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException
    {
        g.translate(-cameraPos[0], -cameraPos[1]);
        areaMap.render(0, 0);
        player.draw();
        g.translate(cameraPos[0], cameraPos[1]);
        ui.draw(g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException
    {
        if(!isPause())
            keyDown(container.getInput());
        
    	if(player.move(destPoint[0], destPoint[1]))
    	{
    		player.update(delta);
    	}
    	
    	if(ui.isExitReq())
    		container.exit();
    }
    
    @Override
    public void mouseReleased(int button, int x, int y)
    {
    	if(!ui.isMouseOver() && !isPause())
    	{
    		if(button == Input.MOUSE_LEFT_BUTTON)
        	{
        		destPoint[0] = x;
        		destPoint[1] = y;
        	}
    	}
    }
    
    @Override
    public void keyPressed(int key, char c)
    {
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
    }
    
    private boolean isPause()
    {
        return ui.isPauseReq();
    }

    @Override
    public int getID()
    {
        return 2;
    }
    

}
