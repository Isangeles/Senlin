/*
 * EffectTile.java
 * 
 * Copyright 2017 Dariusz Sikora <dev@isangeles.pl>
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
import java.io.InputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.data.GBase;
import pl.isangeles.senlin.gui.InterfaceTile;
/**
 * Class for effects icons
 * @author Isangeles
 *
 */
public class EffectTile extends InterfaceTile 
{
	private TrueTypeFont ttf;
	
	public EffectTile(InputStream fileInput, String ref, boolean flipped, GameContainer gc, String info)
			throws SlickException, IOException, FontFormatException 
	{
		super(fileInput, ref, flipped, gc, info);
		ttf = new TrueTypeFont(GBase.getFont("mainUiFont").deriveFont(12f), true);
	}
	
	public EffectTile(Image iconImg, GameContainer gc, String info)
			throws SlickException, IOException, FontFormatException 
	{
		super(iconImg, gc, info);
		ttf = new TrueTypeFont(GBase.getFont("mainUiFont").deriveFont(12f), true);
	}
	/**
	 * Draws icon with label at center
	 * @param x Position on x-axis
	 * @param y Position on y-axis
	 * @param text Text for label
	 * @param scaledPos True if position should be scaled, false otherwise
	 */
	public void draw(float x, float y, String text, boolean scaledPos)
	{
		super.draw(x, y, scaledPos);
		ttf.drawString(x, y + super.getScaledHeight(), text);
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
