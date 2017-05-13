package pl.isangeles.senlin.data.pattern;

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
    private EffectType type;
    private int hpMod;
    private int manaMod;
    private Attributes attMod;
    private float hasteMod;
    private float dodgeMod;
    private int dmgMod;
    private int duration;
    private int time;
    private boolean on;
    
    public EffectPattern(String id, int hpMod, int manaMod, Attributes attMod, float hasteMod, float dodgeMod, int dmgMod, int duration, String type)
    {
        this.id = id;
        this.name = TConnector.getText("effects", id);
        this.type = EffectType.fromString(type);
        this.hpMod = hpMod;
        this.manaMod = manaMod;
        this.attMod = attMod;
        this.hasteMod = hasteMod;
        this.dodgeMod = dodgeMod;
        this.dmgMod = dmgMod;
        this.duration = duration;
    }
    
    public Effect make()
    {
        return new Effect(id, name, hpMod, manaMod, attMod, hasteMod, dodgeMod, dmgMod, duration, type);
    }
}
