package pl.isangeles.senlin.dialogue;

import pl.isangeles.senlin.util.TConnector;
/**
 * Class for answer, smallest dialogue part
 * @author Isangeles
 *
 */
public class Answer 
{
	private final String id;
	private final boolean end;
	
	public Answer(String text, boolean end) 
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
