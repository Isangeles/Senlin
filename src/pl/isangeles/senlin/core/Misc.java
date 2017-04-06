package pl.isangeles.senlin.core;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
/**
 * Class for miscellaneous items
 * @author Isangeles
 *
 */
public class Misc extends Item 
{

	public Misc(String id, String name, String info, int value, String imgName, GameContainer gc)
			throws SlickException, IOException, FontFormatException 
	{
		super(id, name, info, value, imgName, gc);
	}

}
