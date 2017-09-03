/*
 * CharacterFrame.java
 * 
 * Copyright 2017 Dariusz Sikora <darek@darek-PC-LinuxMint18>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */
package pl.isangeles.senlin.gui.tools;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.MouseOverArea;

import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.skill.Buff;
import pl.isangeles.senlin.gui.Bar;
import pl.isangeles.senlin.gui.InterfaceObject;
import pl.isangeles.senlin.gui.InterfaceTile;
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
    private List<InterfaceTile> effectsIcons = new ArrayList<>();
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
        super(GConnector.getInput("ui/background/charFrameBG_DG.png"), "uiCharFrame", false, gc);
        this.character = character;
        
        health = new Bar(GConnector.getInput("ui/bar/hpBar.png"), "uiHpBar", false, gc, "Health:");
        magicka = new Bar(GConnector.getInput("ui/bar/manaBar.png"), "uiManaBar", false, gc, "Magicka:");
        experience = new Bar(GConnector.getInput("ui/bar/expBar.png"), "uiExpBar", false, gc, "Experience:");
        
        File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
        Font textFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        textTtf = new TrueTypeFont(textFont.deriveFont(getSize(11f)), true);
        
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
        effectsIcons.clear();
        for(Effect effect : character.getEffects())
        {
        	if(!effectsIcons.contains(effect.getTile()))
        		effectsIcons.add(effect.getTile());
        }
    }
    /**
     * Draws frame
     */
    public void draw(float x, float y)
    {
        super.draw(x, y);
        character.getPortrait().draw(x+getDis(40), y+getDis(9), getSize(95f), getSize(130f));
        textTtf.drawString(super.x+getDis(150), super.y+getDis(15), character.getName());
        if(Character.class.isInstance(character))
        {
            textTtf.drawString(super.x+getDis(150), super.y+getDis(110), TConnector.getText("ui", "levelName") + ":" + character.getLevel());
            health.draw(x+getDis(139), y+getDis(36));
            magicka.draw(x+getDis(139), y+getDis(62));
            experience.draw(x+getDis(139), y+getDis(88));
        }
        
        //Draws effects
    	int row = 0;
    	int column = 0;
        for(InterfaceTile icon : effectsIcons)
        {
        	icon.draw(x+getDis(34) + (icon.getScaledWidth()*column), y+getDis(142) + (icon.getScaledHeight()*row), false);
        	column ++;
        	if(column == 6)
        	{
        		row ++;
        		column = 0;
        	}
        }

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
    /**
     * Sets game object for that frame 
     * @param character Targetable game object
     */
    public void setCharacter(Targetable character)
    {
    	this.character = character;
    }

}
