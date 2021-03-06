/*
 * HealthRequirement.java
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
package pl.isangeles.senlin.core.req;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for health points requirement
 *
 * @author Isangeles
 */
public class HealthRequirement extends Requirement {
  private int reqHp;
  /**
   * Health requirement constructor
   *
   * @param healthPoints Amount of required health points
   */
  public HealthRequirement(int healthPoints) {
    super(RequirementType.HEALTH, TConnector.getText("ui", "hpName") + ":" + healthPoints);
    reqHp = healthPoints;
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.data.save.SaveElement#getSave(org.w3c.dom.Document)
   */
  @Override
  public Element getSave(Document doc) {
    Element hpReqE = doc.createElement("hpReq");
    hpReqE.setTextContent(reqHp + "");
    return hpReqE;
  }

  /* (non-Javadoc)
   * @see pl.isangeles.senlin.core.req.Requirement#isMetBy(pl.isangeles.senlin.core.character.Character)
   */
  @Override
  public boolean isMetBy(Character character) {
    if (character.getHealth() >= reqHp) {
      met = true;
      return true;
    } else return false;
  }

  /* (non-Javadoc)
   * @see pl.isangeles.senlin.core.req.Requirement#charge(pl.isangeles.senlin.core.character.Character)
   */
  @Override
  public void charge(Character character) {
    if (met) {
      character.modHealth(-reqHp);
      met = false;
    }
  }
}
