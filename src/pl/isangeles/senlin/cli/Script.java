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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.data.save.SaveElement;

/**
 * Class for CLI scripts
 * @author Isangeles
 *
 */
public class Script implements SaveElement
{
	private String name;
	private String body;
	private String[] commands;
	private String ifBody;
	private String endBody;
	private int useCount;
	private int activeIndex;
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
		
		commands = body.split(";|(;\\r?\\n)");
		for(String command : commands)
		{
			command = command.replaceFirst("^\\s*", "");
		}
	}
	/**
	 * Updates script
	 * @param delta Time from last update
	 */
	public void update(int delta)
	{
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
	 * Returns active command of script
	 * @return String with script command
	 */
	public String getActiveCommand()
	{
		return commands[activeIndex];
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
	
	public boolean hasNext()
	{
		return activeIndex < commands.length;
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
	 * Sets specified ID as active script line ID
	 * @param id ID
	 */
	public void setActiveLineId(int id)
	{
		activeIndex = id;
	}
	/**
	 * Increases number of uses of this script
	 */
	public void used()
	{
		useCount ++;
	}
	/**
	 * Restarts command index
	 * (next command will be first command of the script)
	 */
	public void restart()
	{
		activeIndex = 0;
	}
	/**
	 * Moves command index forward
	 */
	public void next()
	{
		if(hasNext())
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
		end = true;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.data.save.SaveElement#getSave(org.w3c.dom.Document)
	 */
	@Override
	public Element getSave(Document doc) 
	{
		Element scriptE = doc.createElement("script");
		scriptE.setTextContent(name);
		scriptE.setAttribute("wait", waitTime+"");
		scriptE.setAttribute("aID", activeIndex+"");
		return scriptE;
	}
}
