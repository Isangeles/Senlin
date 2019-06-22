/*
 * CharacterAvatar.java
 * 
 * Copyright 2017 Dariusz Sikora <dev@isangeles.pl>
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
import pl.isangeles.senlin.gui.InfoWindow;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;

/**
 * Interface for characters graphical representations
 * @author Isangeles
 *
 */
public abstract class CharacterAvatar implements MouseListener, Effective
{
	protected InfoWindow avName;
	protected InfoWindow speakWindow;
	
	protected Character character;
	
	protected TrueTypeFont ttf;
	
	protected boolean isMove;
	protected boolean isSpeaking;
	protected int speechTime;
	
	private List<SimpleAnim> effects = new ArrayList<>();
	private List<SimpleAnim> loopEffects = new ArrayList<>();
	private List<SimpleAnim> effectsToRemove = new ArrayList<>();
	/**
	 * Character avatar constructor
	 * @param character Game character
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public CharacterAvatar(Character character, GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		gc.getInput().addMouseListener(this);
		this.character = character;
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		ttf = new TrueTypeFont(font.deriveFont(11f), true);
		
		avName = new InfoWindow(gc, character.getName());
		speakWindow = new InfoWindow(gc, "");
	}
    /**
     * Draws avatar at specified position
     * @param x Position on X axis
     * @param y Position on Y axis
     */
    public void draw(float x, float y)
    {
		for(SimpleAnim effect : loopEffects)
		{
			effect.draw(x+effect.getDis(25), y+effect.getDis(90), Coords.getScale(), false);
		}
		
		for(SimpleAnim effect : effects)
		{
			effect.draw(x+effect.getDis(25), y+effect.getDis(90), Coords.getScale(), false);
		}
		
		if(isSpeaking && speechTime < 1500)
			speakWindow.draw(x, y);
		else if(speechTime > 1500)
		{
			isSpeaking = false;
			speechTime = 0;
		}
    }
    /**
     * Updates avatar
     * @param delta
     */
    public void update(int delta)
    {
		avName.setText(character.getName() + System.lineSeparator() + character.getGuild().getName());
		
    	if(isSpeaking)
		{
			speechTime += delta;
		}
		
		for(SimpleAnim effect : effects)
		{
			if(effect.isLastFrame())
				effectsToRemove.add(effect);
		}
		effects.removeAll(effectsToRemove);
		effectsToRemove.clear();
    }
    
    public abstract void goUp();
    
    public abstract void goRight();
    
    public abstract void goDown();
    
    public abstract void goLeft();
    
    public abstract void kneel();
    
    public abstract void lie();
    /**
     * Turns move animation on or off
     * @param move True to turn animation on, false to turn off
     */
    public abstract void move(boolean move);
    /**
     * Starts melee attack animation 
     * @param True if animation should be looped, false otherwise
     */
    public abstract void meleeAnim(boolean loop);
    /**
     * Starts range attack animation
     * @param True if animation should be looped, false otherwise
     */
    public abstract void rangeAnim(boolean loop);
    /**
     * Starts casting animation
     * @param True if animation should be looped, false otherwise
     */
    public abstract void castAnim(boolean loop);
    /**
     * Stops all avatar animations
     */
    public abstract void stopAnim();
    /**
     * Resets avatar stance
     */
    public abstract void resetStance();
    /**
     * Displays specified text above avatar head
     * @param text Text to display
     */
	public void speak(String text)
	{
		speakWindow.setText(text);
		isSpeaking = true;
	}
    /**
     * Returns object direction
     * @return Direction id (0 - up, 1 - right, 2 - down, 3 - left)
     */
    public abstract int getDirection();
    /**
     * Checks if avatar is static
     * @return True if avatar is static, false otherwise
     */
    public abstract boolean isStatic();
	/**
	 * Checks if mouse is over avatar
	 * @return True if mouse is over avatar, false otherwise
	 */
	public abstract boolean isMouseOver();
	/**
	 * Checks if avatar is in move
	 * @return
	 */
	public boolean isMove()
	{
		return isMove;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.graphic.Effective#addEffect(pl.isangeles.senlin.graphic.SimpleAnim, boolean)
	 */
	@Override
	public boolean addEffect(SimpleAnim effect, boolean loop)
	{
		if(effect != null)
		{
			if(loop)
				return loopEffects.add(effect);
			else
				return effects.add(effect);
		}
		else
			return false;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.graphic.Effective#removeEffect(pl.isangeles.senlin.graphic.SimpleAnim)
	 */
	@Override
	public boolean removeEffect(SimpleAnim effect) 
	{
		return loopEffects.remove(effect);
	}
    /**
     * Returns avatar default torso
     * @return Animated object
     */
    public abstract AnimObject getDefTorso();

	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#inputEnded()
	 */
	@Override
	public void inputEnded() 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#inputStarted()
	 */
	@Override
	public void inputStarted() 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#isAcceptingInput()
	 */
	@Override
	public boolean isAcceptingInput() 
	{
		return true;
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#setInput(org.newdawn.slick.Input)
	 */
	@Override
	public void setInput(Input input) 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseClicked(int, int, int, int)
	 */
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseDragged(int, int, int, int)
	 */
	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseMoved(int, int, int, int)
	 */
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy)
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mousePressed(int, int, int)
	 */
	@Override
	public void mousePressed(int button, int x, int y) 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseReleased(int, int, int)
	 */
	@Override
	public void mouseReleased(int button, int x, int y) 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseWheelMoved(int)
	 */
	@Override
	public void mouseWheelMoved(int change) 
	{
	}
}
