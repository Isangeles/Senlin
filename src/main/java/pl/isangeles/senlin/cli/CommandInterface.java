/*
 * CommandInterface.java
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
package pl.isangeles.senlin.cli;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;
import pl.isangeles.senlin.cli.tools.CharMan;
import pl.isangeles.senlin.cli.tools.CliTool;
import pl.isangeles.senlin.cli.tools.UiMan;
import pl.isangeles.senlin.cli.tools.WorldMan;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.gui.tools.UserInterface;
import pl.isangeles.senlin.states.GameWorld;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for game command-line interface
 *
 * <p>command syntax: $[tool] [target] [command] [-option] [arguments]
 *
 * @author Isangeles
 */
public class CommandInterface {
  // output signals codes
  public static final String SUCCESS = "0",
      COMMAND_ERROR = "1",
      COMMAND_ERROR_2 = "2",
      OPTION_ERROR = "3",
      TOOL_ERROR = "4",
      TOOL_NOT_FOUND = "8",
      CLI_ERROR = "9",
      SYNTAX_ERROR = "5";

  private Map<String, CliTool> tools;
  private Character player;
  private ScriptProcessor ssp;
  /**
   * Command interface constructor
   *
   * @param player Player character
   * @param gw Game world
   */
  public CommandInterface(Character player, GameWorld gw) {
    this.player = player;

    tools = new HashMap<>();
    CharMan charman = new CharMan(player, gw);
    WorldMan worldman = new WorldMan(gw);
    tools.put(charman.getName(), charman);
    tools.put(worldman.getName(), worldman);

    ssp = new ScriptProcessor(this);
  }
  /**
   * Executes specified command
   *
   * @param line String with command
   * @return Array with command result[0] and output[1]
   */
  public String[] executeCommand(String line) {
    String[] output = {"0", ""};

    if (line.startsWith("$")) {
      Scanner scann = new Scanner(line);
      try {
        String toolName = scann.next().replace("$", "");
        String command = scann.nextLine();

        if (toolName.equals("debug")) // TODO debug mode don't work
        {
          if (command.equals("on")) {
            Log.setDebug(true);
          } else if (command.equals("off")) {
            Log.setDebug(false);
          }
        } else {
          if (tools.containsKey(toolName)) {
            CliTool tool = tools.get(toolName);
            output = tool.handleCommand(command);
          } else {
            Log.addWarning(toolName + " " + TConnector.getText("ui", "logCmdFail"));
            output[0] = CommandInterface.TOOL_NOT_FOUND;
          }
        }
      } catch (NoSuchElementException e) {
        Log.addSystem("Command scann error: " + line);
        output[0] = CommandInterface.COMMAND_ERROR;
      } finally {
        scann.close();
      }
    } else output[1] = line;

    return output;
  }
  /**
   * Executes specified expression Expressions: if false then: [command1] !| [command2] - executes
   * command 2 if command 1 returns false as output value if true then: [command1] | [command2] -
   * executes command 2 if command 1 returns true as output value
   *
   * @param expr CLI expression
   * @return Array with command result[0] and output[1]
   */
  public String[] executeExpression(String expr) {
    String[] out = {SUCCESS, ""};
    if (expr.contains(" !| ")) {
      String[] cmds = expr.split(Pattern.quote(" !| "));
      for (String cmd : cmds) {
        try {
          out = executeCommand(cmd);
          if (out[1].equals("true")) break;
        } catch (ArrayIndexOutOfBoundsException e) {
          out[0] = CLI_ERROR;
          out[1] = "error";
        }
      }
    } else if (expr.contains(" | ")) {
      String[] cmds = expr.split(Pattern.quote(" | "));
      out[1] = "false";
      for (String cmd : cmds) {
        try {
          if (executeCommand(cmd)[1].equals("true")) out[1] = "true";
          else break;
        } catch (ArrayIndexOutOfBoundsException e) {
          out[0] = CLI_ERROR;
          out[1] = "error";
        }
      }
    } else out = executeCommand(expr);

    return out;
  }
  /**
   * Executes specified script
   *
   * @param script Script to execute
   * @return True if script was successfully executed, false otherwise
   */
  public boolean executeScript(Script script) {
    return ssp.process(script);
  }
  /**
   * Sets GUI to manage by CLI
   *
   * @param uiToMan GUI
   */
  public void setUiMan(UserInterface uiToMan) {
    UiMan uiman = new UiMan(uiToMan);
    tools.put(uiman.getName(), uiman);
  }
  /**
   * Returns player character
   *
   * @return Game character
   */
  public Character getPlayer() {
    return player;
  }
}
