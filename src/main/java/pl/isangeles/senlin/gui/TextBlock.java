/*
 * TextBlock.java
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
package pl.isangeles.senlin.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.newdawn.slick.TrueTypeFont;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.Position;

/**
 * Class for text with multiple lines
 *
 * @author Isangeles
 */
public class TextBlock {
  private static final int PADDING_LEFT = Coords.getDis(10);
  private List<String> textLines = new ArrayList<>();
  private String text;
  private int charsInLine;
  private TrueTypeFont ttf;
  private Position pos;
  /**
   * Converts list with string values to TextBlocks list
   *
   * @param rawStrings List with strings
   * @param charsInLine Maximal number of characters in line
   * @param ttf TrueTypeFont for text
   * @return ArrayList with blocks of text
   */
  public static List<TextBlock> toTextBlocks(
      List<String> rawStrings, int charsInLine, TrueTypeFont ttf) {
    List<TextBlock> blocks = new ArrayList<>();

    for (String string : rawStrings) {
      blocks.add(new TextBlock(string, charsInLine, ttf));
    }

    return blocks;
  }
  /**
   * Text block constructor
   *
   * @param text String with text
   * @param charsInLine Maximum number of chars in single line
   * @param ttf Slick TrueTypeFont
   */
  public TextBlock(String text, int charsInLine, TrueTypeFont ttf) {
    this.text = text;
    this.ttf = ttf;
    this.charsInLine = charsInLine;

    pos = new Position();

    for (String line : text.split("\r?\n")) {
      addLines(line, charsInLine);
    }
  }
  /**
   * Creates new empty text block
   *
   * @param charsInLine Maximum number of characters in one line
   * @param ttf Slick TTF for text
   */
  public TextBlock(int charsInLine, TrueTypeFont ttf) {
    this.ttf = ttf;
    this.charsInLine = charsInLine;

    pos = new Position();
  }
  /**
   * Draws text block on specified position
   *
   * @param x Position on x-axis
   * @param y Position on y-axis
   */
  public void draw(float x, float y) {
    pos.x = (int) x;
    pos.y = (int) y;

    for (int i = 0; i < textLines.size(); i++) {
      if (textLines.size() > 1)
        ttf.drawString(x + PADDING_LEFT, y + ttf.getHeight(textLines.get(i)) * i, textLines.get(i));
      else ttf.drawString(x + PADDING_LEFT, y, textLines.get(0));
    }
  }
  /** Draws block on last specified position */
  public void draw() {
    this.draw(pos.x, pos.y);
  }

  public void move(int x, int y) {
    pos.x = x;
    pos.y = y;
  }
  /**
   * Adds texts to box
   *
   * @param text String with text
   */
  public void addText(String text) {
    textLines.add("");
    add(text);
  }
  /** Clears text block */
  public void clear() {
    textLines.clear();
  }
  /**
   * Returns string with whole text
   *
   * @return String with whole block text
   */
  public String getText() {
    return text;
  }
  /**
   * Returns text block height
   *
   * @return Float block height
   */
  public float getTextHeight() {
    return ttf.getHeight(text) * textLines.size();
  }
  /**
   * Returns maximal text block width
   *
   * @return Text block width
   */
  public float getTextWidth() {
    float maxWidth = 0;
    for (String line : textLines) {
      float lineWidth = ttf.getWidth(line);
      if (lineWidth > maxWidth) maxWidth = lineWidth;
    }
    return maxWidth + PADDING_LEFT;
  }
  /**
   * Returns box position
   *
   * @return X,Y position
   */
  public Position getPosition() {
    return pos;
  }

  private void addLines(String text, int charsInLine) {
    int index = 0;

    while (index < text.length()) {
      textLines.add(text.substring(index, Math.min(index + charsInLine, text.length())));
      index += charsInLine;
      /*
      if(text.contains(System.lineSeparator()) && text.indexOf(System.lineSeparator()) != text.length()-1)
      {
      	textLines.add(text.substring(index, Math.min(Math.min(index + charsInLine, index + text.indexOf(System.lineSeparator())), text.length())));

      	if((index + charsInLine) > (index + text.indexOf(System.lineSeparator())))
          	index += text.indexOf(System.lineSeparator());
          else
          	index += charsInLine;
      }
      else
      {
      	textLines.add(text.substring(index, Math.min(index + charsInLine, text.length())));
          index += charsInLine;
      }
      */
    }
  }
  /**
   * Splits specified text to lines (by new line separator) and adds them to text lines list
   *
   * @param text String with text
   */
  private void add(String text) {
    Scanner scann = new Scanner(text);
    scann.useDelimiter("\r?\n");

    while (scann.hasNext()) {
      addLines(scann.next(), charsInLine);
    }

    scann.close();
  }
}
