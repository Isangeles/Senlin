/*
 * LoadingScreen.java
 *
 * Copyright 2017-2018 Dariusz Sikora <dev@isangeles.pl>
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
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.data.DialoguesBase;
import pl.isangeles.senlin.data.EffectsBase;
import pl.isangeles.senlin.data.GuildsBase;
import pl.isangeles.senlin.data.ItemsBase;
import pl.isangeles.senlin.data.NpcBase;
import pl.isangeles.senlin.data.ObjectsBase;
import pl.isangeles.senlin.data.QuestsBase;
import pl.isangeles.senlin.data.RecipesBase;
import pl.isangeles.senlin.data.ScenariosBase;
import pl.isangeles.senlin.data.SkillsBase;
import pl.isangeles.senlin.data.save.SaveEngine;
import pl.isangeles.senlin.data.save.SavedGame;
import pl.isangeles.senlin.gui.InfoField;
import pl.isangeles.senlin.gui.tools.UserInterface;
import pl.isangeles.senlin.util.Settings;
import pl.isangeles.senlin.util.Stopwatch;

/**
 * Class for game loading state
 *
 * @author Isangeles
 */
public class LoadingScreen extends BasicGameState {
  private static final int NEW_GAME = 0;
  private static final int SAVED_GAME = 1;

