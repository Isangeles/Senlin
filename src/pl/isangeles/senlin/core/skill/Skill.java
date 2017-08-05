/*
 * Skill.java
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
package pl.isangeles.senlin.core.skill;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import pl.isangeles.senlin.util.AConnector;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.core.Attributes;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.effect.EffectType;
import pl.isangeles.senlin.core.exc.CharacterOut;
import pl.isangeles.senlin.gui.SlotContent;
import pl.isangeles.senlin.gui.elements.SkillTile;

/**
 * Class for character skills like attacks, spells, etc.
 * @author Isangeles
 *
 */
public abstract class Skill implements SlotContent
{
	private static int skillCounter = 0;
	private int serial = skillCounter ++;
	protected String id;
	protected String serialId;
	protected String name;
	protected String info;
	protected EffectType type;
	protected int magickaCost;
    protected Character owner;
	protected Targetable target;
    protected boolean ready;
    protected boolean active;
    protected boolean useWeapon;
    protected int cooldown;
    protected List<Effect> effects;
    private int castTime;
    private int timer;
 	private String imgName;
	private SkillTile tile;
	private Sound soundEffect;
	/**
	 * Skill constructor
	 * @param character Skill owner
	 * @param id Skill ID
	 * @param name Skill Name
	 * @param info Basic informations about this skill
	 * @param imgName Skill icon image
	 * @param type Skill effect type
	 * @param magickaCost Mana cost
	 * @param castTime Casting time
	 * @param cooldown Cooldown time
	 * @param useWeapon If weapon is needed to use this skill
	 * @param effects Skill effect list
	 */
	public Skill(Character character, String id, String name, String info, String imgName, EffectType type, int magickaCost, int castTime, int cooldown, boolean useWeapon, List<Effect> effects) 
	{
		this.type = type;
		this.id = id;
		this.name = name;
		this.info = info;
		this.imgName = imgName;
		this.magickaCost = magickaCost;
		this.castTime = castTime;
		this.cooldown = cooldown;
		this.useWeapon = useWeapon;
		this.effects = effects;
		owner = character;
		active = true;
		serialId = id + "_" + serial;
	}
	/**
	 * Draws skill UI icon
	 * @param x Position on x-axis 
	 * @param y Position on y-axis
	 * @param scaledPos If position should be scaled to resolution
	 */
	public void draw(float x, float y, boolean scaledPos)
	{
		tile.draw(x, y, scaledPos);
	}
	/**
	 * Updates skill
	 * @param delta Time between game updates
	 */
	public void update(int delta)
	{
		tile.setActive(active);
		if(!active)
			timer += delta;
		if(timer >= cooldown)
		{
			active = true;
			timer = 0;
		}
	}
	/**
	 * Sets skill active or inactive
	 * @param active True if skill should be active, false otherwise
	 */
	public void setActive(boolean active)
	{
		this.active = active;
		tile.setActive(active);
	}
	/**
	 * Returns skill casting speed
	 * @return Casting speed
	 */
    public float getCastSpeed()
    {
        return castTime;//(owner.getHaste()-castTime);
    }
    /**
     * Returns skill ID
     * @return String with ID
     */
	public String getId()
	{
		return id;
	}
	/**
	 * Returns serial ID
	 */
	public String getSerialId()
	{
		return serialId;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.SlotContent#getMaxStack()
	 */
	@Override
	public int getMaxStack() 
	{
		return 1;
	}
	/**
	 * Returns skill name 
	 * @return String with name
	 */
	public String getName()
	{
	    return name;
	}
	/**
	 * Returns skill UI icon
	 */
	public SkillTile getTile()
	{
		return tile;
	}
	/**
	 * Checks if skill is magical
	 * @return True if skill is magical, false otherwise
	 */
	public boolean isMagic()
	{
		if(magickaCost > 0)
			return true;
		else
			return false;
	}
	/**
	 * Checks if skill is active
	 * @return True if skill is active, false otherwise
	 */
	public boolean isActive()
	{
		return active;
	}
	/**
	 * Returns skill owner
	 * @return Character thats own this skill 
	 */
	public Character getOwner()
	{
		return owner;
	}
    /**
     * Activates skill prepared skill
     */
	public abstract void activate();
	/**
	 * Prepares skill
     * @param user Character thats use skill
     * @param target Character targeted by skill user
     * @return True if skill was successfully activate, false otherwise 
	 */
	public abstract CharacterOut prepare(Character user, Targetable target);
	
	protected abstract String getInfo();
	
	protected void setTile(GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		this.tile = new SkillTile(GConnector.getInput("icon/skill/"+imgName), "skillTile", false, gc, getInfo());
	}
	/**
	 * Sets sound effect dependent on skill type
	 * @throws SlickException
	 * @throws IOException
	 */
	protected void setSoundEffect() throws SlickException, IOException
	{
	    switch(type)
	    {
	    case NORMAL:
	    	this.soundEffect = new Sound(AConnector.getInput("effects/melee1.ogg"), "melee1.ogg");
	    	return;
	    case FIRE:
	    	
	    	return;
	    case ICE:
	    	
	    	return;
	    case NATURE:
	    	
	    	return;
	    case MAGIC:
	    	
	    	return;
	    }
	}
	/**
	 * Plays skill on-use sound effect 
	 */
	protected void playSoundEffect()
	{
	    this.soundEffect.play();
	}
	
	protected String getTypeString()
	{
		switch(type)
		{
		case FIRE:
			return TConnector.getText("ui", "eleTFire");
		case ICE:
			return TConnector.getText("ui", "eleTIce");
		case NATURE:
			return TConnector.getText("ui", "eleTNat");
		case MAGIC:
			return TConnector.getText("ui", "eleTMag");
		case NORMAL:
			return TConnector.getText("ui", "eleTNorm");
		default:
			return TConnector.getText("ui", "errorName");
		}
	}
}
