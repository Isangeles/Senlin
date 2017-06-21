/*
 * SaveGameWindow.java
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
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.data.SaveMaker;
import pl.isangeles.senlin.inter.Button;
import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.inter.TextButton;
import pl.isangeles.senlin.inter.TextInput;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;

/**
 * Class for save game window
 * @author Isangeles
 *
 */
class SaveGameWindow extends InterfaceObject implements UiElement, MouseListener
{
	private Button saveB;
	private Button exitB;
	private Button upB;
	private Button downB;
	private TextInput fileName;
	private List<SaveSlot> saveSlots;
	private List<File> saves = new ArrayList<>();
	private int startIndex;
	
	private File selSave;
	
	private boolean openReq;
	private boolean saveReq;
	/**
	 * SaveGameWindow constructor
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public SaveGameWindow(GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		super(GConnector.getInput("ui/background/saveBG.png"), "uiSaveGWin", false, gc);
		
		gc.getInput().addMouseListener(this);
		
		saveB = new Button(GConnector.getInput("button/buttonS.png"), "uiSaveGWinSave", false, "save game", gc);
		exitB = new Button(GConnector.getInput("button/buttonS.png"), "uiSaveGWinExit", false, "exit", gc);
		upB = new Button(GConnector.getInput("button/buttonUp.png"), "uiSaveGWinUp", false, "", gc);
		downB = new Button(GConnector.getInput("button/buttonDown.png"), "uiSaveGWinDown", false, "", gc);
		fileName = new TextInput(GConnector.getInput("field/textFieldBG.png"), "uiSaveGWinFNameBg", false, 200f, 30f, gc);
		fileName.showBorder(false);
		saveSlots = new ArrayList<>();
		for(int i = 0; i < 9; i ++)
		{
			saveSlots.add(new SaveSlot(gc));
		}
	}
	
	public void draw(float x, float y, Graphics g)
	{
		super.draw(x, y, false);
		
		saveB.draw(x+getDis(310), y+getDis(460), false);
		exitB.draw(x+getDis(20), y+getDis(460), false);
		upB.draw(x+getDis(355), y+getDis(25), false);
		downB.draw(x+getDis(355), y+getDis(425), false);
		fileName.draw(x+getDis(95), y+getDis(460));
		fileName.render(g);
		
		float firstSlotX = x + getDis(15);
		float firstSlotY = y + getDis(40);
		int column = 0;
		for(SaveSlot slot : saveSlots)
		{
			slot.draw(firstSlotX, firstSlotY + ((slot.getScaledHeight() + getDis(10)) * column), false);
			column ++;
		}
	}
	
	public void open()
	{
		openReq = true;
		loadSaves();
	}
	
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
		if(openReq)
		{
			if(selSave != null)
				fileName.setText(selSave.getName());
		}
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

	public boolean isOpenReq()
	{
		return openReq;
	}
	
	public boolean takeSaveReq()
	{
		boolean valueToReturn = saveReq;
		if(saveReq)
			saveReq = false;
		return valueToReturn;
	}
	
	public String getSaveName()
	{
		return fileName.getText();
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
			if(saveB.isMouseOver())
			{
				saveReq = true;
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
	}
	
	private void loadSaves()
	{
		File savesDir = new File(SaveMaker.SAVES_PATH);
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
	
	private void clearSlots()
	{
		for(SaveSlot slot : saveSlots)
		{
			slot.clear();
		}
	}
	
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
		fileName.setText(save.getName().replaceAll(".ssg", ""));
	}
	/**
	 * Inner class for saves slots
	 * @author Isangeles
	 *
	 */
	private class SaveSlot extends TextButton
	{
		private File saveFile;
		
		public SaveSlot(GameContainer gc) throws SlickException, FontFormatException, IOException
		{
			super(gc);
		}
		
		public void insertSave(File saveFile)
		{
			this.saveFile = saveFile;
			setLabel(saveFile.getName());
		}
		
		public void clear()
		{
			saveFile = null;
		}
		
		public File getSaveFile()
		{
			return saveFile;
		}
		
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
			}
		}
	}

}
