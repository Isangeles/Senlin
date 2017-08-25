/*
 * CharacterWindow.java
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
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.gui.InterfaceObject;
import pl.isangeles.senlin.gui.Portrait;
import pl.isangeles.senlin.gui.TextBlock;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.data.GBase;

/**
 * Class for UI character window
 * @author Isangeles
 *
 */
class CharacterWindow extends InterfaceObject implements UiElement, MouseListener
{
	private Character player;
	
	private TrueTypeFont ttf;
	
	private TextBlock upInfoA;
	private TextBlock upInfoB;
	private TextBlock downInfoA;
	private TextBlock downInfoB;
	
	private boolean openReq;
	private boolean focus;
	
	public CharacterWindow(GameContainer gc, Character player) throws SlickException, IOException
	{
		super(GConnector.getInput("ui/background/infoBG.png"), "uiCharWinBg", false, gc);
		gc.getInput().addMouseListener(this);
		
		this.player = player;
		
		Font font = GBase.getFont("mainUiFont");
		ttf = new TrueTypeFont(font.deriveFont(getSize(13f)), true);
		
		upInfoA = new TextBlock(30, ttf);
		upInfoB = new TextBlock(30, ttf);
		downInfoA = new TextBlock(60, ttf);
		downInfoB = new TextBlock(60, ttf);
	}
	
	@Override
	public void draw(float x, float y)
	{
		super.draw(x, y, false);
		
		player.getPortrait().draw(x+getDis(12), y+getDis(40), getSize(125), getSize(185));
		
		upInfoA.draw(x+getDis(160), y+getDis(45));
		upInfoB.draw(x+getDis(340), y+getDis(45));
		
		downInfoA.draw(x+getDis(10), y+getDis(255));
		downInfoB.draw(x+getDis(260), y+getDis(255));
	}
	
	public void open()
	{
		openReq = true;
		focus = true;
		
		String[] info = getCharStats();
		for(int i = 0; i < info.length && i < 9; i ++)
		{
			if(i < 5)
				upInfoA.addText(info[i]);
			else
				upInfoB.addText(info[i]);
		}
		for(int i = 8; i < info.length; i ++)
		{
			if(i < 13)
				downInfoA.addText(info[i]);
			else
				downInfoB.addText(info[i]);
		}
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.tools.UiElement#close()
	 */
	@Override
	public void close() 
	{
		openReq = false;
		reset();
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.tools.UiElement#update()
	 */
	@Override
	public void update()
	{
	}

	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.tools.UiElement#reset()
	 */
	@Override
	public void reset() 
	{
		focus = false;
		upInfoA.clear();
		upInfoB.clear();
		downInfoA.clear();
		downInfoB.clear();
		moveMOA(Coords.getX("BR", 0), Coords.getY("BR", 0));
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
		// TODO Auto-generated method stub
		
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
		return focus;
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#setInput(org.newdawn.slick.Input)
	 */
	@Override
	public void setInput(Input input) 
	{
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseClicked(int, int, int, int)
	 */
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) 
	{
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseDragged(int, int, int, int)
	 */
	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) 
	{
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseMoved(int, int, int, int)
	 */
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) 
	{
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mousePressed(int, int, int)
	 */
	@Override
	public void mousePressed(int button, int x, int y) 
	{
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseReleased(int, int, int)
	 */
	@Override
	public void mouseReleased(int button, int x, int y) 
	{
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseWheelMoved(int)
	 */
	@Override
	public void mouseWheelMoved(int change) 
	{
		// TODO Auto-generated method stub
		
	}

    /**
     * Builds and returns string with various player statistics
     * @return String with player statistics
     */
    private String[] getCharStats()
    {
    	return new String[]{player.getName(), TConnector.getText("ui", "levelName") + ": " + player.getLevel(), 
    			TConnector.getText("ui", "expName") + ": " + player.getExperience() + "/" + player.getMaxExperience(), TConnector.getText("ui", "guildName") + ": " + player.getGuild().toString(), 
    			TConnector.getText("ui", "hpName") + ": " + player.getHealth(), TConnector.getText("ui", "manaName") + ": " + player.getMagicka(), TConnector.getText("ui", "lpName") + ": " + player.getLearnPoints(),
    			TConnector.getText("ui", "hastName") + ": " + player.getAttributes().getHaste(), TConnector.getText("ui", "dmgName") + ": " + player.getDamage()[0] + "-" + player.getDamage()[1], 
    			TConnector.getText("ui", "armRat") + ": " + player.getArmorRating(), TConnector.getText("ui", "dodgeCh") + ": " + player.getDodgeChance() + "%", 
    			TConnector.getText("ui", "attStrName") + ": " + player.getAttributes().getStr(), TConnector.getText("ui", "attConName") + ": " + player.getAttributes().getCon(), 
    			TConnector.getText("ui", "attDexName") + ": " + player.getAttributes().getDex(), TConnector.getText("ui", "attIntName") + ": " + player.getAttributes().getInt(), 
    			TConnector.getText("ui", "attWisName") + ": " + player.getAttributes().getWis()};
    }
}
