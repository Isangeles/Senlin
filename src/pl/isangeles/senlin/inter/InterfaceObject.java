package pl.isangeles.senlin.inter;

import java.io.InputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class InterfaceObject
{   
    Image baseTex;
    float endX;
    float endY;
    
    public InterfaceObject(String pathToTex) throws SlickException
    {
        baseTex = new Image(pathToTex);
        
    }
    
    public InterfaceObject(InputStream fileInput, String ref, boolean flipped) throws SlickException
    {
        baseTex = new Image(fileInput, ref, flipped);
    }
    
    protected void draw(float x, float y)
    {
        baseTex.draw(x, y);
        endX = x + baseTex.getWidth();
        endY = y + baseTex.getHeight();
    }
    
    protected void draw(float x, float y, Color filter)
    {
        baseTex.draw(x, y, filter);
        endX = x + baseTex.getWidth();
        endY = y + baseTex.getHeight();
    }
    
    protected float getCenteredCoord(float bgCoord, float bgSize, float obSize)
    {
    	return (bgCoord + (bgSize/2)) - obSize/2; 
    }
    
    protected float getWidth()
    {
    	return baseTex.getWidth();
    }
    
    protected float getHeight()
    {
    	return baseTex.getHeight();
    }
    
}
