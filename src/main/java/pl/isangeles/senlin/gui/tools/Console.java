/*
 * Console.java
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
package pl.isangeles.senlin.gui.tools;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import pl.isangeles.senlin.cli.CommandInterface;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.data.GBase;
import pl.isangeles.senlin.gui.TextBlock;
import pl.isangeles.senlin.gui.TextBox;
import pl.isangeles.senlin.gui.TextInput;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.Stopwatch;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for game console command syntax: $[tool] [target] [command] [-option] [arguments]
 *
 * @author Isangeles
 */
final class Console extends TextInput implements UiElement {
  private boolean hide;
  private TextBox logBox;
  private CommandInterface cli;
  private Character player;
  private TrueTypeFont alertTtf;

  private final String warningCatName;
  private String alert;
  private Stopwatch alertTimer = new Stopwatch();
  private boolean alertReq;

  /**
   * Console constructor
   *
   * @param gc Game container for superclass
   * @param player Player character to manipulate by commands
   * @throws SlickException
   * @throws FontFormatException
   * @throws IOException
   */
  public Console(GameContainer gc, CommandInterface cli, Character player)
      throws SlickException, FontFormatException, IOException {
    super(GConnector.getInput("ui/background/consoleBG_DG.png"), "uiConsoleBg", false, gc);
    super.textField =
        new TextField(
            gc,
            textTtf,
            (int) Coords.getX("BR", 0),
            (int) Coords.getY("BR", 0),
            super.getWidth(),
            super.getHeight() - 170,
            this);
    this.cli = cli;
    this.player = player;
    logBox = new TextBox(gc);
    hide = true;
    logBox.setFocus(true);

    Font font = GBase.getFont("mainUiFont");
    alertTtf = new TrueTypeFont(font.deriveFont(Coords.getSize(18f)), true);

    warningCatName = TConnector.getText("ui", "logWarn");
  }
  /**
   * Draws console on unscaled position
   *
   * @param x Position on x-axis
   * @param y Position on y-axis
   * @param g Slick graphic to render text field
   */
  public void draw(float x, float y, Graphics g) {
    super.draw(x, y, false);
    /*
    for(int i = 1; i < 10; i ++)
    {
    	super.textTtf.drawString(super.x, (super.y + super.getScaledHeight() - 7) - textField.getHeight()*i, Log.get(Log.size()-i));
    }
    */
    logBox.draw(x, y, 630f, 180f, false);
    if (alert != null) {
      alertTtf.drawString(Coords.getX("CE", 0), Coords.getY("CE", 350), alert, Color.red);
      if (!alertReq && alertTimer.check() > 3) {
        alert = null;
        alertTimer.stop();
      }
    }

    if (!hide) {
      textField.setLocation((int) super.x, (int) (super.y + super.getScaledHeight()));
      super.render(g);
    }
  }

  @Override
  public void update() {
    for (String msg : Log.getAll()) {
      if (!logBox.contains(msg)) {
        logBox.add(new TextBlock(msg, 80, textTtf));

        if (isAlertMsg(msg)) {
          alert = msg.split(":")[4];
          alertTimer.start();
        }

        // alert = "test alert"; //TEST
        // alertTimer.start(); //TEST
      }
    }
  }

  @Override
  public void keyPressed(int key, char c) {
    if (key == Input.KEY_GRAVE && !hide) {
      super.textField.deactivate();
      hide = true;
      super.textField.setFocus(false);
    } else if (key == Input.KEY_GRAVE && hide) {
      hide = false;
      super.textField.setFocus(true);
    }

    if (key == Input.KEY_ENTER && !hide) {
      String textLine = super.getText();
      if (textLine != null) {
        if (textLine.startsWith("$")) // if entered text is a game command
        {
          String[] output = cli.executeCommand(super.getText());
          Log.addSystem("command out[" + output[0] + "]: " + output[1]);
        } else player.speak(textLine);
      }
      super.clear();
    }
  }
  /**
   * Sets specified message as alert to display for unlimited time
   *
   * @param msg Alert message content
   */
  public void setAlert(String msg) {
    alertReq = true;
    alert = msg;
  }
  /**
   * Checks if alert is requested
   *
   * @return True if alert is requested, false otherwise
   */
  public boolean isAlertReq() {
    return alertReq;
  }
  /** Clears alert message */
  public void clearAlert() {
    alertReq = false;
    alert = null;
  }

  @Override
  public void mouseReleased(int button, int x, int y) {}
  /**
   * Checks if console is hidden
   *
   * @return True if is hidden or false otherwise
   */
  public boolean isHidden() {
    return hide;
  }
  /**
   * Checks if console is focused
   *
   * @return True if console is focused, false otherwise
   */
  public boolean isFocused() {
    return textField.hasFocus();
  }

  @Override
  public void draw(float x, float y) {
    // TODO Auto-generated method stub

  }

  @Override
  public void reset() {}
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.gui.elements.UiElement#close()
   */
  @Override
  public void close() {
    // TODO Auto-generated method stub

  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.gui.tools.UiElement#isOpenReq()
   */
  @Override
  public boolean isOpenReq() {
    return true;
  }
  /**
   * Checks if specified log message is alert message Don't use Log here, causes concurrent
   * exception
   *
   * @param msg String Log message('[message category name]:[message content]')
   * @return True if specified message is alert message, false otherwise
   */
  private boolean isAlertMsg(String msg) {
    try {
      String[] msgParts = msg.split(":");
      String msgCat = msgParts[3];
      if (msgParts.length > 4) return msgCat.equals(warningCatName);
      else return false;
    } catch (ArrayIndexOutOfBoundsException e) {
      return false;
    }
  }
}
