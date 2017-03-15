package pl.isangeles.senlin.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import pl.isangeles.senlin.core.Bonuses;
import pl.isangeles.senlin.core.Item;
import pl.isangeles.senlin.core.Weapon;

/**
 * This class provides static methods giving access to game data like items, NPCs, etc; 
 * @author Isangeles
 *
 */
public final class DConnector 
{
	/**
	 * Private constructor to prevent initialization
	 */
	private DConnector(){}
	
	public static Item getItem(String id) throws FileNotFoundException
	{
		Item item;
		switch(id.toCharArray()[0])
		{
		case 'w':
		{
			File base = new File("data" + File.separator + "item" + File.separator + "weaponBase");
			Scanner scann = new Scanner(base);
			scann.useDelimiter(";\r?\n");
			String baseLine = scann.nextLine();
			scann.close();
			
			scann = new Scanner(baseLine);
			scann.useDelimiter(":|;");
			item =  new Weapon(scann.next(), TConnector.getText("textWeapons", scann.next()), TConnector.getText("textWeapons", scann.next()), 
					scann.nextInt(), scann.nextInt(), scann.nextInt(), new Bonuses(scann.nextInt(), scann.nextInt(), 
					scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextFloat()));
			scann.close();
			return item;
		}
		default:
		{
			return null;
		}
		}
	}
}
