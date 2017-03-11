package pl.isangeles.senlin.inter;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.util.GConnector;

public final class TextSwitch extends InterfaceObject implements MouseListener
{
    private List<String> textToDraw = new ArrayList<>();
    private int lineId;
    private Font textFont;
    private TrueTypeFont textTtf;
    private Button plus;
    private Button minus;
    private boolean isChange;
    
    public TextSwitch(GameContainer gc, String textToSwitch, String delimiter) throws SlickException, FontFormatException, IOException
    {
        super(GConnector.getInput("switch/switchBG.png"), "switchBG", false, gc);
        plus = new Button(GConnector.getInput("switch/switchButtonPlus.png"), "switchTBP", false, "", gc);
        minus = new Button(GConnector.getInput("switch/switchButtonMinus.png"), "switchTBM", false, "", gc);
        gc.getInput().addMouseListener(this);
        
        File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
        Font textFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        textTtf = new TrueTypeFont(textFont.deriveFont(12f), true);
        
        Scanner scann = new Scanner(textToSwitch);
        scann.useDelimiter(delimiter);
        while(scann.hasNext())
        {
            textToDraw.add(scann.next());
        }
        scann.close();
    }
    
    public void draw(float x, float y)
    {
        super.draw(x, y);
        
        plus.draw(super.x+super.getWidth()-35, super.y+2);
		minus.draw(super.x, super.y+2);
		
        super.drawString(textToDraw.get(lineId), textTtf);
    }
    
    public String getString()
    {
    	return textToDraw.get(lineId);
    }
    
    public boolean isChange()
    {
        return isChange;
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
        if(button == Input.MOUSE_LEFT_BUTTON && plus.isMouseOver() && lineId < textToDraw.size()-1)
        {
            isChange = true;
            lineId ++;
        }
        else if(button == Input.MOUSE_LEFT_BUTTON && minus.isMouseOver() && lineId > 0)
        {
            isChange = true;
            lineId --;
        }
    }

    @Override
    public void mouseWheelMoved(int change)
    {
    }

}
