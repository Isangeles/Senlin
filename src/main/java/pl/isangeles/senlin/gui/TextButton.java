/*
 * TextButton.java
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
package pl.isangeles.senlin.gui;

import java.awt.FontFormatException;
import java.io.IOException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import pl.isangeles.senlin.data.GBase;

/**
 * Simple button with transparent texture
 *
 * @author Isangeles
 */
public class TextButton extends Button {
  public TextButton(GameContainer gc) throws SlickException, FontFormatException, IOException {
    super(GBase.getImage("textButtonBg"), "", gc);
  }
}
