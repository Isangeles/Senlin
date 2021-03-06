/*
 * Trinket.java
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
import pl.isangeles.senlin.core.action.Action;
import pl.isangeles.senlin.core.action.ActionType;
import pl.isangeles.senlin.core.bonus.Modifiers;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.data.GBase;
import pl.isangeles.senlin.gui.tools.ItemTile;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for trinkets like rings, amulets, etc.
 *
 * @author Isangeles
 */
public class Trinket extends Equippable {
  public static final int FINGER = 0, // TODO use trinket type enum {@link TrinketType}
      NECK = 1,
      ARTIFACT = 2;
  /**
   * Trinket constructor
   *
   * @param id Item ID
   * @param type Trinket type
   * @param value Item value
   * @param imgName Item icon image file name
   * @param reqLevel Level required to use this item
   * @param bonuses Bonuses for owner
   * @param equippEffects List with IDs of all equip effects
   * @param equipReq List with equip requirements
   * @param onUse Action on use
   * @param gc Slick game container
   * @throws SlickException
   * @throws IOException
   * @throws FontFormatException
   */
  public Trinket(
      String id,
      TrinketType type,
      int value,
      String imgName,
      Modifiers bonuses,
      List<String> equipEffects,
      List<Requirement> equipReq,
      Action onUse,
      GameContainer gc)
      throws SlickException, IOException, FontFormatException {
    super(
        id, value, imgName, gc, bonuses, equipEffects, equipReq, type.ordinal(), ItemMaterial.IRON);
    this.itemTile = this.buildIcon(gc);
    if (onUse.getType() != ActionType.NONE) this.onUse = onUse;
  }
  /**
   * Trinket constructor (with specified serial number)
   *
   * @param id Item ID
   * @param serial Item serial number
   * @param type Trinket type
   * @param value Item value
   * @param imgName Item icon image file name
   * @param reqLevel Level required to use this item
   * @param bonuses Bonuses for owner
   * @param equippEffects List with IDs of all equip effects
   * @param equipReq List with equip requirements
   * @param onUse Action on use
   * @param gc Slick game container
   * @throws SlickException
   * @throws IOException
   * @throws FontFormatException
   */
  public Trinket(
      String id,
      long serial,
      TrinketType type,
      int value,
      String imgName,
      Modifiers bonuses,
      List<String> equipEffects,
      List<Requirement> equipReq,
      Action onUse,
      GameContainer gc)
      throws SlickException, IOException, FontFormatException {
    super(
        id,
        serial,
        value,
        imgName,
        gc,
        bonuses,
        equipEffects,
        equipReq,
        type.ordinal(),
        ItemMaterial.IRON);
    this.itemTile = this.buildIcon(gc);
    if (onUse.getType() != ActionType.NONE) this.onUse = onUse;
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.core.item.Item#getInfo()
   */
  @Override
  protected String getInfo() {
    String fullInfo =
        name
            + System.lineSeparator()
            + getTypeName()
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
      case FINGER:
        return TConnector.getText("ui", "triFinger");
      case NECK:
        return TConnector.getText("ui", "triNeck");
      case ARTIFACT:
        return TConnector.getText("ui", "triArtifact");
      default:
        return TConnector.getText("ui", "errorName");
    }
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.core.Usable#use(pl.isangeles.senlin.core.Targetable, pl.isangeles.senlin.core.Targetable)
   */
  @Override
  public boolean use(Targetable user, Targetable target) {
    return onUse.start(user, target);
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.core.item.Item#setTile(org.newdawn.slick.GameContainer)
   */
  @Override
  protected ItemTile buildIcon(GameContainer gc)
      throws SlickException, IOException, FontFormatException {
    try {
      if (!icons.containsKey(id)) {
        Image iconImg = new Image(GConnector.getInput("icon/item/trinket/" + imgName), id, false);
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
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.core.item.Equippable#setMSprite(java.lang.String)
   */
  @Override
  protected void setMSprite(String ssName) throws IOException, SlickException {
    // Trinkets do not use any sprite sheets
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.core.item.Equippable#setFSprite(java.lang.String)
   */
  @Override
  protected void setFSprite(String ssName) throws IOException, SlickException {
    // Trinkets do not use any sprite sheets
  }
}
