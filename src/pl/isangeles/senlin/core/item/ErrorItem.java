package pl.isangeles.senlin.core.item;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.action.Action;
import pl.isangeles.senlin.core.action.EffectAction;
import pl.isangeles.senlin.util.TConnector;
/**
 * Class for error items
 * @author Isangeles
 *
 */
public class ErrorItem extends Misc 
{
	/**
	 * Error item constructor
	 * @param id Error ID
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public ErrorItem(String id, GameContainer gc)throws SlickException, IOException, FontFormatException 
	{
		super("error" + id, 0, 0, false, false, "unknown.png", new EffectAction(), gc);
		name = TConnector.getText("items", "erorrItem-name");
		info = TConnector.getText("items", "errorItem-info" + " " + id);
	}

}
