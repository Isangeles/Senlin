package pl.isangeles.senlin.inter;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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
public abstract class InterfaceObject
{   
    private GameContainer gc;
    protected Image baseTex;
    private float scale;
    protected float x;
    protected float y;
    
    private InfoWindow info;
    private boolean isInfo;
    private MouseOverArea iObjectMOA;
    /**
     * Old "pre-archive" constructor
     * @param pathToTex
     * @throws SlickException
     */
    @Deprecated
    public InterfaceObject(String pathToTex) throws SlickException
    {
        baseTex = new Image(pathToTex);
        iObjectMOA = new MouseOverArea(gc, baseTex, 0, 0);
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
    public InterfaceObject(InputStream fileInput, String ref, boolean flipped, GameContainer gc) throws SlickException, IOException
    {
        baseTex = new Image(fileInput, ref, flipped);
        this.gc = gc;
        setProportion();
        iObjectMOA = new MouseOverArea(gc, baseTex, 0, 0);
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
    public InterfaceObject(InputStream fileInput, String ref, boolean flipped, GameContainer gc, String textForInfo) throws SlickException, IOException, FontFormatException
    {
    	this(fileInput, ref, flipped, gc);
    	isInfo = true;
    	info = new InfoWindow(gc, textForInfo);
    	iObjectMOA = new MouseOverArea(gc, baseTex, 0, 0);
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
    protected void draw(float x, float y)
    {
        this.x = x * scale;
        this.y = y * scale;
        baseTex.draw(this.x, this.y, scale);
        
        iObjectMOA.setLocation(this.x, this.y);
        if(isInfo && iObjectMOA.isMouseOver())
        {
        	info.draw(gc.getInput().getMouseX()+10, gc.getInput().getMouseY()+10);
        }
    }
    /**
     * Draws object with specific color
     * @param x Position on x axis
     * @param y Position on y axis
     * @param filter Color for object
     */
    protected void draw(float x, float y, Color filter)
    {
        this.x = x * scale;
        this.y = y * scale;
        baseTex.draw(this.x, this.y, scale, filter);
        
        iObjectMOA.setLocation(this.x, this.y);
        if(isInfo && iObjectMOA.isMouseOver())
        {
        	info.draw(gc.getInput().getMouseX()+10, gc.getInput().getMouseY()+10);
        }
    }
    /**
     * Draws object with specific width and height
     * @param x Position on x axis
     * @param y Position on y axis
     * @param width Width for object
     * @param height Height for object
     */
    protected void draw(float x, float y, float width, float height)
    {
    	this.x = x * scale;
        this.y = y * scale;
        baseTex.draw(this.x, this.y, width*scale, height*scale);
        
        iObjectMOA.setLocation(this.x, this.y);
        if(isInfo && iObjectMOA.isMouseOver())
        {
        	info.draw(gc.getInput().getMouseX()+10, gc.getInput().getMouseY()+10);
        }
    }
    /**
     * Draws string in middle of object
     * @param text String to draw
     * @param ttf TTF font for text
     */
    protected void drawString(String text, TrueTypeFont ttf)
    {
        float thisEndX = getWidth();
        float thisEndY = getHeight();
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
    protected float getWidth()
    {
    	return baseTex.getWidth()*scale;
    }
    /**
     * Returns base width of object
     * @return Base width of object
     */
    protected float getBaseWidth()
    {
    	return baseTex.getWidth();
    }
    /**
     * Returns height of object corrected by scale
     * @return Height of object multiplied by scale
     */
    protected float getHeight()
    {
    	return baseTex.getHeight()*scale;
    }
    /**
     * Returns base height of object 
     * @return Base height of object
     */
    protected float getBaseHeight()
    {
    	return baseTex.getHeight();
    }
    /**
     * Set proportion for object based on current resolution
     * @throws FileNotFoundException
     */
    private void setProportion() throws FileNotFoundException
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
