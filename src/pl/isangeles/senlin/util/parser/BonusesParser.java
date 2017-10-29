/*
 * BonusesParser.java
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
import pl.isangeles.senlin.core.bonus.Bonus;
import pl.isangeles.senlin.core.bonus.BonusType;
import pl.isangeles.senlin.core.bonus.DamageBonus;
import pl.isangeles.senlin.core.bonus.DualwieldBonus;
import pl.isangeles.senlin.core.bonus.HealthBonus;
import pl.isangeles.senlin.core.bonus.ResistanceBonus;
import pl.isangeles.senlin.core.bonus.StatsBonus;
import pl.isangeles.senlin.core.bonus.UndetectBonus;
import pl.isangeles.senlin.core.effect.EffectType;
import pl.isangeles.senlin.core.item.WeaponType;

/**
 * Static class for bonuses nodes parsing
 * @author Isangeles
 *
 */
public final class BonusesParser 
{
	/**
	 * Private constructor to prevent initialization
	 */
	private BonusesParser() {}
	/**
	 * Parses specified bonuses node to list with bonuses
	 * @param bonusesNode Bonuses node with bonus nodes
	 * @return List with bonus objects
	 */
	public static List<Bonus> getBonusesFromNode(Node bonusesNode)
	{
		List<Bonus> bonuses = new ArrayList<>();
		if(bonusesNode == null)
			return bonuses;
		
		NodeList bonusNodes = bonusesNode.getChildNodes();
		for(int i = 0; i < bonusNodes.getLength(); i ++)
		{
			Node bonusNode = bonusNodes.item(i);
			if(bonusNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
			{
				Element bonusE = (Element)bonusNode;
				
				BonusType type = BonusType.fromString(bonusE.getNodeName());
				try
				{
					switch(type)
					{
					case STATS:
						Attributes stats = new Attributes(bonusE.getTextContent());
						bonuses.add(new StatsBonus(stats));
						break;
					case HEALTH:
						int hp = Integer.parseInt(bonusE.getTextContent());
						bonuses.add(new HealthBonus(hp));
						break;
					case DAMAGE:
						int dmg = Integer.parseInt(bonusE.getTextContent());
						String wTypeName = bonusE.getAttribute("type");
						if(wTypeName != "")
							bonuses.add(new DamageBonus(dmg, WeaponType.fromName(wTypeName)));
						else
							bonuses.add(new DamageBonus(dmg));
						break;
					case UNDETECT:
						int stealthLevel = Integer.parseInt(bonusE.getTextContent());
						bonuses.add(new UndetectBonus(stealthLevel));
						break;
					case DUALWIELD:
						float dwBonus = Float.parseFloat(bonusE.getTextContent());
						bonuses.add(new DualwieldBonus(dwBonus));
						break;
					case RESISTANCE:
						int resiValue = Integer.parseInt(bonusE.getTextContent());
						EffectType resiType = EffectType.fromId(bonusE.getAttribute("type"));
						bonuses.add(new ResistanceBonus(resiType, resiValue));
						break;
					default:
						break;
					}
				}
				catch(NumberFormatException e)
				{
					Log.addSystem("bonus_builder_fail_msg///base node corrupted!");
					continue;
				}
			}
		}
		return bonuses;
	}
}
