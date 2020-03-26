/*
 * Chapter.java
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
package pl.isangeles.senlin.core;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.data.ScenariosBase;
import pl.isangeles.senlin.data.area.Area;
import pl.isangeles.senlin.data.area.Scenario;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.data.save.SavedScenario;

/**
 * Class for game chapters
 *
 * @author Isangeles
 */
public class Chapter implements SaveElement {
  private String id;
  private List<SavedScenario> savedScenarios = new ArrayList<>();
  private List<Scenario> loadedScenarios = new ArrayList<>();
  private Scenario activeScenario;
  /**
   * Chapter constructor
   *
   * @param id Chapter ID
   * @param startScenario Start scenario
   */
  public Chapter(String id, String startScenario, GameContainer gc) {
    this.id = id;
    setScenario(startScenario, gc);
  }
  /**
   * Chapter constructor
   *
   * @param id Chapter ID
   * @param scenarios List of scenarios
   * @param startScenario Start scenario
   * @throws FontFormatException
   * @throws IOException
   * @throws SlickException
   * @throws DOMException
   * @throws NumberFormatException
   */
  public Chapter(
      String id, List<SavedScenario> savedScenarios, Scenario startScenario, GameContainer gc)
      throws NumberFormatException, DOMException, SlickException, IOException, FontFormatException {
    this.id = id;
    // this.loadedScenarios = scenarios;
    this.savedScenarios = savedScenarios;
    /*
    for(SavedScenario s : savedScenarios)
    {
    	if(s.getScenarioId().equals(startScenario))
    	{
    		Scenario scenario = s.load(gc);
    		activeScenario = scenario;
    		return;
    	}
    }
    */
    activeScenario = startScenario;
    loadedScenarios.add(activeScenario);
    // If start scenario was not found
    // activeScenario = savedScenarios.get(0).load(gc);
  }

  public String getId() {
    return id;
  }
  /**
   * Returns scenario with specified ID from scenarios list
   *
   * @param scenarioId String with desired scenario ID
   * @return Scenario with specified ID or null if not such scenario was found
   */
  public Scenario getScenario(String scenarioId, GameContainer gc) {
    final String scenarioID = scenarioId;
    for (SavedScenario s : savedScenarios) {
      if (s.getScenarioId().equals(scenarioID)) {
        try {
          return s.load(gc);
        } catch (NumberFormatException
            | DOMException
            | SlickException
            | IOException
            | FontFormatException e) {
          System.err.println(
              "chapter_get_scenario_load_saved_scenario_fail-msg//" + e.getMessage());
          break;
        }
      }
    }
    for (Scenario scenario : loadedScenarios) {
      if (scenario.getId().equals(scenarioID)) return scenario;
    }

    Scenario scenario = ScenariosBase.getScenario(scenarioID);
    if (scenario != null) loadedScenarios.add(scenario);
    return scenario;
  }
  /**
   * Checks if this chapter contains scenario with specified ID
   *
   * @param scenarioId String with scenario ID
   * @return True if this character contains scenario with specified ID
   */
  public boolean containsScenario(String scenarioId) {
    if (activeScenario.getId().equals(scenarioId)) return true;
    else {
      for (Scenario scenario : loadedScenarios) {
        if (scenario.getId().equals(scenarioId)) return true;
      }

      for (SavedScenario s : savedScenarios) {
        if (s.getScenarioId().equals(scenarioId)) return true;
      }
    }
    return false;
  }
  /**
   * Returns active scenario
   *
   * @return Active chapter scenario
   */
  public Scenario getActiveScenario() {
    return activeScenario;
  }
  /**
   * Returns all loaded(visited) scenarios of this chapter
   *
   * @return List with scenarios
   */
  public List<Scenario> getScenarios() {
    return loadedScenarios;
  }
  /**
   * Returns character with specified ID from all spawned character in this chapter
   *
   * @param serial Character serial ID
   * @return Character with specified serial ID or null if no character with such serial ID found
   */
  public Character getCharacter(String serial) {
    for (Scenario scenario : loadedScenarios) {
      for (Area area : scenario.getAreas()) {
        for (Character character : area.getCharacters()) {
          if (character.getSerialId().equals(serial)) return character;
        }
      }
    }
    return null;
  }
  /**
   * Returns targetable object with specified ID from all spawned object in this chapter
   *
   * @param tObId Targetable object ID
   * @return Targetable object with specified ID
   */
  public Targetable getTObject(String tObId) {
    if (tObId.equals("player")) tObId = "player_0";

    for (Scenario scenario : loadedScenarios) {
      for (Area area : scenario.getAreas()) {
        for (Character character : area.getCharacters()) {
          if (character.getSerialId().equals(tObId)) return character;
        }

        for (TargetableObject object : area.getObjects()) {
          if (object.getSerialId().equals(tObId)) return object;
        }
      }
    }
    return null;
  }
  /**
   * Removes specified targetable object from chapter
   *
   * @param obToRemove Targetable object to remove
   * @return True if specified object was successfully removed, false otherwise
   */
  public boolean removeTObject(Targetable obToRemove) {
    for (Scenario scenario : loadedScenarios) {
      for (Area area : scenario.getAreas()) {
        for (Character character : area.getCharacters()) {
          if (character == obToRemove) {
            if (area.getCharacters().remove(obToRemove)) {
              character.setArea(null);
              return true;
            } else return false;
          }
        }

        for (TargetableObject object : area.getObjects()) {
          if (object == obToRemove) return area.getObjects().remove(obToRemove);
        }
      }
    }
    return false;
  }
  /**
   * Sets scenario with specified ID as active scenario
   *
   * @param scenarioId Scenario ID
   * @return True if scenario with specified ID was successfully set as active scenario, false
   *     otherwise
   */
  public boolean setScenario(String scenarioId, GameContainer gc) {
    Scenario scenario = getScenario(scenarioId, gc);
    if (scenario != null) {
      activeScenario = scenario;
      return true;
    } else return false;
  }

  /* (non-Javadoc)
   * @see pl.isangeles.senlin.data.save.SaveElement#getSave(org.w3c.dom.Document)
   */
  @Override
  public Element getSave(Document doc) {
    Element chapterE = doc.createElement("chapter");
    chapterE.setAttribute("id", id);

    Element scenariosE = doc.createElement("scenarios");
    for (Scenario scenario : loadedScenarios) {
      scenariosE.appendChild(scenario.getSave(doc));
    }
    chapterE.appendChild(scenariosE);

    return chapterE;
  }

  @Override
  public String toString() {
    return id;
  }
}
