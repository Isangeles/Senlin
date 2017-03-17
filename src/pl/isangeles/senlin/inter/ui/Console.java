package pl.isangeles.senlin.inter.ui;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;

import pl.isangeles.senlin.inter.TextInput;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.data.ItemBase;
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
    /**
     * Console constructor
     * @param gc Game container for superclass
     * @param player Player character to manipulate by commands
     * @throws SlickException
     * @throws FontFormatException
     * @throws IOException
     */
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
    /**
     * Checks if console is hidden
     * @return True if is hidden or false otherwise
     */
    public boolean isHidden()
    {
        return hide;
    }
    /**
     * Checks entered command
     */
    private void checkCommand(String command)
    {
        Scanner scann = new Scanner(command);
        scann.useDelimiter(".");
        
        if(command.equals("unlock"))
            messages.add("console unlocked!");
        
        if(scann.next().equals("player"))
        {
           playerCommands(scann.next());
        }
        scann.close();
    }
    
    private void playerCommands(String command)
    {
        Scanner scann = new Scanner(command);
        scann.useDelimiter(" ");
        if(scann.next().equals("additem"))
        {
            if(player.addItem(ItemBase.getItem(scann.next())))
                messages.add("item added!");
            else
                messages.add("item do not exist");
        }
        scann.close();
    }
    
}
