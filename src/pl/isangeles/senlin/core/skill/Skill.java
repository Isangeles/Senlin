package pl.isangeles.senlin.core.skill;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.inter.SlotContent;
import pl.isangeles.senlin.inter.ui.SkillTile;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.core.Character;
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
	protected int magickaCost;
	protected Character user;
	protected Targetable target;
    protected boolean ready;
    protected boolean active;
    protected Character owner;
    private int castTime;
	private String imgName;
	private SkillTile tile;
	
	public Skill(Character character, String id, String name, String info, String imgName, int magickaCost, int castTime) 
	{
		this.id = id;
		this.name = name;
		this.info = info;
		this.imgName = imgName;
		this.magickaCost = magickaCost;
		this.castTime = castTime;
		owner = character;
		active = true;
	}
	
	public void draw(float x, float y, boolean scaledPos)
	{
		tile.draw(x, y, scaledPos);
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
}
