/*
 * MapWindow.java
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

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import pl.isangeles.senlin.data.GBase;
import pl.isangeles.senlin.gui.Button;
import pl.isangeles.senlin.gui.InterfaceObject;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.core.character.Character;

/**
 * Class for UI map window
 * @author Isangeles
 *
 */
class MapWindow extends InterfaceObject implements UiElement, MouseListener 
{
	private Character player;
	private TiledMap map;
	
	private Button closeB;
	private boolean openReq;
	private boolean focus;
	/**
	 * Map window constructor
	 * @param player Player character
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public MapWindow(GameContainer gc, Character player) throws SlickException, IOException, FontFormatException
	{
		super(GConnector.getInput("ui/background/mapBG.png"), "uiMapWindowBg", false, gc);
		gc.getInput().addMouseListener(this);
		
		this.player = player;
		
		closeB = new Button(GBase.getImage("buttonS"), TConnector.getText("ui", "uiClose"), gc);
	}
	/**
	 * Draws map window at specified position
	 * @param x Position on X-axis
	 * @param y Position on Y-axis
	 * @param g Game graphics for map rendering
	 */
	public void draw(float x, float y, Graphics g)
	{
		super.draw(x, y, false);
		closeB.draw(x+getDis(425), y+getDis(520), false);
		renderMap(g);
	}
	/**
	 * Opens window
	 * @param map Map to show in window
	 */
	public void open(TiledMap map)
	{
		this.map = map;
		openReq = true;
		focus = true;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.tools.UiElement#close()
	 */
	@Override
	public void close() 
	{
		openReq = false;
		reset();
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.tools.UiElement#update()
	 */
	@Override
	public void update() 
	{
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.tools.UiElement#reset()
	 */
	@Override
	public void reset() 
	{
		hideMOA();
		focus = false;
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
		return focus;
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#setInput(org.newdawn.slick.Input)
	 */
	@Override
	public void setInput(Input input) 
	{
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
		if(button == Input.MOUSE_LEFT_BUTTON)
		{
			if(closeB.isMouseOver())
				close();
		}
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseWheelMoved(int)
	 */
	@Override
	public void mouseWheelMoved(int change) 
	{
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.tools.UiElement#isOpenReq()
	 */
	@Override
	public boolean isOpenReq()
	{
		return openReq;
	}
	/**
	 * Renders area map
	 * @param g Game graphics for map render
	 */
	private void renderMap(Graphics g)
	{
		int renderStartX = getDis(25);
    	int renderStartY = getDis(25);
    	int renderEndX = ((int)getSize(812))/32;
    	int renderEndY = ((int)getSize(480))/32;
    	int fTileX = Math.floorDiv(renderStartX, 32);
    	int fTileY = Math.floorDiv(renderStartY, 32);
		g.scale(getSize(0.2f), getSize(0.2f));
		map.render((int)((x+getDis(25))/0.2f), (int)((y+getDis(25))/0.2f), fTileX, fTileY, renderEndX, renderEndY);
		g.resetTransform();
	}
}
