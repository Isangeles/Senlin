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

import pl.isangeles.senlin.core.craft.Profession;
import pl.isangeles.senlin.core.craft.ProfessionLevel;
import pl.isangeles.senlin.core.craft.ProfessionType;
import pl.isangeles.senlin.core.craft.Recipe;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.data.RecipesBase;
import pl.isangeles.senlin.data.pattern.NpcPattern;
import pl.isangeles.senlin.data.pattern.RandomItem;

/**
 * Static class for NPC parsing
 * @author Isangeles
 *
 */
public final class NpcParser
{
	/**
	 * Private constructor to prevent initialization
	 */
    private NpcParser(){};
    /**
     * Parses specified npc node to NPC pattern 
     * @param npcNode npc node from document(NPCs base or save file)
     * @return NPC pattern from specified node
     */
    public static NpcPattern getNpcFromNode(Node npcNode) throws NumberFormatException, NullPointerException
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
        Element spritesheetE = (Element)npc.getElementsByTagName("spritesheet").item(0);
        String spritesheet = spritesheetE.getTextContent();
        boolean ssType = Boolean.parseBoolean(spritesheetE.getAttribute("static"));
        
        NodeList npcNodes = npcNode.getChildNodes();
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
        List<String> skills = getSkills(skillsNode);
        
        Node effectsNode = npc.getElementsByTagName("effects").item(0);
        Map<String, Integer> effects = getEffects(effectsNode);
        
        Node craftingNode = npc.getElementsByTagName("crafting").item(0);
        List<Profession> professions = getProfessions(craftingNode);
        
        npcP = new NpcPattern(id, attitude, trade, train, guildID, level, stats, head, chest, hands, mainHand, offHand, feet,
                              neck, fingerA, fingerB, artifact, spritesheet, ssType, portraitName, gold, itemsIn, skills, effects,
                              professions);
        return npcP;
    }
    /**
     * Parses specified skills node to list with skills IDs
     * @param skillsNode NPC base node (skills node)
     * @return List with skills IDs
     * @throws NullPointerException
     */
    private static List<String> getSkills(Node skillsNode)
    {
        List<String> skills = new ArrayList<>();
        
        if(skillsNode == null)
        	return skills;
        
    	Element skillsE = (Element)skillsNode;
        NodeList skillList = skillsE.getElementsByTagName("skill");
        
        for(int j = 0; j < skillList.getLength(); j++)
        {
            Node skillNode = skillList.item(j);
            if(skillNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            {
                Element skillE = (Element)skillNode;
                skills.add(skillE.getTextContent());
            }
        }
        
        return skills;
    }
    /**
     * Parses specified effects node to map with effects IDs as keys and current duration time as values 
     * @param effectsNode NPC base node (effects node)
     * @return Map with effects IDs as key and current duration time as values
     * @throws NullPointerException
     * @throws NumberFormatException
     */
    private static Map<String, Integer> getEffects(Node effectsNode)
    {
        Map<String, Integer> effects = new HashMap<>();
        
        if(effectsNode == null)
        	return effects;
        
    	Element effectsE = (Element)effectsNode;
        NodeList effectsList = effectsE.getElementsByTagName("effect");
        
        for(int j = 0; j < effectsList.getLength(); j ++)
        {
            Node effectNode = effectsList.item(j);
            if(effectNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            {
                Element effectE = (Element)effectNode;
                try
                {
                	effects.put(effectE.getTextContent(), Integer.parseInt(effectE.getAttribute("duration")));
                }
                catch(NumberFormatException e)
                {
                	continue;
                }
            }
        }
        
        return effects;
    }
    /**
     * Parses NPC base crafting node to list with profession objects
     * @param craftingNode Node from NPC base document (crafting node)
     * @return List with profession objects
     */
    private static List<Profession> getProfessions(Node craftingNode)
    {
    	List<Profession> professions = new ArrayList<>();
    	
    	if(craftingNode == null)
        	return professions;
    	
    	Element craftingE = (Element)craftingNode;
    	NodeList professionsList = craftingE.getElementsByTagName("profession");
    	for(int i = 0; i < professionsList.getLength(); i ++)
    	{
    		Node professionNode = professionsList.item(i);
    		if(professionNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
    		{
    			Element professionE = (Element)professionNode;
    			ProfessionType type = ProfessionType.fromString(professionE.getAttribute("type"));
    			ProfessionLevel level = ProfessionLevel.fromString(professionE.getAttribute("level"));
    			List<Recipe> recipes = getRecipes(professionE);
    			professions.add(new Profession(type, level, recipes));
    		}
    	}
    	
    	return professions;
    }
    
    private static List<Recipe> getRecipes(Element professionE)
    {
    	List<Recipe> recipes = new ArrayList<>();
    	
    	NodeList recipesList = professionE.getElementsByTagName("recipe");
    	for(int i = 0; i < recipesList.getLength(); i ++)
    	{
    		Node recipe = recipesList.item(i);
    		if(recipe.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
    		{
    			Element recipeE = (Element)recipe;
    			recipes.add(RecipesBase.get(recipeE.getTextContent()));
    		}
    	}
    	return recipes;
    }
    
}
