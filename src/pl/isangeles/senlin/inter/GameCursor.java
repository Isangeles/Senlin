package pl.isangeles.senlin.inter;
import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.data.CommBase;
import pl.isangeles.senlin.states.Global;
/**
 * Class for game cursor
 * @author Isangeles
 *
 */
public class GameCursor extends InterfaceObject implements MouseListener
{
	private GameContainer gc;
	private int x;
	private int y;
	
	public GameCursor(GameContainer gc) throws SlickException, IOException 
	{
		super(GConnector.getInput("ui/cursorBlack.png"), "gameCursor", false, gc);
		gc.getInput().addMouseListener(this);
		this.gc = gc;
		x = gc.getInput().getMouseX();
		y = gc.getInput().getMouseY();
	}
	
	@Override
	public void draw()
	{
		x = (int)(gc.getInput().getMouseX()-Global.getCameraPos()[0]);
		y = (int)(gc.getInput().getMouseY()-Global.getCameraPos()[1]);
		super.draw(x, y, true);
		//moveMouse(new Point(x, y));
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

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount)
	{
		if(CommBase.isDebug())
			CommBase.addDebug("Mouse pos: " + x + "/" +  y);
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) 
	{
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy)
	{
		/*
		oldx += Global.getCameraPos()[0];
		oldy += Global.getCameraPos()[1];
		newx += Global.getCameraPos()[0];
		newy += Global.getCameraPos()[1];
		
		x += (newx-oldx);
		y += (newy-oldy);
		*/
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
	/**
	 * UNUSED
	 * @param p
	 */
	private void moveMouse(Point p) 
	{
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice[] gs = ge.getScreenDevices();

	    // Search the devices for the one that draws the specified point.
	    for (GraphicsDevice device: gs) 
	    { 
	        GraphicsConfiguration[] configurations = device.getConfigurations();
	        for (GraphicsConfiguration config: configurations) 
	        {
	            Rectangle bounds = config.getBounds();
	            if(bounds.contains(p)) 
	            {
	                // Set point to screen coordinates.
	                Point b = bounds.getLocation(); 
	                Point s = new Point(p.x - b.x, p.y - b.y);

	                try 
	                {
	                    Robot r = new Robot(device);
	                    r.mouseMove(s.x, s.y);
	                } 
	                catch (AWTException e) 
	                {
	                    e.printStackTrace();
	                }

	                return;
	            }
	        }
	    }
	    // Couldn't move to the point, it may be off screen.
	    return;
	}
}
