/*
 * DialogueParser.java
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
package pl.isangeles.senlin.util.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.cli.Script;
import pl.isangeles.senlin.core.bonus.Modifier;
import pl.isangeles.senlin.core.dialogue.Answer;
import pl.isangeles.senlin.core.dialogue.Dialogue;
import pl.isangeles.senlin.core.dialogue.DialoguePart;
import pl.isangeles.senlin.core.dialogue.DialogueTransfer;
import pl.isangeles.senlin.core.req.Requirement;
import pl.isangeles.senlin.core.req.Requirements;

/**
 * Static class for dialogues XML base parsing
 *
 * @author Isangeles
 */
public class DialogueParser {
  private static Node defaultDialogueNode;
  /** Private constructor to prevent initialization */
  private DialogueParser() {}
  /**
   * Returns default dialogue XML node
   *
   * @return Node with parsed default dialogue
   */
  public static Node getDefDialogueNode() {
    return defaultDialogueNode;
  }
  /**
   * Parses XML dialogue node
   *
   * @param dialogueNode Dialogue node from XML dialogues base
   * @return Dialogue object
   */
  public static Dialogue getDialogueFromNode(Node dialogueNode) {
    Element dialog = (Element) dialogueNode;
    String dialogId = dialog.getAttribute("id");
    List<DialoguePart> partsList = new ArrayList<>();

    for (int j = 0; j < dialog.getElementsByTagName("text").getLength(); j++) {
      Node textNode = dialog.getElementsByTagName("text").item(j);
      partsList.add(getDialoguePartFromNode(textNode));
    }

    Node reqNode = dialog.getElementsByTagName("dReq").item(0);
    List<Requirement> reqs = RequirementsParser.getReqFromNode(reqNode);

    return new Dialogue(dialogId, reqs, partsList);
  }
  /**
   * Returns dialogue part from specified XML node
   *
   * @param textNode XML node from dialogues base file
   * @return DialoguePart object
   */
  private static DialoguePart getDialoguePartFromNode(Node textNode) {
    List<Answer> answersList = new ArrayList<>();
    if (textNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE) {
      Element textE = (Element) textNode;
      String id = textE.getAttribute("id");
      String ordinalId = textE.getAttribute("ordinal");
      boolean start = Boolean.parseBoolean(textE.getAttribute("start"));

      NodeList answers = textE.getElementsByTagName("answer");
      for (int i = 0; i < answers.getLength(); i++) {
        Node answerNode = answers.item(i);
        if (answerNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE) {
          answersList.add(getAnswerFromNode(answerNode, id));
        }
      }

      Node reqNode = textE.getElementsByTagName("pReq").item(0);
      List<Requirement> req = RequirementsParser.getReqFromNode(reqNode);

      Element modE = (Element) textE.getElementsByTagName("mod").item(0);

      if (modE != null) {
        DialogueTransfer transfer = new DialogueTransfer();
        Node transferNode = modE.getElementsByTagName("transfer").item(0);
        if (transferNode != null) transfer = getTransferFromNode(transferNode);

        List<Modifier> modOwner = new ArrayList<>();
        Node modOwnerNode = modE.getElementsByTagName("modOwner").item(0);
        if (modOwnerNode != null) modOwner = ModifiersParser.getModifiersFromNode(modOwnerNode);

        List<Modifier> modPlayer = new ArrayList<>();
        Node modPlayerNode = modE.getElementsByTagName("modPlayer").item(0);
        if (modPlayerNode != null) modPlayer = ModifiersParser.getModifiersFromNode(modPlayerNode);

        List<Script> scripts = new ArrayList<>();
        Node scriptsNode = modE.getElementsByTagName("scripts").item(0);
        if (scriptsNode != null) scripts = ScriptParser.getScriptsFromNode(scriptsNode);

        return new DialoguePart(
            id, ordinalId, start, req, answersList, transfer, modOwner, modPlayer, scripts);
      }
      return new DialoguePart(id, ordinalId, start, req, answersList);
    } else {
      Log.addSystem("dialog_builder_msg//fail");
    }
    answersList.add(new Answer("bye01", "err01", "", false, false, true, new Requirements()));
    return new DialoguePart("err01", "", true, null, answersList);
  }
  /**
   * Parses specified answer node to dialogue answer
   *
   * @param answerNode XML document node
   * @return Dialogue answer from specified node
   */
  private static Answer getAnswerFromNode(Node answerNode, String dialoguePart) {
    Element answerE = (Element) answerNode;

    String aId = answerE.getAttribute("id");
    boolean train = false;
    boolean trade = false;
    boolean end = false;
    if (answerE.hasAttribute("train")) train = Boolean.parseBoolean(answerE.getAttribute("train"));
    if (answerE.hasAttribute("trade")) trade = Boolean.parseBoolean(answerE.getAttribute("trade"));
    if (answerE.hasAttribute("end")) end = Boolean.parseBoolean(answerE.getAttribute("end"));

    String toId = answerE.getAttribute("to");
    if (toId.equals("end")) end = true;
    if (toId.equals("train")) train = true;
    if (toId.equals("trade")) trade = true;

    if (train || trade) end = true;

    Node reqNode = answerE.getElementsByTagName("aReq").item(0);
    List<Requirement> reqs = RequirementsParser.getReqFromNode(reqNode);

    return new Answer(aId, dialoguePart, toId, train, trade, end, reqs);
  }
  /**
   * Parses specified transfer node to dialogue transfer
   *
   * @param transferNode XML document node
   * @return Dialogue transfer from specified node
   */
  private static DialogueTransfer getTransferFromNode(Node transferNode) {
    Element transferE = (Element) transferNode;
    Map<String, Integer> iToGive = new HashMap<>();
    Map<String, Integer> iToTake = new HashMap<>();

    Element giveE = (Element) transferE.getElementsByTagName("give").item(0);
    if (giveE != null) {
      NodeList itemsToGiveList = giveE.getChildNodes();
      for (int j = 0; j < itemsToGiveList.getLength(); j++) {
        Node itemNode = itemsToGiveList.item(j);
        if (itemNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE) {
          Element itemE = (Element) itemNode;
          int amount = 1;
          if (itemE.hasAttribute("amount")) {
            try {
              amount = Integer.parseInt(itemE.getAttribute("amount"));
            } catch (NumberFormatException e) {
              Log.addSystem("dialogue_parser_fail-msg///transfer item node currupted!");
            }
          }
          iToGive.put(itemE.getTextContent(), amount);
        }
      }
    }

    Element takeE = (Element) transferE.getElementsByTagName("take").item(0);
    if (takeE != null) {
      NodeList itemsToTakeList = takeE.getChildNodes();
      for (int j = 0; j < itemsToTakeList.getLength(); j++) {
        Node itemNode = itemsToTakeList.item(j);
        if (itemNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE) {
          Element itemE = (Element) itemNode;
          int amount = 1;
          if (itemE.hasAttribute("amount")) {
            try {
              amount = Integer.parseInt(itemE.getAttribute("amount"));
            } catch (NumberFormatException e) {
              Log.addSystem("dialogue_parser_fail-msg///transfer item node currupted!");
            }
          }
          iToTake.put(itemE.getTextContent(), amount);
        }
      }
    }

    return new DialogueTransfer(iToGive, iToTake);
  }
}
