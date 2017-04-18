package pl.isangeles.senlin.inter;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.core.Character;
/**
 * Class for game cursor
 * @author Isangeles
 *
 */
public class Cursor extends InterfaceObject 
{
	private static Character target;
	
	public Cursor(GameContainer gc) throws SlickException, IOException 
	{
		super(GConnector.getInput("ui/cursorBlack.png"), "gameCursor", false, gc);
	}

	public static void setTarget(Object target)
	{
		if(Character.class.isInstance(target))
			Cursor.target = (Character)target;
		else
			Cursor.target = null;
	}
	
	public static Character getTarChar()
	{
		return Cursor.target;
	}
}
