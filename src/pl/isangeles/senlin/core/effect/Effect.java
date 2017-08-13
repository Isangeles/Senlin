/*
 * Effect.java
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
package pl.isangeles.senlin.core.effect;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Attributes;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.gui.tools.EffectTile;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for effects used by items, skills, etc.
 * @author Isangeles
 *
 */
public class Effect 
{
    private String id;
    private String name;
    private String info;
    private String imgName;
	private EffectType type;
	private int hpMod;
	private int manaMod;
	private Attributes attMod;
	private float hasteMod;
	private float dodgeMod;
	private int dmgMod;
	private int dot;
	private int duration;
	private int time;
	private int dotTimer;
	private boolean dotActive;
	private boolean on;
	private EffectTile tile;
	/**
	 * Effect constructor
	 * @param id Effect ID
	 * @param name Effect name
	 * @param info Basic informations about effect
	 * @param hpMod Affect on health points
	 * @param manaMod Affect on magicka points
	 * @param attMod Affect on attributions 
	 * @param hasteMod Affect on haste
	 * @param dodgeMod Affect on dodge rating
	 * @param dmgMod Affect on damage
     * @param dot Damage over time effect value (positive value heals target) 
	 * @param duration Effect duration
	 * @param type Effect type
	 * @throws FontFormatException 
	 * @throws IOException 
	 * @throws SlickException 
	 */
	public Effect(String id, String imgName, int hpMod, int manaMod, Attributes attMod, float hasteMod, float dodgeMod, 
				  int dmgMod, int dot, int duration, EffectType type, GameContainer gc) throws SlickException, IOException, FontFormatException 
	{
	    this.id = id;
	    this.name = TConnector.getInfoFromModule("effects", id)[0];
	    this.info = TConnector.getInfoFromModule("effects", id)[1];
	    this.imgName = imgName;
		this.type = type;
		this.hpMod = hpMod;
		this.manaMod = manaMod;
		this.attMod = attMod;
		this.hasteMod = hasteMod;
		this.dodgeMod = dodgeMod;
		this.dmgMod = dmgMod;
		this.dot = dot;
		this.duration = duration;
		buildTile(gc);
	}
	/**
	 * Starts affect on specified character
	 * @param character Some game character
	 */
	public void affect(Character character)
	{
		if(dotActive)
		{
			character.addHealth(dot);
			dotActive = false;
		}
	}
	/**
	 * Removes affect from specified character
	 * @param character Some game character
	 */
	public void removeFrom(Targetable character)
	{
		character.modHealth(-hpMod);
		character.modMagicka(-manaMod);
		character.modAttributes(attMod.nagative());
		time = 0;
		dotTimer = 0;
	}
	
	public int getHpMod()
	{
		return hpMod;
	}
	
	public int getManaMod()
	{
		return manaMod;
	}
	
	public Attributes getAttributesMod()
	{
		return attMod;
	}
	
	public float getHasteMod()
	{
		return hasteMod;
	}
	
	public float getDodgeMod()
	{
		return dodgeMod;
	}
	
	public int getDmgMod()
	{
		return dmgMod;
	}
	
	public int getDuration()
	{
		return duration;
	}
	
	public boolean isOn()
	{
		return on;
	}
	/**
	 * Updates effect time
	 * @param delta Time from last game loop update
	 */
	public void updateTime(int delta)
	{
		time += delta;
		dotTimer += delta;
		if(time >= duration)
			on = false;
		if(dotTimer > 1000 && !dotActive)
		{
			dotActive = true;
			dotTimer = 0;
		}
	}
	/**
	 * Starts effect timer
	 */
	public void turnOn(Targetable character)
	{
		on = true;
		character.getEffects().add(this);
		character.modHealth(hpMod);
		character.modMagicka(manaMod);
		character.modAttributes(attMod);
	}
	/**
	 * Sets current effect time 
	 * @param timeInMillis Time in milliseconds
	 */
	public void setTime(int timeInMillis)
	{
	    time = timeInMillis;
	}
	/**
	 * Returns current effect time
	 * @return Effect time in milliseconds
	 */
	public int getTime()
	{
	    return time;
	}
	/**
	 * Returns effect UI icon
	 * @return EffectTile object
	 */
	public EffectTile getTile()
	{
		return tile;
	}
	/**
	 * Returns effect Id
	 * @return String with effect ID
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * Builds effect UI icon
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	private void buildTile(GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		tile = new EffectTile(GConnector.getInput("icon/effect/"+imgName), "uiEff"+id, false, gc, info);
	}

}
