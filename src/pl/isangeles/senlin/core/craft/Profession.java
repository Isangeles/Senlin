/*
 * Profession.java
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
package pl.isangeles.senlin.core.craft;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.data.SaveElement;
import pl.isangeles.senlin.gui.ScrollableContent;

/**
 * Class for game professions
 * @author Isangeles
 *
 */
public class Profession extends ArrayList<Recipe> implements SaveElement, ScrollableContent
{
	private static final long serialVersionUID = 1L;
	
	private final ProfessionType type;
	private ProfessionLevel level;
	/**
	 * Profession constructor 
	 * @param type Profession type
	 */
	public Profession(ProfessionType type)
	{
		this.type = type;
		level = ProfessionLevel.NOVICE;
	}
	
	public Profession(ProfessionType type, ProfessionLevel level, List<Recipe> recipes)
	{
		this.type = type;
		this.level = level;
		this.addAll(recipes);
	}
	
	public void setLevel(ProfessionLevel level)
	{
		this.level = level;
	}
	
	public ProfessionType getType()
	{
		return type;
	}
	
	public ProfessionLevel getLevel()
	{
		return level;
	}
	
	public Recipe get(String id)
	{
		for(Recipe recipe : this)
		{
			if(recipe.getId().equals(id))
				return recipe;
		}
		return null;
	}
	
	@Override
	public boolean add(Recipe recipe)
	{
		if(!this.contains(recipe))
		{
			if(recipe.getType() == this.type && recipe.getLevel().ordinal() <= this.level.ordinal())
				return super.add(recipe);
		}
		
		return false;
			
	}
	
	public boolean equals(Profession profession)
	{
		return (profession.getType() == this.type);
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.data.SaveElement#getSave(org.w3c.dom.Document)
	 */
	@Override
	public Element getSave(Document doc) 
	{
		Element professionE = doc.createElement("profession");
		
		professionE.setAttribute("type", type.toString());
		professionE.setAttribute("level", level.toString());
		for(Recipe recipe : this)
		{
			if(recipe != null)
			{
				Element recipeE = doc.createElement("recipe");
				recipeE.setTextContent(recipe.getId());
				professionE.appendChild(recipeE);
			}
		}
		
		return professionE;
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.ScrollableContent#getName()
	 */
	@Override
	public String getName() 
	{
		return type.getName();
	}
}
