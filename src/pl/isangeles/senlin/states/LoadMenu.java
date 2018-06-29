/*
 * LoadMenu.java
 * 
 * Copyright 2017-2018 Dariusz Sikora <darek@pc-solus>
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
package pl.isangeles.senlin.states;

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.data.save.SaveEngine;
import pl.isangeles.senlin.graphic.Sprite;
import pl.isangeles.senlin.gui.Button;
import pl.isangeles.senlin.gui.InfoField;
import pl.isangeles.senlin.gui.TextButton;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;

/**
 * @author Isangeles
 *
 */
public class LoadMenu extends BasicGameState
{
	private Sprite background;
    private Image listBg;
    private Button backB;
    private Button loadB;
    private Button upB;
    private Button downB;
    private File selSave;
    private List<File> saves = new ArrayList<>(); 
    private List<SaveSlot> saveSlots;
    private int startIndex;
    
    private boolean backReq;
    private boolean loadReq;
    
    public LoadMenu()
    {
    }
    /* (non-Javadoc)
     * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
     */
    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException
    {
        try
        {
        	background = new Sprite(GConnector.getInput("field/mainMenuBg.png"), "menuBg", false);
            listBg = new Image(GConnector.getInput("field/messageBG.png"), "loadMenuBg", false);
            backB = new Button(GConnector.getInput("button/buttonS.png"), "loadMenuBack", false, "back", container);
            loadB = new Button(GConnector.getInput("button/buttonS.png"), "loadMenuLoad", false, "load", container);
            upB = new Button(GConnector.getInput("button/buttonUp.png"), "loadMenuUp", false, "", container);
            downB = new Button(GConnector.getInput("button/buttonDown.png"), "loadMenuDown", false, "", container);
            
            saveSlots = new ArrayList<>();
            for(int i = 0; i < 10; i ++)
            {
                saveSlots.add(new SaveSlot(container));
            }
        } 
        catch (FontFormatException | IOException e)
        {
            e.printStackTrace();
        }

        loadSaves();
    }

    /* (non-Javadoc)
     * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException
    {
        float bgX = Coords.getX("CE", -400);
        float bgY = Coords.getY("CE", -250);
        background.drawFullScreen();
        listBg.draw(bgX, bgY, Coords.getSize(800), Coords.getDis(500));
        loadB.draw(bgX+Coords.getDis(700), bgY+Coords.getDis(460), false);
        backB.draw(bgX+Coords.getDis(20), bgY+Coords.getDis(460), false);
        upB.draw(bgX+Coords.getDis(700), bgY+Coords.getDis(25), false);
        downB.draw(bgX+Coords.getDis(700), bgY+Coords.getDis(425), false);
        
        float firstSlotX = bgX+Coords.getDis(300);
        float firstSlotY = bgY+Coords.getDis(20);
        int row = 0;
        for(SaveSlot slot : saveSlots)
        {
            slot.draw(firstSlotX, firstSlotY + ((slot.getScaledHeight() + 10) * row), false);
            row ++;
        }
    }

    /* (non-Javadoc)
     * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
     */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException
    {
        if(backReq)
        {
            backReq = false;
            game.enterState(0);
        }
        if(loadReq)
        {
            loadReq = false;
            game.addState(new LoadingScreen(selSave.getName().replaceAll("[.]ssg$", "")));
            game.getState(4).init(container, game);
            game.enterState(4);
        }
    }
    
    @Override
    public void mouseReleased(int button, int x, int y)
    {
        if(button == Input.MOUSE_LEFT_BUTTON)
        {
            if(backB.isMouseOver())
                backReq = true;
            if(loadB.isMouseOver())
                loadReq = true;
        }
    }

    /* (non-Javadoc)
     * @see org.newdawn.slick.state.BasicGameState#getID()
     */
    @Override
    public int getID()
    {
        return 4;
    }
    /**
     * Loads save files to list
     */
    private void loadSaves()
    {
        File savesDir = new File(SaveEngine.SAVES_PATH);
        if(!savesDir.exists())
        	savesDir.mkdirs();
        
        for(File save : savesDir.listFiles())
        {
            if(save.getName().endsWith(".ssg"))
            {
                saves.add(save);
                addSave(save);
            }
        }
    }
    private void unselectAll()
    {
        for(SaveSlot slot : saveSlots)
        {
            slot.unselect();
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
     * Selects specified save file
     * @param save Save game file
     */
    private void selectSave(File save)
    {
        selSave = save;
    }
    /**
     * Inner class for saves slots
     * @author Isangeles
     *
     */
    private class SaveSlot extends TextButton
    {
        private File saveFile;
        private Image selectTex;
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
            selectTex = new Image(GConnector.getInput("field/select.png"), "sSlotSelect", false);
        }
        @Override
        public void draw(float x, float y, boolean scaledPos)
        {
            if(select)
            	selectTex.draw(x, y, getScale());
            
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
        /**
         * Deselects slot
         */
        public void unselect()
        {
            select = false;
        }
        @Override
        public void mouseReleased(int button, int x, int y)
        {
            if(button == Input.MOUSE_LEFT_BUTTON && isMouseOver())
            {
                unselectAll();
                selectSave(saveFile);
                select = true;
            }
        }
    }
}
