/*
 * TargetableObject.java
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
package pl.isangeles.senlin.core;

import java.util.List;

import org.newdawn.slick.Sound;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.audio.Voicing;
import pl.isangeles.senlin.core.action.Action;
import pl.isangeles.senlin.core.action.ActionType;
import pl.isangeles.senlin.core.bonus.Modifier;
import pl.isangeles.senlin.core.bonus.Modifiers;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.effect.Effects;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.core.skill.Buff;
import pl.isangeles.senlin.core.skill.Passive;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.graphic.Effective;
import pl.isangeles.senlin.graphic.GameObject;
import pl.isangeles.senlin.graphic.ObjectAvatar;
import pl.isangeles.senlin.graphic.SimpleAnim;
import pl.isangeles.senlin.graphic.Sprite;
import pl.isangeles.senlin.gui.Portrait;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.util.Settings;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.util.TilePosition;

/**
 * Class for simple game objects
 * @author Isangeles
 *
 */
public class TargetableObject implements Targetable, SaveElement, Voicing
{
	private String id;
	private String name;
	private Health hp = new Health(1, 1);
    private ObjectAvatar avatar;
    private Sound voice;
    private Portrait portrait;
    private Position pos;
    private Inventory inventory;
    private Attributes attributes = new Attributes();
    private Defense defense = new Defense(this);
    private Modifiers bonuses = new Modifiers();
    private Effects effects = new Effects(this);
    private Action onClick;
    /**
     * Simple game object constructor(with animated texture)
     * @param id Object ID
     * @param texture Simple animated texture
     * @param portrait Object portrait
     * @param onClick Action on click
     * @param gold Amount of gold in object
     * @param items List of items in object
     */
    public TargetableObject(String id, SimpleAnim texture, Portrait portrait, Action onClick)
    {
        this.avatar = new ObjectAvatar(texture);
        this.portrait = portrait;
        this.id = id;
        this.onClick = onClick;
        name = TConnector.getTextFromModule("objects", id);
        inventory = new Inventory(this);
    }
    /**
     * Simple game object constructor(with static texture)
     * @param id Object ID
     * @param texture Static texture
     * @param portrait Object portrait
     * @param onClick Action on click
     * @param gold Amount of gold in object
     * @param items List of items in object
     */
    public TargetableObject(String id, Sprite texture, Portrait portrait, Action onClick)
    {
    	this.avatar = new ObjectAvatar(texture);
    	this.portrait = portrait;
    	this.id = id;
        this.onClick = onClick;
        name = TConnector.getTextFromModule("objects", id);
        inventory = new Inventory(this);
    }
    /**
     * Simple game object constructor (with sound and animated texture)
     * @param id Object ID
     * @param texture Simple animated texture
     * @param portrait Object portrait
     * @param sound Object Sound
     * @param onClick Action on click
     * @param gold Amount of gold in object
     * @param items List of items in object
     */
    public TargetableObject(String id, SimpleAnim texture, Portrait portrait, Sound sound, Action onClick)
    {
    	this(id, texture, portrait, onClick);
    	voice = sound;
    }
    /**
     * Simple game object constructor with sound(with static texture)
     * @param id Object ID
     * @param texture Static texture
     * @param portrait Object portrait
     * @param sound Object sound
     * @param onClick Action on click
     * @param gold Amount of gold in object
     * @param items List of items in object
     */
    public TargetableObject(String id, Sprite texture, Portrait portrait, Sound sound, Action onClick)
    {
    	this(id, texture, portrait, onClick);
    	voice = sound;
    }
    /**
     * Draws object avatar
     * @param x Position on x-axis
     * @param y Position on y-axis
     * @param scaledPos True if position should be scaled to current resolution, false otherwise
     */
    public void draw(float x, float y, boolean scaledPos)
    {
        avatar.draw(x, y, scaledPos);
    }
    /**
     * Draws object avatar in current avatar position
     * @param size Desired size for object to draw
     */
    public void draw(float size)
    {
        avatar.draw(size);
    }
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#update(int)
	 */
	public void update(int delta) 
	{
		avatar.update(delta);
		effects.update(delta);
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.audio.Voicing#playSound()
	 */
	@Override
	public void playSound() 
	{
		if(voice != null)
		{
			if(!voice.playing())
			{
				voice.play(1.0f, Settings.getEffectsVol());
			}
		}
	}
    /**
     * Sets specified position as object position
     * @param pos Position to set
     */
    public void setPosition(TilePosition tilePos)
    {
        this.pos = tilePos.asPosition();
        avatar.setPosition(pos);
    }
    /**
     * Starts object on-click action
     * @param user Request source
     */
    public void startAction(Targetable user)
    {
        onClick.start(user, this);
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#setTarget(pl.isangeles.senlin.core.Targetable)
     */
    @Override
    public void setTarget(Targetable target)
    {
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#getTarget()
     */
    @Override
    public Targetable getTarget()
    {
        return null;
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#getId()
     */
    @Override
    public String getId()
	{
		return id;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#getSerialId()
	 */
	@Override
	public String getSerialId() 
	{
		return id;
	}
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#getName()
     */
    @Override
    public String getName()
    {
        return name;
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#getPortrait()
     */
    @Override
    public Portrait getPortrait()
    {
        return portrait;
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#getEffects()
     */
    @Override
    public Effects getEffects()
    {
        return effects;
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#getHealth()
     */
    @Override
    public int getHealth()
    {
        return hp.getValue();
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#getMaxHealth()
     */
    @Override
    public int getMaxHealth()
    {
        return hp.getMax();
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#getMagicka()
     */
    @Override
    public int getMagicka()
    {
        return 0;
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#getMaxMagicka()
     */
    @Override
    public int getMaxMagicka()
    {
        return 0;
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#getExperience()
     */
    @Override
    public int getExperience()
    {
        return 0;
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#getMaxExperience()
     */
    @Override
    public int getMaxExperience()
    {
        return 0;
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#getLevel()
     */
    @Override
    public int getLevel()
    {
        return 0;
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#getPosition()
     */
    @Override
    public int[] getPosition()
    {
        return pos.asTable();
    }
    /**
     * Returns object avatar
     * @return Object avatar
     */
    public ObjectAvatar getAvatar()
    {
    	return avatar;
    }
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#getGEffectsTarget()
	 */
	@Override
	public Effective getGEffectsTarget() 
	{
		return avatar;
	}
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#isLive()
     */
    @Override
    public boolean isLive()
    {
        return false;
    }
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#addHealth(int)
	 */
	@Override
	public void modHealth(int value) 
	{
		hp.modValue(value);
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#addMagicka(int)
	 */
	@Override
	public void modMagicka(int value) 
	{
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#getAttributes(pl.isangeles.senlin.core.Tragetable)
	 */
	@Override
	public Attributes getAttributes()
	{
		return attributes;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#getDefense()
	 */
	@Override
	public Defense getDefense() 
	{
		return defense;
	}
	/**
	 * Sets specified inventory as this object inventory 
	 * @param inventory Inventory to set
	 */
	public void setInventory(Inventory inventory)
	{
		this.inventory = inventory;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#isMouseOver()
	 */
	@Override
	public boolean isMouseOver() 
	{
		return avatar.isMouseOver();
	}
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#getInventory()
     */
    @Override
    public Inventory getInventory()
    {
        return inventory;
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#looting(boolean)
     */
    @Override
    public void startLooting(Targetable target)
    {
    }
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#stopLooting()
	 */
	@Override
	public void stopLooting() 
	{
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#startReading(java.lang.String)
	 */
	@Override
	public void startReading(String textId) 
	{
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#stopReading()
	 */
	@Override
	public void stopReading() 
	{
	}
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#looting()
     */
    @Override
    public Targetable looting()
    {
        return null;
    }
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#reading()
	 */
	@Override
	public String reading() 
	{
		return null;
	}
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#addModifier(pl.isangeles.senlin.core.bonus.Modifier)
     */
    @Override
    public boolean addModifier(Modifier bonus)
    {
    	if(bonuses.add(bonus))
    	{
    		bonus.applyOn(this);
    		return true;
    	}
    	else
    		return false;
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#removeModifier(pl.isangeles.senlin.core.bonus.Modifier)
     */
    @Override
    public boolean removeModifier(Modifier bonus)
    {
        if(bonuses.remove(bonus))
        {
        	bonus.removeFrom(this);
        	return true;
        }
        else
        	return false;
    }

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#hasBonus(pl.isangeles.senlin.core.bonus.Bonus)
     */
    @Override
    public boolean hasModifier(Modifier bonus)
    {
        return bonuses.contains(bonus);
    }
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#takeHealth(pl.isangeles.senlin.core.Targetable, int)
	 */
	@Override
	public void takeHealth(Targetable source, int value) 
	{
		hp.modValue(-value);
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#modMaxHealth(int)
	 */
	@Override
	public void modMaxHealth(int value) 
	{
		hp.modMax(value);
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#incMaxMagicka(int)
	 */
	@Override
	public void modMaxMagicka(int value) 
	{	
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#modExperience(int)
	 */
	@Override
	public void modExperience(int value) 
	{
	}
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.data.SaveElement#getSave(org.w3c.dom.Document)
     */
    @Override
    public Element getSave(Document doc)
    {
        Element objectE = doc.createElement("object");
        objectE.setAttribute("id", id);
        objectE.setAttribute("position", new TilePosition(pos.x/32, pos.y/32).toString());
        objectE.appendChild(inventory.getSaveWithoutEq(doc));
        
        return objectE;
    }
}
