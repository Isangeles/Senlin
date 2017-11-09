/*
 * DestinationPoint.java
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
package pl.isangeles.senlin.gui.tools;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.gui.InterfaceObject;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.Position;

/**
 * Class for player destination point GUI mark
 * @author Isangeles
 *
 */
class DestinationPoint extends InterfaceObject implements UiElement 
{
	private Character player;
	private Position pos;
	/**
	 * Destination point constructor
	 * @param gc Slick game container
	 * @param player Player character
	 * @throws SlickException 
	 * @throws IOException
	 */
	public DestinationPoint(GameContainer gc, Character player) throws SlickException, IOException
	{
		super(GConnector.getInput("sprite/nTarget.png"), "uiDestPoint", false, gc);
		this.player = player;
	}
	
	@Override
	public void draw()
	{
	    //super.draw(Global.uiX(player.getDestPoint().x), Global.uiY(player.getDestPoint().y), false);
	    super.draw(Global.uiX(pos.x), Global.uiY(pos.y), false);
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.elements.UiElement#update()
	 */
	@Override
	public void update() 
	{
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.elements.UiElement#reset()
	 */
	@Override
	public void reset() 
	{
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.elements.UiElement#close()
	 */
	@Override
	public void close() 
	{
	}
	/**
	 * Sets specified position as point position
	 * @param pos XY position
	 */
	public void setPosition(Position pos)
	{
	    this.pos = pos;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.tools.UiElement#isOpenReq()
	 */
	@Override
	public boolean isOpenReq() 
	{
		Position pointPos = player.getDestPoint();
		if(!new Position(player.getPosition()).equals(pointPos) && player.isMove())
		    return true;
		else
		    return false;
	}

}
