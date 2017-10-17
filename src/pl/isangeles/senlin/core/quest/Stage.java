/*
 * Stage.java
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
package pl.isangeles.senlin.core.quest;

import java.io.File;
import java.util.List;

import pl.isangeles.senlin.core.Module;
import pl.isangeles.senlin.util.TConnector;
/**
 * Class for quest stages
 * @author Isangeles
 *
 */
public class Stage
{
    private String id;
    private String nextStage;
    private String info;
    private String flagOn;
    private String flagOff;
    private List<Objective> objectives;
    private boolean complete;
    /**
     * Stage constructor 
     * @param id Stage ID
     * @param nextStage ID of stage that should be triggered after completing this stage
     * @param objectives List of stage objectives
     */
    public Stage(String id, String flagOn, String flagOff, String nextStage, List<Objective> objectives)
    {
        this.id = id;
        this.nextStage = nextStage;
        this.flagOn = flagOn;
        this.flagOff = flagOff;
        info = TConnector.getTextFromChapter("quests", id);
        this.objectives = objectives;
    }
    /**
     * Checks if stage objectives all completed
     * @return Boolean value
     */
    public boolean isComplete()
    {
        boolean complete = true;
        for(Objective objecitve : objectives)
        {
            if(!objecitve.isComplete())
            {
                complete = false;
            }
            else
            {
                if(objecitve.isFinisher())
                    return true;
            }
        }
        return complete;
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
     * Returns flag to set ID
     * @return Flag ID
     */
    public String getFlagToSet()
    {
    	return flagOn;
    }
    /**
     * Returns flag to remove ID
     * @return Flag ID
     */
    public String getFlagToRemove()
    {
    	return flagOff;
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
     * Clears all stage flags
     */
    public void clearFlags()
    {
    	flagOn = "";
    	flagOff = "";
    }
    /**
     * Clears specified stage flag
     * @param flag Flag ID
     */
    public void clearFlag(String flag)
    {
    	if(flagOn.equals(flag))
    	{
    		flagOn = "";
    		return;
    	}
    	if(flagOff.equals(flag))
    	{
    		flagOff = "";
    		return;
    	}
    }
    /**
     * Checks if stage have any flag
     * @return True if stage have any flag, false otherwise
     */
    public boolean hasFlag()
    {
    	if(flagOn != "" || flagOff != "")
    		return true;
    	else
    		return false;
    }
}
