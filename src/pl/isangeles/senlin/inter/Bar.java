package pl.isangeles.senlin.inter;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.MouseOverArea;
/**
 * Class  for graphical bars like hp, magicka, experience bars etc.
 * @author Isangeles
 *
 */
public class Bar extends InterfaceObject
{
    private String label;
    private int baseValue;
    private int value;
    private MouseOverArea barMOA;
    private Font barFont;
    private TrueTypeFont barTtf;
    /**
     * Bar constructor
     * @param fileInput File input pointed to bar img
     * @param ref Name for image in system
     * @param flipped If texture suppose to be flipped
     * @param gc Game container for superclass and MOA 
     * @param label Label for bar
     * @param value Bar actual value
     * @param baseValue Bar maximal value
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    public Bar(InputStream fileInput, String ref, boolean flipped, GameContainer gc, String label) throws SlickException, IOException, FontFormatException
    {
        super(fileInput, ref, flipped, gc);
        this.label = label;
        
        barMOA = new MouseOverArea(gc, this, 0, 0);
        
        File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
        barFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        barTtf = new TrueTypeFont(barFont.deriveFont(10f), true);
    }
    /**
     * Updates bar max and actual values
     * @param value
     * @param baseValue
     */
    public void update(int value, int baseValue)
    {
    	this.baseValue = baseValue;
        this.value = value;
    }
    /**
     * Draws bar
     */
    @Override
    public void draw(float x, float y)
    {
        super.draw(x, y, getBarSize()-5f, 21f);
        barMOA.setLocation(super.x, super.y);
        if(barMOA.isMouseOver())
        	barTtf.drawString(super.x+20, super.y, label + value + "/" + baseValue);
        
    }
    /**
     * Gives bar size based on actual value
     * @return Float bar width
     */
    private float getBarSize()
    {
    	if(value <= 0)
    		return 0f;
    	else
    		return (float)((value * 100f) / baseValue)*2;
    }

}
