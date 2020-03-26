/*
 * LevelRequirement.java
 *
 * Copyright 2018 Dariusz Sikora <dev@isangeles.pl>
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
 * Class for minimal level requirement
 *
 * @author Isangeles
 */
public class LevelRequirement extends Requirement {
  private final int reqLevel;
  /**
   * Level requirement constructor
   *
   * @param reqLevel Minimal required level
   */
  public LevelRequirement(int reqLevel) {
    super(RequirementType.LEVEL, TConnector.getText("ui", "levelName:") + ":" + reqLevel);
    this.reqLevel = reqLevel;
  }

  /* (non-Javadoc)
   * @see pl.isangeles.senlin.core.req.Requirement#isMetBy(pl.isangeles.senlin.core.character.Character)
   */
  @Override
  public boolean isMetBy(Character character) {
    return character.getLevel() >= reqLevel;
  }

  /* (non-Javadoc)
   * @see pl.isangeles.senlin.core.req.Requirement#charge(pl.isangeles.senlin.core.character.Character)
   */
  @Override
  public void charge(Character character) {
    return; // this requirement don't take anything from character
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.data.save.SaveElement#getSave(org.w3c.dom.Document)
   */
  @Override
  public Element getSave(Document doc) {
    Element levelReqE = doc.createElement(type.toTagName());
    levelReqE.setTextContent(reqLevel + "");
    return levelReqE;
  }
}
