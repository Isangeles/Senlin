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
