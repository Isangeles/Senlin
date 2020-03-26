/*
 * SenlinGame.java
 *
 * Copyright 2017-2019 Dariusz Sikora <dev@isangeles.pl>
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

import java.io.File;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import pl.isangeles.senlin.states.*;
import pl.isangeles.senlin.util.Settings;

/**
 * Main game class, contains all game states
 *
 * @author Isangeles
 */
public class SenlinGame extends StateBasedGame {
  public static final String VERSION = "0.1.3";

  public SenlinGame(String name) {
    super(name);
  }

  @Override
  public void initStatesList(GameContainer container) throws SlickException {
    this.addState(new MainMenu());
    this.addState(new NewGameMenu());
    this.addState(new SettingsMenu());
    this.addState(new LoadMenu());
    this.enterState(0);
  }

  public static void main(String[] args) {
    // Set path to native lwjgl libs.
    System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
    // Create game container.
    try {
      AppGameContainer appGC =
          new AppGameContainer(
              new ScalableGame(
                  new SenlinGame("Senlin " + VERSION),
                  (int) Settings.getResolution()[0],
                  (int) Settings.getResolution()[1]));
      try {
        appGC.setDisplayMode(
            (int) Settings.getResolution()[0],
            (int) Settings.getResolution()[1],
            Settings.isFullscreen());
      } catch (SlickException e) {
        System.err.println(
            "engine_msg: Unsupported resolution: "
                + Settings.getResolution()[0]
                + "x"
                + Settings.getResolution()[1]
                + ", switching to system resolution...");
        Settings.setResolution(Settings.getSystemResolution());
        System.gc();
        appGC =
            new AppGameContainer(
                new ScalableGame(
                    new SenlinGame("Senlin " + VERSION),
                    (int) Settings.getResolution()[0],
                    (int) Settings.getResolution()[1]));
        appGC.setDisplayMode(
            (int) Settings.getResolution()[0], (int) Settings.getResolution()[1], false);
      }
      appGC.setTargetFrameRate(60);
      // appGC.setClearEachFrame(false);
      appGC.setIcon("icon.png");
      appGC.start();
    } catch (SlickException e) {
      System.err.println(e.getMessage());
    }
  }
}
