package pl.isangeles.senlin.data.pattern;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Effect;
import pl.isangeles.senlin.core.EffectType;
import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.util.TConnector;
/**
 * Pattern for attack skills
 * @author Isangeles
 *
 */
public class AttackPattern
{
    private String id;
    private String name;
    private String info;
    private EffectType type;
    private int magickaCost;
    private boolean useWeapon;
    private int cooldown;
    private int castTime;
    private String imgName;
    private int damage;
    private int range;
    private List<Effect> effects;
    
    public AttackPattern(String id, String imgName, String type, int damage, int magickaCost, int castTime, int cooldown, boolean useWeapon, int range, List<Effect> effects)
    {
        this.type = EffectType.fromString(type);
        this.id = id;
        this.name = TConnector.getInfo("skills", id)[0];
        this.info = TConnector.getInfo("skills", id)[1];
        this.imgName = imgName;
        this.magickaCost = magickaCost;
        this.castTime = castTime*1000;
        this.cooldown = cooldown*1000;
        this.useWeapon = useWeapon;
        this.damage = damage;
        this.range = range;
        this.effects = effects;
    }
    
    public Attack make(Character character, GameContainer gc) throws SlickException, IOException, FontFormatException
    {
        return new Attack(character, id, name, info, imgName, type, damage, magickaCost, castTime, cooldown, useWeapon, range, effects, gc);
    }
}
