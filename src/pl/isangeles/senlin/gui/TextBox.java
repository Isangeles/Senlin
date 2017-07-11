/*
 * TextBox.java
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

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.Position;
/**
 * Class for scrollable text box
 * @author Isangeles
 *
 */
public class TextBox extends InterfaceObject implements MouseListener
{
    private List<TextBlock> texts = new ArrayList<>();
    protected List<TextBlock> visibleTexts = new ArrayList<>();
    private Button up;
    private Button down;
    private int firstIndex;
    /**
     * Text box constructor
     * @param gc Slick game container
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    public TextBox(GameContainer gc) throws SlickException, IOException, FontFormatException
    {
        super(GConnector.getInput("field/textBoxBG.png"), "textBox", false, gc);
        
        gc.getInput().addMouseListener(this);
        
        up = new Button(GConnector.getInput("button/buttonUp.png"), "textBoxUp", false, "", gc);
        down = new Button(GConnector.getInput("button/buttonDown.png"), "textBoxDown", false, "", gc);
    }
    /**
     * Draws text box
     */
    @Override
    public void draw(float x, float y, float width, float height, boolean scaledPos)
    {
       super.draw(x, y, width, height, scaledPos);
       up.draw(super.getTR().x - up.getScaledWidth(), super.getTR().y);
       down.draw(getBR().x - down.getScaledWidth(), getBR().y - down.getScaledHeight());
       
       for(int i = 0; i < visibleTexts.size(); i ++)
       {
           TextBlock text = visibleTexts.get(i);
           if(i == 0)
           {
               text.draw(super.x, (super.y + super.getScaledHeight() - getDis(25)) - text.getTextHeight());
           }
           else if(i > 0)
           {
               TextBlock prevText = visibleTexts.get(i-1);
               text.draw(super.x, (prevText.getPosition().y) - text.getTextHeight());
           }
       }
    }
    /**
     * Adds text to text box
     * @param text Block of text
     */
    public void add(TextBlock text)
    {
        this.texts.add(text);
        firstIndex = texts.size()-1;
        setVisibleTexts();
    }
    /**
     * Adds all text blocks to text box
     * @param texts List with blocks of text
     */
    public void addAll(List<TextBlock> texts)
    {
        this.texts.addAll(texts);
        firstIndex = this.texts.size()-1;
        setVisibleTexts();
    }
    /**
     * Removes all text from box
     */
    public void clear()
    {
        texts.clear();
    }
    
    public void useContainer(List<TextBlock> container)
    {
    	texts = container;
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
        if(button == Input.MOUSE_LEFT_BUTTON)
        {
            if(up.isMouseOver())
            {
                if(firstIndex > 0)
                {
                    firstIndex --;
                    setVisibleTexts();
                }
            }
            if(down.isMouseOver())
            {
                if(firstIndex < texts.size()-1)
                {
                    firstIndex ++;
                    setVisibleTexts();
                }
            }
        }
    }

    @Override
    public void mouseWheelMoved(int change)
    {
    	if(isMouseOver())
    	{
    		if(change > 0)
        	{
                if(firstIndex > 0)
                {
                    firstIndex --;
                    setVisibleTexts();
                }
        	}
        	else
        	{
                if(firstIndex < texts.size()-1)
                {
                    firstIndex ++;
                    setVisibleTexts();
                }
        	}
    	}
    }
    
    public boolean contains(String rawText)
    {
    	for(TextBlock text : texts)
    	{
    		if(text.getText().equals(rawText))
    			return true;
    	}
    	
    	return false;
    }
    
    protected void drawWithoutText(float x, float y, float width, float height, boolean scaledPos)
    {
    	super.draw(x, y, width, height, scaledPos);
        up.draw(super.getTR().x - up.getScaledWidth(), super.getTR().y);
        down.draw(getBR().x - down.getScaledWidth(), getBR().y - down.getScaledHeight());
    }
    /**
     * Returns box top right position including up/down button width
     */
    @Override
    protected Position getTR()
    {
    	return new Position((int)(super.getTR().x - up.getScaledWidth()) - getDis(10), super.getTR().y);  
    }
    /**
     * Sets list of text blocks that should be displayed in box
     * method checks each block height and add them to list only if height of all blocks in list does not exceed text box height  
     */
    private void setVisibleTexts()
    {
        visibleTexts.clear();
        
        if(firstIndex == 0)
        {
        	visibleTexts.add(texts.get(firstIndex));
        	return;
        }
        
        float textsHeight = 0;
        for(int i = firstIndex; i >= 0; i --)
        {
            TextBlock text = texts.get(i);
            textsHeight += text.getTextHeight() + 5f;
            //Log.addSystem("hs:" + textsHeight + "/sh:" + text.getTextHeight() + "/max:" + height); //TEST LINE
            if(textsHeight > height)
                return;
            else
                visibleTexts.add(text);
        }
    
    }
}
