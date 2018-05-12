/*
 * QuestTracker.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.data.QuestsBase;
/**
 * Class for character quests progress tracking
 * @author Isangeles
 *
 */
public class QuestTracker
{
    private Character character;
    private Map<String, String> questsToStart = new HashMap<>(); // key: quest ID, value: trigger ID
    private List<String> startedQuests = new ArrayList<>();
    /**
     * Quest tracker constructor
     * @param character Game character with quests for quest tracker 
     */
    public QuestTracker(Character character)
    {
        this.character = character;
    }
    /**
     * Checks if specified objective target matches to one of character quest objectives,
     * also checks if specified objective target is trigger of one of quests to start
     * @param ot ObjectiveTraget such as dialogue answer, killed mob or item
     */
    public void check(ObjectiveTarget ot)
    {
    	if(ot != null)
    	{
    		//Log.addSystem("qtrack check:" + ot.getId()); //TEST
            for(Quest quest : character.getQuests())
            {
            	//Log.addSystem("qtrack q check:" + quest.getId()); //TEST
                quest.check(ot);
            }
            for(String questId : questsToStart.keySet())
            {
            	//Log.addSystem("check q: " + questId + " trigger:" + ot.getId()); //TEST
            	String qTrigger = questsToStart.get(questId);
            	if(qTrigger.equals(ot.getId()) || qTrigger.equals(ot.getObjectiveTargetId()))
            	{
            		character.startQuest(QuestsBase.get(questId));
            		startedQuests.add(questId);
            		break;
            	}
            }
    	}
        for(String startedQuestId : startedQuests)
        {
        	questsToStart.remove(startedQuestId);
        }
    }
    /**
     * Adds quest to start on specified trigger
     * @param trigger Trigger ID, eg. dialogue line ID, character ID or item ID
     * @param questId Quest to start on specified trigger
     */
    public void addQuestToStart(Quest quest, String trigger)
    {
    	if(trigger.equals("now"))
    		character.startQuest(quest);
    	else
    		questsToStart.put(quest.getId(), trigger);
    }
}
