package pl.isangeles.senlin.core.item;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Action;
import pl.isangeles.senlin.core.ActionType;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.inter.ui.ItemTile;
import pl.isangeles.senlin.util.GConnector;
/**
 * Class for miscellaneous items
 * @author Isangeles
 *
 */
public class Misc extends Item 
{
	/**
	 * Miscellaneous item constructor
	 * @param id Item ID
	 * @param value Item value
	 * @param imgName Item image name, for icon
	 * @param onUse Action on use(ppm click in inventory)
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Misc(String id, int value, String imgName, Action onUse, GameContainer gc) throws SlickException, IOException, FontFormatException 
	{
		super(id, value, imgName, gc);
		this.onUse = onUse;
		this.itemTile = setTile(gc);
	}
    /**
     * Returns basic info about item for item tile
     * @return String with basic info
     */
    protected String getInfo()
    {
    	String fullInfo = name + System.lineSeparator() + info + System.lineSeparator() + "Value: " + value;
    	return fullInfo;
    }
    /* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.Usable#use(pl.isangeles.senlin.core.Targetable, pl.isangeles.senlin.core.Targetable)
	 */
    @Override
    public boolean use(Targetable user, Targetable target)
    {
    	return onUse.start(user, target);
    }
	/* (non-Javadoc)
	 * @see pl.isangeles.senlin.core.item.Item#setTile(org.newdawn.slick.GameContainer)
	 */
	@Override
	protected ItemTile setTile(GameContainer gc) throws SlickException, IOException, FontFormatException 
    {
    	return new ItemTile(GConnector.getInput("icon/item/misc/"+imgName), id+itemNumber, false, gc, this.getInfo());
    }
}
