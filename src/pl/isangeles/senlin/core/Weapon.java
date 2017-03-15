package pl.isangeles.senlin.core;

public class Weapon extends Item 
{
	int maxDamage;
	int minDamage;
	Bonuses bonuses;
	
	public Weapon(String id, String name, String info, int maxDmg, int minDmg, int value, Bonuses bonuses) 
	{
		super(id, name, info, value);
	}

}
