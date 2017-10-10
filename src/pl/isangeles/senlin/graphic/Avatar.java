/*
 * Avatar.java
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
import org.newdawn.slick.gui.MouseOverArea;

import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.util.*;
/**
 * Graphical representation of character
 * @author Isangeles
 *
 */
public class Avatar extends CharacterAvatar
{
	protected AnimObject torso;
	private AnimObject head;
	private AnimObject weapon;
	
	private AnimObject defTorso;
	private AnimObject defHead;

	protected MouseOverArea avMOA;
	/**
	 * Character avatar constructor
	 * @param character Character to represent by avatar
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Avatar(Character character, GameContainer gc, String spritesheet) throws SlickException, IOException, FontFormatException
	{
		super(character, gc);
		if(isStatic())
		{
			defTorso = new AnimObject(GConnector.getInput("sprite/mob/"+spritesheet), spritesheet, false, 80, 90);
			defTorso.setName(spritesheet);
		}
		else
		{
			defTorso = new AnimObject(GConnector.getInput("sprite/avatar/"+spritesheet), spritesheet, false, 80, 90);
			defTorso.setName(spritesheet);
			defHead = new AnimObject(GConnector.getInput("sprite/avatar/"+character.getGender().getSSName("headBlack-1222211-80x90.png")), "headBlackSS" + character.getId(), false, 80, 90);
		}
		
		torso = defTorso;
		head = defHead;
		
		avMOA = new MouseOverArea(gc, defTorso.getCurrentSprite(), 0, 0);
	}
	/**
	 * Draws avatar
	 * @param x Position on x-axis
	 * @param y Position on y-axis
	 * @param chest Chest equipped by character
	 * @param helmet Helmet equipped by character
	 * @param weaponType Weapon equipped by character
	 */
	@Override
	public void draw(float x, float y)
	{
    	x -= Coords.getDis(35);
		y -= Coords.getDis(90);
		
		super.draw(x, y);
		
		avMOA.setLocation(Global.uiX(x), Global.uiY(y));
		if(avMOA.isMouseOver())
		{
			avName.draw(x, y);
		}
		
		if(isTargeted)
			target.draw(x+target.getDis(35), y+target.getDis(100), false);
		
		torso.draw(x, y, 1.5f);
		head.draw(x, y, 1.5f);
		ttf.drawString(x, (y-head.getDis(25)), character.getName());
		if(this.weapon != null)
			this.weapon.draw(x, y, 1.5f);
		
		/*
		* After death some characters(e.g. NPCs) are not updated anymore, but still player can loot them,
		* so appearance changes must be still handled
		*/
		if(!character.isLive())
			updateAppearance();
	}
	/**
	 * Updates avatar animations
	 * @param delta
	 */
	@Override
	public void update(int delta)
	{
		super.update(delta);
		
		updateAppearance();
		
		torso.update(delta);
		head.update(delta);
		if(weapon != null)
			weapon.update(delta);	
		
	}
	/**
	 * Toggles kneel animation
	 */
	public void kneel()
	{
	    torso.kneel(true);
	    head.kneel(true);
	    if(weapon != null)
	        weapon.kneel(true);
		defTorso.kneel(true);
		defHead.kneel(true);
	}
	/**
	 * Toggles lie animation
	 */
	public void lie()
	{
		torso.lie(true);
		head.lie(true);
		if(weapon != null)
			weapon.lie(true);
		defTorso.lie(true);
		defHead.lie(true);
	}
	
	public void goUp()
	{
		torso.goUp();
		head.goUp();
		if(weapon != null)
			weapon.goUp();
		defTorso.goUp();
		defHead.goUp();
	}
	public void goRight()
	{
		torso.goRight();
		head.goRight();
		if(weapon != null)
			weapon.goRight();
	}
	public void goDown()
	{
		torso.goDown();
		head.goDown();
		if(weapon != null)
			weapon.goDown();
		defTorso.goDown();
		defHead.goDown();
	}
	public void goLeft()
	{
		torso.goLeft();
		head.goLeft();
		if(weapon != null)
			weapon.goLeft();
		defTorso.goLeft();
		defHead.goLeft();
	}
	
	public void move(boolean move)
	{
		isMove = move;
		head.move(move);
		torso.move(move);
		if(weapon != null)
			weapon.move(move);
		defTorso.move(move);
		defHead.move(move);
	}
	
	public void meleeAnim(boolean loop)
	{
		torso.meleeAnim(loop);
		head.meleeAnim(loop);
		if(weapon != null)
			weapon.meleeAnim(loop);
		defTorso.meleeAnim(loop);
		defHead.meleeAnim(loop);
	}
	
	public void rangeAnim(boolean loop)
	{
		torso.rangeAnim(loop);
		head.rangeAnim(loop);
		if(weapon != null)
		{
			weapon.rangeAnim(loop);
		}
		defTorso.rangeAnim(loop);
		defHead.rangeAnim(loop);
	}
	/**
	 * Toggles spell casting animation
	 * @param loop True if animation should be looped, false otherwise 
	 */
	public void castAnim(boolean loop)
	{
		torso.castAnim(loop);
		head.castAnim(loop);
		if(weapon != null)
			weapon.castAnim(loop);
		defTorso.castAnim(loop);
		defHead.castAnim(loop);
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.graphic.CharacterAvatar#stopAnim()
	 */
	@Override
	public void stopAnim() 
	{
		torso.stopAnim();
		head.stopAnim();
		if(weapon != null)
			weapon.stopAnim();
	}
	/**
	 * Resets avatar
	 */
	public void reset()
	{
		stopAnim();
		torso = defTorso;
		head = defHead;
		weapon = null;
	}
	/**
	 * Returns object direction
	 * @return Direction id (0 - up, 1 - right, 2 - down, 3 - left)
	 */
	public int getDirection()
	{
		return torso.getDirection();
	}
	
	public float getWidth()
	{
		return torso.getWidth();
	}
	
	public AnimObject getDefTorso()
	{
		return defTorso;
	}
	
	public float getHeight()
	{
		return torso.getHeight();
	}
	/**
	 * Checks if avatar is static
	 * @return True if avatar is static, false otherwise
	 */
	public boolean isStatic()
	{
		return false;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.graphic.CharacterAvatar#isMouseOver()
	 */
	@Override
	public boolean isMouseOver() 
	{
		return avMOA.isMouseOver();
	}
	/**
	 * Updates character appearance
	 */
	private void updateAppearance()
	{
		if(character.getInventory().getChest() != null)
			torso = character.getInventory().getChest().getSpriteFor(character.getGender());
		else
			torso = defTorso;
		
		if(character.getInventory().getHelmet() != null)
			head = character.getInventory().getHelmet().getSpriteFor(character.getGender());
		else
			head = defHead;
		
		if(character.getInventory().getMainWeapon() != null)
			this.weapon = character.getInventory().getMainWeapon().getSpriteFor(character.getGender());
		else
			this.weapon = null;
	}
}
