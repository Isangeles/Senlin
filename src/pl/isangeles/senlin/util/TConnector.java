package pl.isangeles.senlin.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

import pl.isangeles.senlin.data.DialoguesBase;
import pl.isangeles.senlin.data.Log;
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
     * Static method which searches specified text file in current lang directory and return string with specific id
     * construction of text file: [text id]:[text];[new line mark]
     * @param fileName Name of the file in current lang directory
     * @param textID Id of text line
     * @return String with text or error message 
     */
	public static String getText(String fileName ,String textID)
	{
		String fullPath = "data" + File.separator + "lang" + File.separator + Settings.getLang() + File.separator + fileName;
		File textFile = new File(fullPath);
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
			return "Text file not fonud: " + fileName;
		}
		catch (NoSuchElementException e)
		{
			return "Text not found: " + textID;
		}
	}
	/**
	 * Returns table with name[0] and info[1] for specified lang file and ID
	 * @param langFile Some file in current lang directory
	 * @param id ID of line in specified file
	 * @return Table with name[0] and info[1]
	 */
	public static String[] getInfo(String langFile, String id)
	{
		return getText(langFile, id).split(";");
	}
	/**
	 * Returns random text from "random" file in data/lang directory
	 * @param category One of categories in random file
	 * @return Random text from specified category in data/lang/[current language]/random file
	 */
	public static String getRanomText(String category)
	{
	    Random roll = new Random();
	    File randomTextFile = new File("data" + File.separator + "lang" + File.separator + Settings.getLang() + File.separator + "random");
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
		return getText(DialoguesBase.getBaseName(), id);
	}
}
