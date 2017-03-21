package pl.isangeles.senlin.core;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
/**
 * Class for equippable items
 * @author Isangeles
 *
 */
public abstract class Equippable extends Item 
{
	int reqLevel;
	Bonuses bonuses;
	
	public Equippable(String id, String name, String info, int value, String imgName, GameContainer gc, int reqLevel, Bonuses bonuses)
			throws SlickException, IOException, FontFormatException
	{
		super(id, name, info, value, imgName, gc);
		this.reqLevel = reqLevel;
		this.bonuses = bonuses;
	}

}
