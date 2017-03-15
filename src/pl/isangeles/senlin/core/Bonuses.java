package pl.isangeles.senlin.core;

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
}
