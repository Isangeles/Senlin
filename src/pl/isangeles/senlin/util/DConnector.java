package pl.isangeles.senlin.util;

import java.awt.FontFormatException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Bonuses;
import pl.isangeles.senlin.core.Item;
import pl.isangeles.senlin.core.Weapon;
import pl.isangeles.senlin.inter.ui.ItemTile;

/**
 * This class provides static methods giving access to game data like items, NPCs, etc; 
 * TODO rewrite item access methods 
 * @author Isangeles
 *
 */
public final class DConnector 
{
	/**
	 * Private constructor to prevent initialization
	 */
	private DConnector(){}
	
	public static Item getItem(String itemId, GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		Item item;
		switch(itemId.toCharArray()[0])
		{
		case 'w':
		{
			File base = new File("data" + File.separator + "item" + File.separator + "weaponBase");
			Scanner scann = new Scanner(base);
			scann.useDelimiter(";\r?\n");
			String baseLine;
			while((baseLine = scann.findInLine(itemId+".*[^;]")) == null)
			{
			    scann.nextLine();
			}
			scann.close();
			
			scann = new Scanner(baseLine);
			scann.useDelimiter(":|;");
			item =  new Weapon(scann.next(), TConnector.getText("textWeapons", scann.next()), TConnector.getText("textWeapons", scann.next()), 
					scann.nextInt(), scann.nextInt(), scann.nextInt(), new Bonuses(scann.nextInt(), scann.nextInt(), 
					scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextFloat()), scann.nextInt(), scann.next(), gc);
			scann.close();
			return item;
		}
		default:
		{
			return null;
		}
		}
	}
	
	public static Item getWeaponFromLine(String line, GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		Scanner scann = new Scanner(line);
		scann.useDelimiter(":|;");
		Item item =  new Weapon(scann.next(), TConnector.getText("textWeapons", scann.next()), TConnector.getText("textWeapons", scann.next()), 
				scann.nextInt(), scann.nextInt(), scann.nextInt(), new Bonuses(scann.nextInt(), scann.nextInt(), 
				scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextFloat()), scann.nextInt(), scann.next(), gc);
		scann.close();
		return item;
	}
	
	public static List<Weapon> getWeaponBase(String baseName, GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		List<Weapon> itemList = new LinkedList<>();
		
		File baseFile = new File("data" + File.separator + "item" + File.separator + baseName);
		Scanner scann = new Scanner(baseFile);
		scann.useDelimiter(";\r?\n");
		
		while(scann.hasNextLine())
		{
			itemList.add((Weapon)getWeaponFromLine(scann.nextLine(), gc));
		}
		scann.close();
		
		return itemList;
		
	}
}
