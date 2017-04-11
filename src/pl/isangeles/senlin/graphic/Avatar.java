package pl.isangeles.senlin.graphic;

import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import pl.isangeles.senlin.core.item.Weapon;
import pl.isangeles.senlin.util.*;
/**
 * Graphical representation of character
 * @author Isangeles
 *
 */
public class Avatar
{
	private AvatarPart torso;
	private AvatarPart head;
	private AvatarPart sword;
	
	private boolean isMove;
	
	public Avatar() throws SlickException, IOException
	{
		torso = new AvatarPart(GConnector.getInput("sprite/avatar/cloth12221-45x55-2.png"), "clothSS", false);
		head = new AvatarPart(GConnector.getInput("sprite/avatar/headBlack12221-45x55.png"), "headBlackSS", false);
		sword = new AvatarPart(GConnector.getInput("sprite/avatar/sword122-45x55-2.png"), "swordSS", false);
	}
	/**
	 * Draws avatar
	 * @param x Position on x-axis
	 * @param y Position on y-axis
	 * @param weaponType Type of weapon equipped by character
	 */
	public void draw(float x, float y, int weaponType)
	{
		torso.draw(x, y);
		head.draw(x, y);
		if(weaponType == Weapon.SWORD)
			sword.draw(x, y);
	}
	
	public void update(int delta)
	{
		torso.update(delta);
		head.update(delta);
		sword.update(delta);
	}
	
	public void goUp()
	{
		torso.goUp();
		head.goUp();
		sword.goUp();
	}
	public void goRight()
	{
		torso.goRight();
		head.goRight();
		sword.goRight();
	}
	public void goDown()
	{
		torso.goDown();
		head.goDown();
		sword.goDown();
	}
	public void goLeft()
	{
		torso.goLeft();
		head.goLeft();
		sword.goLeft();
	}
	
	public void move(boolean trueFalse)
	{
		isMove = trueFalse;
	}
	
	public boolean isMove()
	{
		return isMove;
	}

	/**
	 * Inner class for avatar parts like torso, head, etc.
	 * @author Isangeles
	 *
	 */
	private class AvatarPart extends GameObject
	{
		private Sprite idle,
		  			  idleUp,
		  			  idleRight,
		  			  idleDown,
		  			  idleLeft;
		private Animation move,
		  				  moveUp,
		  				  moveRight,
		  				  moveDown,
		  				  moveLeft;
		
		public AvatarPart(InputStream is, String ref, boolean flipped) throws SlickException
		{
			super(is, ref, flipped);
			SpriteSheet ss = new SpriteSheet(this, 45, 55);
			
			Image[] moveUpList = {ss.getSprite(1, 0), ss.getSprite(2, 0)};
			Image[] moveRightList = {ss.getSprite(1, 1), ss.getSprite(2, 1)};
			Image[] moveDownList = {ss.getSprite(1, 2), ss.getSprite(2, 2)};
			Image[] moveLeftList = {ss.getSprite(1, 3), ss.getSprite(2, 3)};
			idleUp = new Sprite(ss.getSprite(0, 0));
			idleRight = new Sprite(ss.getSprite(0, 1));
			idleDown = new Sprite(ss.getSprite(0, 2));
			idleLeft = new Sprite(ss.getSprite(0, 3));
			idle = idleDown;
			
			int[] duration = {300, 300};
			moveUp = new Animation(moveUpList, duration, false);
			moveRight = new Animation(moveRightList, duration, false);
			moveDown = new Animation(moveDownList, duration, false);
			moveLeft = new Animation(moveLeftList, duration, false);
			move = moveDown;
		}
		@Override
		public void draw(float x, float y)
		{
			if(isMove)
			{
				move.draw(x, y, move.getCurrentFrame().getWidth() * getScale(), move.getCurrentFrame().getHeight() * getScale());
			}
			else
			{
				idle.draw(x, y, false);
			}
		}
		
		public void update(int delta)
		{
			move.update(delta);
		}
		
		public void goUp()
		{
			move = moveUp;
			idle = idleUp;
		}
		public void goRight()
		{
			move =	moveRight;
			idle = idleRight;
		}
		public void goDown()
		{
			move = moveDown;
			idle = idleDown;
		}
		public void goLeft()
		{
			move = moveLeft;
			idle = idleLeft;
		}
	}
}
