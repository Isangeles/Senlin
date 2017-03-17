package pl.isangeles.senlin.inter.ui;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;

import pl.isangeles.senlin.inter.TextInput;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.core.Character;
/**
 * Class for game console
 * @author Isangeles
 *
 */
public final class Console extends TextInput
{
    boolean hide;
    Character player;
    List<String> messages = new LinkedList<>();
    
    public Console(GameContainer gc, Character player) throws SlickException, FontFormatException, IOException
    {
        super(GConnector.getInput("ui/background/consoleBG.png"), "uiConsoleBg", false, gc);
        super.textField = new TextField(gc, textTtf, (int)Coords.getX("BR", 0), (int)Coords.getY("BR", 0), super.baseTex.getWidth(), super.baseTex.getHeight()-170, this);
        this.player = player;
        hide = true;
        messages.add("Welcome in game console");
    }
    
    public void draw(float x, float y, Graphics g)
    {
        if(!hide)
        {
            super.draw(Coords.getX("TR", super.getWidth()+10), 10);
            super.textTtf.drawString(super.x, super.y, messages.get(messages.size()-1));
            textField.setLocation((int)super.x, (int)super.y+170);
            super.render(g);
        }
    }
            
    @Override
    public void keyPressed(int key, char c)
    {
        if(key == Input.KEY_GRAVE && !hide)
        {
            super.textField.deactivate();
            hide = true;
            super.textField.setFocus(false);
        }
        else if(key == Input.KEY_GRAVE && hide)
        {
            hide = false;
            super.textField.setFocus(true);
        }
        
        if(key == Input.KEY_ENTER && !hide)
        {
            checkCommand(super.getText());
            super.clear();
        }
    }
    
    @Override
    public void mouseReleased(int button, int x, int y)
    {
    }
    
    private void checkCommand(String command)
    {
        if(command.equals("unlock"))
            messages.add("console unlocked!");
    }
    
    public boolean isHidden()
    {
        return hide;
    }
    
}
