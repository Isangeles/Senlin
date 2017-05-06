package pl.isangeles.senlin.graphic;

import java.io.InputStream;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
/**
 * Class for animated objects like avatar parts(torso, head, weapon), etc.
 * TODO make animProgress work
 * @author Isangeles
 *
 */
public class AnimObject extends GameObject 
{
	private Sprite idle, idleUp, idleRight, idleDown, idleLeft;
	private Sprite lie, lieUp, lieRight, lieDown, lieLeft;
	private Animation move, moveUp, moveRight, moveDown, moveLeft;
	private Animation melee, meleeUp, meleeRight, meleeDown, meleeLeft; 
	private boolean isMove;
	private boolean meleeReq;
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
		
		lieUp = new Sprite(ss.getSprite(7, 0));
		lieRight = new Sprite(ss.getSprite(7, 0));
		lieDown = new Sprite(ss.getSprite(7, 0));
		lieLeft = new Sprite(ss.getSprite(7, 0));
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
			melee.draw(x, y, (melee.getCurrentFrame().getWidth() * getScale())*scale, (melee.getCurrentFrame().getHeight() * getScale())*scale);
			if(melee.isStopped())
			{
				meleeReq = false;
				animTime = 0f;
			}
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
	 * Starts object's melee attack animation
	 */
	public void meleeAttack(float animSpeed)
	{
		meleeReq = true;
		melee.setSpeed(animSpeed);
		melee.setLooping(false);
		melee.restart();
		animDuration = melee.getFrameCount() * (0.25f/animSpeed);
		animProgress = 0f;
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
	    if(meleeReq)
	    	animTime += delta;
	    if(animTime > 250)
	    {
	    	animProgress += 0.25f;
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
		lie = lieLeft;
	}
	
	public Sprite getCurrentSprite()
	{
		if(isMove)
			return (Sprite)move.getCurrentFrame();
		else
			return idle;
	}
	
	public float getAnimProgress()
	{
	    return animProgress;
	}
	
	public float getAnimDuration()
	{
		return animDuration;
	}
	/**
	 * Checks if attack animation is stopped
	 * @return True if animation is stopped, false otherwise
	 */
	public boolean isAttackAnimStopped()
	{
	    if(melee.isStopped())
	        return true;
	    else
	        return false;
	}
	
}
