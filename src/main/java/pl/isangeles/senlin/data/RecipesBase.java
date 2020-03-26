/*
 * RecipesBase.java
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
package pl.isangeles.senlin.data;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import pl.isangeles.senlin.core.craft.Recipe;
import pl.isangeles.senlin.util.DConnector;

/**
 * Static class for recipes base
 *
 * @author Isangeles
 */
public final class RecipesBase {
  private static List<Recipe> recipesList;
  /** Private constructor to prevent initialization */
  private RecipesBase() {}
  /**
   * Returns recipe with specified ID from base
   *
   * @param id String with desired recipe ID
   * @return Recipe with specified ID or null if recipe was not found
   */
  public static Recipe get(String id) {
    for (Recipe recipe : recipesList) {
      if (recipe.getId().equals(id)) return recipe;
    }
    return null;
  }
  /**
   * Loads recipes base
   *
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws IOException
   */
  public static void load(String itemsPath)
      throws ParserConfigurationException, SAXException, IOException {
    recipesList = DConnector.getRecipes(itemsPath + File.separator + "recipes");
  }
}
