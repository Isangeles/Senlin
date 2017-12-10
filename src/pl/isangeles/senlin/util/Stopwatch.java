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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Static class for time management 
 * @author Isangles
 *
 */
public class Stopwatch
{
	private static long nanoTime;
	/**
	 * Private constructor to prevent initialization
	 */
    private Stopwatch() {}
    /**
     * Converts seconds to milliseconds
     * @param sec Time in seconds
     * @return Time in milliseconds
     */
    public static long sec(int sec)
    {
        return sec * 1000;
    }
    /**
     * Converts minutes to milliseconds
     * @param sec Time in minutes
     * @return Time in milliseconds
     */
    public static long min(int min)
    {
        return min * 60000;
    }
    /**
     * Converts time in milliseconds to minutes 
     * @param millis Milliseconds
     * @return Time in minutes
     */
    public static long toMin(long millis)
    {
    	long min = millis / 60000;
    	return min;
    }
    /**
     * Converts milliseconds to seconds
     * @param millis Milliseconds
     * @return Time in seconds
     */
    public static long toSec(long millis)
    {
    	long sec = millis / 1000;
    	return sec;
    }
    /**
     * Returns string with specified time in HMS format
     * @param timeInMillis Time in milliseconds
     * @return String with time converted to hh:mm:ss format
     */
    public static String timeFromMillis(long timeInMillis)
    {
    	Date date = new Date(timeInMillis);
    	SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    	String hms = format.format(date);
    	
    	return hms;
    }
    /**
     * Starts time measurement
     */
    public static void start()
    {
    	nanoTime = System.nanoTime();
    }
    /**
     * Stops and prints time measurement
     * @param name Measure name
     */
    public static void stopAndPrint(String name)
    {
    	long currentTime = System.nanoTime();
    	System.out.println(name + " time:" + TimeUnit.SECONDS.convert((currentTime - nanoTime), TimeUnit.NANOSECONDS));
    	nanoTime = 0L;
    }
}
