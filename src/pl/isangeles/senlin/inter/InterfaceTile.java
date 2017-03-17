package pl.isangeles.senlin.inter;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
/**
 * Base class for graphical representations of skills, items, etc. in ui   
 * @author Isangeles
 *
 */
public abstract class InterfaceTile extends InterfaceObject 
{

	public InterfaceTile(InputStream fileInput, String ref, boolean flipped, GameContainer gc, String info)
			throws SlickException, IOException, FontFormatException 
	{
		super(fileInput, ref, flipped, gc, info);
	}
	
	public void draw(float x, float y)
	{
		super.draw(x, y, 45f, 40f);
	}
}
