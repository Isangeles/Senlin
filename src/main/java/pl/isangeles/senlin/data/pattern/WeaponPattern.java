/*
 * WeaponPattern.java
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
import org.newdawn.slick.SlickException;
import pl.isangeles.senlin.core.bonus.Modifier;
import pl.isangeles.senlin.core.bonus.Modifiers;
import pl.isangeles.senlin.core.item.ItemMaterial;
import pl.isangeles.senlin.core.item.Weapon;
import pl.isangeles.senlin.core.item.WeaponType;
import pl.isangeles.senlin.core.req.Requirement;

/**
 * Class for weapons patterns
 *
 * @author Isangeles
 */
public class WeaponPattern {
  private final String id;
  private final String type;
  private final String material;
  private final int value;
  private final int minDmg;
  private final int maxDmg;
  private final Modifiers bonuses;
  private final List<String> equipEffects;
  private final List<String> hitEffects;
  private final List<Requirement> equipReq;
  private final String icon;
  private final String spriteSheet;
  /**
   * Weapon pattern constructor
   *
   * @param id Item ID
   * @param type Item type name
   * @param material Item material name
   * @param value Item value
   * @param minDmg Weapon minimal damage
   * @param maxDmg Weapon maximal damage
   * @param bonuses Item bonuses
   * @param equipEffects List with IDs of all equip effects
   * @param hitEffects List with IDs of all hit effects
   * @param icon Item UI icon
   * @param spriteSheet Item sprite sheet
   */
  public WeaponPattern(
      String id,
      String type,
      String material,
      int value,
      int minDmg,
      int maxDmg,
      List<Modifier> bonuses,
      List<String> equipEffects,
      List<String> hitEffects,
      List<Requirement> equipReq,
      String icon,
      String spriteSheet) {
    this.id = id;
    this.type = type;
    this.material = material;
    this.value = value;
    this.minDmg = minDmg;
    this.maxDmg = maxDmg;
    this.bonuses = new Modifiers();
    this.bonuses.addAll(bonuses);
    this.equipEffects = equipEffects;
    this.hitEffects = hitEffects;
    this.equipReq = equipReq;
    this.icon = icon;
    this.spriteSheet = spriteSheet;
  }
  /**
   * Returns pattern weapon ID
   *
   * @return String with weapon ID
   */
  public String getId() {
    return id;
  }
  /**
   * Creates new weapons from this pattern
   *
   * @param gc Slick game container
   * @return New weapon object
   * @throws SlickException
   * @throws IOException
   * @throws FontFormatException
   */
  public Weapon make(GameContainer gc) throws SlickException, IOException, FontFormatException {
    if (spriteSheet.equals("default") || spriteSheet.equals(""))
      return new Weapon(
          id,
          WeaponType.fromName(type),
          ItemMaterial.fromName(material),
          value,
          minDmg,
          maxDmg,
          bonuses,
          equipEffects,
          hitEffects,
          equipReq,
          icon,
          gc);
    else
      return new Weapon(
          id,
          WeaponType.fromName(type),
          ItemMaterial.fromName(material),
          value,
          minDmg,
          maxDmg,
          bonuses,
          equipEffects,
          hitEffects,
          equipReq,
          icon,
          spriteSheet,
          gc);
  }

  /**
   * Creates new weapons from this pattern
   *
   * @param gc Slick game container
   * @param serial Serial number for item
   * @return New weapon object
   * @throws SlickException
   * @throws IOException
   * @throws FontFormatException
   */
  public Weapon make(GameContainer gc, long serial)
      throws SlickException, IOException, FontFormatException {
    if (spriteSheet.equals("default") || spriteSheet.equals(""))
      return new Weapon(
          id,
          serial,
          WeaponType.fromName(type),
          ItemMaterial.fromName(material),
          value,
          minDmg,
          maxDmg,
          bonuses,
          equipEffects,
          hitEffects,
          equipReq,
          icon,
          gc);
    else
      return new Weapon(
          id,
          serial,
          WeaponType.fromName(type),
          ItemMaterial.fromName(material),
          value,
          minDmg,
          maxDmg,
          bonuses,
          equipEffects,
          hitEffects,
          equipReq,
          icon,
          spriteSheet,
          gc);
  }
}
