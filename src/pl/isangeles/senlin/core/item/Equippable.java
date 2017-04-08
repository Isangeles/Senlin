package pl.isangeles.senlin.core.item;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Bonuses;
/**
 * Class for equippable items
 * @author Isangeles
 *
 */
public abstract class Equippable extends Item 
{
	protected int reqLevel;
	protected final int type;
	protected Bonuses bonuses;
	
	protected abstract String getTypeName();
	
	public Equippable(String id, String name, String info, int value, String imgName, GameContainer gc, int reqLevel, Bonuses bonuses, int type)
			throws SlickException, IOException, FontFormatException
	{
		super(id, name, info, value, imgName, gc);
		this.reqLevel = reqLevel;
		this.bonuses = bonuses;
		this.type = type;
	}
	/**
	 * Return item type
	 * @return Integer representing item type
	 */
	public int type()
	{
		return this.type;
	}

}
