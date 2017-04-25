package pl.isangeles.senlin.states;

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.xml.sax.SAXException;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.data.ItemBase;
import pl.isangeles.senlin.data.NpcBase;
import pl.isangeles.senlin.data.SkillsBase;
import pl.isangeles.senlin.inter.ui.UserInterface;
/**
 * State for game world
 * @author Isangeles
 *
 */
public class GameWorld extends BasicGameState
{
	private TiledMap areaMap;
	private Character player;
	private List<Character> areaNpcs = new ArrayList<>(); 
	private UserInterface ui;
	private int[] destPoint;
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
        	NpcBase.load(container);     	
        	
            areaMap = new TiledMap(new String("data" + File.separator + "area" + File.separator + "map" + File.separator + "area01.tmx"));
            ui = new UserInterface(container, player);
            
            player.setPosition(500, 250);
            destPoint = new int[]{500, 250};
            		
            player.addItem(ItemBase.getItem("wSOHI")); //test line
            player.addItem(ItemBase.getItem("wSOHI")); //test line
            Character testBandit = NpcBase.spawn("bandit01"); //test line
            testBandit.setPosition(600, 250); //test line
            areaNpcs.add(testBandit); //test line
        } 
        catch (SlickException | IOException | FontFormatException | ParserConfigurationException | SAXException e) 
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
        for(Character npc : areaNpcs)
        	npc.draw();
        g.translate(cameraPos[0], cameraPos[1]);
        ui.draw(g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException
    {
        if(!isPause())
            keyDown(container.getInput());
        
    	player.move(destPoint[0], destPoint[1]);
    	
    	player.update(delta);
    	for(Character npc : areaNpcs)
    		npc.update(delta);
    	
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
