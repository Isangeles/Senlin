/*
 * Weather.java
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
package pl.isangeles.senlin.core.day;

import java.io.IOException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.util.AConnector;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.Settings;
import pl.isangeles.senlin.util.TConnector;
/**
 * Class for game world weather(graphical and sound effects)
 * @author Isangeles
 *
 */
class Weather implements SaveElement
{
	private Animation rainAnim;
	private Sound rainSound;
	private WeatherType type;
	private int timer;
	private int effectTime;
	/**
	 * Weather constructor
	 * @throws SlickException
	 * @throws IOException
	 */
	public Weather() throws SlickException, IOException 
	{
		Image[] rainFrames = new Image[4];
		rainFrames[0] = new Image(GConnector.getInput("day/rain/rain01.png"), "rain01", false);
		rainFrames[1] = new Image(GConnector.getInput("day/rain/rain02.png"), "rain02", false);
		rainFrames[2] = new Image(GConnector.getInput("day/rain/rain03.png"), "rain03", false);
		rainFrames[3] = new Image(GConnector.getInput("day/rain/rain04.png"), "rain04", false);
		int[] duration = {300, 300, 300, 300};
		
		rainAnim = new Animation(rainFrames, duration);
		rainSound = new Sound(AConnector.getInput("effects/rain01.ogg"), "rain01.ogg");
		type = WeatherType.SUN;
	}
	/**
	 * Draws current weather
	 * @param x 
	 * @param y
	 */
	public void draw(float x, float y)
	{
		if(type == WeatherType.RAIN)
			rainAnim.draw(x, y);
	}
	/**
	 * Updates weather 
	 * @param delta Time between updates
	 */
	public void update(int delta)
	{
		if(type == WeatherType.RAIN)
		{
			rainAnim.update(delta);
			timer += delta;
		}
		if(timer >= effectTime)
		{
		    rainSound.stop();
		    type = WeatherType.SUN;
		}
	}
	/**
	 * Starts raining animation and raining sound effect
	 * @param howLong Rain duration
	 */
	public void startRaining(int howLong)
	{
		type = WeatherType.RAIN;
		effectTime = howLong;
		timer = 0;
        rainSound.loop(1.0f, Settings.getEffectsVol());
	}
	/**
	 * Sets specified weather effect
	 * @param type Weather effect type
	 * @param timerState State of weather effect timer
	 * @param howLong Duration of weather effect
	 */
	public void setWeatherEffect(WeatherType type, int timerState, int howLong)
	{
		switch(type)
		{
		case RAIN:
			startRaining(howLong);
			timer = timerState;
			return;
		default:
			return;
		}
	}
	/**
	 * Checks if rain effect is active
	 * @return True if rain effect is active, false otherwise
	 */
	public boolean isRaining()
	{
		return type == WeatherType.RAIN;
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
		Element weatherE = doc.createElement("weather");
		weatherE.setAttribute("timer", timer+"");
		weatherE.setAttribute("to", effectTime+"");
		weatherE.setTextContent(type.getId());
		return weatherE;
	}

}
