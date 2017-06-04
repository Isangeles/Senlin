package pl.isangeles.senlin.inter;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.util.GConnector;
/**
 * Class for scrollable text box
 * @author Isangeles
 *
 */
public class TextBox extends InterfaceObject implements MouseListener
{
    private List<TextBlock> texts = new ArrayList<>();
    private Button up;
    private Button down;
    private int firstIndex;
    private int lastIndex;
    
    public TextBox(GameContainer gc) throws SlickException, IOException, FontFormatException
    {
        super(GConnector.getInput("field/textBoxBG.png"), "textBox", false, gc);
        
        gc.getInput().addMouseListener(this);
        
        up = new Button(GConnector.getInput("button/buttonUp.png"), "textBoxUp", false, "", gc);
        down = new Button(GConnector.getInput("button/buttonDown.png"), "textBoxDown", false, "", gc);
    }
    
    @Override
    public void draw(float x, float y, float width, float height, boolean scaledPos)
    {
       super.draw(x, y, width, height, scaledPos);
       up.draw(getTR().x - up.getScaledWidth(), getTR().y);
       down.draw(getBR().x - down.getScaledWidth(), getBR().y - down.getScaledHeight());
       
       for(int i = firstIndex; i >= lastIndex; i --)
       {
           TextBlock text = texts.get(i);
           if(i == texts.size()-1)
           {
               text.draw(super.x, (super.y + super.getScaledHeight()) - text.getTextHeight());
           }
           else if(i < texts.size()-1)
           {
               TextBlock prevText = texts.get(i+1);
               text.draw(super.x, (prevText.getPosition().y - getDis(10)) - text.getTextHeight());
           }
       }
    }
    
    public void add(TextBlock text)
    {
        this.texts.add(text);
        firstIndex = texts.size()-1;
        setVisibleTexts();
    }
    
    public void addAll(List<TextBlock> texts)
    {
        this.texts.addAll(texts);
        firstIndex = texts.size()-1;
        setVisibleTexts();
    }
    
    public void clear()
    {
        texts.clear();
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
        if(button == Input.MOUSE_LEFT_BUTTON)
        {
            if(up.isMouseOver())
            {
                if(firstIndex > 0 && lastIndex > 0)
                {
                    firstIndex --;
                    setVisibleTexts();
                }
            }
            if(down.isMouseOver())
            {
                if(firstIndex < texts.size() && lastIndex < texts.size())
                {
                    firstIndex ++;
                    setVisibleTexts();
                }
            }
        }
    }

    @Override
    public void mouseWheelMoved(int change)
    {
    }
    
    private void setVisibleTexts()
    {
        lastIndex = 0;
        float textsHeight = 0;
        for(int i = firstIndex; i >= 0; i --)
        {
            TextBlock text = texts.get(i);
            textsHeight += text.getTextHeight();
            if(textsHeight > height)
            {
                textsHeight -= text.getTextHeight();
                break;
            }
            else
                lastIndex ++;
        }
    }
}
