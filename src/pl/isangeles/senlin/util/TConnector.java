/*
 * TConnector.java
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
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Module;
import pl.isangeles.senlin.data.DialoguesBase;
/**
 * Static class giving access to external text files
 * @author Isangeles
 *
 */
public final class TConnector 
{
	/**
	 * Private constructor to prevent initialization 
	 */
	private TConnector(){}
	/**
     * Static method which searches specified text file and return string with specific id
     * construction of text file: [text id]:[text];[new line mark]
     * @param filePath Path to text file
     * @param textID Id of text line
     * @return String with text or error message 
     */
	public static String getTextFromFile(String filePath, String textID)
	{
		File textFile = new File(filePath);
		try 
		{
			Scanner scann;
			scann = new Scanner(textFile);
			scann.useDelimiter(";\r?\n");
			String text;
			while((text = scann.findInLine(textID+".*[^;]")) == null)
			{
			    scann.nextLine();
			}
			scann.close();
			
			scann = new Scanner(text);
			scann.useDelimiter(":");
			String textId = scann.next();
			String textForGame = scann.next();
			scann.close();
			return textForGame;
		} 
		catch (FileNotFoundException e) 
		{
			return "Text file not fonud: " + filePath;
		}
		catch (NoSuchElementException e)
		{
			return "Text not found: " + textID;
		}
	}
	/**
     * Static method which searches specified text file in current lang directory and return string with specific id
     * construction of text file: [text id]:[text];[new line mark]
     * @param fileName Name of the file in current lang directory
     * @param textID Id of text line
     * @return String with text or error message 
     */
	public static String getText(String fileName ,String textID)
	{
		if(!fileName.endsWith(".lang"))
	    {
	    	fileName += ".lang";
	    }
		String fullPath = "data" + File.separator + "lang" + File.separator + Settings.getLang() + File.separator + fileName;
		return getTextFromFile(fullPath, textID);
	}
	/**
     * Returns text with for specified ID from specified file in current module lang directory (data/modules/[currentModuleName]/lang/[currentLangName]/[fileName])
     * construction of text file: [text id]:[text];[new line mark]
     * @param fileName Name of the file in current lang directory
     * @param textID Id of text line
     * @return String with text or error message 
     */
	public static String getTextFromModule(String fileName ,String textID)
	{
		if(!fileName.endsWith(".lang"))
	    {
	    	fileName += ".lang";
	    }
		String fullPath = Module.getGlobalLangPath() + File.separator + fileName;
		return getTextFromFile(fullPath, textID);
	}
	/**
     * Returns text with for specified ID from specified file in lang directory of active chapter in current module directory 
     * (data/modules/[current module name]/chapers/[current chapter name]/lang/[current lang name]/[file name])
     * construction of text file: [text id]:[text];[new line mark]
     * @param fileName Name of the file in current lang directory
     * @param textID Id of text line
     * @return String with text or error message 
     */
	public static String getTextFromChapter(String fileName ,String textID)
	{
		if(!fileName.endsWith(".lang"))
	    {
	    	fileName += ".lang";
	    }
		String fullPath = Module.getLangPath() + File.separator + fileName;
		return getTextFromFile(fullPath, textID);
	}
	/**
	 * Returns table with name[0] and info[1] for specified ID form specified lang file in data/lang directory
	 * @param langFile Some file in current lang directory
	 * @param id ID of line in specified file
	 * @return Table with name[0] and info[1]
	 */
	public static String[] getInfo(String langFile, String id)
	{
		String[] nameAndInfo = new String[]{"", ""};
		try
		{
			nameAndInfo[0] = getText(langFile, id).split(";")[0];
			nameAndInfo[1] = getText(langFile, id).split(";")[1];
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			Log.addSystem("tc_get_info_fail_msg///No name or info in " + langFile + " for: " + id);
			e.printStackTrace();
		}
		return nameAndInfo;
	}
	/**
	 * Returns table with name[0] and info[1] for specified ID from specified file
	 * @param filePath Path to lang file
	 * @param id ID of line in specified file
	 * @return Table with name[0] and info[1]
	 */
	public static String[] getInfoFromFile(String filePath, String id)
	{
		String[] nameAndInfo = new String[]{"", ""};
		try
		{
			nameAndInfo[0] = getTextFromFile(filePath, id).split(";")[0];
			nameAndInfo[1] = getTextFromFile(filePath, id).split(";")[1];
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			Log.addSystem("tc_get_info_fail_msg///No name or info in " + filePath + " for: " + id);
			e.printStackTrace();
		}
		return nameAndInfo;
	}

