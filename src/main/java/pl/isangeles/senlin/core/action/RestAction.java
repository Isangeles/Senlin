/*
 * RestAction.java
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

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Targetable;

/**
 * Class for rest action
 *
 * @author Isangeles
 */
public class RestAction extends Action {
  /** Read action constructor */
  public RestAction() {
    super(ActionType.REST);
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.core.action.Action#start(pl.isangeles.senlin.core.Targetable, pl.isangeles.senlin.core.Targetable)
   */
  @Override
  public boolean start(Targetable user, Targetable target) {
    Log.addSystem("rest action started!");
    user.getSignals().startResting();
    return true;
  }
}
