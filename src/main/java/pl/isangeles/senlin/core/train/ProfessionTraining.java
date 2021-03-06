/*
 * ProfessionTraining.java
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
import pl.isangeles.senlin.core.craft.Profession;
import pl.isangeles.senlin.core.craft.ProfessionLevel;
import pl.isangeles.senlin.core.craft.ProfessionType;
import pl.isangeles.senlin.core.req.Requirement;

/**
 * Class for professions training
 *
 * @author Isangeles
 */
public class ProfessionTraining extends Training {
  private Profession profession;
  /**
   * Profession training constructor
   *
   * @param type Type of profession to train
   * @param level Level of profession to train
   * @param trainingReq List of requirements for this training
   */
  public ProfessionTraining(
      ProfessionType type, ProfessionLevel level, List<Requirement> trainingReq) {
    super(trainingReq);
    profession = new Profession(type);
    profession.setLevel(level);
    name = type.getName() + ": " + level.getName();
    info = name;
    for (Requirement req : trainingReq) {
      info += System.lineSeparator() + req.getInfo();
    }
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.core.Training#teach(pl.isangeles.senlin.core.Character)
   */
  @Override
  public boolean teach(Character trainingCharacter)
      throws SlickException, IOException, FontFormatException {
    for (Requirement req : trainReq) {
      if (!req.isMetBy(trainingCharacter)) return false;
    }

    if (profession.getLevel() == ProfessionLevel.NOVICE) {
      for (Requirement req : trainReq) {
        req.charge(trainingCharacter);
      }
      return trainingCharacter.addProfession(profession);
    } else {
      Profession pro = trainingCharacter.getProfession(profession.getType());
      if (pro != null) {
        if (pro.getLevel().ordinal() + 1 == profession.getLevel().ordinal()) {
          for (Requirement req : trainReq) {
            req.charge(trainingCharacter);
          }
          pro.setLevel(profession.getLevel());
          return true;
        }
      }
      return false;
    }
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.data.SaveElement#getSave(org.w3c.dom.Document)
   */
  @Override
  public Element getSave(Document doc) {
    Element professionE = doc.createElement("profession");
    professionE.setAttribute("type", profession.getType().toString());
    professionE.setAttribute("level", profession.getLevel().toString());

    Element trainReqE = doc.createElement("trainReq");
    for (Requirement req : trainReq) {
      trainReqE.appendChild(req.getSave(doc));
    }
    professionE.appendChild(trainReqE);

    return professionE;
  }
}
