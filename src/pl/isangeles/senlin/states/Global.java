package pl.isangeles.senlin.states;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.inter.Cursor;
/**
 * Class for game globals, like player character, targeted characters, etc. 
 * @author Isangeles
 *
 */
public class Global 
{
	private static Character player;
	private static Character tarChar;
	
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
	/**
	 * Returns currently targeted character
	 * @return Targeted character
	 */
	public static Character getTarChar()
	{
		return Global.tarChar;
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
}
