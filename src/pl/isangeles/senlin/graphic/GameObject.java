/*
 * GameObject.java
 * 
 * Copyright 2017 Dariusz Sikora <darek@darek-PC-LinuxMint18>
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
package pl.isangeles.senlin.graphic;

import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;

import pl.isangeles.senlin.gui.InfoWindow;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.util.Settings;
/**
 * Class for game object like avatars etc.
 * @author Isangeles
 *
 */
public abstract class GameObject extends Image
{
    protected float scale;
    private InfoWindow objectInfo;
    protected float x;
    protected float y;
    protected MouseOverArea gObjectMOA;
    
    public GameObject(InputStream is, String ref, boolean flipped) throws SlickException
    {
    	super(is, ref, flipped);
    	setScale();
    }
    
    public GameObject(InputStream is, String ref, boolean flipped, String infoText, GameContainer gc) throws SlickException, IOException, FontFormatException
    {
    	this(is, ref, flipped);
    	gObjectMOA = new MouseOverArea(gc, this, 0, 0); 
    	objectInfo = new InfoWindow(gc, infoText);
    }
    
    public GameObject(Image img)
    {
    	super(img);
    	setScale();
    }
    
    public GameObject(Image img, String infoText, GameContainer gc) throws SlickException, IOException, FontFormatException
    {
    	this(img);
    	gObjectMOA = new MouseOverArea(gc, this, 0, 0); 
    	objectInfo = new InfoWindow(gc, infoText);
    }
    /**
     * Draws object on specified position on screen
     * @param x Position on x-axis
     * @param y Position on y-axis
     * @param scaledPos True if position should be scale to current resolution, false otherwise(Irrespective to this, object size is still scaled)
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
        
        
        if(gObjectMOA != null)
        {
        	gObjectMOA.setLocation(x-Global.getCameraPos()[0], y-Global.getCameraPos()[1]);
        	
        	if(gObjectMOA.isMouseOver())
        		objectInfo.draw(x, y);
        }
    }
    /**
     * Draws object on specified position on screen
     * @param x Position on x-axis
     * @param y Position on y-axis
     * @param reqSize Defines size of object(remember that object is always implicitly scaled to current resolution)
     * @param scaledPos True if position should be scale to current resolution, false otherwise(Irrespective to this, object size is still scaled)
     */
    public void draw(float x, float y, float reqSize, boolean scaledPos)
    {
    	this.x = x;
    	this.y = y;
        if(scaledPos)
        {
        	this.x *= scale;
            this.y *= scale;
        }
        super.draw(this.x, this.y, scale*reqSize);
        
        if(gObjectMOA != null)
        {
        	gObjectMOA.setLocation(x-Global.getCameraPos()[0], y-Global.getCameraPos()[1]);
        	
        	if(gObjectMOA.isMouseOver())
        		objectInfo.draw(x, y);
        }
    }
    /**
     * Draws object on specified position on screen
     * @param x Position on x-axis
     * @param y Position on y-axis
     * @param width
     * @param height
     * @param scaledPos True if position should be scale to current resolution, false otherwise(Irrespective to this, object size is still scaled)
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
        
        if(gObjectMOA != null)
        {
        	gObjectMOA.setLocation(x-Global.getCameraPos()[0], y-Global.getCameraPos()[1]);
        	
        	if(gObjectMOA.isMouseOver())
        		objectInfo.draw(x, y);
        }
    }
    
    public void draw(float reqSize)
    {
    	this.x *= scale;
        this.y *= scale;
        
        super.draw(this.x, this.y, scale*reqSize);
        
        if(gObjectMOA != null)
        {
        	gObjectMOA.setLocation(x-Global.getCameraPos()[0], y-Global.getCameraPos()[1]);
        	
        	if(gObjectMOA.isMouseOver())
        		objectInfo.draw(x, y);
        }
    }
    
    public void setPosition(Position pos)
    {
    	this.x = pos.x;
    	this.y = pos.y;
    }
    
    public float getScale()
    {
    	return scale;
    }
    
    public float getDis(float dis)
    {
    	return dis * scale;
    }
    
    public float getScaledHeight()
    {
    	return this.height*scale;
    }
    
    public float getScaledWidth()
    {
    	return this.width*scale;
    }
    
    public boolean isMouseOver()
    {
    	return gObjectMOA.isMouseOver();
    }
    /**
     * Returns distance corrected by scale
     * @param rawDistance Distance on 1920x1080
     * @return Distance scaled to current resolution
     */
    protected int getDis(int rawDistance)
    {
    	return Math.round(rawDistance * scale);
    }
    /**
     * Sets proportion for object based on current resolution, called by constructor
     * @throws FileNotFoundException
     */
    private void setScale()
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
