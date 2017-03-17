package pl.isangeles.senlin.inter.ui;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.inter.InterfaceTile;
/**
 * Class for graphical representation of items in ui
 * @author Isangeles
 *
 */
public class ItemTile extends InterfaceTile 
{
	public ItemTile(InputStream fileInput, String ref, boolean flipped, GameContainer gc, String info)
			throws SlickException, IOException, FontFormatException 
	{
		super(fileInput, ref, flipped, gc, info);
	}

}
