package pl.isangeles.senlin.inter;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.core.Attribute;
/**
 * Class for points fields 
 * @author Isangeles
 *
 */
public class PointsField extends InterfaceObject 
{
	Attribute value;
	String label;
	Font textFont;
	TrueTypeFont textTtf;
	
	private float valueWidth;
	private float valueHeight;
	private float labelWidth;
	private float labelHeight;
	private float texWidth;
	private float texHeight;
	/**
	 * Constructor without info window
	 * @param fileInput Input pointed on specific graphic file for field background
	 * @param ref The name that should be assigned to the image
	 * @param flipped True if the image should be flipped on the y-axis on load
	 * @param value Value to display
	 * @param label Label to display
	 * @param gc GameContainer for super class
	 * @throws SlickException
	 * @throws FontFormatException
	 * @throws IOException
	 */
	public PointsField(InputStream fileInput, String ref, boolean flipped, Attribute value, String label, GameContainer gc) throws SlickException, FontFormatException, IOException 
	{
		super(fileInput, ref, flipped, gc);
		build(value, label, gc);
	}
	/**
     * Constructor with info window
     * @param fileInput Input pointed on specific graphic file for field background
     * @param ref The name that should be assigned to the image
     * @param flipped True if the image should be flipped on the y-axis on load
     * @param value Value to display
     * @param label Label to display
     * @param gc GameContainer for super class
     * @param info Informations about field to display in info window
     * @throws SlickException
     * @throws FontFormatException
     * @throws IOException
     */
	public PointsField(InputStream fileInput, String ref, boolean flipped, Attribute value, String label, GameContainer gc, String info) throws SlickException, FontFormatException, IOException 
    {
        super(fileInput, ref, flipped, gc, info);
        build(value, label, gc);
    }
	/**
	 * Draws field
	 * @param x Position on x axis
	 * @param y Position on y axis
	 */
	public void draw(float x, float y)
	{
		super.draw(x, y);
        
        textTtf.drawString(this.getCenteredCoord(x, texWidth, valueWidth), getCenteredCoord(y, texHeight, valueHeight), value+"");
        textTtf.drawString(this.getCenteredCoord(x, texWidth, labelWidth), getCenteredCoord(y, texHeight, labelHeight-20), label);
	}
	/**
	 * Private method called by constructor, builds field point
	 * @param value
	 * @param label
	 * @param gc
	 * @throws FontFormatException
	 * @throws IOException
	 */
	private void build(Attribute value, String label, GameContainer gc) throws FontFormatException, IOException
	{
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

}
