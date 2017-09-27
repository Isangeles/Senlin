package pl.isangeles.senlin.core.dialogue;

import pl.isangeles.senlin.core.quest.ObjectiveTarget;
import pl.isangeles.senlin.core.quest.Quest;
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
	private final boolean end;
	/**
	 * Answer constructor
	 * @param text Answer text content ID
	 * @param qOn ID of quest triggered on answer selection 
	 * @param end If Dialogue should end after this answer
	 */
	public Answer(String text, String qOn, boolean end) 
	{
		this.id = text;
		this.end = end;
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
}
