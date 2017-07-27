/*
 * RandomItem.java
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
package pl.isangeles.senlin.data.pattern;

import java.util.Random;

import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.data.ItemsBase;
/**
 * Class for random items(for NPCs loot)
 * @author Isangeles
 *
 */
public class RandomItem
{
	private String id;
	private int serial = -1;
	private boolean random;
	private Random gen = new Random();
	/**
	 * Random item constructor
	 * @param id Item id
	 * @param random True if item spawn should be random, false otherwise
	 */
	public RandomItem(String id, boolean random)
	{
		this.id = id;
		this.random = random;
	}
	/**
	 * Random item constructor
	 * @param id Item id
	 * @param random True if item spawn should be random, false otherwise
	 */
	public RandomItem(String id, int serial, boolean random)
	{
		this.id = id;
		this.serial = serial;
		this.random = random;
	}
	/**
	 * Creates new item, if pattern is random can return null
	 * @return Item or null(if pattern is random)
	 */
	public Item make()
	{
		if(random)
		{
			if(gen.nextBoolean())
			{
				if(serial == -1)
					return ItemsBase.getItem(id);
				else
					return ItemsBase.getItem(id, serial);
			}
			else
				return null;
		}
		else
		{
			if(serial == -1)
				return ItemsBase.getItem(id);
			else
				return ItemsBase.getItem(id, serial);
		}
	}
}
