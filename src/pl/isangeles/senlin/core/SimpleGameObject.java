/*
 * SimpleGameObject.java
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.core.action.Action;
import pl.isangeles.senlin.core.action.ActionType;
import pl.isangeles.senlin.core.bonus.Bonus;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.effect.Effects;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.core.skill.Buff;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.graphic.GameObject;
import pl.isangeles.senlin.graphic.SimpleAnimObject;
import pl.isangeles.senlin.graphic.Sprite;
import pl.isangeles.senlin.gui.Portrait;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for simple game objects
 * @author Isangeles
 *
 */
public class SimpleGameObject implements Targetable, SaveElement
{
	private String id;
	private String name;
    private GameObject texture;
    private Portrait portrait;
    private Position pos;
    private Inventory inventory = new Inventory();
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
    public SimpleGameObject(String id, SimpleAnimObject texture, Portrait portrait, Action onClick, int gold, List<Item> items)
    {
        this.texture = texture;
        this.portrait = portrait;
        this.id = id;
        this.onClick = onClick;
        name = TConnector.getTextFromModule("objects", id);
        inventory.addAll(items);
        inventory.addGold(gold);
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
    public SimpleGameObject(String id, Sprite texture, Portrait portrait, Action onClick, int gold, List<Item> items)
    {
    	this.texture = texture;
    	this.portrait = portrait;
    	this.id = id;
        this.onClick = onClick;
        name = TConnector.getTextFromModule("objects", id);
        inventory.addAll(items);
        inventory.addGold(gold);
    }
    
    public void draw(float x, float y, boolean scaledPos)
    {
        texture.draw(x, y, scaledPos);
    }
    
    public void draw(float size)
    {
        texture.draw(size);
    }
    
    public void setPosition(Position pos)
    {
        this.pos = pos;
        texture.setPosition(pos);
    }
    
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
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#getTarget()
     */
    @Override
    public Targetable getTarget()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public String getId()
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
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#getMaxHealth()
     */
    @Override
    public int getMaxHealth()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#getMagicka()
     */
    @Override
    public int getMagicka()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#getMaxMagicka()
     */
    @Override
    public int getMaxMagicka()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#getExperience()
     */
    @Override
    public int getExperience()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#getMaxExperience()
     */
    @Override
    public int getMaxExperience()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#getLevel()
     */
    @Override
    public int getLevel()
    {
        // TODO Auto-generated method stub
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

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#takeHealth(int)
     */
    @Override
    public void takeHealth(int value)
    {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#takeMagicka(int)
     */
    @Override
    public void takeMagicka(int value)
    {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#takeAttack(pl.isangeles.senlin.core.Targetable, int, java.util.List)
     */
    @Override
    public void takeAttack(Targetable aggressor, Attack attack)
    {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#isLive()
     */
    @Override
    public boolean isLive()
    {
        // TODO Auto-generated method stub
        return false;
    }

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#addHealth(int)
	 */
	@Override
	public void addHealth(int value) 
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#addMagicka(int)
	 */
	@Override
	public void addMagicka(int value) 
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#getAttributes(pl.isangeles.senlin.core.Tragetable)
	 */
	@Override
	public Attributes getAttributes()
	{
		return null;
	}
	
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
		return texture.isMouseOver();
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
        // TODO Auto-generated method stub
        
    }

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#stopLooting()
	 */
	@Override
	public void stopLooting() 
	{
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#startReading(java.lang.String)
	 */
	@Override
	public void startReading(String textId) 
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#stopReading()
	 */
	@Override
	public void stopReading() 
	{
		// TODO Auto-generated method stub
		
	}
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#looting()
     */
    @Override
    public Targetable looting()
    {
        // TODO Auto-generated method stub
        return null;
    }
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#reading()
	 */
	@Override
	public String reading() 
	{
		// TODO Auto-generated method stub
		return null;
	}
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.data.SaveElement#getSave(org.w3c.dom.Document)
     */
    @Override
    public Element getSave(Document doc)
    {
        Element objectE = doc.createElement("object");
        objectE.setAttribute("id", id);
        objectE.setAttribute("position", pos.toString());
        objectE.appendChild(inventory.getSaveWithoutEq(doc));
        
        return objectE;
    }

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#addBonus(pl.isangeles.senlin.core.bonus.Bonus)
     */
    @Override
    public boolean addBonus(Bonus bonus)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#removeBonus(pl.isangeles.senlin.core.bonus.Bonus)
     */
    @Override
    public boolean removeBonus(Bonus bonus)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#hasBonus(pl.isangeles.senlin.core.bonus.Bonus)
     */
    @Override
    public boolean hasBonus(Bonus bonus)
    {
        // TODO Auto-generated method stub
        return false;
    }

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#takeBuff(pl.isangeles.senlin.core.Targetable, pl.isangeles.senlin.core.skill.Buff)
	 */
	@Override
	public void takeBuff(Targetable buffer, Buff buff) 
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#decMaxHealth(int)
	 */
	@Override
	public void decMaxHealth(int value) 
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#decMaxMagicka(int)
	 */
	@Override
	public void decMaxMagicka(int value) 
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#incMaxHealth(int)
	 */
	@Override
	public void incMaxHealth(int value) 
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#incMaxMagicka(int)
	 */
	@Override
	public void incMaxMagicka(int value) 
	{
		// TODO Auto-generated method stub
		
	}
    
}
