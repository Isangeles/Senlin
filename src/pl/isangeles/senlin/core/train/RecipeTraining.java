/*
 * RecipeTraining.java
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

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.craft.Profession;
import pl.isangeles.senlin.core.craft.Recipe;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.data.RecipesBase;

/**
 * Class for recipes trainings
 * @author Isangeles
 *
 */
public class RecipeTraining extends Training 
{
	private final Recipe recipe;
	/**
	 * Recipe training constructor 
	 * @param recipeId ID of recipe to train
	 */
	public RecipeTraining(String recipeId)
	{
		super(RecipesBase.get(recipeId).getTrainRequirements());
		recipe = RecipesBase.get(recipeId);
		name = recipe.getName();
		info = recipe.getInfo();
		for(Requirement req : trainReq)
		{
			info += System.lineSeparator() + req.getInfo();
		}
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Training#teach(pl.isangeles.senlin.core.Character)
	 */
	@Override
	public boolean teach(Character trainingCharacter) throws SlickException, IOException, FontFormatException 
	{
		for(Requirement req : trainReq)
		{
			if(!req.isMetBy(trainingCharacter))
				return false;
		}
		
		Profession recPro = trainingCharacter.getProfession(recipe.getType());
		if(recPro != null && recPro.getLevel().ordinal() >= recipe.getLevel().ordinal())
		{
			for(Requirement req : trainReq)
			{
				req.charge(trainingCharacter);
			}
			return recPro.add(recipe);
		}
		else
			return false;

	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Training#getSave(org.w3c.dom.Document)
	 */
	@Override
	public Element getSave(Document doc) 
	{
		Element recipeE = doc.createElement("recipe");
		
		recipeE.setTextContent(recipe.getId());
		
		return recipeE;
	}

}
