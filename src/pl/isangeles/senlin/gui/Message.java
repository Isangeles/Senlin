/*
 * Message.java
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
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.util.*; 
/**
 * Class for game messages which consist of text, background and dismiss button
 * @author Isangeles
 *
 */
public class Message extends InterfaceObject implements MouseListener
{
    private String textMessage;
    protected Button buttonOk;
    private Font textFont;
    private TrueTypeFont textTtf;
    private boolean openReq;
    
    /**
     * Message constructor
     * @param gc GameContainer for superclass 
     * @param textMessage Text for message window
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    public Message(GameContainer gc) throws SlickException, IOException, FontFormatException
    {
        super(GConnector.getInput("field/messageBG.png"), "messageBg", false, gc);
        gc.getInput().addMouseListener(this);
       
        File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
        textFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        textTtf = new TrueTypeFont(textFont.deriveFont(12f), true);
        
        buttonOk = new Button(GConnector.getInput("button/buttonOK.png"), "buttOk", false, "", gc);
    }
    /**
     * If message should be shown
     * @return
     */
    public boolean isOpenReq()
    {
        return openReq;
    }
    /**
     * Force message to close
     */
    public void close()
    {
        openReq = false;
    }
    /**
     * Draws message
     */
    @Override
    public void draw(float x, float y, boolean scaledPos)
    {
        super.draw(x, y, scaledPos);
        textTtf.drawString(super.x+getDis(20), super.y+getDis(10), textMessage);
        buttonOk.draw(x+getDis(200), y+super.getScaledHeight()-getDis(50), false);
    }
    @Override
    public void draw()
    {
        draw(Coords.getX("CE", -200), Coords.getY("CE", -80), false);
        textTtf.drawString(super.x+getDis(20), super.y+getDis(10), textMessage);
        buttonOk.draw(x+getDis(200), y+super.getScaledHeight()-getDis(50), false);
    }
    /**
     * Sets text and opens message 
     * @param textMessage Text for message
     */
    public void open(String textMessage)
    {
    	this.textMessage = textMessage;
        openReq = true;
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
        return openReq;
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
        if(button == Input.MOUSE_LEFT_BUTTON && buttonOk.isMouseOver())
            close();
    }

    @Override
    public void mouseWheelMoved(int change)
    {
    }

}