	/**
	 * Returns table with name[0] and info[1] for specified ID from lang file in current module directory
	 * @param langFile Some file in current lang directory
	 * @param id ID of line in specified file
	 * @return Table with name[0] and info[1]
	 */
	public static String[] getInfoFromModule(String langFile, String id)
	{
		if(!langFile.endsWith(".lang"))
	    {
	    	langFile += ".lang";
	    }
		String[] nameAndInfo = new String[]{"", ""};
		try
		{
			nameAndInfo[0] = getTextFromFile(Module.getGlobalLangPath() + File.separator + langFile, id).split(";")[0];
			nameAndInfo[1] = getTextFromFile(Module.getGlobalLangPath() + File.separator + langFile, id).split(";")[1];
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			Log.addSystem("tc_get_info_fail_msg///No name or info in " + langFile + " for: " + id);
		}
		return nameAndInfo;
	}
	/**
	 * Returns table with name[0] and info[1] for specified ID from lang file in active chapter directory of current module
	 * @param langFile Some file in current lang directory
	 * @param id ID of line in specified file
	 * @return Table with name[0] and info[1]
	 */
	public static String[] getInfoFromChapter(String langFile, String id)
	{
		if(!langFile.endsWith(".lang"))
	    {
	    	langFile += ".lang";
	    }
		String[] nameAndInfo = new String[]{"", ""};
		try
		{
			nameAndInfo[0] = getTextFromFile(Module.getLangPath() + File.separator + langFile, id).split(";")[0];
			nameAndInfo[1] = getTextFromFile(Module.getLangPath() + File.separator + langFile, id).split(";")[1];
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			Log.addSystem("tc_get_info_fail_msg///No name or info in " + langFile + " for: " + id);
		}
		return nameAndInfo;
	}
	/**
	 * Returns random text from "random" file in data/lang directory
	 * @param category One of categories in random file
	 * @return Random text from specified category in data/lang/[current language]/random file
	 */
	public static String getRanomText(String category)
	{
	    Random roll = new Random();
	    File randomTextFile = new File("data" + File.separator + "lang" + File.separator + Settings.getLang() + File.separator + "random.lang");
	    try
	    {
	        Scanner scann = new Scanner(randomTextFile);
	        scann.useDelimiter(";\r?\n");
	        String line;
	        while((line = scann.findInLine(category+".*[^;\r?\n]")) == null)
            {
                scann.nextLine();
            }
            scann.close();
            
            String[] textTab = line.split(":")[1].split(";");
            return textTab[roll.nextInt(textTab.length-1)];
	    }
	    catch(FileNotFoundException e)
	    {
	        Log.addSystem("tconnector: msg//" + "Random text file not found in: " + randomTextFile);
	        return "...";
	    }
	    catch(NoSuchElementException e)
	    {
	        Log.addSystem("tconnector: msg//" + "No such text in random file!");
            return "...";
	    }
	}
	/**
	 * Returns dialogue text from current dialogues base
	 * @param id Id of text in lang file
	 * @return String with ID-corresponding text for current language 
	 */
	public static String getDialogueText(String id)
	{
		return getTextFromFile(Module.getLangPath() + File.separator + "dialogues.lang", id);
	}
	/**
	 * Returns setting with specified ID from settings file
	 * @param settingId Setting ID
	 * @return String with setting
	 * @throws FileNotFoundException
	 * @throws NoSuchElementException
	 */
	public static String getSetting(String settingId) throws FileNotFoundException, NoSuchElementException
	{
	    File textFile = new File("settings.txt");
	    
	    Scanner scann;
        scann = new Scanner(textFile);
        scann.useDelimiter(";\r?\n");
        String text;
        while((text = scann.findInLine(settingId+".*[^;]")) == null)
        {
            scann.nextLine();
        }
        scann.close();
        
        scann = new Scanner(text);
        scann.useDelimiter(":");
        String textId = scann.next();
        String textForGame = scann.next();
        scann.close();
        return textForGame;
	}
}
