package pl.isangeles.senlin.inter;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.core.Atribute;

public class PointsField extends InterfaceObject 
{
	Atribute value;
	String label;
	Font textFont;
	TrueTypeFont textTtf;
	
	private float valueWidth;
	private float valueHeight;
	private float labelWidth;
	private float labelHeight;
	private float texWidth;
	private float texHeight;
	
	public PointsField(InputStream fileInput, String ref, boolean flipped, Atribute value, String label) throws SlickException, FontFormatException, IOException 
	{
		super(fileInput, ref, flipped);
		this.value = value;
		this.label = label;
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		textFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		textTtf = new TrueTypeFont(textFont.deriveFont(12f), true);
		

        valueWidth = textTtf.getWidth(value.toString());
        valueHeight = textTtf.getHeight(value.toString());
        labelWidth = textTtf.getWidth(label);
        labelHeight = textTtf.getHeight(label);
        texWidth = super.getWidth();
        texHeight = super.getHeight();
	}
	
	public void draw(float x, float y)
	{
		super.draw(x, y);
        
        textTtf.drawString(this.getCenteredCoord(x, texWidth, valueWidth), getCenteredCoord(y, texHeight, valueHeight), value+"");
        textTtf.drawString(this.getCenteredCoord(x, texWidth, labelWidth), getCenteredCoord(y, texHeight, labelHeight-20), label);
	}

}
