/*
 * Script.java
 * 
 * Copyright 2017-2018 Dariusz Sikora <darek@pc-solus>
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

import java.util.LinkedList;
import java.util.List;

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
	private static final String TARGET_MACRO = "@target";
	private String name;
	private String body;
	private String[] commands;
	private List<String> ifOrCode;
	private String endBody;
	private String targetId;
	private int useCount;
	private int activeIndex;
	private long waitTime;
	private boolean end;
	/**
	 * Script constructor
	 * @param name Script name
	 * @param body Script code
	 */
	public Script(String name, String body, List<String> ifOrCode, String endBody)
	{
		this.name = name;
		this.body = body;
		this.endBody = endBody;
		this.ifOrCode = ifOrCode;
		
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
		//TODO return whole script body instead only body
		return body;//.replaceAll(TARGET_MACRO, targetId);
	}
	/**
	 * Returns code of if body
	 * @return String with if body code
	 */
	public List<String> getIfOrCode()
	{
		List<String> code = new LinkedList<>();
		for(String cmd : ifOrCode)
		{
			code.add(cmd.replaceAll(TARGET_MACRO, targetId));
		}
		return code;
	}
	/**
	 * Returns code of end body
	 * @return String with end body code
	 */
	public String getEndCode()
	{
		return endBody.replaceAll(TARGET_MACRO, targetId);
	}
	/**
	 * Returns active command of script
	 * @return String with script command
	 */
	public String getActiveCommand()
	{
		return commands[activeIndex].replaceAll(TARGET_MACRO, targetId);
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
	 * Checks if script has any next command
	 * @return True if script has any command to execute, false otherwise
	 */
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
	 * Sets script target
	 * @param target Targetable object ID
	 */
	public void setTarget(String targetId)
	{
		this.targetId = targetId;
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
