/*
 * SkillParser.java
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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.data.EffectsBase;
import pl.isangeles.senlin.data.pattern.AttackPattern;

/**
 * Class for parsing skills bases
 * @author Isangeles
 *
 */
public final class SkillParser
{
    /**
     * Private constructor to prevent initialization
     */
    private SkillParser() {}
    
    public static AttackPattern getAttackFromNode(Node skillNode) throws NumberFormatException, NoSuchElementException 
    {
        Element skill = (Element)skillNode;
        String id = skill.getAttribute("id");
        String type = skill.getAttribute("type");
        
        String imgName = skill.getElementsByTagName("icon").item(0).getTextContent();
        int range = Integer.parseInt(skill.getElementsByTagName("range").item(0).getTextContent());
        int cast = Integer.parseInt(skill.getElementsByTagName("cast").item(0).getTextContent());
        int damage = Integer.parseInt(skill.getElementsByTagName("damage").item(0).getTextContent());
        int cooldown = Integer.parseInt(skill.getElementsByTagName("cooldown").item(0).getTextContent());
        
        Node useReqNode = skill.getElementsByTagName("useReq").item(0);
        List<Requirement> useReqs = RequirementsParser.getReqFromNode(useReqNode);
        
        Node trainReqNode = skill.getElementsByTagName("trainReq").item(0);
        List<Requirement> trainReq = RequirementsParser.getReqFromNode(trainReqNode);

        List<Effect> effects = new ArrayList<>();
        NodeList enl = skill.getElementsByTagName("effects").item(0).getChildNodes();
        for(int j = 0; j < enl.getLength(); j ++)
        {
            Node effectNode = enl.item(j);
            //Iterating effects
            if(effectNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            {
                Element effect = (Element)effectNode;
                String effectId = effect.getTextContent();
                effects.add(EffectsBase.getEffect(effectId));
            }
        }
        
        return new AttackPattern(id, imgName, type, damage, useReqs, cast, cooldown, range, effects, trainReq);
    }
}
