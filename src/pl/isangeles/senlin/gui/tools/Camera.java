/*
 * Camera.java
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
package pl.isangeles.senlin.gui.tools;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.states.GameWorld;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.util.Size;

/**
 * Class for UI camera
 * @author Isangeles
 *
 */
public class Camera implements KeyListener, MouseListener, SaveElement
{
	private Position pos = new Position();
	private GameWorld gw;
	private Size size = new Size();
	private float zoom = 1.0f;
	private boolean lock;
	/**
	 * Camera constructor
	 * @param size Camera size
	 */
	public Camera(GameContainer gc, GameWorld gw, Size size)
	{
	    this.size = size;
	    this.gw = gw;
	    gc.getInput().addMouseListener(this);
	}
	/**
	 * Updates camera
	 * @param input
	 */
	public void update(Input input)
	{
		if(!lock)
			keyDown(input);
	}
	/**
	 * Moves camera up by specified value
	 * @param value Value
	 */
	public void up(int value)
	{
		pos.y -= value;
	}
	/**
	 * Moves camera down by specified value
	 * @param value Value
	 */
	public void down(int value)
	{
		pos.y += value;
	}
	/**
	 * Moves camera to the right by specified value
	 * @param value Value
	 */
	public void right(int value)
	{
		pos.x += value;
	}
	/**
	 * Moves camera to the left by specified value
	 * @param value Value
	 */
	public void left(int value)
	{
		pos.x -= value;
	}
	/**
	 * Zooms camera by specified value
	 * @param value Zoom value
	 */
	public void zoom(float value)
	{
		zoom += value;
		Log.addSystem("zooming");
		/*
		pos.x *= value;
		pos.y *= value;
		size.width *= value;
		size.height *= value;
		*/
	}
	/**
	 * Unzooms camera by specified value
	 * @param value Unzoom value
	 */
	public void unzoom(float value)
	{
		zoom -= value;
		Log.addSystem("unzooming");
		/*
		pos.x /= value;
		pos.y /= value;
		size.width /= value;
		size.height /= value;
		*/
	}
	/**
	 * Centers camera at specified position
	 * @param pos XY position
	 */
	public void centerAt(Position pos)
	{
	    this.pos.x = pos.x - ((int)size.width/2);
	    this.pos.y = pos.y - ((int)size.height/2);
	}
	/**
	 * Sets specified position as camera position
	 * @param pos XY position
	 */
	public void setPos(Position pos)
	{
		this.pos = pos;
	}
	/**
	 * Returns current camera position
	 * @return NEW XY position
	 */
	public Position getPos()
	{
		return new Position(pos.x, pos.y);
	}
	/**
	 * Returns position of bottom right angle
	 * @return XY position
	 */
	public Position getBRPos()
	{
		return new Position(pos.x + size.width, pos.y + size.height);
	}
	/**
	 * Returns camera size
	 * @return NEW size tuple
	 */
	public Size getSize()
	{
		return new Size(size.width, size.height);
	}
	/**
	 * Returns camera zoom
	 * @return Camera zoom value
	 */
	public float getZoom()
	{
		return zoom;
	}
	/**
	 * Locks or unlocks camera
	 * @param lock True to lock, false to unlock
	 */
	public void setLock(boolean lock)
	{
		this.lock = lock;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.data.save.SaveElement#getSave(org.w3c.dom.Document)
	 */
	@Override
	public Element getSave(Document doc) 
	{
		Element cameraE = doc.createElement("camera");
		
		Element posE = doc.createElement("pos");
		posE.setTextContent(pos.toString());
		cameraE.appendChild(posE);
		
		return cameraE;
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#inputEnded()
	 */
	@Override
	public void inputEnded() 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#inputStarted()
	 */
	@Override
	public void inputStarted() 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#isAcceptingInput()
	 */
	@Override
	public boolean isAcceptingInput() 
	{
		return !lock;
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#setInput(org.newdawn.slick.Input)
	 */
	@Override
	public void setInput(Input input) 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.KeyListener#keyPressed(int, char)
	 */
	@Override
	public void keyPressed(int key, char c) 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.KeyListener#keyReleased(int, char)
	 */
	@Override
	public void keyReleased(int key, char c) 
	{
	}
    /**
     * KeyDown method called in update, because engine does not provide keyDown method for override  
     * @param input Input from game container
     */
    private void keyDown(Input input)
    {
    	if(input.isKeyDown(Input.KEY_W) && pos.y > -200)
    		up(32);
        if(input.isKeyDown(Input.KEY_S) && getBRPos().y < (gw.getArea().getMapSize().height+Coords.getSize(200)))
            down(32);
        if(input.isKeyDown(Input.KEY_A) && pos.x > -200)
            left(32);
        if(input.isKeyDown(Input.KEY_D) && getBRPos().x < (gw.getArea().getMapSize().width+Coords.getSize(200)))
            right(32);
        Global.setCamerPos(pos.x, pos.y);
    }
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseClicked(int, int, int, int)
	 */
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount)
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseDragged(int, int, int, int)
	 */
	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseMoved(int, int, int, int)
	 */
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy)
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mousePressed(int, int, int)
	 */
	@Override
	public void mousePressed(int button, int x, int y)
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseReleased(int, int, int)
	 */
	@Override
	public void mouseReleased(int button, int x, int y) 
	{
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseWheelMoved(int)
	 */
	@Override
	public void mouseWheelMoved(int change) 
	{
		if(change > 0 && zoom < 1.5f)
    	{
    		zoom(0.1f);
    	}
    	if(change < 0 && zoom > 0.5f)
    	{
    		unzoom(0.1f);
    	}
	}
}
