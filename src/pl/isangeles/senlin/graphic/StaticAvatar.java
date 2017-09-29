/*
 * StaticAvatar.java
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
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.MouseOverArea;

import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.gui.InfoWindow;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.GConnector;

/**
 * Class for 'static' avatar (equipment changes do not affect avatar)
 * @author Isangeles
 *
 */
public class StaticAvatar implements MouseListener, CharacterAvatar
{
    protected AnimObject torso;
    private AnimObject defTorso;
    
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
    
    private List<SimpleAnimObject> effects = new ArrayList<>();
    private List<SimpleAnimObject> loopEffects = new ArrayList<>();
    private List<SimpleAnimObject> effectsToRemove = new ArrayList<>();
	/**
	 * @param character
	 * @param gc
	 * @param spritesheet
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public StaticAvatar(Character character, GameContainer gc, String spritesheet) throws SlickException, IOException, FontFormatException 
	{
	    gc.getInput().addMouseListener(this);
        this.character = character;
        
        hostileT = new Sprite(GConnector.getInput("sprite/hTarget.png"), "hTarget", false);
        neutralT = new Sprite(GConnector.getInput("sprite/nTarget.png"), "nTarget", false);
        friendlyT = new Sprite(GConnector.getInput("sprite/fTarget.png"), "fTarget", false);
        deadT = new Sprite(GConnector.getInput("sprite/fTarget.png"), "dTarget", false);
        
        defTorso = new AnimObject(GConnector.getInput("sprite/mob/"+spritesheet), spritesheet, false, 80, 90);
        defTorso.setName(spritesheet);
        
        File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        ttf = new TrueTypeFont(font.deriveFont(11f), true);
        
        torso = defTorso;
        
        avMOA = new MouseOverArea(gc, torso.getCurrentSprite(), 0, 0);
        avName = new InfoWindow(gc, character.getName());
        speakWindow = new InfoWindow(gc, "");
	}
	
	@Override
	public void draw(float x, float y)
	{
		for(SimpleAnimObject effect : loopEffects)
		{
			effect.draw(x+target.getDis(15), y+target.getDis(50), false);
		}
		for(SimpleAnimObject effect : effects)
		{
			effect.draw(x+target.getDis(15), y+target.getDis(50), false);
		}
		
		if(isTargeted)
			target.draw(x+target.getDis(15), y+target.getDis(50), false);
		
		torso.draw(x, y, 1.5f);
		ttf.drawString(x, (y-torso.getDis(25)), character.getName());
		
		avMOA.setLocation(Global.uiX(x), Global.uiY(y));
		
		if(avMOA.isMouseOver())
			avName.draw(x, y);
		
		if(isSpeaking && speechTime < 1500)
			speakWindow.draw(x, y);
		else if(speechTime > 1500)
		{
			isSpeaking = false;
			speechTime = 0;
		}
	}
	
	@Override
	public void update(int delta)
	{	
		avName.setText(character.getName() + System.lineSeparator() + character.getGuild().getName());
		setTargetSprite();
		
		torso.update(delta);
		
		if(isSpeaking)
		{
			speechTime += delta;
		}
		
		for(SimpleAnimObject effect : effects)
		{
			if(effect.isLastFrame())
				effectsToRemove.add(effect);
		}
		effects.removeAll(effectsToRemove);
		effectsToRemove.clear();
	}

	@Override
	public void lie()
	{
		torso.lie(true);
	}
	@Override
	public void goUp()
	{
		torso.goUp();
	}
	@Override
	public void goRight()
	{
		torso.goRight();
	}
	@Override
	public void goDown()
	{
		torso.goDown();
	}
	@Override
	public void goLeft()
	{
		torso.goLeft();
	}
	@Override
	public void move(boolean trueFalse)
	{
		isMove = trueFalse;
		torso.move(trueFalse);
	}
	
	@Override
	public void meleeAnim()
	{
		torso.meleeAnim();
	}
	
	@Override
	public void rangeAnim()
	{
		torso.rangeAinm();
	}
	
	@Override
	public boolean isStatic()
	{
		return true;
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

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.graphic.CharacterAvatar#kneel()
     */
    @Override
    public void kneel()
    {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.graphic.CharacterAvatar#castAnim()
     */
    @Override
    public void castAnim()
    {
        // TODO Auto-generated method stub
        
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.graphic.CharacterAvatar#speak(java.lang.String)
     */
    @Override
    public void speak(String text)
    {
        speakWindow.setText(text);
        isSpeaking = true;
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.graphic.CharacterAvatar#targeted(boolean)
     */
    @Override
    public void targeted(boolean targeted)
    {
        this.isTargeted = targeted;
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.graphic.CharacterAvatar#getDirection()
     */
    @Override
    public int getDirection()
    {
        return torso.getDirection();
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.graphic.CharacterAvatar#isMouseOver()
     */
    @Override
    public boolean isMouseOver()
    {
        return avMOA.isMouseOver();
    }
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.graphic.CharacterAvatar#addEffect(pl.isangeles.senlin.graphic.SimpleAnimObject, boolean)
	 */
	@Override
	public boolean addEffect(SimpleAnimObject effect, boolean loop) 
	{
		if(effect != null)
		{
			if(loop)
				return loopEffects.add(effect);
			else
				return effects.add(effect);
		}
		else
			return false;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.graphic.CharacterAvatar#removeEffect(pl.isangeles.senlin.graphic.SimpleAnimObject)
	 */
	@Override
	public boolean removeEffect(SimpleAnimObject effect) 
	{
		return loopEffects.remove(effect);
	}
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.graphic.CharacterAvatar#getDefTorso()
     */
    @Override
    public AnimObject getDefTorso()
    {
        return defTorso;
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
