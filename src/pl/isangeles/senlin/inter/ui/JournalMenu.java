/*
 * JournalMenu.java
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

import pl.isangeles.senlin.inter.Button;
import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.inter.TextBlock;
import pl.isangeles.senlin.quest.Quest;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.core.Character;
/**
 * Class for UI journal menu
 * @author Isangeles
 * 
 */
class JournalMenu extends InterfaceObject implements UiElement 
{
	private Character player;
	private List<QuestField> questFields;
	private List<Quest> quests = new ArrayList<>();
	private TrueTypeFont ttf;
	private TextBlock questDesc;
	/**
	 * Journal menu constructor
	 * @param gc Slick game container
	 * @param player Player character
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public JournalMenu(GameContainer gc, Character player) throws SlickException, IOException, FontFormatException 
	{
		super(GConnector.getInput("ui/background/journalBG.png"), "uiJournalMenuBg", false, gc);
		
		this.player = player;
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		ttf = new TrueTypeFont(font.deriveFont(12f), true);
		
		questFields = new ArrayList<>();
		for(int i = 0; i < 10; i ++)
		{
			questFields.add(new QuestField(gc));
		}
	}
	
	@Override
	public void draw(float x, float y)
	{
		super.draw(x, y, false);
		
		ttf.drawString(x + getDis(90), y + getDis(5), TConnector.getText("ui", "jMenuQuests"));
		
		int qfFirstX = (int)(x + getDis(30));
		int qfFirstY = (int)(y + getDis(20));
		int column = 0;
		
		for(QuestField field : questFields)
		{
			field.draw(qfFirstX, qfFirstY + ((field.getHeight() + getDis(10)) * column));
			column ++;
		}
		
		if(questDesc != null)
			questDesc.draw(x + getDis(285), y + getDis(20));
		
		moveMOA(super.x, super.y);
	}

	@Override
	public void update()
	{
		for(Quest quest : player.getQuests())
		{
			if(!quests.contains(quest))
			{
				addQuest(quest);
			}
		}
	}

	@Override
	public void reset() 
	{
		moveMOA(Coords.getX("BR", 0), Coords.getY("BR", 0));
		questDesc = null;
	}
	
	private void addQuest(Quest quest)
	{
		quests.add(quest);
		for(QuestField field : questFields)
		{
			if(field.isEmpty())
			{
				field.insertQ(quest);
				return;
			}
		}
	}
	
	private void setActiveQuest(Quest quest)
	{
		questDesc = new TextBlock(quest.getInfo()[0], 40, ttf);
		for(int i = 1; i < quest.getInfo().length; i ++)
		{
			questDesc.addText(quest.getInfo()[i]);
		}
	}

	private class QuestField extends Button
	{
		private Quest quest;
		
		public QuestField(GameContainer gc) throws SlickException, FontFormatException, IOException 
		{
			super(GConnector.getInput("field/textBg.png"), "uiJournalMenuQField", false, "", gc, "");
		}
		
		@Override
		public void draw(float x, float y)
		{
			super.draw(x, y, false);
			if(!isEmpty())
				super.drawString(quest.getName(), ttf);
		}
		
		public void insertQ(Quest quest)
		{
			this.quest = quest;
		}
		
		public boolean isEmpty()
		{
			if(quest == null)
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
					setActiveQuest(quest);
			}
		}
	}
}
