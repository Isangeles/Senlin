package pl.isangeles.senlin.quest;

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
    private String flagOn;
    private String flagOff;
    private List<Objective> objectives;
    private boolean complete;
    /**
     * Stage constructor 
     * @param id Stage ID
     * @param nextStage ID of stage that should be triggered after completing this stage
     * @param objectives List of stage objectives
     */
    public Stage(String id, String flagOn, String flagOff, String nextStage, List<Objective> objectives)
    {
        this.id = id;
        this.nextStage = nextStage;
        this.flagOn = flagOn;
        this.flagOff = flagOff;
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
    /**
     * Returns info about this stage
     * @return String with stage info
     */
    public String getInfo()
    {
    	return info;
    }
    /**
     * Returns flag to set ID
     * @return Flag ID
     */
    public String getFlagToSet()
    {
    	return flagOn;
    }
    /**
     * Returns flag to remove ID
     * @return Flag ID
     */
    public String getFlagToRemove()
    {
    	return flagOff;
    }
    /**
     * Checks if specified objective target meets any stage objective requirements
     * @param ot Some objective target like dialogue answer, item, character, etc.
     */
    public void check(ObjectiveTarget ot)
    {
        for(Objective objective : objectives)
        {
            objective.check(ot);
        }
    }
    /**
     * Clears all stage flags
     */
    public void clearFlags()
    {
    	flagOn = "";
    	flagOff = "";
    }
    /**
     * Clears specified stage flag
     * @param flag Flag ID
     */
    public void clearFlag(String flag)
    {
    	if(flagOn.equals(flag))
    	{
    		flagOn = "";
    		return;
    	}
    	if(flagOff.equals(flag))
    	{
    		flagOff = "";
    		return;
    	}
    }
    /**
     * Checks if stage have any flag
     * @return True if stage have any flag, false otherwise
     */
    public boolean hasFlag()
    {
    	if(flagOn != "" || flagOff != "")
    		return true;
    	else
    		return false;
    }
}
