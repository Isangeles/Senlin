/*
 * CharacterOut.java
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
package pl.isangeles.senlin.core.out;

import pl.isangeles.senlin.util.TConnector;

/**
 * Enumeration for character actions results, e.g. attack
 *
 * @author Isangeles
 */
public enum CharacterOut {
  SUCCESS,
  NORANGE,
  NOTARGET,
  NOTREADY,
  UNABLE,
  LOCKED,
  UNKNOWN;

  @Override
  public String toString() {
    switch (this) {
      case SUCCESS:
        return null;
      case NORANGE:
        return TConnector.getText("ui", "logNoRange");
      case NOTARGET:
        return TConnector.getText("ui", "logNoTar");
      case NOTREADY:
        return TConnector.getText("ui", "logNotRdy");
      case UNABLE:
        return TConnector.getText("ui", "logUnable");
      case LOCKED:
        return TConnector.getText("ui", "logLocked");
      default:
        return TConnector.getText("ui", "errorName");
    }
  }

  public boolean isSuccess() {
    if (this == SUCCESS) return true;
    else return false;
  }
}