  private InfoField loadingInfo;
  private Character player;
  private SavedGame gameToLoad;
  private String saveName;
  private UserInterface ui;
  private CommandInterface cli;
  private GameWorld gw;
  private int loadType;
  private int loadCounter;
  /**
   * Creates new game loader
   *
   * @param player Player character
   */
  public LoadingScreen(Character player) {
    this.player = player;
    Global.setPlayer(player);
    loadType = NEW_GAME;
  }
  /**
   * Creates saved game loader
   *
   * @param gameToLoad Saved game
   */
  public LoadingScreen(String saveName) {
    this.saveName = saveName;
    loadType = SAVED_GAME;
  }
  /* (non-Javadoc)
   * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
   */
  @Override
  public void init(GameContainer container, StateBasedGame game) throws SlickException {
    try {
      loadingInfo = new InfoField(100f, 70f, "Loading...", container);
    } catch (IOException | FontFormatException e) {
      e.printStackTrace();
    }
  }
  /* (non-Javadoc)
   * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
   */
  @Override
  public void render(GameContainer container, StateBasedGame game, Graphics g)
      throws SlickException {
    g.clear();
    loadingInfo.draw(loadingInfo.atCenter().x, loadingInfo.atCenter().y, 250f, 100f, false);
  }
  /* (non-Javadoc)
   * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
   */
  @Override
  public void update(GameContainer container, StateBasedGame game, int delta)
      throws SlickException {
    try {
      if (loadType == NEW_GAME) {
        if (!loadNewGame(container, game)) container.exit();
      } else if (loadType == SAVED_GAME) {
        if (!loadSave(container, game)) container.exit();
      }
    } catch (IOException
        | FontFormatException
        | ParserConfigurationException
        | SAXException
        | InterruptedException e) {
      System.err.println("fail_to_load_game");
      e.printStackTrace();
      container.exit();
    }
  }
  /* (non-Javadoc)
   * @see org.newdawn.slick.state.BasicGameState#getID()
   */
  @Override
  public int getID() {
    return 4;
  }
  /**
   * Loads new game
   *
   * @param container Slick game container
   * @param game Slick game
   * @return True if no errors occurred, false otherwise
   * @throws IOException
   * @throws FontFormatException
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws SlickException
   * @throws InterruptedException
   */
  private boolean loadNewGame(GameContainer container, StateBasedGame game)
      throws IOException, FontFormatException, ParserConfigurationException, SAXException,
          SlickException, InterruptedException {
    // GameDataLoader gdLoad = new GameDataLoader(container);
    // ExecutorService exe = Executors.newFixedThreadPool(2);
    Stopwatch loadTimer = new Stopwatch();
    switch (loadCounter) {
      case 0:
        loadTimer.start(); // TEST loading time measurement
        Module.setDir(Settings.getModuleName());
        loadingInfo.setText("loding game data...");
        break;
      case 1:
        // exe.execute(gdLoad);
        // exe.awaitTermination(3, TimeUnit.MINUTES);
        EffectsBase.load(Module.getSkillsPath(), container);
        SkillsBase.load(Module.getSkillsPath(), container);
        ItemsBase.load(Module.getItemsPath(), container);
        RecipesBase.load(Module.getItemsPath());
        DialoguesBase.load(Module.getDBasePath());
        NpcBase.load(Module.getNpcsPath(), container);
        GuildsBase.load(Module.getGuildPath());
        QuestsBase.load(Module.getQuestsPath());
        ObjectsBase.load(Module.getChapterObjectsPath(), container);
        ObjectsBase.load(Module.getModuleObjectsPath(), container);
        ScenariosBase.load(Module.getAreaPath(), container);
        break;
      case 2:
        loadingInfo.setText("loading game world...");
        break;
      case 3:
        gw = new GameWorld(player, Module.getChapter(Module.getActiveChapterName(), container));
        game.addState(gw);
        game.getState(gw.getID()).init(container, game);
        break;
      case 4:
        loadingInfo.setText("loading user interface...");
        break;
      case 5:
        cli = new CommandInterface(player, gw);
        ui = new UserInterface(container, cli, player, gw);
        cli.setUiMan(ui);
        gw.setGui(ui);
        gw.setCli(cli);
        break;
      case 6:
        long time = loadTimer.stop();
        Log.addSystem("Loading time:" + time + "s"); // TEST loading time measurement
        System.out.println("Loading time:" + time + "s"); // TEST loading time measurement
        loadCounter = 0;
        game.enterState(gw.getID());
        break;
    }

    loadCounter++;
    return true;
  }
  /**
   * Loads game from save file
   *
   * @param container Slick game container
   * @param game Slick game
   * @return True if no errors occurred, false otherwise
   * @throws IOException
   * @throws FontFormatException
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws SlickException
   */
  private boolean loadSave(GameContainer container, StateBasedGame game)
      throws IOException, FontFormatException, ParserConfigurationException, SAXException,
          SlickException {
    Stopwatch loadTimer = new Stopwatch();
    switch (loadCounter) {
      case 0:
        loadTimer.start(); // TEST loading time measurement
        Module.setDir(Settings.getModuleName());
        loadingInfo.setText("loding game data...");
        break;
      case 1:
        EffectsBase.load(Module.getSkillsPath(), container);
        SkillsBase.load(Module.getSkillsPath(), container);
        ItemsBase.load(Module.getItemsPath(), container);
        RecipesBase.load(Module.getItemsPath());
        DialoguesBase.load(Module.getDBasePath());
        GuildsBase.load(Module.getGuildPath());
        NpcBase.load(Module.getNpcsPath(), container);
        QuestsBase.load(Module.getQuestsPath());
        ObjectsBase.load(Module.getChapterObjectsPath(), container);
        ObjectsBase.load(Module.getModuleObjectsPath(), container);
        ScenariosBase.load(Module.getAreaPath(), container);
        SaveEngine.loadModuleFrom(saveName);
        break;
      case 2:
        loadingInfo.setText("loading saved game...");
        break;
      case 3:
        gameToLoad = SaveEngine.load(saveName, container);
        player = gameToLoad.getPlayer();
        Global.setPlayer(gameToLoad.getPlayer());
        break;
      case 4:
        loadingInfo.setText("loading game world...");
        break;
      case 5:
        gw = new GameWorld(gameToLoad);
        game.addState(gw);
        game.getState(gw.getID()).init(container, game);
        break;
      case 6:
        loadingInfo.setText("loading user interface...");
        break;
      case 7:
        cli = new CommandInterface(player, gw);
        ui = new UserInterface(container, cli, player, gw);
        ui.setLayout(gameToLoad.getUiLayout());
        cli.setUiMan(ui);
        gw.setGui(ui);
        gw.setCli(cli);
        break;
      case 8:
        long time = loadTimer.stop();
        Log.addSystem("Loading time:" + time + "s"); // TEST loading time measurement
        System.out.println("Loading time:" + time + "s"); // TEST loading time measurement
        loadCounter = 0;
        game.enterState(gw.getID());
        break;
    }

    loadCounter++;
    return true;
  }
}
