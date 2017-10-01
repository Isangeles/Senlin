/*
 * Global.java
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
package pl.isangeles.senlin.states;

import pl.isangeles.senlin.core.Chapter;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.gui.GameCursor;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.util.Settings;
/**
 * Class for game globals, like player character, targeted characters, etc. 
 * @author Isangeles
 *
 */
public class Global 
{
	private static Character player;
	private static float[] cameraPos = {0, 0};
	private static Chapter activeChapter;
	
	private Global() 
	{}
	/**
	 * Sets character as player's character
	 * @param character Game character
	 */
	public static void setPlayer(Character character)
	{
		player = character;
	}
	
	public static void setChapter(Chapter chapter)
	{
		activeChapter = chapter;
	}
	/**
	 * Returns current player's character
	 * @return Player's character
	 */
	public static Character getPlayer()
	{
		return player;
	}
	
	public static Chapter getChapter()
	{
		return activeChapter;
	}
	
	public static void setCamerPos(float x, float y)
	{
		Global.cameraPos[0] = x;
		Global.cameraPos[1] = y;
	}
	
	public static float[] getCameraPos()
	{
		return cameraPos;
	}
	
	public static Position getCameraStartPos()
	{
		return new Position(cameraPos[0], cameraPos[1]);
	}
	
	public static Position getCameraEndPos()
	{
		return new Position(cameraPos[0] + Settings.getResolution()[0], cameraPos[1] + Settings.getResolution()[1]);
	}
	/**
	 * Converts raw x position to in game x position
	 * @param x Position on x-axis
	 * @return World position on x-axis
	 */
	public static float worldX(float x)
	{
		return (x + cameraPos[0]) - GameCursor.WIDTH;
	}
	/**
	 * Converts raw y position to in game y position
	 * @param y Position on y-axis
	 * @return World position on y-axis
	 */
	public static float worldY(float y)
	{
		return (y + cameraPos[1]) - GameCursor.HEIGHT;
	}
	/**
	 * Converts world x position to ui x position
	 * @param x Position on x-axis
	 * @return Ui position on x-axis
	 */
	public static float uiX(float x)
	{
		return x - cameraPos[0];
	}
	/**
	 * Converts world y position to ui y position
	 * @param y Position on y-axis
	 * @return Ui position on y-axis
	 */
	public static float uiY(float y)
	{
		return y - cameraPos[1];
	}
	
	
}
