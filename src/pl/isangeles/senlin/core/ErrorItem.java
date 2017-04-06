package pl.isangeles.senlin.core;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
/**
 * Class for error items
 * @author Isangeles
 *
 */
public class ErrorItem extends Misc 
{

	public ErrorItem(String id, String name, String info, GameContainer gc)throws SlickException, IOException, FontFormatException 
	{
		super("error" + id, name, info, 0, "unknown.png", gc);
	}

}
