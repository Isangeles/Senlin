package pl.isangeles.senlin.quest;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.data.Log;
/**
 * Class for character quests progress tracking
 * @author Isangeles
 *
 */
public class QuestTracker
{
    private Character character;
    
    public QuestTracker(Character character)
    {
        this.character = character;
    }
    /**
     * Checks if specified objective target matches to one of character quest objectives
     * @param ot ObjectiveTraget such as dialogue answer, killed mob or item
     */
    public void check(ObjectiveTarget ot)
    {
        for(Quest quest : character.getQuests())
        {
            quest.check(ot);
            if(quest.isComplete())
                Log.addInformation(quest.getName() + " completed");
        }
    }
}
