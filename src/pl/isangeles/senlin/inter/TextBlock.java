/*
 * TextBlock.java
 * 
 * Copyright 2017 Dariusz Sikora <darek@darek-PC-LinuxMint18>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */
package pl.isangeles.senlin.inter;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.Position;
/**
 * Class for slick text with multiple lines
 * @author Isangeles
 *
 */
public class TextBlock
{
    private List<String> textLines = new ArrayList<>();
    private String text;
    private int charsInLine;
    private TrueTypeFont ttf;
    private Position pos;
    /**
     * Converts list with string values to TextBlocks list
     * @param rawStrings List with strings
     * @param charsInLine Maximal number of characters in line
     * @param ttf TrueTypeFont for text
     * @return ArrayList with blocks of text
     */
    public static List<TextBlock> toTextBlocks(List<String> rawStrings, int charsInLine, TrueTypeFont ttf)
    {
    	List<TextBlock> blocks = new ArrayList<>();
    	
    	for(String string : rawStrings)
    	{
    		blocks.add(new TextBlock(string, charsInLine, ttf));
    	}
    	
    	return blocks;
    }
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
        
        pos = new Position();
        
        addLines(text, charsInLine);
    }
    /**
     * Draws text block on specified position
     * @param x Position on x-axis
     * @param y Position on y-axis
     */
    public void draw(float x, float y)
    {
        pos.x = (int)x;
        pos.y = (int)y;
        
        for(int i = 0; i < textLines.size(); i ++)
        {
            if(textLines.size() > 1)
                ttf.drawString(x+Coords.getDis(10), y+ttf.getHeight(textLines.get(i))*i, textLines.get(i));
            else
                ttf.drawString(x+Coords.getDis(10), y, textLines.get(0));
        }
    }
    
    public void draw()
    {
        this.draw(pos.x, pos.y);
    }
    
    public void move(int x, int y)
    {
        pos.x = x;
        pos.y = y;
    }
    
    public void addText(String text)
    {
    	textLines.add("");
    	addLines(text, charsInLine);
    }
    /**
     * Returns string with whole text
     * @return String with whole block text
     */
    public String getText()
    {
        return text;
    }
    /**
     * Returns text block height
     * @return Float block height
     */
    public float getTextHeight()
    {
        return ttf.getHeight(text) * textLines.size();        
    }
    /**
     * Returns maximal text block width
     * @return Text block width
     */
    public float getTextWidth()
    {
    	float maxWidth = 0;
    	for(String line : textLines)
    	{
    		float lineWidth = ttf.getWidth(line);
    		if(lineWidth > maxWidth)
    			maxWidth = lineWidth;
    	}
    	return maxWidth;
    }
    
    public Position getPosition()
    {
        return pos;
    }
    
    private void addLines(String text, int charsInLine)
    {
        int index = 0;
        /* TODO Line separator detecting, don't work
        while(index < text.length()) 
        {
            String range = text.substring(index, Math.min(index + charsInLine,text.length()));
            if(range.contains("$sep"))
            {
                String line = text.substring(index, range.indexOf("$sep"));
                textLines.add(line);
                index += line.length();
            }
            else
            {
                textLines.add(text.substring(index, Math.min(index + charsInLine,text.length())));
                index += charsInLine;
            }
        }
        */
        while(index < text.length())
        {
            textLines.add(text.substring(index, Math.min(index + charsInLine,text.length())));
            index += charsInLine;
        }
    }
}