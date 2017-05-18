package pl.isangeles.senlin.data;

import java.util.LinkedList;
import java.util.List;

import pl.isangeles.senlin.util.TConnector;
/**
 * Static class for all in-game communicates
 * @author Isangeles
 *
 */
public class Log
{
	private static List<String> commList = new LinkedList<String>();
	private static boolean debugMode;
	/**
	 * Private constructor to prevent initialization
	 */
	private Log(){};
	/**
	 * Adds new information to communicates stack
	 * @param information Content of communicate
	 */
	public static void addInformation(String information)
	{
		clearOld();
		commList.add(information);
	}
	/**
	 * Adds new warning to communicates stack
	 * @param warning
	 */
	public static void addWarning(String warning)
	{
		clearOld();
		commList.add(TConnector.getText("ui", "logWarn") + ": " + warning);
	}
	/**
	 * Adds new debug message to list
	 * @param debug Debug message
	 */
	public static void addDebug(String debug)
	{
		clearOld();
		if(debugMode)
			commList.add(TConnector.getText("ui", "logDebug") + ": " + debug);
	}
	/**
	 * Adds new system message to list
	 * @param systemMsg System message
	 */
	public static void addSystem(String systemMsg)
	{
		clearOld();
		commList.add(TConnector.getText("ui", "logSysMsg") + ": " + systemMsg);
	}
	/**
	 * Adds new speech message to log
	 * @param who Name of speech author
	 * @param speech Content of speech
	 */
	public static void addSpeech(String who, String speech)
	{
		clearOld();
		commList.add(who + " " + TConnector.getText("ui", "logSpeech") + ": " + speech);
	}
	/**
	 * Adds message about character points loss
	 * @param who The author of the message(e.g. character name)
	 * @param value Value deducted from character
	 * @param statName Name of the character statistic from which points were deducted
	 */
	public static void loseInfo(String who, int value, String statName)
	{
		clearOld();
		addInformation(who + " " + TConnector.getText("ui", "logPtsLose") + ": " + value + " " + statName);
	}
    /**
     * Adds message about character points loss
     * @param who The author of the message(e.g. character name)
     * @param value Value
     * @param statName Name of the character statistic from which points were deducted
     */
    public static void loseInfo(String who, String value, String statName)
    {
		clearOld();
        addInformation(who + " " + TConnector.getText("ui", "logPtsLose") + ": " + value + " " + statName);
    }
	/**
	 * Adds message about character points gain
	 * @param who The author of the message(e.g. character name)
	 * @param value Value deducted from character health
	 * @param statName Name of the character statistic to which points were added
	 */
	public static void gainInfo(String who, int value, String statName)
	{
		clearOld();
		addInformation(who + " " + TConnector.getText("ui", "logPtsGain") + ": " + value + " " + statName);
	}
    /**
     * Adds message about character points gain
     * @param who The author of the message(e.g. character name)
     * @param value Value
     * @param statName Name of the character statistic to which points were added
     */
    public static void gainInfo(String who, String value, String statName)
    {
		clearOld();
        addInformation(who + " " + TConnector.getText("ui", "logPtsGain") + ": " + value + " " + statName);
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
		Log.debugMode = debugMode;
		
		if(Log.debugMode)
			Log.addInformation(TConnector.getText("ui", "logDebugOn"));
		else
    		Log.addInformation(TConnector.getText("ui", "logDebugOff"));
	}
	/**
	 * Checks if debug mode is on
	 * @return
	 */
	public static boolean isDebug()
	{
		return debugMode;
	}
	/**
	 * Removes old communicates
	 */
	private static void clearOld()
	{
		if(commList.size() > 25)
			commList.remove(commList.size()-1);
	}
}
