/*
 * DialogueTransfer.java
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
package pl.isangeles.senlin.core.dialogue;

import java.util.ArrayList;
import java.util.List;

import pl.isangeles.senlin.core.character.Character;

/**
 * Class for dialogue transfer
 * @author Isangeles
 *
 */
public class DialogueTransfer 
{
	private final List<String> itemsToGive;
	private final List<String> itemsToTake;
	private final int goldToGive;
	private final int goldToTake;
	/**
	 * Default constructor, creates empty transfer
	 */
	public DialogueTransfer()
	{
		itemsToGive = new ArrayList<>();
		itemsToTake = new ArrayList<>();
		goldToGive = 0;
		goldToTake = 0;
	}
	/**
	 * Dialogue transfer constructor
	 * @param itemsToGive List with IDs of items to give to player 
	 * @param itemsToTake List with IDs of items to take from player 
	 * @param goldToGive Amount of gold to give to player 
	 * @param goldToTake Amount of gold to take from player
	 */
	public DialogueTransfer(List<String> itemsToGive, List<String> itemsToTake, int goldToGive, int goldToTake)
	{
		this.itemsToGive = itemsToGive;
		this.itemsToTake = itemsToTake;
		this.goldToGive = goldToGive;
		this.goldToTake = goldToTake;
	}
	/**
	 * Transfers items and gold between to characters
	 * @param charA First game character
	 * @param charB Second game character
	 */
	public void exchange(Character charA, Character charB)
	{
		for(String itemId : itemsToGive)
		{
			charB.addItem(charA.getInventory().takeItem(itemId));
		}
		for(String itemId : itemsToTake)
		{
			charA.addItem(charB.getInventory().takeItem(itemId));
		}
		charB.addGold(charA.getInventory().takeGold(goldToGive));
		charA.addGold(charB.getInventory().takeGold(goldToTake));
	}
}
