/*
 * GameCursor.java
 * 
 * Copyright 2017 Dariusz Sikora <darek@darek-PC-LinuxMint18>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */
package pl.isangeles.senlin.gui;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.io.IOException;

import org.lwjgl.input.Cursor;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;

import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.states.Global;
/**
 * Class for game cursor
 * TODO clean this mess
 * @author Isangeles
 *
 */
public class GameCursor extends InterfaceObject implements MouseListener
{
	public static final int WIDTH = 50;
	public static final int HEIGHT = 40;
	private GameContainer gc;
	private int x;
	private int y;
	private Image selectCursor;
	private Image speak;
	private String type;
	
	public GameCursor(GameContainer gc) throws SlickException, IOException 
	{
		super(GConnector.getInput("ui/cursorBlack.png"), "gameCursor", false, gc);
		gc.getInput().addMouseListener(this);
		this.gc = gc;
		x = gc.getInput().getMouseX();
		y = gc.getInput().getMouseY();
		selectCursor = new Image(GConnector.getInput("ui/cursorSelect.png"), "selctCursor", false);
		speak = new Image(GConnector.getInput("ui/cursorSpeak.png"), "speakCursor", false);
		type = "normal";
		
	}
	
	@Override
	public void draw(float x, float y)
	{
		super.draw(x, y, true);
	}
	
	@Override
	public void draw()
	{
		/*
		x = (int)(gc.getInput().getMouseX()-Global.getCameraPos()[0]);
		y = (int)(gc.getInput().getMouseY()-Global.getCameraPos()[1]);
		super.draw(x, y, true);
		*/
		//moveMouse(new Point(x, y));
		x = (int)(gc.getInput().getMouseX());
		y = (int)(gc.getInput().getMouseY());
		if(type.equals("speak"))
		{
			speak.draw(x, y);
			gc.setMouseGrabbed(true);
		}
		if(type.equals("normal"))
			gc.setMouseGrabbed(false);
	}
	
	public void update(Character player)
	{
	}
	
	public void changeToSelect()
	{
		type = "select";
	}
	
	public void changeToNormal()
	{
		type = "normal";
		Log.addSystem("to norm");
	}
	
	public void changeToSpeak()
	{
		type = "speak";
		Log.addSystem("to speak");
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
		if(Log.isDebug())
			Log.addDebug("Mouse pos: " + x + "/" +  y);
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
