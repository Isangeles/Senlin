/*
 * SettingsMenu.java
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import pl.isangeles.senlin.core.Attribute;
import pl.isangeles.senlin.gui.Button;
import pl.isangeles.senlin.gui.Message;
import pl.isangeles.senlin.gui.ObjectSwitch;
import pl.isangeles.senlin.gui.Switch;
import pl.isangeles.senlin.gui.Switchable;
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
	private ObjectSwitch resolution;
	private ObjectSwitch language;
	private ObjectSwitch fow;
	private ObjectSwitch mapRender;
	private ObjectSwitch module;
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
    		List<Switchable> resValues = new ArrayList<>();
    		for(String val : Settings.getResList())
    		{
    			Setting res = new Setting(val, val);
    			resValues.add(res);
    		}
    		List<Switchable> langValues = new ArrayList<>();
    		for(String val : Settings.getLangList())
    		{
    			Setting lang = new Setting(val, TConnector.getText("ui", "sValue_" + val));
    			langValues.add(lang);
    		}
    		List<Switchable> fowValues = new ArrayList<>();
    		for(String val : Settings.getFowTypes())
    		{
    			Setting fow = new Setting(val, TConnector.getText("ui", "sValue_" + val));
    			fowValues.add(fow);
    		}
    		List<Switchable> mRenderValues = new ArrayList<>();
    		for(String val : Settings.getMapRenderTypes())
    		{
    			Setting mRender = new Setting(val, TConnector.getText("ui", "sValue_" + val));
    			mRenderValues.add(mRender);
    		}
    		List<Switchable> modulesNames = new ArrayList<>();
    		for(String val : Settings.getModulesNames())
    		{
    			Setting mName = new Setting(val, val);
    			modulesNames.add(mName);
    		}
    		resolution = new ObjectSwitch(container, TConnector.getText("ui", "settRes"), resValues);
			language = new ObjectSwitch(container, TConnector.getText("ui", "settLang"), langValues);
			fow = new ObjectSwitch(container, TConnector.getText("ui", "settFow"), fowValues);
			mapRender = new ObjectSwitch(container, TConnector.getText("ui", "settMRen"), mRenderValues);
			module = new ObjectSwitch(container, TConnector.getText("ui", "settMod"), modulesNames);
			effectsVol = new Switch(container, TConnector.getText("ui", "settEVol"), (int)(Settings.getEffectsVol()*100), new Attribute(100));
            musicVol = new Switch(container, TConnector.getText("ui", "settMVol"), (int)(Settings.getMusicVol()*100), new Attribute(100));
			
			buttBack = new Button(GConnector.getInput("button/buttonBack.png"), "BSB", false, "", container);
			message = new Message(container);
			
			setCurrentValues();
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
        module.draw(1000, 550, true);
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
        Settings.setLang(language.getValue());
        Settings.setResolution(new Size(resolution.getValue().replace('x', ';')));
        Settings.setFowType(fow.getValue());
        Settings.setMapRenderType(mapRender.getValue());
        Settings.setEffectsVol((float)effectsVol.getValue()/100);
        Settings.setMusicVol((float)musicVol.getValue()/100);
        Settings.setModuleName(module.getValue());
    }
    /**
     * Sets current values of game settings to settings switches 
     * @throws ArrayIndexOutOfBoundsException
     */
    private void setCurrentValues() throws ArrayIndexOutOfBoundsException
    {
    	String currentRes = Settings.getResolution()[0] + "x" + Settings.getResolution()[1];
    	resolution.setValue(currentRes);
    	language.setValue(Settings.getLang());
    	fow.setValue(Settings.getFowType());
    	mapRender.setValue(Settings.getMapRenderType());
    	module.setValue(Settings.getModuleName());
    }
    /**
     * Tuple class for settings switches values
     * @author Isangeles
     *
     */
    class Setting implements Switchable
    {
    	private String id;
    	private String name;
    	
    	public Setting(String id, String name)
    	{
    		this.id = id;
    		this.name = name;
    	}
		/* (non-Javadoc)
		 * @see pl.isangeles.senlin.gui.Switchable#getName()
		 */
		@Override
		public String getName() 
		{
			return name;
		}
		/* (non-Javadoc)
		 * @see pl.isangeles.senlin.gui.Switchable#getId()
		 */
		@Override
		public String getId() 
		{
			return id;
		}
    	
    }
}