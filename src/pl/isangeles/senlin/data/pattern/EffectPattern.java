package pl.isangeles.senlin.data.pattern;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Attributes;
import pl.isangeles.senlin.core.Effect;
import pl.isangeles.senlin.core.EffectType;
import pl.isangeles.senlin.util.TConnector;
/**
 * Pattern for creating effects
 * @author Isangeles
 *
 */
public class EffectPattern
{
    private String id;
    private String name;
    private String info;
    private String imgName;
    private EffectType type;
    private int hpMod;
    private int manaMod;
    private Attributes attMod;
    private float hasteMod;
    private float dodgeMod;
    private int dmgMod;
    private int dot;
    private int duration;
    
    public EffectPattern(String id, String imgName, int hpMod, int manaMod, Attributes attMod, float hasteMod, float dodgeMod, int dmgMod, int dot, int duration, String type)
    {
        this.id = id;
        this.name = TConnector.getInfo("effects", id)[0];
        this.info = TConnector.getInfo("effects", id)[1];
        this.imgName = imgName;
        this.type = EffectType.fromString(type);
        this.hpMod = hpMod;
        this.manaMod = manaMod;
        this.attMod = attMod;
        this.hasteMod = hasteMod;
        this.dodgeMod = dodgeMod;
        this.dmgMod = dmgMod;
        this.dot = dot;
        this.duration = duration*1000;
    }
    
    public Effect make(GameContainer gc) throws SlickException, IOException, FontFormatException
    {
        return new Effect(id, name, info, imgName, hpMod, manaMod, attMod, hasteMod, dodgeMod, dmgMod, dot, duration, type, gc);
    }
}
