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
	private Animation melee, meleeUp, meleeRight, meleeDown, meleeLeft; 
	private boolean isMove;
	private boolean meleeReq;

	public AnimObject(InputStream is, String ref, boolean flipped) throws SlickException
	{
		super(is, ref, flipped);
		SpriteSheet ss = new SpriteSheet(this, 45, 55);

		Sprite[] moveUpList = {new Sprite(ss.getSprite(1, 0)), new Sprite(ss.getSprite(2, 0))};
		Sprite[] moveRightList = {new Sprite(ss.getSprite(1, 1)), new Sprite(ss.getSprite(2, 1))};
		Sprite[] moveDownList = {new Sprite(ss.getSprite(1, 2)), new Sprite(ss.getSprite(2, 2))};
		Sprite[] moveLeftList = {new Sprite(ss.getSprite(1, 3)), new Sprite(ss.getSprite(2, 3))};
		
		Sprite[] meleeUpList = {new Sprite(ss.getSprite(3, 0)), new Sprite(ss.getSprite(4, 0))};
		Sprite[] meleeRightList = {new Sprite(ss.getSprite(3, 1)), new Sprite(ss.getSprite(4, 1))};
		Sprite[] meleeDownList = {new Sprite(ss.getSprite(3, 2)), new Sprite(ss.getSprite(4, 2))};
		Sprite[] meleeLeftList = {new Sprite(ss.getSprite(3, 3)), new Sprite(ss.getSprite(4, 3))};
		
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
		
		meleeUp = new Animation(meleeUpList, duration, false);
		meleeRight = new Animation(meleeRightList, duration, false);
		meleeDown = new Animation(meleeDownList, duration, false);
		meleeLeft = new Animation(meleeLeftList, duration, false);
		melee = meleeUp;
		
	}
	/**
	 * Draws object
	 * @param scale Defines size of object(remember that object is always implicitly scaled to current resolution)
	 */
	@Override
	public void draw(float x, float y, float scale) 
	{
		if(isMove && !meleeReq) 
		{
			move.draw(x, y, (move.getCurrentFrame().getWidth() * getScale())*scale, (move.getCurrentFrame().getHeight() * getScale())*scale);
		} 
		else if(!meleeReq)
		{
			idle.draw(x, y, scale, false);
		}
		if(meleeReq)
		{
			melee.setSpeed(0.5f);
			melee.draw(x, y, (melee.getCurrentFrame().getWidth() * getScale())*scale, (melee.getCurrentFrame().getHeight() * getScale())*scale);
			if(melee.getCurrentFrame() == melee.getImage(1))
				meleeReq = false;
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
	
	public void meleeAttack()
	{
		meleeReq = true;
	}

	public void update(int delta) 
	{
		move.update(delta);
		melee.update(delta);
	}

	public void goUp() 
	{
		move = moveUp;
		idle = idleUp;
		melee = meleeUp;
	}

	public void goRight() 
	{
		move = moveRight;
		idle = idleRight;
		melee = meleeRight;
	}

	public void goDown() 
	{
		move = moveDown;
		idle = idleDown;
		melee = meleeDown;
	}

	public void goLeft() 
	{
		move = moveLeft;
		idle = idleLeft;
		melee = meleeLeft;
	}
	
	public Sprite getCurrentSprite()
	{
		if(isMove)
			return (Sprite)move.getCurrentFrame();
		else
			return idle;
	}
}
