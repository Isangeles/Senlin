/*
 * SavedGame.java
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
package pl.isangeles.senlin.data.save;

import java.util.List;
import java.util.Map;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.SimpleGameObject;
import pl.isangeles.senlin.data.area.Scenario;
import pl.isangeles.senlin.gui.elements.UserInterface;
import pl.isangeles.senlin.util.Position;

/**
 * Class for saved games
 * @author Isangeles
 *
 */
public class SavedGame
{
    private Character player;
    private List<Scenario> scenarios;
    private Scenario activeScenario;
    private Map<String, Integer> bBarLayout;
    private Map<String, Integer[]> invLayout;
    private float[] cameraPos;
    /**
     * Creates saved game to load 
     * @param player Player game character
     * @param scenarios List with saved game scenarios
     * @param activeScenarioId ID of active game scenario
     */
    public SavedGame(Character player, List<Scenario> scenarios, String activeScenarioId, Map<String, Integer> bBarLayout, Map<String, Integer[]> invLayout, 
    		         float[] cameraPos)
    {
        this.player = player;
        this.scenarios = scenarios;
        for(Scenario scenario : scenarios)
        {
            if(scenario.getId().equals(activeScenarioId))
                activeScenario = scenario;
        }
        this.bBarLayout = bBarLayout;
        this.invLayout = invLayout;
        this.cameraPos = cameraPos;
    }
    /**
     * Returns saved player character
     * @return Game character
     */
    public Character getPlayer()
    {
        return player;
    }
    /**
     * Returns list with saved scenarios
     * @return List with game areas scenarios
     */
    public List<Scenario> getScenarios()
    {
        return scenarios;
    }
    /**
     * Returns active saved scenario
     * @return Active game area scenario
     */
    public Scenario getActiveScenario()
    {
        return activeScenario;
    }
    
    public Map<String, Integer> getBBarLayout()
    {
    	return bBarLayout;
    }
    
    public Map<String, Integer[]> getInvLayout()
    {
    	return invLayout;
    }
    
    public float[] getCameraPos()
    {
    	return cameraPos;
    }
}
