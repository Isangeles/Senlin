/*
 * ABase.java
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
package pl.isangeles.senlin.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import pl.isangeles.senlin.util.AConnector;

/**
 * Class with pre-loaded sound effects loaded in main menu
 *
 * @author Isangeles
 */
public final class ABase {
  private static Map<String, Sound> effects = new HashMap<>();
  private static Map<Music, String> music = new HashMap<>();
  /** Private constructor to prevent initialization */
  private ABase() {}
  /**
   * Returns sound effect with specified name from base
   *
   * @param audioName Desired audio name
   * @return Sound or null if no such audio was found
   */
  public static Sound get(String audioName) {
    return effects.get(audioName);
  }
  /**
   * Loads base
   *
   * @throws NullPointerException
   * @throws SlickException
   * @throws IOException
   */
  public static void load() throws NullPointerException, SlickException, IOException {
    effects.put("spellHit", new Sound(AConnector.getInput("effects/spellHit.aif"), "spellHit.aif"));
  }
}
