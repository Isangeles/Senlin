/*
 * ActionParser.java
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

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import pl.isangeles.senlin.data.pattern.ActionPattern;

/**
 * Class for parsing action nodes
 *
 * @author Isangeles
 */
public final class ActionParser {
  /** Private constructor to prevent initialization */
  private ActionParser() {}
  /**
   * Parses specified action node to action pattern
   *
   * @param actionNode Node from XML document, action node
   * @return Action pattern from specified node
   */
  public static ActionPattern getActionFromNode(Node actionNode) {
    Element actionE = (Element) actionNode;

    String typeId = actionE.getAttribute("type");
    String actionId = actionE.getTextContent();

    return new ActionPattern(typeId, actionId);
  }
}
