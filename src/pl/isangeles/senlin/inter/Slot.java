package pl.isangeles.senlin.inter;

import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.states.Global;
import pl.isangeles.senlin.util.GConnector;
/**
 * Abstract class for interface slots
 * TODO join super class content field and child classes specific content fields into one super class field 
 * @author Isangeles
 *
 */
public abstract class Slot extends InterfaceObject 
{
	protected SlotContent content;
	
	public Slot(InputStream is, String ref, boolean flipped, GameContainer gc) throws SlickException, IOException
	{
		super(is, ref, flipped, gc);
	}

	/**
	 * Checks if slot is empty
	 * @return True if slot is empty, false otherwise
	 */
	public abstract boolean isNull();
	/**
	 * Inserts content in slot
	 * @param content Content for slot
	 */
	public abstract boolean insertContent(SlotContent content);
	/**
	 * Removes content from slot
	 */
	public abstract void removeContent();
	/**
	 * Returns slot content
	 * @return Slot content or null
	 */
	public abstract SlotContent getContent();
	
	@Override
	public void draw(float x, float y, boolean scaledPos)
	{
		super.draw(x, y, false);
	}
	
	public void click(boolean clicked)
	{
		if(!isNull())
			content.getTile().click(clicked);
	}
	
	public void dragged(boolean dragged)
	{
		content.getTile().dragged(dragged);
	}
	/**
	 * Checks if content tile in slot is dragged
	 * @return True if slot is dragged, false otherwise
	 */
	public boolean isContentDragged()
	{
		if(!isNull())
			return content.getTile().isDragged();
		else
			return false;
	}
	
	
}
