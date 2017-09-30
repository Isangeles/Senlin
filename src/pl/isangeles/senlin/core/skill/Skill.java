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
public abstract class Skill implements SlotContent, SaveElement
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
    protected List<String> effects;
    protected AvatarAnimType avatarAnim = AvatarAnimType.MELEE;
	protected Sound castSound;
	protected Sound activateSound;
	protected SimpleAnim castAnim;
	protected SimpleAnim activeAnim;
    private int castTime;
    private int timer;
 	private String imgName;
	private SkillTile tile;
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
		this.name = TConnector.getInfoFromModule("skills", id)[0];
		this.info = TConnector.getInfoFromModule("skills", id)[1];
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
		int fullCooldown = cooldown - owner.getAttributes().getCastBonus();
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
	public void setActive(boolean active)
	{
		this.active = active;
	}
	/**
	 * Returns skill casting speed
	 * @return Casting speed
	 */
    public float getCastTime()
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
	        	effectsToPass.add(EffectsBase.getEffect(owner, effectId));
	        }
        }
        return effectsToPass;
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
	 * Sets sound effect dependent on skill type
	 * @throws SlickException
	 * @throws IOException
	 */
	protected void setSoundEffect() throws SlickException, IOException
	{
	    switch(type)
	    {
	    case NORMAL:
	    	this.castSound = new Sound(AConnector.getInput("effects/melee1.ogg"), "melee1.ogg");
	    	this.activateSound = new Sound(AConnector.getInput("effects/melee1.ogg"), "melee1.ogg");
	    	return;
	    case FIRE:
	    	this.castSound = new Sound(AConnector.getInput("effects/firebCast.aif"), "firebCast.aif");
	    	this.activateSound = new Sound(AConnector.getInput("effects/spellHit.aif"), "spellHit.aif");
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
	 * Sets graphic effects for this skill
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 */
	protected void setGraphicEffects(GameContainer gc) throws SlickException, IOException
	{
		switch(type)
		{
		case FIRE:
			castAnim = new SimpleAnim(GConnector.getInput("effect/fireSpellCast.png"), "fireSpellC.png", false, 70, 70, 3, gc);
			activeAnim = new SimpleAnim(GConnector.getInput("effect/fireSpellActive.png"), "fireSpellA.png", false, 70, 70, 3, gc);
			break;
		default:
			castAnim = null;
			activeAnim = null;
		}
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
