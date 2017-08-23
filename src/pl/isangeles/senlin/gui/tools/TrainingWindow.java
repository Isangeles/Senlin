/*
 * TrainingWindow.java
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
package pl.isangeles.senlin.gui.tools;

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

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Training;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.craft.Profession;
import pl.isangeles.senlin.core.craft.ProfessionTraining;
import pl.isangeles.senlin.core.craft.RecipeTraining;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.core.skill.SkillTraining;
import pl.isangeles.senlin.gui.Button;
import pl.isangeles.senlin.gui.InterfaceObject;
import pl.isangeles.senlin.gui.ScrollableList;
import pl.isangeles.senlin.gui.TextBlock;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for UI training window
 * @author Isangeles
 *
 */
class TrainingWindow extends InterfaceObject implements UiElement, MouseListener
{
	private Character player;
	private Character trainer;
	private ScrollableList trainList;
	private Training selectedTrain;
	private TextBlock trainInfo;
	private Button train;
	private Button exit;
	private TrueTypeFont ttf;
	
	private boolean openReq;
	/**
	 * Training window constructor
	 * @param gc Slick game container
	 * @param player Player character
	 * @throws SlickException
	 * @throws FontFormatException
	 * @throws IOException
	 */
	public TrainingWindow(GameContainer gc, Character player) throws SlickException, FontFormatException, IOException
	{
		super(GConnector.getInput("ui/background/journalBG.png"), "uiTrainerWinBg", false, gc);
		gc.getInput().addMouseListener(this);
		this.player = player;
		
		trainList  = new ScrollableList(10, gc);
		train = new Button(GConnector.getInput("button/buttonS.png"), "uiTrainButton", false, TConnector.getText("ui", "trainWinTrain"), gc);
		exit = new Button(GConnector.getInput("button/buttonS.png"), "uiTrainExit", false, TConnector.getText("ui", "uiClose"), gc);
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		ttf = new TrueTypeFont(font.deriveFont(getSize(12f)), true);
		trainInfo = new TextBlock("", 80, ttf);
	}
	
	@Override
	public void draw(float x, float y)
	{
		super.draw(x, y, false);
		train.draw(x + getDis(600), y + getDis(450), false);
		exit.draw(x+getDis(400), y+getDis(450), false);
		
		int qfFirstX = (int)(x + getDis(30));
		int qfFirstY = (int)(y + getDis(20));
		
		trainList.draw(qfFirstX, qfFirstY, false);
		trainInfo.draw(x + getDis(280), y + getDis(20));
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.inter.ui.UiElement#update()
	 */
	@Override
	public void update() 
	{
		Training train = (Training)trainList.getSelected();
		if(train != null && selectedTrain != train)
		{
			selectedTrain = train;
			trainInfo.clear();
			trainInfo.addText(selectedTrain.getInfo());
		}
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.inter.ui.UiElement#reset()
	 */
	@Override
	public void reset() 
	{
		moveMOA(Coords.getX("BR", 0), Coords.getY("BR", 0));
		trainList.setFocus(false);
	}
	/**
	 * Opens training window
	 * @param trainer Game character with skills
	 */
	public void open(Character trainer)
	{
		openReq = true;
		trainList.setFocus(true);
		Character newTrainer = trainer;
		if(newTrainer != null && newTrainer != this.trainer)
		{
			this.trainer = newTrainer;
			for(Training training : trainer.getTrainings())
			{
				trainList.add(training);
				trainList.update();
			}
		}
		
	}
	/**
	 * Closes window
	 */
	public void close()
	{
		openReq = false;
		reset();
	}
	/**
	 * Checks if window is opened
	 * @return True if window is opened, false otherwise
	 */
	public boolean isOpenReq()
	{
		return openReq;
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
            if(exit.isMouseOver())
                close();
            if(train.isMouseOver())
            {
            	try 
            	{
					if(selectedTrain.teach(player))
						Log.addInformation(TConnector.getText("ui", "trainWinTrain") + ": " + selectedTrain.getName());
					else
						Log.addInformation(TConnector.getText("ui", "trainWinFail") + ": " + selectedTrain.getName());
				} 
            	catch (SlickException | IOException | FontFormatException e1) 
            	{
                    Log.addSystem("train_win_skill_builder_fail_msg///" + e1.getMessage());
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
    }
	
}
