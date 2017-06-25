/*
 * NpcParser.java
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pl.isangeles.senlin.core.Effect;
import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.data.pattern.NpcPattern;
import pl.isangeles.senlin.data.pattern.RandomItem;

/**
 * Static class for NPC parsing
 * @author Isangeles
 *
 */
public class NpcParser
{
    private NpcParser(){};
    
    public static NpcPattern getNpcFromNode(Node npcNode)
    {
        try
        {
            Element npc = (Element)npcNode;
            String id = npc.getAttribute("id");
            String attitude = npc.getAttribute("attitude");
            boolean trade = Boolean.parseBoolean(npc.getAttribute("trade"));
            boolean train = Boolean.parseBoolean(npc.getAttribute("train"));
            int guildID = Integer.parseInt(npc.getAttribute("guild"));
            int level = Integer.parseInt(npc.getAttribute("level"));
            NpcPattern npcP;
            
            String stats = npc.getElementsByTagName("stats").item(0).getTextContent();
            String portraitName = npc.getElementsByTagName("portrait").item(0).getTextContent();
            String spritesheet = npc.getElementsByTagName("spritesheet").item(0).getTextContent();
            
            NodeList npcNodes = npcNode.getChildNodes();
            Node eqNode = npcNodes.item(1);
            Element eq = (Element)npcNodes;
            
            String head = eq.getElementsByTagName("head").item(0).getTextContent();
            String chest = eq.getElementsByTagName("chest").item(0).getTextContent();
            String hands = eq.getElementsByTagName("hands").item(0).getTextContent();
            String mainHand = eq.getElementsByTagName("mainhand").item(0).getTextContent();
            String offHand = eq.getElementsByTagName("offhand").item(0).getTextContent();
            String feet = eq.getElementsByTagName("feet").item(0).getTextContent();
            String neck = eq.getElementsByTagName("neck").item(0).getTextContent();
            String fingerA = eq.getElementsByTagName("finger1").item(0).getTextContent();
            String fingerB = eq.getElementsByTagName("finger2").item(0).getTextContent();
            String artifact = eq.getElementsByTagName("artifact").item(0).getTextContent();
            
            Element in = (Element)eq.getElementsByTagName("in").item(0);
            int gold = Integer.parseInt(in.getAttribute("gold"));
            
            List<RandomItem> itemsIn = new LinkedList<>();
            
            for(int j = 0; j < in.getElementsByTagName("item").getLength(); j ++)
            {
                Element itemNode = (Element)in.getElementsByTagName("item").item(j);
                boolean ifRandom = Boolean.parseBoolean(itemNode.getAttribute("random"));
                String itemInId = itemNode.getTextContent();
                RandomItem ip = new RandomItem(itemInId, ifRandom);
                itemsIn.add(ip);
            }
            
            Node skillsNode = npc.getElementsByTagName("skills").item(0);
            NodeList skillList = skillsNode.getChildNodes();
            List<String> skills = new ArrayList<>();
            for(int j = 0; j < skillList.getLength(); j++)
            {
                Node skillNode = skillList.item(j);
                if(skillNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
                {
                    Element skillE = (Element)skillNode;
                    skills.add(skillE.getTextContent());
                }
            }
            
            Node effectsNode = npc.getElementsByTagName("effects").item(0);
            NodeList effectsList = effectsNode.getChildNodes();
            Map<String, Integer> effects = new HashMap<>();
            for(int j = 0; j < effectsList.getLength(); j ++)
            {
                Node effectNode = effectsList.item(j);
                if(effectNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
                {
                    Element effectE = (Element)effectNode;
                    effects.put(effectE.getTextContent(), Integer.parseInt(effectE.getAttribute("duration")));
                }
            }
            
            npcP = new NpcPattern(id, attitude, trade, train, guildID, level, stats, head, chest, hands, mainHand, offHand, feet,
                                  neck, fingerA, fingerB, artifact, spritesheet, portraitName, gold, itemsIn, skills, effects);
            return npcP;
        }
        catch(NumberFormatException e)
        {
            Log.addSystem("npc_base_parser_fail msg///npc node corrputed");
            return null;
        }
    }
}
