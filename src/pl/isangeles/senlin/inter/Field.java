/*
 * Field.java
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

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
