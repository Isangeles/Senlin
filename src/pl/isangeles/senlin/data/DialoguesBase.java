package pl.isangeles.senlin.data;

import java.io.IOException;
import java.util.HashMap;
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
	/**
	 * Private constructor to prevent initialization
	 */
	private DialoguesBase() {}
	
	public static Dialogue getDialog(String npcId)
	{
		return dialogsMap.get(npcId);
	}
	/**
	 * Loads base
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void load() throws ParserConfigurationException, SAXException, IOException
	{
		dialogsMap = DConnector.getDialogueMap("prologue");
	}
}
