package pl.isangeles.senlin.core;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
/**
 * Class for weapons
 * @author Isangeles
 *
 */
public class Weapon extends Item 
{
	int maxDamage;
	int minDamage;
	Bonuses bonuses;
	
	public Weapon(String id, String name, String info, int value, int maxDmg, int minDmg, Bonuses bonuses, String picName, GameContainer gc) throws SlickException, IOException, FontFormatException 
	{
		super(id, name, info, value, picName, gc);
	}

}
