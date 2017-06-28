/*
 * Portrait.java
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
package pl.isangeles.senlin.inter;

import java.io.File;
import java.io.FileNotFoundException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.util.Coords;
/**
 * Class for game objects portraits
 * @author Isangeles
 *
 */
public class Portrait extends InterfaceObject 
{
	public Portrait(String pathToTex, GameContainer gc) throws SlickException, FileNotFoundException 
	{
		super(pathToTex, (int)Coords.getSize(95), (int)Coords.getSize(130), gc);
	}
	
	public Portrait(Image image, GameContainer gc) throws FileNotFoundException
	{
		super(image, (int)Coords.getSize(95), (int)Coords.getSize(130), gc);
	}
}
