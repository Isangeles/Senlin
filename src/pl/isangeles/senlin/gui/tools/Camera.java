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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.data.save.SaveElement;

/**
 * Class for UI camera
 * @author Isangeles
 *
 */
public class Camera implements SaveElement
{
	private float[] pos = {0, 0};
	
	public Camera()
	{
		
	}
	
	public void up(int value)
	{
		pos[1] -= value;
	}
	
	public void down(int value)
	{
		pos[1] += value;
	}
	
	public void right(int value)
	{
		pos[0] += value;
	}
	
	public void left(int value)
	{
		pos[0] -= value;
	}
	
	public void setPos(float[] pos)
	{
		this.pos[0] = pos[0];
		this.pos[1] = pos[1];
	}
	
	public float[] getPos()
	{
		return pos;
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.data.save.SaveElement#getSave(org.w3c.dom.Document)
	 */
	@Override
	public Element getSave(Document doc) 
	{
		Element cameraE = doc.createElement("camera");
		
		Element posE = doc.createElement("pos");
		posE.setTextContent(pos[0] + ";" + pos[1]);
		cameraE.appendChild(posE);
		
		return cameraE;
	}
}
