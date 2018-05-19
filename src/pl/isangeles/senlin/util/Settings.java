/*
 * Settings.java
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
package pl.isangeles.senlin.util;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;
/**
 * Static class giving access to external settings file
 * @author Isangeles
 *
 */
public final class Settings
{
    private static String langId;
    private static String newLangId;
    private static Size resolution;
    private static Size newResolution;
    private static float scale;
    private static float effectsVol;
    private static float musicVol;
    private static String fowType;
    private static String mRenderType;
    private static String module;
    private static boolean hwCursor;
    private static boolean fullscreen;
    public static final String SCREENSHOTS_DIR = "screenshots";
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
        try
        {
            langId = TConnector.getSetting("language");
        } 
        catch (FileNotFoundException | NoSuchElementException e)
        {
            langId = "english";
        }
        try
        {
            setRes(TConnector.getSetting("resolution"));
        }
        catch (FileNotFoundException | NoSuchElementException e)
        {
            setRes("1980x1080");
        }
        try
        {
            effectsVol = Float.parseFloat(TConnector.getSetting("effectsVol"));
        } 
        catch (NumberFormatException | FileNotFoundException | NoSuchElementException e)
        {
            effectsVol = 1.0f;
        }
        try
        {
            musicVol = Float.parseFloat(TConnector.getSetting("musicVol"));
        } 
        catch (NumberFormatException | FileNotFoundException | NoSuchElementException e)
        {
            musicVol = 1.0f;
        }
        try
        {
            fowType = TConnector.getSetting("fogOfWar");
        } 
        catch (FileNotFoundException | NoSuchElementException e)
        {
            fowType = "off";
        }
        try
        {
        	mRenderType = TConnector.getSetting("mapRenderType");
        }
        catch(FileNotFoundException | NoSuchElementException e)
        {
        	mRenderType = "full";
        }
        try
        {
            module = TConnector.getSetting("module");
        }
        catch (FileNotFoundException | NoSuchElementException e)
        {
            module = "senlin";
            System.err.println("settings_module_param_not_found");
        }
        try
        {
            hwCursor = Boolean.parseBoolean(TConnector.getSetting("hwCursor"));
        }
        catch (FileNotFoundException | NoSuchElementException e)
        {
            hwCursor = true;
        }
        try
        {
            fullscreen = Boolean.parseBoolean(TConnector.getSetting("fullscreen"));
        }
        catch (FileNotFoundException | NoSuchElementException e)
        {
            fullscreen = false;
        }
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
        return resolution.toTable();
    }
    /**
     * Returns current system resolution
     * @return Resolution width and height
     */
    public static Size getSystemResolution()
    {
    	Dimension currentResolution = Toolkit.getDefaultToolkit().getScreenSize();
    	int width = (int)currentResolution.getWidth();
    	int height = (int)currentResolution.getHeight();
    	return new Size(width, height);
    }
    /**
     * Returns array string with all available resolutions    
     * @return String array with resolutions in format: [width]x[height];
     */
    public static String[] getResList()
    {
    	return new String[] {"1920x1080", "1600x800", "1280x720"};
    }
    /**
     * Returns array string with language options
     * @return String array
     */
    public static String[] getLangList()
    {
    	return new String[] {"english", "polish"};
    }
    /**
     * Returns array string with fog of war options
     * @return String array
     */
    public static String[] getFowTypes()
    {
    	return new String[] {"light", "full", "off"};
    }
    /**
     * Returns array with map rendering types
     * @return String array
     */
    public static String[] getMapRenderTypes()
    {
    	return new String[] {"full", "light"};
    }
    
    public static String[] getModulesNames()
    {
    	//TODO return list of modules in data/modules directory
    	return new String[] {"senlin", "arena"};
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
     * Returns audio effects volume level
     * @return Volume level
     */
    public static float getEffectsVol()
    {
        return effectsVol;
    }
    /**
     * Returns music volume level
     * @return Volume level
     */
    public static float getMusicVol()
    {
        return musicVol;
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
     * Returns current map rendering type
     * @return String with map rendering type name
     */
    public static String getMapRenderType()
    {
    	return mRenderType;
    }
    /**
     * Returns game module name
     * @return String with game module
     */
    public static String getModuleName()
    {
    	return module;
    }
    /**
     * Checks if hardware cursor is set
     * @return True if hardware cursor is set, false otherwise
     */
    public static boolean isHwCursor()
    {
    	return hwCursor;
    }
    /**
     * Checks if fullscreen mode is on
     * @return True if fullscreen mode is on, false otherwise
     */
    public static boolean isFullscreen()
    {
    	return fullscreen;
    }
    /**
     * Sets specified size as game resolution(needs game restart)
     * @param resolution Size with width and height
     */
    public static void setResolution(Size resolution)
    {
    	Settings.resolution = resolution;
    	newResolution = resolution; 
    	setScale();
    	System.out.println("engine_msg:Resolution set to:" + resolution);
    }
    /**
     * Sets specified language with specified ID as game language
     * @param langId String with lang ID
     */
    public static void setLang(String langId)
    {
        newLangId = langId;
    }
    
    public static void setModuleName(String moduleId)
    {
        module = moduleId;
    }
    /**
     * Sets type of fog of war
     * @param fowTypeId String with FOW type ID ('full', 'light' or 'off')
     */
    public static void setFowType(String fowTypeId)
    {
    	if(fowTypeId.equals("full") || fowTypeId.equals("light") || fowTypeId.equals("off"))
    		fowType = fowTypeId;
    }
    /**
     * Sets map rendering type
     * @param mRenderType String with desired map rendering type ID ('full' or 'light')
     */
    public static void setMapRenderType(String mRenderType)
    {
    	if(mRenderType.equals("full") || mRenderType.equals("light"))
    		Settings.mRenderType = mRenderType;
    }
    /**
     * Sets effects volume level
     * @param volLevel Desired volume level
     */
    public static void setEffectsVol(float volLevel)
    {
        effectsVol = volLevel;
    }
    /**
     * Sets music volume level
     * @param volLevel Desired volume level
     */
    public static void setMusicVol(float volLevel)
    {
        musicVol = volLevel;
    }
    /**
     * Enables or disables hardware cursor  
     * @param hwCursor True to enable hardware cursor, false to disable
     */
    public static void setHwCursor(boolean hwCursor)
    {
    	Settings.hwCursor = hwCursor;
    }
    /**
     * Saves current settings to settings file
     * @return True if settings was successfully saved, false otherwise
     */
    public static boolean saveSettings()
    {
        File settingsFile = new File("game.conf");
        try 
        {
            PrintWriter pw = new PrintWriter(settingsFile);
            
            if(newLangId != null || newLangId != "")
                pw.write("language:" + newLangId);
            else
                pw.write("language:" + langId);
            pw.write(";" + System.lineSeparator());
            
            if(newResolution != null)
                pw.write("resolution:" + newResolution);
            else
                pw.write("resolution:" + resolution);
            pw.write(";" + System.lineSeparator());
            
            pw.write("fogOfWar:" + fowType);
            pw.write(";" + System.lineSeparator());
            
            pw.write("mapRenderType:" + mRenderType);
            pw.write(";" + System.lineSeparator());
            
            pw.write("effectsVol:" + effectsVol);
            pw.write(";" + System.lineSeparator());
            
            pw.write("musicVol:" + musicVol);
            pw.write(";" + System.lineSeparator());
            
            pw.write("module:" + module);
            pw.write(";" + System.lineSeparator());
            
            pw.close();
            return true;
        } 
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
            return false;
        }
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
            resolution = new Size(Float.parseFloat(scann.next()), Float.parseFloat(scann.next()));
        }
        catch(NumberFormatException e)
        {
        	resolution = new Size(1920, 1080);
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
        float proportionX = resolution.width / defResWidth;
        float proportionY = resolution.height / defResHeight;
        scale = Math.round(Math.min(proportionX, proportionY) * 10f) / 10f;
    }
}
