package pl.isangeles.senlin.inter;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.util.GConnector;
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
		super(GConnector.getInput("field/infoWindowBG.png"), "infoWinBG", false, gc);
		this.textInfo = textInfo;
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		textFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		textTtf = new TrueTypeFont(textFont.deriveFont(12f), true);
		
		for(String line : textInfo.split(System.lineSeparator())) //counting lines 
        {
            noLines++;
        }
	}
	/**
	 * Draws window with information text split into lines
	 */
	public void draw(float x, float y)
	{
	    String[] lines = textInfo.split(System.lineSeparator());
	    super.drawUnscaled(x+getDis(10), y, textTtf.getWidth(textInfo), textTtf.getHeight(textInfo)*noLines);
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
	}
}
