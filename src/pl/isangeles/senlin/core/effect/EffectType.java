/*
 * EffectType.java
 * 
 * Copyright 2017-2018 Dariusz Sikora <darek@pc-solus>
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
package pl.isangeles.senlin.core.effect;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import pl.isangeles.senlin.graphic.SimpleAnim;
import pl.isangeles.senlin.util.AConnector;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;

/**
 * Enumeration for effect types used by items, skills, etc.
 * @author Isangeles
 *
 */
public enum EffectType 
{
	FIRE, ICE, NATURE, MAGIC, NORMAL;
	/**
	 * Converts type ID to effect type enumeration
	 * @param typeId String with type ID
	 * @return Effect type enumeration
	 */
	public static EffectType fromId(String typeId)
	{
		switch(typeId)
		{
		case "fire":
			return EffectType.FIRE;
		case "ice":
			return EffectType.ICE;
		case "nature":
			return EffectType.NATURE;
		case "magic":
			return EffectType.MAGIC;
		default:
			return EffectType.NORMAL;
		}
	}
	/**
	 * Returns type name
	 * @return String with type name
	 */
	public String getName()
	{
		switch(this)
		{
		case FIRE:
			return TConnector.getText("ui", "");
		case ICE:
			return TConnector.getText("ui", "");
		case NATURE:
			return TConnector.getText("ui", "");
		case MAGIC:
			return TConnector.getText("ui", "");
		default:
			return "";
		}
	}
	/**
	 * Returns effect name to display
	 * @return String with effect name
	 */
	public String getDisplayName()
	{
		switch(this)
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
	/**
	 * Returns activation sound effect for this effect type
	 * @throws SlickException
	 * @throws IOException
	 * @return Activation sound
	 */
	public Sound getActivationSoundEffect() throws SlickException, IOException
	{
	    switch(this)
	    {
	    case NORMAL:
	    	return new Sound(AConnector.getInput("effects/melee1.ogg"), "melee1.ogg");
	    case FIRE:
	    	return new Sound(AConnector.getInput("effects/spellHit.aif"), "spellHit.aif");    	
	    case ICE:
	    	return new Sound(AConnector.getInput("effects/melee1.ogg"), "melee1.ogg"); //TODO ice sound
	    case NATURE:
	    	return new Sound(AConnector.getInput("effects/melee1.ogg"), "melee1.ogg"); //TODO nature sound
	    case MAGIC:
	    	return new Sound(AConnector.getInput("effects/melee1.ogg"), "melee1.ogg"); //TODO magic sound
    	default:
	    	return new Sound(AConnector.getInput("effects/melee1.ogg"), "melee1.ogg");
	    }
	}
	/**
	 * Returns cast sound effect for this effect type
	 * @throws SlickException
	 * @throws IOException
	 * @return Casting sound
	 */
	public Sound getCastSoundEffect() throws SlickException, IOException
	{
	    switch(this)
	    {
	    case NORMAL:
	    	return new Sound(AConnector.getInput("effects/melee1.ogg"), "melee1.ogg");
	    case FIRE:
	    	return new Sound(AConnector.getInput("effects/firebCast.aif"), "firebCast.aif");
	    case ICE:
	    	return new Sound(AConnector.getInput("effects/melee1.ogg"), "melee1.ogg"); //TODO ice sound
	    case NATURE:
	    	return new Sound(AConnector.getInput("effects/melee1.ogg"), "melee1.ogg"); //TODO nature sound
	    case MAGIC:
	    	return new Sound(AConnector.getInput("effects/melee1.ogg"), "melee1.ogg"); //TODO magic sound
    	default:
	    	return new Sound(AConnector.getInput("effects/melee1.ogg"), "melee1.ogg");
	    }
	}
	/**
	 * Returns graphic effects for this effect type
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @return Simple animation
	 */
	public SimpleAnim getActivationGraphicEffect(GameContainer gc) throws SlickException, IOException
	{
		switch(this)
		{
		case FIRE:
			return new SimpleAnim(GConnector.getInput("effect/fireSpellActive.png"), "fireSpellA.png", false, 70, 70, 3, gc);
		default:
			return null;
		}
	}
	/**
	 * Returns graphic effects for this effect type
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @return Simple animation
	 */
	public SimpleAnim getCastGraphicEffect(GameContainer gc) throws SlickException, IOException
	{
		switch(this)
		{
		case FIRE:
			return new SimpleAnim(GConnector.getInput("effect/fireSpellCast.png"), "fireSpellC.png", false, 70, 70, 3, gc);
		default:
			return null;
		}
	}
}
