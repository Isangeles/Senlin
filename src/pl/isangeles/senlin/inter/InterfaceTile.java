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
 * Base class for graphical representations of skills, items, etc. in UI   
 * @author Isangeles
 *
 */
public abstract class InterfaceTile extends InterfaceObject implements MouseListener
{ 
	private static int counter; //TODO check if necessary
	private Input gameInput;
	private boolean dragged;
	private Color clickColor;
	protected boolean clicked;
	/**
	 * InterfaceTile constructor
	 * @param fileInput Input stream to image
	 * @param ref Name for image
	 * @param flipped If image should be filpped
	 * @param gc Slick game container
	 * @param info Info from content
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public InterfaceTile(InputStream fileInput, String ref, boolean flipped, GameContainer gc, String info)
			throws SlickException, IOException, FontFormatException 
	{
		super(fileInput, ref+counter, flipped, gc, info);
		gameInput = gc.getInput();
		gameInput.addMouseListener(this);
        clickColor = new Color(73, 73, 73);
		counter ++;
	}
	@Override
	public void draw(float x, float y, boolean scaledPos)
	{
	    if(clicked)
	    {
	        draw(x, y, 45f, 40f, clickColor, scaledPos);
	        return;
	    }
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
	
	public void click(boolean click)
	{
		clicked = click;
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
