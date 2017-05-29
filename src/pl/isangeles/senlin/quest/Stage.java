package pl.isangeles.senlin.quest;

import java.util.ArrayList;
import java.util.List;
/**
 * Class for quest stages
 * @author Isangeles
 *
 */
public class Stage
{
    private String id;
    private String nextStage;
    private List<Objective> objectives;
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
                break;
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
}
