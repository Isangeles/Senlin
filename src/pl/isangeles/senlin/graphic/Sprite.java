/*
 * Sprite.java
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

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 * Class for simple graphical game objects
 * @author Isangeles
 *
 */
public class Sprite extends GameObject 
{
	public Sprite(InputStream is, String ref, boolean flipped) throws SlickException
	{
		super(is, ref, flipped);
	}
	
	public Sprite(InputStream is, String ref, boolean flipped, String infoText, GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		super(is, ref, flipped, infoText, gc);
	}
	
	public Sprite(Image img) throws SlickException 
	{
		super(img);
	}
	
	public Sprite(Image img, String infoText, GameContainer gc) throws SlickException, IOException, FontFormatException 
	{
		super(img, infoText, gc);
	}
}
