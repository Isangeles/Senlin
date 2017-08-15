package pl.isangeles.senlin.gui.tools;

import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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
	
	public SkillSlot(GameContainer gc) throws SlickException, IOException, FontFormatException 
	{
		super(GBase.getImage("uiSlotB"), gc);
		gc.getInput().addMouseListener(this);
	}
	/**
	 * Inserts skill in slot
	 * @param skill Skill tile
	 */
	public boolean insertContent(SlotContent skill)
	{
		if(!isFull())
		{
			try
			{
				skillInSlot = (Skill)skill;
				return super.content.add(skill);
			}
			catch(ClassCastException e)
			{
				return false;
			}
		}
		else
			return false;
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
	
	public Skill getContent()
	{
		return skillInSlot;
	}

	@Override
	public void inputEnded() 
	{
	}

	@Override
	public void inputStarted() 
	{
	}

	@Override
	public boolean isAcceptingInput() 
	{
		return true;
	}

	@Override
	public void setInput(Input input) 
	{
	}

	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) 
	{
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) 
	{
	}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) 
	{
	}

	@Override
	public void mousePressed(int button, int x, int y)
	{
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

	@Override
	public void mouseWheelMoved(int arg0) 
	{
	}

}
