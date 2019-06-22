/*
 * SkillParser.java
 * 
 * Copyright 2017 Dariusz Sikora <dev@isangeles.pl>
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
import pl.isangeles.senlin.core.bonus.Modifier;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.data.EffectsBase;
import pl.isangeles.senlin.data.pattern.AttackPattern;
import pl.isangeles.senlin.data.pattern.BuffPattern;
import pl.isangeles.senlin.data.pattern.PassivePattern;

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
    /**
     * Parsing specified document node to attack pattern
     * @param skillNode Skill node from attacks base
     * @return Attack pattern from specified node
     * @throws NumberFormatException
     * @throws NoSuchElementException
     */
    public static AttackPattern getAttackFromNode(Node skillNode) throws NumberFormatException, NoSuchElementException 
    {
        Element skillE = (Element)skillNode;
        String id = skillE.getAttribute("id");
        String type = skillE.getAttribute("effect");
        boolean useWeapon = Boolean.parseBoolean(skillE.getAttribute("useWeapon"));
        String attackType = skillE.getAttribute("type");
        
        String imgName = skillE.getElementsByTagName("icon").item(0).getTextContent();
        int range = Integer.parseInt(skillE.getElementsByTagName("range").item(0).getTextContent());
        int cast = Integer.parseInt(skillE.getElementsByTagName("cast").item(0).getTextContent());
        int damage = Integer.parseInt(skillE.getElementsByTagName("damage").item(0).getTextContent());
        int cooldown = Integer.parseInt(skillE.getElementsByTagName("cooldown").item(0).getTextContent());
        
        Node useReqNode = skillE.getElementsByTagName("useReq").item(0);
        List<Requirement> useReqs = RequirementsParser.getReqFromNode(useReqNode);
        
        Node trainReqNode = skillE.getElementsByTagName("trainReq").item(0);
        List<Requirement> trainReq = RequirementsParser.getReqFromNode(trainReqNode);

        List<String> effects = new ArrayList<>();
        NodeList enl = skillE.getElementsByTagName("effects").item(0).getChildNodes();
        for(int j = 0; j < enl.getLength(); j ++)
        {
            Node effectNode = enl.item(j);
            //Iterating effects
            if(effectNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            {
                Element effect = (Element)effectNode;
                String effectId = effect.getTextContent();
                effects.add(effectId);
            }
        }
        
        return new AttackPattern(id, imgName, type, useWeapon, attackType, damage, useReqs, cast, cooldown, range, effects, trainReq);
    }
    /**
     * Parses specified document node to buff pattern
     * @param skillNode Node from buffs base
     * @return Buff pattern from specified skill node
     * @throws NumberFormatException
     * @throws NoSuchElementException
     */
    public static BuffPattern getBuffFromNode(Node skillNode) throws NumberFormatException, NoSuchElementException 
    {
    	Element skillE = (Element)skillNode;
        String id = skillE.getAttribute("id");
        String type = skillE.getAttribute("type");
        String effect = skillE.getAttribute("effect");
        
        String imgName = skillE.getElementsByTagName("icon").item(0).getTextContent();
        int range = Integer.parseInt(skillE.getElementsByTagName("range").item(0).getTextContent());
        int cast = Integer.parseInt(skillE.getElementsByTagName("cast").item(0).getTextContent());
        int cooldown = Integer.parseInt(skillE.getElementsByTagName("cooldown").item(0).getTextContent());
        
        Node useReqNode = skillE.getElementsByTagName("useReq").item(0);
        List<Requirement> useReqs = RequirementsParser.getReqFromNode(useReqNode);
        
        Node trainReqNode = skillE.getElementsByTagName("trainReq").item(0);
        List<Requirement> trainReq = RequirementsParser.getReqFromNode(trainReqNode);

        List<String> effects = new ArrayList<>();
        NodeList enl = skillE.getElementsByTagName("effects").item(0).getChildNodes();
        for(int j = 0; j < enl.getLength(); j ++)
        {
            Node effectNode = enl.item(j);
            //Iterating effects
            if(effectNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            {
                Element effectE = (Element)effectNode;
                String effectId = effectE.getTextContent();
                effects.add(effectId);
            }
        }
        
        return new BuffPattern(id, imgName, effect, type, useReqs, cooldown, cast, range, effects, trainReq);
    }
    /**
     * Parses specified skill node to passive skill pattern
     * @param skillNode Skill node from XML skills base 
     * @return Passive skill pattern from specified node
     */
    public static PassivePattern getPassiveFromNode(Node skillNode)
    {
    	Element skillE = (Element)skillNode;
        String id = skillE.getAttribute("id");
        String type = skillE.getAttribute("type");
        String effect = skillE.getAttribute("effect");
        
        String imgName = skillE.getElementsByTagName("icon").item(0).getTextContent();
        
        Node useReqNode = skillE.getElementsByTagName("useReq").item(0);
        List<Requirement> useReqs = RequirementsParser.getReqFromNode(useReqNode);
        
        Node trainReqNode = skillE.getElementsByTagName("trainReq").item(0);
        List<Requirement> trainReq = RequirementsParser.getReqFromNode(trainReqNode);

        List<String> effects = new ArrayList<>();
        NodeList enl = skillE.getElementsByTagName("effects").item(0).getChildNodes();
        for(int j = 0; j < enl.getLength(); j ++)
        {
            Node effectNode = enl.item(j);
            //Iterating effects
            if(effectNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            {
                Element effectE = (Element)effectNode;
                String effectId = effectE.getTextContent();
                effects.add(effectId);
            }
        }
        
        return new PassivePattern(id, imgName, effect, type, useReqs, effects, trainReq);
    }
}
