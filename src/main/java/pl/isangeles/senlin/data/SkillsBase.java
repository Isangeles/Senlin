/*
 * SkillsBase.java
 *
 * Copyright 2017-2018 Dariusz Sikora <dev@isangeles.pl>
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.xml.sax.SAXException;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.effect.EffectType;
import pl.isangeles.senlin.core.item.WeaponType;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.core.req.WeaponRequirement;
import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.core.skill.AttackType;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.data.pattern.AttackPattern;
import pl.isangeles.senlin.data.pattern.BuffPattern;
import pl.isangeles.senlin.data.pattern.PassivePattern;
import pl.isangeles.senlin.data.pattern.SkillPattern;
import pl.isangeles.senlin.util.DConnector;

/**
 * Static class for skills base loaded at newGameMenu initialization
 *
 * @author Isangeles
 */
public class SkillsBase {
  private static GameContainer gc;
  private static Map<String, AttackPattern> attacksMap = new HashMap<>();
  private static Map<String, BuffPattern> buffsMap = new HashMap<>();
  private static Map<String, PassivePattern> passivesMap = new HashMap<>();
  private static boolean loaded = false;
  /** Private constructor to prevent initialization */
  private SkillsBase() {}
  /**
   * Returns auto attack skill from base
   *
   * @param character Game character for skill
   * @return Auto attack skill
   * @throws SlickException
   * @throws IOException
   * @throws FontFormatException
   */
  public static Attack getAutoAttack(Character character)
      throws SlickException, IOException, FontFormatException {
    // return attacksMap.get("autoA").make(character, gc);

    List<Requirement> reqs = new ArrayList<Requirement>();
    return new Attack(
        character,
        "autoA",
        "autoAttack.png",
        EffectType.NORMAL,
        AttackType.MELEE,
        0,
        reqs,
        true,
        0,
        2000,
        40,
        new ArrayList<String>(),
        gc);
  }
  /**
   * Returns shot skill from base
   *
   * @param character Game character for skill
   * @return Shot skill
   * @throws SlickException
   * @throws IOException
   * @throws FontFormatException
   */
  public static Attack getShot(Character character)
      throws SlickException, IOException, FontFormatException {
    // return attacksMap.get("shot").make(character, gc);

    List<Requirement> reqs = new ArrayList<Requirement>();
    reqs.add(new WeaponRequirement(WeaponType.BOW));
    return new Attack(
        character,
        "shot",
        "shot.png",
        EffectType.NORMAL,
        AttackType.RANGE,
        0,
        reqs,
        true,
        0,
        3000,
        150,
        new ArrayList<String>(),
        gc);
  }

  public static Skill getSkill(Character character, String id)
      throws SlickException, IOException, FontFormatException {
    if (attacksMap.containsKey(id)) return attacksMap.get(id).make(character, gc);
    else if (buffsMap.containsKey(id)) return buffsMap.get(id).make(character, gc);
    else if (passivesMap.containsKey(id)) return passivesMap.get(id).make(character, gc);

    return null;
  }
  /**
   * Returns pattern for skill with specified ID
   *
   * @param id String with desired skill ID
   * @return SkillPattern with desired skill or null if skill was not found
   */
  public static SkillPattern getPattern(String id) {
    if (attacksMap.containsKey(id)) return attacksMap.get(id);
    else if (buffsMap.containsKey(id)) return buffsMap.get(id);
    else if (passivesMap.containsKey(id)) return passivesMap.get(id);

    return null;
  }
  /**
   * Loads skills base
   *
   * @param gc Slick game container
   * @throws SlickException
   * @throws IOException
   * @throws FontFormatException
   * @throws ParserConfigurationException
   * @throws SAXException
   */
  public static void load(String skillsPath, GameContainer gc)
      throws SlickException, IOException, FontFormatException, SAXException,
          ParserConfigurationException {
    if (!loaded) {
      SkillsBase.gc = gc;
      attacksMap = DConnector.getAttacksMap(skillsPath + File.separator + "attacks");
      buffsMap = DConnector.getBuffsMap(skillsPath + File.separator + "buffs");
      passivesMap = DConnector.getPassivesMap(skillsPath + File.separator + "passives");
      loaded = true;
    }
  }
}
