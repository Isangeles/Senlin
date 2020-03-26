/*
 * Attack.java
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
package pl.isangeles.senlin.core.skill;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.List;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.effect.EffectType;
import pl.isangeles.senlin.core.out.CharacterOut;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for offensive skills
 *
 * @author Isangeles
 */
public class Attack extends Skill {
  private AttackType attackType;
  private int damage;
  private int range;
  private boolean useWeapon;
  /**
   * Attack constructor
   *
   * @param character Skill owner
   * @param id Skill ID
   * @param name Skill Name
   * @param info Skill description
   * @param imgName Skill icon file
   * @param damage Damage dealt on target
   * @param reqs List with requirements needed to use skill
   * @param useWeapon True if equipped weapon damage should be counted to attack damage, false
   *     otherwise
   * @param castTime Cast time
   * @param cooldown Time that must be waited after skill use
   * @param range Required range
   * @param gc Slick game container
   * @throws SlickException
   * @throws IOException
   * @throws FontFormatException
   */
  public Attack(
      Character character,
      String id,
      String imgName,
      EffectType type,
      AttackType attackType,
      int damage,
      List<Requirement> reqs,
      boolean useWeapon,
      int castTime,
      int cooldown,
      int range,
      List<String> effects,
      GameContainer gc)
      throws SlickException, IOException, FontFormatException {
    super(character, id, imgName, type, reqs, castTime, cooldown, effects);
    this.attackType = attackType;
    this.damage = damage;
    this.range = range;
    this.useWeapon = useWeapon;

    avatarAnim = attackType.getAnimType();

    setTile(gc);
    setGraphicEffects(gc);
    setSoundEffect();
  }
  /**
   * Returns attack damage
   *
   * @return Damage value, -1 if attack is missed
   */
  public int getDamage() {
    int ownerHit = 0;
    if (useWeapon) {
      ownerHit = owner.getHit();
      if (ownerHit < 0) // means miss
      return -1;
    }

    switch (attackType) {
      case MELEE:
      case RANGE:
        return damage + ownerHit;
      case SPELL:
        return ((damage + owner.getHit()) + owner.getAttributes().getBasicSpell())
            * owner.getAttributes().getSpellPower();
      default:
        return damage;
    }
  }

  @Override
  public String getInfo() {
    String fullInfo =
        name
            + System.lineSeparator()
            + TConnector.getText("ui", "eleTInfo")
            + ":"
            + type.getDisplayName()
            + System.lineSeparator()
            + TConnector.getText("ui", "dmgName")
            + ":"
            + damage
            + System.lineSeparator()
            + TConnector.getText("ui", "rangeName")
            + ":"
            + range
            + System.lineSeparator()
            + TConnector.getText("ui", "castName")
            + ":"
            + getCastTime() / 1000
            + System.lineSeparator()
            + TConnector.getText("ui", "cdName")
            + ":"
            + cooldown / 1000
            + System.lineSeparator();
    for (Requirement req : useReqs) {
      if (!req.getInfo().equals("")) fullInfo += req.getInfo() + System.lineSeparator();
    }
    fullInfo += info;

    return fullInfo;
  }

  @Override
  public CharacterOut prepare(Character user, Targetable target) {
    if (ready && useReqs.isMetBy(user)) {
      if (target != null) {
        // Log.addInformation("Range: " + owner.getRangeFrom(target.getPosition()) + " maxRange: " +
        // range); //TEST CODE
        if (owner.getRangeFrom(target.getPosition()) <= range) {
          this.target = target;
          active = true;
          ready = false;
          return CharacterOut.SUCCESS;
        } else {
          user.moveTo(target, range);
          return CharacterOut.NORANGE;
        }
      } else return CharacterOut.NOTARGET;
    } else return CharacterOut.NOTREADY;
  }
  /** Activates attack skill */
  @Override
  public void activate() {
    if (active) {
      useReqs.chargeAll(owner);
      target.getDefense().handleSkill(this);
      active = false;
    }
  }
}
