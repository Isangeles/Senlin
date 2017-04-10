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
		commList.add(information);
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
		if(debugMode)
			commList.add(TConnector.getText("ui", "logDebug") + ": " + debug);
	}
	/**
	 * Adds message about character points loss
	 * @param value Value deducted from character health
	 * @param statName Name of the character statistic from which points were deducted
	 */
	public static void loseInfo(int value, String statName)
	{
		addInformation(TConnector.getText("ui", "logPtsLose") + ": " + value + " " + statName);
	}
	/**
	 * Adds message about character points gain
	 * @param value Value deducted from character health
	 * @param statName Name of the character statistic to which points were added
	 */
	public static void gainInfo(int value, String statName)
	{
		addInformation(TConnector.getText("ui", "logPtsGain") + ": " + value + " " + statName);
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
		return commList;
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
		
		if(CommBase.debugMode)
			CommBase.addInformation(TConnector.getText("ui", "logDebugOn"));
		else
    		CommBase.addInformation(TConnector.getText("ui", "logDebugOff"));
	}
}
