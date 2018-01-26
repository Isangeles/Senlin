/*
 * TargetableFrame.java
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
import pl.isangeles.senlin.util.Stopwatch;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.gui.Bar;
import pl.isangeles.senlin.gui.InterfaceObject;
/**
 * Class for targetable objects frame
 * TODO fix experience bar on resolutions different then default
 * @author Isangeles
 *
 */
class TargetFrame extends InterfaceObject
{
	protected Targetable target;
    private Bar health;
    private List<Effect> effects = new ArrayList<>();
    private MouseOverArea frameMOA;
    protected TrueTypeFont textTtf;
    /**
     * Targetable object frame constructor
     * @param gc Game container for superclass and frame elements
     * @param object Targetable object to display in frame
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    public TargetFrame(GameContainer gc, Targetable object) throws SlickException, IOException, FontFormatException
    {
        super(GConnector.getInput("ui/background/charFrameBG_DG.png"), "uiCharFrame", false, gc);
        this.target = object;
        
        health = new Bar(GConnector.getInput("ui/bar/hpBar.png"), "uiHpBar", false, gc, "Health:");
        
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
        health.update(target.getHealth(), target.getMaxHealth());
        effects = target.getEffects();
    }
    /**
     * Draws frame
     */
    @Override
    public void draw(float x, float y)
    {
        super.draw(x, y, false);
        target.getPortrait().draw(x+getDis(40), y+getDis(9), getSize(95f), getSize(130f), false);
        textTtf.drawString(super.x+getDis(150), super.y+getDis(15), target.getName());
        health.draw(x+getDis(139), y+getDis(36), false);
        
        //Draws effects
    	int row = 0;
    	int column = 0;
        for(Effect effect : effects)
        {
        	EffectTile icon = effect.getTile();
        	icon.draw(x+getDis(34) + (icon.getScaledWidth()*column), y+getDis(142) + ((icon.getScaledHeight()+getDis(15))*row), Stopwatch.timeFromMillis(effect.getTime()), false);
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
    	this.target = character;
    }

}
