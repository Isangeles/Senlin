/*
 * TilePosition.java
 * 
 * Copyright 2017-2018 Dariusz Sikora <dev@isangeles.pl>
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
package pl.isangeles.senlin.util;

import java.util.NoSuchElementException;

/**
 * Tuple for tile position
 * @author Isangeles
 *
 */
public class TilePosition 
{
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;
	
	public int row;
	public int column;
	/**
	 * Position default constructor
	 */
	public TilePosition()
	{
		this.row = 0;
		this.column = 0;
	}
	
	public TilePosition(int row, int column) 
	{
		this.row = row;
		this.column = column;
	}
	
	public TilePosition(float row, float column)
	{
		this.row = (int)row;
		this.column = (int)column;
	}
	
	public TilePosition(int[] rowColumn)
	{
		this.row = rowColumn[0];
		this.column = rowColumn[1];
	}
	
	/**
	 * Copy constructor
	 * @param pos Tile position to copy
	 */
	public TilePosition(TilePosition pos)
	{
		row = pos.row;
		column = pos.column;
	}
	/**
	 * Constructs position from string in this form: [row ID];[column ID]
	 * @param xSemicolonY String in this form: [row ID];[column ID]
	 * @throws NumberFormatException
	 */
	public TilePosition(String rowSemicolonColumn) throws NumberFormatException, NoSuchElementException
	{
		this.row = Integer.parseInt(rowSemicolonColumn.split(";")[0]);
		this.column = Integer.parseInt(rowSemicolonColumn.split(";")[1]);
	}
	/**
	 * Creates tile position from raw position
	 * @param pos XY position
	 */
	public TilePosition(Position pos)
	{
		this.row = pos.x / TILE_WIDTH;
		this.column = pos.y / TILE_HEIGHT;
	}
	/**
	 * Returns position as normal table with two values: row[0] and column[1]
	 * @return Table with row[0] and column[1] numbers
	 */
	public int[] toArray()
	{
	    return new int[]{row, column};
	}
	/**
	 * Converts tile position to world XY position
	 * @return XY position
	 */
	public Position asPosition()
	{
		return new Position(row*TILE_WIDTH, column*TILE_HEIGHT);
	}
	
	@Override
	public String toString()
	{
		return row + ";" + column;
	}
	
	public boolean equals(Position pos)
	{
		return (row == pos.x / TILE_WIDTH && column == pos.y / TILE_HEIGHT);
	}
	
	public boolean equals(TilePosition pos)
	{
		return (row == pos.row && column == pos.column);
	}
}
