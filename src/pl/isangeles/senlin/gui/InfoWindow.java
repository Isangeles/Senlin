package pl.isangeles.senlin.gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.data.GBase;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.Settings;
/**
 * Class for information windows
 * @author Isangeles
 *
 */
public class InfoWindow extends InterfaceObject
{
	private String textInfo;
	private Font textFont;
	private TrueTypeFont textTtf;
	private int noLines = 0;
	/**
	 * Info window constructor
	 * @param gc Slick game container
	 * @param textInfo Text for window
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public InfoWindow(GameContainer gc, String textInfo) throws SlickException, IOException, FontFormatException 
	{
		super(GBase.getImage("infoWinBg"), gc);
		this.textInfo = textInfo;
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		textFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		textTtf = new TrueTypeFont(textFont.deriveFont(12f), true);
		
		countLines();
	}
	/**
	 * Draws window with information text split into lines
	 */
	public void draw(float x, float y)
	{
	    String[] lines = textInfo.split(System.lineSeparator());
	    super.drawUnscaled(getCorrectX(x), getCorrectY(y), textTtf.getWidth(textInfo), textTtf.getHeight(textInfo)*noLines);
		for(int i = 0; i < noLines; i ++)
		{
	        if(noLines > 1)
	            textTtf.drawString(super.x+getDis(10), super.y+textTtf.getHeight(lines[i])*i, lines[i]);
	        else
	            textTtf.drawString(super.x+getDis(10), super.y, lines[i]);
		}
	}
	/**
	 * Sets text to display
	 * @param text String with text
	 */
	public void setText(String text)
	{
		this.textInfo = text;
		countLines();
	}
	/**
	 * Checks if specified x position need to be corrected to not protrude the screen 
	 * @param x Position on x-axis
	 * @return Corrected or untouched x value
	 */
	private float getCorrectX(float x)
	{
		x += 10;
		if(x >= Settings.getResolution()[0] - getDis(25))
			x -= getWidth();
		
		return x;
	}
	/**
	 * Checks if specified y position need to be corrected to not protrude the screen 
	 * @param y Position on y-axis
	 * @return Corrected or untouched y value
	 */
	private float getCorrectY(float y)
	{
		if(y >= Settings.getResolution()[1] - getDis(25))
			y -= getHeight();
		
		return y;
	}
	/**
	 * Counts lines for current text
	 */
	private void countLines()
	{
		noLines = 0;
		for(String line : textInfo.split(System.lineSeparator())) //counting lines 
        {
            noLines++;
        }
	}
}
