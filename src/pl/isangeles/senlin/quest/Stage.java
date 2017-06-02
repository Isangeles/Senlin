package pl.isangeles.senlin.quest;

import java.util.ArrayList;
import java.util.List;

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
    private List<Objective> objectives;
    private boolean complete;
    /**
     * Stage constructor 
     * @param id Stage ID
     * @param nextStage ID of stage that should be triggered after completing this stage
     * @param objectives List of stage objectives
     */
    public Stage(String id, String nextStage, List<Objective> objectives)
    {
        this.id = id;
        this.nextStage = nextStage;
        info = TConnector.getText("quests", id);
        this.objectives = objectives;
    }
    /**
     * Checks if stage objectives all completed
     * @return Boolean value
     */
    public boolean isComplete()
    {
        for(Objective objecitve : objectives)
        {
            if(!objecitve.isComplete())
            {
                return false;
            }
        }
        return true;
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
    
    public String getInfo()
    {
    	return info;
    }
    
    public void check(ObjectiveTarget ot)
    {
        for(Objective objective : objectives)
        {
            objective.check(ot);
        }
    }
}
