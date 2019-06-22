/*
 * UiLayout.java
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
package pl.isangeles.senlin.gui;

import java.util.Map;

import pl.isangeles.senlin.util.Position;

/**
 * Class for saved UI layout (for save game)
 * @author Isangeles
 *
 */
public class UiLayout 
{
	private final Map<String, Integer> bBarLayout;
	private final Map<String, Integer[]> invLayout;
	private final Position cameraPos;
	/**
	 * UiLayout constructor
	 * @param bBarLayout Layout of bottom bar slots, skills IDs as keys and slots IDs as values
	 * @param invLayout Layout of inventory menu slots, items IDs as keys and slots IDs as values
	 * @param cameraPos Camera position, [0] - position on X axis and [1] - position on Y axis
	 */
	public UiLayout(Map<String, Integer> bBarLayout, Map<String, Integer[]> invLayout, float[] cameraPos)
	{
		this.bBarLayout = bBarLayout;
		this.invLayout = invLayout;
		this.cameraPos = new Position();
		this.cameraPos.x = (int)cameraPos[0];
		this.cameraPos.y = (int)cameraPos[1];
	}
	/**
	 * Returns bottom bar layout
	 * @return Map with skills IDs as keys and slots IDs as values
	 */
	public Map<String, Integer>getBarLayout()
	{
		return bBarLayout;
	}
	/**
	 * Returns inventory menu layout
	 * @return Map with items IDs as keys and slots IDs as values
	 */
	public Map<String, Integer[]> getInvLayouyt()
	{
		return invLayout;
	}
	/**
	 * Returns camera position
	 * @return Table with osition on X axis[0] and position on Y axis[1]
	 */
	public Position getCameraPos()
	{
		return cameraPos;
	}
}
