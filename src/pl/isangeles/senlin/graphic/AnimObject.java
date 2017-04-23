package pl.isangeles.senlin.graphic;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
/**
 * Class for animated objects like avatar parts(torso, head, weapon), etc.
 * @author Isangeles
 *
 */
public class AnimObject extends GameObject 
{
	private Sprite idle, idleUp, idleRight, idleDown, idleLeft;
	private Animation move, moveUp, moveRight, moveDown, moveLeft;
	private boolean isMove;

	public AnimObject(InputStream is, String ref, boolean flipped) throws SlickException
	{
		super(is, ref, flipped);
		SpriteSheet ss = new SpriteSheet(this, 45, 55);

		Sprite[] moveUpList = {new Sprite(ss.getSprite(1, 0)), new Sprite(ss.getSprite(2, 0))};
		Image[] moveRightList = {new Sprite(ss.getSprite(1, 1)), new Sprite(ss.getSprite(2, 1))};
		Image[] moveDownList = {new Sprite(ss.getSprite(1, 2)), new Sprite(ss.getSprite(2, 2))};
		Image[] moveLeftList = {new Sprite(ss.getSprite(1, 3)), new Sprite(ss.getSprite(2, 3))};
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
	/**
	 * Draws object
	 * @param scale Defines size of object(remember that object is always implicitly scaled to current resolution)
	 */
	@Override
	public void draw(float x, float y, float scale) 
	{
		if(isMove) 
		{
			move.draw(x, y, (move.getCurrentFrame().getWidth() * getScale())*scale, (move.getCurrentFrame().getHeight() * getScale())*scale);
		} 
		else 
		{
			idle.draw(x, y, scale, false);
		}
	}
	
	public void move(boolean move)
	{
		isMove = move;
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
		move = moveRight;
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
	
	public Sprite getCurrentSprite()
	{
		if(isMove)
			return (Sprite)move.getCurrentFrame();
		else
			return idle;
	}
}
