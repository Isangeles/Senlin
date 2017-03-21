package pl.isangeles.senlin.core;

import pl.isangeles.senlin.util.TConnector;

public class Bonuses 
{
	private int strBonus;
	private int conBonus;
	private int dexBonus;
	private int intBonus;
	private int wisBonus;
	private int dmgBonus;
	private float hasteBonus;
	
	public Bonuses()
	{
	}
	
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
	
	public boolean isBonus()
	{
		if(strBonus != 0 || conBonus != 0 || dexBonus != 0 
		|| intBonus != 0 || wisBonus != 0 || dmgBonus != 0 
		|| hasteBonus != 0)
			return true;
		else
			return false;
	}
	
	public String getInfo()
	{
		String bonusInfo = "";
		
		if(strBonus != 0)
			bonusInfo += TConnector.getText("textUi.txt", "attStrName") + "+" + strBonus + System.lineSeparator();
		if(conBonus != 0 )
			bonusInfo += TConnector.getText("textUi.txt", "attConName") + "+" + conBonus + System.lineSeparator();
		if(dexBonus != 0 )
			bonusInfo += TConnector.getText("textUi.txt", "attDexName") + "+" + dexBonus + System.lineSeparator();
		if(intBonus != 0 )
			bonusInfo += TConnector.getText("textUi.txt", "attIntName") + "+" + intBonus + System.lineSeparator();
		if(wisBonus != 0 )
			bonusInfo += TConnector.getText("textUi.txt", "attWisName") + "+" + wisBonus + System.lineSeparator();
		if(dmgBonus != 0 )
			bonusInfo += TConnector.getText("textUi.txt", "dmgName") + "+" + dmgBonus + System.lineSeparator();
		if(hasteBonus != 0 )
			bonusInfo += TConnector.getText("textUi.txt", "hastName") + "+" + hasteBonus + System.lineSeparator();
		
		return bonusInfo;
	}
}
