/*
 * CastBar.java
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

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.gui.Bar;
import pl.isangeles.senlin.gui.InterfaceObject;
import pl.isangeles.senlin.util.GConnector;
/**
 * Class for cast bar
 * @author Isangeles
 *
 */
public class CastBar extends InterfaceObject implements UiElement
{
    private Character player;
    private Bar castBar;
    private Skill castedSkill;
    
    public CastBar(GameContainer gc, Character player) throws SlickException, IOException, FontFormatException
    {
        super(GConnector.getInput("ui/background/castBG.png"), "uiCastBg", false, gc);
        castBar = new Bar(GConnector.getInput("ui/bar/castBar.png"), "uiCastBar", false, gc, "Progress: ");
        
        this.player = player;
    }
    
    @Override
    public void draw(float x, float y)
    {
        if(player.getAvatar().isCasting())
        {
            super.draw(x, y, false);
            castBar.draw(x+getDis(10), y+getDis(10));
        }
    }
    /**
     * Updates cast bar
     */
    @Override
    public void update()
    {
        castBar.update(player.getAvatar().getCastProgress(), player.getAvatar().getCastDuration());
    }

    @Override
    public void reset()
    {
    }

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.elements.UiElement#close()
	 */
	@Override
	public void close() 
	{
		// TODO Auto-generated method stub
		
	}
    
    
}
