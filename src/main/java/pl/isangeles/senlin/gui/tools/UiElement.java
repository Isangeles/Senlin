/*
 * UiElement.java
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
package pl.isangeles.senlin.gui.tools;
/**
 * Interface for ui elements
 * @author Isangeles
 *
 */
interface UiElement
{
    /**
     * Draws UI element, specified positions should be scaled to current screen resolution
     * @param x Position on X-axis
     * @param y Position on Y-axis
     */
    public void draw(float x, float y);
    /**
     * Closes UI element
     */
    public void close();
    /**
     * Updates UI element
     */
    public void update();
    /**
     * Resets UI element
     */
    public void reset();
    /**
     * Checks if element should be drawn
     * @return True if element should be drawn, false otherwise
     */
    public boolean isOpenReq();
}
