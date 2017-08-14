/*
 * ManaRequirement.java
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
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for magicka points requirement
 * @author Isangeles
 *
 */
public class ManaRequirement extends Requirement
{
    private int reqMana;
    /**
     * Magicka requirement constructor
     * @param manaPoints Amount of required magicka points
     */
    public ManaRequirement(int manaPoints)
    {
        reqMana = manaPoints;
        info = TConnector.getText("ui", "reqMana") + ":" + reqMana;
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.data.save.SaveElement#getSave(org.w3c.dom.Document)
     */
    @Override
    public Element getSave(Document doc)
    {
        Element manaReqE = doc.createElement("manaReq");
        manaReqE.setTextContent(reqMana+"");
        return manaReqE;
    }

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.core.req.Requirement#isMetBy(pl.isangeles.senlin.core.character.Character)
     */
    @Override
    public boolean isMetBy(Character character)
    {
        if(character.getMagicka() >= reqMana)
        {
            met = true;
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
        if(met)
        {
            character.takeMagicka(reqMana);
            met = false;
        }
    }

}
