/*
 * SavedGame.java
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
package pl.isangeles.senlin.data.save;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.List;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.w3c.dom.DOMException;
import pl.isangeles.senlin.core.Chapter;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.day.Day;
import pl.isangeles.senlin.data.area.Scenario;
import pl.isangeles.senlin.gui.UiLayout;

/**
 * Class for saved games
 *
 * @author Isangeles
 */
public class SavedGame {
  private Character player;
  private Chapter chapter;
  private Day day;
  private UiLayout uiLayout;
  /**
   * Creates saved game to load
   *
   * @param player Player game character
   * @param scenarios List with saved game scenarios
   * @param activeScenarioId ID of active game scenario
   * @throws FontFormatException
   * @throws IOException
   * @throws SlickException
   * @throws DOMException
   * @throws NumberFormatException
   */
  public SavedGame(
      Character player,
      String chapterId,
      List<SavedScenario> savedScenarios,
      Scenario activeScenario,
      Day savedDay,
      UiLayout savedLayout,
      GameContainer gc)
      throws NumberFormatException, DOMException, SlickException, IOException, FontFormatException {
    this.player = player;
    chapter = new Chapter(chapterId, savedScenarios, activeScenario, gc);
    day = savedDay;
    uiLayout = savedLayout;
  }
  /**
   * Returns saved player character
   *
   * @return Game character
   */
  public Character getPlayer() {
    return player;
  }
  /**
   * Returns saved chapter
   *
   * @return Game chapter
   */
  public Chapter getChapter() {
    return chapter;
  }
  /**
   * Returns saved day state
   *
   * @return Game world day
   */
  public Day getDay() {
    return day;
  }
  /**
   * Returns saved GUI layout
   *
   * @return GUI layout
   */
  public UiLayout getUiLayout() {
    return uiLayout;
  }
}
