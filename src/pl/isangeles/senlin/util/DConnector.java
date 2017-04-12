package pl.isangeles.senlin.util;

import java.awt.FontFormatException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Bonuses;
import pl.isangeles.senlin.core.item.Armor;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.item.Weapon;
import pl.isangeles.senlin.inter.ui.ItemTile;

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
	/**
	 * Build and returns item with the specified id 
	 * @param itemId Item id in base file
	 * @param gc Slick game container for item constructor
	 * @return New object of specific item from base file
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	private static Item getItem(String itemId, GameContainer gc) throws SlickException, IOException, FontFormatException
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
			item =  new Weapon(scann.next(), 
					scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(), new Bonuses(scann.nextInt(), scann.nextInt(), 
					scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextFloat()), scann.nextInt(), scann.next(), scann.next(), gc);
			scann.close();
			return item;
		}
		default:
		{
			return null;
		}
		}
	}
	/**
	 * Builds and returns weapon from line of text
	 * @param line String with text in this form: 
	 * [id]:[name]:[basic info]:[type(0-5)]:[material(0-2)]:[value]:[min damage]:[max damage]:[bonus str]:[bonus con]:[bonus dex]:[bonus int]:[bonus wis]:[bonus dmg]:[bonus haste]:[required level]:[img file name];
	 * @param gc Slick game container
	 * @return New weapon
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public static Weapon getWeaponFromLine(String line, GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		Scanner scann = new Scanner(line);
		scann.useDelimiter(":|;");
		Weapon item =  new Weapon(scann.next(),
				scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(), new Bonuses(scann.nextInt(), scann.nextInt(), 
				scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextFloat()), scann.nextInt(), scann.next(), scann.next(), gc);
		scann.close();
		return item;
	}
	/**
	 * Builds and returns armor from line of text
	 * @param line String with text in this form: 
	 * [id]:[name]:[basic info]:[type(0-5)]:[material(0-4)]:[value]:[armor rating]:[bonus str]:[bonus con]:[bonus dex]:[bonus int]:[bonus wis]:[bonus dmg]:[bonus haste]:[required level]:[img file name];
	 * @param gc Slick game container
	 * @return New armor
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public static Armor getArmorFromLine(String line, GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		Scanner scann = new Scanner(line);
		scann.useDelimiter(":|;");
		Armor item = new Armor(scann.next(),
				scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(),
				new Bonuses(scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextInt(), scann.nextFloat()), 
				scann.nextInt(), scann.next(), gc);
		scann.close();
		return item;
	}
	/**
	 * UNUSED Builds and returns list of all weapons in base file
	 * @param baseName name of base in data/item/ directory
	 * @param gc Slick game container for item constructor
	 * @return Linked list with items
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
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
	/**
	 * Returns map with lines from base file as values and lines item IDs as keys
	 * @return Map with item lines from base file
	 * @throws FileNotFoundException If file was not found
	 */
	public static Map<String, String> getItemsLinesMap(String itemBaseName) throws FileNotFoundException
	{
		Map<String, String> map = new HashMap<>();
		File baseFile = new File("data" + File.separator + "item" + File.separator + itemBaseName);
		Scanner scann = new Scanner(baseFile);
		scann.useDelimiter(";\r?\n");
		
		while(scann.hasNextLine())
		{
			String line = scann.nextLine();
			map.put(line.split(":|;")[0], line);
		}
		scann.close();
		return map;
	}
}
