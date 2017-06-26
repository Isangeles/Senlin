package pl.isangeles.senlin.inter;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.util.*; 
/**
 * Class for game messages which consist of text, background and dismiss button
 * @author Isangeles
 *
 */
public class Message extends InterfaceObject implements MouseListener
{
    private String textMessage;
    protected Button buttonOk;
    private Font textFont;
    private TrueTypeFont textTtf;
    private boolean isOpen;
    
    /**
     * Message constructor
     * @param gc GameContainer for superclass 
     * @param textMessage Text for message window
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    public Message(GameContainer gc) throws SlickException, IOException, FontFormatException
    {
        super(GConnector.getInput("field/messageBG.png"), "messageBg", false, gc);
        gc.getInput().addMouseListener(this);
       
        File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
        textFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        textTtf = new TrueTypeFont(textFont.deriveFont(12f), true);
        
        buttonOk = new Button(GConnector.getInput("button/buttonOK.png"), "buttOk", false, "", gc);
    }
    /**
     * If message should be shown
     * @return
     */
    public boolean isOpen()
    {
        return isOpen;
    }
    /**
     * Force message to close
     */
    public void close()
    {
        isOpen = false;
    }
    /**
     * Draws message
     */
    @Override
    public void draw(float x, float y, boolean scaledPos)
    {
        super.draw(x, y, scaledPos);
        textTtf.drawString(super.x+getDis(20), super.y+getDis(10), textMessage);
        buttonOk.draw(x+getDis(200), y+super.getScaledHeight()-getDis(50), false);
    }
    @Override
    public void draw()
    {
        draw(Coords.getX("CE", -200), Coords.getY("CE", -80), false);
        textTtf.drawString(super.x+getDis(20), super.y+getDis(10), textMessage);
        buttonOk.draw(x+getDis(200), y+super.getScaledHeight()-getDis(50), false);
    }
    /**
     * Sets text and opens message 
     * @param textMessage Text for message
     */
    public void show(String textMessage)
    {
    	set(textMessage);
        isOpen = true;
    }
    
    private void set(String textMessage)
    {
        this.textMessage = textMessage;
    }

    @Override
    public void inputEnded()
    {
    }

    @Override
    public void inputStarted()
    {
    }

    @Override
    public boolean isAcceptingInput()
    {
        return true;
    }

    @Override
    public void setInput(Input input)
    {
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount)
    {
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy)
    {
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy)
    {
    }

    @Override
    public void mousePressed(int button, int x, int y)
    {
    }

    @Override
    public void mouseReleased(int button, int x, int y)
    {
        if(button == Input.MOUSE_LEFT_BUTTON && buttonOk.isMouseOver())
            close();
    }

    @Override
    public void mouseWheelMoved(int change)
    {
    }

}
