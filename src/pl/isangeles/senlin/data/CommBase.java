package pl.isangeles.senlin.data;

import java.util.LinkedList;
import java.util.List;

import pl.isangeles.senlin.util.TConnector;
/**
 * Static class for all game communicates
 * @author Isangeles
 *
 */
public class CommBase
{
	private static List<String> commList = new LinkedList<String>();
	private static boolean debugMode;
	/**
	 * Private constructor to prevent initialization
	 */
	private CommBase(){};
	/**
	 * Adds new information to communicates stack
	 * @param information Content of communicate
	 */
	public static void addInformation(String information)
	{
		commList.add(TConnector.getText("ui", "logInfo") + ": " + information);
	}
	/**
	 * Adds new warning to communicates stack
	 * @param warning
	 */
	public static void addWarning(String warning)
	{
		commList.add(TConnector.getText("ui", "logWarn") + ": " + warning);
	}
	/**
	 * Adds new debug message to list
	 * @param debug Debug message
	 */
	public static void addDebug(String debug)
	{
		commList.add(TConnector.getText("ui", "logDebug") + ": " + debug);
	}
	/**
	 * Returns message with specific index
	 * @param index Index of message on list
	 * @return
	 */
	public static String get(int index)
	{
		String message;
		try
		{
			message = commList.get(index) + System.getProperty("line.separator");
		}
		catch(IndexOutOfBoundsException e)
		{
			message = "";
		}
		return message;
	}
	/**
	 * Returns list with all messages
	 * @return LinkedList with messages
	 */
	public static List<String> get()
	{
		List<String> list = new LinkedList<>();
		for(String message : commList)
		{
			if(!debugMode)
			{
				if(!message.startsWith(TConnector.getText("ui", "logDebug")))
					list.add(message);
			}
			else
			{
				list.add(message);
			}
		}
		return list;
	}
	/**
	 * Returns base size
	 * @return Size of base
	 */
	public static int size()
	{
		return commList.size();
	}
	/**
	 * Turns debug mode on or off
	 * @param debugMode True to activate debug mode on false otherwise
	 */
	public static void setDebug(boolean debugMode)
	{
		CommBase.debugMode = debugMode;
	}
}
