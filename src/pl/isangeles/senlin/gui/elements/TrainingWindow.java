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
package pl.isangeles.senlin.gui.elements;

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

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.core.skill.Training;
import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.gui.Button;
import pl.isangeles.senlin.gui.InterfaceObject;
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
	private List<SkillField> fields;
	private List<Skill> skills = new ArrayList<>();
	private Button train;
	private Button exit;
	private TrueTypeFont ttf;
	private SkillField markField;
	
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
		
		fields = new ArrayList<>();
		for(int i = 0; i < 10; i ++)
		{
			fields.add(new SkillField(gc));
		}
		train = new Button(GConnector.getInput("button/buttonS.png"), "uiTrainButton", false, TConnector.getText("ui", "trainWinTrain"), gc);
		exit = new Button(GConnector.getInput("button/buttonS.png"), "uiTrainExit", false, TConnector.getText("ui", "trainWinExit"), gc);
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		ttf = new TrueTypeFont(font.deriveFont(getSize(12f)), true);
	}
	
	@Override
	public void draw(float x, float y)
	{
		super.draw(x, y, false);
		train.draw(x + getDis(600), y + getDis(450), false);
		exit.draw(x+getDis(400), y+getDis(450), false);
		
		int qfFirstX = (int)(x + getDis(30));
		int qfFirstY = (int)(y + getDis(20));
		int column = 0;
		
		for(SkillField field : fields)
		{
			field.draw(qfFirstX, qfFirstY + ((field.getHeight() + getDis(10)) * column));
			column ++;
		}
		
		if(markField != null)
		    markField.drawDesc(x+getDis(285), y+getDis(20));
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.inter.ui.UiElement#update()
	 */
	@Override
	public void update() 
	{
		if(trainer != null)
		{
		    for(Skill skill : trainer.getSkills())
	        {
	            if(!skills.contains(skill))
	                addSkill(skill);
	        }
		}
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.inter.ui.UiElement#reset()
	 */
	@Override
	public void reset() 
	{
		moveMOA(Coords.getX("BR", 0), Coords.getY("BR", 0));
	}
	/**
	 * Opens training window
	 * @param trainer Game character with skills
	 */
	public void open(Character trainer)
	{
		openReq = true;
		this.trainer = trainer;
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
	/**
	 * Adds skill to window
	 * @param skill Skill object
	 */
	private void addSkill(Skill skill)
	{
		if(!player.getSkills().isKnown(skill))
		{
			skills.add(skill);
			for(SkillField field : fields)
			{
				if(field.isEmpty())
				{
					field.insertSkill(skill);
					return;
				}
			}
		}
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
                if(markField != null)
                {
                    try
                    {
                        markField.getTraining().teach(player);
                    } 
                    catch (SlickException | IOException| FontFormatException e)
                    {
                        Log.addSystem("train_win_skill_builder_fail_msg///" + e.getMessage());
                    }
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
	/**
	 * Inner class for training options
	 * @author Isangeles
	 *
	 */
	private class SkillField extends Button
	{
		private Training skillTraining;
		private TextBlock description;
		
		public SkillField(GameContainer gc) throws SlickException, FontFormatException, IOException 
		{
			super(GConnector.getInput("field/textBg.png"), "uiJournalMenuQField", false, "", gc, "");
		}
		
		@Override
		public void draw(float x, float y)
		{
			super.draw(x, y, false);
			if(!isEmpty())
				super.drawString(skillTraining.getName(), ttf);
		}
		
		public void drawDesc(float x, float y)
		{
		    description.draw(x, y);
		}
		/**
		 * Converts skill to training and inserts it into field
		 * @param skill
		 */
		public void insertSkill(Skill skill)
		{
			this.skillTraining = new Training(skill.getId());
			List<String> desc = skillTraining.getInfo();
			description = new TextBlock(desc.get(0), 50, ttf);
			for(int i = 1; i < desc.size(); i ++)
			{
			    description.addText(desc.get(i));
			}
		}
		/**
		 * Checks if field is empty
		 * @return True if field is empty, false otherwise
		 */
		public boolean isEmpty()
		{
			if(skillTraining == null)
				return true;
			else
				return false;
		}
		/**
		 * Returns training description
		 * @return TextBlock with text
		 */
		public TextBlock getDisc()
		{
		    return description;
		}
		/**
		 * Returns training from field
		 * @return Training object
		 */
		public Training getTraining()
		{
		    return skillTraining;
		}
		
		@Override
		public void mouseReleased(int button, int x, int y)
		{
			super.mouseReleased(button, x, y);
			
			if(button == Input.MOUSE_LEFT_BUTTON)
			{
				if(!isEmpty() && isMouseOver())
				{
				    markField = this;
				}
			}
		}
	}
}
