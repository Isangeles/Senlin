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

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.MouseOverArea;

import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.gui.InfoWindow;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.GConnector;

/**
 * Class for 'static' avatar (equipment changes do not affect avatar)
 * @author Isangeles
 *
 */
public class StaticAvatar extends CharacterAvatar implements MouseListener
{
    protected AnimObject torso;
    private AnimObject defTorso;
    
    protected MouseOverArea avMOA;
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
        super(character, gc);
        defTorso = new AnimObject(GConnector.getInput("sprite/mob/"+spritesheet), spritesheet, false, 80, 90);
        defTorso.setName(spritesheet);
        
        torso = defTorso;
        
        avMOA = new MouseOverArea(gc, torso.getCurrentSprite(), 0, 0);
	}
	
	@Override
	public void draw(float x, float y)
	{
		super.draw(x, y);
		
		torso.draw(x, y, 1.5f);
		
		avMOA.setLocation(Global.uiX(x), Global.uiY(y));
		if(avMOA.isMouseOver())
			avName.draw(x, y);
	}
	
	@Override
	public void update(int delta)
	{	
		super.update(delta);
		torso.update(delta);
	}

	@Override
	public void lie()
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

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.graphic.CharacterAvatar#kneel()
     */
    @Override
    public void kneel()
    {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.graphic.CharacterAvatar#castAnim()
     */
    @Override
    public void castAnim()
    {
        // TODO Auto-generated method stub
        
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.graphic.CharacterAvatar#getDirection()
     */
    @Override
    public int getDirection()
    {
        return torso.getDirection();
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.graphic.CharacterAvatar#isMouseOver()
     */
    @Override
    public boolean isMouseOver()
    {
        return avMOA.isMouseOver();
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.graphic.CharacterAvatar#getDefTorso()
     */
    @Override
    public AnimObject getDefTorso()
    {
        return defTorso;
    }
}
