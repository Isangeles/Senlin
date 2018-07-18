/*
 * Skill.java
 * 
 * Copyright 2017 Dariusz Sikora <dev@isangeles.pl>
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.util.AConnector;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.Settings;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Attributes;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.effect.EffectSource;
import pl.isangeles.senlin.core.effect.EffectType;
import pl.isangeles.senlin.core.out.CharacterOut;
import pl.isangeles.senlin.core.req.ManaRequirement;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.core.req.Requirements;
import pl.isangeles.senlin.data.EffectsBase;
import pl.isangeles.senlin.data.pattern.EffectPattern;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.graphic.AvatarAnimType;
import pl.isangeles.senlin.graphic.SimpleAnim;
import pl.isangeles.senlin.gui.SlotContent;
import pl.isangeles.senlin.gui.tools.SkillTile;

/**
 * Class for character skills like attacks, spells, etc.
 * @author Isangeles
 *
 */
public abstract class Skill implements SlotContent, SaveElement, EffectSource
{
	private static int skillCounter = 0;
	private int serial = skillCounter ++;
	protected String id;
	protected String serialId;
	protected String name;
	protected String info;
	protected EffectType type;
	protected Requirements useReqs = new Requirements();
    protected Character owner;
	protected Targetable target;
    protected boolean active;
    protected boolean ready;
    protected int cooldown;
    protected final List<String> effects;
    protected AvatarAnimType avatarAnim = AvatarAnimType.MELEE;
	protected Sound castSound;
	protected Sound activateSound;
	protected SimpleAnim castAnim;
	protected SimpleAnim activeAnim;
    private int castTime;
    private int timer;
 	private String imgName;
	protected SkillTile tile;
	protected Random rng = new Random();
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
	public Skill(Character character, String id, String imgName, EffectType type, List<Requirement> reqs, int castTime, int cooldown, List<String> effects) 
	{
		this.type = type;
		this.id = id;
		String[] nameInfo = TConnector.getInfoFromModule("skills", id);
		this.name = nameInfo[0];
		this.info = nameInfo[1];
		this.imgName = imgName;
		this.useReqs.addAll(reqs);
		this.castTime = castTime;
		this.cooldown = cooldown;
		this.effects = effects;
		owner = character;
		ready = true;
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
		int fullCooldown = getCooldown();
		tile.setActive(ready);
		if(!ready)
		{
			timer += delta;
		}
		if(timer >= fullCooldown)
		{
			ready = true;
			timer = 0;
		}
	}
	/**
	 * Sets skill ready or unready
	 * @param ready True if skill should be ready, false otherwise
	 */
	public void setReady(boolean ready)
	{
		this.ready = ready;
		tile.setActive(ready);
	}
	/**
	 * Sets specified time as current cooldown time
	 * @param time Time in milliseconds
	 */
	public void setCooldownTime(int time)
	{
		timer = time;
	}
	/**
	 * Activates or deactivates this skill
	 * @param active True to activate, false to deactivate
	 */
	private void setActive(boolean active)
	{
		this.active = active;
	}
	/**
	 * Returns skill casting speed
	 * @return Casting speed
	 */
    public float getCastTime()
    {
        return castTime / owner.getAttributes().getConcentration(); 
    }
    /**
     * Returns skill cooldown
     * @return Skill cooldown
     */
    public int getCooldown()
    {
    	if(isMagic())
    		return (int)(cooldown / owner.getAttributes().getConcentration());
    	else
    		return (int)(cooldown / owner.getAttributes().getHaste());
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
	 * Returns casting audio effect
	 * @return Sound to play
	 */
	public Sound getCastSound()
	{
		return castSound;
	}
	/**
	 * Returns skill activation audio effect
	 * @return Sound to play
	 */
	public Sound getActivateSound()
	{
		return activateSound;
	}
	/**
	 * Returns casting animation
	 * @return Simple animated object
	 */
	public SimpleAnim getCastAnim()
	{
		return castAnim;
	}
	/**
	 * Returns activation animation
	 * @return Simple animated object
	 */
	public SimpleAnim getActiveAnim()
	{
		return activeAnim;
	}
	/**
	 * Returns required type of avatar animation
	 * @return Avatar anim type enum
	 */
	public AvatarAnimType getAvatarAnimType()
	{
		return avatarAnim;
	}
	/**
	 * Returns current skill target (may return NULL!)
	 * @return Targetable object or null if this skill has no target
	 */
	public Targetable getTarget()
	{
		return target;
	}
	/**
	 * Return type of skill effect
	 * @return Effect type enumeration
	 */
	public EffectType getEffectType()
	{
		return type;
	}
	/**
	 * Returns all skill activation effects
	 * @return List with effects
	 */
	public List<Effect> getEffects()
	{
		List<Effect> effectsToPass = new ArrayList<>();
        if(effects != null)
        {
	        for(String effectId : effects)
	        {
	        	effectsToPass.add(EffectsBase.getEffect(this, effectId));
	        }
        }
        return effectsToPass;
	}
	/**
	 * Returns list with all IDs of effects from this skill
	 * @return List with effects IDs
	 */
	public List<String> getEffectsIds()
	{
		return effects;
	}
	/**
	 * Return new instance of effect with specified ID from skill effects list
	 * @param effectId ID of desired effect 
	 * @return New instance of effect with specified ID or null if there is no such effect in skill effects list
	 */
	public Effect getEffect(String effectId)
	{
		for(String id : effects)
		{
			if(id.equals(effectId))
				return EffectsBase.getEffect(this, id);
		}
		return null;
	}
	/**
	 * Checks if skill is magical
	 * @return True if skill is magical, false otherwise
	 */
	public boolean isMagic()
	{
		for(Requirement req : useReqs)
		{
		    if(ManaRequirement.class.isInstance(req))
		        return true;
		}
		return false;
	}
	
	public boolean isInstant()
	{
		if(castTime <= 0)
			return true;
		else
			return false;
	}
	/**
	 * Checks if skill is ready
	 * @return True if skill is ready, false otherwise
	 */
	public boolean isReady()
	{
		return ready;
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
	 * Resets skill to default state
	 */
	public void reset()
	{
	    active = false;
	    ready = true;
	    timer = 0;
	}
	
	@Override
	public Element getSave(Document doc)
	{
		Element skillE = doc.createElement("skill");
		skillE.setAttribute("cooldownTime", timer+"");
		skillE.setTextContent(id);
		return skillE;
	}
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
	 * Sets sound effect dependent on skill effect type
	 * @throws SlickException
	 * @throws IOException
	 */
	protected void setSoundEffect() throws SlickException, IOException
	{
	    	this.castSound = type.getCastSoundEffect();
	    	this.activateSound = type.getActivationSoundEffect();
	}
	/**
	 * Sets graphic effects for this skill
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 */
	protected void setGraphicEffects(GameContainer gc) throws SlickException, IOException
	{
		activeAnim = type.getActivationGraphicEffect(gc);
		castAnim = type.getCastGraphicEffect(gc);
	}
}
