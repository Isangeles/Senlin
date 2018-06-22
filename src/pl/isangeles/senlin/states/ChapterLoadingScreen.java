/*
 * ChapterLoadingScreen.java
 * 
 * Copyright 2018 Dariusz Sikora <darek@pc-solus>
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
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.xml.sax.SAXException;

import pl.isangeles.senlin.cli.CommandInterface;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Module;
import pl.isangeles.senlin.data.DialoguesBase;
import pl.isangeles.senlin.data.GuildsBase;
import pl.isangeles.senlin.data.NpcBase;
import pl.isangeles.senlin.data.ObjectsBase;
import pl.isangeles.senlin.data.QuestsBase;
import pl.isangeles.senlin.data.ScenariosBase;
import pl.isangeles.senlin.gui.InfoField;
import pl.isangeles.senlin.gui.tools.UserInterface;
import pl.isangeles.senlin.util.Settings;
import pl.isangeles.senlin.util.Stopwatch;
import pl.isangeles.senlin.core.character.Character;

/**
 * Class to loading game chapters
 * @author Isangeles
 *
 */
public class ChapterLoadingScreen extends BasicGameState 
{
	private int loadCounter;
    private InfoField loadingInfo;
    private Character pc;
    private GameWorld gw;
    private String chapterName;
	/**
	 * Setup chapter loading screen
	 * loads next chapter of current module
	 * @param pc Player character
	 */
	public void setupLoad(Character pc)
	{
		this.pc = pc;
		chapterName = null;
		Module.nextChapter();
	}
	/**
	 * Setup chapter loading screen
	 * @param pc Player character
	 * @param chapterName Name of chapter to load
	 */
	public void setupLoad(Character pc, String chapterName)
	{
		this.pc = pc;
		this.chapterName = chapterName;
	} 
	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException 
	{
        try
        {
            loadingInfo = new InfoField(100f, 70f, chapterName + System.lineSeparator() + "Loading...", container);
        } 
        catch (IOException | FontFormatException e)
        {
            e.printStackTrace();
        }
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	{
        g.clear();
        loadingInfo.draw(loadingInfo.atCenter().x, loadingInfo.atCenter().y, 250f, 100f, false);
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
		try 
		{
			if(!loadChapter(container, game))
				container.exit();
		} 
		catch (ParserConfigurationException | SAXException | IOException | FontFormatException e) 
		{
			System.err.println("fail_to_game_chapter");
			e.printStackTrace();
			container.exit();
		}
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
	@Override
	public int getID() 
	{
		return 6;
	}
    /**
     * Reloads game for new chapter
     * @param container Slick game container
     * @param game Slick game
     * @return True if no errors occurred, false otherwise 
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws SlickException
     * @throws FontFormatException
     */
    private boolean loadChapter(GameContainer container, StateBasedGame game) 
    		throws ParserConfigurationException, SAXException, IOException, SlickException, FontFormatException
    {
    	//GameDataLoader gdLoad = new GameDataLoader(container);
    	//ExecutorService exe = Executors.newFixedThreadPool(2);
    	Stopwatch loadTimer = new Stopwatch();
    	switch(loadCounter)
		{
		case 0:
			loadTimer.start(); //TEST loading time measurement
		    loadingInfo.setText("loding game data...");
		    break;
		case 1:
			//exe.execute(gdLoad);
			//exe.awaitTermination(3, TimeUnit.MINUTES);
	        DialoguesBase.load(Module.getDBasePath());
            NpcBase.load(Module.getNpcsPath(), container);
	        GuildsBase.load(Module.getGuildPath());
            QuestsBase.load(Module.getQuestsPath());
            ObjectsBase.load(Module.getChapterObjectsPath(), container);
            ScenariosBase.load(Module.getAreaPath(), container);
		    break;
		case 2:
			loadingInfo.setText("loading game world...");
			break;
		case 3:
			gw = (GameWorld)game.getState(2);
			if(gw == null)
			{
				gw = new GameWorld(pc, Module.getChapter(Module.getActiveChapterName(), container));
		        game.addState(gw);
			}
			gw.setupWorld(pc, Module.getChapter(Module.getActiveChapterName(), container));
	        game.getState(gw.getID()).init(container, game);
	        break;
        case 4:
            loadingInfo.setText("loading user interface...");
            break;
        case 5:
        	CommandInterface cli = new CommandInterface(pc, gw);
        	UserInterface ui = new UserInterface(container, cli, pc, gw);
            cli.setUiMan(ui);
            gw.setGui(ui);
            gw.setCli(cli);
            break;
		case 6:
			long time = loadTimer.stop();
			Log.addSystem("Loading time:" + time + "s"); //TEST loading time measurement
			System.out.println("Loading time:" + time + "s"); //TEST loading time measurement
			loadCounter = 0;
	        game.enterState(gw.getID());
	        break;
		}
		
		loadCounter ++;
		return true;
    }

}
