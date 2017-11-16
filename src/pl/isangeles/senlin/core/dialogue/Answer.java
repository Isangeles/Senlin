package pl.isangeles.senlin.core.dialogue;

import java.util.List;

import pl.isangeles.senlin.core.quest.ObjectiveTarget;
import pl.isangeles.senlin.core.quest.Quest;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.core.req.Requirements;
import pl.isangeles.senlin.data.QuestsBase;
import pl.isangeles.senlin.util.TConnector;
/**
 * Class for answer, smallest dialogue part
 * @author Isangeles
 *
 */
public class Answer implements ObjectiveTarget
{
	private final String id;
	private final String to;
	private final boolean end;
	private final Requirements reqs;
	private final String text;
	/**
	 * Answer constructor
	 * @param id Answer text content ID
	 * @param to ID of text to display after this answer, 'end' to end dialogue after this answer
	 * @param end True if dialogue should end after this answer, false otherwise
	 */
	public Answer(String id, String to, boolean end, List<Requirement> reqs) 
	{
		this.id = id;
		this.to = to;
		if(this.to.equals("end"))
			this.end = true;
		else
			this.end = end;
		this.reqs = new Requirements(reqs);
		
		text = TConnector.getDialogueText(id);
	}
	/**
	 * Returns answer ID
	 * @return String with ID
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * Returns ordinal ID of text to display after choosing this answer
	 * @return String with text ID
	 */
	public String getTo()
	{
		return to;
	}
	/**
	 * Returns text corresponding to answer ID
	 * @return String with text
	 */
	public String getText()
	{
		return text;
	}
	/**
	 * Checks if that answer ends dialogue
	 * @return True if dialogue should be ended after this answer, false otherwise
	 */
	public boolean isEnd()
	{
		return end;
	}
	/**
	 * Returns answer requirements
	 * @return Requirements container
	 */
	public Requirements getReqs()
	{
		return reqs;
	}
}
