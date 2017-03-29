package pl.isangeles.senlin.inter;

import java.io.FileNotFoundException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Portrait extends InterfaceObject 
{

	public Portrait(String pathToTex, GameContainer gc) throws SlickException, FileNotFoundException 
	{
		super(pathToTex,gc);
	}
	
	public Portrait(Image image, GameContainer gc) throws FileNotFoundException
	{
		super(image, gc);
	}

}
