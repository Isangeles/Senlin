package pl.isangeles.senlin.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class GConnector
{
    public static InputStream getInput(String filePath) throws IOException
    {
    	String pathString = new String("graphic/" + filePath);
    	ZipFile gArch = new ZipFile("data" + File.separator + "gData");
        ZipEntry gFile = gArch.getEntry(pathString);
        return gArch.getInputStream(gFile);
    }
    
    public static List <Image> getPortraits() throws FileNotFoundException, SlickException
    {
    	List<Image> imgList = new ArrayList<>();
    	
    	File portraitsBase = new File("data" + File.separator + "portrait" + File.separator + "portraits.txt");
    	Scanner nameScann = new Scanner(portraitsBase);
    	nameScann.useDelimiter(";\r?\n");
    	String imgName;
    	while(nameScann.hasNext())
    	{
    		imgName = nameScann.next();
    		imgList.add(new Image("data" + File.separator + "portrait" + File.separator + imgName));
    	}
    	return imgList;
    }
    
}
