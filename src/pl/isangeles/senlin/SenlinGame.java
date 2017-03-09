package pl.isangeles.senlin;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import pl.isangeles.senlin.states.*;
/**
 * Main game class, contains all game states
 * @author Isangeles
 *
 */
public class SenlinGame extends StateBasedGame
{

    public SenlinGame(String name)
    {
        super(name);
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException
    {
        this.addState(new MainMenu());
        this.addState(new NewGameMenu());
        this.enterState(0);
    }

    public static void main(String[] args)
    {
        try 
        {
            AppGameContainer appGC = new AppGameContainer(new SenlinGame("Senlin"));
            appGC.setDisplayMode(1920, 1080, true);
            appGC.setMouseGrabbed(false);
            appGC.start();
        }
        catch(SlickException e)
        {
            System.err.println(e.getMessage());
        }
    }

}
