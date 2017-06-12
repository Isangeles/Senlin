package pl.isangeles.senlin.core.item;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Bonuses;
import pl.isangeles.senlin.graphic.AnimObject;
import pl.isangeles.senlin.util.GConnector;
import pl.isangeles.senlin.util.TConnector;
/**
 * Class for armor parts
 * TODO implement different sprite sheet for each armor material
 * @author Isangeles
 *
 */
public class Armor extends Equippable 
{
	public static final int FEET = 0,
							HANDS = 1,
							OFFHAND = 2,
							CHEST = 4,
							HEAD = 5;
	public static final int CLOTH = 0,
							LEATHER = 1,
							IRON = 2,
							STEEL = 3,
							NEPHRITE = 4;
	private int armorRating;
	/**
	 * Armor constructor
	 * @param id Item ID
	 * @param name Item name
	 * @param info Item description
	 * @param type Armor type (0-5 value)
	 * @param material Material of which item is made (0-4 value)
	 * @param value Item value in gold
	 * @param armRat Armor rating value
	 * @param bonuses Armor bonuses to statistics
	 * @param reqLevel Level requested to wear armor
	 * @param imgName Name of image file in icon directory for item tile
	 * @param gc Slick game container for item tile
	 * @throws SlickException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Armor(String id, int type, int material, int value, int armRat, Bonuses bonuses, int reqLevel, String imgName, GameContainer gc) 
			throws SlickException, IOException, FontFormatException 
	{
		super(id, value, imgName, gc, reqLevel, bonuses, type, material);
		armorRating = armRat;
		this.itemTile = this.setTile(gc);
		if(type == CHEST)
			setSprite();
	}
	/**
	 * Returns item armor rating
	 * @return Armor rating value
	 */
	public int getArmorRat()
	{
		return armorRating;
	}
	
	@Override
	protected String getInfo()
	{
		String fullInfo = name + System.lineSeparator() +  getTypeName() + System.lineSeparator() + getMaterialName() + System.lineSeparator() + 
				TConnector.getText("ui", "armRat") + ": " + armorRating + System.lineSeparator() + bonuses.getInfo() + TConnector.getText("ui", "itemRLInfo") 
				+ ": " + reqLevel + System.lineSeparator() + info + System.lineSeparator() + TConnector.getText("ui", "itemVInfo") + ": " + value;

		return fullInfo;
	}
	
	@Override
	protected String getTypeName()
	{
		switch(type)
		{
		case FEET:
			return TConnector.getText("ui", "armFeet");
		case HANDS:
			return TConnector.getText("ui", "armHand");
		case OFFHAND:
			return TConnector.getText("ui", "armOff");
		case CHEST:
			return TConnector.getText("ui", "armChest");
		case HEAD:
			return TConnector.getText("ui", "armHead");
		default:
			return TConnector.getText("ui", "errorName");
		}
	}
	
	private String getMaterialName()
	{
		switch(material)
		{
		case CLOTH:
			return TConnector.getText("ui", "matCloth");
		case LEATHER:
			return TConnector.getText("ui", "matLeath");
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
	/**
	 * Sets sprite for item depended on item material 
	 * @throws SlickException
	 * @throws IOException
	 */
	private void setSprite() throws SlickException, IOException
	{
		switch(material)
		{
		case CLOTH:
			itemSprite = new AnimObject(GConnector.getInput("sprite/avatar/cloth12221-60x70.png"), "sprite"+id, false, 60, 70);
		case LEATHER:
			itemSprite = new AnimObject(GConnector.getInput("sprite/avatar/cloth12221-60x70.png"), "sprite"+id, false, 60, 70);
		case IRON:
			itemSprite = new AnimObject(GConnector.getInput("sprite/avatar/cloth12221-60x70.png"), "sprite"+id, false, 60, 70);
		case STEEL:
			itemSprite = new AnimObject(GConnector.getInput("sprite/avatar/cloth12221-60x70.png"), "sprite"+id, false, 60, 70);
		case NEPHRITE:
			itemSprite = new AnimObject(GConnector.getInput("sprite/avatar/cloth12221-60x70.png"), "sprite"+id, false, 60, 70);
		default:
			itemSprite = new AnimObject(GConnector.getInput("sprite/avatar/cloth12221-60x70.png"), "sprite"+id, false, 60, 70);
		}
	}
}
