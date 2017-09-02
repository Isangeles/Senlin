/*
 * Exit.java
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
package pl.isangeles.senlin.data.area;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.data.ObjectsBase;
import pl.isangeles.senlin.graphic.Sprite;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for area exits
 * @author Isangeles
 *
 */
public class Exit 
{
	private String scenarioId;
	private String subAreaId;
	private Position pos;
	private boolean toSub;
	private Sprite exitArea;
	/**
	 * Area exit constructor 
	 * @param pos Position on area map
	 * @param exitToId ID of scenario to enter after using this exit
	 * @throws SlickException
	 * @throws IOException
	 */
	public Exit(Position pos, String exitToId) throws SlickException, IOException
	{
		String[] exitId = exitToId.split(":");
		if(exitId.length > 1)
		{
			toSub = true;
			exitToId = exitId[0];
			subAreaId = exitId[1];
		}
		else
		{
			toSub = false;
			scenarioId = exitToId;
			subAreaId = "";
		}
		this.pos = pos;
		this.exitArea = ObjectsBase.getExitTex();
	}
	/**
	 * Draws exit area texture
	 */
	public void draw()
	{
		exitArea.draw(pos.x, pos.y, true);
	}
	/**
	 * Returns ID of scenario to enter after using this exit
	 * @return String with scenario ID
	 */
	public String getScenarioId()
	{
		return scenarioId;
	}
	/**
	 * Returns ID of sub area
	 * @return String with sub area ID
	 */
	public String getSubAreaId()
	{
		return subAreaId;
	}
	/**
	 * Checks if mouse is over exit area texture
	 * @return True if mouse is over exit area texture, false otherwise
	 */
	public boolean isMouseOver()
	{
		return exitArea.isMouseOver();
	}
	/**
	 * Checks if this exit leads to sub area
	 * @return True if this exits leads to sub area, false otherwise
	 */
	public boolean isToSub()
	{
		return toSub;
	}
	/**
	 * Returns exit position on area map
	 * @return Exit position
	 */
	public Position getPos()
	{
		return pos;
	}
}
