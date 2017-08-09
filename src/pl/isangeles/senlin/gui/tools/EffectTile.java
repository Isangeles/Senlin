package pl.isangeles.senlin.gui.tools;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.gui.InterfaceTile;

public class EffectTile extends InterfaceTile 
{

	public EffectTile(InputStream fileInput, String ref, boolean flipped, GameContainer gc, String info)
			throws SlickException, IOException, FontFormatException 
	{
		super(fileInput, ref, flipped, gc, info);
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) 
	{
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) 
	{
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) 
	{
	}
	
	@Override
	public void mousePressed(int button, int x, int y)
	{
		
	}

	@Override
	public void mouseReleased(int button, int x, int y) 
	{
	}

	@Override
	public void mouseWheelMoved(int change) 
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
		return false;
	}

	@Override
	public void setInput(Input input) 
	{
		
	}

}
