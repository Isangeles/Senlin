package pl.isangeles.senlin.inter;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.util.Coords;
/**
 * Class for slick text with multiple lines
 * @author Isangeles
 *
 */
public class TextBlock
{
    private List<String> textLines = new ArrayList<>();
    private String text;
    int charsInLine;
    private TrueTypeFont ttf;
    /**
     * Text block constructor 
     * @param text String with text
     * @param charsInLine Maximum number of chars in single line
     * @param tff Slick TrueTypeFont
     */
    public TextBlock(String text, int charsInLine, TrueTypeFont tff)
    {
        this.text = text;
        this.ttf = tff;
        this.charsInLine = charsInLine;
        
        addLines(text, charsInLine);
    }
    /**
     * Draws text block on specified position
     * @param x Position on x-axis
     * @param y Position on y-axis
     */
    public void draw(float x, float y)
    {
        for(int i = 0; i < textLines.size(); i ++)
        {
            if(textLines.size() > 1)
                ttf.drawString(x+Coords.getDis(10), y+ttf.getHeight(textLines.get(i))*i, textLines.get(i));
            else
                ttf.drawString(x+Coords.getDis(10), y, textLines.get(0));
        }
    }
    
    public void addText(String text)
    {
    	textLines.add("");
    	addLines(text, charsInLine);
    }
    /**
     * Return string with whole text
     * @return String with whole block text
     */
    public String getText()
    {
        return text;
    }
    /**
     * Return text block height
     * @return Float block height
     */
    public float getTextHeight()
    {
        return ttf.getHeight(text) * textLines.size();        
    }
    
    private void addLines(String text, int charsInLine)
    {
        int index = 0;
        while(index < text.length()) 
        {
            textLines.add(text.substring(index, Math.min(index + charsInLine,text.length())));
            index += charsInLine;
        }
    }
}
