/*
 * Quest.java
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
package pl.isangeles.senlin.quest;

import java.util.ArrayList;
import java.util.List;

import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.inter.TextBlock;
import pl.isangeles.senlin.util.TConnector;
/**
 * Class for game quests
 * @author Isangeles
 *
 */
public class Quest
{
    private String id;
    private String name;
    private String info;
    private String flagOn;
    private List<Stage> stages;
    private Stage currentStage;
    private boolean complete;
    private boolean active;
    /**
     * Quest constructor 
     * @param id Quest ID
     * @param stages List of quest stages
     */
    public Quest(String id, String flag, List<Stage> stages)
    {
        this.id = id;
        this.stages = stages;
        this.flagOn = flag;
        
        name = TConnector.getInfo("quests", id)[0];
        info = TConnector.getInfo("quests", id)[1];
        
        for(Stage stage : stages)
        {
            if(stage.getId().equals(id+"0"))
            	currentStage = stage;
        }   
    }
    /**
     * Changes current stage
     */
    public void nextStage()
    {
        if(!complete && currentStage.isComplete() && currentStage.getNextStage().equals("end"))
        {
            completed();
            return;
        }
        
        for(Stage stage : stages)
        {
            if(stage.getId().equals(currentStage.getNextStage()))
            {
                currentStage = stage;
                break;
            }
        }
    }
    /**
     * Starts quest
     */
    public void start()
    {
    	active = true;
    }
    /**
     * Checks if quest is completed
     * @return Boolean value
     */
    public boolean isComplete()
    {
    	return complete;
    }
    /**
     * Returns quest ID
     * @return String with quest ID
     */
    public String getId()
    {
        return id;
    }
    /**
     * Returns quest name
     * @return String with quest name
     */
    public String getName()
    {
    	return name;
    }
    /**
     * Returns list of flags from quest and completed stages that should be set for character
     * @return List with flags IDs to set
     */
    public List<String> getFlagsToSet()
    {
    	List<String> flags = new ArrayList<>();
    	flags.add(flagOn);
    	for(Stage stage : stages)
    	{
    		if(stage.isComplete())
    			flags.add(stage.getFlagToSet());
    	}
    	return flags;
    }
    /**
     * Returns list of flags from quest and completed stages that should bet removed from character
     * @return List with flags IDs to remove
     */
    public List<String> getFlagsToRemove()
    {
    	List<String> flags = new ArrayList<>();
    	for(Stage stage : stages)
    	{
    		if(stage.isComplete())
    			flags.add(stage.getFlagToRemove());
    	}
    	return flags;
    }
    /**
     * Returns quest info
     * @return String with quest info
     */
    public String[] getInfo()
    {
    	return new String[]{info, currentStage.getInfo()};
    }
    /**
     * Checks if specified objective target meets any current stage objective requirements
     * @param ot Some objective target like dialogue answer, item, character, etc.
     */
    public void check(ObjectiveTarget ot)
    {
        currentStage.check(ot);
        
        if(currentStage.isComplete())
        	nextStage();
    }
    /**
     * Clears all quest flags(stages flags too)
     */
    public void clearFlags()
    {
    	flagOn = "";
    	for(Stage stage : stages)
    	{
    		stage.clearFlags();
    	}
    }
    /**
     * Clears specified flag
     * @param flag Flag ID
     */
    public void clearFlag(String flag)
    {
    	if(flagOn.equals(flag))
    	{
    		flagOn = "";
    		return;
    	}
    	
    	for(Stage stage : stages)
    	{
    		stage.clearFlag(flag);
    		return;
    	}
    }
    /**
     * Checks if quest is completed
     * @return True if quest is completed, false otherwise
     */
    public boolean isCompleted()
    {
    	return complete;
    }
    /**
     * Checks if quest have any flag
     * @return True if quest have any flag, false otherwise
     */
    public boolean hasFlag()
    {
    	if(flagOn != "")
    		return true;
    	
    	for(Stage stage : stages)
    	{
    		if(stage.hasFlag())
    			return true;
    	}
    	
    	return false;
    }
    /**
     * Marks quest as completed
     */
    private void completed()
    {
    	name += "(" + TConnector.getText("ui", "jMenuCmp") + ")";
    	complete = true;
    	active = false;
    }
}
