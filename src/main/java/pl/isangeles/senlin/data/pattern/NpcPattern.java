/*
 * NpcPattern.java
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
package pl.isangeles.senlin.data.pattern;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Attributes;
import pl.isangeles.senlin.core.character.Attitude;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.character.Gender;
import pl.isangeles.senlin.core.character.Race;
import pl.isangeles.senlin.core.craft.Profession;
import pl.isangeles.senlin.core.dialogue.Dialogue;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.item.Equippable;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.train.Training;
import pl.isangeles.senlin.data.EffectsBase;
import pl.isangeles.senlin.data.SkillsBase;
import pl.isangeles.senlin.gui.Portrait;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for NPC patterns used to create specific NPC by NpcBase class
 *
 * @author Isangeles
 */
public class NpcPattern {
  private final String npcId;
  private final String npcName;
  private final Gender npcGender;
  private final Race npcRace;
  private final Attitude npcAttitude;
  private final boolean trade;
  private final boolean train;
  private final String guildID;
  private final int level;
  private final String stats;
  private final String headItem;
  private final String chestItem;
  private final String handsItem;
  private final String mainHandItem;
  private final String offHandItem;
  private final String feetItem;
  private final String neckItem;
  private final String fingerAItem;
  private final String fingerBItem;
  private final String artifact;
  private final String spritesheet;
  private final boolean staticAvatar;
  private final String portraitName;
  // private final int gold;
  private final List<RandomItem> invItems;
  private final List<String> skills;
  private final Map<String, Integer> effects;
  private final List<Profession> professions;
  private final List<Training> trainings;
  private final List<Dialogue> dialogues;
  /**
   * NPC pattern constructor
   *
   * @param npcId Character ID
   * @param gender Character gender name
   * @param race Character race name
   * @param attitude NPC attitude name
   * @param trade True if character is trader(can trade with player)
   * @param train True if character is trainer(can train player)
   * @param guildID Character guild ID
   * @param level Character level
   * @param stats String with character attributes separated by semicolons
   * @param headItem Character head item
   * @param chestItem Character chest item
   * @param handsItem Character hands item
   * @param mainHandItem Character main hand item
   * @param offHandItem Character off hand item
   * @param feetItem Character feet item
   * @param neckItem Character neck item
   * @param fingerAItem Character finger item
   * @param fingerBItem Character finger item
   * @param artifact Character artifact
   * @param spritesheet Character sprite sheet file name
   * @param staticAvatar True if sprite sheet is for static avatar
   * @param portraitName Character portrait file name
   * @param gold Character cash
   * @param invItems List with IDs of all character items
   * @param skills List with IDs of all character skills
   * @param effects List with IDs of all characters effects
   * @param professions List with character professions
   * @param trainings List with all character trainings
   */
  public NpcPattern(
      String npcId,
      String gender,
      String race,
      String attitude,
      boolean trade,
      boolean train,
      String guildID,
      int level,
      String stats,
      String headItem,
      String chestItem,
      String handsItem,
      String mainHandItem,
      String offHandItem,
      String feetItem,
      String neckItem,
      String fingerAItem,
      String fingerBItem,
      String artifact,
      String spritesheet,
      boolean staticAvatar,
      String portraitName,
      List<RandomItem> invItems,
      List<String> skills,
      Map<String, Integer> effects,
      List<Profession> professions,
      List<Training> trainings,
      List<Dialogue> dialogues) {
    this.npcId = npcId;
    this.npcName = TConnector.getTextFromChapter("npc", npcId);
    npcAttitude = Attitude.fromString(attitude);
    npcGender = Gender.fromId(gender);
    npcRace = Race.fromId(race);
    this.trade = trade;
    this.train = train;
    this.guildID = guildID;
    this.level = level;
    this.stats = stats;
    this.headItem = headItem;
    this.chestItem = chestItem;
    this.handsItem = handsItem;
    this.mainHandItem = mainHandItem;
    this.offHandItem = offHandItem;
    this.feetItem = feetItem;
    this.neckItem = neckItem;
    this.fingerAItem = fingerAItem;
    this.fingerBItem = fingerBItem;
    this.artifact = artifact;
    this.spritesheet = spritesheet;
    this.staticAvatar = staticAvatar;
    this.portraitName = portraitName;
    this.invItems = invItems;
    this.skills = skills;
    this.effects = effects;
    this.professions = professions;
    this.trainings = trainings;
    this.dialogues = dialogues;
  }
  /**
   * Returns ID of NPC from this pattern
   *
   * @return String with NPC pattern
   */
  public String getId() {
    return npcId;
  }
  /**
   * Creates new character from all pattern fields
   *
   * @param gc Slick game container
   * @return New character object based on this pattern
   * @throws IOException
   * @throws FontFormatException
   * @throws SlickException
   */
  public Character make(GameContainer gc) throws IOException, FontFormatException, SlickException {
    Portrait portrait = new Portrait(GConnector.getPortrait(portraitName), gc);
    portrait.setName(portraitName);

    Attributes attributes = new Attributes(stats);

    Character npc = null;
    if (spritesheet.equals("default") || spritesheet.equals(""))
      npc =
          new Character(
              npcId,
              npcGender,
              npcRace,
              npcAttitude,
              guildID,
              npcName,
              level,
              attributes,
              portrait,
              gc);
    else
      npc =
          new Character(
              npcId,
              npcGender,
              npcRace,
              npcAttitude,
              guildID,
              npcName,
              level,
              attributes,
              portrait,
              spritesheet,
              staticAvatar,
              gc);

    if (trade) npc.setTrade();
    if (train) npc.setTrain(trainings);

    for (RandomItem ip : invItems) {
      Item it = ip.make();
      if (it != null) npc.addItem(it);
    }

    try {
      Equippable helmet = (Equippable) npc.getItem(headItem);
      Equippable chest = (Equippable) npc.getItem(chestItem);
      Equippable gloves = (Equippable) npc.getItem(handsItem);
      Equippable mainWeap = (Equippable) npc.getItem(mainHandItem);
      Equippable offHand = (Equippable) npc.getItem(offHandItem);
      Equippable boots = (Equippable) npc.getItem(feetItem);
      Equippable amulet = (Equippable) npc.getItem(neckItem);
      Equippable ringA = (Equippable) npc.getItem(fingerAItem);
      Equippable ringB = (Equippable) npc.getItem(fingerBItem);
      Equippable artifact = (Equippable) npc.getItem(this.artifact);

      npc.equipItem(helmet);
      npc.equipItem(chest);
      npc.equipItem(gloves);
      npc.equipItem(mainWeap);
      npc.equipItem(offHand);
      npc.equipItem(boots);
      npc.equipItem(amulet);
      npc.equipItem(ringA);
      npc.equipItem(ringB);
      npc.equipItem(artifact);
    } catch (Exception e) {
      Log.addSystem("npc_pattern:" + npcId + "-fail_to_set_equipment");
    }

    for (String skillId : skills) {
      npc.addSkill(SkillsBase.getSkill(npc, skillId));
    }
    for (String effectId : effects.keySet()) {
      Effect effect = EffectsBase.getEffect(null, effectId);
      effect.setTime(effects.get(effectId));
      npc.getEffects().add(effect);
    }
    for (Profession profession : professions) {
      npc.addProfession(profession);
    }
    for (Dialogue dialogue : dialogues) {
      npc.addDialogue(dialogue);
    }

    return npc;
  }

