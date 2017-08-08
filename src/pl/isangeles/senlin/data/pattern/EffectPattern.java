/*
 * EffectPattern.java
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

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Attributes;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.effect.EffectType;
import pl.isangeles.senlin.util.TConnector;
/**
 * Pattern for creating effects
 * @author Isangeles
 *
 */
public class EffectPattern
{
    private String id;
    private String imgName;
    private EffectType type;
    private int hpMod;
    private int manaMod;
    private Attributes attMod;
    private float hasteMod;
    private float dodgeMod;
    private int dmgMod;
    private int dot;
    private int duration;
    /**
     * Effect pattern constructor
     * @param id Effect ID
     * @param imgName Name of image file for effect UI icon
     * @param hpMod Affect on target health points
     * @param manaMod Affect on target magicka points
     * @param attMod Affect on target attributes points
     * @param hasteMod Affect on target haste value
     * @param dodgeMod Affect on target dodge chance
     * @param dmgMod Affect on damage caused by target
     * @param dot Damage over time effect value (positive value heals target) 
     * @param duration Effect duration
     * @param type Effect type
     */
    public EffectPattern(String id, String imgName, int hpMod, int manaMod, Attributes attMod, float hasteMod, float dodgeMod, int dmgMod, int dot, int duration, String type)
    {
        this.id = id;
        this.imgName = imgName;
        this.type = EffectType.fromString(type);
        this.hpMod = hpMod;
        this.manaMod = manaMod;
        this.attMod = attMod;
        this.hasteMod = hasteMod;
        this.dodgeMod = dodgeMod;
        this.dmgMod = dmgMod;
        this.dot = dot;
        this.duration = duration*1000;
    }
    /**
     * Builds new effect object from this pattern
     * @param gc Slick game container
     * @return New effect object
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    public Effect make(GameContainer gc) throws SlickException, IOException, FontFormatException
    {
        return new Effect(id, imgName, hpMod, manaMod, attMod, hasteMod, dodgeMod, dmgMod, dot, duration, type, gc);
    }
}
