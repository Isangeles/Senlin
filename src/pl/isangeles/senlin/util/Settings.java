/*
 * Settings.java
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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * Static class giving access to external settings file
 * @author Isangeles
 *
 */
public class Settings
{
    private static String langId;
    private static float resWidth;
    private static float resHeight;
    private static float scale;
    private static String fowType;
    /**
     * Private constructor to prevent initialization
     */
    private Settings(){}
    /**
     * Tries to load settings from settings file, if file was not found default settings will be loaded 
     * settings file construction: [setting];[newline mark]
     */
    static
    {
        File settingsFile = new File("settings.txt");
        Scanner scann;
		try 
		{
			scann = new Scanner(settingsFile);
		} 
		catch (FileNotFoundException e) 
		{
			String defSettings = "english;" + System.lineSeparator() + "1920x1080;" + System.lineSeparator() + "lightFOW";
			scann = new Scanner(defSettings);
		}
        scann.useDelimiter(";\r?\n");
        
        langId = scann.next();
        setRes(scann.next());
        fowType = scann.next();
        scann.close();
        setScale();
    }
    /**
     * Returns language ID
     * @return String with language ID
     */
    public static String getLang()
    {
        return langId;
    }
    /**
     * Returns resolution width and height
     * @return Table with width[0] and height[1]
     */
    public static float[] getResolution()
    {
        return new float[]{resWidth, resHeight};
    }
    /**
     * Returns string with all available resolutions    
     * @return String with resolutions in format: [width]x[height];
     */
    public static String getResList()
    {
    	return "1920x1080;1600x800;1280x720";
    }
    /**
     * Returns string with language options
     * @return String with languages options delimited by semicolons(;)
     */
    public static String getLangList()
    {
    	return "english;polish";
    }
    /**
     * Returns string with fog of war options
     * @return String with FOW options delimited by semicolons(;)
     */
    public static String getFowTypes()
    {
    	return "light FOW;full FOW;OFF";
    }
    /**
     * Returns scale for current resolution
     * @return Float scale value
     */
    public static float getScale()
    {
    	return scale;
    }
    /**
     * Returns current fog of war type
     * @return String with current FOW type name
     */
    public static String getFowType()
    {
    	return fowType;
    }
    /**
     * Sets resolution from provided string
     * @param resString String with resolution ([width]x[height];)
     */
    private static void setRes(String resString)
    {
    	Scanner scann = new Scanner(resString);
        scann.useDelimiter("x|;");
        try
        {
            resWidth = Float.parseFloat(scann.next());
            resHeight = Float.parseFloat(scann.next());
        }
        catch(NumberFormatException e)
        {
        	resWidth = 1920;
        	resHeight = 1080;
        }
        finally
        {
        	scann.close();
        }
    }
    /**
     * Sets scale for current resolution
     */
    private static void setScale()
    {
    	float defResWidth = 1920;
        float defResHeight = 1080;
        float proportionX = resWidth / defResWidth;
        float proportionY = resHeight / defResHeight;
        scale = Math.round(Math.min(proportionX, proportionY) * 10f) / 10f;
    }
}
