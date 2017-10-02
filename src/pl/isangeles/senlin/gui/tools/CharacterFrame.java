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

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.gui.Bar;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for characters frames
 * @author Isangeles
 *
 */
class CharacterFrame extends TargetFrame 
{
	private Character character;
	
    private Bar magicka;
    private Bar experience;
	/**
	 * Character frame constructor
	 * @param gc Slick game container
	 * @param character Game character
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public CharacterFrame(GameContainer gc, Character character) throws SlickException, IOException, FontFormatException 
	{
		super(gc, character);
		this.character = (Character)super.target;
        magicka = new Bar(GConnector.getInput("ui/bar/manaBar.png"), "uiManaBar", false, gc, "Magicka:");
        experience = new Bar(GConnector.getInput("ui/bar/expBar.png"), "uiExpBar", false, gc, "Experience:");
	}
	
	@Override
	public void update()
	{
		super.update();
        magicka.update(character.getMagicka(), character.getMaxMagicka());
        experience.update(character.getExperience(), character.getMaxExperience());
	}
	
	@Override
	public void draw(float x, float y)
	{
		super.draw(x, y);
        textTtf.drawString(super.x+getDis(150), super.y+getDis(110), TConnector.getText("ui", "levelName") + ":" + target.getLevel());
        magicka.draw(x+getDis(139), y+getDis(62));
        experience.draw(x+getDis(139), y+getDis(88));
	}
	
	public void setCharacter(Character character)
	{
		super.setCharacter(character);
	}
}
