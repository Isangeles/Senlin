/*
 * Equippable.java
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
import pl.isangeles.senlin.core.bonus.Bonuses;
import pl.isangeles.senlin.core.character.Gender;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.effect.EffectSource;
import pl.isangeles.senlin.data.EffectsBase;
import pl.isangeles.senlin.graphic.AnimObject;
/**
 * Class for equippable items
 * @author Isangeles
 *
 */
public abstract class Equippable extends Item implements EffectSource
{
	protected int reqLevel;
	protected final int type;
	protected final ItemMaterial material;
	protected Bonuses bonuses;
	protected AnimObject itemMSprite;
	protected AnimObject itemFSprite;
	protected List<String> equipEffects;
	
	public Equippable(String id, int value, String imgName, GameContainer gc, int reqLevel, Bonuses bonuses, List<String> equipEffects, int type, ItemMaterial material)
			throws SlickException, IOException, FontFormatException
	{
		super(id, value, 1, imgName, gc);
		this.reqLevel = reqLevel;
		this.bonuses = bonuses;
		this.equipEffects = equipEffects;
		this.type = type;
		this.material = material;
		onUse = new EquipAction(this);
	}
	
	public Equippable(String id, int serial, int value, String imgName, GameContainer gc, int reqLevel, Bonuses bonuses, List<String> equipEffects, int type, ItemMaterial material)
			throws SlickException, IOException, FontFormatException
	{
		super(id, serial, value, 1, imgName, gc);
		this.reqLevel = reqLevel;
		this.bonuses = bonuses;
		this.equipEffects = equipEffects;
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
