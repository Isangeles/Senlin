/*
 * InfoWindow.java
 * 
 * Copyright 2018 Dariusz Sikora <dev@isangeles.pl>
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

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.data.GBase;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.Settings;
/**
 * Class for floating window with text
 * @author Isangeles
 *
 */
public class InfoWindow extends InterfaceObject
{
	private static final int MAX_CHARS = 30;
	private static final float FONT_SIZE = 12f;
	private TextBlock text;
	private TrueTypeFont ttf;
	/**
	 * Info window constructor
	 * @param gc Slick game container
	 * @param textInfo Text for window
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public InfoWindow(GameContainer gc, String textInfo) throws SlickException, IOException, FontFormatException 
	{
		super(GBase.getImage("infoWinBg"), gc);

		Font font = GBase.getFont("mainUiFont");
		ttf = new TrueTypeFont(font.deriveFont(getSize(FONT_SIZE)), true);
		
		this.text = new TextBlock(textInfo, MAX_CHARS, ttf);
	}
	/**
	 * Draws window with information text split into lines
	 */
	public void draw(float x, float y)
	{
	    super.drawUnscaled(getCorrectX(x), getCorrectY(y), text.getTextWidth(), text.getTextHeight());
	    text.draw(super.x, super.y);
	}
	/**
	 * Sets text to display
	 * @param text String with text
	 */
	public void setText(String text)
	{
		this.text = new TextBlock(text, MAX_CHARS, ttf);
	}
	/**
	 * Checks if specified x position need to be corrected to not protrude the screen 
	 * @param x Position on x-axis
	 * @return Corrected or untouched x value
	 */
	private float getCorrectX(float x)
	{
		if(x + getScaledWidth() >= Global.getCameraSize().width)
			x -= text.getTextWidth();
		
		return x;
	}
	/**
	 * Checks if specified y position need to be corrected to not protrude the screen 
	 * @param y Position on y-axis
	 * @return Corrected or untouched y value
	 */
	private float getCorrectY(float y)
	{
		if(y + getScaledHeight() >= Global.getCameraSize().height)
			y -= text.getTextHeight();
		
		return y;
	}
}
