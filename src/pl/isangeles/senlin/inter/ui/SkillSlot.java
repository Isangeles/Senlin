package pl.isangeles.senlin.inter.ui;

import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.item.Item;
import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.inter.GameCursor;
import pl.isangeles.senlin.inter.InterfaceObject;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.GConnector;
/**
 * Class for skill slots
 * @author Isangeles
 *
 */
public class SkillSlot extends InterfaceObject implements MouseListener 
{
	private Skill skillInSlot;
	
	public SkillSlot(GameContainer gc) throws SlickException, IOException 
	{
		super(GConnector.getInput("ui/slotB.png"), "uiSSlot", false, gc);
		gc.getInput().addMouseListener(this);
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
	
	public void click(boolean clicked)
	{
		if(!isNull())
			skillInSlot.getTile().click(clicked);
	}
	/**
	 * Removes skill from slot
	 */
	public void removeSkill()
	{
		skillInSlot = null;
	}
	
	public void useSkill()
	{
		if(skillInSlot != null)
			Global.getPlayer().useSkill(skillInSlot);
	}
	
	public void dragged(boolean dragged)
	{
		skillInSlot.getTile().dragged(dragged);
	}
	
	public boolean isActive()
	{
		if(skillInSlot != null)
			return skillInSlot.isActive();
		else
			return false;
	}
	/**
	 * Checks if tile of skill in slots is dragged
	 * @return True if slot is dragged, false otherwise
	 */
	public boolean isSkillDragged()
	{
		if(!isNull())
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
		if(!isNull() && isMouseOver() && button == Input.MOUSE_LEFT_BUTTON)
			skillInSlot.getTile().click(true);
	}

	@Override
	public void mouseReleased(int button, int x, int y) 
	{
		if(!isNull() && button == Input.MOUSE_LEFT_BUTTON)
		{
			if(isMouseOver())
				Global.getPlayer().useSkill(skillInSlot);
			else
				skillInSlot.getTile().click(false);
		}
	}

	@Override
	public void mouseWheelMoved(int arg0) 
	{
	}

}
