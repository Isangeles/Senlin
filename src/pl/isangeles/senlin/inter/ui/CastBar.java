package pl.isangeles.senlin.inter.ui;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.inter.Bar;
import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.core.Character;

public class CastBar extends InterfaceObject implements UiElement
{
    private Character player;
    private Bar castBar;
    private Skill castedSkill;
    
    public CastBar(GameContainer gc, Character player) throws SlickException, IOException, FontFormatException
    {
        super(GConnector.getInput("ui/background/castBG.png"), "uiCastBg", false, gc);
        castBar = new Bar(GConnector.getInput("ui/bar/castBar.png"), "uiCastBar", false, gc, "Progress: ");
        
        this.player = player;
    }
    
    @Override
    public void draw(float x, float y)
    {
        if(player.getAvatar().isCasting())
        {
            super.draw(x, y, false);
            castBar.draw(x+getDis(10), y+getDis(10));
        }
    }

    @Override
    public void update()
    {
        castBar.update((int)player.getAvatar().getCastProgress(), 100);
    }

    @Override
    public void reset()
    {
    }
    
    
}
