/*
 * Module.java
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
package pl.isangeles.senlin.core;

import java.io.File;
import org.newdawn.slick.GameContainer;
import pl.isangeles.senlin.util.Settings;
import pl.isangeles.senlin.util.TConnector;

/**
 * Static class for current game module details
 *
 * @author Isangeles
 */
public final class Module {
  private static String name = "none";
  private static String modulePath;
  private static String activeChapterName;
  private static boolean inModDir;
  /** Private constructor to prevent initialization */
  private Module() {}

  public static boolean setDir(String modName, String chapterName) {
    File modDir = new File("data" + File.separator + "modules" + File.separator + modName);
    if (modDir.isDirectory()) {
      modulePath = "data" + File.separator + "modules" + File.separator + modName;
      name = modName;
      activeChapterName = chapterName;
      inModDir = true;
      return true;
    } else return false;
  }

  public static boolean setDir(String modName) {
    File modDir = new File("data" + File.separator + "modules" + File.separator + modName);
    if (modDir.isDirectory()) {
      modulePath = "data" + File.separator + "modules" + File.separator + modName;
      name = modName;
      String modInfo =
          "data" + File.separator + "modules" + File.separator + name + File.separator + "mod.conf";
      activeChapterName = TConnector.getTextFromFile(modInfo, "startChapter");
      inModDir = true;
      return true;
    } else return false;
  }
  /**
   * Sets chapter with specified ID as active chapter
   *
   * @param chapterId String with desired chapter ID
   */
  public static Chapter getChapter(String chapterId, GameContainer gc) {
    String chapterInfo =
        "data"
            + File.separator
            + "modules"
            + File.separator
            + name
            + File.separator
            + "chapters"
            + File.separator
            + chapterId
            + File.separator
            + "chapter.conf";
    String startScenario = TConnector.getTextFromFile(chapterInfo, "startScenario");
    return new Chapter(chapterId, startScenario, gc);
  }

  public static void nextChapter() {
    String chaptersInfoPath =
        modulePath + File.separator + "chapters" + File.separator + "chapters.list";
    String nextChapterName = TConnector.getTextFromFile(chaptersInfoPath, activeChapterName);
    activeChapterName = nextChapterName;
  }
  /**
   * Returns current module name
   *
   * @return String with module name
   */
  public static String getName() {
    return name;
  }
  /**
   * Returns active chapter name
   *
   * @return String with active chapter name
   */
  public static String getActiveChapterName() {
    return activeChapterName;
  }
  /**
   * Returns path to dialogues base file in current module directory
   *
   * @return String with path to dialogues base file
   */
  public static String getDBasePath() {
    if (inModDir) {
      return "data"
          + File.separator
          + "modules"
          + File.separator
          + name
          + File.separator
          + "chapters"
          + File.separator
          + activeChapterName
          + File.separator
          + "dialogues"
          + File.separator
          + "dialogues";
    } else return null;
  }
  /**
   * Returns path to quests base file in current module directory
   *
   * @return String with path to quests base file
   */
  public static String getQuestsPath() {
    if (inModDir) {
      return "data"
          + File.separator
          + "modules"
          + File.separator
          + name
          + File.separator
          + "chapters"
          + File.separator
          + activeChapterName
          + File.separator
          + "quests"
          + File.separator
          + "quests";
    } else return null;
  }
  /**
   * Returns path to npc base file in current module directory
   *
   * @return String with path to npc base file
   */
  public static String getNpcsPath() {
    if (inModDir) {
      return "data"
          + File.separator
          + "modules"
          + File.separator
          + name
          + File.separator
          + "chapters"
          + File.separator
          + activeChapterName
          + File.separator
          + "npc"
          + File.separator
          + "npc";
    } else return null;
  }
  /**
   * Returns path to guilds base file in current module directory
   *
   * @return String with path to quests base file
   */
  public static String getGuildPath() {
    if (inModDir) {
      return "data"
          + File.separator
          + "modules"
          + File.separator
          + name
          + File.separator
          + "chapters"
          + File.separator
          + activeChapterName
          + File.separator
          + "npc"
          + File.separator
          + "guilds";
    } else return null;
  }
  /**
   * Returns path to area directory in current module directory
   *
   * @return String with path to area directory
   */
  public static String getAreaPath() {
    if (inModDir) {
      return "data"
          + File.separator
          + "modules"
          + File.separator
          + name
          + File.separator
          + "chapters"
          + File.separator
          + activeChapterName
          + File.separator
          + "area";
    } else return null;
  }
  /**
   * Returns path to maps directory in current module directory
   *
   * @return String with path to maps directory
   */
  public static String getAreaMapsPath() {
    if (inModDir) {
      return "data"
          + File.separator
          + "modules"
          + File.separator
          + name
          + File.separator
          + "chapters"
          + File.separator
          + activeChapterName
          + File.separator
          + "area"
          + File.separator
          + "maps";
    } else return null;
  }
  /**
   * Returns path to scripts directory in current module directory
   *
   * @return String with path to scripts directory
   */
  public static String getScriptsPath() {
    if (inModDir) {
      return "data"
          + File.separator
          + "modules"
          + File.separator
          + name
          + File.separator
          + "scripts";
    } else return null;
  }

  /**
   * Returns path to items directory in current module directory
   *
   * @return String with path to items directory
   */
  public static String getItemsPath() {
    if (inModDir) {
      return "data" + File.separator + "modules" + File.separator + name + File.separator + "items";
    } else return null;
  }
  /**
   * Returns path to skills directory in current module directory
   *
   * @return String with path to skills directory
   */
  public static String getSkillsPath() {
    if (inModDir) {
      return "data"
          + File.separator
          + "modules"
          + File.separator
          + name
          + File.separator
          + "skills";
    } else return null;
  }
  /**
   * Returns path to objects directory in current chapter directory
   *
   * @return String with path to objects directory
   */
  public static String getChapterObjectsPath() {
    if (inModDir) {
      return "data"
          + File.separator
          + "modules"
          + File.separator
          + name
          + File.separator
          + "chapters"
          + File.separator
          + activeChapterName
          + File.separator
          + "objects";
    } else return null;
  }
  /**
   * Returns path to objects directory in current module directory
   *
   * @return String with path to objects directory
   */
  public static String getModuleObjectsPath() {
    if (inModDir) {
      return "data"
          + File.separator
          + "modules"
          + File.separator
          + name
          + File.separator
          + "objects";
    } else return null;
  }
  /**
   * Returns path to lang directory for current language in current chapter directory of active
   * module e.g. for english in settings : 'data/modules/[module name]/chapters/[chapter
   * name]/lang/english'
   *
   * @return String with path to dialogues lang file
   */
  public static String getLangPath() {
    if (inModDir) {
      return modulePath
          + File.separator
          + "chapters"
          + File.separator
          + activeChapterName
          + File.separator
          + "lang"
          + File.separator
          + Settings.getLang();
    } else return null;
  }
  /**
   * Returns path to global lang dir in current module directory
   *
   * @return String with path to dialogues lang file
   */
  public static String getGlobalLangPath() {
    if (inModDir) {
      return modulePath + File.separator + "lang" + File.separator + Settings.getLang();
    } else return null;
  }
}
