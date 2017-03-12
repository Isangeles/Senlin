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
	String textInfo;
	Font textFont;
	TrueTypeFont textTtf;
	
	public InfoWindow(GameContainer gc, String textInfo) throws SlickException, IOException, FontFormatException 
	{
		super(GConnector.getInput("field/infoWindowBG.png"), "infoWinBG", false, gc);
		this.textInfo = textInfo;
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		textFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		textTtf = new TrueTypeFont(textFont.deriveFont(12f), true);
		
	}
	
	public void draw(float x, float y)
	{
		super.draw(x, y, textTtf.getWidth(textInfo), textTtf.getHeight(textInfo));
		textTtf.drawString(super.x, super.y, textInfo);
	}

}
