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
    /**
     * Private constructor to prevent initialization
     */
    private Settings(){}
    /**
     * Tries to load settings from settings file, if file is not found default settings will be loaded 
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
			String defSettings = "english;" + System.lineSeparator() + "1920x1080;";
			scann = new Scanner(defSettings);
		}
        scann.useDelimiter(";\r?\n");
        
        langId = scann.next();
        String resString = scann.next();
        setRes(resString);
        scann.close();
    }
    /**
     * Get language ID
     * @return String with language ID
     */
    public static String getLang()
    {
        return langId;
    }
    /**
     * Get resolution
     * @return Table with width[0] and height[1]
     */
    public static float[] getResolution()
    {
        return new float[]{resWidth, resHeight};
    }
    /**
     * Sets resolution from provided string
     * @param resString String with resolution ([width]x[height];)
     */
    /**
     * Returns string with all available resolutions    
     * @return String with resolutions in format: [width]x[height];
     */
    public static String getResList()
    {
    	return "1920x1080;1600x800;1280x720";
    }
    
    private static void setRes(String resString)
    {
        Scanner scann = new Scanner(resString);
        scann.useDelimiter("x|;");
        resWidth = Float.parseFloat(scann.next());
        resHeight = Float.parseFloat(scann.next());
        scann.close();
    }
}
