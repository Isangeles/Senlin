/*
 * DayPhase.java
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
package pl.isangeles.senlin.core.day;

import java.io.IOException;

import org.newdawn.slick.SlickException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.graphic.Sprite;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
/**
 * Class for day phases
 * @author Isangeles
 *
 */
class DayPhase implements SaveElement
{
	private Sprite morningFilter,
				   middayFilter,
				   afternoonFilter,
				   nightFilter;
	private PhaseType type;
	/**
	 * Day phase constructor
	 * @throws SlickException
	 * @throws IOException
	 */
	public DayPhase() throws SlickException, IOException 
	{
		middayFilter = new Sprite(GConnector.getInput("day/midday.png"), "dayFilterMidday", false);
		afternoonFilter = new Sprite(GConnector.getInput("day/afternoon.png"), "dayFilterAfternoon", false);
		nightFilter = new Sprite(GConnector.getInput("day/night.png"), "dayFilterNight", false);
		type = PhaseType.MORNING;
	}
	/**
	 * Draws day phase filter
	 * @param x Position on x-axis
	 * @param y Position on y-axis
	 */
	public void draw(float x, float y)
	{
		switch(type)
		{
		case MORNING:
			break;
		case MIDDAY:
			middayFilter.draw(x, y, false);
			break;
		case AFTERNOON:
			afternoonFilter.draw(x, y, false);
			break;
		case NIGHT:
			nightFilter.draw(x, y, false);
			break;
		}
	}
	/**
	 * Changes current day phase 
	 * @param type Day phase type
	 */
	public void changePhase(PhaseType type)
	{
		this.type = type;
	}
	/**
	 * Switches to next day phase
	 */
	public void nextPhase()
	{
		type = type.getNext();
	}
	@Override
	public String toString()
	{
		return type.getName();
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.data.save.SaveElement#getSave(org.w3c.dom.Document)
	 */
	@Override
	public Element getSave(Document doc) 
	{
		Element phaseE = doc.createElement("phase");
		phaseE.setTextContent(this.toString());
		return phaseE;
	}
}
