package pl.isangeles.senlin.inter.ui;

import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.util.GConnector;

public class SkillSlot extends InterfaceObject 
{
	private Skill skillInSlot;
	
	public SkillSlot(GameContainer gc) throws SlickException, IOException 
	{
		super(GConnector.getInput("ui/slot.png"), "uiSlot", false, gc);
	}
	
	@Override
	public void draw(float x, float y, boolean scaledPos)
	{
		if(skillInSlot != null)
			skillInSlot.draw(x-3, y-3, scaledPos);
		
		super.draw(x, y, false);
	}
	/**
	 * Inserts skill in slot
	 * @param item Item tile
	 */
	public void insertSkill(Skill skill)
	{
		skillInSlot = skill; 
	}
	/**
	 * Removes item from slot
	 */
	public void removeSkill()
	{
		skillInSlot = null;
	}
	
	public void dragged(boolean dragged)
	{
		skillInSlot.getTile().dragged(dragged);
	}
	/**
	 * Checks if tile of skill in slots is dragged
	 * @return True if slot is dragged, false otherwise
	 */
	public boolean isSkillDragged()
	{
		if(skillInSlot != null)
			return skillInSlot.getTile().isDragged();
		else
			return false;
	}
	/**
	 * Checks if slot is empty
	 * @return True if slot is empty, false otherwise
	 */
	public boolean isNull()
	{
		if(skillInSlot == null)
			return true;
		else
			return false;
	}
	
	public Skill getSkill()
	{
		return skillInSlot;
	}

}
