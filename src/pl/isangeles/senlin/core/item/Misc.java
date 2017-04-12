package pl.isangeles.senlin.core.item;

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

	public Misc(String id, int value, String imgName, GameContainer gc)
			throws SlickException, IOException, FontFormatException 
	{
		super(id, value, imgName, gc);
	}
    /**
     * Returns basic info about item for item tile
     * @return String with basic info
     */
    protected String getInfo()
    {
    	String fullInfo = name + System.lineSeparator() + info + System.lineSeparator() + "Value: " + value;
    	return fullInfo;
    }
}
