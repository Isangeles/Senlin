/*
 * InterfaceObject.java
 * 
 * Copyright 2017-2018 Dariusz Sikora <darek@pc-solus>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */
package pl.isangeles.senlin.gui;

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

import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.util.Settings;
/**
 * Abstract super class for all interface objects
 * @author Isangeles
 *
 */
public abstract class InterfaceObject extends Image
{   
    protected GameContainer gc;
    protected float x;
    protected float y;
    protected float width = getWidth();
    protected float height = getHeight();
    
    private InfoWindow info;
    private boolean isInfo;
    private MouseOverArea moa;
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
        moa = new MouseOverArea(gc, this, (int)Coords.getX("BR", 0), (int)Coords.getY("BR", 0), (int)getScaledWidth(), (int)getScaledHeight());
    }
    /**
     * Object constructor with custom MOA size that uses raw path(outside graphical archive)
     * @param pathToTex Path to image file
     * @param gc Slick game container
     * @param moaWidth MouseOverArea width
     * @param moaHeight MouseOverArea height
     * @throws SlickException
     * @throws FileNotFoundException 
     */
    protected InterfaceObject(String pathToTex, int moaWidth, int moaHeight, GameContainer gc) throws SlickException, FileNotFoundException
    {
        super(pathToTex);
        this.gc = gc;
        moa = new MouseOverArea(gc, this, (int)Coords.getX("BR", 0), (int)Coords.getY("BR", 0), moaWidth, moaHeight);
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
        moa = new MouseOverArea(gc, this, (int)Coords.getX("BR", 0), (int)Coords.getY("BR", 0), (int)getScaledWidth(), (int)getScaledHeight());
    }
    /**
     * Object with info window constructor that uses another image
     * @param image Slick image object
     * @param gc Slick game container
     * @param textForInfo Text for info window
     * @throws FontFormatException 
     * @throws IOException 
     * @throws SlickException 
     */
    protected InterfaceObject(Image image, GameContainer gc, String textForInfo) throws SlickException, IOException, FontFormatException
    {
    	super(image);
        this.gc = gc;
        moa = new MouseOverArea(gc, this, (int)Coords.getX("BR", 0), (int)Coords.getY("BR", 0), (int)getScaledWidth(), (int)getScaledHeight());
    	isInfo = true;
    	info = new InfoWindow(gc, textForInfo);
    }
    /**
     * Object with custom MOA size constructor that uses another image
     * @param image Slick image object
     * @param gc Slick game container
     * @param moaWidth MouseOverArea width
     * @param moaHeight MouseOverArea height
     * @throws FileNotFoundException
     */
    protected InterfaceObject(Image image, int moaWidth, int moaHeight, GameContainer gc) throws FileNotFoundException
    {
    	super(image);
        this.gc = gc;
        moa = new MouseOverArea(gc, this, (int)Coords.getX("BR", 0), (int)Coords.getY("BR", 0), moaWidth, moaHeight);
    }
    /**
     * Creates object with custom MOA size and info window, uses another image
     * @param image Slick image object
     * @param textForInfo String with text for info window
     * @param moaWidth MouseOverArea width
     * @param moaHeight MouseOverArea height
     * @param gc Slick game container
     * @throws IOException 
     * @throws SlickException 
     * @throws FileNotFoundException
     */
    protected InterfaceObject(Image image, String textForInfo, int moaWidth, int moaHeight, GameContainer gc) throws FontFormatException, SlickException, IOException
    {
    	super(image);
        this.gc = gc;
        moa = new MouseOverArea(gc, this, (int)Coords.getX("BR", 0), (int)Coords.getY("BR", 0), moaWidth, moaHeight);
    	isInfo = true;
    	info = new InfoWindow(gc, textForInfo);
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
        moa = new MouseOverArea(gc, this, (int)Coords.getX("BR", 0), (int)Coords.getY("BR", 0), (int)getScaledWidth(), (int)getScaledHeight());
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
    }
    /**
     * Constructor for interface object with info window and custom MOA size, implicitly scales object for current resolution
     * @param fileInput in - The input stream to read the image from
     * @param ref The name that should be assigned to the image
     * @param flipped True if the image should be flipped on the y-axis on load
     * @param gc GameContainer for mouse position 
     * @param textForInfo Text for info window to display
     * @param moaWidth MouseOverArea width
     * @param moaHeight MouseOverArea height
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    protected InterfaceObject(InputStream fileInput, String ref, boolean flipped, GameContainer gc, String textForInfo, int moaWidth, int moaHeight) throws SlickException, IOException, FontFormatException
    {
    	super(fileInput, ref, flipped);
        this.gc = gc;
        moa = new MouseOverArea(gc, this, (int)Coords.getX("BR", 0), (int)Coords.getY("BR", 0), moaWidth, moaHeight);
    	isInfo = true;
    	info = new InfoWindow(gc, textForInfo);
    }
    /**
     * Checks if mouse is over object
     * @return
     */
    public boolean isMouseOver()
    {
    	return moa.isMouseOver();
    }
    /**
     * Draws object on current position 
     * (position is scaled)
     */
    @Override
    public void draw()
    {
        float drawX = x * Coords.getScale();
        float drawY = y * Coords.getScale();
        super.draw(drawX, drawY, Coords.getScale());
        
        moa.setLocation(drawX, drawY);
        if(isInfo && moa.isMouseOver())
        {
            info.draw(gc.getInput().getMouseX()+getDis(20), gc.getInput().getMouseY()+getDis(20));
        }
    }
    /**
     * Draws object
     * @param x Position on x axis
     * @param y Position on y axis
     */
    @Override
    public void draw(float x, float y)
    {
        setPosition(x, y);
        
        float drawX = Coords.getDis((int)x);
        float drawY = Coords.getDis((int)y);
        super.draw(drawX, drawY, Coords.getScale());
        
        moa.setLocation(drawX, drawY);
        if(isInfo && moa.isMouseOver())
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
        setPosition(x, y);
        
        float drawX = x * Coords.getScale();
        float drawY = y * Coords.getScale();
        super.draw(drawX, drawY, Coords.getScale(), filter);
        
        moa.setLocation(drawX, drawY);
        if(isInfo && moa.isMouseOver())
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
        setPosition(x, y);
        setSize(width, height);
    
        float drawX = x * Coords.getScale();
        float drawY = y * Coords.getScale();
        float drawWidth = width * Coords.getScale();
        float drawHeight = height * Coords.getScale();
        
        super.draw(drawX, drawY, drawWidth, drawHeight);
        
        moa.setLocation(drawX, drawY);
        if(isInfo && moa.isMouseOver())
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
        setPosition(x, y);
        setSize(width, height);
        
        float drawX = x;
        float drawY = y;
        if(scaledPos)
        {
            drawX *= Coords.getScale();
            drawY *= Coords.getScale();
        }
        float drawWidth = width * Coords.getScale();
        float drawHeight = height * Coords.getScale();
        
        super.draw(drawX, drawY, drawWidth, drawHeight);
        
        moa.setLocation(drawX, drawY);
        if(isInfo && moa.isMouseOver())
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
        setPosition(x, y);
        
        float drawX = x;
        float drawY = y;
        if(scaledPos)
        {
            drawX *= Coords.getScale();
            drawY *= Coords.getScale();
        }
        super.draw(drawX, drawY, Coords.getScale());
         
        moa.setLocation(drawX, drawY);
        if(isInfo && moa.isMouseOver())
        {
            info.draw(gc.getInput().getMouseX()+getDis(20), gc.getInput().getMouseY()+getDis(20));
        }
    }
    /**
     * Draws object with specified color
     * @param x Position on x axis
     * @param y Position on y axis
     * @param filter Color for object
     * @param scaledPos True if object position should be scaled
     */
    public void draw(float x, float y, Color filter, boolean scaledPos)
    {
        setPosition(x, y);
        
        float drawX = x;
        float drawY = y;
        if(scaledPos)
        {
            drawX *= Coords.getScale();
            drawY *= Coords.getScale();
        }
        super.draw(drawX, drawY, Coords.getScale(), filter);
        
        moa.setLocation(drawX, drawY);
        if(isInfo && moa.isMouseOver())
        {
            info.draw(gc.getInput().getMouseX()+getDis(20), gc.getInput().getMouseY()+getDis(20));
        }
    }
    /**
     * Draws object with specified color and size
     * @param x Position on x axis
     * @param y Position on y axis
     * @param witdh Width for object
     * @param height Height for object
     * @param filter Color for object
     * @param scaledPos True if position should be scaled to current resolution
     */
    public void draw(float x, float y, float width, float height, Color filter, boolean scaledPos)
    {
        setPosition(x, y);
        setSize(width, height);
        
        float drawX = x;
        float drawY = y;
        if(scaledPos)
        {
            drawX *= Coords.getScale();
            drawY *= Coords.getScale();
        }
        float drawWidth = width * Coords.getScale();
        float drawHeight = height * Coords.getScale();
        
        super.draw(drawX, drawY, drawWidth, drawHeight, filter);
        
        moa.setLocation(drawX, drawY);
        if(isInfo && moa.isMouseOver())
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
    	return Coords.getScale();
    }
    /**
     * Returns distance corrected by scale
     * @param rawDistance Distance on 1920x1080
     * @return Distance scaled to current resolution
     */
    public int getDis(int rawDistance)
    {
    	return Math.round(rawDistance * Coords.getScale());
    }
    /**
     * Returns size corrected by scale
     * @param size Raw float size
     * @return Float size value
     */
    public float getSize(float size)
    {
    	return size * Coords.getScale();
    }
    /**
     * Returns object position
     * @return XY position
     */
    public Position getPos()
    {
    	return new Position(x, y);
    }
    /**
     * Returns width of object corrected by scale
     * @return Width of object multiplied by scale
     */
    public float getScaledWidth()
    {
    	return getWidth()*Coords.getScale();
    }
    /**
     * Returns height of object corrected by scale
     * @return Height of object multiplied by scale
     */
    public float getScaledHeight()
    {
    	return getHeight()*Coords.getScale();
    }
    /**
     * Returns position in the center of the screen for this object and current resolution
     * @return Position in the center of the screen
     */
    public Position atCenter()
    {
    	return new Position(Coords.getX("CE", 0) - (getScaledWidth()/2), Coords.getY("CE", 0) - (getScaledHeight()/2));
    }
    /**
     * Sets specified text as object info
     * @param info String with text for info
     */
    public void setInfo(String info)
    {
    	if(isInfo)
        	this.info.setText(info);
    }
    /**
     * Sets object position
     * @param x Position on x-axis
     * @param y Position on y-axis
     */
    public void setPosition(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
    /**
     * Sets object size
     * @param width Width
     * @param height Height
     */
    public void setSize(float width, float height)
    {
        this.width = width;
        this.height = height;
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
     * Draws string in middle of object
     * @param text String to draw
     * @param ttf TTF font for text
     */
    protected void drawString(String text, TrueTypeFont ttf, boolean scaledPos)
    {
        float textWidth = ttf.getWidth(text);
        float textHeight = ttf.getHeight(text);
    	/*
        float thisEndX = width;
        float thisEndY = height;
        
        ttf.drawString(getCenteredCoord(x, thisEndX, textX), getCenteredCoord(y, thisEndY, textY), text);
    	*/
    	ttf.drawString(getCenter(scaledPos).x - textWidth/2, getCenter(scaledPos).y - textHeight/2, text);
    }
    /**
     * Draws string in specified color in middle of object
     * @param text String to draw
     * @param ttf TTF font for text
     * @param color Text color
     */
    protected void drawString(String text, TrueTypeFont ttf, Color color, boolean scaledPos)
    {
        float textWidth = ttf.getWidth(text);
        float textHeight = ttf.getHeight(text);
    	/*
        float thisEndX = width;
        float thisEndY = height;
        
        ttf.drawString(getCenteredCoord(x, thisEndX, textWidth), getCenteredCoord(y, thisEndY, textHeight), text, color);
    	*/
    	ttf.drawString(getCenter(scaledPos).x - textWidth/2, getCenter(scaledPos).y - textHeight/2, text, color);
    }
    /**
     * Moves MouseOverArea
     * @param x position on x-axis
     * @param y position on y-axis
     */
    protected void moveMOA(float x, float y)
    {
    	moa.setLocation(x, y);
    }
    /**
     * Moves MouseOverArea to unreachable by user position(behind bottom right edge of screen)
     */
    protected void hideMOA()
    {
    	moveMOA(Coords.getX("BR", 0), Coords.getY("BR", 0));
    }
    /**
     * Now use getCenter()
     * @param bgCoord
     * @param bgSize
     * @param obSize
     * @return
     */
    @Deprecated
    protected float getCenteredCoord(float bgCoord, float bgSize, float obSize)
    {
    	return (bgCoord + (bgSize/2)) - obSize/2; 
    }
    /**
     * Returns top right corner of basic interface object texture
     * @return Position with top right coordinates
     */
    protected Position getTR(boolean scaledPos)
    {
    	int posx = (int)x;
    	int posy = (int)y;
    	
    	if(scaledPos)
    	{
    		posx = getDis((int)x);
        	posy = getDis((int)y);
    	}
    	
        if(isCustomSize())
            return new Position((int)(posx + width), (int)posy);
        else
            return new Position((int)(posx + getScaledWidth()), (int)posy);
    }
    /**
     * Returns bottom right corner of basic interface object texture
     * @return Position with bottom right coordinates
     */
    protected Position getBR(boolean scaledPos)
    {
    	int posx = (int)x;
    	int posy = (int)y;
    	
    	if(scaledPos)
    	{
    		posx = getDis((int)x);
        	posy = getDis((int)y);
    	}
    	
        if(isCustomSize())
            return new Position((int)(posx + width), (int)(posy + height));
        else
            return new Position((int)(posx + getScaledWidth()), (int)(posy + getScaledHeight()));
    }
    /**
     * Returns bottom left corner of basic interface object texture
     * @return Position with bottom left coordinates
     */
    protected Position getBL(boolean scaledPos)
    {
    	int posx = (int)x;
    	int posy = (int)y;
    	
    	if(scaledPos)
    	{
    		posx = getDis((int)x);
        	posy = getDis((int)y);
    	}
    	
        if(isCustomSize())
            return new Position((int)posx, (int)(posy + height));
        else
            return new Position((int)posx, (int)(posy + getScaledHeight()));       
    }
    /**
     * Returns position at center of object texture 
     * @param True if position should be scaled, false otherwise
     * @return XY position
     */
    protected Position getCenter(boolean scaledPos)
    {
    	int posx = (int)x;
    	int posy = (int)y;
    	
    	if(scaledPos)
    	{
    		posx = getDis((int)x);
        	posy = getDis((int)y);
    	}
    	
    	if(isCustomSize())
    		return new Position(posx + (width/2), posy + (height/2));
    	else
    		return new Position(posx + (getScaledWidth()/2), posy + (getScaledHeight()/2));
    }
    /**
     * Checks if object is drawn in custom size
     * @return True if object is drawn in custom size, false otherwise
     */
    private boolean isCustomSize()
    {
        return ((width != getWidth() && width != getScaledWidth()) || (height != getHeight() && height != getScaledHeight()));
    }
}
