package pl.isangeles.senlin.inter;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.util.DConnector;
import pl.isangeles.senlin.util.GConnector;
/**
 * Class for field containing one line of text
 * @author Isangeles
 *
 */
public class Field extends InterfaceObject
{
    private String label;
    private TrueTypeFont ttf;
    private float width;
    private float height;
    
    public Field(float width, float height, String label, GameContainer gc) throws SlickException, IOException, FontFormatException
    {
        super(GConnector.getInput("field/infoWindowBG.png"), "uiField", false, gc);
        
        this.label = label;
        this.width = width;
        this.height = height;
        
        File fontFile = new File(DConnector.SIMSUN_FONT);
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        ttf = new TrueTypeFont(font.deriveFont(12f), true);
    }
    
    @Override
    public void draw(float x, float y, boolean scaledPos)
    {
        super.draw(x, y, width, height, scaledPos);
        super.drawString(label, ttf);
    }
    
    @Override
    public void draw(float x, float y, float width, float height, boolean scaledPos)
    {
        super.draw(x, y, width, height, scaledPos);
        super.drawString(label, ttf);
    }
    
    public void setText(String text)
    {
        label = text;
    }
}
