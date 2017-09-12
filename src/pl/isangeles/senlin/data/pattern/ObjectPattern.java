/*
 * ObjectPattern.java
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
package pl.isangeles.senlin.data.pattern;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import pl.isangeles.senlin.core.SimpleGameObject;
import pl.isangeles.senlin.core.action.Action;
import pl.isangeles.senlin.core.action.ActionType;
import pl.isangeles.senlin.core.action.EffectAction;
import pl.isangeles.senlin.core.action.LootAction;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.data.ItemsBase;
import pl.isangeles.senlin.graphic.GameObject;
import pl.isangeles.senlin.graphic.SimpleAnimObject;
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
	private final String type;
	private final int frames;
	private final int fWidth;
	private final int fHeight;
	private final boolean flipped;
	private final ActionType action;
	private final int gold;
	private final List<RandomItem> objectItems = new ArrayList<>();
	/**
	 * ObjectPattern constructor
	 */
	public ObjectPattern(String id, String mainTexture, String portrait, String sound, String type, int frames, int fWidth, int fHeight, String action,
						 int gold, Map<String, Boolean> items) 
	{
		this.id = id;
		this.info = TConnector.getTextFromModule("objects", id);
		this.mainTexture = mainTexture;
		this.portrait = portrait;
		this.sound = sound;
		this.type = type;
		this.frames = frames;
		this.fWidth = fWidth;
		this.fHeight = fHeight;
		this.flipped = false;
		this.action = ActionType.fromString(action);
		this.gold = gold;
		for(String itemId : items.keySet())
		{
		    objectItems.add(new RandomItem(itemId, items.get(itemId)));
		}
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
	public SimpleGameObject make(GameContainer gc) throws SlickException, IOException, FontFormatException
	{
	    Portrait uiPortrait = new Portrait(GConnector.getObjectPortrait(portrait), gc);
	    Sound objectSound = null;
	    if(sound != "")
		    objectSound = new Sound(AConnector.getInput("effects/" + sound), sound);
	    Action objectAction;
	    switch(action)
	    {
	    case LOOT:
	        objectAction = new LootAction();
	        break;
	    default:
	        objectAction = new EffectAction();
	    }
	    
	    List<Item> itemsIn = new ArrayList<>();
	    for(RandomItem rItem : objectItems)
	    {
	        itemsIn.add(rItem.make());
	    }
	    
	    if(type.equals("anim"))
	    {
	    	SimpleAnimObject animTexture = new SimpleAnimObject(GConnector.getInput("object/anim/"+mainTexture), id, flipped, fWidth, fHeight, frames, info, gc);
		    if(objectSound != null)
				return new SimpleGameObject(id, animTexture, uiPortrait, objectSound, objectAction, gold, itemsIn);
		    else
		    	return new SimpleGameObject(id, animTexture, uiPortrait, objectAction, gold, itemsIn); 	
	    }
	    if(type.equals("static"))
	    {
	    	Sprite staticTexture = new Sprite(GConnector.getInput("object/static/"+mainTexture), id, flipped, info, gc);
			if(objectSound != null)
				return new SimpleGameObject(id, staticTexture, uiPortrait, objectSound, objectAction, gold, itemsIn);
			else
				return new SimpleGameObject(id, staticTexture, uiPortrait, objectAction, gold, itemsIn);
	    }
		return null;
	}
}
