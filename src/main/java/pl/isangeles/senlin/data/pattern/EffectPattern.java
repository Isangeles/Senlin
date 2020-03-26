/*
 * EffectPattern.java
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
package pl.isangeles.senlin.data.pattern;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.List;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import pl.isangeles.senlin.core.bonus.Modifier;
import pl.isangeles.senlin.core.bonus.Modifiers;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.effect.EffectSource;
import pl.isangeles.senlin.core.effect.EffectType;
import pl.isangeles.senlin.data.GBase;
import pl.isangeles.senlin.util.GConnector;

/**
 * Pattern for creating effects
 *
 * @author Isangeles
 */
public class EffectPattern {
  private String id;
  private String imgName;
  private Image icon;
  private EffectType type;
  private Modifiers bonuses;
  private int dot;
  private int duration;
  /**
   * Effect pattern constructor
   *
   * @param id Effect ID
   * @param imgName Name of image file for effect UI icon
   * @param hpMod Affect on target health points
   * @param manaMod Affect on target magicka points
   * @param attMod Affect on target attributes points
   * @param hasteMod Affect on target haste value
   * @param dodgeMod Affect on target dodge chance
   * @param dmgMod Affect on damage caused by target
   * @param dot Damage over time effect value (positive value heals target)
   * @param duration Effect duration
   * @param type Effect type
   * @throws IOException
   * @throws SlickException
   */
  public EffectPattern(
      String id, String imgName, List<Modifier> bonuses, int dot, int duration, String type) {
    this.id = id;
    this.imgName = imgName;
    try {
      icon = new Image(GConnector.getInput("icon/effect/" + imgName), id + "_icon", false);
    } catch (SlickException | IOException e) {
      icon = GBase.getImage("errorIcon");
    }
    this.type = EffectType.fromId(type);
    this.bonuses = new Modifiers();
    this.bonuses.addAll(bonuses);
    this.dot = dot;
    if (duration != -1) this.duration = duration * 1000;
    else this.duration = -1;
  }
  /**
   * Returns effect ID
   *
   * @return String with effect ID
   */
  public String getId() {
    return id;
  }
  /**
   * Builds new effect object from this pattern
   *
   * @param gc Slick game container
   * @param source Effect source, e.g. skill owner
   * @return New effect object
   * @throws SlickException
   * @throws IOException
   * @throws FontFormatException
   */
  public Effect make(EffectSource source, GameContainer gc)
      throws SlickException, IOException, FontFormatException {
    return new Effect(id, icon, bonuses, dot, duration, type, source, gc);
  }

  /**
   * Builds new effect object from this pattern(with saved source)
   *
   * @param gc Slick game container
   * @param savedSourceOwner Saved source owner serial ID
   * @param savedSource Saved source ID
   * @return New effect object
   * @throws SlickException
   * @throws IOException
   * @throws FontFormatException
   */
  public Effect make(String savedSourceOwner, String savedSource, GameContainer gc)
      throws SlickException, IOException, FontFormatException {
    return new Effect(id, icon, bonuses, dot, duration, type, savedSourceOwner, savedSource, gc);
  }
}
