package pl.isangeles.senlin.inter;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
/**
 * Base class for graphical representations of skills, items, etc. in ui   
 * @author Isangeles
 *
 */
public abstract class InterfaceTile extends InterfaceObject implements MouseListener
{ 
	private static int counter; //TODO check if necessary
	private Input gameInput;
	private boolean dragged;
	
	public InterfaceTile(InputStream fileInput, String ref, boolean flipped, GameContainer gc, String info)
			throws SlickException, IOException, FontFormatException 
	{
		super(fileInput, ref+counter, flipped, gc, info);
		gameInput = gc.getInput();
		gameInput.addMouseListener(this);
		
		counter ++;
	}
	@Override
	public void draw(float x, float y, boolean scaledPos)
	{
		if(!dragged)
			super.draw(x, y, 45f, 40f, scaledPos);
		else
			super.draw(gameInput.getMouseX(), gameInput.getMouseY(), 45f, 40f, scaledPos);
	}
	@Override
	public void draw(float x, float y, Color filter, boolean scaledPos)
	{
		if(!dragged)
			super.draw(x, y, 45f, 40f, filter, scaledPos);
		else
			super.draw(gameInput.getMouseX(), gameInput.getMouseY(), 45f, 40f, filter, scaledPos);
	}
	
	public void dragged(boolean dragged)
	{
		this.dragged = dragged;
	}
	
	public boolean isDragged()
	{
		return dragged;
	}
	
	@Override
	public void mousePressed(int button, int x, int y)
	{
		if(button == Input.MOUSE_LEFT_BUTTON && super.isMouseOver() && !dragged)
			dragged = true;
	}
	
}
