package pl.isangeles.senlin;

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
        this.addState(new SettingsMenu());
        this.enterState(0);
    }

    public static void main(String[] args)
    {
        try 
        {
            AppGameContainer appGC = new AppGameContainer(new ScalableGame(new SenlinGame("Senlin"), (int)Settings.getResolution()[0], (int)Settings.getResolution()[1]));
            appGC.setDisplayMode((int)Settings.getResolution()[0], (int)Settings.getResolution()[1], true);
            appGC.setTargetFrameRate(60);
            appGC.start();
        }
        catch(SlickException e)
        {
            System.err.println(e.getMessage());
        }
    }
    /**
     * Used before to load natives, now natives are packed into build
     * @return Absolute path to current executable
     */
    private static String getExecutionPath()
    {
        String absolutePath = SenlinGame.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
        absolutePath = absolutePath.replaceAll("%20"," "); // Surely need to do this here
        return absolutePath;
    }

}
