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

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.SimpleGameObject;
import pl.isangeles.senlin.graphic.GameObject;
import pl.isangeles.senlin.graphic.SimpleAnimObject;
import pl.isangeles.senlin.graphic.Sprite;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for game objects patterns
 * @author Isangeles
 *
 */
public class ObjectPattern 
{
	private String id;
	private String info;
	private String mainTexture;
	private String type;
	private int frames;
	private int fWidth;
	private int fHeight;
	private boolean flipped;
	/**
	 * ObjectPattern constructor
	 */
	public ObjectPattern(String id, String mainTexture, String type, int frames, int fWidth, int fHeight) 
	{
		this.id = id;
		this.info = TConnector.getText("objects", id);
		this.mainTexture = mainTexture;
		this.type = type;
		this.frames = frames;
		this.fWidth = fWidth;
		this.fHeight = fHeight;
		this.flipped = false;
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
		switch(type)
		{
		case "anim":
		    SimpleAnimObject animTexture = new SimpleAnimObject(GConnector.getInput("object/anim/"+mainTexture), id, flipped, fWidth, fHeight, frames, info, gc);
			return new SimpleGameObject(id, animTexture);
		case "static":
			Sprite staticTexture = new Sprite(GConnector.getInput("object/static/"+mainTexture), id, flipped, info, gc);
			return new SimpleGameObject(id, staticTexture);
		}
		return null;
	}
}
