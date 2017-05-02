package pl.isangeles.senlin.inter.ui;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;

import pl.isangeles.senlin.inter.Button;
import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.inter.Warning;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
/**
 * In-game menu class
 * @author Isangeles
 *
 */
class InGameMenu extends InterfaceObject implements UiElement, MouseListener
{
	private Button resume;
	private Button save;
	private Button load;
	private Button settings;
	private Button exit;
	private MouseOverArea menuMOA;
	private Warning menuWarning;
	
	private boolean resumeReq;
	private boolean exitReq;
	/**
	 * In-game menu constructor
	 * @param gc Game container for superclass
	 * @throws SlickException 
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public InGameMenu(GameContainer gc) throws SlickException, IOException, FontFormatException 
	{
		super(GConnector.getInput("ui/background/bgInGameMenu.png"), "bgIGMenu", false, gc);
		gc.getInput().addMouseListener(this);
		
		resume = new Button(GConnector.getInput("ui/button/buttonMenuSmall.png"), "buttIGM", false, TConnector.getText("menu", "resName"), gc);
		save = new Button(GConnector.getInput("ui/button/buttonMenuSmall.png"), "buttIGM", false, TConnector.getText("menu", "sgName"), gc);
		load = new Button(GConnector.getInput("ui/button/buttonMenuSmall.png"), "buttIGM", false, TConnector.getText("menu", "lgName"), gc);
		settings = new Button(GConnector.getInput("ui/button/buttonMenuSmall.png"), "buttIGM", false, TConnector.getText("menu", "settName"), gc);
		exit = new Button(GConnector.getInput("ui/button/buttonMenuSmall.png"), "buttIGM", false, TConnector.getText("menu", "exitName"), gc);
		
		menuMOA = new MouseOverArea(gc, this, (int)Coords.getX("BR", 0), (int)Coords.getY("BR", 0));
		menuWarning = new Warning(gc, "");
	}
	/**
	 * Draws menu
	 * @param x Position on X axis
	 * @param y Position on Y axis
	 */
	public void draw(float x, float y)
	{
		super.draw(x, y, false);
		
		resume.draw(x+getDis(30), y+getDis(30), false);
		save.draw(x+getDis(30), y+getDis(80), false);
		load.draw(x+getDis(30), y+getDis(130), false);
		settings.draw(x+getDis(30), y+getDis(180), false);
		exit.draw(x+getDis(30), y+getDis(230), false);
		
		menuMOA.setLocation(super.x, super.y);
		
		if(exitReq)
			menuWarning.show(TConnector.getText("menu", "warnConf"));
		if(!menuWarning.isUndecided() && menuWarning.isCancel())
		{
			exitReq = false;
			menuWarning.close();
		}
	}
    @Override
    public void update()
    {
    }
	/**
	 * Checks if mouse is over menu
	 * @return Boolean value
	 */
	public boolean isMouseOver()
	{
		return menuMOA.isMouseOver();
	}
	/**
	 * Checks if exit from game is requested
	 * @return Boolean value
	 */
	public boolean isExitReq()
	{
		if(menuWarning.isAccept())
			return exitReq;
		else
			return false;
	}
	/**
	 * Checks if resume game is requested
	 * @return Boolean value
	 */
	public boolean isResumeReq()
	{
		return resumeReq;
	}
	/**
	 * Resets menu to default state
	 */
	public void reset()
	{
		resumeReq = false;
		exitReq = false;
		menuWarning.close();
		menuMOA.setLocation(Coords.getX("BR", 0), Coords.getY("BR", 0));
	}

	@Override
	public void inputEnded() 
	{
	}

	@Override
	public void inputStarted()
	{
	}

	@Override
	public boolean isAcceptingInput() 
	{
		return true;
	}

	@Override
	public void setInput(Input input) 
	{
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) 
	{
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) 
	{
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy)
	{
	}

	@Override
	public void mousePressed(int button, int x, int y) 
	{
	}

	@Override
	public void mouseReleased(int button, int x, int y)
	{
		if(button == Input.MOUSE_LEFT_BUTTON && exit.isMouseOver())
		{
			exitReq = true;
			menuWarning.open();
		}
		if(button == Input.MOUSE_LEFT_BUTTON && resume.isMouseOver())
			resumeReq = true;
	}

	@Override
	public void mouseWheelMoved(int change) 
	{
	}
}
