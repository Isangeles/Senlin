/*
 * EffectParser.java
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
package pl.isangeles.senlin.util.parser;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import pl.isangeles.senlin.core.Attributes;
import pl.isangeles.senlin.core.bonus.Bonus;
import pl.isangeles.senlin.data.pattern.EffectPattern;

/**
 * Class for parsing effects base
 * @author Isangeles
 *
 */
public final class EffectParser
{
    private EffectParser() {}
    
    public static EffectPattern getEffectFromNode(Node effectNode)
    {
        Element effectE = (Element)effectNode;
        
        String id = effectE.getAttribute("id");
        int duration = Integer.parseInt(effectE.getAttribute("duration"));
        String type = effectE.getAttribute("type");
        String icon = effectE.getAttribute("icon");
        
        Node modifersNode = effectE.getElementsByTagName("modifers").item(0);
        List<Bonus> bonuses = BonusesParser.getBonusesFromNode(modifersNode);
        
        int dot = Integer.parseInt(effectE.getElementsByTagName("dot").item(0).getTextContent());
        
        return new EffectPattern(id, icon, bonuses, dot, duration, type);
    }
}
