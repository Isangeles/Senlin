package pl.isangeles.senlin.inter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.util.GConnector;
/**
 * Class for scrollable text box
 * @author Isangeles
 *
 */
public class TextBox extends InterfaceObject
{
    private List<TextBlock> texts = new ArrayList<>();
    
    public TextBox(GameContainer gc) throws SlickException, IOException
    {
        super(GConnector.getInput("field/textBoxBG.png"), "textBox", false, gc);
    }
    
    @Override
    public void draw(float x, float y, float width, float height, boolean scaledPos)
    {
        super.draw(x, y, width, height, scaledPos);
        
        int textX = (int) x;
        int textY = 0;
        int textPrevY = 0;
        for(int i = texts.size()-1, j = 1; i >= 0; i --, j ++)
        {
            textY = (int)(( y + (super.getHeight() - texts.get(i).getTextHeight())) - textPrevY);
            texts.get(i).draw(textX, textY);
            textPrevY = (int)(textY - y);
        }
    }
    
    public void add(TextBlock text)
    {
        this.texts.add(text);
    }
    
    public void addAll(List<TextBlock> texts)
    {
        this.texts.addAll(texts);
    }
    
    public void clear()
    {
        texts.clear();
    }
}
