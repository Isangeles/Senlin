/*
 * Stopwatch.java
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
package pl.isangeles.senlin.util;

import java.util.concurrent.TimeUnit;

/**
 * This class make slick time management easier 
 * @author Isangles
 *
 */
public class Stopwatch
{
	/**
	 * Private constructor to prevent initialization
	 */
    private Stopwatch() {}
    /**
     * Converts seconds to milliseconds
     * @param sec Time in seconds
     * @return Time in milliseconds
     */
    public static int sec(int sec)
    {
        return sec * 1000;
    }
    /**
     * Converts minutes to milliseconds
     * @param sec Time in minutes
     * @return Time in milliseconds
     */
    public static int min(int min)
    {
        return min * 10000;
    }
    
    public static String timeFromMillis(long timeInMillis)
    {
    	String hmsTime = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timeInMillis),
    								   TimeUnit.MILLISECONDS.toMinutes(timeInMillis) % TimeUnit.HOURS.toMinutes(1),
    								   TimeUnit.MILLISECONDS.toSeconds(timeInMillis) % TimeUnit.MINUTES.toSeconds(1));
    	return hmsTime;
    }
}
