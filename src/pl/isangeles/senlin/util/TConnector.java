package pl.isangeles.senlin.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TConnector 
{
	//TODO FIX THIS METHOD
	public static String getText(String fileName ,String textID)
	{
		String fullPath = "data" + File.separator + "lang" + File.separator + Settings.getLang() + File.separator + fileName;
		File textFile = new File(fullPath);
		try 
		{
			Scanner scann;
			scann = new Scanner(textFile);
			scann.useDelimiter(";\r?\n");
			String text = "";
			do
			{
				text = scann.findInLine(textID+".*[^;]");
			}while(scann.next() == null || text == "" || text == null);
				
			System.out.println(text);
			scann.close();
			
			scann = new Scanner(text);
			scann.useDelimiter(":");
			String trash = scann.next();
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
