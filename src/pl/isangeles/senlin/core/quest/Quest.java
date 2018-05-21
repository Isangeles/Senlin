/*
 * Quest.java
 * 
 * Copyright 2017-2018 Dariusz Sikora <darek@pc-solus>
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
package pl.isangeles.senlin.core.quest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.data.save.SaveElement;
import pl.isangeles.senlin.gui.ScrollableContent;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.core.character.Character;
/**
 * Class for game quests
 * @author Isangeles
 *
 */
public class Quest implements ScrollableContent, SaveElement
{
    private String id;
    private String name;
    private String info;
    private List<String> flagsOnStart;
    private List<String> flagsOffStart;
    private List<String> flagsOnEnd;
    private List<String> flagsOffEnd;
    private List<Stage> stages;
    private Stage currentStage;
    private boolean complete;
    private boolean active;
    private Character owner;
    /**
     * Quest constructor 
     * @param id Quest ID
     * @param stages List of quest stages
     */
    public Quest(String id, List<String> flagsOnStart, List<String> flagsOffStart, List<String> flagsOnEnd, List<String> flagsOffEnd, List<Stage> stages)
    {
        this.id = id;
        this.stages = stages;
        this.flagsOnStart = flagsOnStart;
        this.flagsOffStart = flagsOffStart;
        this.flagsOnEnd = flagsOnEnd;
        this.flagsOffEnd = flagsOffEnd;
        try
        {
        	name = TConnector.getInfoFromChapter("quests", id)[0];
            info = TConnector.getInfoFromChapter("quests", id)[1];
        }
        catch(IndexOutOfBoundsException e)
        {
        	info = "quests_lang_file_corrupted";
        }
        
        for(Stage stage : stages)
        {
            if(stage.isStartStage())
            	currentStage = stage;
        }   
        if(currentStage == null)
        {
            Log.addSystem("quest_constructor_warning///No start stage for " + id);
            currentStage = stages.get(0);
        }
    }
    /**
     * Changes current stage
     */
    private void nextStage()
    {
    	if(active)
    	{
    		currentStage.complete(owner);
            if(!complete && currentStage.isComplete() && currentStage.getNextStage().equals("end"))
            {
                complete();
                return;
            }
            
            for(Stage stage : stages)
            {
                if(stage.getId().equals(currentStage.getNextStage()))
                {
                	stage.start(owner);
                    currentStage = stage;
                    break;
                }
            }
    	}
    }
    /**
     * Starts quest
     * @param character Game character
     * @return True was successfully started, false otherwise
     */
    public boolean start(Character character)
    {
    	if(character != null)
    	{
        	active = true;
        	owner = character;
        	owner.getFlags().addAll(flagsOnStart);
        	owner.getFlags().removeAll(flagsOffStart);
        	currentStage.start(owner);
        	return true;
    	}
    	else
    		return false;
    }
    /**
     * Sets stage with specified ID as current stage of this quest
     * @param stageId String with quest stage ID
     */
    public void setStage(String stageId)
    {
        for(Stage stage : stages)
        {
            if(stage.getId().equals(stageId))
                currentStage = stage;
        }
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
     * Returns current stage ID
     * @return String with current stage ID
     */
    public String getCurrentStageId()
    {
    	return currentStage.getId();
    }
    /**
     * Returns list with current stage objectives
     * @return List with objectives
     */
    public List<Objective> getCurrentObjectives()
    {
    	return currentStage.getObjectives();
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
        if(active)
        {
        	currentStage.check(ot);
            
            if(currentStage.isComplete())
            {
            	nextStage();
            }
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
    	if(!flagsOnStart.isEmpty())
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
    public void complete()
    {
    	if(active && owner != null)
    	{
    		owner.getFlags().addAll(flagsOnEnd);
    		owner.getFlags().removeAll(flagsOffEnd);
    		name += "(" + TConnector.getText("ui", "jMenuCmp") + ")";
        	complete = true;
        	active = false;
        	Log.addSystem("q_complete_id:" + id);
    	}
    }
    
    public boolean equals(Quest q)
    {
    	return id.equals(q.getId());
    }
    /**
     * Sets quest active or inactive
     * @param active True to set quest active, false to set inactive
     */
    public void setActive(boolean active)
    {
    	this.active = active;
    }
    /**
     * Sets specified game character as owner of this quest
     * @param character Game character
     */
    public void setOwner(Character character)
    {
    	owner = character;
    }
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.data.save.SaveElement#getSave(org.w3c.dom.Document)
	 */
	@Override
	public Element getSave(Document doc) 
	{
		Element questE = doc.createElement("quest");
		questE.setAttribute("id", id);
		questE.setAttribute("stage", currentStage.getId());
		for(Objective ob : currentStage.getObjectives())
		{
			questE.appendChild(ob.getSave(doc));
		}
		return questE;
	}
}
