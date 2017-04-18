package pl.isangeles.senlin.graphic;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Sprite extends GameObject 
{
	public Sprite(Image img) throws SlickException 
	{
		super(img);
	}
	
	public Sprite(Image img, String infoText, GameContainer gc) throws SlickException, IOException, FontFormatException 
	{
		super(img, infoText, gc);
	}
}
