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
package pl.isangeles.senlin.core.skill;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Training;
import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.data.SkillsBase;
import pl.isangeles.senlin.data.pattern.AttackPattern;
import pl.isangeles.senlin.data.pattern.SkillPattern;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for skills training
 * @author Isangeles
 *
 */
public class SkillTraining implements Training
{
    private SkillPattern skill;
    private String name;
    private String info;
    /**
     * Training constructor 
     * @param skillID ID of skill to train
     */
    public SkillTraining(String skillID)
    {
        skill = SkillsBase.getPattern(skillID);
        name = TConnector.getInfo("skills", skillID)[0];
        info = TConnector.getInfo("skills", skillID)[1];
    }
    /**
     * Teaches specified game character skill from this training
     * @param trainingCharacter Game character to train
     * @throws SlickException 
     * @throws IOException
     * @throws FontFormatException
     */
    public void teach(Character trainingCharacter) throws SlickException, IOException, FontFormatException
    {
        if(skill.isMeetReq(trainingCharacter))
        {
            trainingCharacter.getInventory().takeGold(skill.getPrice());
            trainingCharacter.addSkill(SkillsBase.getSkill(trainingCharacter, skill.getId()));
        }
        else
        {
            Log.addInformation(TConnector.getText("ui", "trainWinReqErr"));
        }
    }
    /**
     * Returns training name
     * @return String with training name
     */
    public String getName()
    {
        return name;
    }
    /**
     * Returns training info
     * @return String with training info
     */
    public List<String> getInfo()
    {
        List<String> fullInfo = new ArrayList<>();
        fullInfo.add(info);
        fullInfo.add(TConnector.getText("ui", "trainWinReqAtt") + ": " + skill.getReqAttributes().toString());
        fullInfo.add(TConnector.getText("ui", "trainWinPrice") + ": " + skill.getPrice());
        return fullInfo;
    }
}
