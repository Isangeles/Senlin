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

public class Message extends InterfaceObject implements MouseListener
{
    private String textMessage;
    private Button buttonOk;
    private Font textFont;
    private TrueTypeFont textTtf;
    private boolean isOpen;
    
    
    public Message(GameContainer gc, String textMessage) throws SlickException, IOException, FontFormatException
    {
        super(GConnector.getInput("field/messageBG.png"), "messageBg", false, gc);
        gc.getInput().addMouseListener(this);
        
        this.textMessage = textMessage;
        File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
        textFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        textTtf = new TrueTypeFont(textFont.deriveFont(12f), true);
        
        buttonOk = new Button(GConnector.getInput("button/buttonOK.png"), "buttOk", false, "", gc);
    }
    
    public boolean isOpen()
    {
        return isOpen;
    }
    
    public void close()
    {
        isOpen = false;
    }
    
    public void draw(float x, float y)
    {
        super.draw(x, y);
        textTtf.drawString(super.x+20, super.y+10, textMessage);
        buttonOk.draw(super.x+130, super.y+super.getHeight()-50);
    }
    
    public void show()
    {
        if(isOpen)
            draw(Coords.get("CE", -200, -100)[0], Coords.get("CE", -100, -80)[1]);
    }
    
    public void set(String textMessage)
    {
        this.textMessage = textMessage;
        isOpen = true;
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
