package pl.isangeles.senlin.inter.ui;

import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.inter.InterfaceTile;
/**
 * Class for graphical representation of skills in ui
 * @author Isangeles
 *
 */
public class SkillTile extends InterfaceTile 
{
	public SkillTile(InputStream fileInput, String ref, boolean flipped, GameContainer gc, String textForInfo)
			throws SlickException, IOException, FontFormatException 
	{
		super(fileInput, ref, flipped, gc, textForInfo);
	}

	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) 
	{
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) 
	{
	}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) 
	{
	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) 
	{
	}

	@Override
	public void mouseWheelMoved(int arg0) 
	{
	}

	@Override
	public void inputEnded() 
	{
	}

	@Override
	public void inputStarted() 
	{
	}

	@Override
	public boolean isAcceptingInput() 
	{
		return true;
	}

	@Override
	public void setInput(Input arg0) 
	{
	}

}
