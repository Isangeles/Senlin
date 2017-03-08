package pl.isangeles.senlin.inter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.awt.Font;
import java.awt.FontFormatException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.MouseOverArea;

/**
 * Interface button class, implements MouseListener to detect mouse click 
 * @author Isangeles
 *
 */
public final class Button extends InterfaceObject implements MouseListener
{
    private String buttText;
    private Font buttTextFont;
    private TrueTypeFont ttf;
    private MouseOverArea clickArea;
    private GameContainer gc;
    private boolean isClicked;
    private boolean active;
    private Color clickColor;
    
    /**
     * Create an button based on path to file string
     * @param pathToImg String with path to file
     * @param buttText String with text for button 
     * @param gc Game container for click detect
     * @throws SlickException
     * @throws FontFormatException
     * @throws IOException
     */
    public Button(String pathToImg, String buttText, GameContainer gc) throws SlickException, FontFormatException, IOException
    {
        super(pathToImg);
        this.buttText = buttText;
        this.gc = gc;
        this.gc.getInput().addMouseListener(this);
        
        clickArea = new MouseOverArea(gc, super.baseTex, 0, 0);
        
        active = true;
        
        isClicked = false;
        clickColor = new Color(73, 73, 73);
        
        File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
        buttTextFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        
        ttf = new TrueTypeFont(buttTextFont.deriveFont(12f), true);
    }
    /**
     * Create an button based on input stream(for zip arch)
     * @param fileInput Input Stream to image 
     * @param ref Name for image
     * @param flipped If file suppose to be flipped
     * @param buttText buttText String with text for button 
     * @param gc Game container for click detect
     * @throws SlickException
     * @throws FontFormatException
     * @throws IOException
     */
    public Button(InputStream fileInput, String ref, boolean flipped, String buttText, GameContainer gc) throws SlickException, FontFormatException, IOException
    {
        super(fileInput, ref, flipped);
        this.buttText = buttText;
        this.gc = gc;
        this.gc.getInput().addMouseListener(this);
        
        active = true;
        
        clickArea = new MouseOverArea(gc, super.baseTex, 0, 0);
        
        isClicked = false;
        clickColor = new Color(73, 73, 73);
        
        File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
        buttTextFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        
        ttf = new TrueTypeFont(buttTextFont.deriveFont(12f), true);
    }
    
    public void draw(float x, float y)
    {
    	if(!isClicked && active)
    		super.draw(x, y);
    	else
    		super.draw(x, y, clickColor);
    	
        clickArea.setLocation(x, y);
        
        float buttonEndX = super.getWidth();
        float buttonEndY = super.getHeight();
        float textX = ttf.getWidth(buttText);
        float textY = ttf.getHeight(buttText);
        
        ttf.drawString(this.getCenteredCoord(x, buttonEndX, textX), getCenteredCoord(y, buttonEndY, textY), buttText);
    }
    
    public boolean clicked()
    {
    	return isClicked;
    }
    
    public boolean isMouseOver()
    {
    	return clickArea.isMouseOver();
    }
    
    public boolean isActive()
    {
        return active;
    }
    
    public void setActive(boolean bool)
    {
    	active = bool;
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
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(int button, int x, int y) 
	{
		if(clickArea.isMouseOver() && active)
			isClicked = true;
	}

	@Override
	public void mouseReleased(int button, int x, int y) 
	{
		if(active)
			isClicked = false;
	}

	@Override
	public void mouseWheelMoved(int change) {
		// TODO Auto-generated method stub
		
	}
}
