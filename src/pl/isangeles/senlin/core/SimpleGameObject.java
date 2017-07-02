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
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.data.SaveElement;
import pl.isangeles.senlin.graphic.GameObject;
import pl.isangeles.senlin.graphic.SimpleAnimObject;
import pl.isangeles.senlin.graphic.Sprite;
import pl.isangeles.senlin.inter.Portrait;
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
    private Effects effects = new Effects();
    private Action onClick;
    /**
     * Simple game object constructor 
     * @param texture Graphical representation of object
     */
    public SimpleGameObject(String id, SimpleAnimObject texture, Portrait portrait, Action onClick, int gold, List<Item> items)
    {
        this.texture = texture;
        this.portrait = portrait;
        this.id = id;
        this.onClick = onClick;
        name = TConnector.getText("objects", id);
        inventory.addAll(items);
        inventory.addGold(gold);
    }
    
    public SimpleGameObject(String id, Sprite texture, Portrait portrait, Action onClick, int gold, List<Item> items)
    {
    	this.texture = texture;
    	this.portrait = portrait;
    	this.id = id;
        this.onClick = onClick;
        name = TConnector.getText("objects", id);
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
        if(onClick.getType() == ActionType.LOOT)
        {
            user.setTarget(this);
            user.looting(true);
        }
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
    public void takeAttack(Targetable aggressor, int attackDamage,
            List<Effect> effects)
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
	 * @see pl.isangeles.senlin.core.Targetable#modHealth(int)
	 */
	@Override
	public void modHealth(int value) 
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#modMagicka(int)
	 */
	@Override
	public void modMagicka(int value) 
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Targetable#modAttributes(pl.isangeles.senlin.core.Attributes)
	 */
	@Override
	public void modAttributes(Attributes attributes)
	{
		// TODO Auto-generated method stub
		
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
    public void looting(boolean looting)
    {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.Targetable#isLooting()
     */
    @Override
    public boolean isLooting()
    {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.data.SaveElement#getSave(org.w3c.dom.Document)
     */
    @Override
    public Element getSave(Document doc)
    {
        Element objectE = doc.createElement("object");
        objectE.setAttribute("id", id);
        objectE.appendChild(inventory.getSave(doc));
        
        return objectE;
    }

    
}
