package pl.isangeles.senlin.data;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Item;
import pl.isangeles.senlin.core.ErrorItem;
import pl.isangeles.senlin.core.Weapon;
import pl.isangeles.senlin.util.DConnector;
/**
 * Class with containers with all items in game
 * @author Isangeles
 *
 */
public class ItemBase 
{
	//public static List<Weapon> weapons; //UNUSED
	public static Map<String, String> weaponsMap;
	private static GameContainer gc;
	/**
	 * Private constructor to prevent initialization
	 */
	private ItemBase(){}
	/**
	 * Returns new copy of item with specific id from base
	 * @param id Item id
	 * @return Copy of item from base (by clone method)
	 */
	public static Item getItem(String id)
	{
		/* TEST CODE
	 	for(Item item : weapons)
		{
			if(item.getId() == id)
				return item;
		}
		 */
		try
		{
			if(weaponsMap.get(id) != null)
			{
				return (Weapon)DConnector.getWeaponFromLine(weaponsMap.get(id), gc);
			}
			
			return new ErrorItem(id, "errorItem", "Item not found", gc);
		}
		catch(SlickException | IOException | FontFormatException e)
		{
			System.err.println(e.getMessage());
			return null;
		}
		
	}
	/**
	 * Loads text files with items to game maps
	 * @param gc Slick game container for getItem method
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public static void loadBases(GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		ItemBase.gc = gc;
		//weapons = DConnector.getWeaponBase("weaponBase", gc); //UNUSED
		weaponsMap = DConnector.getWeaponsLinesMap();
	}
}
