/*
 * Equippable.java
 * 
 * Copyright 2017-2018 Dariusz Sikora <dev@isangeles.pl>
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
package pl.isangeles.senlin.core.item;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.action.EquipAction;
import pl.isangeles.senlin.core.bonus.Modifiers;
import pl.isangeles.senlin.core.character.Gender;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.effect.EffectSource;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.core.req.Requirements;
import pl.isangeles.senlin.data.EffectsBase;
import pl.isangeles.senlin.graphic.AnimObject;
/**
 * Class for equippable items
 * @author Isangeles
 *
 */
public abstract class Equippable extends Item implements EffectSource
{
	private static final int EQUIPABLE_MAX_STACK = 1;
	
	protected final int type;
	protected final ItemMaterial material;
	protected Modifiers bonuses;
	protected AnimObject itemMSprite;
	protected AnimObject itemFSprite;
	protected List<String> equipEffects;
	protected Requirements equipReq;
	/**
	 * Eqippable item constructor
	 * @param id Item ID
	 * @param value Item value
	 * @param imgName Item icon image file name
	 * @param gc Slick game container
	 * @param reqLevel Required level to equip(only for backward compatibility, now requirement in {@link Eqippable#equipReq})
	 * @param bonuses List with on-equip modifiers
	 * @param equipEffects List with IDs of on-equip effects
	 * @param equipReq List with equip requirements
	 * @param type Type of equipable item
	 * @param material Item material
	 * @throws SlickException 
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Equippable(String id, int value, String imgName, GameContainer gc, Modifiers bonuses, List<String> equipEffects, 
					  List<Requirement> equipReq, int type, ItemMaterial material)
			throws SlickException, IOException, FontFormatException
	{
		super(id, value, EQUIPABLE_MAX_STACK, imgName, gc);
		this.bonuses = bonuses;
		this.equipEffects = equipEffects;
		this.equipReq = new Requirements(equipReq);
		this.type = type;
		this.material = material;
		onUse = new EquipAction(this);
	}
	/**
	 * Eqippable item constructor(with custom serial number, for save engine)
	 * @param id Item ID
	 * @param serial Item serial numberc
	 * @param value Item value
	 * @param imgName Item icon image file name
	 * @param gc Slick game container
	 * @param bonuses List with on-equip modifiers
	 * @param equipEffects List with IDs of on-equip effects
	 * @param equipReq List with equip requirements
	 * @param type Type of equipable item
	 * @param material Item material
	 * @throws SlickException 
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Equippable(String id, long serial, int value, String imgName, GameContainer gc, Modifiers bonuses, 
					  List<String> equipEffects, List<Requirement> equipReq, int type, ItemMaterial material)
			throws SlickException, IOException, FontFormatException
	{
		super(id, serial, value, EQUIPABLE_MAX_STACK, imgName, gc);
		this.bonuses = bonuses;
		this.equipEffects = equipEffects;
		this.equipReq = new Requirements(equipReq);
		this.type = type;
		this.material = material;
		onUse = new EquipAction(this);
	}
	/**
	 * Return item type
	 * @return Integer representing item type
	 */
	public int type()
	{
		return this.type;
	}
	/**
	 * Returns sprite for specified gender
	 * @param sex Game character gender
	 * @return Animated object for specified gender
	 */
	public AnimObject getSpriteFor(Gender sex)
	{
	    switch(sex)
	    {
	    case MALE:
	        return itemMSprite;
	    case FEMALE:
	        return itemFSprite;
	    default:
	        return itemMSprite;
	    }
	}
	/**
	 * Returns equip requirements
	 * @return Equip requirements
	 */
	public Requirements getEquipReqs()
	{
		return equipReq;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.effect.EffectSource#getOwner()
	 */
	@Override
	public Targetable getOwner() 
	{
		return owner;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.effect.EffectSource#getEffects()
	 */
	@Override
	public Collection<Effect> getEffects() 
	{
		List<Effect> effects = new ArrayList<>();
		for(String id : equipEffects)
		{
			effects.add(EffectsBase.getEffect(this, id));
		}
		return effects;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.effect.EffectSource#getEffect(java.lang.String)
	 */
	@Override
	public Effect getEffect(String effectId) 
	{
		for(String id : equipEffects)
		{
			if(id.equals(effectId))
				return EffectsBase.getEffect(this, id);
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.effect.EffectSource#getEffectsIds()
	 */
	@Override
	public List<String> getEffectsIds() 
	{
		return equipEffects;
	}
	/**
	 * Resets item to default state(resets item sprite)
	 */
	public void reset()
	{
		if(itemMSprite != null)
			itemMSprite.reset();
		if(itemFSprite != null)
			itemFSprite.reset();
	}
	/**
     * Sets sprite from file with specified name as item male sprite
     * @param spriteFileName Name of sprite sheet file
	 * @throws SlickException
	 * @throws IOException
	 */
	protected abstract void setMSprite(String ssName) throws IOException, SlickException;
	/**
	 * Sets sprite from file with specified name as item female sprite
	 * @param spriteFileName Name of sprite sheet file
	 * @throws SlickException
	 * @throws IOException
	 */
	protected abstract void setFSprite(String ssName) throws IOException, SlickException;

	protected abstract String getTypeName();
}
