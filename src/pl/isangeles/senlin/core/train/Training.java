/*
 * Training.java
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
package pl.isangeles.senlin.core.train;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.List;

import org.newdawn.slick.SlickException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.core.req.Requirements;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.gui.ScrollableContent;

/**
 * Interface for training classes
 * @author Isangeles
 *
 */
public abstract class Training implements SaveElement, ScrollableContent
{
	protected String name = "";
	protected String info = "";
    protected final Requirements trainReq;
    
    public Training(List<Requirement> reqs)
    {
    	trainReq = new Requirements(reqs);
    }
	/**
	 * Teaches specified character
	 * @param trainingCharacter Game character
	 * @throws SlickException 
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public abstract boolean teach(Character trainingCharacter) throws SlickException, IOException, FontFormatException;
	
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.ScrollableContent#getName()
	 */
	@Override
    public String getName()
    {
        return name;
    }
    
	public String getInfo()
	{
		return info;
	}
}
