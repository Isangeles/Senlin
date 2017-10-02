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
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Attributes;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.bonus.Bonus;
import pl.isangeles.senlin.core.bonus.Bonuses;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.gui.tools.EffectTile;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.Stopwatch;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for effects used by items, skills, etc.
 * @author Isangeles
 *
 */
public class Effect implements SaveElement
{
    private String id;
    private String name;
    private String info;
    private String imgName;
	private EffectType type;
	private Bonuses bonuses;
	private int dot;
	private int duration;
	private int time;
	private int dotTimer;
	private boolean dotActive;
	private boolean on;
	private boolean alwaysOn;
	private EffectTile tile;
	private EffectSource source;
	private String ownerToRestore;
	private String sourceToRestore;
	/**
	 * Effect constructor
	 * @param id Effect ID
	 * @param imgName Effect icon image name
	 * @param bonuses Effect bonuses
	 * @param dot Damage over time
	 * @param duration Effect duration
	 * @param type Effect Type
	 * @param source Effect source, e.g. skill or item
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Effect(String id, String imgName, Bonuses bonuses, int dot, int duration, EffectType type, EffectSource source, GameContainer gc) throws SlickException, IOException, FontFormatException 
	{
	    this.id = id;
	    this.name = TConnector.getInfoFromModule("effects", id)[0];
	    this.info = TConnector.getInfoFromModule("effects", id)[1];
	    this.imgName = imgName;
		this.type = type;
		this.bonuses = bonuses;
		this.dot = dot;
		this.duration = duration;
		if(duration == -1)
			alwaysOn = true;
		this.source = source;
		buildTile(gc);
	}
	/**
	 * Effect constructor(with saved source ID)
	 * @param id Effect ID
	 * @param imgName Effect icon image name
	 * @param bonuses Effect bonuses
	 * @param dot Damage over time
	 * @param duration Effect duration
	 * @param type Effect Type
	 * @param savedSourceId Saved serial ID of effect source(source restored later in update)
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Effect(String id, String imgName, Bonuses bonuses, int dot, int duration, EffectType type, String savedOwnerId, String savedSourceId, GameContainer gc) 
			throws SlickException, IOException, FontFormatException 
	{
	    this.id = id;
	    this.name = TConnector.getInfoFromModule("effects", id)[0];
	    this.info = TConnector.getInfoFromModule("effects", id)[1];
	    this.imgName = imgName;
		this.type = type;
		this.bonuses = bonuses;
		this.dot = dot;
		this.duration = duration;
		if(duration == -1)
			alwaysOn = true;
		ownerToRestore = savedOwnerId;
		sourceToRestore = savedSourceId;
		buildTile(gc);
	}
	/**
	 * Starts affect on specified character
	 * @param character Some game character
	 */
	public void affect(Targetable target)
	{
		if(dotActive)
		{
			if(source != null)
				target.takeHealth(source.getOwner(), -dot);
			else
				target.modHealth(dot);
			dotActive = false;
		}
	}
	/**
	 * Removes affect from specified character
	 * @param target Some game character
	 */
	public void removeFrom(Targetable target)
	{
		for(Bonus bonus : bonuses)
		{
		    if(target.hasBonus(bonus))
		        target.removeBonus(bonus);
		}
		time = 0;
		dotTimer = 0;
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
		if(ownerToRestore != null)
		{
			Character owner = Global.getChapter().getCharacter(ownerToRestore);
			if(owner != null)
				source = owner.getEffectSource(sourceToRestore);
			
			ownerToRestore = null;
			sourceToRestore = null;
			if(source != null)
			{
				tile.setInfo(getInfo());
				//Log.addSystem("source properly restored: " + source.getSerialId()); DEBUG
			}
		}
		if(!alwaysOn)
		{
			time += delta;
			if(time >= duration) 
			{
				on = false;
			}
		}
		
		if(dot != 0)
		{
			dotTimer += delta;
			if(dotTimer > 1000 && !dotActive)
			{
				dotActive = true;
				dotTimer = 0;
			}
		}
	}
	/**
	 * Starts effect timer
	 */
	public boolean turnOn(Targetable character)
	{
		on = true;
		//character.getEffects().add(this);
		for(Bonus bonus : bonuses)
		{
		    if(!character.hasBonus(bonus))
		        character.addBonus(bonus);
		}
		return on;
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
	 * Returns string with effect ID and source ID
	 * @return String with effect and source IDs
	 */
	public String getSourceId()
	{
		if(source != null)
			return id + "_" + source.getSerialId();
		else
			return id + "_null"; 
	}
	/**
	 * Returns source of this effect(may return NULl)
	 * @return Effect source
	 */
	public EffectSource getSource()
	{
		return source;
	}
	/**
	 * Returns effect info
	 * @return String with effect info
	 */
	public String getInfo()
	{
		String fullInfo = info;
		for(Bonus bonus : bonuses)
		{
			fullInfo += System.lineSeparator() + bonus.getInfo();
		}
		if(!alwaysOn)
		{
			fullInfo += System.lineSeparator() + TConnector.getText("ui", "effDuration") + ":" + Stopwatch.timeFromMillis(duration);
		}
		//DEBUG
		if(source != null)
			fullInfo += System.lineSeparator() + "source:" + source.getSerialId();
		else
			fullInfo += System.lineSeparator() + "source:null";
		return fullInfo;
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
		tile = new EffectTile(GConnector.getInput("icon/effect/"+imgName), "uiEff"+id, false, gc, getInfo());
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.data.save.SaveElement#getSave(org.w3c.dom.Document)
	 */
	@Override
	public Element getSave(Document doc) 
	{
		Element effectE = doc.createElement("effect");
        effectE.setAttribute("duration", time+"");
        if(source != null)
        {
            effectE.setAttribute("owner", source.getOwner().getSerialId());
            effectE.setAttribute("source", source.getId());
        }
        effectE.setTextContent(id);
        return effectE;
	}

}
