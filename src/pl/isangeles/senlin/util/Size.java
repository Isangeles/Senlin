/*
 * Size.java
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
package pl.isangeles.senlin.util;

/**
 * Tuple for width/height size
 * @author Isangeles
 *
 */
public class Size 
{
	public float width;
	public float height;
	
	/**
	 * Default size constructor
	 */
	public Size()
	{
		this.width = 0;
		this.height = 0;
	}
	
	public Size(int width, int height) 
	{
		this.width = width;
		this.height = height;
	}
	
	public Size(float width, float height)
	{
		this.width = width;
		this.height = height;
	}
	
	public Size(int[] widthWeight)
	{
		this.width = widthWeight[0];
		this.height = widthWeight[1];
	}
	/**
	 * Constructs size from string in this form: [width];[height]
	 * @param xSemicolonY String in this form: [width];[height]
	 * @throws NumberFormatException
	 */
	public Size(String wSemicolonH) throws NumberFormatException
	{
		this.width = Integer.parseInt(wSemicolonH.split(";")[0]);
		this.height = Integer.parseInt(wSemicolonH.split(";")[1]);
	}
	
	@Override
	public String toString()
	{
		return width + ";" + height;
	}
}
