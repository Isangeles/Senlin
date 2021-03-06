/*
 * ObjectsBase.java
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

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.xml.sax.SAXException;
import pl.isangeles.senlin.core.TargetableObject;
import pl.isangeles.senlin.data.pattern.ObjectPattern;
import pl.isangeles.senlin.util.DConnector;

/**
 * Static class for game objects base
 *
 * @author Isangeles
 */
public class ObjectsBase {
  private static Map<String, ObjectPattern> objectsMap = new HashMap<>();
  private static GameContainer gc;
  /** Private constructor to prevent initialization */
  private ObjectsBase() {}
  /**
   * Returns game object with specified ID
   *
   * @param objectId String with desired object ID
   * @return New game object from base or null if desired object was not found
   * @throws SlickException
   * @throws IOException
   * @throws FontFormatException
   */
  public static TargetableObject get(String objectId)
      throws SlickException, IOException, FontFormatException {
    return objectsMap.get(objectId).make(gc);
  }
  /**
   * Loads specified objects base
   *
   * @param baseName Name of XML base in data/objects
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws IOException
   * @throws SlickException
   * @throws FontFormatException
   */
  public static void load(String objectsPath, GameContainer gc)
      throws ParserConfigurationException, SAXException, IOException, SlickException,
          FontFormatException {
    ObjectsBase.gc = gc;
    objectsMap.putAll(DConnector.getObjects(objectsPath + File.separator + "objects"));
  }
}
