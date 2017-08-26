package pl.isangeles.senlin.gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import pl.isangeles.senlin.core.skill.Skill;
import pl.isangeles.senlin.data.GBase;
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
	protected List<SlotContent> content = new ArrayList<>();
	private TrueTypeFont ttf;
	
	private Slot(InputStream is, String ref, boolean flipped, GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		super(is, ref, flipped, gc);
		
		Font font = GBase.getFont("mainUiFont");
		ttf = new TrueTypeFont(font.deriveFont(getSize(9f)), true);
	}
	
	public Slot(Image tex, GameContainer gc) throws SlickException, IOException, FontFormatException
	{
		super(tex, gc);
		
		File fontFile = new File("data" + File.separator + "font" + File.separator + "SIMSUN.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		ttf = new TrueTypeFont(font.deriveFont(getSize(9f)), true);
	}

	/**
	 * Checks if slot is empty
	 * @return True if slot is empty, false otherwise
	 */
	public boolean isEmpty()
	{
		return content.size() == 0;
	}
	/**
	 * Checks if slot is full
	 * @return
	 */
	public boolean isFull()
	{
		if(!isEmpty())
		{
			return (content.size() == content.get(0).getMaxStack());
		}
		else
			return false;
	}
	/**
	 * Inserts content in slot
	 * @param content Content for slot
	 */
	public abstract boolean insertContent(SlotContent content);
	/**
	 * Inserts all content from specified collection to slot
	 * @param content Collection with content
	 * @return True if insertion was successfully, false otherwise
	 */
	public abstract boolean insertContent(Collection<? extends SlotContent> content);
	/**
	 * Removes content from slot
	 */
	public void removeContent()
	{
		content.clear();
	}
	/**
	 * Returns slot content
	 * @return Slot content or null
	 */
	public List<? extends SlotContent> getContent()
	{
		if(isEmpty())
			return null;
		else
			return content;
	}
	
	@Override
	public void draw(float x, float y, boolean scaledPos)
	{
		super.draw(x, y, false);
		if(!isEmpty())
		{
			content.get(0).draw(x - getDis(3), y - getDis(3), scaledPos);
			if(content.size() > 1)
			{
				ttf.drawString(x, y, content.size()+"");
			}
		}
	}
	
	public void click(boolean clicked)
	{
		if(!isEmpty())
			content.get(0).getTile().click(clicked);
	}
	
	public void dragged(boolean dragged)
	{
		for(SlotContent con : content)
		{
			con.getTile().dragged(dragged);
		}
	}
	/**
	 * Checks if content tile in slot is dragged
	 * @return True if slot is dragged, false otherwise
	 */
	public boolean isContentDragged()
	{
		if(!isEmpty())
			return content.get(0).getTile().isDragged();
		else
			return false;
	}
	
	
}
