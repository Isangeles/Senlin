/*
 * ModifiersParser.java
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

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Attributes;
import pl.isangeles.senlin.core.bonus.Modifier;
import pl.isangeles.senlin.core.bonus.ModifierType;
import pl.isangeles.senlin.core.bonus.AttitudeModifier;
import pl.isangeles.senlin.core.bonus.DamageBonus;
import pl.isangeles.senlin.core.bonus.DualwieldBonus;
import pl.isangeles.senlin.core.bonus.HealthBonus;
import pl.isangeles.senlin.core.bonus.ResistanceBonus;
import pl.isangeles.senlin.core.bonus.StatsBonus;
import pl.isangeles.senlin.core.bonus.UndetectBonus;
import pl.isangeles.senlin.core.bonus.UnlockBonus;
import pl.isangeles.senlin.core.character.Attitude;
import pl.isangeles.senlin.core.effect.EffectType;
import pl.isangeles.senlin.core.item.WeaponType;

/**
 * Static class for modifiers nodes parsing
 * @author Isangeles
 *
 */
public final class ModifiersParser 
{
	/**
	 * Private constructor to prevent initialization
	 */
	private ModifiersParser() {}
	/**
	 * Parses specified modifiers node to list with modifiers
	 * @param modifiersNode Modifiers node with modifier nodes
	 * @return List with modifiers objects
	 */
	public static List<Modifier> getModifiersFromNode(Node modifiersNode)
	{
		List<Modifier> modifiers = new ArrayList<>();
		if(modifiersNode == null)
			return modifiers;
		
		NodeList modifiersNodes = modifiersNode.getChildNodes();
		for(int i = 0; i < modifiersNodes.getLength(); i ++)
		{
			Node modifierNode = modifiersNodes.item(i);
			if(modifierNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				Element modifierE = (Element)modifierNode;
				
				ModifierType type = ModifierType.fromString(modifierE.getNodeName());
				try
				{
					switch(type)
					{
					case STATS:
						Attributes stats = new Attributes(modifierE.getTextContent());
						modifiers.add(new StatsBonus(stats));
						break;
					case HEALTH:
						int hp = Integer.parseInt(modifierE.getTextContent());
						modifiers.add(new HealthBonus(hp));
						break;
					case DAMAGE:
						int dmg = Integer.parseInt(modifierE.getTextContent());
						String wTypeName = modifierE.getAttribute("type");
						if(wTypeName != "")
							modifiers.add(new DamageBonus(dmg, WeaponType.fromName(wTypeName)));
						else
							modifiers.add(new DamageBonus(dmg));
						break;
					case UNDETECT:
						int stealthLevel = Integer.parseInt(modifierE.getTextContent());
						modifiers.add(new UndetectBonus(stealthLevel));
						break;
					case DUALWIELD:
						float dwBonus = Float.parseFloat(modifierE.getTextContent());
						modifiers.add(new DualwieldBonus(dwBonus));
						break;
					case RESISTANCE:
						int resiValue = Integer.parseInt(modifierE.getTextContent());
						EffectType resiType = EffectType.fromId(modifierE.getAttribute("type"));
						modifiers.add(new ResistanceBonus(resiType, resiValue));
						break;
					case UNLOCK:
						int lockLevel = Integer.parseInt(modifierE.getTextContent());
						modifiers.add(new UnlockBonus(lockLevel));
						break;
					case ATTITUDE:
						Attitude attitude = Attitude.fromString(modifierE.getTextContent());
						modifiers.add(new AttitudeModifier(attitude));
						break;
					default:
						break;
					}
				}
				catch(NumberFormatException e)
				{
					Log.addSystem("modifier_builder_fail_msg///base node corrupted!");
					continue;
				}
			}
		}
		return modifiers;
	}
}
