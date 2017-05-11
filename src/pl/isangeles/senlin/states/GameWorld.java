package pl.isangeles.senlin.states;

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.CursorLoader;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.xml.sax.SAXException;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.ai.CharacterAi;
import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.data.DialoguesBase;
import pl.isangeles.senlin.data.GuildsBase;
import pl.isangeles.senlin.data.ItemBase;
import pl.isangeles.senlin.data.NpcBase;
import pl.isangeles.senlin.data.SkillsBase;
import pl.isangeles.senlin.inter.GameCursor;
import pl.isangeles.senlin.inter.ui.UserInterface;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.Settings;
/**
 * State for game world
 * 
 * System cursor currently in use
 * @author Isangeles
 *
 */
public class GameWorld extends BasicGameState
{
	private TiledMap areaMap;
	private Character player;
	private List<Character> areaNpcs = new ArrayList<>();
	private CharacterAi npcsAi;
	private UserInterface ui;
	private float[] cameraPos = {0f, 0f};
	private GameCursor gwCursor;
	
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
        	gwCursor = new GameCursor(container);
        	
        	ItemBase.loadBases(container);
        	GuildsBase.load();
        	NpcBase.load(container);
        	DialoguesBase.load();
        	
            areaMap = new TiledMap(new String("data" + File.separator + "area" + File.separator + "map" + File.separator + "area01.tmx"));
            ui = new UserInterface(container, player);
            
           
            player.setPosition(500, 250);
            player.addItem(ItemBase.getItem("wSOHI")); //test line
            player.addItem(ItemBase.getItem("wSOHI")); //test line
            Character testBandit = NpcBase.spawn("bandit01"); //test line
            testBandit.setPosition(900, 250); //test line
            areaNpcs.add(testBandit); //test line

        	npcsAi = new CharacterAi(this);
            npcsAi.addNpcs(areaNpcs);
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
        //gwCursor.draw();
        
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException
    {
        if(!isPause())
            keyDown(container.getInput());
    	
    	player.update(delta);
    	npcsAi.update(delta);
    	
    	if(ui.isExitReq())
    		container.exit();
    }
    /**
     * Returns all nearby characters in area 
     * @param character A character around which to look for other nearby characters
     * @return List with all nearby characters
     */
    public List<Character> getNearbyCharacters(Character character)
    {
    	List<Character> nearbyCharacters = new ArrayList<>();
    	
    	if(character.getRangeFrom(player.getPosition()) < 200)
    		nearbyCharacters.add(player);
    	
    	for(Character npc : areaNpcs)
    	{
    		if(npc != character && character.getRangeFrom(npc.getPosition()) < 200)
        		nearbyCharacters.add(npc);
    	}
    	
    	return nearbyCharacters;
    }
    
    @Override
    public void mouseReleased(int button, int x, int y)
    {
    	if(!ui.isMouseOver() && !isPause())
    	{
    		if(button == Input.MOUSE_LEFT_BUTTON)
    		{
    			player.moveTo((int)Global.worldX(x), (int)Global.worldY(y));
    			Log.addInformation("Move: " + (int)Global.worldX(x) + "/" + (int)Global.worldY(y)); //TEST LINE
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
        Global.setCamerPos(cameraPos[0], cameraPos[1]);
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
