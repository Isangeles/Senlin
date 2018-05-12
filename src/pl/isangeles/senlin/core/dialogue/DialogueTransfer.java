/*
 * DialogueTransfer.java
 * 
 * Copyright 2017-2018 Dariusz Sikora <darek@pc-solus>
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
package pl.isangeles.senlin.core.dialogue;

import java.util.HashMap;
import java.util.Map;

import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.item.Item;

/**
 * Class for dialogue transfer
 * @author Isangeles
 *
 */
public class DialogueTransfer 
{
	private final Map<String, Integer> itemsToGive;
	private final Map<String, Integer> itemsToTake;
	/**
	 * Default constructor, creates empty transfer
	 */
	public DialogueTransfer()
	{
		itemsToGive = new HashMap<>();
		itemsToTake = new HashMap<>();
	}
	/**
	 * Dialogue transfer constructor
	 * @param itemsToGive List with IDs of items to give to player 
	 * @param itemsToTake List with IDs of items to take from player 
	 * @param goldToGive Amount of gold to give to player 
	 * @param goldToTake Amount of gold to take from player
	 */
	public DialogueTransfer(Map<String, Integer> itemsToGive, Map<String, Integer> itemsToTake)
	{
		this.itemsToGive = itemsToGive;
		this.itemsToTake = itemsToTake;
	}
	/**
	 * Transfers items and gold between to characters
	 * @param charA First game character(NPC)
	 * @param charB Second game character(PC)
	 */
	public void exchange(Character charA, Character charB)
	{
		for(String itemId : itemsToGive.keySet())
		{
			int amount = itemsToGive.get(itemId);
			for(int i = 0; i < amount; i ++)
			{
				Item takenItem = charA.getInventory().takeItem(itemId);
				if(takenItem != null)
					charB.addItem(takenItem);
			}
		}
		for(String itemId : itemsToTake.keySet())
		{
			int amount = itemsToTake.get(itemId);
			for(int i = 0; i < amount; i ++)
			{
				Item takenItem = charB.getInventory().takeItem(itemId);
				if(takenItem != null)
					charA.addItem(takenItem);
			}
		}
	}
}
