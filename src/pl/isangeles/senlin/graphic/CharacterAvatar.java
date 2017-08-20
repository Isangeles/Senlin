/*
 * CharacterAvatar.java
 * 
 * Copyright 2017 Dariusz Sikora <darek@darek-PC-LinuxMint18>
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

/**
 * Interface for characters graphical representations
 * @author Isangeles
 *
 */
public interface CharacterAvatar
{
    /**
     * Draws avatar at specified position
     * @param x Position on X axis
     * @param y Position on Y axis
     */
    public void draw(float x, float y);
    /**
     * Updates avatar
     * @param delta
     */
    public void update(int delta);
    
    public void goUp();
    
    public void goRight();
    
    public void goDown();
    
    public void goLeft();
    
    public void kneel();
    
    public void lie();
    /**
     * Turns move animation on or off
     * @param move True to turn animation on, false to turn off
     */
    public void move(boolean move);
    /**
     * Starts melee attack animation 
     */
    public void meleeAnim();
    /**
     * Starts range attack animation
     */
    public void rangeAnim();
    /**
     * Starts casting animation
     */
    public void castAnim();
    /**
     * Displays specified text above avatar head
     * @param text Text to display
     */
    public void speak(String text);
    /**
     * Informs avatar that his character is targeted or not
     * @param isTargeted True if is targeted, false otherwise
     */
    public void targeted(boolean targeted);
    /**
     * Returns object direction
     * @return Direction id (0 - up, 1 - right, 2 - down, 3 - left)
     */
    public int getDirection();
    /**
     * Checks if avatar is static
     * @return True if avatar is static, false otherwise
     */
    public boolean isStatic();
    /**
     * Checks if mouse is over avatar
     * @return True if mouse is over avatar, false otherwise
     */
    public boolean isMouseOver();
    /**
     * Returns avatar default torso
     * @return Animated object
     */
    public AnimObject getDefTorso();
}
