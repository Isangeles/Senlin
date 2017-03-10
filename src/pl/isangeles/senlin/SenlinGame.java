package pl.isangeles.senlin;

import java.io.FileNotFoundException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import pl.isangeles.senlin.states.*;
import pl.isangeles.senlin.util.Settings;
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
            Settings.set();
            AppGameContainer appGC = new AppGameContainer(new ScalableGame(new SenlinGame("Senlin"), (int)Settings.getResolution()[0], (int)Settings.getResolution()[1]));
            appGC.setDisplayMode(1920, 1080, true);
            appGC.setMouseGrabbed(false);
            appGC.setTargetFrameRate(60);
            appGC.start();
        }
        catch(SlickException e)
        {
            System.err.println(e.getMessage());
        } 
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

}
