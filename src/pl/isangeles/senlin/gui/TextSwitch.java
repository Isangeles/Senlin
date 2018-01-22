/*
 * TextSwitch.java
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
package pl.isangeles.senlin.gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.data.GBase;
import pl.isangeles.senlin.util.GConnector;
/**
 * Switch for manipulation of text values
 * @author Isangeles
 *
 */
public final class TextSwitch extends InterfaceObject implements MouseListener
{
    private String label;
    private List<String> textToDraw = new ArrayList<>();
    private int textId;
    private TrueTypeFont textTtf;
    private Button plus;
    private Button minus;
    private boolean isChange;
    /**
     * Text switch constructor 
     * @param gc Slick game container
     * @param textToSwitch String with all text values to switch
     * @throws SlickException
     * @throws FontFormatException
     * @throws IOException
     */
    public TextSwitch(GameContainer gc, String label, String[] textToSwitch) throws SlickException, FontFormatException, IOException
    {
        super(GConnector.getInput("switch/switchBG.png"), "switchBG", false, gc);
        
        plus = new Button(GBase.getImage("buttonNext"), "", gc);
        minus = new Button(GBase.getImage("buttonBack"), "", gc);
        gc.getInput().addMouseListener(this);
        
        File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
        Font textFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        textTtf = new TrueTypeFont(textFont.deriveFont(12f), true);
        
        this.label = label;
        for(String text : textToSwitch)
        {
            textToDraw.add(text);
        }
    }
    /**
     * Text switch constructor(with info window)  
     * @param gc Slick game container
     * @param textToSwitch textToSwitch String with all text values to switch
     * @param info Text for info
     * @throws SlickException
     * @throws FontFormatException
     * @throws IOException
     */
    public TextSwitch(GameContainer gc, String label, String[] textToSwitch, String info) throws SlickException, FontFormatException, IOException
    {
        super(GConnector.getInput("switch/switchBG.png"), "switchBG", false, gc, info);
        
        plus = new Button(GConnector.getInput("switch/switchButtonPlus.png"), "switchTBP", false, "", gc);
        minus = new Button(GConnector.getInput("switch/switchButtonMinus.png"), "switchTBM", false, "", gc);
        gc.getInput().addMouseListener(this);
        
        File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
        Font textFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        textTtf = new TrueTypeFont(textFont.deriveFont(12f), true);
        
        this.label = label;
        for(String text : textToSwitch)
        {
            textToDraw.add(text);
        }
    }
    /**
     * Draws switch
     */
    @Override
    public void draw(float x, float y, boolean scaledPos)
    {
    	super.draw(x, y, scaledPos);
        
        plus.draw(x+getWidth()-getDis(35), y+getDis(2), scaledPos);
		minus.draw(x, y+getDis(2), scaledPos);
		
		textTtf.drawString((super.x+getScaledWidth()/2)-textTtf.getWidth(label), super.y+getScaledHeight(), label);
        super.drawString(textToDraw.get(textId), textTtf, scaledPos);
    }
    /**
     * Returns current switch value
     * @return String with actual switch value
     */
    public String getString()
    {
    	return textToDraw.get(textId);
    }
    /**
     * Returns current value ID
     * @return Numerical ID
     */
    public int getValueId()
    {
    	return textId;
    }
    /**
     * Checks if switch value been changed
     * @return True if value was changed, false otherwise
     */
    public boolean isChange()
    {
        return isChange;
    }

    @Override
    public void inputEnded()
    {
    }

    @Override
    public void inputStarted()
    {
    }

    @Override
    public boolean isAcceptingInput()
    {
        return true;
    }

    @Override
    public void setInput(Input input)
    {
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount)
    {
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy)
    {
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy)
    {
    }

    @Override
    public void mousePressed(int button, int x, int y)
    {
    }

    @Override
    public void mouseReleased(int button, int x, int y)
    {
        if(button == Input.MOUSE_LEFT_BUTTON && plus.isMouseOver() && textId < textToDraw.size()-1)
        {
            isChange = true;
            textId ++;
        }
        else if(button == Input.MOUSE_LEFT_BUTTON && minus.isMouseOver() && textId > 0)
        {
            isChange = true;
            textId --;
        }
    }

    @Override
    public void mouseWheelMoved(int change)
    {
    }

}
