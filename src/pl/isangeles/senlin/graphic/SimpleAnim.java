/*
 * SimpleAnim.java
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

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.gui.MouseOverArea;

import pl.isangeles.senlin.gui.InfoWindow;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.Coords;
/**
 * Class for simple animated objects
 * @author Isangeles
 *
 */
public class SimpleAnim extends GameObject 
{
	private Animation anim;
	private MouseOverArea objectMOA;
	private InfoWindow infoWin;
	/**
	 * SimpleAnimObject constructor(with info window)
	 * @param is InputStream to sprite sheet file
	 * @param ref Name for sprite sheet
	 * @param flipped If sprite sheet should be flipped
	 * @param frameWith Width of single frame on sprite sheet
	 * @param frameHeight Height of single frame on sprite sheet
	 * @param nOfFrames Number of frame in sprite sheet(max 5)
	 * @param info Strin with info for info window
	 * @throws SlickException
	 * @throws FontFormatException 
	 * @throws IOException 
	 */
	public SimpleAnim(InputStream is, String ref, boolean flipped, int frameWidth, int frameHeight, int nOfFrames, String info, GameContainer gc) 
			throws SlickException, IOException, FontFormatException 
	{
		super(is, ref, flipped);
		
		SpriteSheet ss = new SpriteSheet(this, frameWidth, frameHeight);
		Sprite[] frames;
		int duration[];
		
		switch(nOfFrames)
		{
		case 3:
			frames = new Sprite[] {new Sprite(ss.getSubImage(0, 0)), new Sprite(ss.getSubImage(1, 0)), new Sprite(ss.getSubImage(2, 0))};
			duration = new int[] {300, 300, 300};
			break;
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
		objectMOA = new MouseOverArea(gc, anim.getCurrentFrame(), (int)Coords.getX("BR", 0), (int)Coords.getY("BR", 0));
		infoWin = new InfoWindow(gc, info);
	}
	/**
	 * SimpleAnimObject constructor(without info window)
	 * @param is InputStream to sprite sheet file
	 * @param ref Name for sprite sheet
	 * @param flipped If sprite sheet should be flipped
	 * @param frameWith Width of single frame on sprite sheet
	 * @param frameHeight Height of single frame on sprite sheet
	 * @param nOfFrames Number of frame in sprite sheet(max 5)
	 * @throws SlickException
	 * @throws FontFormatException 
	 * @throws IOException 
	 */
	public SimpleAnim(InputStream is, String ref, boolean flipped, int frameWidth, int frameHeight, int nOfFrames, GameContainer gc)
			throws SlickException 
	{
		super(is, ref, flipped);
		
		SpriteSheet ss = new SpriteSheet(this, frameWidth, frameHeight);
		Sprite[] frames;
		int duration[];
		
		switch(nOfFrames)
		{
		case 3:
			frames = new Sprite[] {new Sprite(ss.getSubImage(0, 0)), new Sprite(ss.getSubImage(1, 0)), new Sprite(ss.getSubImage(2, 0))};
			duration = new int[] {300, 300, 300};
			break;
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
		objectMOA = new MouseOverArea(gc, anim.getCurrentFrame(), (int)Coords.getX("BR", 0), (int)Coords.getY("BR", 0));
	}
	
	@Override
	public void draw(float x, float y, float scale, boolean scaledPos)
	{
		objectMOA.setLocation(Global.uiX(x), Global.uiY(y));
		anim.draw(x ,y, (anim.getCurrentFrame().getWidth() * getScale())*scale, (anim.getCurrentFrame().getHeight() * getScale())*scale);
		if(infoWin != null) 
		{
			if(objectMOA.isMouseOver())
				infoWin.draw(x, y);
		}
	}
	
	@Override
	public void draw(float reqSize)
	{
		objectMOA.setLocation(Global.uiX(x), Global.uiY(y));
		anim.draw(x ,y, (anim.getCurrentFrame().getWidth() * getScale())*scale, (anim.getCurrentFrame().getHeight() * getScale())*scale);
		if(infoWin != null)
		{
			if(objectMOA.isMouseOver())
				infoWin.draw(x, y);
		}
	}
	
	@Override
	public boolean isMouseOver()
	{
	    return objectMOA.isMouseOver();
	}
	/**
	 * Checks if current animation frame is last frame
	 * @return True if current frame is last, false otherwise
	 */
	public boolean isLastFrame()
	{
		return anim.getFrame() == anim.getFrameCount()-1;
	}
}
