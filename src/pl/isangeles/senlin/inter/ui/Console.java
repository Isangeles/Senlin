package pl.isangeles.senlin.inter.ui;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;

import pl.isangeles.senlin.inter.TextInput;
import pl.isangeles.senlin.util.Coords;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.data.CommBase;
import pl.isangeles.senlin.data.ItemBase;
/**
 * Class for game console
 * command syntax: [target] [command] [-prefix] [value]
 * TODO Seems to command check not work entirely properly
 * @author Isangeles
 *
 */
public final class Console extends TextInput
{
    private boolean hide;
    private Character player;
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
    }
    /**
     * Draws console on unscaled position
     * @param x Position on x-axis
     * @param y Position on y-axis
     * @param g Slick graphic to render text field
     */
    public void draw(float x, float y, Graphics g)
    {

        super.draw(x, y, false);
        
        for(int i = 1; i < 6; i ++)
        {
        	super.textTtf.drawString(super.x, (super.y + super.getScaledHeight() - 7) - textField.getHeight()*i, CommBase.get(CommBase.size()-i));
        }
        
        if(!hide)
        {   
            textField.setLocation((int)super.x, (int)(super.y + super.getScaledHeight()));
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
            if(super.getText() != null)
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
     * Checks entered command target, first command check  
     * @param command Text line with command to check 
     */
    private void checkCommand(String line)
    {
        Scanner scann = new Scanner(line);
        String commandTarget = "";
        String command = "";
        try
        {
            commandTarget = scann.next();
            command = scann.nextLine();
        }
        catch(NoSuchElementException e)
        {
        	CommBase.addDebug("Command scann error: " + line);
        }
        scann.close();
        
        if(commandTarget.equals("unlock"))
        {
        	CommBase.addInformation("console unlocked!");
        	return;
        }
        
        if(commandTarget.equals("debug"))
        {
        	if(command.equals("on"))
        	{
        		CommBase.setDebug(true);
        	}
        	else if(command.equals("off"))
        	{
        		CommBase.setDebug(false);
        	}
        	
        	return;
        }
        
        if(commandTarget.equals("player"))
        {
        	CommBase.addDebug("In player check");
        	playerCommands(command);
        	return;
        }
        
        CommBase.addWarning(commandTarget + " " + TConnector.getText("ui", "logCmdFail"));
       
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
        if(command.equals("remove"))
        {
        	removeCommands(prefix, player);
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
                CommBase.addInformation(TConnector.getText("ui", "logAddI"));
            else
                CommBase.addInformation(TConnector.getText("ui", "logAddIFail"));
    	}
    	try
    	{
    		if(prefix.equals("-h"))
        		player.addHealth(Integer.parseInt(value));
        	if(prefix.equals("-m"))
        		player.addMagicka(Integer.parseInt(value));
        	if(prefix.equals("-e"))
        		player.addExperience(Integer.parseInt(value));
    	}
    	catch(NumberFormatException e)
    	{
    		CommBase.addWarning(TConnector.getText("ui", "logBadVal"));
    	}
    }
    /**
     * Checks remove command for target, last command check
     * @param commLine Rest of command line (after command)
     * @param target Target of command
     */
    private void removeCommands(String commandLine, Character target)
    {
    	Scanner scann = new Scanner(commandLine);
    	String prefix = scann.next();
    	String value = scann.next();
    	scann.close();
    	
    	try
    	{
    		if(prefix.equals("-h"))
        		player.takeHealth(Integer.parseInt(value));
        	if(prefix.equals("-m"))
        		player.takeMagicka(Integer.parseInt(value));
        	if(prefix.equals("-e"))
        		player.takeExperience(Integer.parseInt(value));
    	}
    	catch(NumberFormatException e)
    	{
    		CommBase.addInformation(TConnector.getText("ui", "logBadVal"));
    	}
    }
    
}
