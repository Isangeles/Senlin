/*
 * Log.java
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

import java.util.LinkedList;
import java.util.List;
import pl.isangeles.senlin.util.Stopwatch;
import pl.isangeles.senlin.util.TConnector;

/**
 * Static class for all in-game communicates
 *
 * @author Isangeles
 */
public class Log {
  private static List<String> commList = new LinkedList<String>();
  private static boolean debugMode;
  /** Private constructor to prevent initialization */
  private Log() {};
  /**
   * Adds new information to communicates stack
   *
   * @param information Content of communicate
   */
  public static void addInformation(String information) {
    clearOld();
    add(information);
  }
  /**
   * Adds new warning to communicates stack
   *
   * @param warning
   */
  public static void addWarning(String warning) {
    clearOld();
    add(TConnector.getText("ui", "logWarn") + ": " + warning);
  }
  /**
   * Adds new debug message to list
   *
   * @param debug Debug message
   */
  public static void addDebug(String debug) {
    clearOld();
    if (debugMode) add(TConnector.getText("ui", "logDebug") + ": " + debug);
  }
  /**
   * Adds new system message to list
   *
   * @param systemMsg System message
   */
  public static void addSystem(String systemMsg) {
    clearOld();
    add(TConnector.getText("ui", "logSysMsg") + ": " + systemMsg);
  }
  /**
   * Adds new combat message to log
   *
   * @param combatMsg String with message
   */
  public static void addCombat(String combatMsg) {
    clearOld();
    add(TConnector.getText("ui", "logCombat") + ": " + combatMsg);
  }
  /**
   * Adds new speech message to log
   *
   * @param who Name of speech author
   * @param speech Content of speech
   */
  public static void addSpeech(String who, String speech) {
    clearOld();
    add(who + " " + TConnector.getText("ui", "logSpeech") + ": " + speech);
  }
  /**
   * Adds message about character points loss
   *
   * @param who The author of the message(e.g. character name)
   * @param value Value deducted from character
   * @param statName Name of the character statistic from which points were deducted
   */
  public static void loseInfo(String who, int value, String statName) {
    clearOld();
    addInformation(
        who + " " + TConnector.getText("ui", "logPtsLose") + ": " + value + " " + statName);
  }
  /**
   * Adds message about character points loss
   *
   * @param who The author of the message(e.g. character name)
   * @param value Value
   * @param statName Name of the character statistic from which points were deducted
   */
  public static void loseInfo(String who, String value, String statName) {
    clearOld();
    addInformation(
        who + " " + TConnector.getText("ui", "logPtsLose") + ": " + value + " " + statName);
  }
  /**
   * Adds message about character points gain
   *
   * @param who The author of the message(e.g. character name)
   * @param value Value deducted from character health
   * @param statName Name of the character statistic to which points were added
   */
  public static void gainInfo(String who, int value, String statName) {
    clearOld();
    addInformation(
        who + " " + TConnector.getText("ui", "logPtsGain") + ": " + value + " " + statName);
  }
  /**
   * Adds message about character points gain
   *
   * @param who The author of the message(e.g. character name)
   * @param value Value
   * @param statName Name of the character statistic to which points were added
   */
  public static void gainInfo(String who, String value, String statName) {
    clearOld();
    addInformation(
        who + " " + TConnector.getText("ui", "logPtsGain") + ": " + value + " " + statName);
  }
  /**
   * Returns message with specified index
   *
   * @param index Index of message on list
   * @return
   */
  public static String get(int index) {
    String message;
    try {
      message = commList.get(index) + System.getProperty("line.separator");
    } catch (IndexOutOfBoundsException e) {
      message = "";
    }
    return message;
  }
  /**
   * Returns list with all messages
   *
   * @return LinkedList with messages
   */
  public static List<String> getAll() {
    return commList;
  }
  /**
   * Returns base size
   *
   * @return Size of base
   */
  public static int size() {
    return commList.size();
  }
  /**
   * Turns debug mode on or off
   *
   * @param debugMode True to activate debug mode on false otherwise
   */
  public static void setDebug(boolean debugMode) {
    Log.debugMode = debugMode;

    if (Log.debugMode) Log.addInformation(TConnector.getText("ui", "logDebugOn"));
    else Log.addInformation(TConnector.getText("ui", "logDebugOff"));
  }
  /**
   * Checks if debug mode is on
   *
   * @return
   */
  public static boolean isDebug() {
    return debugMode;
  }
  /** Removes old communicates TODO fix this */
  private static void clearOld() {
    if (commList.size() > 25) commList.remove(0);
  }

  private static void add(String text) {
    String record = Stopwatch.timeFromMillis(System.currentTimeMillis()) + ":" + text;
    commList.add(record);
  }
}
