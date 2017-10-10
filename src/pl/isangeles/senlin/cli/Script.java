/*
 * Script.java
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
package pl.isangeles.senlin.cli;

import java.util.List;

/**
 * Class for CLI scripts
 * @author Isangeles
 *
 */
public class Script 
{
	private String name;
	private String body;
	private String ifBody;
	private String endBody;
	private int useCount;
	private int activeIndex = 1;
	private long waitTime;
	private boolean end;
	/**
	 * Script constructor
	 * @param name Script name
	 * @param body Script code
	 */
	public Script(String name, String body, String ifBody, String endBody)
	{
		this.name = name;
		this.body = body;
		this.endBody = endBody;
		this.ifBody = ifBody;
	}
	/**
	 * Updates script
	 * @param delta Time from last update
	 */
	public void update(int delta)
	{
		Log.addSystem(name + " waiting");
		if(waitTime > 0)
			waitTime -= delta;
		if(waitTime < 0)
			waitTime = 0;
	}
	/**
	 * Return script name 
	 * @return String with script name
	 */
	public String getName()
	{
		return name;
	}
	
	@Override
	public String toString()
	{
		return body;
	}
	/**
	 * Returns code of if body
	 * @return String with if body code
	 */
	public String getIfCode()
	{
		return ifBody;
	}
	/**
	 * Returns code of end body
	 * @return String with end body code
	 */
	public String getEndCode()
	{
		return endBody;
	}
	/**
	 * Returns active script command index
	 * @return Active command index
	 */
	public int getActiveIndex()
	{
		return activeIndex;
	}
	/**
	 * Returns number of uses of this script
	 * @return Number of uses
	 */
	public int getUseCount()
	{
		return useCount;
	}
	/**
	 * Checks if script is finished
	 * @return True if script should not be executed, false otherwise
	 */
	public boolean isFinished()
	{
		return end;
	}
	/**
	 * Checks if script is stopped for some time 
	 * @return True if script must wait, false otherwise
	 */
	public boolean isWaiting()
	{
		return waitTime > 0;
	}
	/**
	 * Increases number of uses of this script
	 */
	public void used()
	{
		useCount ++;
	}
	/**
	 * Moves command index forward
	 */
	public void next()
	{
		activeIndex ++;
	}
	/**
	 * Pauses script for specified time
	 * @param millis Time in millis
	 */
	public void pause(long millis)
	{
		waitTime = millis;
	}
	/**
	 * Marks script as finished
	 */
	public void finish()
	{
		Log.addSystem(name + " finished");
		activeIndex = 1;
		end = true;
	}
}
