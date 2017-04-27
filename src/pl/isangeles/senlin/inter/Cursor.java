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
	public Cursor(GameContainer gc) throws SlickException, IOException 
	{
		super(GConnector.getInput("ui/cursorBlack.png"), "gameCursor", false, gc);
	}
}
