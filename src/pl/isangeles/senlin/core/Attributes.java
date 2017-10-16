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

import pl.isangeles.senlin.util.TConnector;

/**
 * Class for character attributes
 * @author Isangeles
 *
 */
public class Attributes 
{
	private Attribute strenght;
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
		strenght = new Attribute(str);
		constitution = new Attribute(con);
		dexterity = new Attribute(dex);
		intelligence = new Attribute(inte);
		wisdom = new Attribute(wis);
	}
	/**
	 * Attributes constructor
	 * @param attLine Strength value
	 */
	public Attributes(String attLine)
	{
		String[] atts = attLine.split(";");
		try
		{
			strenght = new Attribute(Integer.parseInt(atts[0]));
			constitution = new Attribute(Integer.parseInt(atts[1]));
			dexterity = new Attribute(Integer.parseInt(atts[2]));
			intelligence = new Attribute(Integer.parseInt(atts[3]));
			wisdom = new Attribute(Integer.parseInt(atts[4]));
		}
		catch(NumberFormatException | ArrayIndexOutOfBoundsException e)
		{
			strenght = new Attribute(0);
			constitution = new Attribute(0);
			dexterity = new Attribute(0);
			intelligence = new Attribute(0);
			wisdom = new Attribute(0);
		}
	}
	public int addHealth()
	{
		return constitution.value * 10 + strenght.value;
	}
	public int addMagicka()
	{
		return intelligence.value * 10 + wisdom.value;
	}
	public float addHaste()
	{
		return 1.0f + (((constitution.value/4) + dexterity.value/2)/2);
	}
	/**
	 * Returns basic hit value
	 * @return Integer with basic hit value
	 */
	public int getBasicHit()
	{
		return strenght.value *2 + constitution.value;
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
	 * Returns haste value(reduces skills cooldown)
	 * @return Haste value
	 */
	public float getHaste()
	{
		return 1.0f + (((constitution.value/4) + dexterity.value/2)/2);
	}
	/**
	 * Returns concentration value 
	 * @return Concentration value
	 */
	public float getConcentration()
	{
		return 1.0f + ((intelligence.value/4) + (dexterity.value/2)/2);
	}
	/**
	 * Returns spell power value
	 * @return Spell power value
	 */
	public int getSpellPower()
	{
		int spellPower = 0;
		for(int i = 0; i < intelligence.value; i += 5)
		{
			spellPower += 1;
		}
		return spellPower;
	}
	
	public float getDualwieldPenalty()
	{
		return 2.0f - (dexterity.value/2) + (strenght.value/4) + (constitution.value/4);
	}
	/*
	public int getCastBonus()
	{
		int castBonus = 0;
		for(float i = getHaste(); i > 0; i --)
		{
			castBonus += 2;
		}
		return castBonus;
	}*/
	/**
	 * Returns new attributes object with all attributes negative
	 * @return Attributes with inverted values
	 */
	public Attributes nagative()
	{
		return new Attributes(-strenght.value, -constitution.value, -dexterity.value, -intelligence.value, -wisdom.value);
	}
	
	public int getStr()
	{ return strenght.value; }
	
	public int getCon()
	{ return constitution.value; }
	
	public int getDex()
	{ return dexterity.value; }
	
	public int getInt()
	{ return intelligence.value; }
	
	public int getWis()
	{ return wisdom.value; }
	
	public void addStr()
	{ strenght.value ++; }
	
	public void addStr(int value)
	{ strenght.value += value; }
	
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
	public void mod(Attributes attributes)
	{
		strenght.value += attributes.getStr();
		constitution.value += attributes.getCon();
		dexterity.value += attributes.getDex();
		intelligence.value += attributes.getInt();
		wisdom.value += attributes.getWis();
	}
	/**
	 * Modifies specified attribute
	 * @param type Type of attribute to modify
	 * @param value Mod value
	 */
	public void modAtt(AttributeType type, int value)
	{
		switch(type)
		{
		case STRENGHT:
			strenght.value += value;
			break;
		case CONSTITUTION:
			constitution.value += value;
			break;
		case DEXTERITY:
			dexterity.value += value;
			break;
		case INTELLIGENCE:
			intelligence.value += value;
			break;
		case WISDOM:
			wisdom.value += value;
			break;
		}
	}
	/**
	 * Compares this attributes to other some attributes 
	 * @param otherAtt Some other attributes
	 * @return False if specified attributes have any attribute greater, true if all attributes are lower or equal
	 */
	public boolean compareTo(Attributes otherAtt)
	{
	    if(strenght.getValue() < otherAtt.getStr() || constitution.getValue() < otherAtt.getCon() || dexterity.getValue() < otherAtt.getDex() || 
	       intelligence.getValue() < otherAtt.getInt() || wisdom.getValue() < otherAtt.getWis())
	        return false;
	    
	    if(strenght.getValue() >= otherAtt.getStr() && constitution.getValue() >= otherAtt.getCon() && dexterity.getValue() >= otherAtt.getDex() && 
           intelligence.getValue() >= otherAtt.getInt() && wisdom.getValue() >= otherAtt.getWis())
            return true;
	    
	    return false;
	}
	
	public String getInfo()
	{
		String info = "";
		
		if(strenght.value != 0)
			info += TConnector.getText("ui", "strName") + ":" + strenght.value;
		if(constitution.value != 0)
			info += TConnector.getText("ui", "conName") + ":" + constitution.value;
		if(dexterity.value != 0)
			info += TConnector.getText("ui", "dexName") + ":" + dexterity.value;
		if(intelligence.value != 0)
			info += TConnector.getText("ui", "intName") + ":" + intelligence.value;
		if(wisdom.value != 0)
			info += TConnector.getText("ui", "wisName") + ":" + wisdom.value;
		
		return info;
	}
	
	@Override
	public String toString()
	{
		return strenght.value + ";" + constitution.value + ";" + dexterity.value + ";" + intelligence.value + ";" +wisdom.value + ";";
	}
}
