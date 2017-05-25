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

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 * This class provides static methods giving access to graphic archive and other external graphics files
 * @author Isangeles
 *
 */
public final class GConnector
{
	/**
	 * Private Constructor to prevent initialization
	 */
	private GConnector(){}
    /**
     * Return InputStream pointed on specific file in graphic archive
     * WARNING If gArch will be closed in these method then returned InputStrem will be closed too
     * @param filePath Path to file in gData
     * @return InputStream pointed on specific file in graphic archive
     * @throws IOException If path is wrong
     */
    public static InputStream getInput(String filePath) throws IOException
    {
    	String pathString = new String("graphic/" + filePath);
    	ZipFile gArch = new ZipFile("data" + File.separator + "gData");
        ZipEntry gFile = gArch.getEntry(pathString);
        InputStream is = gArch.getInputStream(gFile);
        return is;
    }
    /**
     * Return image from portrait data dir
     * @param fileName Name of img file
     * @return New image
     * @throws SlickException
     */
    public static Image getPortrait(String fileName) throws SlickException
    {
    	return new Image("data" + File.separator + "portrait" + File.separator + fileName);
    }
    /**
     * This static method reads file with image names in portrait folder and adds these files to Image List
     * @return List with images
     * @throws FileNotFoundException
     * @throws SlickException
     */
    public static List<Image> getPortraits() throws FileNotFoundException, SlickException
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
    	nameScann.close();
    	return imgList;
    }
    
}
