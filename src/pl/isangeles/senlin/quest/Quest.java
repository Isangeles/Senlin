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
    private List<Stage> stages;
    private Stage currentStage;
    private boolean complete;
    private boolean active;
    /**
     * Quest constructor 
     * @param id Quest ID
     * @param stages List of quest stages
     */
    public Quest(String id, List<Stage> stages)
    {
        this.id = id;
        this.stages = stages;
        
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
        if(currentStage.isComplete() && currentStage.getNextStage().equals("end"))
        {
            completed();
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
     * Returns quest info
     * @return String with quest info
     */
    public String[] getInfo()
    {
    	return new String[]{info, currentStage.getInfo()};
    }
    
    public void check(ObjectiveTarget ot)
    {
        currentStage.check(ot);

        if(currentStage.isComplete())
            nextStage();
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
