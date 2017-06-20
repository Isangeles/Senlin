/*
 * Attributes.java
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
package pl.isangeles.senlin.core;
/**
 * Class for character attributes
 * @author Isangeles
 *
 */
public class Attributes 
{
	private Attribute strength;
	private Attribute constitution;
	private Attribute dexterity;
	private Attribute intelligence;
	private Attribute wisdom;
	/**
	 * Attributes constructor
	 * @param str Strength value
	 * @param con Constitution value
	 * @param dex Dexterity value
	 * @param inte Intelligence value
	 * @param wis Wisdom value
	 */
	public Attributes(int str, int con, int dex, int inte, int wis)
	{
		strength = new Attribute(str);
		constitution = new Attribute(con);
		dexterity = new Attribute(dex);
		intelligence = new Attribute(inte);
		wisdom = new Attribute(wis);
	}
	public int addHealth()
	{
		return constitution.value * 10 + strength.value;
	}
	public int addMagicka()
	{
		return intelligence.value * 10 + wisdom.value;
	}
	public float addHaste()
	{
		return 1.0f + ((constitution.value/4) + dexterity.value/2)/2;
	}
	/**
	 * Returns basic hit value
	 * @return Integer with basic hit value
	 */
	public int getBasicHit()
	{
		return strength.value *2 + constitution.value;
	}
	
	public int getBasicSpell()
	{
		return intelligence.value *2 + wisdom.value;
	}
	/**
	 * Returns dodge chance
	 * @return Float with dodge chance
	 */
	public float getDodge()
	{
		return (dexterity.value+(constitution.value/2f))/100;
	}
	/**
	 * Returns new attributes object with all attributes negative
	 * @return Attributes with inverted values
	 */
	public Attributes nagative()
	{
		return new Attributes(-strength.value, -constitution.value, -dexterity.value, -intelligence.value, -wisdom.value);
	}
	
	public int getStr()
	{ return strength.value; }
	
	public int getCon()
	{ return constitution.value; }
	
	public int getDex()
	{ return dexterity.value; }
	
	public int getInt()
	{ return intelligence.value; }
	
	public int getWis()
	{ return wisdom.value; }
	
	public void addStr()
	{ strength.value ++; }
	
	public void addStr(int value)
	{ strength.value += value; }
	
	public void addCon()
	{ constitution.value ++; }
	
	public void addCon(int value)
	{ constitution.value += value; }
	
	public void addDex()
	{ dexterity.value ++; }
	
	public void addDex(int value) 
	{ dexterity.value += value; }
	
	public void addInt()
	{ intelligence.value ++; }
	
	public void addInt(int value) 
	{ intelligence.value += value; }
	
	public void addWis()
	{ wisdom.value ++; }
	
	public void addWis(int value) 
	{ wisdom.value += value; }
	/**
	 * Adds specified attributes to this attributes
	 * @param attributes Attributes object
	 */
	public void addAll(Attributes attributes)
	{
		strength.value += attributes.getStr();
		constitution.value += attributes.getCon();
		dexterity.value += attributes.getDex();
		intelligence.value += attributes.getInt();
		wisdom.value += attributes.getWis();
	}
	/**
	 * Compares this attributes to other some attributes 
	 * @param otherAtt Some other attributes
	 * @return False if specified attributes have any attribute greater, true if all attributes are lower or equal
	 */
	public boolean compareTo(Attributes otherAtt)
	{
	    if(strength.getValue() < otherAtt.getStr() || constitution.getValue() < otherAtt.getCon() || dexterity.getValue() < otherAtt.getDex() || 
	       intelligence.getValue() < otherAtt.getInt() || wisdom.getValue() < otherAtt.getWis())
	        return false;
	    
	    if(strength.getValue() >= otherAtt.getStr() && constitution.getValue() >= otherAtt.getCon() && dexterity.getValue() >= otherAtt.getDex() && 
           intelligence.getValue() >= otherAtt.getInt() && wisdom.getValue() >= otherAtt.getWis())
            return true;
	    
	    return false;
	}
	
	public String toLine()
	{
		return strength.value + ";" + constitution.value + ";" + dexterity.value + ";" + intelligence.value + ";" +wisdom.value + ";";
	}
	
	@Override
	public String toString()
	{
	    return " s:" + strength.value + " c:" + constitution.value + " d:" + dexterity.value + " i:" + intelligence.value + " w:" + wisdom.value;
	}
}
