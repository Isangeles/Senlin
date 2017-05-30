package pl.isangeles.senlin.quest;

import java.util.ArrayList;
import java.util.List;

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
    private List<Stage> stages;
    private Stage currentStage;
    private boolean complete;
    /**
     * Quest constructor 
     * @param id Quest ID
     * @param stages List of quest stages
     */
    public Quest(String id, List<Stage> stages)
    {
        this.id = id;
        this.stages = stages;
        
        name = TConnector.getText("quests", id);
        
        for(Stage stage : stages)
        {
            if(stage.getId().equals(0))
                currentStage = stage;
        }
    }
    /**
     * Changes current stage
     */
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
}
