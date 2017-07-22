/*
 * TextInput.java
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

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;

import pl.isangeles.senlin.util.Coords;
/**
 * Class for text fields
 * @author Isangeles
 *
 */
public class TextInput extends InterfaceObject implements MouseListener, KeyListener, ComponentListener
{
	private Font textFont;
	protected TrueTypeFont textTtf;
	protected TextField textField;
	private MouseOverArea fieldMOA;
	private GameContainer gc;
	private float width;
	private float height;
	/**
	 * TextInput constructor
	 * @param inputToBG IntputStream to background image
	 * @param ref Name for bg image on system
	 * @param flipped If bg image should be flipped
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws FontFormatException
	 * @throws IOException
	 */
	public TextInput(InputStream inputToBG, String ref, boolean flipped, GameContainer gc) throws SlickException, FontFormatException, IOException
	{
		super(inputToBG, ref, flipped, gc);
		this.gc = gc;
		this.gc.getInput().addMouseListener(this);
		this.gc.getInput().addKeyListener(this);
		width = super.getScaledWidth();
		height = super.getScaledHeight();
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		textFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		textTtf = new TrueTypeFont(textFont.deriveFont(12f), true);
		textField = new TextField(gc, textTtf, (int)Coords.getX("BR", 0), (int)Coords.getY("BR", 0), (int)width, (int)height, this);
		fieldMOA = new MouseOverArea(this.gc, this, 0, 0);
	}
	/**
	 * TextInput constructor (with custom size) 
	 * @param inputToBG IntputStream to background image
	 * @param ref Name for bg image on system
	 * @param flipped If bg image should be flipped
	 * @param width Width for field
	 * @param height Height for field 
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws FontFormatException
	 * @throws IOException
	 */
	public TextInput(InputStream inputToBG, String ref, boolean flipped, float width, float height, GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		super(inputToBG, ref, flipped, gc);
		this.gc = gc;
		this.gc.getInput().addMouseListener(this);
		this.gc.getInput().addKeyListener(this);
		this.width = width;
		this.height = height;
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		textFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		textTtf = new TrueTypeFont(textFont.deriveFont(12f), true);
		textField = new TextField(gc, textTtf, (int)Coords.getX("BR", 0), (int)Coords.getY("BR", 0), (int)width, (int)height, this);
		fieldMOA = new MouseOverArea(this.gc, this, 0, 0);
	}
	
	public void draw(float x, float y)
	{
		super.draw(x, y, width, height);
		textField.setLocation((int)super.x, (int)super.y);
		fieldMOA.setLocation(super.x, super.y);
	}
	
	public void render(Graphics g)
	{
		textField.render(gc, g);
	}
	
	public String getText()
	{
	    return textField.getText();
	}
	
	public void clear()
	{
	    textField.setText("");
	}
	
	public void showBorder(boolean border)
	{
		if(!border)
			textField.setBorderColor(null);
		else
			textField.setBorderColor(Color.white);
	}
	
	public void setText(String text)
	{
		textField.setText(text);
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
	public void setInput(Input input) 
	{
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
		if(fieldMOA.isMouseOver() && textField.hasFocus())
			textField.setFocus(true);
		else if(fieldMOA.isMouseOver() && !textField.hasFocus())
			textField.setFocus(false);
	}

	@Override
	public void mouseWheelMoved(int change) 
	{
	}

	@Override
	public void keyPressed(int key, char c) 
	{
	}

	@Override
	public void keyReleased(int key, char c) 
	{
	}

	@Override
	public void componentActivated(AbstractComponent source) 
	{
	}
}