/*
 * FogOfWar.java
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
package pl.isangeles.senlin.graphic;

import java.awt.image.BufferedImage;
import java.io.IOException;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import pl.isangeles.senlin.util.GConnector;

/**
 * Class for 'fog of war'
 *
 * @author Isangeles
 */
public class FogOfWar {
  public Image fowTex;
  public Image visArea;
  public Image fowTile;
  public BufferedImage fowTileBuf;
  public Shape fogShape;

  public FogOfWar() throws SlickException, IOException {
    fowTex = new Image(GConnector.getInput("day/fow.png"), "fowLight", false);
    visArea = new Image(GConnector.getInput("day/visArea.png"), "visArea", false);
    fowTile = new Image(GConnector.getInput("day/fowTile.png"), "fowTile", false);
    fowTileBuf = new BufferedImage(32, 32, BufferedImage.TYPE_BYTE_BINARY);
  }

  public void draw(float x, float y, float width, float height) {
    fowTex.draw(x, y, width, height);
  }

  public void drawVis(float x, float y, float scale) {
    visArea.draw(x, y, scale);
  }
  /**
   * Draws FOW tile (32x32)
   *
   * @param x
   * @param y
   * @param scale
   */
  public void drawTile(float x, float y, float scale) {
    fowTile.draw(x, y);
  }
}
