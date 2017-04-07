package pl.isangeles.senlin.core.item;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Bonuses;
import pl.isangeles.senlin.data.CommBase;
import pl.isangeles.senlin.util.TConnector;
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
							BOW = 5;
	public static final int IRON = 0,
							STEEL = 1,
							NEPHRITE = 2;
	private int maxDamage;
	private int minDamage;
	private int material;
	/**
	 * Weapon constructor
	 * @param id Weapon ID	
	 * @param name Weapon name
	 * @param info Informations about weapon
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
	public Weapon(String id, String name, String info, int type, int material,int value, int minDmg, int maxDmg, Bonuses bonuses, int reqLevel, String picName, GameContainer gc) 
			throws SlickException, IOException, FontFormatException 
	{
		super(id, name, info, value, picName, gc, reqLevel, bonuses, type);
		this.minDamage = minDmg;
		this.maxDamage = maxDmg;
        this.itemTile = this.setTile(gc);
        this.material = material;
	}
	@Deprecated
	@Override
	public Weapon clone()
	{
		return (Weapon) super.clone();
	}
	
	public int[] getDamage()
	{
		return new int[]{minDamage, maxDamage};
	}
	
	@Override
	protected String getInfo()
	{
		String fullInfo = name + System.lineSeparator() +  getType() + System.lineSeparator() + getMaterial() + System.lineSeparator() +
						TConnector.getText("ui", "dmgName") + ": " +  minDamage + "-" + maxDamage + System.lineSeparator() + bonuses.getInfo() + 
						TConnector.getText("ui", "itemRLInfo") + ": " + reqLevel + System.lineSeparator() + info + System.lineSeparator() + 
						TConnector.getText("ui", "itemVInfo") + ": " + value + " "	+ itemNumber;
		
		return fullInfo;
	}
	
	@Override
	protected String getType()
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
	
	private String getMaterial()
	{
		switch(material)
		{
		case IRON:
			return TConnector.getText("ui", "matIron");
		case STEEL:
			return TConnector.getText("ui", "matSteel");
		case NEPHRITE:
			return TConnector.getText("ui", "matNephr");
		default:
			return TConnector.getText("ui", "errorName");
		}
	}
}
