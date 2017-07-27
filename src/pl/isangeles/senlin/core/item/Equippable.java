package pl.isangeles.senlin.core.item;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Bonuses;
import pl.isangeles.senlin.core.action.EquipAction;
import pl.isangeles.senlin.graphic.AnimObject;
/**
 * Class for equippable items
 * @author Isangeles
 *
 */
public abstract class Equippable extends Item 
{
	protected int reqLevel;
	protected final int type;
	protected final ItemMaterial material;
	protected Bonuses bonuses;
	protected AnimObject itemSprite;
	
	protected abstract String getTypeName();
	
	public Equippable(String id, int value, String imgName, GameContainer gc, int reqLevel, Bonuses bonuses, int type, ItemMaterial material)
			throws SlickException, IOException, FontFormatException
	{
		super(id, value, 1, imgName, gc);
		this.reqLevel = reqLevel;
		this.bonuses = bonuses;
		this.type = type;
		this.material = material;
		onUse = new EquipAction(this);
	}
	
	public Equippable(String id, int serial, int value, String imgName, GameContainer gc, int reqLevel, Bonuses bonuses, int type, ItemMaterial material)
			throws SlickException, IOException, FontFormatException
	{
		super(id, serial, value, 1, imgName, gc);
		this.reqLevel = reqLevel;
		this.bonuses = bonuses;
		this.type = type;
		this.material = material;
		onUse = new EquipAction(this);
	}
	/**
	 * Return item type
	 * @return Integer representing item type
	 */
	public int type()
	{
		return this.type;
	}
	
	public AnimObject getSprite()
	{
		return itemSprite;
	}

}
