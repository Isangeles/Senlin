package pl.isangeles.senlin.inter.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.MouseOverArea;

import pl.isangeles.senlin.inter.Bar;
import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.core.Character;
/**
 * Class for player character frame
 * @author Isangeles
 *
 */
public class CharacterFrame extends InterfaceObject
{
    Image portrait;
    String name;
    int level;
    Bar health;
    Bar magicka;
    Bar experience;
    MouseOverArea frameMOA;
    Font textFont;
    TrueTypeFont textTtf;
    /**
     * Character frame constructor
     * @param gc Game container for superclass and frame elements
     * @param player Player character to display in frame
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    public CharacterFrame(GameContainer gc, Character player) throws SlickException, IOException, FontFormatException
    {
        super(GConnector.getInput("ui/background/charFrameBG.png"), "uiCharFrame", false, gc);
        this.portrait = player.getPortrait();
        
        health = new Bar(GConnector.getInput("ui/bar/hpBar.png"), "uiHpBar", false, gc, "Health:", player.getHealth(), player.getMaxHealth());
        magicka = new Bar(GConnector.getInput("ui/bar/manaBar.png"), "uiManaBar", false, gc, "Magicka:", player.getMagicka(), player.getMaxMagicka());
        experience = new Bar(GConnector.getInput("ui/bar/expBar.png"), "uiExpBar", false, gc, "Experience:", player.getExperience(), player.getMaxExperience());
        name = player.getName();
        level = player.getLevel();
        
        File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
        textFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        textTtf = new TrueTypeFont(textFont.deriveFont(11f), true);
        
        frameMOA = new MouseOverArea(gc, super.baseTex, 0, 0);
    }
    /**
     * Updates frame
     * @param player Player character to update frame 
     */
    public void update(Character player)
    {
        health.update(player.getHealth(), player.getMaxHealth());
        magicka.update(player.getMagicka(), player.getMaxMagicka());
        experience.update(player.getExperience(), player.getMaxExperience());
        name = player.getName();
        level = player.getLevel();
    }
    /**
     * Draws frame
     */
    public void draw(float x, float y)
    {
        super.draw(x, y);
        portrait.draw(super.x+40, super.y+9, 95f, 130f);
        textTtf.drawString(x+150, y+15, name);
        textTtf.drawString(x+150, y+110, "Level:" + level);
        health.draw(x+139, y+36);
        magicka.draw(x+139, y+62);
        experience.draw(x+139, y+88);

        frameMOA.setLocation(super.x, super.y);
    }
    /**
     * Checks if mouse is over frame
     * @return True if mouse is over false otherwise
     */
    public boolean isMouseOver()
    {
    	return frameMOA.isMouseOver();
    }

}
