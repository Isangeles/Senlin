/*
 * Action.java
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
package pl.isangeles.senlin.core.action;

import pl.isangeles.senlin.core.Targetable;

/**
 * Class for in-game actions(eg. items on-click actions)
 *
 * @author Isangeles
 */
public abstract class Action {
  protected ActionType type;
  protected Targetable lastUser;
  /** Default constructor, action do nothing */
  public Action() {
    type = ActionType.NONE;
  }
  /**
   * Action constructor
   *
   * @param type Type of action
   */
  public Action(ActionType type) {
    this.type = type;
  }
  /**
   * Starts action
   *
   * @param user Action user
   * @param target User target
   * @return
   */
  public abstract boolean start(Targetable user, Targetable target);
  /**
   * Returns action type
   *
   * @return ActionType enumeration
   */
  public ActionType getType() {
    return type;
  }
}
