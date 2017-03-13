package pl.isangeles.senlin.inter.ui;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;

import pl.isangeles.senlin.inter.Button;
import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.inter.Warning;
import pl.isangeles.senlin.util.GConnector;

public class InGameMenu extends InterfaceObject implements MouseListener
{
	private Button resume;
	private Button save;
	private Button load;
	private Button settings;
	private Button exit;
	private MouseOverArea menuMOA;
	private Warning menuWarning;
	
	private boolean exitReq;

	public InGameMenu(GameContainer gc) throws SlickException, IOException, FontFormatException 
	{
		super(GConnector.getInput("ui/background/bgInGameMenu.png"), "bgIGMenu", false, gc);
		gc.getInput().addMouseListener(this);
		
		resume = new Button(GConnector.getInput("ui/button/buttonMenuSmall.png"), "buttIGMResume", false, "Resume", gc);
		save = new Button(GConnector.getInput("ui/button/buttonMenuSmall.png"), "buttIGMResume", false, "Save game", gc);
		load = new Button(GConnector.getInput("ui/button/buttonMenuSmall.png"), "buttIGMResume", false, "Load game", gc);
		settings = new Button(GConnector.getInput("ui/button/buttonMenuSmall.png"), "buttIGMResume", false, "Settings", gc);
		exit = new Button(GConnector.getInput("ui/button/buttonMenuSmall.png"), "buttIGMResume", false, "Exit game", gc);
		
		menuMOA = new MouseOverArea(gc, super.baseTex, 0, 0);
		menuWarning = new Warning(gc, "");
	}
	
	public void draw(float x, float y)
	{
		super.draw(x, y);
		
		resume.draw(super.x+30, super.y+30);
		save.draw(super.x+30, super.y+80);
		load.draw(super.x+30, super.y+130);
		settings.draw(super.x+30, super.y+180);
		exit.draw(super.x+30, super.y+230);
		
		menuMOA.setLocation(super.x, super.y);
		
		if(exitReq)
			menuWarning.show("Are you sure ?");
	
	public boolean isMouseOver()
	{
		return menuMOA.isMouseOver();
	}
	
	public boolean isExitReq()
	{
		if(menuWarning.isAccept())
			return exitReq;
		else
			return false;
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
			exitReq = true;
	}

	@Override
	public void mouseWheelMoved(int change) 
	{
	}

	private void cancelExit()
	{
		exitReq = false;
	}
}
