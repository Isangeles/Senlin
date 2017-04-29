package pl.isangeles.senlin.core.skill;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.inter.ui.SkillTile;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.core.Character;

/**
 * Class for character skills like attacks, spells, etc.
 * @author Isangeles
 *
 */
public abstract class Skill 
{
	protected String id;
	protected String name;
	protected String info;
	protected int magickaCost;
	private String imgName;
	private SkillTile tile;
	private boolean active;
	
	public Skill(String id, String name, String info, String imgName, int magickaCost) 
	{
		this.id = id;
		this.name = name;
		this.info = info;
		this.imgName = imgName;
		this.magickaCost = magickaCost;
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
	 * Activates skill 
	 * @param user Character thats use skill
	 * @param target Character targeted by skill user
	 * @return True if skill was successfully activate, false otherwise 
	 */
	public abstract boolean activate(Character user, Character target);
	
	protected abstract String getInfo();
	
	protected void setTile(GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		this.tile = new SkillTile(GConnector.getInput("icon/skill/"+imgName), "skillTile", false, gc, getInfo());
	}
}
