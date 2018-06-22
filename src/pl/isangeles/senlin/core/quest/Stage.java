/*
 * Stage.java
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

import java.util.List;

import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.character.Character;
/**
 * Class for quest stages
 * @author Isangeles
 *
 */
public class Stage
{
    private final String id;
    private String nextStage;
    private final String info;
    private final List<String> flagsOnStart;
    private final List<String> flagsOffStart;
    private final List<String> flagsOnEnd;
    private final List<String> flagsOffEnd;
    private final List<Objective> objectives;
    private final boolean startStage;
    /**
     * Stage constructor 
     * @param id Stage ID
     * @param nextStage ID of stage that should be triggered after completing this stage
     * @param objectives List of stage objectives
     */
    public Stage(String id, List<String> flagsOnStart, List<String> flagsOffStart, List<String> flagsOnEnd, List<String> flagsOffEnd,
    			 String nextStage, List<Objective> objectives, boolean startStage)
    {
        this.id = id;
        this.nextStage = nextStage;
        this.flagsOnStart = flagsOnStart;
        this.flagsOffStart = flagsOffStart;
        this.flagsOnEnd = flagsOnEnd;
        this.flagsOffEnd = flagsOffEnd;
        info = TConnector.getTextFromChapter("quests", id);
        this.objectives = objectives;
        this.startStage = startStage;
        
        //Auto flag
        this.flagsOnStart.add(id);
        this.flagsOffEnd.add(id);
    }
    /**
     * Checks if stage objectives all completed
     * @return Boolean value
     */
    public boolean isComplete()
    {
    	for(Objective objective : objectives)
    	{
            if(objective.isComplete() && objective.isFinisher())
            {
            	if(!objective.getToId().equals("")) //TODO maybe some more validation needed
            		nextStage = objective.getToId();
            	return true;
            }
    	}
    	
        for(Objective objective : objectives)
        {
            if(!objective.isComplete())
	            return false;
        }
        
        return true;
    }
    /**
     * Starts stage for specified character
     * @param character Game character
     */
    public void start(Character character)
    {
    	character.getFlags().addAll(flagsOnStart);
    	character.getFlags().removeAll(flagsOffStart);
    }
    /**
     * Completes stage for specified character
     * @param character Game character
     */
    public void complete(Character character)
    {
    	character.getFlags().addAll(flagsOnEnd);
    	character.getFlags().removeAll(flagsOffEnd);
    }
    /**
     * Returns next stage ID
     * @return String with next stage ID
     */
    public String getNextStage()
    {
        return nextStage;
    }
    /**
     * Returns stage ID
     * @return String with stage ID
     */
    public String getId()
    {
        return id;
    }
    /**
     * Returns info about this stage
     * @return String with stage info
     */
    public String getInfo()
    {
    	return info;
    }
    /**
     * Returns list with all objectives of this stage
     * @return List with objectives
     */
    public List<Objective> getObjectives()
    {
    	return objectives;
    }
    /**
     * Checks if this stage is start stage
     * @return True if this start is start stage, false otherwise
     */
    public boolean isStartStage()
    {
    	return startStage;
    }
    /**
     * Checks if specified objective target meets any stage objective requirements
     * @param ot Some objective target like dialogue answer, item, character, etc.
     */
    public void check(ObjectiveTarget ot)
    {
        for(Objective objective : objectives)
        {
            objective.check(ot);
        }
    }
    /**
     * Checks if stage have any flag
     * @return True if stage have any flag, false otherwise
     */
    public boolean hasFlag()
    {
    	if(!flagsOnEnd.isEmpty() || !flagsOffEnd.isEmpty())
    		return true;
    	else
    		return false;
    }
}
