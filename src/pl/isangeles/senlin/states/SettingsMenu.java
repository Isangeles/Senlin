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

import pl.isangeles.senlin.inter.Button;
import pl.isangeles.senlin.inter.Message;
import pl.isangeles.senlin.inter.TextSwitch;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.Settings;
import pl.isangeles.senlin.util.TConnector;
/**
 * Game settings state class
 * @author Isangeles
 *
 */
public class SettingsMenu extends BasicGameState
{
	private TextSwitch switchResolution;
	private TextSwitch switchLanguage;
	private TextSwitch switchFOW;
	private Button buttBack;
	private Message message;
	
	private boolean backReq;
	private boolean changed;
	
    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException
    {
    	
    	try 
    	{
    		switchResolution = new TextSwitch(container, Settings.getResList(), ";");
			switchLanguage = new TextSwitch(container, "english;polish", ";");
			switchFOW = new TextSwitch(container, Settings.getFowTypes(), ";");
			buttBack = new Button(GConnector.getInput("button/buttonBack.png"), "BSB", false, "", container);
			message = new Message(container, "");
		} 
    	catch (FontFormatException | IOException e) 
    	{
			e.printStackTrace();
		}
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException
    {
    	switchResolution.draw(700, 400);
    	switchLanguage.draw(700, 550);
    	switchFOW.draw(700, 700);
    	buttBack.draw(10, 900);
    	if(changed)
    		message.show(TConnector.getText("menu", "settMessConf"));
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException
    {
    	if(backReq && !message.isOpen())
    	{
    		backReq = false;
    		File settingsFile = new File("settings.txt");
    		try 
    		{
				PrintWriter pw = new PrintWriter(settingsFile);
				pw.write(switchLanguage.getString());
				pw.write(";" + System.lineSeparator());
				pw.write(switchResolution.getString());
                pw.write(";" + System.lineSeparator());
                pw.write(switchFOW.getString());
                pw.write(";" + System.lineSeparator());
				pw.close();
			} 
    		catch (FileNotFoundException e) 
    		{
				e.printStackTrace();
			}
    		game.enterState(0);
    	}
    }
    
    @Override
    public void mouseReleased(int button, int x, int y)
    {
    	if(button == Input.MOUSE_LEFT_BUTTON && buttBack.isMouseOver())
    	{
    	    backReq = true;
    	    if(switchResolution.isChange())
    	    {
    	    	message.open();
    	    	changed = true;
    	    }
    	}
    }
    
    @Override
    public int getID()
    {
        return 3;
    }

}
