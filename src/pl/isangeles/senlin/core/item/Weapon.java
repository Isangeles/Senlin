package pl.isangeles.senlin.core.item;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Bonuses;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
import pl.isangeles.senlin.graphic.AnimObject;
import pl.isangeles.senlin.inter.ui.ItemTile;
/**
 * Class for weapons
 * @author Isangeles
 *
 */
public class Weapon extends Equippable 
{
	public static final int DAGGER = 0,
							SWORD = 1,
							AXE = 2,
							MACE = 3,
							SPEAR = 4,
							BOW = 5,
							FIST = 6;
	private int maxDamage;
	private int minDamage;
	/**
	 * Weapon constructor
	 * @param id Weapon ID	
	 * @param name Weapon name
	 * @param info Informations about weapon
	 * @param type Weapon type (0-5)
	 * @param material Weapon material (0-2)
	 * @param value Weapon value
	 * @param maxDmg Max weapon damage
	 * @param minDmg Min weapon damage
	 * @param bonuses Weapon bonuses
	 * @param reqLevel Required level
	 * @param picName Weapon icon image file name
	 * @param gc Slick game container
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Weapon(String id, int type, ItemMaterial material, int value, int minDmg, int maxDmg, Bonuses bonuses, int reqLevel, String picName, String spriteName, GameContainer gc) 
			throws SlickException, IOException, FontFormatException 
	{
		super(id, value, picName, gc, reqLevel, bonuses, type, material);
		this.minDamage = minDmg;
		this.maxDamage = maxDmg;
        this.itemTile = this.setTile(gc);
        itemSprite = new AnimObject(GConnector.getInput("sprite/item/" + spriteName), "sprite"+id, false, 60, 70);
	}
	/**
	 * Returns weapon maximal and minimal damage
	 * @return Table with min[0] and max[1] damage
	 */
	public int[] getDamage()
	{
		return new int[]{minDamage, maxDamage};
	}
	
	public int getType()
	{
		return type;
	}
	/**
	 * Returns full info about item
	 */
	@Override
	protected String getInfo()
	{
		String fullInfo = name + System.lineSeparator() +  getTypeName() + System.lineSeparator() + getMaterialName() + System.lineSeparator() +
						TConnector.getText("ui", "dmgName") + ": " +  minDamage + "-" + maxDamage + System.lineSeparator() + bonuses.getInfo() + 
						TConnector.getText("ui", "itemRLInfo") + ": " + reqLevel + System.lineSeparator() + info + System.lineSeparator() + 
						TConnector.getText("ui", "itemVInfo") + ": " + value;
		
		return fullInfo;
	}
	
	@Override
	protected String getTypeName()
	{
		switch(type)
		{
		case DAGGER:
			return TConnector.getText("ui", "weaDagger");
		case SWORD:
			return TConnector.getText("ui", "weaSword");
		case AXE:
			return TConnector.getText("ui", "weaAxe");
		case MACE:
			return TConnector.getText("ui", "weaMace");
		case SPEAR:
			return TConnector.getText("ui", "weaSpear");
		case BOW:
			return TConnector.getText("ui", "weaBow");
		default:
			return TConnector.getText("ui", "errorName");
		}
	}
	
	private String getMaterialName()
	{
		return material.toString();
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
    	return new ItemTile(GConnector.getInput("icon/"+imgName), id+itemNumber, false, gc, this.getInfo());
    }
}
