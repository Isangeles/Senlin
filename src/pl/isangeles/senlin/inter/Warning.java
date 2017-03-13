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
		abort.draw(super.x+50, super.y+super.getHeight()-50);
	}
	
	@Override
	public void show(String textWarning)
	{
		super.show(textWarning);

		
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
			undecided = false;
			super.close();
		}
		else if(button == Input.MOUSE_LEFT_BUTTON && super.buttonOk.isMouseOver())
		{
			accept = true;
			undecided = false;
			super.close();
		}
	}
}
