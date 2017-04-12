package pl.isangeles.senlin.core.item;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.core.Bonuses;
import pl.isangeles.senlin.util.TConnector;
/**
 * Class for trinkets like rings, amulets, etc.
 * @author Isangeles
 *
 */
public class Trinket extends Equippable 
{
	public static final int FINGER = 0,
							NECK = 1,
							ARTIFACT = 2;
	
	public Trinket(String id, int type, int value, String imgName, GameContainer gc, int reqLevel,
			Bonuses bonuses) throws SlickException, IOException, FontFormatException 
	{
		super(id, value, imgName, gc, reqLevel, bonuses, type, 0); //0 is for material
		this.itemTile = this.setTile(gc);
	}

	@Override
	protected String getInfo() 
	{
		String fullInfo = name + System.lineSeparator() + getTypeName() + System.lineSeparator() + bonuses.getInfo() + TConnector.getText("ui", "itemRLInfo") + ": " + reqLevel + 
						  System.lineSeparator() + info + System.lineSeparator() + TConnector.getText("ui", "itemVInfo") + ": " + value;
		return fullInfo;
	}
	@Override
	protected String getTypeName()
	{
		switch(type)
		{
		case FINGER:
			return TConnector.getText("ui", "triFinger");
		case NECK:
			return TConnector.getText("ui", "triNeck");
		case ARTIFACT:
			return TConnector.getText("ui", "triArtifact");
		default:
			return TConnector.getText("ui", "errorName");
		}
	}
}
