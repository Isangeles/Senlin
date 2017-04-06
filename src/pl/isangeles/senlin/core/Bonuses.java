package pl.isangeles.senlin.core;

import pl.isangeles.senlin.util.TConnector;
/**
 * Class representing bonuses(e.g. items bonuses)
 * @author Isnageles
 *
 */
public class Bonuses 
{
	private int strBonus;
	private int conBonus;
	private int dexBonus;
	private int intBonus;
	private int wisBonus;
	private int dmgBonus;
	private float hasteBonus;
	/**
	 * Bonuses constructor
	 * @param str Bonus to strength
	 * @param con Bonus to constitution
	 * @param dex Bonus to dexterity
	 * @param inte Bonus to intelligence
	 * @param wis Bonus to wisdom
	 * @param dmg Bonus to damage 
	 * @param haste Bonus to haste
	 */
	public Bonuses(int str, int con, int dex, int inte, int wis, int dmg, float haste)
	{
		strBonus = str;
		conBonus = con;
		dexBonus = dex;
		intBonus = inte;
		wisBonus = wis;
		dmgBonus = dmg;
		hasteBonus = haste;
	}
	/**
	 * Check if any bonus occurs
	 * @return
	 */
	public boolean isBonus()
	{
		if(strBonus != 0 || conBonus != 0 || dexBonus != 0 
		|| intBonus != 0 || wisBonus != 0 || dmgBonus != 0 
		|| hasteBonus != 0)
			return true;
		else
			return false;
	}
	/**
	 * Get full info about this specific bonus
	 * @return String with full info about bonus
	 */
	public String getInfo()
	{
		String bonusInfo = "";
		
		if(strBonus != 0)
			bonusInfo += TConnector.getText("ui", "attStrName") + "+" + strBonus + System.lineSeparator();
		if(conBonus != 0 )
			bonusInfo += TConnector.getText("ui", "attConName") + "+" + conBonus + System.lineSeparator();
		if(dexBonus != 0 )
			bonusInfo += TConnector.getText("ui", "attDexName") + "+" + dexBonus + System.lineSeparator();
		if(intBonus != 0 )
			bonusInfo += TConnector.getText("ui", "attIntName") + "+" + intBonus + System.lineSeparator();
		if(wisBonus != 0 )
			bonusInfo += TConnector.getText("ui", "attWisName") + "+" + wisBonus + System.lineSeparator();
		if(dmgBonus != 0 )
			bonusInfo += TConnector.getText("ui", "dmgName") + "+" + dmgBonus + System.lineSeparator();
		if(hasteBonus != 0 )
			bonusInfo += TConnector.getText("ui", "hastName") + "+" + hasteBonus + System.lineSeparator();
		
		return bonusInfo;
	}
}
