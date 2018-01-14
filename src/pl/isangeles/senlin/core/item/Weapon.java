/*
 * Weapon.java
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
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.bonus.Modifiers;
import pl.isangeles.senlin.core.character.Gender;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.data.EffectsBase;
import pl.isangeles.senlin.data.GBase;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.graphic.AnimObject;
import pl.isangeles.senlin.gui.tools.ItemTile;
/**
 * Class for weapons
 * @author Isangeles
 *
 */
public class Weapon extends Equippable 
{
	public static final int DAGGER = 0,
							SWORD = 1,
							AXE = 2,
							MACE = 3,
							SPEAR = 4,
							BOW = 5,
							FIST = 6;
	private int maxDamage;
	private int minDamage;
	private List<String> hitEffects;
	/**
	 * Weapon constructor
	 * @param id Weapon ID	
	 * @param name Weapon name
	 * @param info Informations about weapon
	 * @param type Weapon type (0-5)
	 * @param material Weapon material (0-2)
	 * @param value Weapon value
	 * @param maxDmg Max weapon damage
	 * @param minDmg Min weapon damage
	 * @param bonuses Weapon bonuses
	 * @param equippEffects List with IDs of all equip effects 
	 * @param hitEffects List with IDs of all hit effects 
	 * @param reqLevel Required level
	 * @param picName Weapon icon image file name
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Weapon(String id, WeaponType type, ItemMaterial material, int value, int minDmg, int maxDmg, Modifiers bonuses, List<String> equipEffects, 
				  List<String> hitEffects, int reqLevel, String picName, String spriteName, GameContainer gc) 
			throws SlickException, IOException, FontFormatException 
	{
		super(id, value, picName, gc, reqLevel, bonuses, equipEffects, type.ordinal(), material);
		this.minDamage = minDmg;
		this.maxDamage = maxDmg;
		this.hitEffects = hitEffects;
        this.itemTile = this.buildIcon(gc);
        itemMSprite = new AnimObject(GConnector.getInput("sprite/item/" + spriteName), "sprite"+id, false, 80, 90);
	}
	/**
	 * Weapon constructor (with saved serial number)
	 * @param id Weapon ID	
     * @param serial Saved serial number
	 * @param name Weapon name
	 * @param info Informations about weapon
	 * @param type Weapon type (0-5)
	 * @param material Weapon material (0-2)
	 * @param value Weapon value
	 * @param maxDmg Max weapon damage
	 * @param minDmg Min weapon damage
	 * @param bonuses Weapon bonuses
	 * @param equippEffects List with IDs of all equip effects 
	 * @param hitEffects List with IDs of all hit effects 
	 * @param reqLevel Required level
	 * @param picName Weapon icon image file name
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Weapon(String id, long serial, WeaponType type, ItemMaterial material, int value, int minDmg, int maxDmg, Modifiers bonuses, List<String> equipEffects, 
				  List<String> hitEffects, int reqLevel, String picName, String spriteName, GameContainer gc) 
			throws SlickException, IOException, FontFormatException 
	{
		super(id, serial, value, picName, gc, reqLevel, bonuses, equipEffects, type.ordinal(), material);
		this.minDamage = minDmg;
		this.maxDamage = maxDmg;
		this.hitEffects = hitEffects;
        this.itemTile = this.buildIcon(gc);
        setMSprite(spriteName);
	}
	/**
	 * Weapon constructor (with default spritesheet)
	 * @param id Weapon ID	
	 * @param name Weapon name
	 * @param info Informations about weapon
	 * @param type Weapon type (0-5)
	 * @param material Weapon material (0-2)
	 * @param value Weapon value
	 * @param maxDmg Max weapon damage
	 * @param minDmg Min weapon damage
	 * @param bonuses Weapon bonuses
	 * @param equippEffects List with IDs of all equip effects 
	 * @param hitEffects List with IDs of all hit effects 
	 * @param reqLevel Required level
	 * @param picName Weapon icon image file name
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Weapon(String id, WeaponType type, ItemMaterial material, int value, int minDmg, int maxDmg, Modifiers bonuses, List<String> equipEffects, 
				  List<String> hitEffects, int reqLevel, String picName, GameContainer gc) throws SlickException, IOException, FontFormatException 
	{
		this(id, type, material, value, minDmg, maxDmg, bonuses, equipEffects, hitEffects, reqLevel, picName, type.getDefaultMaleSpritesheet(), gc);
	}
	/**
	 * Weapon constructor (with saved serial number and default spritesheet)
	 * @param id Weapon ID	
     * @param serial Saved serial number
	 * @param name Weapon name
	 * @param info Informations about weapon
	 * @param type Weapon type (0-5)
	 * @param material Weapon material (0-2)
	 * @param value Weapon value
	 * @param maxDmg Max weapon damage
	 * @param minDmg Min weapon damage
	 * @param bonuses Weapon bonuses
	 * @param equippEffects List with IDs of all equip effects 
	 * @param hitEffects List with IDs of all hit effects 
	 * @param reqLevel Required level
	 * @param picName Weapon icon image file name
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Weapon(String id, long serial, WeaponType type, ItemMaterial material, int value, int minDmg, int maxDmg, Modifiers bonuses, List<String> equipEffects, 
				  List<String> hitEffects, int reqLevel, String picName, GameContainer gc) throws SlickException, IOException, FontFormatException 
	{
		this(id, serial, type, material, value, minDmg, maxDmg, bonuses, equipEffects, hitEffects, reqLevel, picName, type.getDefaultMaleSpritesheet(), gc);
	}
	/**
	 * Returns weapon maximal and minimal damage
	 * @return Table with min[0] and max[1] damage
	 */
	public int[] getDamage()
	{
		return new int[]{minDamage, maxDamage};
	}
	/**
	 * Return weapon type
	 * @return Item type ID
	 */
	public int getType()
	{
		return type;
	}
	/**
	 * Returns full info about item
	 */
	@Override
	protected String getInfo()
	{
		String fullInfo = name + System.lineSeparator() +  getTypeName() + System.lineSeparator() + getMaterialName() + System.lineSeparator() +
						TConnector.getText("ui", "dmgName") + ": " +  minDamage + "-" + maxDamage + System.lineSeparator() + bonuses.getInfo() + 
						TConnector.getText("ui", "itemRLInfo") + ": " + reqLevel + System.lineSeparator() + info + System.lineSeparator() + 
						TConnector.getText("ui", "itemVInfo") + ": " + value;
		
		return fullInfo;
	}
	
