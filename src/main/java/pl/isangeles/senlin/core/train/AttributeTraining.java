/*
 * AttributeTraining.java
 *
 * Copyright 2017-2018 Dariusz Sikora <dev@isangeles.pl>
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
import pl.isangeles.senlin.core.AttributeType;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.req.Requirement;

/**
 * Class for attributes trainings
 *
 * @author Isangeles
 */
public class AttributeTraining extends Training {
  private AttributeType attribute;

  public AttributeTraining(AttributeType attribute, List<Requirement> reqs) {
    super(reqs);
    this.attribute = attribute;
    name = attribute.getName();
    info = name + System.lineSeparator() + trainReq.toString();
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.core.Training#teach(pl.isangeles.senlin.core.character.Character)
   */
  @Override
  public boolean teach(Character trainingCharacter)
      throws SlickException, IOException, FontFormatException {
    if (!trainReq.isMetBy(trainingCharacter)) return false;
    else {
      trainReq.chargeAll(trainingCharacter);
      trainingCharacter.getAttributes().modAtt(attribute, 1);
      return true;
    }
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.core.Training#getSave(org.w3c.dom.Document)
   */
  @Override
  public Element getSave(Document doc) {
    Element attributeE = doc.createElement("attribute");

    attributeE.setAttribute("type", attribute.getId());

    Element trainReqE = doc.createElement("trainReq");
    for (Requirement req : trainReq) {
      trainReqE.appendChild(req.getSave(doc));
    }
    attributeE.appendChild(trainReqE);

    return attributeE;
  }
}
