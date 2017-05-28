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
    
    public Stage(String id, String nextStage, List<Objective> objectives)
    {
        this.id = id;
        this.nextStage = nextStage;
        this.objectives = objectives;
    }
    
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
    
    public String getNextStage()
    {
        return nextStage;
    }
    
    public String getId()
    {
        return id;
    }
}
