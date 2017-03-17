package pl.isangeles.senlin.data;

import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Item;
import pl.isangeles.senlin.core.Weapon;
import pl.isangeles.senlin.util.DConnector;
/**
 * Class with containers with all items in game
 * @author Isangeles
 *
 */
public class ItemBase 
{
	List<Weapon> weapons;
	
	public ItemBase(GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		loadBases(gc);
	}
	
	public Item getItem(String id)
	{
		for(int i = 0; i < weapons.size(); i ++)
		{
			if(weapons.get(i).getId().equals(id))
				return weapons.get(i);
		}
		return null;
	}
	
	private void loadBases(GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		weapons = DConnector.getWeaponBase("weaponBase", gc);
	}
}
