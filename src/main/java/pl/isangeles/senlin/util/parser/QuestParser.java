/*
 * QuestParser.java
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
package pl.isangeles.senlin.util.parser;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pl.isangeles.senlin.core.quest.Objective;
import pl.isangeles.senlin.core.quest.ObjectiveType;
import pl.isangeles.senlin.core.quest.Quest;
import pl.isangeles.senlin.core.quest.Stage;

/**
 * Class for external quest base parsing
 *
 * @author Isangeles
 */
public class QuestParser {
  /** Private constructor to prevent initialization */
  private QuestParser() {}
  /**
   * Parses specified quest node from XML base to quest object
   *
   * @param questNode Quest node from quests base
   * @return Quest object
   */
  public static Quest getQuestFromNode(Node questNode) throws NumberFormatException {
    Element questE = (Element) questNode;

    String id = questE.getAttribute("id");
    Node qFlagsNode = questE.getElementsByTagName("qFlags").item(0);
    List<String> flagsOnStart = getFlags(qFlagsNode, "on", "start");
    List<String> flagsOffStart = getFlags(qFlagsNode, "off", "start");
    List<String> flagsOnEnd = getFlags(qFlagsNode, "on", "end");
    List<String> flagsOffEnd = getFlags(qFlagsNode, "off", "end");
    List<Stage> stages = new ArrayList<>();

    Node stagesNode = questE.getElementsByTagName("stages").item(0);
    NodeList stagesList = stagesNode.getChildNodes();
    for (int i = 0; i < stagesList.getLength(); i++) {
      Node stageNode = stagesList.item(i);
      if (stageNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
        stages.add(getStageFromNode(stageNode));
    }

    return new Quest(id, flagsOnStart, flagsOffStart, flagsOnEnd, flagsOffEnd, stages);
  }
  /**
   * Parses specified stage node
   *
   * @param stageNode Stage node
   * @return Stage object
   */
  private static Stage getStageFromNode(Node stageNode) throws NumberFormatException {
    Element stageE = (Element) stageNode;

    boolean startStage = stageE.hasAttribute("start");
    String id = stageE.getAttribute("id");
    String nextStage = stageE.getAttribute("next");

    Node sFlagsNode = stageE.getElementsByTagName("sFlags").item(0);
    List<String> flagsOnStart = getFlags(sFlagsNode, "on", "start");
    List<String> flagsOffStart = getFlags(sFlagsNode, "off", "start");
    List<String> flagsOnEnd = getFlags(sFlagsNode, "on", "end");
    List<String> flagsOffEnd = getFlags(sFlagsNode, "off", "end");

    List<Objective> objectives = new ArrayList<>();

    Element objectivesE = (Element) stageE.getElementsByTagName("objectives").item(0);
    NodeList objectivesList = objectivesE.getChildNodes();
    for (int i = 0; i < objectivesList.getLength(); i++) // accepts both objectives and finishers
    {
      Node objectiveNode = objectivesList.item(i);
      if (objectiveNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE) {
        Objective objective = getObjectiveFromNode(objectiveNode);
        objectives.add(objective);
      }
    }

    return new Stage(
        id,
        flagsOnStart,
        flagsOffStart,
        flagsOnEnd,
        flagsOffEnd,
        nextStage,
        objectives,
        startStage);
  }
  /**
   * Parses specified objective node
   *
   * @param objectiveNode Objective node
   * @return Objective object
   */
  private static Objective getObjectiveFromNode(Node objectiveNode) throws NumberFormatException {
    Element objectiveE = (Element) objectiveNode;

    String type = objectiveE.getAttribute("type");
    boolean finisher = (objectiveE.getTagName() == "finisher");
    String to = "";
    if (finisher) to = objectiveE.getAttribute("to");
    int amount = 0;

    if (type.equals("kill") || type.equals("gather"))
      amount = Integer.parseInt(objectiveE.getAttribute("amount"));

    String target = objectiveE.getTextContent();

    return new Objective(ObjectiveType.fromString(type), target, amount, finisher, to);
  }
  /**
   * Parses specified flags node to list with flags
   *
   * @param flagsNode XML flags node
   * @param flagsType String with desired flags type ('on' or 'off')
   * @return List with flags IDs
   */
  private static List<String> getFlags(Node flagsNode, String flagsType, String flagsTrigger) {
    List<String> flags = new ArrayList<>();
    if (flagsNode == null) return flags;

    Element flagsE = (Element) flagsNode;
    NodeList flagNl = flagsE.getElementsByTagName("flag");
    for (int i = 0; i < flagNl.getLength(); i++) {
      Node flagNode = flagNl.item(i);
      if (flagNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE) {
        Element flagE = (Element) flagNode;
        String flagType = flagE.getAttribute("type");
        String flagTrigger = flagE.getAttribute("on");
        if (flagType.equals(flagsType) && flagTrigger.equals(flagsTrigger)) {
          flags.add(flagE.getTextContent());
        }
      }
    }
    return flags;
  }
}
