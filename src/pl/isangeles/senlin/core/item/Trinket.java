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
	
	public Trinket(String id, String name, String info, int type, int value, String imgName, GameContainer gc, int reqLevel,
			Bonuses bonuses) throws SlickException, IOException, FontFormatException 
	{
		super(id, name, info, value, imgName, gc, reqLevel, bonuses, type);
	}

	@Override
	protected String getInfo() 
	{
		String fullInfo = name + System.lineSeparator() + getType() + System.lineSeparator() + bonuses.getInfo() + TConnector.getText("ui", "itemRLInfo") + ": " + reqLevel + 
						  System.lineSeparator() + info + System.lineSeparator() + TConnector.getText("ui", "itemVInfo") + ": " + value;
		return fullInfo;
	}
	@Override
	protected String getType()
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
