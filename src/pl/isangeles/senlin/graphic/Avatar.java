/*
 * Avatar.java
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
import pl.isangeles.senlin.inter.ui.UserInterface;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.data.GuildsBase;
import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.util.*;
/**
 * Graphical representation of character
 * @author Isangeles
 *
 */
public class Avatar implements MouseListener
{
	protected AnimObject torso;
	private AnimObject head;
	private AnimObject weapon;
	
	private AnimObject defTorso;
	private AnimObject defHead;
	
	protected MouseOverArea avMOA;
	protected InfoWindow avName;
	protected InfoWindow speakWindow;
	
	protected Character character;
	
	protected Sprite hostileT;
	protected Sprite neutralT;
	protected Sprite friendlyT;
	protected Sprite deadT;
	protected Sprite target;
	
	protected TrueTypeFont ttf;
	
	protected boolean isMove;
	protected boolean isTargeted;
	protected boolean isSpeaking;
	protected int speechTime;
	
	protected Skill usedSkill;
	protected boolean useSkill;
	/**
	 * Character avatar constructor
	 * @param character Character to represent by avatar
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Avatar(Character character, GameContainer gc, String spritesheet) throws SlickException, IOException, FontFormatException
	{
		gc.getInput().addMouseListener(this);
		this.character = character;
		
		hostileT = new Sprite(GConnector.getInput("sprite/hTarget.png"), "hTarget", false);
		neutralT = new Sprite(GConnector.getInput("sprite/nTarget.png"), "nTarget", false);
		friendlyT = new Sprite(GConnector.getInput("sprite/fTarget.png"), "fTarget", false);
		deadT = new Sprite(GConnector.getInput("sprite/fTarget.png"), "dTarget", false);
		if(isStatic())
		{
			defTorso = new AnimObject(GConnector.getInput("sprite/mob/"+spritesheet), spritesheet, false, 60, 70);
			defTorso.setName(spritesheet);
		}
		else
		{
			defTorso = new AnimObject(GConnector.getInput("sprite/avatar/"+spritesheet), spritesheet, false, 60, 70);
			defTorso.setName(spritesheet);
			defHead = new AnimObject(GConnector.getInput("sprite/avatar/headBlack12221-60x70.png"), "headBlackSS", false, 60, 70);
		}
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		ttf = new TrueTypeFont(font.deriveFont(11f), true);
		
		torso = defTorso;
		head = defHead;
		setTargetSprite();
		
		avMOA = new MouseOverArea(gc, torso.getCurrentSprite(), 0, 0);
		avName = new InfoWindow(gc, character.getName());
		speakWindow = new InfoWindow(gc, "");
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
			target.draw(x+target.getDis(15), y+target.getDis(50), false);
		
		torso.draw(x, y, 1.5f);
		head.draw(x, y, 1.5f);
		ttf.drawString(x, (y-head.getDis(25)), character.getName());
		if(this.weapon != null)
			this.weapon.draw(x, y, 1.5f);
		
		avMOA.setLocation(Global.uiX(x), Global.uiY(y));
		
		if(avMOA.isMouseOver())
		{
			avName.draw(x, y);
		}
		if(isSpeaking && speechTime < 1500)
			speakWindow.draw(x, y);
		else if(speechTime > 1500)
		{
			isSpeaking = false;
			speechTime = 0;
		}
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
		
		avName.setText(character.getName() + System.lineSeparator() + character.getGuild().getName());
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
		
		if(isSpeaking)
		{
			speechTime += delta;
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
	
	public void rangeAttack(Attack attackSkill)
	{
		if(weapon != null)
		{
			usedSkill = attackSkill;
			useSkill = true;
			torso.rangeAttack(attackSkill.getCastSpeed());
			head.rangeAttack(attackSkill.getCastSpeed());
			weapon.rangeAttack(attackSkill.getCastSpeed());
		}
	}
	/**
	 * Draws specified string in speech window
	 * Text is drawn for 1.5 seconds
	 * @param text String with speech
	 */
	public void speak(String text)
	{
		speakWindow.setText(text);
		isSpeaking = true;
	}
	/**
	 * Informs avatar that his character is targeted or not
	 * @param isTargeted True if is targeted, false otherwise
	 */
	public void targeted(boolean isTargeted)
	{
	    this.isTargeted = isTargeted;
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
	
	public boolean isCasting()
	{
	    return useSkill;
	}
	
	public float getCastProgress()
	{
	    if(!torso.isAttackAnimStopped())
	        return torso.getAnimProgress();
	    else
	        return 100f;
	}
	
	public float getCastDuration()
	{
		if(!torso.isAttackAnimStopped())
			return torso.getAnimDuration();
		else
			return 100f;
	}
	/**
	 * Returns object direction
	 * @return Direction id (0 - up, 1 - right, 2 - down, 3 - left)
	 */
	public int getDirection()
	{
		return torso.getDirection();
	}
	
	public float getWidth()
	{
		return torso.getWidth();
	}
	
	public AnimObject getDefTorso()
	{
		return defTorso;
	}
	
	public float getHeight()
	{
		return torso.getHeight();
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
	 * Checks if avatar is static
	 * @return True if avatar is static, false otherwise
	 */
	public boolean isStatic()
	{
		return false;
	}
	/**
	 * Sets color of target circle based on character attitude
	 */
	protected void setTargetSprite()
	{
		switch(character.getAttitudeTo(Global.getPlayer()))
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
		case DEAD:
		    target = deadT;
		    break;
		}
	}
}
