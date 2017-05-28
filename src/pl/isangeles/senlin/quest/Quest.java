package pl.isangeles.senlin.quest;

import java.util.ArrayList;
import java.util.List;
/**
 * Class for game quests
 * @author Isangeles
 *
 */
public class Quest
{
    private String id;
    private List<Stage> stages;
    private Stage currentStage;
    private boolean complete;
    
    public Quest(String id, List<Stage> stages)
    {
        this.id = id;
        this.stages = stages;
        for(Stage stage : stages)
        {
            if(stage.getId().equals(0))
                currentStage = stage;
        }
    }
    
    public void nextStage()
    {
        if(currentStage.getNextStage().equals("end"))
        {
            complete = true;
            return;
        }
        
        for(Stage stage : stages)
        {
            if(stage.getId() == currentStage.getNextStage())
            {
                currentStage = stage;
                break;
            }
        }
    }
}
