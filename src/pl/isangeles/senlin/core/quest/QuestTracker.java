/*
 * QuestTracker.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String, String> questsToStart = new HashMap<>();
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
    	//List<Quest> qCompleted = new ArrayList<>();
        for(Quest quest : character.getQuests())
        {
            quest.check(ot);
            //if(quest.isComplete())
                //qCompleted.add(quest);
        }
        //character.getQuests().markAsCompleted(qCompleted);
        for(String questId : questsToStart.keySet())
        {
        	if(questsToStart.get(questId).equals(ot.getId()))
        	{
        		character.startQuest(QuestsBase.get(questId));
        		startedQuests.add(questId);
        		break;
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
