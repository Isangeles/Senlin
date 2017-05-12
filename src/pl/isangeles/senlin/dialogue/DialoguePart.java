package pl.isangeles.senlin.dialogue;

import java.util.ArrayList;
import java.util.List;

import pl.isangeles.senlin.util.TConnector;
/**
 * Class for dialogue parts, contains id corresponding to text in current lang directory and answers 
 * @author Isangeles
 *
 */
public class DialoguePart 
{
	private final String id;
	private final String on;
	private final List<Answer> answers;
	
	public DialoguePart(String id, String on, List<Answer> answers) 
	{
		this.id = id;
		this.on = on;
		this.answers = answers;
	}

	public String getTrigger()
	{
		return on;
	}
	
	public String getText()
	{
		return TConnector.getDialogueText(id);
	}
	
	public List<Answer> getAnswers()
	{
		return answers;
	}
}