  /**
   * Creates new character from all pattern fields (with specified serial number)
   *
   * @param gc Slick game container
   * @param serial Serial number
   * @return New character object based on this pattern
   * @throws IOException
   * @throws FontFormatException
   * @throws SlickException
   */
  public Character make(GameContainer gc, int serial)
      throws IOException, FontFormatException, SlickException {
    Portrait portrait = new Portrait(GConnector.getPortrait(portraitName), gc);
    portrait.setName(portraitName);

    Attributes attributes = new Attributes(stats);

    Character npc = null;
    if (spritesheet.equals("default") || spritesheet.equals(""))
      npc =
          new Character(
              npcId,
              serial,
              npcGender,
              npcRace,
              npcAttitude,
              guildID,
              npcName,
              level,
              attributes,
              portrait,
              gc);
    else
      npc =
          new Character(
              npcId,
              serial,
              npcGender,
              npcRace,
              npcAttitude,
              guildID,
              npcName,
              level,
              attributes,
              portrait,
              spritesheet,
              staticAvatar,
              gc);

    if (trade) npc.setTrade();
    if (train) npc.setTrain(trainings);

    for (RandomItem ip : invItems) {
      Item it = ip.make();
      if (it != null) npc.addItem(it);
    }

    try {
      Equippable helmet = (Equippable) npc.getItem(headItem);
      Equippable chest = (Equippable) npc.getItem(chestItem);
      Equippable gloves = (Equippable) npc.getItem(handsItem);
      Equippable mainWeap = (Equippable) npc.getItem(mainHandItem);
      Equippable offHand = (Equippable) npc.getItem(offHandItem);
      Equippable boots = (Equippable) npc.getItem(feetItem);
      Equippable amulet = (Equippable) npc.getItem(neckItem);
      Equippable ringA = (Equippable) npc.getItem(fingerAItem);
      Equippable ringB = (Equippable) npc.getItem(fingerBItem);
      Equippable artifact = (Equippable) npc.getItem(this.artifact);

      npc.equipItem(helmet);
      npc.equipItem(chest);
      npc.equipItem(gloves);
      npc.equipItem(mainWeap);
      npc.equipItem(offHand);
      npc.equipItem(boots);
      npc.equipItem(amulet);
      npc.equipItem(ringA);
      npc.equipItem(ringB);
      npc.equipItem(artifact);
    } catch (Exception e) {
      Log.addSystem("npc_pattern:" + npcId + "-fail_to_set_equipment");
    }

    for (String skillId : skills) {
      npc.addSkill(SkillsBase.getSkill(npc, skillId));
    }
    for (String effectId : effects.keySet()) {
      Effect effect = EffectsBase.getEffect(null, effectId);
      effect.setTime(effects.get(effectId));
      npc.getEffects().add(effect);
    }
    for (Profession profession : professions) {
      npc.addProfession(profession);
    }
    for (Dialogue dialogue : dialogues) {
      npc.addDialogue(dialogue);
    }

    return npc;
  }
}
