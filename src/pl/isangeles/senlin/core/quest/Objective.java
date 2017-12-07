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
    private boolean finisher;
    private String to;
    /**
     * Objective constructor
     * @param type Objective type
     * @param target Objective target
     * @param reqAmount Required amount of specified target
     * @param finisher If quest stage should be finished after completing this objective
     * @param to String with ID of next stage after completing this objective 
     */
    public Objective(ObjectiveType type, String target, int reqAmount, boolean finisher, String to)
    {
        this.type = type;
        this.target = target;
        if(reqAmount == 0)
        	this.reqAmount = 1;
        else
            this.reqAmount = reqAmount;
        this.finisher = finisher;
        this.to = to;
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
     * Checks if after completing this objective whole stage should be completed
     * @return True if this objective is stage finisher or false otherwise
     */
    public boolean isFinisher()
    {
        return finisher;
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
     * Returns ID of next stage
     * @return String with stage ID
     */
    public String getToId()
    {
    	return to;
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
