/*
 * Armor.java
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
package pl.isangeles.senlin.core.item;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.List;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.bonus.Modifiers;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.data.GBase;
import pl.isangeles.senlin.graphic.AnimObject;
import pl.isangeles.senlin.gui.tools.ItemTile;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for armor parts TODO implement different sprite sheet for each armor material
 *
 * @author Isangeles
 */
public class Armor extends Equippable {
  public static final int FEET = 0, // TODO use armor type enum {@link ArmorType}
      HANDS = 1,
      OFFHAND = 2,
      CHEST = 3,
      HEAD = 4;
  private int armorRating;
  /**
   * Armor constructor
   *
   * @param id Item ID
   * @param name Item name
   * @param info Item description
   * @param type Armor type (0-5 value)
   * @param material Material of which item is made (0-4 value)
   * @param value Item value in gold
   * @param armRat Armor rating value
   * @param bonuses Armor bonuses to statistics
   * @param equippEffects List with IDs of all equip effects
   * @param equipReq List with equip requirements
   * @param reqLevel Required level (only for backward compatibility, now requirement in {@link
   *     Eqippable#equipReq})
   * @param imgName Name of image file in icon directory for item tile
   * @param gc Slick game container for item tile
   * @throws SlickException
   * @throws IOException
   * @throws FontFormatException
   */
  public Armor(
      String id,
      ArmorType type,
      ItemMaterial material,
      int value,
      int armRat,
      Modifiers bonuses,
      List<String> equipEffects,
      List<Requirement> equipReq,
      String imgName,
      String mSpriteName,
      String fSpriteName,
      GameContainer gc)
      throws SlickException, IOException, FontFormatException {
    super(id, value, imgName, gc, bonuses, equipEffects, equipReq, type.ordinal(), material);
    armorRating = armRat;
    this.itemTile = this.buildIcon(gc);
    if (type == ArmorType.CHEST || type == ArmorType.HEAD) {
      setMSprite(mSpriteName);
      setFSprite(fSpriteName);
    }
  }

