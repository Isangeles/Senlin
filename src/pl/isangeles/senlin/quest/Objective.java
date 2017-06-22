package pl.isangeles.senlin.quest;

import pl.isangeles.senlin.data.Log;

/**
 * Class for quest stage objectives
 * @author Isangeles
 *
 */
public class Objective
{
    private ObjectiveType type;
    private String target;
    private int reqAmount;
    private int amount;
    private boolean complete;
    /**
     * Objective constructor
     * @param type Objective type
     * @param target Objective target
     */
    public Objective(ObjectiveType type, String target, int reqAmount)
    {
        this.type = type;
        this.target = target;
    }
    /**
     * Completes objective
     */
    public void complete()
    {
        complete = true;
    }
    /**
     * Checks if objective is completed
     * @return Boolean value
     */
    public boolean isComplete()
    {
        return complete;
    }
    
    public void check(ObjectiveTarget ot)
    {
        if(target.equals(ot.getId()))
            amount ++;
        
        if(amount >= reqAmount)
        	complete = true;
    }
}
