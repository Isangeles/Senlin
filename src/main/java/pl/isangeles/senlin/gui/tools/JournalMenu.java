/*
 * JournalMenu.java
 * 
 * Copyright 2017 Dariusz Sikora <dev@isangeles.pl>
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

import pl.isangeles.senlin.states.GameWorld;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.quest.Quest;
import pl.isangeles.senlin.data.GBase;
import pl.isangeles.senlin.gui.Button;
import pl.isangeles.senlin.gui.InterfaceObject;
import pl.isangeles.senlin.gui.ScrollableList;
import pl.isangeles.senlin.gui.TextBlock;
/**
 * Class for UI journal menu
 * @author Isangeles
 * 
 */
class JournalMenu extends InterfaceObject implements UiElement, MouseListener
{
	private Character player;
	private GameWorld gw;
	private ScrollableList questsList;
	private Quest selectedQuest;
	private TrueTypeFont ttf;
	private TextBlock questDesc;
	private Button closeB;
	private boolean openReq;
	/**
	 * Journal menu constructor
	 * @param gc Slick game container
	 * @param player Player character
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public JournalMenu(GameContainer gc, GameWorld gw, Character player) throws SlickException, IOException, FontFormatException 
	{
		super(GConnector.getInput("ui/background/journalBG.png"), "uiJournalMenuBg", false, gc);
		gc.getInput().addMouseListener(this);
		this.player = player;
		this.gw = gw;
		
		Font font = GBase.getFont("mainUiFont");
		ttf = new TrueTypeFont(font.deriveFont(12f), true);
		
		questsList = new ScrollableList(10, gc);
		questDesc = new TextBlock(50, ttf);
		closeB = new Button(GConnector.getInput("button/buttonS.png"), "uiButtonClose", false, TConnector.getText("ui", "winClose"), gc);
	}
	
	@Override
	public void draw(float x, float y)
	{
		super.draw(x, y, false);
		
		ttf.drawString(x + getDis(90), y + getDis(5), TConnector.getText("ui", "jMenuQuests"));
		
		int qfFirstX = (int)(x + getDis(30));
		int qfFirstY = (int)(y + getDis(20));
		
		questsList.draw(qfFirstX, qfFirstY, false);

		questDesc.draw(x + getDis(285), y + getDis(20));
		closeB.draw(x + getDis(580), y + getDis(440), false);
		
		moveMOA(super.x, super.y);
	}

	@Override
	public void update()
	{
		questsList.update();
		
		Quest quest = (Quest)questsList.getSelected();
		if(quest != null && selectedQuest != quest)
		{
			selectedQuest = quest;
			questDesc.clear();
			for(String infoLine : quest.getInfo())
			{
				questDesc.addText(infoLine);
			}
		}
	}

	@Override
	public void reset() 
	{
		moveMOA(Coords.getX("BR", 0), Coords.getY("BR", 0));
		selectedQuest = null;
		questsList.clear();
		questDesc.clear();
		questsList.setFocus(false);
	}
	
	public void open()
	{
		openReq = true;
		questsList.addAll(player.getQuests());
		questsList.setFocus(true);
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
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#isAcceptingInput()
	 */
	@Override
	public boolean isAcceptingInput() 
	{
		return openReq;
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
			if(closeB.isMouseOver())
				close();
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
