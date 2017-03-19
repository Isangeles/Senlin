package pl.isangeles.senlin.inter;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
/**
 * Class for game warnings, extends message class
 * @author Isangeles
 *
 */
public class Warning extends Message 
{
	Button abort;
	
	boolean cancel;
	boolean accept;
	boolean undecided;
	/**
	 * Warning constructor
	 * @param gc Game container for superclass
	 * @param textMessage Text for warning
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Warning(GameContainer gc, String textMessage) throws SlickException, IOException, FontFormatException 
	{
		super(gc, textMessage);
		
		abort = new Button(GConnector.getInput("button/buttonNo.png"), "bWarnNo", false, "", gc);
		cancel = true;
	}
	
	@Override
	public void draw(float x, float y)
	{
		super.draw(x, y);
		abort.draw(x+50, y+super.getBaseHeight()-50);
	}
	
	@Override
	public void show(String textWarning)
	{
		super.show(textWarning);
	}
	
	@Override
	public void close()
	{
		undecided = false;
		super.close();
	}
	
	@Override
	public void open()
	{
		undecided = true;
	}
	
	public boolean isCancel()
	{
		return cancel;
	}
	
	public boolean isAccept()
	{
		return accept;
	}
	
	public boolean isUndecided()
	{
		return undecided;
	}
	
	@Override
	public void mouseReleased(int button, int x, int y)
	{
		if(button == Input.MOUSE_LEFT_BUTTON && abort.isMouseOver())
		{
			cancel = true;
			close();
		}
		else if(button == Input.MOUSE_LEFT_BUTTON && super.buttonOk.isMouseOver())
		{
			accept = true;
			close();
		}
	}
}
