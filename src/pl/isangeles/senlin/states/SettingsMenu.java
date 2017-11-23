/*
 * SettingsMenu.java
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
package pl.isangeles.senlin.states;

import java.awt.FontFormatException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import pl.isangeles.senlin.core.Attribute;
import pl.isangeles.senlin.gui.Button;
import pl.isangeles.senlin.gui.Message;
import pl.isangeles.senlin.gui.Switch;
import pl.isangeles.senlin.gui.TextSwitch;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.Settings;
import pl.isangeles.senlin.util.Size;
import pl.isangeles.senlin.util.TConnector;
/**
 * Game settings state class
 * @author Isangeles
 *
 */
public class SettingsMenu extends BasicGameState
{
	private TextSwitch resolution;
	private TextSwitch language;
	private TextSwitch fow;
	private TextSwitch mapRender;
	private Switch effectsVol;
	private Switch musicVol;
	private Button buttBack;
	private Message message;
	
	private boolean backReq;
	private boolean changed;
	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException
    {
    	try 
    	{
    		resolution = new TextSwitch(container, TConnector.getText("ui", "settRes"), Settings.getResList());
			language = new TextSwitch(container, TConnector.getText("ui", "settLang"), Settings.getLangList());
			fow = new TextSwitch(container, TConnector.getText("ui", "settFow"), Settings.getFowTypes());
			mapRender = new TextSwitch(container, TConnector.getText("ui", "settMRen"), Settings.getMapRenderTypes());
			effectsVol = new Switch(container, TConnector.getText("ui", "settEVol"), (int)(Settings.getEffectsVol()*100), new Attribute(100));
            musicVol = new Switch(container, TConnector.getText("ui", "settMVol"), (int)(Settings.getMusicVol()*100), new Attribute(100));
			
			buttBack = new Button(GConnector.getInput("button/buttonBack.png"), "BSB", false, "", container);
			message = new Message(container);
		} 
    	catch (FontFormatException | IOException e) 
    	{
			e.printStackTrace();
		}
    }
    /* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException
    {
    	resolution.draw(700, 400, true);
    	language.draw(700, 550, true);
    	fow.draw(700, 700, true);
    	mapRender.draw(700, 850, true);
    	effectsVol.draw(700, 1000, true);
        musicVol.draw(1000, 400, true);
    	buttBack.draw(10, 900, true);
    	if(message.isOpenReq())
    		message.draw();
    }
    /* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException
    {
    	if(backReq && !message.isOpenReq())
    	{
    		backReq = false;
    		applySettings();
    		Settings.saveSettings();
    		MainMenu mm = (MainMenu)game.getState(0);
    		mm.replayMusic();
    		game.enterState(0);
    	}
    }
    
    @Override
    public void mouseReleased(int button, int x, int y)
    {
    	if(button == Input.MOUSE_LEFT_BUTTON && buttBack.isMouseOver())
    	{
    	    backReq = true;
    	    if(resolution.isChange())
    	    {
    	    	message.open(TConnector.getText("menu", "settMessConf"));
    	    	changed = true;
    	    }
    	}
    }
    /* (non-Javadoc)
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
    @Override
    public int getID()
    {
        return 3;
    }
    /**
     * Applies settings
     */
    private void applySettings()
    {
        Settings.setLang(language.getString());
        Settings.setResolution(new Size(resolution.getString().replace('x', ';')));
        Settings.setFowType(fow.getString());
        Settings.setMapRenderType(mapRender.getString());
        Settings.setEffectsVol((float)effectsVol.getValue()/100);
        Settings.setMusicVol((float)musicVol.getValue()/100);
    }
}