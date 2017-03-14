package pl.isangeles.senlin.inter;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.MouseOverArea;

public class Bar extends InterfaceObject
{
    String label;
    int value;
    MouseOverArea barMOA;
    Font barFont;
    TrueTypeFont barTtf;
    
    public Bar(InputStream fileInput, String ref, boolean flipped, GameContainer gc, String label, int value) throws SlickException, IOException, FontFormatException
    {
        super(fileInput, ref, flipped, gc);
        this.value = value;
        this.label = label;
        
        barMOA = new MouseOverArea(gc, super.baseTex, 0, 0);
        
        File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
        barFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        barTtf = new TrueTypeFont(barFont.deriveFont(10f), true);
    }
    
    public void update(int value)
    {
        this.value = value;
    }
    
    public void draw(float x, float y)
    {
        super.draw(x, y);
        barMOA.setLocation(super.x, super.y);
        
    }

}
