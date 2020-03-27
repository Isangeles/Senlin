/*
 * SenlinGame.java
 *
 * Copyright 2017-2020 Dariusz Sikora <dev@isangeles.pl>
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

package pl.isangeles.senlin;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import pl.isangeles.senlin.states.LoadMenu;
import pl.isangeles.senlin.states.MainMenu;
import pl.isangeles.senlin.states.NewGameMenu;
import pl.isangeles.senlin.states.SettingsMenu;
import pl.isangeles.senlin.util.Settings;

import java.io.File;
import java.util.logging.Logger;

/**
 * Main game class, contains all game states.
 *
 * @author Isangeles
 */
public class SenlinGame extends StateBasedGame {
  public static final String VERSION = "0.1.4";
  private static final Logger LOG = Logger.getLogger(SenlinGame.class.getName());

  public SenlinGame(String name) {
    super(name);
  }

  public static void main(String[] args) {
    // Set path to native lwjgl libs.
    System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
    // Start game container.
    try {
      AppGameContainer gameContainer = createGameContainer();
      gameContainer.setTargetFrameRate(60);
      // appGC.setClearEachFrame(false);
      gameContainer.setIcon("icon.png");
      gameContainer.start();
    } catch (SlickException e) {
      LOG.info(String.format("Unable to start game container: %s", e.getMessage()));
    }
  }

  /**
   * Creates new game container.
   *
   * @return Game container.
   * @throws SlickException In case of exception while creating Slick AppGameContainer.
   */
  private static AppGameContainer createGameContainer() throws SlickException {
    SenlinGame game = new SenlinGame("Senlin " + VERSION);
    int resX = Settings.getResolution()[0];
    int resY = Settings.getResolution()[1];
    ScalableGame scalableGame = new ScalableGame(game, resX, resY);
    AppGameContainer gameContainer = new AppGameContainer(scalableGame);
    try {
      gameContainer.setDisplayMode(resX, resY, Settings.isFullscreen());
    } catch (SlickException e) {
      LOG.info(
          String.format(
              "Unable to set display mode: %s, switching to system resolution...", e.getMessage()));
      Settings.setResolution(Settings.getSystemResolution());
      System.gc();
      gameContainer = new AppGameContainer(scalableGame);
      gameContainer.setDisplayMode(resX, resY, false);
    }
    return gameContainer;
  }

  @Override
  public void initStatesList(GameContainer container) throws SlickException {
    this.addState(new MainMenu());
    this.addState(new NewGameMenu());
    this.addState(new SettingsMenu());
    this.addState(new LoadMenu());
    this.enterState(0);
  }
}
