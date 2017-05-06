package pl.isangeles.senlin.inter.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.MouseOverArea;

import pl.isangeles.senlin.inter.Bar;
import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Targetable;
/**
 * Class for player character frame
 * TODO fix experience bar on resolutions different then default
 * @author Isangeles
 *
 */
class CharacterFrame extends InterfaceObject
{
	private Targetable character;
    private Bar health;
    private Bar magicka;
    private Bar experience;
    private MouseOverArea frameMOA;
    private TrueTypeFont textTtf;
    /**
     * Character frame constructor
     * @param gc Game container for superclass and frame elements
     * @param player Player character to display in frame
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    public CharacterFrame(GameContainer gc, Targetable character) throws SlickException, IOException, FontFormatException
    {
        super(GConnector.getInput("ui/background/charFrameBG.png"), "uiCharFrame", false, gc);
        this.character = character;
        
        health = new Bar(GConnector.getInput("ui/bar/hpBar.png"), "uiHpBar", false, gc, "Health:");
        magicka = new Bar(GConnector.getInput("ui/bar/manaBar.png"), "uiManaBar", false, gc, "Magicka:");
        experience = new Bar(GConnector.getInput("ui/bar/expBar.png"), "uiExpBar", false, gc, "Experience:");
        
        File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
        Font textFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        textTtf = new TrueTypeFont(textFont.deriveFont(11f), true);
        
        frameMOA = new MouseOverArea(gc, this, 0, 0);
    }
    /**
     * Updates frame
     * @param player Player character to update frame 
     */
    public void update()
    {
        health.update(character.getHealth(), character.getMaxHealth());
        magicka.update(character.getMagicka(), character.getMaxMagicka());
        experience.update(character.getExperience(), character.getMaxExperience());
    }
    /**
     * Draws frame
     */
    public void draw(float x, float y)
    {
        super.draw(x, y);
        character.getPortrait().draw(x+40, y+9, 95f, 130f);
        textTtf.drawString(super.x+getDis(150), super.y+getDis(15), character.getName());
        textTtf.drawString(super.x+getDis(150), super.y+getDis(110), "Level:" + character.getLevel());
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
    
    public void setCharacter(Targetable character)
    {
    	this.character = character;
    }

}
