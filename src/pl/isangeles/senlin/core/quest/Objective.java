package pl.isangeles.senlin.core.quest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.data.save.SaveElement;
/**
 * Class for quest stage objectives, smallest quest part
 * @author Isangeles
 *
 */
public class Objective implements SaveElement
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
        if(reqAmount == 0)
        	this.reqAmount = 1;
        else
            this.reqAmount = reqAmount;
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
    /**
     * Checks if specified objective target matches to this objective target
     * @param ot Some ObjectiveTarget
     */
    public void check(ObjectiveTarget ot)
    {
        if(target.equals(ot.getId()))
        {
        	amount ++;
        	if(amount >= reqAmount)
            	complete = true;
        }
    }
    /**
     * Returns current objective progress
     * @return Integer with current progress
     */
    public int getCurrentAmount()
    {
    	return amount;
    }
    /**
     * Returns required amount of objective targets to complete this objective
     * @return Integer with required amount of objective targets
     */
    public int getReqAmount()
    {
    	return reqAmount;
    }
    /**
     * Sets objective complete or incomplete
     * @param complete True to set objective as complete, false otherwise
     */
    public void setComplete(boolean complete)
    {
        this.complete = complete;
    }
    /**
     * Sets objective targets amount
     * @param amount Value for current amount of targets 
     */
    public void setAmount(int amount)
    {
        this.amount = amount;
    }
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.data.save.SaveElement#getSave(org.w3c.dom.Document)
	 */
	@Override
	public Element getSave(Document doc) 
	{
		Element objectiveE = doc.createElement("objective");
		objectiveE.setAttribute("complete", complete+"");
		objectiveE.setTextContent(amount+"/"+reqAmount);
		return objectiveE;
	}
}
