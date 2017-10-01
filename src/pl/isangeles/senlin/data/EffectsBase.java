/*
 * EffectsBase.java
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
package pl.isangeles.senlin.data;

import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.xml.sax.SAXException;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.effect.EffectSource;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.data.pattern.EffectPattern;
import pl.isangeles.senlin.util.DConnector;
/**
 * Static class for all game effects
 * Loaded on newGameMenu initialization
 * @author Isangeles
 *
 */
public class EffectsBase 
{
	private static GameContainer gc;
	private static Map<String, EffectPattern> effectsMap = new HashMap<>();
	private static boolean loaded = false;
	/**
	 * Private constructor to prevent initialization
	 */
	private EffectsBase() 
	{
	}
	/**
	 * Returns new instance of effect with specified ID
	 * @param source Effect source, e.g. skill owner(can be null)
	 * @param id Effect ID
	 * @return New instance of effect with specified ID
	 */
	public static Effect getEffect(EffectSource source, String id)
	{
	    if(effectsMap.get(id) != null)
	    {
	        try 
	        {
				return effectsMap.get(id).make(source, gc);
			} 
	        catch (SlickException | IOException | FontFormatException e) 
	        {
	        	Log.addSystem("effects_base_get-fail msg///effect building from pattern fail: " + id);
	            return null;
			}
	    }
	    else
	    {
	        Log.addSystem("effects_base_get-fail msg///no such effect: " + id);
            return null;
	    }
	}
	/**
	 * Returns pattern for effect with specified ID
	 * @param id Effect ID
	 * @return Effect pattern
	 */
	public static EffectPattern getPattern(String id)
	{
	    return effectsMap.get(id);
	}
	/**
	 * Loads base
	 * @param skillsPath Path to skills directory
	 * @param gc Slick game container
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static void load(String skillsPath, GameContainer gc) throws SAXException, IOException, ParserConfigurationException
	{
		if(!loaded)
		{
		    EffectsBase.gc = gc;
	        effectsMap = DConnector.getEffectsMap(skillsPath + File.separator + "effects");
            loaded = true;
		}
	}
}
