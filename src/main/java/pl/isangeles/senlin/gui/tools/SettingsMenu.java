/*
 * SettingsMenu.java
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

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import pl.isangeles.senlin.core.Attribute;
import pl.isangeles.senlin.gui.Button;
import pl.isangeles.senlin.gui.InterfaceObject;
import pl.isangeles.senlin.gui.Message;
import pl.isangeles.senlin.gui.ObjectSwitch;
import pl.isangeles.senlin.gui.Switch;
import pl.isangeles.senlin.gui.Switchable;
import pl.isangeles.senlin.states.GameWorld;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.Settings;
import pl.isangeles.senlin.util.Size;
import pl.isangeles.senlin.util.TConnector;

/**
 * Class for in-game settings window
 *
 * @author Isangeles
 */
class SettingsMenu extends InterfaceObject implements UiElement, MouseListener {
  private GameWorld gw;
  private ObjectSwitch resolutionS;
  private ObjectSwitch langS;
  private ObjectSwitch fowS;
  private ObjectSwitch mRenderS;
  private Switch effectsVolS;
  private Switch musicVolS;
  private Button backB;
  private Message restartInfo;

  private boolean openReq;
  private boolean change;
  /**
   * In-game settings menu constructor
   *
   * @param gc Slick game container
   * @throws SlickException
   * @throws IOException
   * @throws FontFormatException
   */
  public SettingsMenu(GameContainer gc, GameWorld gw)
      throws SlickException, IOException, FontFormatException {
    super(GConnector.getInput("ui/background/saveBG.png"), "uiSettingsBg", false, gc);

    gc.getInput().addMouseListener(this);

    this.gw = gw;

    List<Switchable> resValues = new ArrayList<>();
    for (String val : Settings.getResList()) {
      Setting res = new Setting(val, val);
      resValues.add(res);
    }
    List<Switchable> langValues = new ArrayList<>();
    for (String val : Settings.getLangList()) {
      Setting lang = new Setting(val, TConnector.getText("ui", "sValue_" + val));
      langValues.add(lang);
    }
    List<Switchable> fowValues = new ArrayList<>();
    for (String val : Settings.getFowTypes()) {
      Setting fow = new Setting(val, TConnector.getText("ui", "sValue_" + val));
      fowValues.add(fow);
    }
    List<Switchable> mRenderValues = new ArrayList<>();
    for (String val : Settings.getMapRenderTypes()) {
      Setting mRender = new Setting(val, TConnector.getText("ui", "sValue_" + val));
      mRenderValues.add(mRender);
    }
    resolutionS = new ObjectSwitch(gc, TConnector.getText("ui", "settRes"), resValues);
    langS = new ObjectSwitch(gc, TConnector.getText("ui", "settLang"), langValues);
    fowS = new ObjectSwitch(gc, TConnector.getText("ui", "settFow"), fowValues);
    mRenderS = new ObjectSwitch(gc, TConnector.getText("ui", "settMRen"), mRenderValues);
    backB =
        new Button(
            GConnector.getInput("button/buttonS.png"),
            "uiSettingsClose",
            false,
            TConnector.getText("ui", "winClose"),
            gc);
    effectsVolS =
        new Switch(
            gc,
            TConnector.getText("ui", "settEVol"),
            (int) (Settings.getEffectsVol() * 100),
            new Attribute(100));
    musicVolS =
        new Switch(
            gc,
            TConnector.getText("ui", "settMVol"),
            (int) (Settings.getMusicVol() * 100),
            new Attribute(100));
    restartInfo = new Message(gc);
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.inter.ui.UiElement#draw(float, float)
   */
  @Override
  public void draw(float x, float y) {
    super.draw(x, y, false);

    resolutionS.draw(x + getDis(40), y + getDis(20), false);
    langS.draw(x + getDis(40), y + getDis(100), false);
    fowS.draw(x + getDis(40), y + getDis(180), false);
    mRenderS.draw(x + getDis(40), y + getDis(260), false);
    effectsVolS.draw(x + getDis(40), y + getDis(340), false);
    musicVolS.draw(x + getDis(40), y + getDis(420), false);
    backB.draw(x + getDis(20), (y + super.getScaledHeight()) - backB.getScaledHeight(), false);
    if (restartInfo.isOpenReq()) restartInfo.draw();
  }
  /** Opens menu */
  public void open() {
    openReq = true;
    setCurrentValues();
  }
  /** Closes menu */
  public void close() {
    if (change) {
      restartInfo.open(TConnector.getText("ui", "settWinInfo"));
      applySettings();
      Settings.saveSettings();
    }
    openReq = false;
    reset();
  }
  /**
   * Checks if menu is open
   *
   * @return True if menu was opened, false otherwise
   */
  public boolean isOpenReq() {
    return openReq;
  }
  /* (non-Javadoc)
   * @see pl.isangeles.senlin.inter.ui.UiElement#update()
   */
  @Override
  public void update() {
    change = resolutionS.isChange() || langS.isChange() || fowS.isChange();
  }

  /* (non-Javadoc)
   * @see pl.isangeles.senlin.inter.ui.UiElement#reset()
   */
  @Override
  public void reset() {
    hideMOA();
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
    return true;
  }

  /* (non-Javadoc)
   * @see org.newdawn.slick.ControlledInputReciever#setInput(org.newdawn.slick.Input)
   */
  @Override
  public void setInput(Input arg0) {}

  /* (non-Javadoc)
   * @see org.newdawn.slick.MouseListener#mouseClicked(int, int, int, int)
   */
  @Override
  public void mouseClicked(int button, int x, int y, int clickCount) {
    // TODO Auto-generated method stub

  }

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
      if (backB.isMouseOver()) {
        close();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.newdawn.slick.MouseListener#mouseWheelMoved(int)
   */
  @Override
  public void mouseWheelMoved(int change) {}
  /** Applies settings */
  private void applySettings() {
    Settings.setLang(langS.getValue());
    Settings.setResolution(new Size(resolutionS.getValue().replace('x', ';')));
    Settings.setFowType(fowS.getValue());
    Settings.setMapRenderType(mRenderS.getValue());
    Settings.setEffectsVol((float) effectsVolS.getValue() / 100);
    Settings.setMusicVol((float) musicVolS.getValue() / 100);
    gw.replayMusic(); // To apply music volume changes
  }
  /**
   * Sets current values of game settings to settings switches
   *
   * @throws ArrayIndexOutOfBoundsException
   */
  private void setCurrentValues() throws ArrayIndexOutOfBoundsException {
    String currentRes = Settings.getResolution()[0] + "x" + Settings.getResolution()[1];
    resolutionS.setValue(currentRes);
    langS.setValue(Settings.getLang());
    fowS.setValue(Settings.getFowType());
    mRenderS.setValue(Settings.getMapRenderType());
  }
  /**
   * Class for settings switches values
   *
   * @author Isangeles
   */
  class Setting implements Switchable {
    private String id;
    private String name;

    public Setting(String id, String name) {
      this.id = id;
      this.name = name;
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.gui.Switchable#getName()
     */
    @Override
    public String getName() {
      return name;
    }
    /* (non-Javadoc)
     * @see pl.isangeles.senlin.gui.Switchable#getId()
     */
    @Override
    public String getId() {
      return id;
    }
  }
}
