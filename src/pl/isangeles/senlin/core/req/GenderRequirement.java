/*
 * GenderRequirement.java
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
package pl.isangeles.senlin.core.req;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.character.Gender;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for gender requirement
 * @author Isangeles
 *
 */
public class GenderRequirement extends Requirement 
{
	private final Gender reqGender;
	/**
	 * Gender requirement constructor
	 * @param gender Game character gender 
	 */
	public GenderRequirement(Gender gender)
	{
		super(RequirementType.GENDER, TConnector.getText("ui", "reqGender") + ":" + gender);
		reqGender = gender;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.data.save.SaveElement#getSave(org.w3c.dom.Document)
	 */
	@Override
	public Element getSave(Document doc) 
	{
		Element statsReqE = doc.createElement("genderReq");
		statsReqE.setTextContent(reqGender.toString());
		return statsReqE;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.req.Requirement#isMetBy(pl.isangeles.senlin.core.character.Character)
	 */
	@Override
	public boolean isMetBy(Character character) 
	{
		if(character.getGender() == reqGender)
		{
			return true;
		}
		else
			return false;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.req.Requirement#charge(pl.isangeles.senlin.core.character.Character)
	 */
	@Override
	public void charge(Character character) 
	{
		return;//this requirement don't take anything from character
	}

}
