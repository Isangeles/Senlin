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

public final class TextSwitch extends InterfaceObject implements MouseListener
{
    List<String> textToDraw = new ArrayList<>();
    int lineId;
    Font textFont;
    TrueTypeFont textTtf;
    Button plus;
    Button minus;
    
    public TextSwitch(InputStream fileInput, String ref, boolean flipped,
            GameContainer gc, String textToSwitch, String delimiter) throws SlickException, FontFormatException, IOException
    {
        super(fileInput, ref, flipped, gc);

        File fontFile = new File("data" + File.separator + "font" + "SIMSUN.ttf");
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
        super.drawString(textToDraw.get(lineId), textTtf);
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
            lineId ++;
        else if(button == Input.MOUSE_LEFT_BUTTON && minus.isMouseOver() && lineId >= 0)
            lineId --;
    }

    @Override
    public void mouseWheelMoved(int change)
    {
    }

}
