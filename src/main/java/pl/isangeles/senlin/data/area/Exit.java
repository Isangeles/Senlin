/*
 * Exit.java
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
package pl.isangeles.senlin.data.area;

import java.awt.FontFormatException;
import java.io.IOException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import pl.isangeles.senlin.data.GBase;
import pl.isangeles.senlin.graphic.Sprite;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.util.Size;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.util.TilePosition;

/**
 * Class for area exits
 *
 * @author Isangeles
 */
public class Exit {
  private String scenarioId;
  private String subAreaId;
  private Position pos;
  private Size size;
  private Position toPos;
  private boolean toSub;
  private Sprite texture;
  /**
   * Area exit constructor
   *
   * @param pos Position on area map
   * @param exitToId ID of scenario to enter after using this exit
   * @throws SlickException
   * @throws IOException
   * @throws FontFormatException
   */
  public Exit(TilePosition pos, String exitToId, TilePosition toPos, GameContainer gc)
      throws SlickException, IOException, FontFormatException {
    String[] exitId = exitToId.split(":");
    if (exitId.length > 1) {
      toSub = true;
      scenarioId = exitId[0];
      subAreaId = exitId[1];
    } else {
      toSub = false;
      scenarioId = exitToId;
      subAreaId = "";
    }
    this.pos = pos.asPosition();
    this.texture =
        new Sprite(
            GBase.getImage("areaExit"), TConnector.getTextFromModule("objects", "areaExit"), gc);
    size = new Size(texture.getScaledWidth(), texture.getScaledHeight());
    this.toPos = toPos.asPosition();
  }
  /**
   * Area exit constructor
   *
   * @param pos Position on area map
   * @param exitToId ID of scenario to enter after using this exit
   * @throws SlickException
   * @throws IOException
   * @throws FontFormatException
   */
  public Exit(
      TilePosition pos, String texName, String exitToId, TilePosition toPos, GameContainer gc)
      throws SlickException, IOException, FontFormatException {
    String[] exitId = exitToId.split(":");
    if (exitId.length > 1) {
      toSub = true;
      scenarioId = exitId[0];
      subAreaId = exitId[1];
    } else {
      toSub = false;
      scenarioId = exitToId;
      subAreaId = "";
    }
    this.pos = pos.asPosition();
    this.toPos = toPos.asPosition();
    this.texture =
        new Sprite(
            new Image(GConnector.getInput("object/static/" + texName), texName, false),
            TConnector.getTextFromModule("objects", "areaExit"),
            gc);
  }
  /** Draws exit area texture */
  public void draw() {
    texture.draw(pos.x, pos.y, false);
  }
  /**
   * Returns ID of scenario to enter after using this exit
   *
   * @return String with scenario ID
   */
  public String getScenarioId() {
    return scenarioId;
  }
  /**
   * Returns ID of sub area
   *
   * @return String with sub area ID
   */
  public String getSubAreaId() {
    return subAreaId;
  }
  /**
   * Return exit position on destination area
   *
   * @return xy position
   */
  public Position getToPos() {
    return toPos;
  }
  /**
   * Checks if mouse is over exit area texture
   *
   * @return True if mouse is over exit area texture, false otherwise
   */
  public boolean isMouseOver() {
    return texture.isMouseOver();
  }
  /**
   * Checks if this exit leads to sub area
   *
   * @return True if this exits leads to sub area, false otherwise
   */
  public boolean isToSub() {
    return toSub;
  }
  /**
   * Returns exit position on area map
   *
   * @return Exit position
   */
  public Position getPos() {
    return pos;
  }
}
