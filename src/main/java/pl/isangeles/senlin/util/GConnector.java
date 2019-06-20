/*
 * GConnector.java
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
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Module;
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
    	Image portrait = new Image("data" + File.separator + "modules" + File.separator + Module.getName() + File.separator + "portraits" + File.separator + fileName);
    	portrait.setName(fileName);
    	return portrait;
    }
    /**
     * Returns image from object/portrait dir in graphic archive
     * @param fileName Name of img file
     * @return New image
     * @throws SlickException
     * @throws IOException
     */
    public static Image getObjectPortrait(String fileName) throws SlickException, IOException
    {
        Image portrait = new Image(getInput("object/portrait/"+fileName), fileName, false);
        portrait.setName(fileName);
        return portrait;
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
    	
    	File portraitsBase = new File("data" + File.separator + "modules" + File.separator + Module.getName() + File.separator + "portraits" + File.separator + "portraits.txt");
    	Scanner nameScann = new Scanner(portraitsBase);
    	nameScann.useDelimiter(";\r?\n");
    	String imgName;
    	while(nameScann.hasNext())
    	{
    		imgName = nameScann.next();
    		imgList.add(new Image("data" + File.separator + "modules" + File.separator + Module.getName() + File.separator + "portraits" + File.separator + imgName));
    	}
    	nameScann.close();
    	return imgList;
    }
    
}
