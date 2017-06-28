package pl.isangeles.senlin.core.item;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Action;
import pl.isangeles.senlin.util.TConnector;
/**
 * Class for error items
 * @author Isangeles
 *
 */
public class ErrorItem extends Misc 
{

	public ErrorItem(String id, GameContainer gc)throws SlickException, IOException, FontFormatException 
	{
		super("error" + id, 0, false, "unknown.png", new Action(), gc);
		name = TConnector.getText("items", "erorrItem-name");
		info = TConnector.getText("items", "errorItem-info" + " " + id);
	}

}
