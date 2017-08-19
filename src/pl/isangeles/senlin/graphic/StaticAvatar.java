/*
 * StaticAvatar.java
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
package pl.isangeles.senlin.graphic;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.states.Global;

/**
 * Class for 'static' avatar (equipment changes do not affect avatar)
 * @author Isangeles
 *
 */
public class StaticAvatar extends Avatar 
{

	/**
	 * @param character
	 * @param gc
	 * @param spritesheet
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public StaticAvatar(Character character, GameContainer gc, String spritesheet) throws SlickException, IOException, FontFormatException 
	{
		super(character, gc, spritesheet);
	}
	
	@Override
	public void draw(float x, float y)
	{
		if(isTargeted)
			target.draw(x+target.getDis(15), y+target.getDis(50), false);
		
		torso.draw(x, y, 1.5f);
		ttf.drawString(x, (y-torso.getDis(25)), character.getName());
		
		avMOA.setLocation(Global.uiX(x), Global.uiY(y));
		
		if(avMOA.isMouseOver())
			avName.draw(x, y);
		
		if(isSpeaking && speechTime < 1500)
			speakWindow.draw(x, y);
		else if(speechTime > 1500)
		{
			isSpeaking = false;
			speechTime = 0;
		}
	}
	
	@Override
	public void update(int delta)
	{	
		avName.setText(character.getName() + System.lineSeparator() + character.getGuild().getName());
		setTargetSprite();
		
		torso.update(delta);
		
		if(isSpeaking)
		{
			speechTime += delta;
		}
	}

	@Override
	public void kill()
	{
		torso.lie(true);
	}
	@Override
	public void goUp()
	{
		torso.goUp();
	}
	@Override
	public void goRight()
	{
		torso.goRight();
	}
	@Override
	public void goDown()
	{
		torso.goDown();
	}
	@Override
	public void goLeft()
	{
		torso.goLeft();
	}
	@Override
	public void move(boolean trueFalse)
	{
		isMove = trueFalse;
		torso.move(trueFalse);
	}
	
	@Override
	public void meleeAnim()
	{
		torso.meleeAnim();
	}
	
	@Override
	public void rangeAnim()
	{
		torso.rangeAinm();
	}
	
	@Override
	public boolean isStatic()
	{
		return true;
	}
}
