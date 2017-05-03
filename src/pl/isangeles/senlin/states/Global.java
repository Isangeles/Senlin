package pl.isangeles.senlin.states;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.inter.GameCursor;
/**
 * Class for game globals, like player character, targeted characters, etc. 
 * @author Isangeles
 *
 */
public class Global 
{
	private static Character player;
	private static Character tarChar;
	private static float[] cameraPos = {0, 0};
	
	private Global() 
	{}
	/**
	 * Sets character as player's character
	 * @param character Game character
	 */
	public static void setPlayer(Character character)
	{
		player = character;
	}
	/**
	 * Returns current player's character
	 * @return Player's character
	 */
	public static Character getPlayer()
	{
		return player;
	}
	/**
	 * Sets object as target
	 * @param target 
	 */
	public static void setTarget(Object target)
	{
		if(Character.class.isInstance(target))
			Global.tarChar = (Character)target;
		else
			Global.tarChar = null;
	}
	public static void setCamerPos(float x, float y)
	{
		Global.cameraPos[0] = x;
		Global.cameraPos[1] = y;
	}
	/**
	 * Returns currently targeted character
	 * @return Targeted character
	 */
	public static Character getTarChar()
	{
		return Global.tarChar;
	}
	/**
	 * Checks if character other then player character is targeted
	 * @return True if other character then player is targeted, false otherwise
	 */
	public static boolean isOtherCharTar()
	{
		if(tarChar != null)
			return tarChar != player;
		else 
			return false;
	}
	/**
	 * Returns range between player and his target
	 * @return Range between player character and targeted character as integer
	 */
	public static int getRangeFromTar()
	{
		if(tarChar != null)
			return (int)Math.sqrt((player.getPosition()[0]-tarChar.getPosition()[0]) * (player.getPosition()[0]-tarChar.getPosition()[0]) +
					 (player.getPosition()[1]-tarChar.getPosition()[1]) * (player.getPosition()[1]-tarChar.getPosition()[1]));
		else
			return 0;
	}
	
	public static float[] getCameraPos()
	{
		return cameraPos;
	}
	/**
	 * Converts raw x position to in game x position
	 * @param x Position on x-axis
	 * @return World position on x-axis
	 */
	public static float worldX(float x)
	{
		return x + cameraPos[0];
	}
	/**
	 * Converts raw y position to in game y position
	 * @param y Position on y-axis
	 * @return World position on y-axis
	 */
	public static float worldY(float y)
	{
		return y + cameraPos[1];
	}
	/**
	 * Converts world x position to in ui x position
	 * @param x Position on x-axis
	 * @return Ui position on x-axis
	 */
	public static float uiX(float x)
	{
		return x - cameraPos[0];
	}
	/**
	 * Converts world y position to ui y position
	 * @param y Position on y-axis
	 * @return Ui position on y-axis
	 */
	public static float uiY(float y)
	{
		return y - cameraPos[1];
	}
}
