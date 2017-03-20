package pl.isangeles.senlin.inter;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.util.GConnector;

public class InfoWindow extends InterfaceObject
{
	private String textInfo;
	private Font textFont;
	private TrueTypeFont textTtf;
	private int noLines = 0;
	
	public InfoWindow(GameContainer gc, String textInfo) throws SlickException, IOException, FontFormatException 
	{
		super(GConnector.getInput("field/infoWindowBG.png"), "infoWinBG", false, gc);
		this.textInfo = textInfo;
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		textFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		textTtf = new TrueTypeFont(textFont.deriveFont(12f), true);
		
		for(String line : textInfo.split(System.lineSeparator()))
        {
            noLines++;
        }
	}
	
	public void draw(float x, float y)
	{
	    String[] lines = textInfo.split(System.lineSeparator());
	    super.draw(x, y, textTtf.getWidth(textInfo), textTtf.getHeight(textInfo)*noLines);
		for(int i = 0; i < noLines; i ++)
		{
	        if(noLines > 1)
	            textTtf.drawString(super.x, super.y+textTtf.getHeight(lines[i])*i, lines[i]);
	        else
	            textTtf.drawString(super.x, super.y, lines[i]);
		}
	}

}
