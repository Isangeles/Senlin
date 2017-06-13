package pl.isangeles.senlin.data.pattern;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Effect;
import pl.isangeles.senlin.core.EffectType;
import pl.isangeles.senlin.core.item.WeaponType;
import pl.isangeles.senlin.core.skill.Attack;
import pl.isangeles.senlin.util.TConnector;
/**
 * Pattern for attack skills
 * @author Isangeles
 *
 */
public class AttackPattern
{
    private final String id;
    private final String name;
    private final String info;
    private final EffectType type;
    private final int magickaCost;
    private final boolean useWeapon;
    private final WeaponType reqWeapon;
    private final int cooldown;
    private final int castTime;
    private final String imgName;
    private final int damage;
    private final int range;
    private final List<Effect> effects;
    /**
     * 
     * Attack pattern constructor
     * @param id Skill ID
     * @param imgName Skill UI icon
     * @param type Skill type
     * @param damage Attack damage
     * @param magickaCost Skill magicka cost
     * @param castTime Skill cast time
     * @param cooldown Skill cooldown
     * @param useWeapon If weapon is required to use that skill 
     * @param reqWeapon Type of required weapon
     * @param range Maximal range from target
     * @param effects Skill use effects
     */
    public AttackPattern(String id, String imgName, String type, int damage, int magickaCost, int castTime, int cooldown, boolean useWeapon, String reqWeapon, int range, List<Effect> effects)
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
        this.reqWeapon = WeaponType.fromName(reqWeapon);
        this.damage = damage;
        this.range = range;
        this.effects = effects;
    }
    /**
     * Creates attack from this pattern
     * @param character Game character, skill owner
     * @param gc Slick game container
     * @return New attack object assigned to specified character
     * @throws SlickException
     * @throws IOException
     * @throws FontFormatException
     */
    public Attack make(Character character, GameContainer gc) throws SlickException, IOException, FontFormatException
    {
        return new Attack(character, id, name, info, imgName, type, damage, magickaCost, castTime, cooldown, useWeapon, reqWeapon, range, effects, gc);
    }
}
