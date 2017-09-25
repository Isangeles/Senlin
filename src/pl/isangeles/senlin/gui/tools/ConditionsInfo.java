/*
 * ConditionsInfo.java
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

import java.awt.Font;
import java.io.FileNotFoundException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.data.GBase;
import pl.isangeles.senlin.gui.InterfaceObject;
import pl.isangeles.senlin.states.GameWorld;

/**
 * Class for UI day phase and weather info field 
 * @author Isangeles
 *
 */
public class ConditionsInfo extends InterfaceObject implements UiElement 
{
	private GameWorld world;
	private String conInfo;
	private TrueTypeFont ttf;
	
	public ConditionsInfo(GameContainer gc, GameWorld world) throws FileNotFoundException
	{
		super(GBase.getImage("uiCurvedBg"), gc);
		this.world = world;
		conInfo = " ";
		
		Font font = GBase.getFont("mainUiFont");
		ttf = new TrueTypeFont(font.deriveFont(getSize(10f)), true);
	}
	@Override
	public void draw(float x, float y)
	{
		super.draw(x, y, false);
		super.drawString(conInfo, ttf);
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.elements.UiElement#close()
	 */
	@Override
	public void close() 
	{
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.elements.UiElement#update()
	 */
	@Override
	public void update() 
	{
		conInfo = world.getDay().getTime() + ", " + world.getDay().getPhase() + ", " + world.getDay().getWeather();
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.elements.UiElement#reset()
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
		return true;
	}

}
