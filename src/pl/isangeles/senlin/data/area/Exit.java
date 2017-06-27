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
 * Class for area exit
 * @author Isangeles
 *
 */
public class Exit 
{
	private final String exitToId;
	private final Position pos;
	private Sprite exitArea;
	
	public Exit(Position pos, String exitToId) throws SlickException, IOException
	{
		this.exitToId = exitToId;
		this.pos = pos;
		this.exitArea = ObjectsBase.getExitTex();
	}
	
	public void draw()
	{
		exitArea.draw(pos.x, pos.y, true);
	}
	
	public String exitTo()
	{
		return exitToId;
	}
	
	public boolean isMouseOver()
	{
		return exitArea.isMouseOver();
	}
	
	public Position getPos()
	{
		return pos;
	}
}
