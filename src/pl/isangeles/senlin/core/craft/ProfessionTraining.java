/*
 * ProfessionTraining.java
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

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.List;

import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Training;
import pl.isangeles.senlin.core.req.Requirement;

/**
 * Class for professions training
 * @author Isangeles
 *
 */
public class ProfessionTraining implements Training 
{
	private Profession profession;
	private List<Requirement> trainingReq;
	/**
	 * Profession training constructor 
	 * @param type Type of profession to train
	 * @param level Level of profession to train
	 * @param trainingReq List of requirements for this training
	 */
	public ProfessionTraining(ProfessionType type, ProfessionLevel level, List<Requirement> trainingReq)
	{
		profession = new Profession(type);
		profession.setLevel(level);
		this.trainingReq = trainingReq;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Training#teach(pl.isangeles.senlin.core.Character)
	 */
	@Override
	public void teach(Character trainingCharacter) throws SlickException, IOException, FontFormatException
	{
		for(Requirement req : trainingReq)
		{
			if(!req.isMetBy(trainingCharacter))
				return;
		}
		
		if(profession.getLevel() == ProfessionLevel.NOVICE)
		{
			trainingCharacter.addProfession(profession);
		}
		else
		{
			Profession pro = trainingCharacter.getProfession(profession.getType());
			if(pro != null)
			{
				if(pro.getLevel().ordinal()+1 == profession.getLevel().ordinal())
					pro.setLevel(profession.getLevel());
			}
		}
	}

}
