package pl.isangeles.senlin.graphic;

import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import pl.isangeles.senlin.core.item.Armor;
import pl.isangeles.senlin.core.item.Weapon;
import pl.isangeles.senlin.util.*;
/**
 * Graphical representation of character
 * @author Isangeles
 *
 */
public class Avatar
{
	private AnimObject torso;
	private AnimObject head;
	private AnimObject weapon;
	
	private AnimObject defTorso;
	private AnimObject defHead;
	
	private boolean isMove;
	
	public Avatar() throws SlickException, IOException
	{
		defTorso = new AnimObject(GConnector.getInput("sprite/avatar/cloth12221-45x55-2.png"), "clothSS", false);
		defHead = new AnimObject(GConnector.getInput("sprite/avatar/headBlack12221-45x55.png"), "headBlackSS", false);
		torso = defTorso;
		head = defHead;
	}
	/**
	 * Draws avatar
	 * @param x Position on x-axis
	 * @param y Position on y-axis
	 * @param chest Chest equipped by character
	 * @param helmet Helmet equipped by character
	 * @param weaponType Weapon equipped by character
	 */
	public void draw(float x, float y, Armor chest, Armor helmet, Weapon weapon)
	{
		if(chest != null)
			torso = chest.getSprite();
		else
			torso = defTorso;
		
		if(helmet != null)
			head = helmet.getSprite();
		else
			head = defHead;
		
		if(weapon != null)
			this.weapon = weapon.getSprite();
		else
			this.weapon = null;

		torso.draw(x, y, 1.5f);
		head.draw(x, y, 1.5f);
		if(this.weapon != null)
			this.weapon.draw(x, y, 1.5f);
	}
	
	public void update(int delta)
	{
		torso.update(delta);
		head.update(delta);
		if(weapon != null)
			weapon.update(delta);
	}
	
	public void goUp()
	{
		torso.goUp();
		head.goUp();
		if(weapon != null)
			weapon.goUp();
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
	}
	public void goLeft()
	{
		torso.goLeft();
		head.goLeft();
		if(weapon != null)
			weapon.goLeft();
	}
	
	public void move(boolean trueFalse)
	{
		isMove = trueFalse;
		head.move(trueFalse);
		torso.move(trueFalse);
		if(weapon != null)
			weapon.move(trueFalse);
	}
	
	public boolean isMove()
	{
		return isMove;
	}
}
