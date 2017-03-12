package pl.isangeles.senlin.inter;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.MouseOverArea;

import pl.isangeles.senlin.core.Attribute;
import pl.isangeles.senlin.util.*;

public final class Switch extends InterfaceObject implements MouseListener
{
	int value;
	Attribute points;
	Font labelFont;
	TrueTypeFont ttf;
	String label;
	Button buttPlus;
	Button buttMinus;
	GameContainer gc;
	
	public Switch(GameContainer gc, String label, int value, Attribute points) throws SlickException, IOException, FontFormatException 
	{
		super(GConnector.getInput("switch/switchBG.png"), "switch", false, gc);
		build(gc, label, value, points);
	}
	
	public Switch(GameContainer gc, String label, int value, Attribute points, String textForInfo) throws SlickException, IOException, FontFormatException
	{
		super(GConnector.getInput("switch/switchBG.png"), "switch", false, gc, textForInfo);
		build(gc, label, value, points);
	}
	
	public void draw(float x, float y)
	{
		super.draw(x, y);
		
		float texEndX = super.getWidth();
        float texEndY = super.getHeight();
        float textX = ttf.getWidth(label);
        float textY = ttf.getHeight(label);
        
		buttPlus.draw(super.x+texEndX-35, super.y+2);
		buttMinus.draw(super.x, super.y+2);
		
		super.drawString(value+"", ttf);
		ttf.drawString(super.getCenteredCoord(x, texEndX, textX), super.getCenteredCoord(y, texEndY, textY-20), label);
		
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
		if(buttPlus.isMouseOver() && points.getValue() > 0)
		{
			value ++;
			points.decrement();
		}
		else if(buttMinus.isMouseOver() && value > 1)
		{
			value --;
			points.increment();;
		}
	}
	@Override
	public void mouseWheelMoved(int change) 
	{
	}

	private void build(GameContainer gc, String label, int value, Attribute points) throws SlickException, FontFormatException, IOException
	{
		this.gc = gc;
		this.label = label;
		this.value = value;
		this.points = points;
		this.gc.getInput().addMouseListener(this);
		
		buttPlus = new Button(GConnector.getInput("switch/switchButtonPlus.png"), "switchBP", false, "", this.gc);
		buttMinus = new Button(GConnector.getInput("switch/switchButtonMinus.png"), "switchBM", false, "", this.gc);
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		labelFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        
        ttf = new TrueTypeFont(labelFont.deriveFont(10f), true);
	}
}
