/*
 * ObjectAvatar.java
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

import java.util.ArrayList;
import java.util.List;

import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.Position;

/**
 * Class for graphical representations of game objects
 * @author Isangeles
 *
 */
public class ObjectAvatar implements Effective
{
	private GameObject texture;
	
	private List<SimpleAnim> effects = new ArrayList<>();
	private List<SimpleAnim> loopEffects = new ArrayList<>();
	private List<SimpleAnim> effectsToRemove = new ArrayList<>();
	/**
	 * Object avatar constructor(with simple sprite as texture)
	 * @param texture Sprite
	 */
	public ObjectAvatar(Sprite texture)
	{
		this.texture = texture;
	}
	/**
	 * Object avatar constructor(with simple animation as texture)
	 * @param anim Simple animation
	 */
	public ObjectAvatar(SimpleAnim anim)
	{
		this.texture = anim;
	}
	/**
	 * Draws avatar 
	 * @param x Position on x-axis
	 * @param y Position on y-axis
	 * @param scaledPos True if position should be scaled to current resolution, false otherwise
	 */
	public void draw(float x, float y, boolean scaledPos)
	{
		texture.draw(x, y, scaledPos);
		
		for(SimpleAnim effect : effects)
		{
			effect.draw(x - texture.getScaledWidth()/2, y - texture.getScaledHeight(), Coords.getScale(), scaledPos);
		}
		for(SimpleAnim effect : loopEffects)
		{
			effect.draw(x - texture.getScaledWidth()/2, y - texture.getScaledHeight(), Coords.getScale(), scaledPos);
		}
	}
	/**
	 * Updates avatar
	 * @param delta Time from last update
	 */
	public void update(int delta)
	{
		for(SimpleAnim effect : effects)
		{
			if(effect.isLastFrame())
				effectsToRemove.add(effect);
		}
		effects.removeAll(effectsToRemove);
		effectsToRemove.clear();
	}
	/**
	 * Draws avatar on current avatar texture position
	 * @param size Desired size of avatar texture
	 */
	public void draw(float size)
	{
		texture.draw(size);
		
		for(SimpleAnim effect : effects)
		{
			effect.draw(texture.x - texture.getScaledWidth()/2, texture.y - texture.getScaledHeight(), size, true);
		}
		for(SimpleAnim effect : loopEffects)
		{
			effect.draw(texture.x - texture.getScaledWidth()/2, texture.y - texture.getScaledHeight(), size, true);
		}
	}
	/**
	 * Sets specified position as avatar texture position
	 * @param pos Position to set
	 */
	public void setPosition(Position pos)
	{
		texture.setPosition(pos);
	}
	/**
	 * Checks if mouse is over avatar texture
	 * @return True if mouse if over avatar texture, false otherwise
	 */
	public boolean isMouseOver()
	{
		return texture.isMouseOver();
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.graphic.Effective#addEffect(pl.isangeles.senlin.graphic.SimpleAnim, boolean)
	 */
	@Override
	public boolean addEffect(SimpleAnim effect, boolean loop) 
	{
		if(effect != null)
		{
			if(loop)
				return loopEffects.add(effect);
			else
				return effects.add(effect);
		}
		else
			return false;
	}
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.graphic.Effective#removeEffect(pl.isangeles.senlin.graphic.SimpleAnim)
	 */
	@Override
	public boolean removeEffect(SimpleAnim effect) 
	{
		return loopEffects.remove(effect);
	}
}
