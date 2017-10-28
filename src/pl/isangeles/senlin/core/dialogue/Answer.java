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
	/**
	 * Answer constructor
	 * @param text Answer text content ID
	 * @param to ID of text to display after this answer
	 * @param end If Dialogue should end after this answer
	 */
	public Answer(String text, String to, boolean end, List<Requirement> reqs) 
	{
		this.id = text;
		this.to = to;
		this.end = end;
		this.reqs = new Requirements(reqs);
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
	 * Returns ID of text to display after this answer
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
		return TConnector.getDialogueText(id);
	}
	/**
	 * Checks if that answer ends dialogue
	 * @return True if dialogue should be ended after this answer, false otherwise
	 */
	public boolean isEnd()
	{
		return end;
	}
	
	public Requirements getReqs()
	{
		return reqs;
	}
}
