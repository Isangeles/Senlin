package pl.isangeles.senlin.graphic;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 * Class for simple graphical game objects
 * @author Isangeles
 *
 */
public class Sprite extends GameObject 
{
	public Sprite(InputStream is, String ref, boolean flipped) throws SlickException
	{
		super(is, ref, flipped);
	}
	
	public Sprite(Image img) throws SlickException 
	{
		super(img);
	}
	
	public Sprite(Image img, String infoText, GameContainer gc) throws SlickException, IOException, FontFormatException 
	{
		super(img, infoText, gc);
	}
}
