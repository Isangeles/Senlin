package pl.isangeles.senlin.core.skill;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.inter.SlotContent;
import pl.isangeles.senlin.inter.ui.SkillTile;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Effect;
import pl.isangeles.senlin.core.EffectType;
import pl.isangeles.senlin.core.Targetable;

/**
 * Class for character skills like attacks, spells, etc.
 * @author Isangeles
 *
 */
public abstract class Skill implements SlotContent
{
	protected String id;
	protected String name;
	protected String info;
	protected EffectType type;
	protected int magickaCost;
    protected Character owner;
	protected Targetable target;
    protected boolean ready;
    protected boolean active;
    protected boolean useWeapon;
    protected int cooldown;
    protected List<Effect> effects;
    private int castTime;
    private int timer;
 	private String imgName;
	private SkillTile tile;
	
	public Skill(Character character, String id, String name, String info, String imgName, EffectType type, int magickaCost, int castTime, int cooldown, boolean useWeapon, List<Effect> effects) 
	{
		this.type = type;
		this.id = id;
		this.name = name;
		this.info = info;
		this.imgName = imgName;
		this.magickaCost = magickaCost;
		this.castTime = castTime;
		this.cooldown = cooldown;
		this.useWeapon = useWeapon;
		this.effects = effects;
		owner = character;
		active = true;
	}
	
	public void draw(float x, float y, boolean scaledPos)
	{
		tile.draw(x, y, scaledPos);
	}
	
	public void update(int delta)
	{
		tile.setActive(active);
		if(!active)
			timer += delta;
		if(timer >= cooldown)
		{
			active = true;
			timer = 0;
		}
	}
	
	public void setActive(boolean active)
	{
		this.active = active;
		tile.setActive(active);
	}

    public float getCastSpeed()
    {
        return (owner.getHaste()-castTime);
    }
    
	public String getId()
	{
		return id;
	}
	
	public String getName()
	{
	    return name;
	}
	
	public SkillTile getTile()
	{
		return tile;
	}
	
	public boolean isMagic()
	{
		if(magickaCost > 0)
			return true;
		else
			return false;
	}
	
	public boolean isActive()
	{
		return active;
	}
	/**
	 * Returns skill owner
	 * @return Character thats own this skill 
	 */
	public Character getOwner()
	{
		return owner;
	}
    /**
     * Activates skill prepared skill
     */
	public abstract void activate();
	/**
	 * Prepares skill
     * @param user Character thats use skill
     * @param target Character targeted by skill user
     * @return True if skill was successfully activate, false otherwise 
	 */
	public abstract boolean prepare(Character user, Targetable target);
	
	protected abstract String getInfo();
	
	protected void setTile(GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		this.tile = new SkillTile(GConnector.getInput("icon/skill/"+imgName), "skillTile", false, gc, getInfo());
	}
	
	protected String getTypeString()
	{
		switch(type)
		{
		case FIRE:
			return TConnector.getText("ui", "eleTFire");
		case ICE:
			return TConnector.getText("ui", "eleTIce");
		case NATURE:
			return TConnector.getText("ui", "eleTNat");
		case MAGIC:
			return TConnector.getText("ui", "eleTMag");
		case NORMAL:
			return TConnector.getText("ui", "eleTNorm");
		default:
			return TConnector.getText("ui", "errorName");
		}
	}
}
