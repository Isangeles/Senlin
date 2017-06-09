package pl.isangeles.senlin.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import pl.isangeles.senlin.dialogue.Dialogue;
import pl.isangeles.senlin.util.DConnector;
import pl.isangeles.senlin.util.parser.DialogueParser;
/**
 * Static class for dialogues base
 * @author Isangeles
 *
 */
public class DialoguesBase 
{
	private static Map<String, Dialogue> dialogsMap = new HashMap<>();
	private static String dialoguesBaseName;
	/**
	 * Private constructor to prevent initialization
	 */
	private DialoguesBase() {}
	/**
	 * Returns all dialogues for specified NPC
	 * @param npcId NPC ID
	 * @return List with dialogues
	 */
	public static List<Dialogue> getDialogues(String npcId)
	{
		List<Dialogue> dialogues = new ArrayList<>();
		for(Dialogue dialogue : dialogsMap.values())
		{
			if(dialogue.getNpcId().equals(npcId))
				dialogues.add(dialogue);
		}
		return dialogues;
	}
	/**
	 * Returns default dialogue from base
	 * @return Dialogue object
	 */
	public static Dialogue getDefaultDialogue()
	{
		//return dialogsMap.get("default");
		return DialogueParser.getDialogueFromNode(DialogueParser.getDefDialogueNode());
	}
	/**
	 * Sets quest trigger for specific dialogue answer 
	 * @param answerId Dialogue answer ID
	 * @param questId Quest ID
	 */
	public static void setTrigger(String answerId, String questId)
	{
		System.out.println("dBase_req_for//" + answerId);
		for(Dialogue dialogue : dialogsMap.values())
		{
			dialogue.setQTrigger(answerId, questId);
		}
	}
	/**
	 * Loads base
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void load(String dialoguesBaseName) throws ParserConfigurationException, SAXException, IOException
	{
		DialoguesBase.dialoguesBaseName = dialoguesBaseName;
		dialogsMap = DConnector.getDialogueMap(dialoguesBaseName);
	}
	/**
	 * Return current dialogues base name
	 * @return String with base name
	 */
	public static String getBaseName()
	{
		return dialoguesBaseName;
	}
}
