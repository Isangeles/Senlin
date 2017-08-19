/*
 * AnimObject.java
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

import java.io.InputStream;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import pl.isangeles.senlin.util.Coords;
/**
 * Class for animated objects like avatar parts(torso, head, weapon), etc.
 * TODO make animProgress work
 * @author Isangeles
 *
 */
public class AnimObject extends GameObject 
{
	private Sprite idle, idleUp, idleRight, idleDown, idleLeft;
	private Sprite kneel, kneelUp, kneelRight, kneelDown, kneelLeft;
	private Sprite lie, lieUp, lieRight, lieDown, lieLeft;
	private Animation move, moveUp, moveRight, moveDown, moveLeft;
	private Animation melee, meleeUp, meleeRight, meleeDown, meleeLeft; 
	private Animation range, rangeUp, rangeRight, rangeDown, rangeLeft;
	private Animation cast, castUp, castRight, castDown, castLeft;
	
	private boolean isMove;
	
	private boolean meleeReq;
	private boolean rangeReq;
	private boolean castReq;
	
	private boolean kneelReq;
	private boolean lieReq;
	
	private float animProgress;
	private float animTime;
	private float animDuration;
	/**
	 * Animated object constructor
	 * @param is InputStrem to sprite sheet
	 * @param ref Name for image in system
	 * @param flipped True if image should be flipped
	 * @throws SlickException 
	 */
	public AnimObject(InputStream is, String ref, boolean flipped, int fWidth, int fHeight) throws SlickException
	{
		super(is, ref, flipped);
		SpriteSheet ss = new SpriteSheet(this, fWidth, fHeight);

		Sprite[] moveUpList = {new Sprite(ss.getSprite(1, 0)), new Sprite(ss.getSprite(2, 0))};
		Sprite[] moveRightList = {new Sprite(ss.getSprite(1, 1)), new Sprite(ss.getSprite(2, 1))};
		Sprite[] moveDownList = {new Sprite(ss.getSprite(1, 2)), new Sprite(ss.getSprite(2, 2))};
		Sprite[] moveLeftList = {new Sprite(ss.getSprite(1, 3)), new Sprite(ss.getSprite(2, 3))};
		
		Sprite[] meleeUpList = {new Sprite(ss.getSprite(3, 0)), new Sprite(ss.getSprite(4, 0))};
		Sprite[] meleeRightList = {new Sprite(ss.getSprite(3, 1)), new Sprite(ss.getSprite(4, 1))};
		Sprite[] meleeDownList = {new Sprite(ss.getSprite(3, 2)), new Sprite(ss.getSprite(4, 2))};
		Sprite[] meleeLeftList = {new Sprite(ss.getSprite(3, 3)), new Sprite(ss.getSprite(4, 3))};
		
		Sprite[] rangeUpList = {new Sprite(ss.getSprite(5, 0)), new Sprite(ss.getSprite(6, 0))};
		Sprite[] rangeRightList = {new Sprite(ss.getSprite(5, 1)), new Sprite(ss.getSprite(6, 1))};
		Sprite[] rangeDownList = {new Sprite(ss.getSprite(5, 2)), new Sprite(ss.getSprite(6, 2))};
		Sprite[] rangeLeftList = {new Sprite(ss.getSprite(5, 3)), new Sprite(ss.getSprite(6, 3))};
		
		Sprite[] castUpList = {new Sprite(ss.getSprite(7, 0)), new Sprite(ss.getSprite(8, 0))};
		Sprite[] castRightList = {new Sprite(ss.getSprite(7, 1)), new Sprite(ss.getSprite(8, 1))};
		Sprite[] castDownList = {new Sprite(ss.getSprite(7, 2)), new Sprite(ss.getSprite(8, 2))};
		Sprite[] castLeftList = {new Sprite(ss.getSprite(7, 3)), new Sprite(ss.getSprite(8, 3))};
 		
		idleUp = new Sprite(ss.getSprite(0, 0));
		idleRight = new Sprite(ss.getSprite(0, 1));
		idleDown = new Sprite(ss.getSprite(0, 2));
		idleLeft = new Sprite(ss.getSprite(0, 3));
		idle = idleDown;
		
		kneelUp = new Sprite(ss.getSprite(9, 0));
		kneelRight = new Sprite(ss.getSprite(9, 1));
		kneelDown = new Sprite(ss.getSprite(9, 2));
		kneelLeft = new Sprite(ss.getSprite(9, 3));
		kneel = kneelDown;
		
		lieUp = new Sprite(ss.getSprite(10, 0));
		lieRight = new Sprite(ss.getSprite(10, 1));
		lieDown = new Sprite(ss.getSprite(10, 2));
		lieLeft = new Sprite(ss.getSprite(10, 3));
		lie = lieDown;

		int[] duration = {300, 300};
		moveUp = new Animation(moveUpList, duration, false);
		moveRight = new Animation(moveRightList, duration, false);
		moveDown = new Animation(moveDownList, duration, false);
		moveLeft = new Animation(moveLeftList, duration, false);
		move = moveDown;
		
		meleeUp = new Animation(meleeUpList, duration, false);
		meleeRight = new Animation(meleeRightList, duration, false);
		meleeDown = new Animation(meleeDownList, duration, false);
		meleeLeft = new Animation(meleeLeftList, duration, false);
		melee = meleeDown;	
		
		rangeUp = new Animation(rangeUpList, duration, false);
		rangeRight = new Animation(rangeRightList, duration, false);
		rangeDown = new Animation(rangeDownList, duration, false);
		rangeLeft = new Animation(rangeLeftList, duration, false);
		range = rangeDown;
		
		castUp = new Animation(castUpList, duration, false);
		castRight = new Animation(castRightList, duration, false);
		castDown = new Animation(castDownList, duration, false);
		castLeft = new Animation(castLeftList, duration, false);
		cast = castDown;
		
		
	}
	/**
	 * Draws object
	 * @param scale Defines size of object(remember that object is always implicitly scaled to current resolution)
	 */
	@Override
	public void draw(float x, float y, float scale) 
	{
		if(lieReq)
		{
			lie.draw(x, y, scale, false);
			return;
		}
		if(isMove && !meleeReq && !rangeReq && !castReq) 
		{
			move.draw(x, y, (move.getCurrentFrame().getWidth() * getScale())*scale, (move.getCurrentFrame().getHeight() * getScale())*scale);
		} 
		else if(!meleeReq && !rangeReq && !castReq)
		{
			idle.draw(x, y, scale, false);
		}
		if(meleeReq)
		{
			melee.draw(x, y, (melee.getCurrentFrame().getWidth() * getScale())*scale, (melee.getCurrentFrame().getHeight() * getScale())*scale);
			if(melee.isStopped())
				meleeReq = false;
		}
		if(rangeReq)
		{
			range.draw(x, y, (range.getCurrentFrame().getWidth() * getScale())*scale, (range.getCurrentFrame().getHeight() * getScale())*scale);
			if(range.isStopped())
				rangeReq = false;
		}
		if(castReq)
		{
			cast.draw(x, y, (cast.getCurrentFrame().getWidth() * getScale())*scale, (cast.getCurrentFrame().getHeight() * getScale())*scale);
			if(cast.isStopped())
				castReq = false;
		}
	}
	/**
	 * Switches object move animation
	 * @param move True if object should animate move, false otherwise
	 */
	public void move(boolean move)
	{
		isMove = move;
	}
	/**
	 * Starts melee attack animation
	 */
	public void meleeAnim()
	{
		meleeReq = true;
		melee.setLooping(false);
		melee.restart();
	}
	/**
	 * Starts range attack animation
	 */
	public void rangeAinm()
	{
		rangeReq = true;
		range.setLooping(false);
		range.restart();
	}
	/**
	 * Start casting animation
	 */
	public void castAnim()
	{
		castReq = true;
		cast.setLooping(false);
		cast.restart();
	}
	
