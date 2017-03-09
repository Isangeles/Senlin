package pl.isangeles.senlin.graphic;

import java.io.IOException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import pl.isangeles.senlin.util.*;

public class Avatar 
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
		Image basicHeadImg = new Image(GConnector.getInput("sprite/avatar/headBlack12221-40x40.png"), "headBlackSS", false);
		SpriteSheet torso = new SpriteSheet(basicTorsoImg, 45, 55);
		SpriteSheet head = new SpriteSheet(basicHeadImg, 40, 40);
		
		Image[] moveUpList = {torso.getSprite(1, 0), torso.getSprite(2, 0)};
		Image[] moveRightList = {torso.getSprite(1, 1), torso.getSprite(2, 1)};
		Image[] moveDownList = {torso.getSprite(1, 2), torso.getSprite(2, 2)};
		Image[] moveLeftList = {torso.getSprite(1, 3), torso.getSprite(2, 3)};
		torsoIdleUp = torso.getSprite(0, 0);
		torsoIdleRight = torso.getSprite(0, 1);
		torsoIdleDown = torso.getSprite(0, 3);
		torsoIdleLeft = torso.getSprite(0, 4);
		torsoIdle = torsoIdleDown;
		
		int[] duration = {300, 300};
		torsoMoveUp = new Animation(moveUpList, duration, false);
		torsoMoveRight = new Animation(moveRightList, duration, false);
		torsoMoveDown = new Animation(moveDownList, duration, false);
		torsoMoveLeft = new Animation(moveLeftList, duration, false);
		torsoMove = torsoMoveDown;
	}
	
	public void draw(float x, float y)
	{
		if(isMove)
		{
			torsoMove.draw(x, y);
			headMove.draw(x, y);
		}
		else
		{
			torsoIdle.draw(x, y);
			headIdle.draw(x, y);
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
	
}
