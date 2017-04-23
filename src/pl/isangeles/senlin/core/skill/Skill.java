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
	private String imgName;
	private SkillTile tile;
	
	public Skill(String id, String name, String info, String imgName) 
	{
		this.id = id;
		this.name = name;
		this.info = info;
		this.imgName = imgName;
	}
	
	public void draw(float x, float y, boolean scaledPos)
	{
		tile.draw(x, y, scaledPos);
	}
	
	public SkillTile getTile()
	{
		return tile;
	}
	
	public abstract void activate(Character user, Character target);
	
	protected abstract String getInfo();
	
	protected void setTile(GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		this.tile = new SkillTile(GConnector.getInput("icon/skill/"+imgName), "skillTile", false, gc, getInfo());
	}
}
