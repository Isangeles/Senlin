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
    
    public Objective(ObjectiveType type, String target)
    {
        this.type = type;
        this.target = target;
    }
    
    public void complete()
    {
        complete = true;
    }
    
    public boolean isComplete()
    {
        return complete;
    }
}
