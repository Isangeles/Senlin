/*
 * ReadWindow.java
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
package pl.isangeles.senlin.gui.tools;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.data.GBase;
import pl.isangeles.senlin.gui.Button;
import pl.isangeles.senlin.gui.InterfaceObject;
import pl.isangeles.senlin.gui.TextBlock;
import pl.isangeles.senlin.gui.TextBox;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for UI reading window
 *
 * @author Isangeles
 */
class ReadWindow extends InterfaceObject implements UiElement, MouseListener {
  private Character player;

  private TextBlock title;
  private TextBox text;
  private Button close;

  private TrueTypeFont ttf;

  private boolean focus;
  private boolean openReq;
  /**
   * Reading window constructor
   *
   * @param gc Slick game container
   * @throws SlickException
   * @throws IOException
   * @throws FontFormatException
   */
  public ReadWindow(GameContainer gc, Character player)
      throws SlickException, IOException, FontFormatException {
    super(GConnector.getInput("ui/background/readBG.png"), "uiReadBg", false, gc);

    this.player = player;

    gc.getInput().addMouseListener(this);

    Font font = GBase.getFont("mainUiFont");
    ttf = new TrueTypeFont(font.deriveFont(getSize(13f)), true);

    title = new TextBlock(60, ttf);
    text = new TextBox(gc);
    close = new Button(GBase.getImage("buttonS"), TConnector.getText("ui", "uiClose"), gc);
  }

  @Override
  public void draw(float x, float y) {
    super.draw(x, y, false);
    title.draw(x + getDis(15), y + getDis(30));
    text.draw(x + getDis(15), y + getDis(65), getSize(370), getSize(370), false);
    close.draw(x + getDis(150), y + getDis(470), false);
  }
  /**
   * Opens window
   *
   * @param textId ID of text for window
   */
  public void open(String textId) {
    openReq = true;
    focus = true;
    String[] text = TConnector.getInfoFromModule("books", textId);
    title.addText(text[0]);
    this.text.setFocus(true);
    this.text.add(new TextBlock(text[1], 50, ttf));
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.gui.tools.UiElement#close()
   */
  @Override
  public void close() {
    openReq = false;
    focus = false;
    this.text.setFocus(false);
    player.getSignals().stopReading();
    reset();
  }

  /* (non-Javadoc)
   * @see pl.isangeles.senlin.gui.tools.UiElement#update()
   */
  @Override
  public void update() {}

  /* (non-Javadoc)
   * @see pl.isangeles.senlin.gui.tools.UiElement#reset()
   */
  @Override
  public void reset() {
    moveMOA(Coords.getX("BR", 0), Coords.getY("BR", 0));
    title.clear();
    text.clear();
  }

  public boolean isOpenReq() {
    return openReq;
  }
  /* (non-Javadoc)
   * @see org.newdawn.slick.ControlledInputReciever#inputEnded()
   */
  @Override
  public void inputEnded() {}
  /* (non-Javadoc)
   * @see org.newdawn.slick.ControlledInputReciever#inputStarted()
   */
  @Override
  public void inputStarted() {}
  /* (non-Javadoc)
   * @see org.newdawn.slick.ControlledInputReciever#isAcceptingInput()
   */
  @Override
  public boolean isAcceptingInput() {
    return focus;
  }
  /* (non-Javadoc)
   * @see org.newdawn.slick.ControlledInputReciever#setInput(org.newdawn.slick.Input)
   */
  @Override
  public void setInput(Input input) {}
  /* (non-Javadoc)
   * @see org.newdawn.slick.MouseListener#mouseClicked(int, int, int, int)
   */
  @Override
  public void mouseClicked(int button, int x, int y, int clickCount) {}
  /* (non-Javadoc)
   * @see org.newdawn.slick.MouseListener#mouseDragged(int, int, int, int)
   */
  @Override
  public void mouseDragged(int oldx, int oldy, int newx, int newy) {}
  /* (non-Javadoc)
   * @see org.newdawn.slick.MouseListener#mouseMoved(int, int, int, int)
   */
  @Override
  public void mouseMoved(int oldx, int oldy, int newx, int newy) {}
  /* (non-Javadoc)
   * @see org.newdawn.slick.MouseListener#mousePressed(int, int, int)
   */
  @Override
  public void mousePressed(int button, int x, int y) {}
  /* (non-Javadoc)
   * @see org.newdawn.slick.MouseListener#mouseReleased(int, int, int)
   */
  @Override
  public void mouseReleased(int button, int x, int y) {
    if (close.isMouseOver()) close();
  }
  /* (non-Javadoc)
   * @see org.newdawn.slick.MouseListener#mouseWheelMoved(int)
   */
  @Override
  public void mouseWheelMoved(int change) {}
}
