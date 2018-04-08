/*
 * WorldTime.java
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
package pl.isangeles.senlin.core;

import pl.isangeles.senlin.util.Stopwatch;

/**
 * Class for game world time
 * @author Isangeles
 *
 */
public class WorldTime 
{
	private long time; //time in millis
	/**
	 * World time constructor
	 */
	public WorldTime() {}
	/**
	 * World time constructor
	 * @param hours Numbers of hours 
	 * @param minutes Numbers of minutes
	 */
	public WorldTime(int hours, int minutes)
	{
		time += Stopwatch.min(hours);
		time += Stopwatch.sec(minutes);
	}
	/**
	 * World time constructor
	 * @param time Time in milliseconds
	 */
	public WorldTime(long time)
	{
		this.time = time;
	}
	/**
	 * Add hours
	 * @param hours Numbers of hours to add/subtract
	 */
	public void addHours(int hours)
	{
		if(hours > 0)
		{
			for(int i = 1; i <= hours; i ++)
			{
				time += 60000;
				if(Stopwatch.toMin(time) > 23)
					time = 0l;
			}
		}
		else
		{
			for(int i = 1; i <= -hours; i ++)
			{
				time -= 60000;
				if(Stopwatch.toMin(time) < 0)
					time = Stopwatch.min(23);
			}
		}
	}
	/**
	 * Adds minutes
	 * @param minutes Numbers of minutes to add/subtract
	 */
	public void addMinutes(int minutes)
	{
		if(minutes > 0)
		{
			for(int i = 0; i < minutes; i ++)
			{
				time += 1000;
				if(Stopwatch.toSec(time) > 1380)
					time = 0l;
			}
		}
		else
		{
			for(int i = 0; i < -minutes; i ++)
			{
				time -= 1000;
				if(Stopwatch.toSec(time) < 0)
					time = Stopwatch.min(23);
			}
		}
	}
	/**
	 * Returns time in milliseconds
	 * @return Time in milliseconds
	 */
	public long inMillis()
	{
		return time;
	}
	/**
	 * Returns time in hour:minute format
	 * @return String with time
	 */
	public String toHM()
	{
		String[] hms = Stopwatch.timeFromMillis(time).split(":");
		return hms[1] + ":" + hms[2];
	}
	
	@Override
	public String toString()
	{
		return toHM();
	}
	
	public boolean equals(WorldTime wt)
	{
		return time == wt.inMillis();
	}
}
