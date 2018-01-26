/*
 * WaitWindow.java
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

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;

import pl.isangeles.senlin.data.GBase;
import pl.isangeles.senlin.gui.Button;
import pl.isangeles.senlin.gui.InfoField;
import pl.isangeles.senlin.gui.InterfaceObject;
import pl.isangeles.senlin.states.GameWorld;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.core.WorldTime;
import pl.isangeles.senlin.core.character.Character;

/**
 * Class for UI wait window 
 * @author Isangeles
 *
 */
class WaitWindow extends InterfaceObject implements UiElement, KeyListener, MouseListener
{
	private GameWorld gw;
	
	private Button hPlusB;
	private Button hMinusB;
	private Button waitB;
	//private Button closeB;
	private InfoField waitTimeF;
	private TrueTypeFont ttf;
	
	//private WorldTime waitTime;
	private int waitTimeH;
	private int waitTimeM;
	
	private boolean focus;
	private boolean openReq;
	private boolean rest;
	/**
	 * Waiting window constructor 
	 * @param gc Slick game container
	 * @param gw Game world
	 * @throws IOException 
	 * @throws FontFormatException 
	 * @throws SlickException 
	 */
	public WaitWindow(GameContainer gc, GameWorld gw) throws SlickException, FontFormatException, IOException
	{
		super(GConnector.getInput("ui/background/waitBG.png"), "uiWaitBg", false, gc);
		gc.getInput().addKeyListener(this);
		gc.getInput().addMouseListener(this);
		
		this.gw = gw;
		
		hPlusB = new Button(GBase.getImage("buttonNext"), "", gc);
		hMinusB = new Button(GBase.getImage("buttonBack"), "", gc);
		waitB = new Button(GBase.getImage("buttonS"), TConnector.getText("ui", "waitWinWait"), gc);
		//closeB = new Button(GBase.getImage("buttonClose"), TConnector.getText("ui", "close"), gc);
		waitTimeF = new InfoField(getSize(70f), getSize(50f), gc);
		Font font = GBase.getFont("mainUiFont");
		ttf = new TrueTypeFont(font.deriveFont(getSize(12f)), true);
		
		focus = true;
	}
	/**
	 * Draws window
	 */
	@Override
	public void draw(float x, float y)
	{
		super.draw(x, y, false);
		
		if(rest)
			ttf.drawString(super.getCenter(false).x, super.getTR(false).y, TConnector.getText("ui", "waitWinTitle"));
		else
			ttf.drawString(super.getCenter(false).x, super.getTR(false).y, TConnector.getText("ui", "waitWinRest"));
		hMinusB.draw(super.x, super.getCenter(false).y, false);
		hPlusB.draw(super.getBR(false).x - hPlusB.getScaledWidth(), super.getCenter(false).y, false);
		waitB.draw(super.getCenter(false).x - waitB.getScaledWidth()/2, super.getCenter(false).y + getDis(40), false);
		waitTimeF.draw(super.getCenter(false).x - getSize(35f), super.getCenter(false).y - getSize(25f), false);
	}
	/**
	 * Opens window
	 * @param rest True if character waiting, false if just waiting 
	 */
	public void open(boolean rest)
	{
		openReq = true;
		this.rest = rest;
		if(rest)
			waitB.setLabel(TConnector.getText("ui", "waitWinRest"));
		else
			waitB.setLabel(TConnector.getText("ui", "waitWinWait"));
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
		waitTimeF.setText(waitTimeH + ":" + waitTimeM);
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.tools.UiElement#reset()
	 */
	@Override
	public void reset() 
	{
		hideMOA();
		rest = false;
		waitTimeH = 0;
		waitTimeM = 0;
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
	 * Sets or removes focus from window
	 * @param focus True to set focus, false to remove
	 */
	public void setFocus(boolean focus)
	{
		this.focus = focus;
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
			if(hPlusB.isMouseOver())
				waitTimeH += 1;
			if(hMinusB.isMouseOver() && waitTimeH > 0)
				waitTimeH -= 1;
			if(waitB.isMouseOver())
			{
				gw.getDay().getTime().addHours(waitTimeH);
				waitTimeH = 0;
				waitTimeM = 0;
			}
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
		if(key == Input.KEY_T && !openReq)
			open(false);
		else if(key == Input.KEY_ESCAPE || (key == Input.KEY_T && openReq))
			close();
	}
}
