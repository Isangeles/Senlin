/*
 * CharMan.java
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
package pl.isangeles.senlin.cli.tools;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.newdawn.slick.SlickException;
import pl.isangeles.senlin.cli.CommandInterface;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.Usable;
import pl.isangeles.senlin.core.character.Attitude;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.character.Guild;
import pl.isangeles.senlin.core.craft.Profession;
import pl.isangeles.senlin.core.craft.ProfessionType;
import pl.isangeles.senlin.core.craft.Recipe;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.out.CharacterOut;
import pl.isangeles.senlin.data.GuildsBase;
import pl.isangeles.senlin.data.ItemsBase;
import pl.isangeles.senlin.data.QuestsBase;
import pl.isangeles.senlin.data.RecipesBase;
import pl.isangeles.senlin.data.SkillsBase;
import pl.isangeles.senlin.states.GameWorld;
import pl.isangeles.senlin.util.Position;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.util.TilePosition;

/**
 * CLI tool for game characters management
 *
 * @author Isangeles
 */
public class CharMan implements CliTool {
  private static final String TOOL_NAME = "charman";
  private Character player;
  private GameWorld gw;
  /**
   * Characters manager constructor
   *
   * @param player Player character
   * @param gw Game world
   */
  public CharMan(Character player, GameWorld gw) {
    this.player = player;
    this.gw = gw;
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.cli.tools.CliTool#getName()
   */
  @Override
  public String getName() {
    return TOOL_NAME;
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.cli.tools.CliTool#equals(java.lang.String)
   */
  @Override
  public boolean equals(String name) {
    return name.equals(TOOL_NAME);
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.cli.CliTool#handleCommand(java.lang.String)
   */
  @Override
  public String[] handleCommand(String line) {
    String[] output = {CommandInterface.SUCCESS, ""};
    Scanner scann = new Scanner(line);
    String commandTarget = "";
    String command = "";
    try {
      commandTarget = scann.next();
      command = scann.nextLine();

      if (commandTarget.equals("player")) output = characterCommands(command, player);
      else if (commandTarget.equals("target")) {
        if (player.getTarget() != null && Character.class.isInstance(player.getTarget())) {
          Character target = (Character) player.getTarget();
          output = characterCommands(command, target);
        } else {
          Log.addSystem("no valid target");
          output[0] = CommandInterface.SYNTAX_ERROR;
        }
      } else {
        Character npc = gw.getCurrentChapter().getCharacter(commandTarget);
        if (npc != null) output = characterCommands(command, npc);
        else {
          Log.addSystem("no such target for charman: " + commandTarget);
          output[0] = CommandInterface.SYNTAX_ERROR;
        }
      }
    } catch (NoSuchElementException e) {
      Log.addSystem("command scann error: " + line);
      output[0] = CommandInterface.SYNTAX_ERROR;
    } finally {
      scann.close();
    }

    return output;
  }
  /**
   * Handles character commands TODO exceptions catching
   *
   * @param commandLine Rest of command line (after target)
   * @param target Command target
   * @return Command result[0] and output[1]
   */
  private String[] characterCommands(String commandLine, Character target) {
    Scanner scann = new Scanner(commandLine);
    String command = scann.next();
    String prefix = scann.nextLine();
    scann.close();

    switch (command) {
      case "add":
        return addCommands(prefix, target);
      case "remove":
        return removeCommands(prefix, target);
      case "set":
        return setCommands(prefix, target);
      case "show":
        return showCommands(prefix, target);
      case "use":
        return useCommands(prefix, target);
      case "is":
        return isCommands(prefix, target);
      default:
        // Log.addSystem(command + " " + TConnector.getText("ui", "logCmdPla"));
        return new String[] {CommandInterface.TOOL_ERROR, "no such command:" + command};
    }
  }
  /**
   * Handles set commands
   *
   * @param commandLine Rest of command line (after command)
   * @param target Target of command
   * @return Command result[0] and output[1]
   */
  private String[] setCommands(String commandLine, Character target) {
    String result = "0";
    String out = "";
    Scanner scann = new Scanner(commandLine);
    try {
      String prefix = scann.next();
      String arg1 = scann.next();
      switch (prefix) {
        case "-g":
        case "--guild":
          Guild guild = GuildsBase.getGuild(arg1);
          target.setGuild(guild);
          break;
        case "-a":
        case "--attitude":
          Attitude att = Attitude.fromString(arg1);
          target.setAttitude(att);
          break;
        case "-p":
        case "--position":
          String[] pos = arg1.split("x");
          int x = Integer.parseInt(pos[0]);
          int y = Integer.parseInt(pos[1]);
          if (!target.setPosition(new Position(x, y))) result = "2";
          break;
        case "-pt":
        case "--positionTile":
          String[] posT = arg1.split("x");
          int xT = Integer.parseInt(posT[0]);
          int yT = Integer.parseInt(posT[1]);
          if (!target.setPosition(new TilePosition(xT, yT))) result = "2";
          break;
        case "-d":
        case "--destination":
          String[] destPos = arg1.split("x");
          int destX = Integer.parseInt(destPos[0]);
          int destY = Integer.parseInt(destPos[1]);
          target.moveTo(destX, destY);
          break;
        case "-dt":
        case "--destinationTile":
          String[] tilePos = arg1.split("x");
          int row = Integer.parseInt(tilePos[0]);
          int column = Integer.parseInt(tilePos[1]);
          Position p = new TilePosition(row, column).asPosition();
          target.moveTo(p.x, p.y);
          break;
        case "-at":
        case "--attackTarget":
          Targetable attackTarget = gw.getCurrentChapter().getTObject(arg1);
          if (attackTarget != null) target.getSignals().startCombat(attackTarget);
          else {
            result = CommandInterface.COMMAND_ERROR_2;
            out = "no such object: " + arg1;
            // Log.addSystem("no such object: " + arg1);
          }
          break;
        default:
          Log.addSystem(prefix + " " + TConnector.getText("ui", "logCmdSet"));
          result = "3";
          break;
      }
    } catch (NoSuchElementException e) {
      // Log.addSystem("Not enought arguments");
      out = "Not enought arguments";
      result = CommandInterface.COMMAND_ERROR;
    } catch (NumberFormatException e) {
      // Log.addSystem(TConnector.getText("ui", "logBadVal") + ":'" + commandLine + "'");
      out = "args error:" + commandLine;
      result = CommandInterface.COMMAND_ERROR;
    } finally {
      scann.close();
    }
    return new String[] {result, out};
  }
  /**
   * Handles add commands
   *
   * @param commandLine Rest of command line (after command)
   * @param target Target of command
   * @return Command result[0] and output[1]
   */
  private String[] addCommands(String commandLine, Character target) {
    String result = "0";
    String out = "";

    Scanner scann = new Scanner(commandLine);
    try {
      String prefix = scann.next();
      String arg1 = scann.next();
      String arg2 = null;
      if (scann.hasNext()) arg2 = scann.next();
      switch (prefix) {
        case "-i":
        case "--item":
          int iAmount = 1;
          if (arg2 != null) iAmount = Integer.parseInt(arg2);
          for (int i = 0; i < iAmount; i++) {
            if (!target.addItem(ItemsBase.getItem(arg1))) {
              result = "2";
              break;
            }
          }
          break;
        case "-g":
        case "--gold":
          int amount = Integer.parseInt(arg1);
          // TODO looks shady
          Item[] gold = new Item[amount];
          for (Item coin : gold) {
            coin = ItemsBase.getItem("gold01");
          }
          if (!target.getInventory().addAll(gold)) result = "2";
          break;
        case "-h":
        case "--health":
          target.modHealth(Integer.parseInt(arg1));
          break;
        case "-m":
        case "--mana":
          target.modMagicka(Integer.parseInt(arg1));
          break;
        case "-e":
        case "--experience":
          target.modExperience(Integer.parseInt(arg1));
          break;
        case "-s":
        case "--skills":
          if (!target.addSkill(SkillsBase.getSkill(target, arg1))) result = "2";
          break;
        case "-sp":
        case "--speech":
          String speech = TConnector.getTextFromChapter("speeches", arg1);
          target.speak(speech);
          break;
        case "-p":
        case "--profession":
          if (!target.addProfession(new Profession(ProfessionType.fromString(arg1)))) result = "2";
          break;
        case "-r":
        case "--recipe":
          Recipe recipe = RecipesBase.get(arg1);
          if (recipe != null && target.getProfession(recipe.getType()) != null) {
            if (!target.getProfession(recipe.getType()).add(recipe)) result = "2";
          }
          break;
        case "-f":
        case "--flag":
          target.getFlags().add(arg1);
          break;
        case "-q":
        case "--quest":
          target.startQuest(QuestsBase.get(arg1));
          break;
        case "-l":
        case "--level":
          target.levelUp();
          break;
        default:
          Log.addSystem(prefix + " " + TConnector.getText("ui", "logCmdAdd"));
          result = "3";
          break;
      }
    } catch (NumberFormatException e) {
      Log.addSystem(TConnector.getText("ui", "logBadVal") + ":'" + commandLine + "'");
      result = "1";
    } catch (NoSuchElementException e) {
      Log.addSystem("not enough arguments" + ":'" + commandLine + "'");
      result = "1";
    } catch (SlickException | IOException | FontFormatException e) {
      Log.addSystem("some fatal error" + ":'" + commandLine + "'");
      result = "1";
    } finally {
      scann.close();
    }

    return new String[] {result, out};
  }
  /**
   * Handles remove commands
   *
   * @param commLine Rest of command line (after command)
   * @param target Target of command
   * @return Command result[0] and output[1]
   */
  private String[] removeCommands(String commandLine, Character target) {
    String result = "0";
    String out = "";
    Scanner scann = new Scanner(commandLine);
    try {
      String prefix = scann.next();
      String arg1 = scann.next();
      String arg2 = null;
      if (scann.hasNext()) arg2 = scann.next();

      switch (prefix) {
        case "-h":
        case "--health":
          target.modHealth(-Integer.parseInt(arg1));
          break;
        case "-m":
        case "-mana":
          target.modMagicka(-Integer.parseInt(arg1));
          break;
        case "-e":
        case "--experience":
          target.modExperience(-Integer.parseInt(arg1));
          break;
        case "-i":
        case "--item":
          if (arg2 == null) {
            if (!target.getInventory().remove(arg1, 1)) result = "2";
          } else {
            int amount = Integer.parseInt(arg2);
            if (!target.getInventory().remove(arg1, amount)) result = "2";
          }
          break;
        default:
          Log.addSystem(
              prefix + " " + TConnector.getText("ui", "logCmdRem") + ":'" + commandLine + "'");
          result = "3";
          break;
      }
    } catch (NoSuchElementException e) {
      Log.addSystem("Not enought arguments" + "-/" + commandLine + "/");
      result = "1";
    } catch (NumberFormatException e) {
      Log.addSystem(TConnector.getText("ui", "logBadVal") + ":'" + commandLine + "'");
      result = "1";
    } finally {
      scann.close();
    }
    return new String[] {result, out};
  }
  /**
   * Handles show commands
   *
   * @param commLine Rest of command line (after command)
   * @param target Target of command
   * @return Command result[0] and output[1]
   */
  private String[] showCommands(String commandLine, Character target) {
    String result = CommandInterface.SUCCESS;
    String out = "";
    Scanner scann = new Scanner(commandLine);
    try {
      String prefix = scann.next();
      String arg1 = null;
      if (scann.hasNext()) arg1 = scann.next();

      switch (prefix) {
        case "-f":
        case "--flag":
          out = target.getSerialId() + "-flags: " + target.getFlags().list();
          break;
        case "-pr":
        case "--recipes":
          out = target.getProfession(ProfessionType.fromString(arg1)).toString();
          break;
        case "-e":
        case "--effects":
          out = target.getEffects().list();
          break;
        case "-d":
        case "--dis":
        case "--distance":
          if (arg1 != null) {
            Targetable object = null;

            if (arg1.equals("player")) object = player;
            else object = gw.getCurrentChapter().getTObject(arg1);

            if (object != null)
              out = "range from " + object.getId() + ":" + target.getRangeFrom(object);
          } else throw new NoSuchElementException();
          break;
        case "-r":
        case "--race":
          out = target.getRace().toString();
          break;
        case "-q":
        case "--quests":
          out = target.getSerialId() + "-quests:" + target.getQuests().list();
          break;
        default:
          // Log.addSystem(prefix + " " + TConnector.getText("ui", "logCmdSho") + ":'" + commandLine
          // + "'");
          out = "no such option:'" + prefix + "'";
          result = CommandInterface.OPTION_ERROR;
          break;
      }
    } catch (NoSuchElementException e) {
      // Log.addSystem("empty value:" + "'" + commandLine + "'");
      out = "empty value:" + "'" + commandLine + "'";
      result = CommandInterface.COMMAND_ERROR;
    } finally {
      scann.close();
    }
    return new String[] {result, out};
  }
  /**
   * Handles use command
   *
   * @param commandLine Command
   * @param target Command target
   * @return Command result[0] and output[1]
   */
  private String[] useCommands(String commandLine, Character target) {
    String result = "0";
    String out = "";
    Scanner scann = new Scanner(commandLine);
    try {
      String prefix = scann.next();
      String arg1 = scann.next();
      String arg2 = null;
      if (scann.hasNext()) arg2 = scann.next();

      switch (prefix) {
        case "-s":
        case "--skill":
          Targetable skillTarget = null;
          if (arg2 != null) skillTarget = gw.getCurrentChapter().getTObject(arg2);

          CharacterOut charOut = CharacterOut.UNKNOWN;

          if (skillTarget != null)
            charOut = target.useSkillOn(skillTarget, target.getSkills().get(arg1));
          else charOut = target.useSkill(target.getSkills().get(arg1));

          if (charOut != CharacterOut.SUCCESS)
            Log.addSystem(charOut.toString()); // display error message
          break;
        case "-i":
        case "--item":
          Item item = target.getInventory().getItem(arg1);
          if (item != null && Usable.class.isInstance(item)) {
            Usable itemToUse = item;
            if (arg2 == null) {
              itemToUse.use(target, target);
              out = "0";
            } else {
              Targetable useTarget = gw.getCurrentChapter().getTObject(arg2);
              if (useTarget != null) {
                itemToUse.use(target, useTarget);
              }
            }
          }
          break;
        default:
          result = "3";
          break;
      }
    } catch (NoSuchElementException e) {
      Log.addSystem("not enough arguments" + ":'" + commandLine + "'");
      result = "1";
    } finally {
      scann.close();
    }

    return new String[] {result, out};
  }
  /**
   * Handles is commands
   *
   * @param commandLine Command
   * @param target Command target
   * @return Command output
   */
  private String[] isCommands(String commandLine, Character target) {
    String result = "0";
    String out = "";
    Scanner scann = new Scanner(commandLine);
    try {
      String prefix = scann.next();
      String arg1 = null;
      String arg2 = null;

      switch (prefix) {
        case "-d<":
        case "--distance<":
          arg1 = scann.next();
          if (scann.hasNext()) arg2 = scann.next();

          int disL = Integer.parseInt(arg1);
          Targetable objectDisL = gw.getCurrentChapter().getTObject(arg2);
          if (objectDisL != null) {
            if (target.getRangeFrom(objectDisL) < disL) out = "true";
            else out = "false";
          } else result = "2";
          break;
        case "-d>":
        case "--distance>":
          arg1 = scann.next();
          if (scann.hasNext()) arg2 = scann.next();

          int disH = Integer.parseInt(arg1);
          Targetable objectDisH = gw.getCurrentChapter().getTObject(arg2);
          if (objectDisH != null) {
            if (target.getRangeFrom(objectDisH) > disH) out = "true";
            else out = "false";
          } else result = "2";
          break;
        case "-pt":
        case "--positionTile":
          arg1 = scann.next();
          TilePosition posToCheck = new TilePosition(arg1.replaceAll("x", ";"));
          TilePosition targetPos = new TilePosition(new Position(target.getPosition()));
          out = Boolean.toString(posToCheck.equals(targetPos));
          break;
        case "-f":
        case "--flag":
          arg1 = scann.next();

          if (target.getFlags().contains(arg1)) out = "true";
          else out = "false";
          break;
        case "-f!":
        case "--flag!":
          arg1 = scann.next();
          if (!target.getFlags().contains(arg1)) out = "true";
          else out = "false";
          break;
        case "-l":
        case "--live":
          out = Boolean.toString(target.isLive());
          break;
        default:
          // Log.addSystem("no such option for is: " + prefix);
          out = "no such option for is: " + prefix;
          result = "3";
          break;
      }
    } catch (NoSuchElementException e) {
      // Log.addSystem("not enough arguments" + ":'" + commandLine + "'");
      out = "not enough arguments" + ":'" + commandLine + "'";
      result = "1";
      e.printStackTrace();
    } catch (NumberFormatException e) {
      // Log.addSystem("bad argument value" + ":'" + commandLine + "'");
      out = "bad argument value" + ":'" + commandLine + "'";
      result = "1";
    } finally {
      scann.close();
    }

    return new String[] {result, out};
  }
}
