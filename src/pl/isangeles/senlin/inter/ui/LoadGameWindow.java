/*
 * LoadGameWindow.java
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
package pl.isangeles.senlin.inter.ui;

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.data.SaveEngine;
import pl.isangeles.senlin.inter.Button;
import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.inter.TextButton;
import pl.isangeles.senlin.inter.TextInput;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;

/**
 * @author Isangeles
 *
 */
class LoadGameWindow extends InterfaceObject implements UiElement, MouseListener
{
    private Button loadB;
    private Button exitB;
    private Button upB;
    private Button downB;
    private TextInput fileName;
    private List<SaveSlot> saveSlots;
    private List<File> saves = new ArrayList<>();
    private int startIndex;
    
    private File selSave;
    
    private boolean openReq;
    private boolean loadReq;
    
    public LoadGameWindow(GameContainer gc) throws SlickException, IOException, FontFormatException
    {
        super(GConnector.getInput("ui/background/saveBG.png"), "uiLoadGWinBg", false, gc);
        
        gc.getInput().addMouseListener(this);
        
        loadB = new Button(GConnector.getInput("button/buttonS.png"), "uiLoadGWinLoad", false, "load game", gc);
        exitB = new Button(GConnector.getInput("button/buttonS.png"), "uiLoadGWinExit", false, "exit", gc);
        upB = new Button(GConnector.getInput("button/buttonUp.png"), "uiLoadGWinUp", false, "", gc);
        downB = new Button(GConnector.getInput("button/buttonDown.png"), "uiLoadGWinDown", false, "", gc);
        fileName = new TextInput(GConnector.getInput("field/textFieldBG.png"), "uiLoadGWinFNameBg", false, 200f, 30f, gc);
        fileName.showBorder(false);
        saveSlots = new ArrayList<>();
        for(int i = 0; i < 9; i ++)
        {
            saveSlots.add(new SaveSlot(gc));
        }
    }
    /**
     * Draws window
     * @param x Position on x axis
     * @param y Position on y axis
     * @param g Slick graphics context
     */
    public void draw(float x, float y, Graphics g)
    {
        super.draw(x, y, false);
        
        loadB.draw(x+getDis(310), y+getDis(460), false);
        exitB.draw(x+getDis(20), y+getDis(460), false);
        upB.draw(x+getDis(355), y+getDis(25), false);
        downB.draw(x+getDis(355), y+getDis(425), false);
        fileName.draw(x+getDis(95), y+getDis(460));
        fileName.render(g);
        
        float firstSlotX = x + getDis(15);
        float firstSlotY = y + getDis(40);
        int row = 0;
        for(SaveSlot slot : saveSlots)
        {
            slot.draw(firstSlotX, firstSlotY + ((slot.getScaledHeight() + getDis(10)) * row), false);
            row ++;
        }
    }
    /**
     * Opens window
     */
    public void open()
    {
        openReq = true;
        loadSaves();
    }
    /**
     * Closes window
     */
    public void close()
    {
        openReq = false;
        reset();
    }
    
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.inter.ui.UiElement#update()
     */
    @Override
    public void update() 
    {
    }

    /* (non-Javadoc)
     * @see pl.isangeles.senlin.inter.ui.UiElement#reset()
     */
    @Override
    public void reset() 
    {
        moveMOA(Coords.getX("BR", 0), Coords.getY("BR", 0));
        saves.clear();
        for(SaveSlot slot : saveSlots)
        {
            slot.clear();
        }
        fileName.clear();
    }
    /**
     * Checks if window open is requested
     * @return
     */
    public boolean isOpenReq()
    {
        return openReq;
    }
    /**
     * Takes load request from window
     * @return True if save is requested, false otherwise
     */
    public boolean takeLoadReq()
    {
        boolean valueToReturn = loadReq;
        if(loadReq)
            loadReq = false;
        return valueToReturn;
    }
    /**
     * Returns current value of text field
     * @return String with current value of text field
     */
    public String getSaveName()
    {
        return fileName.getText();//.replace(".sgg", "");
    }
    /* (non-Javadoc)
     * @see org.newdawn.slick.ControlledInputReciever#inputEnded()
     */
    @Override
    public void inputEnded() 
    {
    }

    /* (non-Javadoc)
     * @see org.newdawn.slick.ControlledInputReciever#inputStarted()
     */
    @Override
    public void inputStarted() 
    {
    }

    /* (non-Javadoc)
     * @see org.newdawn.slick.ControlledInputReciever#isAcceptingInput()
     */
    @Override
    public boolean isAcceptingInput() 
    {
        return true;
    }

    /* (non-Javadoc)
     * @see org.newdawn.slick.ControlledInputReciever#setInput(org.newdawn.slick.Input)
     */
    @Override
    public void setInput(Input input) 
    {
    }

