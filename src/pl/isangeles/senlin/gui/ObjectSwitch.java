/*
 * ObjectSwitch.java
 * 
 * Copyright 2018 Dariusz Sikora <darek@pc-solus>
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
 * Class for object switches
 * @author Isangeles
 *
 */
public class ObjectSwitch extends InterfaceObject implements MouseListener
{
	private String label;
    private List<Switchable> values = new ArrayList<>();
    private int currentValueId;
    private TrueTypeFont ttf;
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
    public ObjectSwitch(GameContainer gc, String label, List<Switchable> values) throws SlickException, FontFormatException, IOException
    {
        super(GConnector.getInput("switch/switchBG.png"), "switchBG", false, gc);
        
        plus = new Button(GBase.getImage("buttonNext"), "", gc);
        minus = new Button(GBase.getImage("buttonBack"), "", gc);
        gc.getInput().addMouseListener(this);
        
        Font textFont = GBase.getFont("mainUiFont");
        ttf = new TrueTypeFont(textFont.deriveFont(12f), true);
        
        this.label = label;
        this.values = values;
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
    public ObjectSwitch(GameContainer gc, String label, List<Switchable> values, String info) throws SlickException, FontFormatException, IOException
    {
        super(GConnector.getInput("switch/switchBG.png"), "switchBG", false, gc, info);
        
        plus = new Button(GConnector.getInput("switch/switchButtonPlus.png"), "switchTBP", false, "", gc);
        minus = new Button(GConnector.getInput("switch/switchButtonMinus.png"), "switchTBM", false, "", gc);
        gc.getInput().addMouseListener(this);
        
        Font textFont = GBase.getFont("mainUiFont");
        ttf = new TrueTypeFont(textFont.deriveFont(12f), true);
        
        this.label = label;
        this.values = values;
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
		
		ttf.drawString(getCenter(scaledPos).x, getBR(scaledPos).y, label);
		super.drawString(values.get(currentValueId).getName(), ttf, scaledPos);
    }
    /**
     * Returns current switch text
     * @return String with actual switch text
     */
    public String getText()
    {
    	return values.get(currentValueId).getName();
    }
    /**
     * Returns current switch value
     * @return String with actual switch value
     */
    public String getValue()
    {
    	return values.get(currentValueId).getId();
    }
    /**
     * Returns current value ID
     * @return Numerical ID
     */
    public int getValueId()
    {
    	return currentValueId;
    }
    /**
     * Returns list with switch values
     * @return List with values
     */
    public List<Switchable>getValues()
    {
    	return values;
    }
    /**
     * Sets value with specified ID as current value
     * @param valueId Value ID
     * @return True if value was selected, false if specified ID was incorrect
     */
    public boolean setValue(int valueId)
    {
    	if(valueId > 0 && valueId < values.size())
    	{
        	this.currentValueId = valueId;
        	return true;
		}
    	else
    		return false;
    }
    /**
     * Sets value as current value
     * @param valueId Value
     * @return True if value was selected, false if specified value was incorrect
     */
    public boolean setValue(String value)
    {
    	for(int i = 0; i < values.size(); i ++)
    	{
    		if(value.equals(values.get(i).getId()))
    		{
    			currentValueId = i;
    			return true;
    		}
    	}
    	return false;
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
        if(button == Input.MOUSE_LEFT_BUTTON && plus.isMouseOver() && currentValueId < values.size()-1)
        {
            isChange = true;
            currentValueId ++;
        }
        else if(button == Input.MOUSE_LEFT_BUTTON && minus.isMouseOver() && currentValueId > 0)
        {
            isChange = true;
            currentValueId --;
        }
    }

    @Override
    public void mouseWheelMoved(int change)
    {
    }

}
