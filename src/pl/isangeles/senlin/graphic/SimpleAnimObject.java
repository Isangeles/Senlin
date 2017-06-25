/*
 * SimpleAnimObject.java
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
package pl.isangeles.senlin.graphic;

import java.io.InputStream;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
/**
 * Class for simple animated objects
 * @author Isangeles
 *
 */
public class SimpleAnimObject extends GameObject 
{
	private String id;
	private Animation anim;
	/**
	 * SimpleAnimObject constructor
	 * @param is InputStream to sprite sheet file
	 * @param ref Name for sprite sheet
	 * @param flipped If sprite sheet should be flipped
	 * @throws SlickException
	 */
	public SimpleAnimObject(InputStream is, String ref, boolean flipped, int frameWidth, int frameHeight, int nOfFrames) throws SlickException 
	{
		super(is, ref, flipped);
		
		id = ref;
		
		SpriteSheet ss = new SpriteSheet(this, frameWidth, frameHeight);
		Sprite[] frames;
		int duration[];
		
		switch(nOfFrames)
		{
		case 5:
			frames = new Sprite[]{new Sprite(ss.getSprite(0, 0)), new Sprite(ss.getSprite(1, 0)), new Sprite(ss.getSprite(2, 0)),
								  new Sprite(ss.getSprite(3, 0)), new Sprite(ss.getSprite(4, 0))};
			duration = new int[]{300, 300, 300, 300, 300};
			break;
		default:
			frames = new Sprite[]{new Sprite(ss.getSprite(0, 0)), new Sprite(ss.getSprite(0, 1))};
			duration = new int[]{300, 300};
			break;
		}
		
		anim = new Animation(frames, duration, true);
	}
	
	@Override
	public void draw(float x, float y, float scale, boolean scaledPos)
	{
		anim.draw(x ,y, (anim.getCurrentFrame().getWidth() * getScale())*scale, (anim.getCurrentFrame().getHeight() * getScale())*scale);
	}
	
	@Override
	public void draw(float reqSize)
	{
		anim.draw(x ,y, (anim.getCurrentFrame().getWidth() * getScale())*scale, (anim.getCurrentFrame().getHeight() * getScale())*scale);
	}
	/**
	 * Returns object ID
	 * @return String with object ID
	 */
	public String getId()
	{
		return id;
	}
}
