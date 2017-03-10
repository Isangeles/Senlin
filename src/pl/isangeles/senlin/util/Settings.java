package pl.isangeles.senlin.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Settings
{
    private static String langId;
    private static float resWidth;
    private static float resHeight;
    
    public static void set() throws FileNotFoundException
    {
        File settingsFile = new File("settings.txt");
        Scanner scann = new Scanner(settingsFile);
        scann.useDelimiter(";\r?\n");
        
        langId = scann.next();
        String resString = scann.next();
        setRes(resString);
        scann.close();
    }
    
    public static String getLang()
    {
        return langId;
    }
    
    public static float[] getResolution()
    {
        return new float[]{resWidth, resHeight};
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
