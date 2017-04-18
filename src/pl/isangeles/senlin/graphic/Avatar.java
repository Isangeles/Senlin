package pl.isangeles.senlin.graphic;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;

import pl.isangeles.senlin.inter.Cursor;
import pl.isangeles.senlin.inter.InfoWindow;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.data.CommBase;
import pl.isangeles.senlin.util.*;
/**
 * Graphical representation of character
 * @author Isangeles
 *
 */
public class Avatar implements MouseListener
{
	private AnimObject torso;
	private AnimObject head;
	private AnimObject weapon;
	
	private AnimObject defTorso;
	private AnimObject defHead;
	
	private MouseOverArea avMOA;
	private InfoWindow avName;
	
	private Character character;
	
	private boolean isMove;
	
	public Avatar(Character character, GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		gc.getInput().addMouseListener(this);
		defTorso = new AnimObject(GConnector.getInput("sprite/avatar/cloth12221-45x55-2.png"), "clothSS", false);
		defHead = new AnimObject(GConnector.getInput("sprite/avatar/headBlack12221-45x55.png"), "headBlackSS", false);
		torso = defTorso;
		head = defHead;
		
		this.character = character;
		avMOA = new MouseOverArea(gc, torso.getCurrentSprite(), 0, 0);
		avName = new InfoWindow(gc, character.getName());
	}
	/**
	 * Draws avatar
	 * @param x Position on x-axis
	 * @param y Position on y-axis
	 * @param chest Chest equipped by character
	 * @param helmet Helmet equipped by character
	 * @param weaponType Weapon equipped by character
	 */
	public void draw(float x, float y)
	{
		torso.draw(x, y, 1.5f);
		head.draw(x, y, 1.5f);
		if(this.weapon != null)
			this.weapon.draw(x, y, 1.5f);
		
		avMOA.setLocation(x, y);
		
		if(avMOA.isMouseOver())
			avName.draw(x, y);
		
	}
	/**
	 * Updates avatar animations
	 * @param delta
	 */
	public void update(int delta)
	{
		if(character.getInventory().getChest() != null)
			torso = character.getInventory().getChest().getSprite();
		else
			torso = defTorso;
		
		if(character.getInventory().getHelmet() != null)
			head = character.getInventory().getHelmet().getSprite();
		else
			head = defHead;
		
		if(character.getInventory().getMainWeapon() != null)
			this.weapon = character.getInventory().getMainWeapon().getSprite();
		else
			this.weapon = null;
		
		avName.setText(character.getName());
		
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
	@Override
	public void inputEnded() 
	{
	}
	@Override
	public void inputStarted() 
	{
	}
	@Override
	public boolean isAcceptingInput() 
	{
		return true;
	}
	@Override
	public void setInput(Input arg0) 
	{
	}
	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) 
	{
	}
	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) 
	{
	}
	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) 
	{
	}
	@Override
	public void mousePressed(int arg0, int arg1, int arg2) 
	{
		if(arg0 == Input.MOUSE_RIGHT_BUTTON)
		{	
			if(avMOA.isMouseOver())
				Cursor.setTarget(character);
			else
				Cursor.setTarget(null);
		}
	}
	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) 
	{
	}
	@Override
	public void mouseWheelMoved(int arg0) 
	{
	}
}
