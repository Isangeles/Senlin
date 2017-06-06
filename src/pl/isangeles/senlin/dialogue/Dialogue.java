package pl.isangeles.senlin.dialogue;

import java.util.ArrayList;
import java.util.List;

import pl.isangeles.senlin.data.Log;
/**
 * Class for characters dialogues
 * @author Isangeles
 *
 */
public class Dialogue 
{
	private final String id;
	private final String npcId;
	private final String flagReq;
	private final List<DialoguePart> parts;
	private DialoguePart currentStage;
	/**
	 * Dialogue constructor
	 * @param id Dialogue unique ID
	 * @param npcId NPC ID holding that dialogue 
	 * @param parts List with all parts of this dialogue
	 */
	public Dialogue(String id, String npcId, String flagReq, List<DialoguePart> parts) 
	{
		this.id = id;
		this.npcId = npcId;
		this.flagReq = flagReq;
		this.parts = parts;
		currentStage = getPart("start");
	}
	/**
	 * Returns text of current dialogue part
	 * @return String with dialogue part text
	 */
	public String getText()
	{
		return currentStage.getText();
	}
	/**
	 * Returns all answers on current dialogue part
	 * @return List with all answers for current dialogue
	 */
	public List<Answer> getAnswers()
	{
		return currentStage.getAnswers();
	}
	/**
	 * Changes current dialogue stage to answer-associated dialog part 
	 * @param answer Some answer
	 */
	public void answerOn(Answer answer)
	{
		currentStage = getPart(answer.getId());
	}
	/**
	 * Resets dialogue
	 */
	public void reset()
	{
		currentStage = getPart("start");
	}
	
	public String getId()
	{
		return id;
	}
	
	public String getNpcId()
	{
		return npcId;
	}
	
	public String getReqFlag()
	{
		return flagReq;
	}
	/**
	 * Get dialogue part corresponding to specified trigger
	 * @param trigger Answer id
	 * @return Dialog part corresponding to trigger
	 */
	private DialoguePart getPart(String trigger)
	{
		for(DialoguePart dp : parts)
		{
			if(dp.getTrigger().equals(trigger))
				return dp;
		}
		
		for(DialoguePart dp : parts)
		{
			if(dp.getTrigger().equals("error01"))
				return dp;
		}
		
		List<Answer> aList = new ArrayList<>();
		aList.add(new Answer("bye01", "", true));
		return new DialoguePart("err02", "error02", aList);
	}

}
