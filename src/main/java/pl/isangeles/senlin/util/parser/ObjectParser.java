/*
 * ObjectParser.java
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
package pl.isangeles.senlin.util.parser;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import pl.isangeles.senlin.core.InventoryLock;
import pl.isangeles.senlin.data.pattern.ActionPattern;
import pl.isangeles.senlin.data.pattern.ObjectPattern;
import pl.isangeles.senlin.data.pattern.RandomItem;

/**
 * Static class for objects XML base parsing
 *
 * @author Isangeles
 */
public class ObjectParser {
  /** Private constructor to prevent initialization */
  private ObjectParser() {}
  /**
   * Parses object node from XML base to game objects pattern
   *
   * @param objectNode Node from XML base
   * @return New object pattern
   * @throws NumberFormatException
   */
  public static ObjectPattern getObjectFormNode(Node objectNode) throws NumberFormatException {
    Element objectE = (Element) objectNode;
    String id = objectE.getAttribute("id");

    Element textureE = (Element) objectE.getElementsByTagName("texture").item(0);
    String mainTex = textureE.getTextContent();
    String type = textureE.getAttribute("type");
    int frames = 0;
    int fWidth = 0;
    int fHeight = 0;
    if (type.equals("anim")) {
      frames = Integer.parseInt(textureE.getAttribute("frames"));
      fWidth = Integer.parseInt(textureE.getAttribute("fWidth"));
      fHeight = Integer.parseInt(textureE.getAttribute("fHeight"));
    }

    String portrait = "default.png";
    Element portraitE = (Element) objectE.getElementsByTagName("portrait").item(0);
    if (portraitE != null) portrait = portraitE.getTextContent();

    String sound = "";
    Element soundE = (Element) objectE.getElementsByTagName("sound").item(0);
    if (soundE != null) sound = soundE.getTextContent();

    ActionPattern ap = new ActionPattern("none", "");
    Node actionNode = objectE.getElementsByTagName("action").item(0);
    if (actionNode != null) ap = ActionParser.getActionFromNode(actionNode);

    List<RandomItem> items = new ArrayList<>();
    Node inNode = objectE.getElementsByTagName("in").item(0);
    Element inE = (Element) inNode;
    int gold = 0;
    InventoryLock lock = new InventoryLock();
    if (inE != null) {
      Node itemsNode = (Element) inE.getElementsByTagName("items").item(0);
      items = InventoryParser.getItemsFromNode(itemsNode);
      // gold = InventoryParser.getGoldFromNode(inNode);
      Node lockNode = (Element) inE.getElementsByTagName("lock").item(0);
      if (lockNode != null) lock = InventoryParser.getLockFromNode(lockNode);
    }

    if (type.equals("anim"))
      return new ObjectPattern(
          id, mainTex, portrait, sound, frames, fWidth, fHeight, ap, gold, items, lock);
    else return new ObjectPattern(id, mainTex, portrait, sound, ap, gold, items, lock);
  }
}