	public void kneel(boolean kneelReq)
	{
		this.kneelReq = kneelReq;
	}
	/**
	 * Sets object lie position on or off
	 * @param lieReq True to turn lie position, false otherwise
	 */
	public void lie(boolean lieReq)
	{
		this.lieReq = lieReq;
	}
	/**
	 * Updates object
	 * @param delta
	 */
	public void update(int delta) 
	{
		move.update(delta);
		melee.update(delta);
		range.update(delta);
		cast.update(delta);
	    if(meleeReq || rangeReq)
	    	animTime += delta;
	    if(animTime > 300)
	    {
	    	animProgress += 0.3f;
	    	animTime = 0f;
	    }
	}
	/**
	 * Turns object up
	 */
	public void goUp() 
	{
		move = moveUp;
		idle = idleUp;
		melee = meleeUp;
		range = rangeUp;
		cast = castUp;
		kneel = kneelUp;
		lie = lieUp;
	}
	/**
	 * Turns object right
	 */
	public void goRight() 
	{
		move = moveRight;
		idle = idleRight;
		melee = meleeRight;
		range = rangeRight;
		cast = castRight;
		kneel = kneelRight;
		lie = lieRight;
	}
	/**
	 * Turns object down
	 */
	public void goDown() 
	{
		move = moveDown;
		idle = idleDown;
		melee = meleeDown;
		range = rangeDown;
		cast = castDown;
		kneel = kneelDown;
		lie = lieDown;
	}
	/**
	 * Turns object left
	 */
	public void goLeft() 
	{
		move = moveLeft;
		idle = idleLeft;
		melee = meleeLeft;
		range = rangeLeft;
		cast = castLeft;
		kneel = kneelLeft;
		lie = lieLeft;
	}
	
	public Sprite getCurrentSprite()
	{
		if(isMove)
			return (Sprite)move.getCurrentFrame();
		else
			return idle;
	}
	/**
	 * Returns object direction
	 * @return Direction id (0 - up, 1 - right, 2 - down, 3 - left)
	 */
	public int getDirection()
	{
		if(move == moveUp || idle == idleUp)
			return Coords.UP;
		if(move == moveRight || idle == idleRight)
			return Coords.RIGHT;
		if(move == moveDown || idle == idleDown)
			return Coords.DOWN;
		if(move == moveLeft || idle == idleLeft)
			return Coords.LEFT;
		
		return 0;
	}
	/**
	 * Checks if animation is stopped
	 * @return True if animation is stopped, false otherwise
	 */
	public boolean isAnimStopped()
	{
	    return melee.isStopped() || range.isStopped() || cast.isStopped();
	}
}
