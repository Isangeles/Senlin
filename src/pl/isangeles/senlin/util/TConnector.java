package pl.isangeles.senlin.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
/**
 * Static class for connecting with external text files
 * @author Isangeles
 *
 */
public class TConnector 
{
    /**
     * Static method which searches specific text file in current lang directory and return string with specific id
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
			return "Text file not fonud!";
		}
		catch (NoSuchElementException e)
		{
			return "Text not found!";
		}
		
	}
}
