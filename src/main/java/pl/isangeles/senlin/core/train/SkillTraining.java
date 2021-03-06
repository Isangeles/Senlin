/*
 * Training.java
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
package pl.isangeles.senlin.core.train;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.List;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.data.SkillsBase;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for skills training Training requirements should be defined only in npc.base, but for
 * backward compatibility old requirements in recipes.base are also supported
 *
 * @author Isangeles
 */
public class SkillTraining extends Training {
  private final String skillId;
  private final boolean customReqs; // for backward compatibility
  /**
   * Training constructor
   *
   * @param skillID ID of skill to train
   */
  public SkillTraining(String skillId) {
    super(SkillsBase.getPattern(skillId).getRequirements());
    this.skillId = skillId;
    name = TConnector.getInfoFromModule("skills", skillId)[0];
    info = name + " " + TConnector.getInfoFromModule("skills", skillId)[1];
    for (Requirement req : trainReq) {
      info += System.lineSeparator() + req.getInfo();
    }
    customReqs = false;
  }
  /**
   * Skill training constructor(with custom training requirements)
   *
   * @param skillId ID of skill to train
   * @param trainReq List with training requirements
   */
  public SkillTraining(String skillId, List<Requirement> trainReq) {
    super(trainReq);
    this.skillId = skillId;
    name = TConnector.getInfoFromModule("skills", skillId)[0];
    info = name + " " + TConnector.getInfoFromModule("skills", skillId)[1];
    for (Requirement req : trainReq) {
      info += System.lineSeparator() + req.getInfo();
    }
    customReqs = true;
  }
  /**
   * Teaches specified game character skill from this training
   *
   * @param trainingCharacter Game character to train
   * @throws SlickException
   * @throws IOException
   * @throws FontFormatException
   */
  public boolean teach(Character trainingCharacter)
      throws SlickException, IOException, FontFormatException {
    for (Requirement req : trainReq) {
      if (!req.isMetBy(trainingCharacter)) return false;
    }

    for (Requirement req : trainReq) {
      req.charge(trainingCharacter);
    }

    return trainingCharacter.addSkill(SkillsBase.getSkill(trainingCharacter, skillId));
  }
  /**
   * Returns training info
   *
   * @return String with training info
   */
  public String getInfo() {
    return info;
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.core.Training#getSave(org.w3c.dom.Document)
   */
  @Override
  public Element getSave(Document doc) {
    Element skillE = doc.createElement("skill");
    if (customReqs) {
      skillE.setAttribute("id", skillId);
      Element trainReqE = doc.createElement("trainReq");
      for (Requirement req : trainReq) {
        trainReqE.appendChild(req.getSave(doc));
      }
      skillE.appendChild(trainReqE);
    } else {
      skillE.setTextContent(skillId);
    }
    return skillE;
  }
}
