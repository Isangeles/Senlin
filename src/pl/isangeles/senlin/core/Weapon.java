package pl.isangeles.senlin.core;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.data.CommBase;
import pl.isangeles.senlin.util.TConnector;
/**
 * Class for weapons
 * @author Isangeles
 *
 */
public class Weapon extends Equippable 
{
	int maxDamage;
	int minDamage;
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
	public Weapon(String id, String name, String info, int value, int minDmg, int maxDmg, Bonuses bonuses, int reqLevel, String picName, GameContainer gc) 
			throws SlickException, IOException, FontFormatException 
	{
		super(id, name, info, value, picName, gc, reqLevel, bonuses);
		this.minDamage = minDmg;
		this.maxDamage = maxDmg;
        this.itemTile = this.setTile(gc);
	}
	
	@Override
	public Weapon clone()
	{
		return (Weapon) super.clone();
	}
	
	@Override
	protected String getInfo()
	{
		String fullInfo = name + System.lineSeparator() +  TConnector.getText("textUI.txt", "dmgName") + ": " +  minDamage + "-" + maxDamage 
						+ System.lineSeparator() + bonuses.getInfo() + TConnector.getText("textUI.txt", "itemRLInfo") 
						+ ": " + reqLevel + System.lineSeparator() + info + System.lineSeparator() + TConnector.getText("textUI.txt", "itemVInfo") + ": " + value + " "	+ itemNumber;
		
		return fullInfo;
	}
}
