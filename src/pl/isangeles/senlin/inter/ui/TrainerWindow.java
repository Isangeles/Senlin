/*
 * TrainerWindow.java
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

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.inter.Button;
import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.inter.TextBlock;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;

/**
 * @author Isangeles
 *
 */
class TrainerWindow extends InterfaceObject implements UiElement 
{
	private Character player;
	private Character trainer;
	private List<SkillField> fields;
	private List<Skill> skills = new ArrayList<>();
	private Button train;
	private TrueTypeFont ttf;
	private TextBlock skillDesc;
	
	private boolean openReq;
	
	public TrainerWindow(GameContainer gc, Character player) throws SlickException, FontFormatException, IOException
	{
		super(GConnector.getInput("ui/background/journalBG.png"), "uiTrainerWinBg", false, gc);
		
		this.player = player;
		
		fields = new ArrayList<>();
		for(int i = 0; i < 10; i ++)
		{
			fields.add(new SkillField(gc));
		}
		train = new Button(GConnector.getInput("button/buttonS.png"), "uiTrainButton", false, "train", gc);
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		ttf = new TrueTypeFont(font.deriveFont(getSize(12f)), true);
	}
	
	@Override
	public void draw(float x, float y)
	{
		super.draw(x, y, false);
		train.draw(x + getDis(600), y + getDis(450), false);
		
		int qfFirstX = (int)(x + getDis(30));
		int qfFirstY = (int)(y + getDis(20));
		int column = 0;
		
		for(SkillField field : fields)
		{
			field.draw(qfFirstX, qfFirstY + ((field.getHeight() + getDis(10)) * column));
			column ++;
		}
		
		if(skillDesc != null)
			skillDesc.draw(x+getDis(285), y+getDis(20));
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.inter.ui.UiElement#update()
	 */
	@Override
	public void update() 
	{
		for(Skill skill : trainer.getSkills())
		{
			if(!skills.contains(skill))
				addSkill(skill);
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
	
	public void open(Character trainer)
	{
		openReq = true;
		this.trainer = trainer;
	}
	
	public void close()
	{
		openReq = false;
		reset();
	}
	
	public boolean isOpenReq()
	{
		return openReq;
	}
	
	private void addSkill(Skill skill)
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
	
	private class SkillField extends Button
	{
		private Skill skill;
		
		public SkillField(GameContainer gc) throws SlickException, FontFormatException, IOException 
		{
			super(GConnector.getInput("field/textBg.png"), "uiJournalMenuQField", false, "", gc, "");
		}
		
		@Override
		public void draw(float x, float y)
		{
			super.draw(x, y, false);
			if(!isEmpty())
				super.drawString(skill.getName(), ttf);
		}
		
		public void insertSkill(Skill skill)
		{
			this.skill = skill;
		}
		
		public boolean isEmpty()
		{
			if(skill == null)
				return true;
			else
				return false;
		}
		
		@Override
		public void mouseReleased(int button, int x, int y)
		{
			super.mouseReleased(button, x, y);
			
			if(button == Input.MOUSE_LEFT_BUTTON)
			{
				if(!isEmpty() && isMouseOver())
				{
					
				}
			}
		}
	}
}
