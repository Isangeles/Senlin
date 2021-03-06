/*
 * Recipe.java
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
package pl.isangeles.senlin.core.craft;

import java.util.ArrayList;
import java.util.List;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.req.ItemsRequirement;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.data.ItemsBase;
import pl.isangeles.senlin.gui.ScrollableContent;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for game professions recipes
 *
 * @author Isangeles
 */
public class Recipe implements ScrollableContent {
  private final String id;
  private final String name;
  private final String info;
  private final ProfessionType type;
  private final ProfessionLevel level;
  private final ItemsRequirement reqComponents;
  private final List<String> result;
  private final List<Requirement> trainReq;
  /**
   * Recipe constructor
   *
   * @param id Recipe ID
   * @param type Recipe type(profession type)
   * @param reqComponents List of components required for item created by this recipe
   * @param result ID of item created by this recipe
   */
  public Recipe(
      String id,
      ProfessionType type,
      ProfessionLevel level,
      ItemsRequirement reqComponents,
      List<String> result,
      List<Requirement> trainReq) {
    this.id = id;
    this.type = type;
    this.level = level;
    this.reqComponents = reqComponents;
    this.result = result;
    this.trainReq = trainReq;
    name =
        TConnector.getTextFromModule("items", "recName")
            + ":"
            + TConnector.getInfoFromModule("items", result.get(0))[0];
    info =
        name
            + " "
            + System.lineSeparator()
            + TConnector.getInfoFromModule("items", result.get(0))[1]
            + " "
            + System.lineSeparator()
            + TConnector.getText("ui", "cMenuLevel")
            + ":"
            + level.getName()
            + System.lineSeparator()
            + reqComponents.getInfo();
  }
  /**
   * Creates item from this recipe
   *
   * @param crafter Game character
   * @return List with created items
   */
  public List<Item> create(Character crafter) {
    if (reqComponents.isMetBy(crafter)) {
      reqComponents.charge(crafter);
      List<Item> resultItems = new ArrayList<>();
      for (String itemId : result) {
        resultItems.add(ItemsBase.getItem(itemId));
      }
      return resultItems;
    } else return null;
  }
  /**
   * Returns recipe ID
   *
   * @return String with recipe ID
   */
  public String getId() {
    return id;
  }
  /**
   * Returns recipe type(profession type)
   *
   * @return Profession type
   */
  public ProfessionType getType() {
    return type;
  }

  public ProfessionLevel getLevel() {
    return level;
  }

  public boolean equals(Recipe recipe) {
    return (recipe.getId().equals(id));
  }

  public String getInfo() {
    return info;
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.gui.ScrollableContent#getName()
   */
  @Override
  public String getName() {
    return name;
  }

  public List<Requirement> getTrainRequirements() {
    return trainReq;
  }
}