    /* (non-Javadoc)
     * @see org.newdawn.slick.MouseListener#mouseClicked(int, int, int, int)
     */
    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) 
    {
    }

    /* (non-Javadoc)
     * @see org.newdawn.slick.MouseListener#mouseDragged(int, int, int, int)
     */
    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) 
    {
    }

    /* (non-Javadoc)
     * @see org.newdawn.slick.MouseListener#mouseMoved(int, int, int, int)
     */
    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) 
    {
    }

    /* (non-Javadoc)
     * @see org.newdawn.slick.MouseListener#mousePressed(int, int, int)
     */
    @Override
    public void mousePressed(int button, int x, int y) 
    {
    }

    /* (non-Javadoc)
     * @see org.newdawn.slick.MouseListener#mouseReleased(int, int, int)
     */
    @Override
    public void mouseReleased(int button, int x, int y) 
    {
        if(button == Input.MOUSE_LEFT_BUTTON)
        {
            if(loadB.isMouseOver())
            {
                loadReq = true;
            }
            if(exitB.isMouseOver())
                close();
            if(upB.isMouseOver())
            {
                if(startIndex > 0)
                {
                    startIndex --;
                    updateSlots();
                }
            }
            if(downB.isMouseOver())
            {
                if(startIndex < saves.size()-1)
                {
                    startIndex ++;
                    updateSlots();
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see org.newdawn.slick.MouseListener#mouseWheelMoved(int)
     */
    @Override
    public void mouseWheelMoved(int change) 
    {
        if(isMouseOver())
        {
            if(change > 0)
            {
                if(startIndex > 0)
                {
                    startIndex --;
                    updateSlots();
                }
            }
            else
            {
                if(startIndex < saves.size()-1)
                {
                    startIndex ++;
                    updateSlots();
                }
            }
        }
    }
    /**
     * Loads save files to list
     */
    private void loadSaves()
    {
        File savesDir = new File(SaveEngine.SAVES_PATH);
        for(File save : savesDir.listFiles())
        {
            if(save.getName().endsWith(".ssg"))
            {
                saves.add(save);
                addSave(save);
            }
        }
    }
    /**
     * Adds saves files to list and insert them to slots
     */
    private void addSave(File save)
    {
        for(SaveSlot slot : saveSlots)
        {
            if(slot.isEmpty())
            {
                slot.insertSave(save);
                break;
            }
        }
    }
    /**
     * Clears all slots
     */
    private void clearSlots()
    {
        for(SaveSlot slot : saveSlots)
        {
            slot.clear();
        }
    }
    /**
     * Updates slots
     */
    private void updateSlots()
    {
        saves.clear();
        clearSlots();
        loadSaves();
    }
    /**
     * Selects specified save file
     * @param save Save game file
     */
    private void selectSave(File save)
    {
        selSave = save;
        fileName.setText(save.getName().replaceAll("[.]ssg$", ""));
    }
    
    private void unselectAll()
    {
        for(SaveSlot slot : saveSlots)
        {
            slot.unselect();
        }
    }

    /**
     * Inner class for saves slots
     * @author Isangeles
     *
     */
    private class SaveSlot extends TextButton
    {
        private File saveFile;
        private boolean select;
        /**
         * SaveSlot constructor
         * @param gc Slick game container
         * @throws SlickException 
         * @throws FontFormatException
         * @throws IOException
         */
        public SaveSlot(GameContainer gc) throws SlickException, FontFormatException, IOException
        {
            super(gc);
        }
        @Override
        public void draw(float x, float y, boolean scaledPos)
        {
            if(select)
                super.draw(x, y, Color.white, scaledPos);
            else
                super.draw(x, y, scaledPos);
        }
        /**
         * Inserts file into slot
         * @param saveFile
         */
        public void insertSave(File saveFile)
        {
            this.saveFile = saveFile;
            setLabel(saveFile.getName().replaceAll("[.]ssg$", ""));
        }
        /**
         * Clears slot(removes save file from slot)
         */
        public void clear()
        {
            saveFile = null;
        }
        
        public void unselect()
        {
            select = false;
        }
        /**
         * Returns save file inside slot
         * @return Save file
         */
        public File getSaveFile()
        {
            return saveFile;
        }
        /**
         * Checks if slot is empty
         * @return True if slot is empty, false otherwise
         */
        public boolean isEmpty()
        {
            return (saveFile == null);
        }
        
        @Override
        public void mouseReleased(int button, int x, int y)
        {
            if(openReq && button == Input.MOUSE_LEFT_BUTTON && isMouseOver())
            {
                selectSave(saveFile);
                unselectAll();
                select = true;
            }
        }
    }
}
