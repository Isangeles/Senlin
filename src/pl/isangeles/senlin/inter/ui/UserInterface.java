package pl.isangeles.senlin.inter.ui;


import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.inter.GameCursor;
import pl.isangeles.senlin.inter.Warning;
import pl.isangeles.senlin.inter.ui.*;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.core.Character;
/**
 * Class containing all ui elements
 * @author Isangeles
 *
 */
public class UserInterface
{
    private Character player;
    private Console gameConsole;
    private BottomBar bBar;
    private CharacterFrame charFrame;
    private CharacterFrame targetFrame;
    private InGameMenu igMenu;
    private InvetoryMenu inventory;
    private SkillsMenu skills;
    private Warning uiWarning;
    /**
     * UI constructor, calls all ui elements constructors
     * @param gc Game container for superclass and ui elements
     * @param player Player character 
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    public UserInterface(GameContainer gc, Character player) throws SlickException, IOException, FontFormatException
    {
        this.player = player;
        
        gameConsole = new Console(gc, player);
        bBar = new BottomBar(gc, player);
        charFrame = new CharacterFrame(gc, player);
        targetFrame = new CharacterFrame(gc, player);
        igMenu = new InGameMenu(gc);
        inventory = new InvetoryMenu(gc, player);
        skills = new SkillsMenu(gc, player);
        uiWarning = new Warning(gc, "");
    }
    /**
     * Draws ui elements
     */
    public void draw(Graphics g)
    {
        gameConsole.draw(Coords.getX("TR", gameConsole.getWidth()+10), Coords.getY("BR", gameConsole.getHeight()+20), g);
        
        bBar.draw(Coords.getX("BL", 200), Coords.getY("BL", 70));
        charFrame.draw(Coords.getX("TL", 10), Coords.getY("TL", 10));
        if(Global.getTarChar() != null)
        	targetFrame.draw(Coords.getX("CE", 0), Coords.getY("TR", 0));
        
        if(bBar.isMenuReq())
        	igMenu.draw(Coords.getX("CE", -100), Coords.getY("CE", -100));
        if(igMenu.isResumeReq() || !bBar.isMenuReq())
        {
        	bBar.hideMenu();
        	igMenu.reset();
        	inventory.reset();
        }
        if(bBar.isInventoryReq())
            inventory.draw(Coords.getX("CE", -500), Coords.getY("CE", -200));
        if(bBar.isSkillsReq())
        	skills.draw(Coords.getX("CE", -500), Coords.getY("CE", -200));
        
        update();     	
    }
    /**
     * Updates ui elements
     */
    private void update()
    {
    	if(Global.getTarChar() != null)
    		targetFrame.setCharacter(Global.getTarChar());
    	
        charFrame.update();
        targetFrame.update();
        inventory.update();
        skills.update();
    }
    /**
     * Checks if mouse is over one of ui elements
     * @return
     */
    public boolean isMouseOver()
    {
    	return bBar.isMouseOver() || igMenu.isMouseOver() || charFrame.isMouseOver() || inventory.isMouseOver();
    }
    /**
     * Checks if exit game is requested
     * @return True if game should be closed or false otherwise
     */
    public boolean isExitReq()
    {
    	return igMenu.isExitReq();
    }
    public boolean isPauseReq()
    {
        return !gameConsole.isHidden() || bBar.isPauseReq();
    }
    
}