  /**
   * Armor constructor (with saved serial number)
   *
   * @param id Item ID
   * @param serial Saved serial number
   * @param name Item name
   * @param info Item description
   * @param type Armor type (0-5 value)
   * @param material Material of which item is made (0-4 value)
   * @param value Item value in gold
   * @param armRat Armor rating value
   * @param bonuses Armor bonuses to statistics
   * @param equippEffects List with IDs of all equip effects
   * @param equipReq List with equip requirements
   * @param imgName Name of image file in icon directory for item tile
   * @param gc Slick game container for item tile
   * @throws SlickException
   * @throws IOException
   * @throws FontFormatException
   */
  public Armor(
      String id,
      long serial,
      ArmorType type,
      ItemMaterial material,
      int value,
      int armRat,
      Modifiers bonuses,
      List<String> equipEffects,
      List<Requirement> equipReq,
      String imgName,
      String mSpriteName,
      String fSpriteName,
      GameContainer gc)
      throws SlickException, IOException, FontFormatException {
    super(
        id, serial, value, imgName, gc, bonuses, equipEffects, equipReq, type.ordinal(), material);
    armorRating = armRat;
    this.itemTile = this.buildIcon(gc);
    if (type == ArmorType.CHEST || type == ArmorType.HEAD) {
      setMSprite(mSpriteName);
      setFSprite(fSpriteName);
    }
  }
  /**
   * Returns item armor rating
   *
   * @return Armor rating value
   */
  public int getArmorRat() {
    return armorRating;
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.core.Usable#use(pl.isangeles.senlin.core.Targetable, pl.isangeles.senlin.core.Targetable)
   */
  @Override
  public boolean use(Targetable user, Targetable target) {
    return onUse.start(user, target);
  }

  @Override
  protected String getInfo() {
    // TODO include requirements info
    String fullInfo =
        name
            + System.lineSeparator()
            + getTypeName()
            + System.lineSeparator()
            + getMaterialName()
            + System.lineSeparator()
            + TConnector.getText("ui", "armRat")
            + ": "
            + armorRating
            + System.lineSeparator()
            + bonuses.getInfo()
            + System.lineSeparator()
            + equipReq.toString()
            + System.lineSeparator()
            + info
            + System.lineSeparator()
            + TConnector.getText("ui", "itemVInfo")
            + ": "
            + value;

    return fullInfo;
  }

  @Override
  protected String getTypeName() {
    switch (type) {
      case FEET:
        return TConnector.getText("ui", "armFeet");
      case HANDS:
        return TConnector.getText("ui", "armHand");
      case OFFHAND:
        return TConnector.getText("ui", "armOff");
      case CHEST:
        return TConnector.getText("ui", "armChest");
      case HEAD:
        return TConnector.getText("ui", "armHead");
      default:
        return TConnector.getText("ui", "errorName");
    }
  }

  private String getMaterialName() {
    return material.toString();
  }
  /**
   * Sets sprite from file with specified name as item male sprite
   *
   * @param spriteFileName Name of sprite sheet file
   * @throws SlickException
   * @throws IOException
   */
  @Override
  protected void setMSprite(String spriteFileName) throws SlickException, IOException {
    try {
      itemMSprite =
          new AnimObject(
              GConnector.getInput("sprite/avatar/" + spriteFileName),
              "spriteM" + id,
              false,
              80,
              90);
    } catch (SlickException | NullPointerException | IOException e) {
      switch (material) {
        case CLOTH:
          itemMSprite =
              new AnimObject(
                  GConnector.getInput("sprite/avatar/m-cloth-1222211-80x90.png"),
                  "spriteM" + id,
                  false,
                  80,
                  90);
          break;
        case LEATHER:
          itemMSprite =
              new AnimObject(
                  GConnector.getInput("sprite/avatar/m-leather-1222211-80x90.png"),
                  "spriteM" + id,
                  false,
                  80,
                  90);
          break;
        default:
          itemMSprite =
              new AnimObject(
                  GConnector.getInput("sprite/avatar/m-cloth-1222211-80x90.png"),
                  "spriteM" + id,
                  false,
                  80,
                  90);
          break;
      }
    }
  }
  /**
   * Sets sprite from file with specified name as item female sprite
   *
   * @param spriteFileName Name of sprite sheet file
   * @throws SlickException
   * @throws IOException
   */
  @Override
  protected void setFSprite(String spriteFileName) throws SlickException, IOException {
    try {
      itemFSprite =
          new AnimObject(
              GConnector.getInput("sprite/avatar/" + spriteFileName),
              "spriteF" + id,
              false,
              80,
              90);
    } catch (SlickException | NullPointerException | IOException e) {
      switch (material) {
        case CLOTH:
          itemFSprite =
              new AnimObject(
                  GConnector.getInput("sprite/avatar/f-cloth-1222211-80x90.png"),
                  "spriteF" + id,
                  false,
                  80,
                  90);
          break;
        default:
          itemFSprite =
              new AnimObject(
                  GConnector.getInput("sprite/avatar/f-cloth-1222211-80x90.png"),
                  "spriteF" + id,
                  false,
                  80,
                  90);
          break;
      }
    }
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.core.item.Item#setTile(org.newdawn.slick.GameContainer)
   */
  @Override
  protected ItemTile buildIcon(GameContainer gc)
      throws FontFormatException, SlickException, IOException {
    try {
      if (!icons.containsKey(id)) {
        Image iconImg = new Image(GConnector.getInput("icon/item/armor/" + imgName), id, false);
        icons.put(id, iconImg);
        return new ItemTile(iconImg, gc, this.getInfo());
      } else {
        Image iconImg = icons.get(id);
        return new ItemTile(iconImg, gc, this.getInfo());
      }
    } catch (SlickException | IOException e) {
      return new ItemTile(GBase.getImage("errorIcon"), gc, this.getInfo());
    }
  }
}
