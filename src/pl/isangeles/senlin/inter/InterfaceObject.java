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

import pl.isangeles.senlin.util.Settings;

public abstract class InterfaceObject
{   
    GameContainer gc;
    Image baseTex;
    float scale;
    float x;
    float y;
    
    public InterfaceObject(String pathToTex) throws SlickException
    {
        baseTex = new Image(pathToTex);
        
    }
    
    public InterfaceObject(InputStream fileInput, String ref, boolean flipped, GameContainer gc) throws SlickException, IOException
    {
        baseTex = new Image(fileInput, ref, flipped);
        this.gc = gc;
        setProportion();
    }
    
    protected void draw(float x, float y)
    {
        this.x = x * scale;
        this.y = y * scale;
        baseTex.draw(this.x, this.y, scale);
    }
    
    protected void draw(float x, float y, Color filter)
    {
        this.x = x * scale;
        this.y = y * scale;
        baseTex.draw(this.x, this.y, scale, filter);
    }
    
    protected void drawString(String text, TrueTypeFont ttf)
    {
        float buttonEndX = baseTex.getWidth();
        float buttonEndY = baseTex.getHeight();
        float textX = ttf.getWidth(text);
        float textY = ttf.getHeight(text);
        
        ttf.drawString(getCenteredCoord(x, buttonEndX, textX), getCenteredCoord(y, buttonEndY, textY), text);
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
