package pl.isangeles.senlin.gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import pl.isangeles.senlin.data.GBase;

/**
 * Interface button class, implements MouseListener to detect mouse click
 *
 * @author Isangeles
 */
public class Button extends InterfaceObject implements MouseListener {
  private String label;
  private TrueTypeFont ttf;
  private GameContainer gc;
  private boolean isClicked;
  private boolean active;
  private Color clickColor;
  /**
   * Create an button based on path to file string ('pre-archive' constructor)
   *
   * @param pathToImg String with path to file
   * @param buttText String with text for button
   * @param gc Game container for click detect
   * @throws SlickException
   * @throws FontFormatException
   * @throws IOException
   */
  @Deprecated
  public Button(String pathToImg, String label, GameContainer gc)
      throws SlickException, FontFormatException, IOException {
    super(pathToImg, gc);
    build(gc, label);
  }
  /**
   * Create an button based on input stream(for zip arch)
   *
   * @param fileInput Input Stream to image
   * @param ref Name for image
   * @param flipped If file suppose to be flipped
   * @param label String with text for button
   * @param gc Game container for click detect
   * @throws SlickException
   * @throws FontFormatException
   * @throws IOException
   */
  public Button(InputStream fileInput, String ref, boolean flipped, String label, GameContainer gc)
      throws SlickException, FontFormatException, IOException {
    super(fileInput, ref, flipped, gc);
    build(gc, label);
  }
  /**
   * Create an button with info window based on input stream(for zip arch)
   *
   * @param fileInput Input Stream to image
   * @param ref Name for image
   * @param flipped If file suppose to be flipped
   * @param label String with text for button
   * @param gc Game container for click detect
   * @param textForWindow Text for info window
   * @throws SlickException
   * @throws FontFormatException
   * @throws IOException
   */
  public Button(
      InputStream fileInput,
      String ref,
      boolean flipped,
      String label,
      GameContainer gc,
      String textForInfo)
      throws SlickException, FontFormatException, IOException {
    super(fileInput, ref, flipped, gc, textForInfo);
    build(gc, label);
  }
  /**
   * Create an button based on existing image
   *
   * @param texture Slick image
   * @param label String with text for button
   * @param gc Game container for click detect
   * @throws SlickException
   * @throws FontFormatException
   * @throws IOException
   */
  public Button(Image texture, String label, GameContainer gc)
      throws SlickException, FontFormatException, IOException {
    super(texture, gc);
    build(gc, label);
  }
  /**
   * Create an button with info window based on existing image
   *
   * @param texture Slick image
   * @param label String with text for button
   * @param gc Game container for click detect
   * @param textForInfo Text for info window
   * @throws SlickException
   * @throws FontFormatException
   * @throws IOException
   */
  public Button(Image texture, String label, GameContainer gc, String textForInfo)
      throws SlickException, FontFormatException, IOException {
    super(texture, gc, textForInfo);
    build(gc, label);
  }
  /** Draws button (position is scaled) */
  @Override
  public void draw(float x, float y) {
    if (!isClicked && active) super.draw(x, y);
    else super.draw(x, y, clickColor);

    // moveMOA(super.x, super.y);

    super.drawString(label, ttf, true);
  }
  /** Draws button */
  @Override
  public void draw(float x, float y, boolean scaledPos) {
    if (!isClicked && active) super.draw(x, y, scaledPos);
    else super.draw(x, y, clickColor, scaledPos);

    // moveMOA(super.x, super.y);

    super.drawString(label, ttf, scaledPos);
  }

  @Override
  public void draw(float x, float y, Color color, boolean scaledPos) {

    if (!isClicked && active) super.draw(x, y, color, scaledPos);
    else super.draw(x, y, clickColor, scaledPos);

    // moveMOA(super.x, super.y);

    super.drawString(label, ttf, scaledPos);
  }

  public boolean clicked() {
    return isClicked;
  }

  public boolean isMouseOver() {
    return super.isMouseOver();
  }

  public boolean isActive() {
    return active;
  }
  /**
   * Sets button active or inactive
   *
   * @param bool True to set button as active, false to set as inactive
   */
  public void setActive(boolean bool) {
    active = bool;
  }
  /**
   * Sets text as button label
   *
   * @param text String with text
   */
  public void setLabel(String text) {
    label = text;
  }

  @Override
  public void inputEnded() {}

  @Override
  public void inputStarted() {}

  @Override
  public boolean isAcceptingInput() {
    return true;
  }

  @Override
  public void setInput(Input input) {}

  @Override
  public void mouseClicked(int button, int x, int y, int clickCount) {}

  @Override
  public void mouseDragged(int oldx, int oldy, int newx, int newy) {}

  @Override
  public void mouseMoved(int oldx, int oldy, int newx, int newy) {}

  @Override
  public void mousePressed(int button, int x, int y) {
    if (isMouseOver() && active) isClicked = true;
  }

  @Override
  public void mouseReleased(int button, int x, int y) {
    if (active) isClicked = false;
  }

  @Override
  public void mouseWheelMoved(int change) {}
  /**
   * Builds button called by every constructor
   *
   * @param gc Slick game container
   * @param text Text for button
   * @throws FontFormatException
   * @throws IOException
   */
  private void build(GameContainer gc, String text) throws FontFormatException, IOException {
    this.label = text;
    this.gc = gc;
    this.gc.getInput().addMouseListener(this);

    active = true;

    isClicked = false;
    clickColor = new Color(73, 73, 73);

    Font font = GBase.getFont("mainUiFont");

    ttf = new TrueTypeFont(font.deriveFont(12f), true);
  }
}
