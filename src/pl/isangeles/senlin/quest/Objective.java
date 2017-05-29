package pl.isangeles.senlin.quest;
/**
 * Class for quest stage objectives
 * @author Isangeles
 *
 */
public class Objective
{
    private ObjectiveType type;
    private String target;
    private boolean complete;
    /**
     * Objective constructor
     * @param type Objective type
     * @param target Objective target
     */
    public Objective(ObjectiveType type, String target)
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
}
