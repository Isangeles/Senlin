/*
 * CraftingMenu.java
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
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.craft.Profession;
import pl.isangeles.senlin.core.craft.Recipe;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.gui.Button;
import pl.isangeles.senlin.gui.InterfaceObject;
import pl.isangeles.senlin.gui.ScrollableList;
import pl.isangeles.senlin.gui.TextBlock;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for crafting menu
 *
 * @author Isangeles
 */
class CraftingMenu extends InterfaceObject implements UiElement, MouseListener {
  private Character player;
  private ScrollableList professionsList;
  private ScrollableList recipesList;
  private TrueTypeFont ttf;
  private TextBlock recipeInfo;
  private Profession selectedPro;
  private Recipe selectedRec;
  private Button closeB;
  private Button createB;
  private boolean openReq;
  /**
   * Crafting menu constructor
   *
   * @param gc Slick game container
   * @param player Player character
   * @throws SlickException
   * @throws IOException
   * @throws FontFormatException
   */
  public CraftingMenu(GameContainer gc, Character player)
      throws SlickException, IOException, FontFormatException {
    super(GConnector.getInput("ui/background/craftingMenuBG.png"), "uiCraftingMenu", false, gc);
    gc.getInput().addMouseListener(this);
    this.player = player;

    recipesList = new ScrollableList(6, gc);
    professionsList = new ScrollableList(10, gc);
    File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
    Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
    ttf = new TrueTypeFont(font.deriveFont(getSize(12f)), true);
    recipeInfo = new TextBlock("", 60, ttf);

    closeB =
        new Button(
            GConnector.getInput("button/buttonS.png"),
            "uiButtonClose",
            false,
            TConnector.getText("ui", "winClose"),
            gc);
    createB =
        new Button(
            GConnector.getInput("button/buttonS.png"),
            "uiButtonClose",
            false,
            TConnector.getText("ui", "cMenuCreate"),
            gc);
  }

  public void draw(float x, float y) {
    super.draw(x, y, false);
    professionsList.draw(x + getDis(20), y + getDis(30), false);
    recipesList.draw(x + getDis(280), y + getDis(30), false);
    recipeInfo.draw(x + getDis(280), y + getDis(340));
    closeB.draw(x + getDis(600), y + getDis(20), false);
    createB.draw(x + getDis(590), y + getDis(450), false);
  }

  public void open() {
    openReq = true;
    professionsList.getContent().addAll(player.getProfessions());
    professionsList.setFocus(true);
    recipesList.setFocus(true);
    professionsList.update();
  }

  public void close() {
    openReq = false;
    reset();
  }

  /* (non-Javadoc)
   * @see org.newdawn.slick.MouseListener#mouseWheelMoved(int)
   */
  @Override
  public void mouseWheelMoved(int change) {
    // TODO Auto-generated method stub

  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.gui.elements.UiElement#update()
   */
  @Override
  public void update() {
    Profession pro = (Profession) professionsList.getSelected();
    if (pro != null && selectedPro != pro) {
      selectedPro = pro;
      recipesList.getContent().addAll(selectedPro);
      recipesList.update();
    }

    Recipe rec = (Recipe) recipesList.getSelected();
    if (rec != null && selectedRec != rec) {
      selectedRec = rec;
      recipeInfo.clear();
      recipeInfo.addText(selectedRec.getInfo());
    }
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.gui.elements.UiElement#reset()
   */
  @Override
  public void reset() {
    this.moveMOA(Coords.getX("BR", 0), Coords.getY("BR", 0));
    selectedPro = null;
    selectedRec = null;
    professionsList.setFocus(false);
    professionsList.clear();

    recipesList.setFocus(false);
    recipesList.clear();

    recipeInfo.clear();
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
    return openReq;
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
    if (button == Input.MOUSE_LEFT_BUTTON) {
      if (createB.isMouseOver()) {
        if (selectedRec != null) {
          List<Item> createdItems = selectedRec.create(player);
          if (createdItems != null) {
            Log.addInformation(TConnector.getText("ui", "cMenuCreateInfo"));
            player.getInventory().addAll(createdItems);
          } else Log.addInformation(TConnector.getText("ui", "cMenuCreateFail"));
        }
      }
      if (closeB.isMouseOver()) close();
    }
  }
}
