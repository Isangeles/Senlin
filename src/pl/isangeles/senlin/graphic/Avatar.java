package pl.isangeles.senlin.graphic;

import java.io.IOException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import pl.isangeles.senlin.util.*;
/**
 * Graphical representation of character
 * @author Isangeles
 *
 */
public class Avatar extends GameObject
{
	private Image torsoIdle,
				  torsoIdleUp,
		  		  torsoIdleRight,
		  		  torsoIdleDown,
		  		  torsoIdleLeft;
	private Image headIdle,
				  headIdleUp,
				  headIdleRight,
				  headIdleDown,
				  headIdleLeft;
	private Animation torsoMove,
			  		  torsoMoveUp,
			  		  torsoMoveRight,
			  		  torsoMoveDown,
			  		  torsoMoveLeft;
	private Animation headMove,
					  headMoveUp,
					  headMoveRight,
					  headMoveDown,
					  headMoveLeft;
	private boolean isMove;
	
	public Avatar() throws SlickException, IOException
	{
		Image basicTorsoImg = new Image(GConnector.getInput("sprite/avatar/cloth12221-45x55.png"), "clothSS", false);
		SpriteSheet torso = new SpriteSheet(basicTorsoImg, 45, 55);
		
		Image[] moveUpList = {torso.getSprite(1, 0), torso.getSprite(2, 0)};
		Image[] moveRightList = {torso.getSprite(1, 1), torso.getSprite(2, 1)};
		Image[] moveDownList = {torso.getSprite(1, 2), torso.getSprite(2, 2)};
		Image[] moveLeftList = {torso.getSprite(1, 3), torso.getSprite(2, 3)};
		torsoIdleUp = torso.getSprite(0, 0);
		torsoIdleRight = torso.getSprite(0, 1);
		torsoIdleDown = torso.getSprite(0, 2);
		torsoIdleLeft = torso.getSprite(0, 3);
		torsoIdle = torsoIdleDown;
		
		int[] duration = {300, 300};
		torsoMoveUp = new Animation(moveUpList, duration, false);
		torsoMoveRight = new Animation(moveRightList, duration, false);
		torsoMoveDown = new Animation(moveDownList, duration, false);
		torsoMoveLeft = new Animation(moveLeftList, duration, false);
		torsoMove = torsoMoveDown;
		

		Image basicHeadImg = new Image(GConnector.getInput("sprite/avatar/headBlack12221-40x40.png"), "headBlackSS", false);
		SpriteSheet head = new SpriteSheet(basicHeadImg, 40, 40);
		
		Image[] headMoveUpList = {head.getSprite(1, 0), head.getSprite(2, 0)};
		Image[] headMoveRightList = {head.getSprite(1, 1), head.getSprite(2, 1)};
		Image[] headMoveDownList = {head.getSprite(1, 2), head.getSprite(2, 2)};
		Image[] headMoveLeftList = {head.getSprite(1, 3), head.getSprite(2, 3)};
		headIdleUp = head.getSprite(0, 0);
		headIdleRight = head.getSprite(0, 1);
		headIdleDown = head.getSprite(0, 2);
		headIdleLeft = head.getSprite(0, 3);
		headIdle = headIdleDown;
		
		headMoveUp = new Animation(headMoveUpList, duration, false);
		headMoveRight = new Animation(headMoveRightList, duration, false);
		headMoveDown = new Animation(headMoveDownList, duration, false);
		headMoveLeft = new Animation(headMoveLeftList, duration, false);
		headMove = headMoveDown;
	}
	
	public void draw(float x, float y)
	{
		if(isMove)
		{
			torsoMove.draw(x, y);
			headMove.draw(x, y - 5);
		}
		else
		{
			torsoIdle.draw(x, y);
			headIdle.draw(x, y - 5);
		}
		
	}
	
	public void update(int delta)
	{
		torsoMove.update(delta);
		headMove.update(delta);
	}
	
	public void goUp()
	{
		torsoMove = torsoMoveUp;
		torsoIdle = torsoIdleUp;
		
		headMove = headMoveUp;
		headIdle = headIdleUp;
	}
	public void goRight()
	{
		torsoMove =	torsoMoveRight;
		torsoIdle = torsoIdleRight;

		headMove = headMoveRight;
		headIdle = headIdleRight;
	}
	public void goDown()
	{
		torsoMove = torsoMoveDown;
		torsoIdle = torsoIdleDown;

		headMove = headMoveDown;
		headIdle = headIdleDown;
	}
	public void goLeft()
	{
		torsoMove = torsoMoveLeft;
		torsoIdle = torsoIdleLeft;

		headMove = headMoveLeft;
		headIdle = headIdleLeft;
	}
	
	public void move(boolean trueFalse)
	{
		isMove = trueFalse;
	}
	
	public boolean isMove()
	{
		return isMove;
	}
}
