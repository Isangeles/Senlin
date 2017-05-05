package pl.isangeles.senlin.graphic;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.MouseOverArea;

import pl.isangeles.senlin.inter.GameCursor;
import pl.isangeles.senlin.inter.InfoWindow;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.core.skill.Skill;
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
	
	private Sprite hostileT;
	private Sprite neutralT;
	private Sprite friendlyT;
	private Sprite target;
	
	private TrueTypeFont ttf;
	
	private boolean isMove;
	private boolean isTargeted;
	
	private Skill usedSkill;
	private boolean useSkill;
	/**
	 * Character avatar constructor
	 * @param character Character to represent by avatar
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Avatar(Character character, GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		gc.getInput().addMouseListener(this);
		this.character = character;
		
		hostileT = new Sprite(GConnector.getInput("sprite/hTarget.png"), "hTarget", false);
		neutralT = new Sprite(GConnector.getInput("sprite/nTarget.png"), "nTarget", false);
		friendlyT = new Sprite(GConnector.getInput("sprite/fTarget.png"), "fTarget", false);
		defTorso = new AnimObject(GConnector.getInput("sprite/avatar/cloth12221-45x55-2.png"), "clothSS", false);
		defHead = new AnimObject(GConnector.getInput("sprite/avatar/headBlack12221-45x55.png"), "headBlackSS", false);
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		ttf = new TrueTypeFont(font.deriveFont(11f), true);
		
		torso = defTorso;
		head = defHead;
		setTargetSprite();
		
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
		if(isTargeted)
			target.draw(x+target.getDis(10), y+target.getDis(45), false);
		
		torso.draw(x, y, 1.5f);
		head.draw(x, y, 1.5f);
		ttf.drawString(x, (y-head.getDis(25)), character.getName());
		if(this.weapon != null)
			this.weapon.draw(x, y, 1.5f);
		
		avMOA.setLocation(Global.uiX(x), Global.uiY(y));
		
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
		setTargetSprite();
		
		torso.update(delta);
		head.update(delta);
		if(weapon != null)
			weapon.update(delta);
		
		if(useSkill && torso.isAttackAnimStopped())
		{
		    usedSkill.activate();
		    useSkill = false;
		}
	}
	
	public void kill()
	{
		torso.lie(true);
		head.lie(true);
		if(weapon != null)
			weapon.lie(true);
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
	
	public void meleeAttack(Attack attackSkill)
	{
	    usedSkill = attackSkill;
	    useSkill = true;
		torso.meleeAttack(attackSkill.getCastSpeed());
		head.meleeAttack(attackSkill.getCastSpeed());
		if(weapon != null)
			weapon.meleeAttack(attackSkill.getCastSpeed());
	}
	/**
	 * Checks if character is in move
	 * @return
	 */
	public boolean isMove()
	{
		return isMove;
	}
	/**
	 * Checks if mouse is over avatar
	 * @return
	 */
	public boolean isMouseOver()
	{
		return avMOA.isMouseOver();
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
	public void mousePressed(int button, int x, int y) 
	{
		if(button == Input.MOUSE_RIGHT_BUTTON)
		{	
			if(avMOA.isMouseOver())
			{
				Global.setTarget(character);
				isTargeted = true;
			}
			else if(!avMOA.isMouseOver() && Global.getTarChar() != character)
			{
				isTargeted = false;
			}
			else if(!avMOA.isMouseOver() && Global.getTarChar() == character)
			{
				Global.setTarget(null);
				isTargeted = false;
			}
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
	/**
	 * Sets color of target circle based on character attitude
	 */
	private void setTargetSprite()
	{
		switch(character.getAttitude())
		{
		case HOSTILE:
			target = hostileT;
			break;
		case NEUTRAL:
			target = neutralT;
			break;
		case FRIENDLY:
			target = friendlyT;
			break;
		}
	}
}
