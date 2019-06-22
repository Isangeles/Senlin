/*
 * ObjectPattern.java
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
package pl.isangeles.senlin.data.pattern;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import pl.isangeles.senlin.core.InventoryLock;
import pl.isangeles.senlin.core.TargetableObject;
import pl.isangeles.senlin.core.action.Action;
import pl.isangeles.senlin.core.action.ActionType;
import pl.isangeles.senlin.core.action.EffectAction;
import pl.isangeles.senlin.core.action.LootAction;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.data.ItemsBase;
import pl.isangeles.senlin.graphic.GameObject;
import pl.isangeles.senlin.graphic.SimpleAnim;
import pl.isangeles.senlin.graphic.Sprite;
import pl.isangeles.senlin.gui.Portrait;
import pl.isangeles.senlin.util.AConnector;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for game objects patterns
 * @author Isangeles
 *
 */
public class ObjectPattern 
{
	private final String id;
	private final String info;
	private final String mainTexture;
	private final String portrait;
	private final String sound;
	private final boolean anim;
	private final int frames;
	private final int fWidth;
	private final int fHeight;
	private final boolean flipped;
	private final ActionPattern actionP;
	private final int gold;
	private final List<RandomItem> objectItems = new ArrayList<>();
	private final InventoryLock lock;
	/**
	 * Game object pattern constructor(with animated texture)
	 * @param id ID for object
	 * @param mainTexture Name of main texture of object
	 * @param portrait Name of portrait for object
	 * @param sound Name of audio file for object sound
	 * @param frames Number of frames for animation
	 * @param fWidth Width of one frame for animation
	 * @param fHeight Height of one frame for animation
	 * @param action Action pattern for object(on click)
	 * @param gold Amount of gold for object inventory
	 * @param items Items for object inventory
	 * @param lock Lock for object inventory
	 */
	public ObjectPattern(String id, String mainTexture, String portrait, String sound, int frames, int fWidth, int fHeight, 
						 ActionPattern action, int gold, List<RandomItem> items, InventoryLock lock) 
	{
		this.id = id;
		this.info = TConnector.getTextFromModule("objects", id);
		this.mainTexture = mainTexture;
		this.portrait = portrait;
		this.sound = sound;
		this.anim = true;
		this.frames = frames;
		this.fWidth = fWidth;
		this.fHeight = fHeight;
		this.flipped = false;
		this.actionP = action;
		this.gold = gold;
		this.objectItems.addAll(items);
		this.lock = lock;
	}
	/**
	 * Game object pattern constructor(with static texture)
	 * @param id ID for object
	 * @param mainTexture Name of main texture of object
	 * @param portrait Name of portrait for object
	 * @param sound Name of audio file for object sound
	 * @param action Action pattern for object(on click)
	 * @param gold Amount of gold for object inventory
	 * @param items Items for object inventory
	 * @param lock Lock for object inventory
	 */
	public ObjectPattern(String id, String mainTexture, String portrait, String sound, ActionPattern action, int gold, 
						 List<RandomItem> items, InventoryLock lock) 
	{
		this.id = id;
		this.info = TConnector.getTextFromModule("objects", id);
		this.mainTexture = mainTexture;
		this.portrait = portrait;
		this.sound = sound;
		this.anim = false;
		this.frames = 0;
		this.fWidth = 0;
		this.fHeight = 0;
		this.flipped = false;
		this.actionP = action;
		this.gold = gold;
		this.objectItems.addAll(items);
		this.lock = lock;
	}
	/**
	 * Return ID of this pattern object 
	 * @return String with object ID
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * Creates new game object from this pattern
	 * @return New game object
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException 
	 */
	public TargetableObject make(GameContainer gc) throws SlickException, IOException, FontFormatException
	{
	    Portrait uiPortrait = new Portrait(GConnector.getObjectPortrait(portrait), gc);
	    Sound objectSound = null;
	    if(sound != "")
		    objectSound = new Sound(AConnector.getInput("effects/" + sound), sound);
	    
	    
	    TargetableObject object = null;
	    if(anim)
	    {
	    	SimpleAnim animTexture = new SimpleAnim(GConnector.getInput("object/anim/"+mainTexture), id, flipped, fWidth, fHeight, frames, info, gc);
		    if(objectSound != null)
				object = new TargetableObject(id, animTexture, uiPortrait, objectSound, actionP.make());
		    else
		    	object = new TargetableObject(id, animTexture, uiPortrait, actionP.make()); 	
	    }
	    else
	    {
	    	Sprite staticTexture = new Sprite(GConnector.getInput("object/static/"+mainTexture), id, flipped, info, gc);
			if(objectSound != null)
				object = new TargetableObject(id, staticTexture, uiPortrait, objectSound, actionP.make());
			else
				object = new TargetableObject(id, staticTexture, uiPortrait, actionP.make());
	    }
	    
	    if(object != null)
	    {
	    	for(RandomItem rItem : objectItems)
		    {
		        object.getInventory().add(rItem.make());
		    }
		    object.getInventory().lock(lock);
	    }
	    
		return object;
	}
}
