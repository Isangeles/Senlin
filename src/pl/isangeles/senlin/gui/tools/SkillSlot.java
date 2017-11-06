package pl.isangeles.senlin.gui.tools;

import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.out.CharacterOut;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.data.GBase;
import pl.isangeles.senlin.gui.GameCursor;
import pl.isangeles.senlin.gui.InterfaceObject;
import pl.isangeles.senlin.gui.Slot;
import pl.isangeles.senlin.gui.SlotContent;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.GConnector;
/**
 * Class for skill slots
 * @author Isangeles
 *
 */
public class SkillSlot extends Slot implements MouseListener 
{
	private Skill skillInSlot;
	/**
	 * Skill slot constructor
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public SkillSlot(GameContainer gc) throws SlickException, IOException, FontFormatException 
	{
		super(GBase.getImage("uiSlotB"), gc);
	}
	/**
	 * Inserts skill in slot
	 * @param skill Skill tile
	 */
	public boolean insertContent(SlotContent skill)
	{
		if(!isFull() && Skill.class.isInstance(skill))
		{
			if(super.content.add((Skill)skill)) 
			{
				skillInSlot = (Skill)skill;
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
	
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.gui.Slot#insertContent(java.util.Collection)
	 */
	@Override
	public boolean insertContent(Collection<? extends SlotContent> content) 
	{
		return false; //Can't insert more then one skill to skill slot
	}
	
	public void click(boolean clicked)
	{
		if(!isEmpty())
			skillInSlot.getTile().click(clicked);
	}
	/**
	 * Uses skill in slot
	 */
	public void useSkill()
	{
		if(skillInSlot != null)
		{
		    CharacterOut out;
			out = skillInSlot.getOwner().useSkill(skillInSlot);
			if(out != CharacterOut.SUCCESS)
			{
	            Log.addSystem(out.toString());
			}
		}
	}
	
	public void dragged(boolean dragged)
	{
		skillInSlot.getTile().dragged(dragged);
	}
	
	public boolean isActive()
	{
		if(skillInSlot != null)
			return skillInSlot.isReady();
		else
			return false;
	}
	/**
	 * Checks if tile of skill in slots is dragged
	 * @return True if slot is dragged, false otherwise
	 */
	public boolean isSkillDragged()
	{
		if(!isEmpty())
			return skillInSlot.getTile().isDragged();
		else
			return false;
	}
	
	@Override
	public void removeContent()
	{
		super.removeContent();
		skillInSlot = null;
	}
	
	public List<? extends SlotContent> getContent()
	{
		if(!isEmpty())
			return content;
		else
			return null;
	}

	@Override
	public void mousePressed(int button, int x, int y)
	{
		super.mousePressed(button, x, y);
		if(!isEmpty() && isMouseOver() && button == Input.MOUSE_LEFT_BUTTON)
			skillInSlot.getTile().click(true);
	}

	@Override
	public void mouseReleased(int button, int x, int y) 
	{
		if(!isEmpty() && button == Input.MOUSE_LEFT_BUTTON)
		{
			if(isMouseOver())
				useSkill();
			else
				skillInSlot.getTile().click(false);
		}
	}
}