	@Override
	protected String getTypeName()
	{
		switch(type)
		{
		case DAGGER:
			return TConnector.getText("ui", "weaDagger");
		case SWORD:
			return TConnector.getText("ui", "weaSword");
		case AXE:
			return TConnector.getText("ui", "weaAxe");
		case MACE:
			return TConnector.getText("ui", "weaMace");
		case SPEAR:
			return TConnector.getText("ui", "weaSpear");
		case BOW:
			return TConnector.getText("ui", "weaBow");
		default:
			return TConnector.getText("ui", "errorName");
		}
	}
	
	private String getMaterialName()
	{
		return material.toString();
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Usable#use(pl.isangeles.senlin.core.Targetable, pl.isangeles.senlin.core.Targetable)
	 */
	@Override
	public boolean use(Targetable user, Targetable target) 
	{
		return onUse.start(user, target);
	}
	
	@Override
	public AnimObject getSpriteFor(Gender sex)
	{
	    return itemMSprite;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.effect.EffectSource#getOwner()
	 */
	@Override
	public Targetable getOwner() 
	{
		return null;
	}
	/**
	 * Returns all 'on-hit' effects
	 * @return Collection with all 'on-hit' effects
	 */
	public Collection<Effect> getHitEffects() 
	{
		List<Effect> effects = new ArrayList<>();
		for(String id : hitEffects)
		{
			effects.add(EffectsBase.getEffect(this, id));
		}
		return effects;
	}
	/**
	 * Returns 'on-hit' effects with specified ID
	 * @param effectId Effects ID
	 * @return New instance of effect with specified ID or null if no such effects was found
	 */
	public Effect getHitEffect(String effectId) 
	{
		for(String id : hitEffects)
		{
			if(id.equals(effectId))
				return EffectsBase.getEffect(this, id);
		}
		return null;
	}
	/**
	 * Returns all IDs of all 'on-hit' effects
	 * @return List with effects IDs
	 */
	public List<String> getHitEffectsIds() 
	{
		return hitEffects;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.item.Item#setTile(org.newdawn.slick.GameContainer)
	 */
	@Override
	protected ItemTile buildIcon(GameContainer gc) throws SlickException, IOException, FontFormatException 
    {
		try 
		{
			if(!icons.containsKey(id))
			{
				Image iconImg = new Image(GConnector.getInput("icon/item/weapon/"+imgName), id, false);
				icons.put(id, iconImg);
				return new ItemTile(iconImg, gc, this.getInfo());
			}
			else
			{
				Image iconImg = icons.get(id);
				return new ItemTile(iconImg, gc, this.getInfo());
			}
    	}
		catch(SlickException | IOException | NullPointerException e) 
    	{
			return new ItemTile(GBase.getImage("errorIcon"), gc, this.getInfo());
		}
    }
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.item.Equippable#setMSprite(java.lang.String)
	 */
	@Override
	protected void setMSprite(String ssName) throws IOException, SlickException 
	{
        itemMSprite = new AnimObject(GConnector.getInput("sprite/item/" + ssName), "sprite"+id, false, 80, 90);
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.item.Equippable#setFSprite(java.lang.String)
	 */
	@Override
	protected void setFSprite(String ssName) throws IOException, SlickException 
	{
		//Weapons use only one sprite sheet(male)
	}
}
