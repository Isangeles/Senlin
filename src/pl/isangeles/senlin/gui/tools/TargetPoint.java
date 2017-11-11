/*
 * TargetPoint.java
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

import pl.isangeles.senlin.graphic.Sprite;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.character.Character;

/**
 * Class for GUI target point
 * @author Isangeles
 *
 */
class TargetPoint implements UiElement 
{
	private Character player;
	
	private Sprite hostileT;
	private Sprite neutralT;
	private Sprite friendlyT;
	private Sprite deadT;
	/**
	 * Target point constructor
	 * @param player Player character
	 * @param gc Slick game container
	 * @throws IOException 
	 * @throws SlickException 
	 */
	public TargetPoint(GameContainer gc, Character player) throws SlickException, IOException
	{
		this.player = player;
		
		hostileT = new Sprite(GConnector.getInput("sprite/hTarget.png"), "hTarget", false);
		neutralT = new Sprite(GConnector.getInput("sprite/nTarget.png"), "nTarget", false);
		friendlyT = new Sprite(GConnector.getInput("sprite/fTarget.png"), "fTarget", false);
		deadT = new Sprite(GConnector.getInput("sprite/dTarget.png"), "dTarget", false);
	}
	/**
	 * Draws target point
	 */
	public void draw() 
	{
		Targetable target = player.getTarget();
		if(target != null)
		{
			if(Character.class.isInstance(target))
			{
				Character charTarget = (Character)target;
				switch(charTarget.getAttitudeTo(player))
				{
				case FRIENDLY:
					friendlyT.draw(target.getPosition()[0], target.getPosition()[1], false);
					break;
				case NEUTRAL:
					neutralT.draw(target.getPosition()[0], target.getPosition()[1], false);
					break;
				case HOSTILE:
					hostileT.draw(target.getPosition()[0], target.getPosition()[1], false);
					break;
				case DEAD:
					deadT.draw(target.getPosition()[0], target.getPosition()[1], false);
					break;
				}
			}
			else 
				deadT.draw(target.getPosition()[0], target.getPosition()[1], false);
		}
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.tools.UiElement#draw(float, float)
	 */
	@Override
	public void draw(float x, float y) 
	{
		draw();
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.tools.UiElement#close()
	 */
	@Override
	public void close() 
	{
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.tools.UiElement#update()
	 */
	@Override
	public void update() 
	{
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.tools.UiElement#reset()
	 */
	@Override
	public void reset() 
	{
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.tools.UiElement#isOpenReq()
	 */
	@Override
	public boolean isOpenReq() 
	{
		return player.getTarget() != null;
	}
}
