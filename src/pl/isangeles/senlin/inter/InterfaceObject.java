package pl.isangeles.senlin.inter;

import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.MouseOverArea;

import pl.isangeles.senlin.util.Settings;
/**
 * Abstract super class for all interface objects
 * @author Isangeles
 *
 */
public abstract class InterfaceObject extends Image
{   
    private GameContainer gc;
    private float scale;
    protected float x;
    protected float y;
    
    private InfoWindow info;
    private boolean isInfo;
    private MouseOverArea iObjectMOA;
    /**
     * Object constructor that uses raw path(outside graphical archive)
     * @param pathToTex Path to image file
     * @param gc Slick game container
     * @throws SlickException
     * @throws FileNotFoundException 
     */
    protected InterfaceObject(String pathToTex, GameContainer gc) throws SlickException, FileNotFoundException
    {
        super(pathToTex);
        this.gc = gc;
        setProportion();
        iObjectMOA = new MouseOverArea(gc, this, 0, 0, (int)getScaledWidth(), (int)getScaledHeight());
    }
    /**
     * Object constructor that uses another image
     * @param image Slick image object
     * @param gc Slick game container
     * @throws FileNotFoundException
     */
    protected InterfaceObject(Image image, GameContainer gc) throws FileNotFoundException
    {
    	super(image);
        this.gc = gc;
        setProportion();
        iObjectMOA = new MouseOverArea(gc, this, 0, 0, (int)getScaledWidth(), (int)getScaledHeight());
    }
    /**
     * Constructor for interface object without info window, implicitly scales object for current resolution
     * @param fileInput in - The input stream to read the image from
     * @param ref The name that should be assigned to the image
     * @param flipped True if the image should be flipped on the y-axis on load
     * @param gc GameContainer for mouse position 
     * @throws SlickException
     * @throws IOException
     */
    protected InterfaceObject(InputStream fileInput, String ref, boolean flipped, GameContainer gc) throws SlickException, IOException
    {
        super(fileInput, ref, flipped);
        this.gc = gc;
        setProportion();
        iObjectMOA = new MouseOverArea(gc, this, 0, 0, (int)getScaledWidth(), (int)getScaledHeight());
    }
    /**
     * Constructor for interface object with info window, implicitly scales object for current resolution
     * @param fileInput in - The input stream to read the image from
     * @param ref The name that should be assigned to the image
     * @param flipped True if the image should be flipped on the y-axis on load
     * @param gc GameContainer for mouse position 
     * @param textForInfo Text for info window to display
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    protected InterfaceObject(InputStream fileInput, String ref, boolean flipped, GameContainer gc, String textForInfo) throws SlickException, IOException, FontFormatException
    {
    	this(fileInput, ref, flipped, gc);
    	isInfo = true;
    	info = new InfoWindow(gc, textForInfo);
    	iObjectMOA = new MouseOverArea(gc, this, 0, 0, (int)getScaledWidth(), (int)getScaledHeight());
    }
    /**
     * Checks if mouse is over object
     * @return
     */
    public boolean isMouseOver()
    {
    	return iObjectMOA.isMouseOver();
    }
    /**
     * Draws object
     * @param x Position on x axis
     * @param y Position on y axis
     */
    @Override
    public void draw(float x, float y)
    {
        this.x = x * scale;
        this.y = y * scale;
        super.draw(this.x, this.y, scale);
        
        iObjectMOA.setLocation(this.x, this.y);
        if(isInfo && iObjectMOA.isMouseOver())
        {
        	info.draw(gc.getInput().getMouseX()+getDis(20), gc.getInput().getMouseY()+getDis(20));
        }
    }
    /**
     * Draws object with specific color
     * @param x Position on x axis
     * @param y Position on y axis
     * @param filter Color for object
     */
    @Override
    public void draw(float x, float y, Color filter)
    {
        this.x = x * scale;
        this.y = y * scale;
        super.draw(this.x, this.y, scale, filter);
        
        iObjectMOA.setLocation(this.x, this.y);
        if(isInfo && iObjectMOA.isMouseOver())
        {
        	info.draw(gc.getInput().getMouseX()+getDis(20), gc.getInput().getMouseY()+getDis(20));
        }
    }  
    /**
     * Draws object with specific width and height
     * @param x Position on x axis
     * @param y Position on y axis
     * @param width Width for object
     * @param height Height for object
     */
    @Override
    public void draw(float x, float y, float width, float height)
    {
    	this.x = x * scale;
        this.y = y * scale;
        super.draw(this.x, this.y, width*scale, height*scale);
        
        iObjectMOA.setLocation(this.x, this.y);
        if(isInfo && iObjectMOA.isMouseOver())
        {
        	info.draw(gc.getInput().getMouseX()+getDis(20), gc.getInput().getMouseY()+getDis(20));
        }
    }
    /**
     * Draws object with specific width and height
     * @param x Position on x axis
     * @param y Position on y axis
     * @param width Width for object
     * @param height Height for object
     * @param scaledPos True if object position should be scaled
     */
    public void draw(float x, float y, float width, float height, boolean scaledPos)
    {
    	this.x = x;
    	this.y = y;
        if(scaledPos)
        {
        	this.x *= scale;
            this.y *= scale;
        }
        super.draw(this.x, this.y, width*scale, height*scale);
        
        iObjectMOA.setLocation(this.x, this.y);
        if(isInfo && iObjectMOA.isMouseOver())
        {
        	info.draw(gc.getInput().getMouseX()+getDis(20), gc.getInput().getMouseY()+getDis(20));
        }
    }
    /**
     * Draws object
     * @param x Position on x axis
     * @param y Position on y axis
     * @param scaledPos True if object position should be scaled
     */
    public void draw(float x, float y, boolean scaledPos)
    {
    	this.x = x;
    	this.y = y;
        if(scaledPos)
        {
        	this.x *= scale;
            this.y *= scale;
        }
        super.draw(this.x, this.y, scale);
        
        iObjectMOA.setLocation(this.x, this.y);
        if(isInfo && iObjectMOA.isMouseOver())
        {
        	info.draw(gc.getInput().getMouseX()+getDis(20), gc.getInput().getMouseY()+getDis(20));
        }
    }
    /**
     * Draws object with specific color
     * @param x Position on x axis
     * @param y Position on y axis
     * @param filter Color for object
     * @param scaledPos True if object position should be scaled
     */
    public void draw(float x, float y, Color filter, boolean scaledPos)
    {
    	this.x = x;
    	this.y = y;
        if(scaledPos)
        {
        	this.x *= scale;
            this.y *= scale;
        }
        super.draw(this.x, this.y, scale, filter);
        
        iObjectMOA.setLocation(this.x, this.y);
        if(isInfo && iObjectMOA.isMouseOver())
        {
        	info.draw(gc.getInput().getMouseX()+getDis(20), gc.getInput().getMouseY()+getDis(20));
        }
    }
    /**
     * Returns scale based on current resolution
     * @return Float scale value
     */
    public float getScale()
    {
    	return scale;
    }
    /**
     * Draws object in default scale on unscaled position
     * @param x
     * @param y
     * @param width
     * @param height
     */
    protected void drawUnscaled(float x, float y, float width, float height)
    {
    	this.x = x;
    	this.y = y;
    	super.draw(x, y, width, height);
    }
    /**
     * Returns distance corrected by scale
     * @param rawDistance Distance on 1920x1080
     * @return Distance scaled to current resolution
     */
    public int getDis(int rawDistance)
    {
    	return Math.round(rawDistance * scale);
    }
    /**
     * Returns size corrected by scale
     * @param size Raw float size
     * @return Float size value
     */
    public float getSize(float size)
    {
    	return size * scale;
    }
    /**
     * Draws string in middle of object
     * @param text String to draw
     * @param ttf TTF font for text
     */
    protected void drawString(String text, TrueTypeFont ttf)
    {
        float thisEndX = getScaledWidth();
        float thisEndY = getScaledHeight();
        float textX = ttf.getWidth(text);
        float textY = ttf.getHeight(text);
        
        ttf.drawString(getCenteredCoord(x, thisEndX, textX), getCenteredCoord(y, thisEndY, textY), text);
    }
    
    protected float getCenteredCoord(float bgCoord, float bgSize, float obSize)
    {
    	return (bgCoord + (bgSize/2)) - obSize/2; 
    }
    /**
     * Returns width of object corrected by scale
     * @return Width of object multiplied by scale
     */
    protected float getScaledWidth()
    {
    	return super.getWidth()*scale;
    }
    /**
     * Returns height of object corrected by scale
     * @return Height of object multiplied by scale
     */
    protected float getScaledHeight()
    {
    	return super.getHeight()*scale;
    }
    /**
     * Sets proportion for object based on current resolution, called by constructor
     * @throws FileNotFoundException
     */
    private void setProportion()
    {
        float defResX = 1920;
        float defResY = 1080;
        float resX = Settings.getResolution()[0];
        float resY = Settings.getResolution()[1];
        float proportionX = resX / defResX;
        float proportionY = resY / defResY;
        scale = Math.round(Math.min(proportionX, proportionY) * 10f) / 10f;
    }
    
}
