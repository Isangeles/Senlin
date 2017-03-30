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
 * command syntax: [target] [command] [-prefix] [value]
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
        super.textField = new TextField(gc, textTtf, (int)Coords.getX("BR", 0), (int)Coords.getY("BR", 0), super.getWidth(), super.getHeight()-170, this);
        this.player = player;
        hide = true;
        messages.add("Welcome in game console");
    }
    
    public void draw(float x, float y, Graphics g)
    {
        if(!hide)
        {
            super.draw(Coords.getX("TR", super.getWidth()+10), 10, false);
            super.textTtf.drawString(super.x, super.y, messages.get(messages.size()-1));
            textField.setLocation((int)super.x, (int)super.y+getDis(170));
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
            checkCommand();
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
     * Checks entered command target, first command check   
     */
    private void checkCommand()
    {
        Scanner scann = new Scanner(super.getText());
        String commandTarget = scann.next();
        String command = scann.nextLine();
        scann.close();
        
        if(commandTarget.equals("unlock"))
            messages.add("console unlocked!");
        
        if(commandTarget.equals("player"))
           playerCommands(command);
       
    }
    /**
     * Checks entered command for player, second command check
     * @param commandLine Rest of command line (after target) 
     */
    private void playerCommands(String commandLine)
    {
        Scanner scann = new Scanner(commandLine);
        String command = scann.next();
        String prefix = scann.nextLine();
        scann.close();
        
        if(command.equals("add"))
        {
            addCommands(prefix, player);
        }
    }
    /**
     * Checks add command for target, last command check
     * @param commandLine Rest of command line (after command)
     * @param target Target of command
     */
    private void addCommands(String commandLine, Character target)
    {
    	Scanner scann = new Scanner(commandLine);
    	String prefix = scann.next();
    	String value = scann.next();
    	scann.close();
    	
    	if(prefix.equals("-i"))
    	{
    		if(target.addItem(ItemBase.getItem(value)))
                messages.add("item added!");
            else
                messages.add("item not found");
    	}
    }
    
}
